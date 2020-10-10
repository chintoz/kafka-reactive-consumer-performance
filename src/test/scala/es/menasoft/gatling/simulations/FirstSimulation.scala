package es.menasoft.gatling.simulations

import java.lang.System.currentTimeMillis

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder
import io.gatling.http.request.builder.HttpRequestBuilder.toActionBuilder
import com.github.mnogu.gatling.kafka.Predef._
import org.apache.kafka.clients.producer.ProducerConfig

class FirstSimulation extends Simulation {

  before {
    println("[JJML] Simulation starts")
  }

  after {
    println("[JJML] Simulation ends")
  }

  val kafkaConf = kafka
    .topic("prueba")
    .properties(
      Map(
        ProducerConfig.ACKS_CONFIG -> "1",
        // list of Kafka broker hostname and port pairs
        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG -> "localhost:9092",
        // in most cases, StringSerializer or ByteArraySerializer
        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG ->
          "org.apache.kafka.common.serialization.StringSerializer",
        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG ->
          "org.apache.kafka.common.serialization.StringSerializer"))

  /*
  val httpProtocolBuilder: HttpProtocolBuilder = http
    .baseUrl("http://localhost:8080")
   */

  /*
  val scenarioBuilder: ScenarioBuilder = scenario("First Scenario")
    .exec(
      http("Count Request")
        .get("/messages")
    )
   */

  val scenarioBuilder: ScenarioBuilder = scenario("First Scenario Test")
    .exec(
      kafka("Count Messages")
        // message to send
        .send[String]("Example Message " + currentTimeMillis()))

  setUp(scenarioBuilder.inject(atOnceUsers(1000000))).protocols(kafkaConf)
}