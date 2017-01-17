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
michael/!Qazxsw2

file location: /home/ftpusers/michael  under docker container
