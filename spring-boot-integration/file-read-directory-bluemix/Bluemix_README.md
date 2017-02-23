## Step 2, build images
```
$ mvn clean package docker:build
```
$ mvn clean package docker:build

## Step 3, login in bluemix
$ cf login
$ cf ic login

## Step 4: build docker image
$ mvn clean package docker:build

## Step 5: tag and push
$ docker tag blue/file-read-directory registry.ng.bluemix.net/myclearflowns/file-read-directory
$ docker push registry.ng.bluemix.net/myclearflowns/file-read-directory

## Step 6: 
$ cf ic run --name file-read-directory  registry.ng.bluemix.net/myclearflowns/file-read-directory

$ cf ic run --name file-read-directory --volume my_volume:/Users/michaelwang/project/integration  registry.ng.bluemix.net/myclearflowns/file-read-directory

$ cf ic run --name file-read-directory --volume my_volume:/mnt/nfs registry.ng.bluemix.net/myclearflowns/file-read-directory

Note: the volume should be the same as bluemix ftp volume

## Step 7:
$ cf ic ps
a59da88f-f8c registry.ng.bluemix.net/myclearflowns/file-read-directory:latest

## Step 8:
$ cf ic logs file-read-directory
$ cf ic logs a59da88f-f8c

## Step 9:
$ cf ic exec -it file-read-directory bash
$ cf ic exec -it a59da88f-f8c bash

Create directory: 
root@instance-001ccfda:/michaelwang/project/integration

## Step 10:
in local host:
$ cd /Users/michaelwang/project/integration 
$ cf ic cp file1.txt a59da88f-f8c:/mnt/nfs/mydata

## Step 11:   
Verify from log   
$ cf ic logs file-read-directory   

2017-01-17 00:18:24.302  INFO 1 --- [ask-scheduler-4] o.s.i.file.FileReadingMessageSource      : Created message: [GenericMessage [payload=/Users/michaelwang/project/integration/file1.txt, headers={id=87b34d4f-28a8-6e78-9173-3822f34d17b6, timestamp=1484612304302}]]
&file1.txt received. Content: hi hello
see you


DONE

---
## Approach 2: Volume
$ docker volume ls
$ mvn clean package docker:build
$ docker tag blue/file-read-directory registry.ng.bluemix.net/myclearflowns/file-read-directory
$ docker push registry.ng.bluemix.net/myclearflowns/file-read-directory
$ cf ic run -d --name file-read-directory-11  --volume ftp_data_root:/Users/michaelwang/project/integration registry.ng.bluemix.net/myclearflowns/file-read-directory
$ cf ic inspect file-read-directory-11
$ cf ic exec -it file-read-directory-11 bash
$ cf ic logs file-read-directory-11
$ cf ic cp file1.txt instance-001d970d:/Users/michaelwang/project/integration
                                        
Not working as expect



