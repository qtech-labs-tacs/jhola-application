FROM openjdk:8u102-jre

WORKDIR /home

COPY target/api-gateway-1.0.jar .

COPY docker-entrypoint.sh docker-entrypoint.sh

RUN chmod +x /home/docker-entrypoint.sh

EXPOSE 8086

ENTRYPOINT ["/home/docker-entrypoint.sh"]


CMD ["api-gateway-1.0.jar"]