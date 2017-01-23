# PART I
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
Binding IP from web gui -> 169.46.19.242  

## Step 6: access bluemix and create ftp user: : michael / password
$ cf ic exec -it stilliard-pure-ftpd bash
root@instance-001c9b14:/# pure-pw useradd michael -f /etc/pure-ftpd/passwd/pureftpd.passwd -m -u ftpuser -d /home/ftpusers/michael

## Step 7: test with command line
$ ftp 169.46.19.242 21
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

---
# Part II: add volume   
$ cf ic volume create ftp_data_root  
$ cf ic volume list   

$ docker pull stilliard/pure-ftpd:hardened    
$ docker tag stilliard/pure-ftpd:hardened registry.ng.bluemix.net/myclearflowns/pure-ftpd     
$ docker push registry.ng.bluemix.net/myclearflowns/pure-ftpd   
$ cf ic images
  Got: registry.ng.bluemix.net/myclearflowns/pure-ftpd
$ cf ic run --name stilliard-pure-ftpd -p 21:21 -p 30000-30009:30000-30009 --volume ftp_data_root:/home/ftpusers -e "PUBLICHOST=169.46.22.124"  registry.ng.bluemix.net/myclearflowns/pure-ftpd  


$ cf ic inspect stilliard-pure-ftpd
  Got: "Mounts": "Destination": "/home/ftpusers", "Source": "/vol/31d169c5-5493-4008-bd58-fe47298a1882/ftp_data_root"

$ cf ic exec -it stilliard-pure-ftpd /bin/bash
  Got: root@instance-001d7d04:/#
  /# pure-pw useradd michael -f /etc/pure-ftpd/passwd/pureftpd.passwd -m -u ftpuser -d /home/ftpusers/michael


Broswer: in https://console.ng.bluemix.net/containers, bind public IP found: 169.46.17.167
$ ftp 169.46.17.217 21
When put file there, Got 553 Can't open that file: Permission denied

------------------------------------
Start with volume: delete all images and try again, part I is working as expect while this one is not working
$ cf ic volume list
$ cf ic run --name stilliard-pure-ftpd-w-volume -p 21:21 -p 30000-30009:30000-30009 --volume ftp_data_root:/home/ftpusers -e "PUBLICHOST=169.46.22.124" registry.ng.bluemix.net/myclearflowns/pure-ftpd
bind public IP: 169.46.19.245

$ cf ic exec -it stilliard-pure-ftpd-w-volume bash
root@instance-001e30e1:/# pure-pw useradd michael -f /etc/pure-ftpd/passwd/pureftpd.passwd -m -u ftpuser -d /home/ftpusers/michael
$ ftp 169.46.19.245 21
Not working: same issue: 553 Can't open that file: Permission denied

-------------------------------------
inside container:
$ cf ic exec -it stilliard-pure-ftpd-w-volume bash
2.1 Create "michael" group and user    
groupadd --gid 1010 michael
useradd --uid 1010 --gid 1010 -m --shell /bin/bash michael

2.2 Add the user to group "root"
adduser michael root
chmod 775 /home/ftpusers/michael

2.3 Create pgsql directory under bind-mount volume
su -c "mkdir -p /home/ftpusers/share" michael
ln -sf /home/ftpusers/share /home/ftpusers/michael

http://container-solutions.com/understanding-volumes-docker/
http://stackoverflow.com/questions/31290202/can-i-change-owner-of-directory-that-is-mounted-on-volume-in-ibm-containers
1. Mount the Volume to `/mnt/pgdata` inside the container

cf ic run --volume pgdata:/mnt/pgdata -p 22 registry.ng.bluemix.net/ruimo/pgsql944-cli

2. Inside the container

2.1 Create "postgres" group and user    
groupadd --gid 1010 postgres
useradd --uid 1010 --gid 1010 -m --shell /bin/bash postgres

2.2 Add the user to group "root"
adduser postgres root
chmod 775 /mnt/pgdata

2.3 Create pgsql directory under bind-mount volume
su -c "mkdir -p /mnt/pgdata/pgsql" postgres
ln -sf /mnt/pgdata/pgsql /var/pgsql

2.2 Remove the user from group "root"
deluser postgres root
chmod 755 /mnt/pgdata
