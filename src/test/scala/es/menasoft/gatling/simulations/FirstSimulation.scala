package es.menasoft.gatling.simulations

import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder
import io.gatling.http.request.builder.HttpRequestBuilder.toActionBuilder


class FirstSimulation extends Simulation {

  before {
    println("[JJML] Simulation starts")
  }

  after {
    println("[JJML] Simulation ends")
  }

  val httpProtocolBuilder: HttpProtocolBuilder = http
    .baseUrl("http://localhost:8080")

  val scenarioBuilder: ScenarioBuilder = scenario("First Scenario")
    .exec(
      http("Count Request")
        .get("/messages")
    )

  setUp(scenarioBuilder.inject(atOnceUsers(1000))).protocols(httpProtocolBuilder)
}