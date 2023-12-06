#
# Package stage
#
# FROM eclipse-temurin:17-jre-jammy
# ARG JAR_FILE=/home/priyankaakhade2023/cloud_run_job_repo/target/*.jar
# COPY --from=build $JAR_FILE /app/runner.jar
# #EXPOSE 8080
# ENTRYPOINT java -jar /app/runner.jar

FROM eclipse-temurin:17-jdk-alpine
WORKDIR /usr/src/app
COPY samplefile.json /usr/src/app/samplefile.json
# WORKDIR /tmp
# VOLUME /tmp
# ARG CloudRunJobPOC-0.0.1-SNAPSHOT.jar
# COPY samplefile.json $samplefile.json
COPY cloud-run-poc-app-0.0.1-SNAPSHOT.jar /usr/src/app/cloud-run-poc-app-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/usr/src/app/cloud-run-poc-app-0.0.1-SNAPSHOT.jar"]