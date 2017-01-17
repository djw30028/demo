FROM java:8
VOLUME /tmp
ADD filemonitor.jar readfile.jar
RUN bash -c 'touch /readfile.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/readfile.jar"]