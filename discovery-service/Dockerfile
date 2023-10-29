FROM openjdk:8u102-jre

WORKDIR /home

COPY target/discovery-service-1.0.jar .

COPY docker-entrypoint.sh docker-entrypoint.sh

RUN chmod +x /home/docker-entrypoint.sh

EXPOSE 8085

ENTRYPOINT ["/home/docker-entrypoint.sh"]


CMD ["discovery-service-1.0.jar"]