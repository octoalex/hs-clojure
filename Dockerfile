FROM clojure

WORKDIR /

COPY target/patients.jar patients.jar
EXPOSE 3000

CMD java -jar patients.jar