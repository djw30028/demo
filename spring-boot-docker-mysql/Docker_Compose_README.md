# Create docker-compose.yml 
## Step 1. clean up
if any, delete images for spring-boot-docker-mysql, mysql-red, mysql-blue

## Step 2, build images
```
mvn clean package docker:build
```

## Step 3. deploy with docker-compose
$ cd spring-boot-docker-mysql/src/main/docker
$ docker-compose up

## Bluemix: https://console.ng.bluemix.net/docs/containers/container_cli_login.html#container_cli_login__option2_li

$ cf ic login
Got following 
export DOCKER_HOST=tcp://containers-api.ng.bluemix.net:8443
export DOCKER_CERT_PATH=/Users/michaelwang/.ice/certs/containers-api.ng.bluemix.net/31d169c5-5493-4008-bd58-fe47298a1882
export DOCKER_TLS_VERIFY=1
  	
$ docker-compose up
docker-compose -f bluemix-docker-compose.yml up

Status: not working 


