FROM openjdk:8u102-jre

WORKDIR /home

COPY target/k8s-microservice-3-1.0.jar .

COPY docker-entrypoint.sh docker-entrypoint.sh

RUN chmod +x /home/docker-entrypoint.sh

EXPOSE 9999

ENTRYPOINT ["/home/docker-entrypoint.sh"]


CMD ["k8s-microservice-3-1.0.jar"]