$ bluemix ic info  
$ bluemix ic images   
$ a8ctl -h   

API endpoint: https://api.ng.bluemix.net   

# Routes
$ bluemix cf routes
>space   host                                       domain           
dev     Liberty-for-Java-Starter-app-tutorial-ww   mybluemix.net   
dev     myspringboot                               mybluemix.net   
dev     CloudTrader-773019                         mybluemix.net   
dev     myclearflow-bridge-app                     mybluemix.net   
dev     lets-chat-bluemix-simple-weiwang2-1026     mybluemix.net   
dev     dev-michael-w-a8-registry                  mybluemix.net   
dev     dev-michael-w-a8-controller                mybluemix.net   

# Group
$ bluemix ic groups    
>Group Id                               Name                  Status            Created                         Updated   Port  Routes   
842844f0-bd09-43d6-b3c0-d44e658d2ae7   amalgam8_controller   CREATE_COMPLETE   2017-01-21 10:55:00 -0500 EST             8080      
95f6f930-ab79-4c7a-ad55-f4983f42464c   amalgam8_registry     CREATE_COMPLETE   2017-01-21 10:53:04 -0500 EST             8080      

# Env
$ bluemix cf domains -> ROUTES_DOMAIN mybluemix.net   
$ bluemix cf target  -> BLUEMIX_SPACE dev   
$ bluemix ic namespace-get -> BLUEMIX_REGISTRY_NAMESPACE myclearflowns   
$ bluemix ic info    -> BLUEMIX_REGISTRY_HOST registry.ng.bluemix.net      
>Date/Time                2017-01-24 15:26:05.25854616 -0500 EST   
Debug mode                  
CCS host/url             https://containers-api.ng.bluemix.net   
Registry host            registry.ng.bluemix.net   
Bluemix api host/url     https://api.ng.bluemix.net   
Bluemix Org              myclearflow(d19b2c42-7ec6-4938-9e8e-81733bac8427)   
Bluemix Space            dev(31d169c5-5493-4008-bd58-fe47298a1882)   
CCS CLI Version          1.0.0   
CCS API Version          3.0  Fri Jan 13 18:07:03 2017   
Namespace                myclearflowns   
Environment name         dal10-03   
Containers limit         Unlimited   
Containers usage         6   
Containers running       6   
CPU limit (cores)        Unlimited   
CPU usage (cores)        6   
Memory limit(MB)         2048   
Memory usage(MB)         1280   
Floating IPs limit       2   
Floating IPs allocated   2   
Public IPs bound         2   

>export ROUTES_DOMAIN=mybluemix.net   
export CONTROLLER_IMAGE=a8-controller:latest   
export REGISTRY_IMAGE=a8-registry:latest   
export BLUEMIX_SPACE=dev   
export BLUEMIX_REGISTRY_HOST=registry.ng.bluemix.net   
export BLUEMIX_REGISTRY_NAMESPACE=myclearflowns   
export DOCKERHUB_NAMESPACE=amalgam8   
export REGISTRY_HOSTNAME=$BLUEMIX_SPACE-a8-registry   
export CONTROLLER_HOSTNAME=$BLUEMIX_SPACE-a8-controller   
export REGISTRY_URL=http://$REGISTRY_HOSTNAME.$ROUTES_DOMAIN     -> http://dev-a8-registry.mybluemix.net   
export CONTROLLER_URL=http://$CONTROLLER_HOSTNAME.$ROUTES_DOMAIN -> http://dev-a8-controller.mybluemix.net   


# Verify
* Registry   
curl -s -o /dev/null -w "%{http_code}\n\n" $REGISTRY_URL/uptime   
curl -s -o /dev/null -w "%{http_code}\n\n" http://dev-a8-registry.mybluemix.net/uptime   


* Controller  
curl -s -o /dev/null -w "%{http_code}\n\n" $CONTROLLER_URL/health   
curl -s -o /dev/null -w "%{http_code}\n\n" http://dev-a8-controller.mybluemix.net/health   

# Deploying a microservice that registers automatically in Amalgam8
https://console.ng.bluemix.net/docs/containers/amalgam8/a8_deploy_svc.html

https://www.amalgam8.io/docs/demo.html   
RUN curl -sSL https://github.com/amalgam8/amalgam8/releases/download/v0.3.1/a8sidecar.sh | sh    
RUN wget -qO- https://github.com/amalgam8/amalgam8/releases/download/v0.3.1/a8sidecar.sh | sh    


