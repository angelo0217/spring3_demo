FROM gradle:7.4.0-jdk17 as cache
ENV DEBIAN_FRONTEND=noninteractive
ENV SERVICE_NAME="demo"

RUN mkdir -p /home/gradle/cache_home
ENV GRADLE_USER_HOME /home/gradle/cache_home
#COPY deploy/src /workspace/${SERVICE_NAME}/src

WORKDIR /workspace/${SERVICE_NAME}/

COPY ../build.gradle /workspace/${SERVICE_NAME}/

RUN gradle dependencies
#RUN gradle clean build -i --stacktrace

FROM gradle:7.4.0-jdk17 as build

ENV DEBIAN_FRONTEND=noninteractive
ENV SERVICE_NAME="world-cup"

COPY --from=cache /home/gradle/cache_home /home/gradle/.gradle

COPY ../ /workspace/${SERVICE_NAME}/
WORKDIR /workspace/${SERVICE_NAME}

RUN gradle bootJar -i --stacktrace -x test --no-daemon

FROM azul/zulu-openjdk-debian:17.0.2
USER root

ENV DEBIAN_FRONTEND=noninteractive
ENV SERVICE_NAME="demo"

RUN apt-get update && \
    apt-get install --yes --no-install-recommends tzdata && \
    ln -fs /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && \
    dpkg-reconfigure -f noninteractive tzdata

WORKDIR /workspace/${SERVICE_NAME}
COPY --from=build /workspace/${SERVICE_NAME}/build/libs/*.jar ./app.jar
#ENTRYPOINT exec java $JAVA_OPTS -jar app.jar
CMD ["java", "-jar", "app.jar"]