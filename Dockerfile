FROM openjdk:21-jdk-slim
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]

# Copie o script de espera para o contêiner
COPY wait-for-db.sh /usr/local/bin/wait-for-db.sh

# Defina permissões de execução para o script
RUN chmod +x /usr/local/bin/wait-for-db.sh

# Copie o JAR da sua aplicação
COPY CursoApi-0.0.1-SNAPSHOT.jar/target/CursoApi-0.0.1-SNAPSHOT.jar

# Comando de inicialização da aplicação
CMD ["./wait-for-db.sh", "java", "-jar", "/app.jar"]