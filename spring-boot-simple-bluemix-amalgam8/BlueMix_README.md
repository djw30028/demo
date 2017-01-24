http://vmtyler.com/Spring-Boot-On-Bluemix/

Install Liberty profile server.
    ```bash
   $ mvn clean package 

Run locally  
   java -jar target/spring-boot-simple-0.0.1-SNAPSHOT.jar

Test   
   http://localhost:8080/
 
# Section 1: Deploy to bluemix apps

Deploy to Cloud Foundry IBM WebSphere (Library). use the ‘-p’ flag to specify the JAR we just created.
    $ cf push myspringboot -p target/spring-boot-simple-0.0.1-SNAPSHOT.jar

Deploy to community java buildpack   
    $ cf push tbspring -p target/spring-boot-simple-0.0.1-SNAPSHOT.jar -b https://github.com/cloudfoundry/java-buildpack.git

Test 
login:    
* http://www.bluemix.net     
* https://myspringboot.mybluemix.net/

===============================

# Section 2: Deploy to docker
https://dzone.com/articles/deploying-a-spring-boot-application-to-bluemix-in  
http://heidloff.net/article/Deploying-Spring-Boot-Applications-to-Bluemix-as-Docker-Containers  
https://github.com/osowski/ibm-containers-codemotion



## Convert to docker locally
1. add Dockerfile, pom.xml plugin
2. start docker
3. check version docker --version, docker ps -a,
4. stop/delete if any: 
  $ docker stop ab638e8ba614, docker rm ab638e8ba614
4. Build docker image: 
  $ mvn clean package docker:build
5. Deploy image locally: 
  $ docker run -p 8080:8080 -t michael/spring-boot-simple
6. Test: http://localhost:8080/  
   http://localhost:8080/v2/api-docs?group=business-api   
   http://localhost:8080/swagger-ui.html

## Push local spring to IBM bluemix docker
1. $ cf login
2. $ cf ic login
3. $ docker tag michael/spring-boot-simple registry.ng.bluemix.net/myclearflowns/spring-boot-simple
4. $ docker push registry.ng.bluemix.net/myclearflowns/spring-boot-simple
5. check: $ cf ic ps
6. run:
   $ cf ic run --name spring-boot-simple -p 8080 registry.ng.bluemix.net/myclearflowns/spring-boot-simple
7. Verify: login bluemix.net and go to Container section, verify spring-boot-simple in there
8. Bind public IP 
9. Test: 169.46.22.192:8080

Good job done