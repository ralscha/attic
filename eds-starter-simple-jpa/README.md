# Starter application with Ext JS 7 Modern Toolkit and Spring Boot

**Run the application on your local machine**

## Prerequisites
  - ExtJS subscription
  - Install Java: https://jdk.java.net/
  - Install Node.js: https://nodejs.org/en/
  - Login: `npm login --registry=https://npm.sencha.com --scope=@sencha`
	
## Development	
  1. Clone the repository
  2. ```cd eds-starter-simple-jpa/clientopen```
  3. ```npm install```
  4. ```npm start```
  5. In another shell ```cd eds-starter-simple-jpa```
  6. ```./mvnw spring-boot:run -Dspring.profiles.active="development"```
  7. Open url http://localhost:8080 in a browser


## Production Build
  1. ```./mvnw clean package```
  2. The file ```target/eds-starter-simple-jpa.jar``` contains the whole application. Deploy it to a server.
  3. Start the application with ```java -jar <any_folder>/eds-starter-simple-jpa.jar```
  4. The application listens by default on port 80
