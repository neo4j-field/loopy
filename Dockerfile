FROM eclipse-temurin:21-jre-jammy

RUN groupadd --system loopy && useradd --system --gid loopy --create-home loopy

WORKDIR /app

COPY target/loopy-*.jar /app/loopy.jar
COPY target/classes/config.properties /app/config.properties
COPY target/classes/example-workload.yaml /app/example-workload.yaml

RUN chown -R loopy:loopy /app
USER loopy

ENTRYPOINT ["java", "-jar", "/app/loopy.jar"]
