FROM clojure

COPY . /app
ADD .env.example /app/.env
WORKDIR /app

RUN lein deps

EXPOSE 3000

CMD java -jar app/patients.jar