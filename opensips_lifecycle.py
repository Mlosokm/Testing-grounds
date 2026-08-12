"""OpenSIPS lifecycle wrapper test code for CodeRabbit review.

This is the current start/stop/restart implementation under review.
"""

import os
import subprocess


def opensips_start():
    pidfile = os.path.expanduser("~/opensips-bridge/opensips-session.pid")
    command = [
        "sh", "-lc",
        'PIDFILE="$HOME/opensips-bridge/opensips-session.pid"; '
        'if [ -s "$PIDFILE" ]; then '
        'PID=$(cat "$PIDFILE"); '
        'if kill -0 "$PID" 2>/dev/null; then '
        'echo ALREADY_RUNNING; exit 0; '
        'fi; '
        'rm -f "$PIDFILE"; '
        'fi; '
        'OUT=$(proot-distro login debian -- sh -lc '
        '"nohup /usr/local/sbin/opensips.real -f /usr/local/etc/opensips/opensips.cfg -F '
        '>/tmp/opensips.log 2>&1 & echo \\$!"); '
        'PID=$(printf "%s\\n" "$OUT" | tail -n1); '
        'case "$PID" in '
        '""|*[!0-9]*) echo START_FAILED; printf "%s\\n" "$OUT"; exit 1 ;; '
        'esac; '
        'sleep 1; '
        'if kill -0 "$PID" 2>/dev/null; then '
        'printf "%s\\n" "$PID" > "$PIDFILE"; '
        'echo STARTED; '
        'else '
        'echo START_FAILED; '
        'cat /tmp/opensips.log 2>/dev/null; '
        'exit 1; '
        'fi'
    ]
    return subprocess.run(command, check=False, capture_output=True, text=True)


def opensips_stop():
    command = [
        "sh", "-lc",
        'PIDFILE="$HOME/opensips-bridge/opensips-session.pid"; '
        'if [ ! -s "$PIDFILE" ]; then echo NOT_RUNNING; exit 0; fi; '
        'PID=$(cat "$PIDFILE"); '
        'if kill -0 "$PID" 2>/dev/null; then '
        'kill "$PID"; '
        'rm -f "$PIDFILE"; '
        'echo STOPPED; '
        'else '
        'rm -f "$PIDFILE"; '
        'echo NOT_RUNNING; '
        'fi'
    ]
    return subprocess.run(command, check=False, capture_output=True, text=True)


def opensips_restart():
    command = [
        "sh", "-lc",
        'PIDFILE="$HOME/opensips-bridge/opensips-session.pid"; '
        'if [ -s "$PIDFILE" ]; then '
        'PID=$(cat "$PIDFILE"); '
        'if kill -0 "$PID" 2>/dev/null; then '
        'kill "$PID"; '
        'sleep 1; '
        'fi; '
        'rm -f "$PIDFILE"; '
        'fi; '
        'OUT=$(proot-distro login debian -- sh -lc '
        '"nohup /usr/local/sbin/opensips.real -f /usr/local/etc/opensips/opensips.cfg -F '
        '>/tmp/opensips.log 2>&1 & echo \\$!"); '
        'PID=$(printf "%s\\n" "$OUT" | tail -n1); '
        'case "$PID" in '
        '""|*[!0-9]*) echo RESTART_FAILED; printf "%s\\n" "$OUT"; exit 1 ;; '
        'esac; '
        'sleep 1; '
        'if kill -0 "$PID" 2>/dev/null; then '
        'printf "%s\\n" "$PID" > "$PIDFILE"; '
        'echo RESTARTED; '
        'else '
        'echo RESTART_FAILED; '
        'cat /tmp/opensips.log 2>/dev/null; '
        'exit 1; '
        'fi'
    ]
    return subprocess.run(command, check=False, capture_output=True, text=True)
