export JAVA_HOME=`pwd`/openjdk
OLD_PATH=$PATH
PATH=$JAVA_HOME/bin:$OLD_PATH
java -version

npm run time
sleep 5
npm run time
sleep 5
npm run time
sleep 5
npm run time
sleep 10

export JAVA_VM="openjdk"
source ./run_k6_java.sh
source ./run_k6_native.sh

