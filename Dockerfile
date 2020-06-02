FROM clojure

COPY . /app
ADD app.env.example /app/app.env
WORKDIR /app

RUN lein deps

EXPOSE 3000

CMD java -jar app/patients.jar