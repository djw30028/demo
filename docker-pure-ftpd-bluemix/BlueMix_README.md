## Step 1: login bluemix   
$ cf login   
$ cf ic login   

## Step 2: pull image from docker hub to local docker: docker images   
docker pull stilliard/pure-ftpd:hardened   

## Step 3: push image to bluemix register    
$ docker tag stilliard/pure-ftpd:hardened registry.ng.bluemix.net/myclearflowns/pure-ftpd   
$ docker push registry.ng.bluemix.net/myclearflowns/pure-ftpd   
$ cf ic images -> egistry.ng.bluemix.net/myclearflowns/pure-ftpd   

## Step 4: run in bluemix   
$ cf ic ps -->  not pur-ftpd   

$ cf ic run --name stilliard-pure-ftpd -p 21:21 -p 30000-30009:30000-30009 -e "PUBLICHOST=169.46.22.124"     registry.ng.bluemix.net/myclearflowns/pure-ftpd    

Note: 21 port is not opened for bluemix   

## Step 5: check running status
https://console.ng.bluemix.net/dashboard/apps   
Binding IP from web gui -> 169.46.22.129   

## Step 6: access bluemix and create ftp user: : michael / password
$ cf ic exec -it stilliard-pure-ftpd bash
root@instance-001c9b14:/# pure-pw useradd michael -f /etc/pure-ftpd/passwd/pureftpd.passwd -m -u ftpuser -d /home/ftpusers/michael

## Step 7: test with command line
$ ftp 169.46.22.129 21
login with: michael/password
ftp>put my_readme.txt
ftp>ls
the file my_readme.txt is in remote

Check the file is put into root@instance-001c9b14:/home/ftpusers/michael#

## Step 8: test with FileZilla   
Not able to connect with FileZilla, get following error:       
ENETUNREACH - Network unreachable"    


## Observation:    
==================================================     
== Following gives error, guess registry.ng.bluemix.net/myclearflowns requires only one path after   
docker tag stilliard/pure-ftpd:hardened registry.ng.bluemix.net/myclearflowns/stilliard/pure-ftpd   
docker push registry.ng.bluemix.net/myclearflowns/stilliard/pure-ftpd   
