#!/usr/bin/env bash

set -x

export PROJECT_NAME=uiautomator

adb shell am start org.bangbang.song.android.commonlib/org.bangbang.song.android.MainActivity

android create uitest-project -n $PROJECT_NAME -t $ANDROID_TARGET -p .
ant build
adb push bin/$PROJECT_NAME.jar /data/local/tmp/
adb shell uiautomator runtest $PROJECT_NAME.jar -c org.bangbang.song.android.commonlib.uiautomator.DemoUiTest

#adb shell uiautomator dump /data/local/tmp/window_dump.xml
#adb pull /data/local/tmp/window_dump.xml .
#cat window_dump.xml
