package com.example.modules

import io.micrometer.statsd.StatsdMeterRegistry
import io.vertx.core.Vertx
import io.vertx.core.VertxOptions
import io.vertx.micrometer.MicrometerMetricsOptions

object VertxModule {
    fun provideVertx(vertxOptions: VertxOptions): Vertx {
        return Vertx.vertx(vertxOptions)
    }

    fun vertxOptions(metricsRegistry: StatsdMeterRegistry): VertxOptions {
        return VertxOptions()
                .setMetricsOptions(
                        MicrometerMetricsOptions()
                                .setEnabled(true)
                                .setJvmMetricsEnabled(true)
                                .setMicrometerRegistry(metricsRegistry)
                )
    }
}


