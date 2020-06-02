FROM clojure

WORKDIR /
COPY . .

ADD .env.example .env

RUN lein deps

EXPOSE 3000
RUN bash export_env.sh

CMD java -jar app/patients.jar