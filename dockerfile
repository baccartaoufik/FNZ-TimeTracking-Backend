# Étape 1: Utiliser une image OpenJDK pour JDK 22 pour compiler le projet
FROM openjdk:22-jdk-slim AS builder

# Définir le répertoire de travail dans le conteneur
WORKDIR /app

# Copier le fichier pom.xml pour télécharger les dépendances
COPY pom.xml .

# Copier le répertoire src pour ajouter le code source
COPY src ./src

# Rendre le Maven Wrapper exécutable
COPY mvnw .
COPY .mvn ./.mvn
RUN chmod +x mvnw

# Compiler l'application en utilisant Maven Wrapper
RUN ./mvnw clean package -DskipTests

# Étape 2: Utiliser une image OpenJDK pour JDK 22 pour exécuter l'application compilée
FROM openjdk:22-jdk-slim

# Définir le répertoire de travail dans le conteneur
WORKDIR /app

# Copier le fichier JAR généré à partir de l'étape précédente
COPY --from=builder /app/target/*.jar app.jar

# Exposer le port utilisé par votre application (dans le cas de Spring Boot, souvent 8080 par défaut)
EXPOSE 8080

# Commande pour exécuter l'application Spring Boot lorsque le conteneur démarre
ENTRYPOINT ["java", "-jar", "app.jar"]

