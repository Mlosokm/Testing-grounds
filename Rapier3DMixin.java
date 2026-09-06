package com.mlosokm.sableandroidpatch.mixin;

import dev.ryanhcode.sable.physics.impl.rapier.Rapier3D;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * Android ARM64 loader patch for Sable 2.0.x.
 *
 * Sable normally extracts sable_rapier_aarch64_linux.so into the Minecraft
 * game directory and calls System.load() on that path. Android 16 can reject
 * native libraries loaded from shared/external storage because of linker
 * namespace restrictions.
 *
 * This overwrite instead loads the Android/Bionic ARM64 native packaged by
 * the compatibility mod from a launcher-private temporary directory.
 *
 * Required resource inside the compatibility mod:
 * natives/sable-rapier/sable_rapier_aarch64_android.so
 */
@Mixin(Rapier3D.class)
public final class Rapier3DMixin {
    private static final String ANDROID_NATIVE =
            "/natives/sable-rapier/sable_rapier_aarch64_android.so";

    @Overwrite
    private static void loadLibrary() {
        try (InputStream nativeStream = Rapier3DMixin.class.getResourceAsStream(ANDROID_NATIVE)) {
            if (nativeStream == null) {
                throw new IllegalStateException(
                        "Sable Android Patch: missing resource " + ANDROID_NATIVE);
            }

            // Files.createTempFile follows the launcher/JVM temp directory. On
            // Android launchers this is normally inside the app-private area,
            // which avoids the external-storage linker namespace restriction.
            final Path tempNative = Files.createTempFile("sable_rapier_android_", ".so");
            try {
                Files.copy(nativeStream, tempNative, StandardCopyOption.REPLACE_EXISTING);
                System.load(tempNative.toAbsolutePath().toString());
            } catch (Throwable loadFailure) {
                try {
                    Files.deleteIfExists(tempNative);
                } catch (Throwable ignored) {
                    // Keep the original native-loading failure as the useful error.
                }
                throw loadFailure;
            }

            // Do not delete a successfully loaded library here. Android's
            // linker may still need the backing file for the lifetime of the
            // process depending on the runtime/launcher implementation.
        } catch (Throwable failure) {
            throw new RuntimeException(
                    "Sable Android Patch could not load the ARM64 Android Rapier native. " +
                    "Make sure the compatibility mod contains " + ANDROID_NATIVE +
                    " and that it matches this Sable build.",
                    failure);
        }
    }
}
