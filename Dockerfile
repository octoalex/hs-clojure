FROM clojure

WORKDIR /app
COPY . /app

ADD .env.example /app/.env

RUN lein deps
RUN lein uberjar
RUN . /app/export_env.sh

EXPOSE 3000

CMD java -jar target/patients.jar