FROM clojure

WORKDIR /app
COPY . /app

ADD .env.example /app/.env

RUN lein deps

EXPOSE 3000

CMD java -jar app/patients.jar