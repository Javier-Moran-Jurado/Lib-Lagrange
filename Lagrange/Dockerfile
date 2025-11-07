FROM eclipse-temurin:21-jdk
VOLUME /tmp
EXPOSE 8080

# Copiar el JAR con el nombre correcto
ARG JAR_FILE=target/Lagrange-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

# Copiar tu librería nativa
COPY src/main/resources/native/liblagrange.so /lib64/

# Configurar path para librerías nativas
ENV LD_LIBRARY_PATH=/usr/local/lib/jni:/lib64:$LD_LIBRARY_PATH

ENTRYPOINT ["java", "-jar", "/app.jar"]