FROM clojure

COPY . /
ADD .env.example /.env
WORKDIR /

RUN lein deps

EXPOSE 3000

CMD java -jar app/patients.jar