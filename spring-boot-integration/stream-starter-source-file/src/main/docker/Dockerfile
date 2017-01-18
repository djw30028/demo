FROM java:8
VOLUME /tmp
ADD fileeventprocess.jar fileevent.jar
RUN bash -c 'touch /fileevent.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/fileevent.jar"]