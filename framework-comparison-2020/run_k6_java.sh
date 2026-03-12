java -jar micronaut.jar &
sleep 10
./k6 run --summary-export=micronaut1-$JAVA_VM.json k6.js
./k6 run --summary-export=micronaut2-$JAVA_VM.json k6.js
./k6 run --summary-export=micronaut3-$JAVA_VM.json k6.js
ps x -o rss,vsz,command | grep java
sleep 5
ps x -o rss,vsz,command | grep java
sleep 5
ps x -o rss,vsz,command | grep java
pkill java
sleep 10
pkill -9 java
sleep 5

java -jar ./quarkus-app/quarkus-run.jar &
sleep 10
./k6 run --summary-export=quarkus1-$JAVA_VM.json k6.js
./k6 run --summary-export=quarkus2-$JAVA_VM.json k6.js
./k6 run --summary-export=quarkus3-$JAVA_VM.json k6.js
ps x -o rss,vsz,command | grep java
sleep 5
ps x -o rss,vsz,command | grep java
sleep 5
ps x -o rss,vsz,command | grep java
pkill java
sleep 10
pkill -9 java
sleep 5


java -jar springboot.jar &
sleep 10
./k6 run --summary-export=springboot1-$JAVA_VM.json k6.js
./k6 run --summary-export=springboot2-$JAVA_VM.json k6.js
./k6 run --summary-export=springboot3-$JAVA_VM.json k6.js
ps x -o rss,vsz,command | grep java
sleep 5
ps x -o rss,vsz,command | grep java
sleep 5
ps x -o rss,vsz,command | grep java
pkill java
sleep 10
pkill -9 java
sleep 5

