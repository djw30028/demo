https://github.com/stilliard/docker-pure-ftpd

1. pull down to local image
  $ docker pull stilliard/pure-ftpd:hardened
  $ docker images
    stilliard/pure-ftpd

2. Starting
$ docker run -d --name ftpd_server -p 21:21 -p 30000-30009:30000-30009 -e "PUBLICHOST=localhost" stilliard/pure-ftpd:hardened

3. Access docker image
$ docker exec -it ftpd_server /bin/bash

4. Create an ftp user: michael !Qazxsw2
 https://download.pureftpd.org/pure-ftpd/doc/README.Virtual-Users

root@086e425649fa:/# pure-pw useradd michael -f /etc/pure-ftpd/passwd/pureftpd.passwd -m -u ftpuser -d /home/ftpusers/michael

5. Test
$ ftp -p localhost 21
michael/<password>

file location: /home/ftpusers/michael  under docker container

================================================
== add volume: docker run -v /Users/<path>:/<container path> ...
==

Approach 1: binding with specified Source with given host location

1. Specify it when running the container:
$ docker run -d --name my-ftpd_server-1 -p 21:21 -p 30000-30009:30000-30009 -v /Users/michaelwang/ftp:/home/ftpusers  -e "PUBLICHOST=localhost" stilliard/pure-ftpd:hardened

2. Verify the volume
$ docker inspect my-ftpd_server-1

"Mounts": [
            {
              "Source": "/Users/michaelwang/ftp",
              "Destination": "/home/ftpusers",

3. Access docker image
$ docker exec -it my-ftpd_server-1 /bin/bash
root@086e425649fa:/# pure-pw useradd michael -f /etc/pure-ftpd/passwd/pureftpd.passwd -m -u ftpuser -d /home/ftpusers/michael

michael <password>

4. Test it
$ ftp -p localhost 21

5. Misc
$ docker volume ls -> list volume
$ docker volume rm <name> -> delete volume

Status: it is working when ftp put file and pick up from share directory
Refer demo/spring-boot-integration/file-read-directory-bluemix/Docker-README.md

------------------------------------------------------
Approach 2: binging with volume

1. create a named volume
$ docker volume create --name ftp-data-root
$ docker volume create --name my-db-volume
$ docker run -d --name my-ftpd_server-2 -p 21:21 -p 30000-30009:30000-30009 -v ftp-data-root:/home/ftpusers -v my-db-volume:/etc/pure-ftpd/passwd -e "PUBLICHOST=localhost" stilliard/pure-ftpd:hardened
$ docker inspect my-ftpd_server-2
    "Source": "/var/lib/docker/volumes/ftp-data-root/_data",
    "Destination": "/home/ftpusers",

$ docker exec -it my-ftpd_server-2 /bin/bash
root@39b911156fa3:/# pure-pw useradd michael -f /etc/pure-ftpd/passwd/pureftpd.passwd -m -u ftpuser -d /home/ftpusers/michael
$ ftp -p localhost 21
  put <filename>
$ docker logs my-ftpd_server-2
