FROM clojure

WORKDIR /app
COPY . /app

ADD .env.example /app/.env

RUN lein deps
RUN lein uberjar


EXPOSE 3000

CMD java -jar target/patients.jar