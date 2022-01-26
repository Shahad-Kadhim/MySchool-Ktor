FROM openjdk:11 as builder

COPY . .

RUN ./gradlew jar

FROM openjdk:11

COPY --from=builder /build/libs/my-school.jar ./my-school.jar

CMD ["java","-jar","my-school.jar"]