FROM clojure

COPY . /app
WORKDIR /app

RUN lein deps

EXPOSE 3000

CMD java -jar app/patients.jar