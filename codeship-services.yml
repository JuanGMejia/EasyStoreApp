# Install java 8
jdk_switcher home oraclejdk8
jdk_switcher use oraclejdk8
export JAVA8_HOME=/usr/lib/jvm/java-8-oracle
export BUILD_TOOLS_VERSION=25.0.2
export ANDROID_SDK=25
export ANDROID_SDK_REV=24
#
# Install android sdk
export SDK_TAR=android-sdk_r$ANDROID_SDK_REV-linux
wget "http://dl.google.com/android/$SDK_TAR.tgz"
tar xvzf "$SDK_TAR.tgz"
rm "$SDK_TAR.tgz"
export ANDROID_HOME=$PWD/android-sdk-linux
export PATH="$ANDROID_HOME/tools:$ANDROID_HOME/platform-tools:$PATH"
echo "y" | android update sdk --no-ui --all --filter tools,platform-tools,build-tools-$BUILD_TOOLS_VERSION > step1.log
echo "y" | android update sdk --no-ui --all --filter extra-android-m2repository,extra-android-support,extra-google-analytics_sdk_v2,extra-google-google_play_services_froyo,extra-google-google_play_services,extra-google-m2repository > step2.log
echo "y" | android update sdk --no-ui --all --filter android-$ANDROID_SDK,sysimg-$ANDROID_SDK,addon-google_apis-google-$ANDROID_SDK > step3.log
#
# Setup gradle
touch local.properties
echo "sdk.dir=$ANDROID_HOME" >> local.properties
chmod u+x ./gradlew
#
# Build
./gradlew --no-color --quiet -S :app:assembleDebug
