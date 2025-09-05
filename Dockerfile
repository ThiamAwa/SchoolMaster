# Étape 1 : Utiliser une image Maven pour compiler (optionnel si déjà compilé)
# FROM maven:3.9.2-eclipse-temurin-17 AS build
# WORKDIR /app
# COPY pom.xml .
# COPY src ./src
# RUN mvn clean package -DskipTests

# Étape 2 : Utiliser une image Java pour exécuter le JAR
FROM eclipse-temurin:17-jdk-alpine

# Répertoire de l'application dans le conteneur
WORKDIR /app

# Copier le JAR compilé depuis le projet local
COPY target/SchoolMaster-0.0.1-SNAPSHOT.jar app.jar

# Exposer le port que Spring Boot utilise (par défaut 8080)
EXPOSE 8080

# Commande pour exécuter l'application
ENTRYPOINT ["java","-jar","app.jar"]
