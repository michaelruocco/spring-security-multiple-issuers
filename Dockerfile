FROM adoptopenjdk/openjdk15:alpine-jre

ARG VERSION

ENV SERVER_PORT=80 \
    SPRING_PROFILES_ACTIVE=default \
    AUTH_ISSUER_1_URI=http://keycloak-1:8080/auth/realms/demo-realm-1 \
    AUTH_ISSUER_1_AUDIENCE=demo-realm-1-audience \
    AUTH_ISSUER_2_URI=http://keycloak-2:8080/auth/realms/demo-realm-2 \
    AUTH_ISSUER_2_AUDIENCE=demo-realm-2-audience

COPY build/libs/spring-security-multiple-issuers-${VERSION}.jar /opt/app.jar

CMD java \
  -Djava.security.egd=file:/dev/./urandom \
  -Dserver.port=${SERVER_PORT} \
  -Dspring.profiles.active=${SPRING_PROFILES_ACTIVE} \
  -Dauth.issuer.1.uri=${AUTH_ISSUER_1_URI} \
  -Dauth.issuer.1.audience=${AUTH_ISSUER_1_AUDIENCE} \
  -Dauth.issuer.2.uri=${AUTH_ISSUER_2_URI} \
  -Dauth.issuer.2.audience=${AUTH_ISSUER_2_AUDIENCE} \
  -jar /opt/app.jar