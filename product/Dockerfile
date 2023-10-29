FROM openjdk:8u102-jre

WORKDIR /home

COPY target/product-1.0.jar .

COPY docker-entrypoint.sh docker-entrypoint.sh

RUN chmod +x /home/docker-entrypoint.sh

EXPOSE 8083

ENTRYPOINT ["/home/docker-entrypoint.sh"]

CMD ["product-1.0.jar"]


