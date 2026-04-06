# precompressed
Testing the new EncodedResourceResolver in Spring 5.1

https://jira.spring.io/browse/SPR-16381


```
git clone https://github.com/ralscha/precompressed.git
cd precompressed
cd client
npm install
npm run dist

cd ..
.\mvnw.cmd spring-boot:run (windows)
./mvnw spring-boot:run (linux)
```

Open URL http://localhost:8080/index.html in browser
