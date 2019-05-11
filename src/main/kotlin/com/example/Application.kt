package com.example

import com.example.modules.*
import io.micrometer.statsd.StatsdMeterRegistry
import io.vertx.core.Vertx
import io.vertx.kotlin.core.closeAwait
import io.vertx.kotlin.core.deployVerticleAwait

suspend fun main() {

    var vertx: Vertx? = null
    var meterRegistry: StatsdMeterRegistry? = null

    try {
        val datadogConfig = MetricsModule.datadogConfig()
        meterRegistry = MetricsModule.metricsRegistry(datadogConfig)
        val vertxOptions = VertxModule.vertxOptions(meterRegistry)
        vertx = VertxModule.provideVertx(vertxOptions)
        val config = SystemModule.provideConfiguration(vertx)
        val pgOptions = DatabaseModule.providePgPoolOptions(config)
        val pgConnection = DatabaseModule.providePgConnectionPool(vertx, pgOptions)
        val dummyRepository = DatabaseModule.provideMyRepsoitory(pgConnection)
        val grpcServerBuilder = GrpcModule.provideServerBuilder(vertx, config)
        val grpcAPIVerticle = VerticlesModule.grpcServerVerticle(grpcServerBuilder, dummyRepository)
        vertx.deployVerticleAwait(grpcAPIVerticle)
    } catch (e: Exception) {
        e.printStackTrace()
        meterRegistry!!.close()
        vertx!!.closeAwait()
    }
}