# CgiPocDropwizard

A simple Dropwizard 1.0.5 project exposing REST API to store assets. The
application is using MySql as a RDBMS.

Database settings are in the config.yml file. To switch to another RDBMS one
should modify the driver and connection URL. By default it is necessary to create
CgiPocDropwizard database and it will be populated using Liquibase migrations.  


How to start the CgiPocDropwizard application
---

1. Create a key store in the project's folder using Java 8 *keytool*
`keytool -genkeypair -keyalg RSA -dname "CN=localhost" -keystore development.keystore -keypass p@ssw0rd -storepass p@ssw0rd`
2. Run `mvn clean package` to build the application
3. To populate the database first ensure that the config.yml file is matching with the database configs,
then run `java -jar target/cgi-poc-dw-1.0-SNAPSHOT.jar db migrate config.yml`
4. Start application with `java -jar target/cgi-poc-dw-1.0-SNAPSHOT.jar server config.yml`
5. To check that your application is running, enter URL `http://localhost:8081` in the browser

How to try the CgiPocDropwizard REST API using Swagger
---

The API is secured with JWT Bearer Token Authentication. 

While running the application, open a web browser and navigate to
http://localhost:<your_port>/swagger example `http://localhost:8080/swagger`.
For more information on swagger https://github.com/federecio/dropwizard-swagger, https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X

The end-points which require Authorization Header require Bearer prefix. For example,

`Authorization: Bearer accessToken`
