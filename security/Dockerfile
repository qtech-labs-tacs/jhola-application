FROM openjdk:8u102-jre

WORKDIR /home

COPY target/security-1.0.jar .

COPY docker-entrypoint.sh docker-entrypoint.sh

RUN chmod +x /home/docker-entrypoint.sh

EXPOSE 8084

ENTRYPOINT ["/home/docker-entrypoint.sh"]

CMD ["security-1.0.jar"]


