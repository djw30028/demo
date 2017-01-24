https://github.com/osowski/ibm-containers-codemotion

Good doc for IBM container

===============================================
== Lab 1: Introduction to IBM Containers
Task 1: Verify your environment
 1. docker version
 2. docker run hello-world
 3. Configure the Cloud Foundry CLI to work with the nearest IBM Bluemix region
   cf api https://api.eu-gb.bluemix.net  --> use US one, list below
   cf api https://api.ng.bluemix.net
 4. Log in to Bluemix through the Cloud Foundry CLI
   cf login
    API endpoint:   https://api.ng.bluemix.net (API version: 2.54.0)
    User:           wei.wang2@ibm.com
    Org:            myclearflow
    Space:          dev
 5. Log in to the IBM Container service on Bluemix
   cf ic login

Task 2: Deploy your first container on Bluemix
  Create ibmliberty container, after login bluemix.net

Task 3: Deploy a container through the command line
  1. list images
    cf ic images
  2. create a container
   cf ic run --name container-lab-1 -p 9080 -m 64 registry.ng.bluemix.net/ibmliberty
  3. list containers
    cf ic ps -a
  4. assign a public IP to the container
    4.1 check first
     cf ic ps -a
    4.2 release IP
    cf ic ip list --> 169.46.20.185
    cf ic ip release 169.46.20.185
    4.3 bind IP
    cf ic ip bind 169.46.20.185 container-lab-1

    Note: above is not working, use UI https://console.ng.bluemix.net/containers/container/
    to bind IP

 ** Addition: Cleanup cf ic rm -f [CONTAINER_NAME]

 =============================================
 == Lab 2: Docker Web Apps, running on IBM Containers
 == https://github.com/osowski/ibm-containers-codemotion/blob/master/2-docker-web-apps.md
 Task 1: Pull your public images
   1. Pull the MongoDB image from DockerHub
      $ docker pull mongo
   2. Pull the Let's Chat image from DockerHub
      $ docker pull sdelements/lets-chat
   3. View docker installed images
      $ docker images
   4. verify these images and running the applications locally.
      Start a Mongo instance:
        $ docker run -d --name lc-mongo mongo
      Start a Let's Chat instance:
        $ docker run -d --name lets-chat --link lc-mongo:mongo -p 8080:8080 sdelements/lets-chat
   5. check processes
       $ docker ps
   6. test:
       http://localhost:8080/
   7. Stop the containers:
      $ docker stop lets-chat lc-mongo
   8. Delete the containers
      $ docker rm lets-chat lc-mongo

Task 2: Push your images to Bluemix
   https://api.ng.bluemix.net
   1. Log in to Bluemix
       $ cf login
   2. Log into the IBM Containers service
       $ cf ic login
       $ cf ic ps
       $ cf ic images
   3. Get namespace
       $ cf ic namespace get
       myclearflowns
   4. Tag Mongo image in a Bluemix-compatible format:
      $ docker tag  mongo registry.ng.bluemix.net/myclearflowns/mongo
      $ docker images
   5. Tag Chat image
      $ docker tag sdelements/lets-chat registry.ng.bluemix.net/myclearflowns/lets-chat

      $ docker images

   6. Push your Mongo image to your Bluemix registry
      $ docker push registry.ng.bluemix.net/myclearflowns/mongo
      $ docker push registry.ng.bluemix.net/myclearflowns/lets-chat

Task 3: Verify security vulnerabilities
    Bluemix Dashboard and click on CATALOG. BM Containers provides Vulnerability Advisor, a pre-integrated security scanning tool

Task 4: Run your web app
   1. list images
     $ cf ic images
     $ cf ic images ps
   2. run Mongo container
     $ cf ic ps
     $ cf ic run --name lc-mongo -p 27017 -m 512 registry.ng.bluemix.net/myclearflowns/mongo
   3. run Let's Chat container
     $ cf ic run --name lets-chat --link lc-mongo:mongo -p 8080 -m 256 registry.ng.bluemix.net/myclearflowns/lets-chat
   4. Bind public IP
      login bluemix.net and then Containers
      Note: free account only provides two free public IP, so need to release IP from other containers

   5. Test
      http://169.46.20.253:8080

 ===============================================
 == Lab 3. Docker Web Apps with IBM Bluemix services
 == https://github.com/osowski/ibm-containers-codemotion
 == bind Bluemix Services to Docker containers
 Task 1: Create a bridge application

   1. Bluemix Dashboard and click on CREATE APP. select Liberty for Java
   2. name: myclearflow-bridge-app
   3. test: click View App -> https://myclearflow-bridge-app.mybluemix.net/
   4. bind a MongoDB service
     After click Overview, Connections section, click Connect New,
     then in Data & Analytics section, click Compose for MongoDB
     --> Create -> Prestage
     Note: Prestage has error
   5. rename to: lets-chat-bridge

 Task 2: Deploy application from existing repository
   IBM Bluemix Delivery Pipeline and the one-click Deploy to Bluemix
   1. access https://github.com/osowski/lets-chat-bluemix-simple
   2. click Deploy to Bluemix
      create account then get email
      password: Bluemix_123
      Project URL: https://hub.jazz.net/project/weiwang2/lets-chat-bluemix-simple-weiwang2-1026
      Git Repository URL: https://hub.jazz.net/git/weiwang2/lets-chat-bluemix-simple-weiwang2-1026

Somehow it is not working

             registry.ng.bluemix.net/myclearflowns/mysql:latest
             
