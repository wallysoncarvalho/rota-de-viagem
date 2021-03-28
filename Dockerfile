FROM adoptopenjdk/maven-openjdk11 as builder
WORKDIR /application
COPY cli ./cli
COPY core ./core
COPY rest ./rest
COPY persistence ./persistence
COPY pom.xml ./
RUN mvn clean package -DskipTests=true
RUN java -Djarmode=layertools -jar rest/target/rest-1.0.jar extract

FROM adoptopenjdk:11-jre-hotspot
WORKDIR /application

COPY --from=builder application/cli/target/cli-1.0-shaded.jar ./cli.jar
COPY graph_files ../graph_files

COPY --from=builder application/dependencies/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/application/ ./

ENV SERVER_PORT=8090
ENV GRAPH_PATH=/graph_files/graph.csv

EXPOSE ${SERVER_PORT}

ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher", "-Dgraph.path=${GRAPH_PATH}"]