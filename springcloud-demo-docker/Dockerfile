FROM 99taxis/mini-java8

ADD target/*.jar idea-docker.jar

RUN bash -c 'touch /idea-docker.jar'

EXPOSE 8761

ENTRYPOINT ["java", "-jar", "idea-docker.jar"]
