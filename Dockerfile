FROM clojure

ENV DB_NAME=hs
ENV DB_TEST_NAME=hs_test
ENV DB_USER=hs
ENV DB_PASSWORD=elfenlied1,


WORKDIR /
COPY . .

ADD .env.example .env

RUN lein deps

EXPOSE 3000

CMD java -jar app/patients.jar