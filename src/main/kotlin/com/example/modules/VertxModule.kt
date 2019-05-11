package com.example.modules

import dagger.Module
import dagger.Provides
import io.micrometer.statsd.StatsdMeterRegistry
import io.vertx.core.Vertx
import io.vertx.core.VertxOptions
import io.vertx.micrometer.MicrometerMetricsOptions
import javax.inject.Singleton

@Module
object VertxModule {
    @Provides
    @Singleton
    fun provideVertx(vertxOptions: VertxOptions): Vertx {
        return Vertx.vertx(vertxOptions)
    }

    @Provides
    @Singleton
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


