#!/bin/sh

function find_java() {
    # Find Java
    if [[ -n "$JAVA_HOME" ]] && [[ -x "$JAVA_HOME/bin/java" ]];  then
        javaexe="$JAVA_HOME/bin/java"
    elif type -p java > /dev/null 2>&1; then
        javaexe=$(type -p java)
    elif [[ -x "/usr/bin/java" ]];  then
        javaexe="/usr/bin/java"
    else
        echo "Unable to find Java, Please set JAVA_HOME or put java (>=1.7) in your PATH."
        exit 1
    fi

    if [[ "$javaexe" ]]; then
        version=$("$javaexe" -version 2>&1 | awk -F '"' '/version/ {print $2}')
      if [[ "$version" > $JAVA_VERSION ]]; then
        echo Java version "$version"
      else
        echo javaexe=$javaexe
        echo Java version "$version" is less than required $JAVA_VERSION
        exit 1
      fi
    fi
}

# ANSI Colors
function echoRed() { echo $'\e[0;31m'"$1"$'\e[0m'; }
function echoGreen() { echo $'\e[0;32m'"$1"$'\e[0m'; }
function echoYellow() { echo $'\e[0;33m'"$1"$'\e[0m'; }

# Utility functions
function checkPermissions() {
  touch "$pid_file" &> /dev/null || { echoRed "Operation not permitted (cannot access pid file)"; return 4; }
}

function isRunning() {
  ps -p "$1" &> /dev/null
}

function await_file() {
  end=$(date +%s)
  let "end+=10"
  while [[ ! -s "$1" ]]; do
    now=$(date +%s)
    if [[ $now -ge $end ]]; then
      break
    fi
    sleep 1
  done
}

# Action functions
function start() {
  if [[ -f "$pid_file" ]]; then
    pid=$(cat "$pid_file")
    isRunning "$pid" && { echoYellow "Already running [$pid]"; return 0; }
  fi
  do_start "$@"
}

function do_start() {
  # setsid
  $command > /dev/null 2>&1 &
  pid=$!
  echo "$pid" > "$pid_file"
  [[ -z $pid ]] && { echoRed "Failed to start"; return 1; }
  echoGreen "Started [$pid]"
}

function stop() {
  [[ -f $pid_file ]] || { echoYellow "Not running (pidfile not found)"; return 0; }
  pid=$(cat "$pid_file")
  isRunning "$pid" || { echoYellow "Not running (process ${pid}). Removing stale pid file."; rm -f "$pid_file"; return 0; }
  do_stop "$pid" "$pid_file"
}

function do_stop() {

    # First, we will try to trigger a controlled shutdown using
    # spring-boot-actuator
    # curl -X POST http://localhost:$APP_PORT/shutdown < /dev/null > /dev/null 2>&1

  kill "$1" &> /dev/null || { echoRed "Unable to kill process $1"; return 1; }
  for i in $(seq 1 60); do
    isRunning "$1" || { echoGreen "Stopped [$1]"; rm -f "$2"; return 0; }
    [[ $i -eq 30 ]] && kill "$1" &> /dev/null
    sleep 1
  done
  echoRed "Unable to kill process $1";
  return 1;
}

function restart() {
  stop && start
}

function force_reload() {
  [[ -f $pid_file ]] || { echoRed "Not running (pidfile not found)"; return 7; }
  pid=$(cat "$pid_file")
  rm -f "$pid_file"
  isRunning "$pid" || { echoRed "Not running (process ${pid} not found)"; return 7; }
  do_stop "$pid" "$pid_file"
  do_start
}

function status() {
  [[ -f "$pid_file" ]] || { echoRed "Not running"; return 3; }
  pid=$(cat "$pid_file")
  isRunning "$pid" || { echoRed "Not running (process ${pid} not found)"; return 1; }
  echoGreen "Running [$pid]"
  return 0
}

function run() {
#  pushd "$(dirname "$jarfile")" > /dev/null
  $command
  result=$?
#  popd > /dev/null
  return "$result"
}



JAVA_VERSION=1.7
APP_NAME=slider
MAIN_CLASS=com.laic.slider.SliderApplication
INSTANCE=0

[[ -n "$DEBUG" ]] && set -x
[[ -n "$APP_NAME" ]] && identity=$APP_NAME
PID_FOLDER=/tmp

#分析命令行参数
while getopts ci:p: opt; do
    case "$opt" in
        c) CONSOLE=true ;;
        i) INSTANCE=${OPTARG} ;;
        p) PROFILE=${OPTARG} ;;
        \?)
            echoRed "Invalid option: -$OPTARG" >&2
            exit 1
            ;;
        :)
            echoRed "Option -$OPTARG requires an argument." >&2
            exit 1
            ;;
    esac
done
shift $(( OPTIND - 1 )) # now do something with $@

if [ -z "$APP_HOME" ]; then
    APP_HOME=$(dirname "$0")
    APP_HOME=`( cd "$APP_HOME" && cd .. && pwd )`
fi
cd $APP_HOME

find_java

# 遍历应用程序依赖的jar包，并加入CLASSPATH。
for jarfile in $(ls lib); do
    CLASSPATH="${CLASSPATH}:lib/$jarfile"
done

JAVA_OPTS="-Xmx2048m -Dsun.misc.URLClassPath.disableJarChecking=true -Dlogging.config=file:config/logback-spring.xml"
[[ -n "${INSTANCE}" ]] && JAVA_OPTS="${JAVA_OPTS} -Dinstance=${INSTANCE}"
[[ -n "${PROFILE}" ]] && JAVA_OPTS="${JAVA_OPTS} -Dspring.profiles.active=${PROFILE}"
[[ -n "${CONSOLE}" ]] && JAVA_OPTS="${JAVA_OPTS} -Dspring.profiles.active=dev"

pid_file=${PID_FOLDER}/${identity}.${INSTANCE}.pid

# Determine the script mode
if [ $# -lt 1 ] ; then
    action=run
else
    action="$1"
    shift
fi

# Build actual command to execute
command="$javaexe $JAVA_OPTS -classpath $CLASSPATH $MAIN_CLASS $*"

# Call the appropriate action function
case ${action} in
    start)
      start "$@"; exit $?;;
    stop)
      stop "$@"; exit $?;;
    restart)
      restart "$@"; exit $?;;
    force-reload)
      force_reload "$@"; exit $?;;
    status)
      status "$@"; exit $?;;
    run)
      run "$@"; exit $?;;
    *)
      echo "Usage: $0 {start|stop|restart|force-reload|status|run}"; exit 1;
esac

exit 0

