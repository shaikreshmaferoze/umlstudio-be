# ---------- build stage ----------
FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
# ensure mvnw is executable if present
RUN if [ -f mvnw ]; then chmod +x mvnw; fi
# build jar (skip tests for faster deploy)
RUN mvn -B -DskipTests clean package

# ---------- runtime stage ----------
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=build /app/target/*.jar ./app.jar
ENV JAVA_TOOL_OPTIONS="-Xmx512m"
ENV PORT=8080
EXPOSE 8080
CMD ["sh", "-c", "java -Dserver.port=$PORT -jar /app/app.jar"]
