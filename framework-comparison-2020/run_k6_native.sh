./quarkus-runner &
sleep 5
./k6 run --summary-export=quarkus-native1.json k6.js
./k6 run --summary-export=quarkus-native2.json k6.js
./k6 run --summary-export=quarkus-native3.json k6.js
ps x -o rss,vsz,command | grep quarkus-runner
sleep 5
ps x -o rss,vsz,command | grep quarkus-runner
sleep 5
ps x -o rss,vsz,command | grep quarkus-runner
pkill quarkus
sleep 10
pkill -9 quarkus
sleep 5

./springboot-runner &
sleep 5
./k6 run --summary-export=springboot-native1.json k6.js
./k6 run --summary-export=springboot-native2.json k6.js
./k6 run --summary-export=springboot-native3.json k6.js
ps x -o rss,vsz,command | grep springboot-runner
sleep 5
ps x -o rss,vsz,command | grep springboot-runner
sleep 5
ps x -o rss,vsz,command | grep springboot-runner
pkill springboot
sleep 10
pkill -9 springboot
sleep 5

./micronaut-runner &
sleep 5
./k6 run --summary-export=micronaut-native1.json k6.js
./k6 run --summary-export=micronaut-native2.json k6.js
./k6 run --summary-export=micronaut-native3.json k6.js
ps x -o rss,vsz,command | grep micronaut-runner
sleep 5
ps x -o rss,vsz,command | grep micronaut-runner
sleep 5
ps x -o rss,vsz,command | grep micronaut-runner
pkill micronaut
sleep 10
pkill -9 micronaut
sleep 5

./goexample &
sleep 5
./k6 run --summary-export=go1.json k6.js
./k6 run --summary-export=go2.json k6.js
./k6 run --summary-export=go3.json k6.js
ps x -o rss,vsz,command | grep goexample
sleep 5
ps x -o rss,vsz,command | grep goexample
sleep 5
ps x -o rss,vsz,command | grep goexample
pkill goexample
sleep 10
pkill -9 goexample
sleep 5


./gofibre &
sleep 5
./k6 run --summary-export=gofibre_1.json k6.js
./k6 run --summary-export=gofibre_2.json k6.js
./k6 run --summary-export=gofibre_3.json k6.js
ps x -o rss,vsz,command | grep gofibre
sleep 5
ps x -o rss,vsz,command | grep gofibre
sleep 5
ps x -o rss,vsz,command | grep gofibre
pkill gofibre
sleep 10
pkill -9 gofibre
sleep 5

./goecho &
sleep 5
./k6 run --summary-export=goecho_1.json k6.js
./k6 run --summary-export=goecho_2.json k6.js
./k6 run --summary-export=goecho_3.json k6.js
ps x -o rss,vsz,command | grep goecho
sleep 5
ps x -o rss,vsz,command | grep goecho
sleep 5
ps x -o rss,vsz,command | grep goecho
pkill goecho
sleep 10
pkill -9 goecho
sleep 5

./rustdemo &
sleep 5
./k6 run --summary-export=rust_salvo_1.json k6.js
./k6 run --summary-export=rust_salvo_2.json k6.js
./k6 run --summary-export=rust_salvo_3.json k6.js
ps x -o rss,vsz,command | grep rustdemo
sleep 5
ps x -o rss,vsz,command | grep rustdemo
sleep 5
ps x -o rss,vsz,command | grep rustdemo
pkill rustdemo
sleep 10
pkill -9 rustdemo