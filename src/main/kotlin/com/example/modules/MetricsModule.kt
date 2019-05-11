package com.example.modules

import com.example.DataDogConfigImpl
import dagger.Module
import dagger.Provides
import io.micrometer.core.instrument.Clock
import io.micrometer.statsd.StatsdMeterRegistry
import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import javax.inject.Singleton

@Module
object MetricsModule {
    @Provides
    @Singleton
    fun metricsRegistry(ddConfig: DataDogConfigImpl): StatsdMeterRegistry {
        return StatsdMeterRegistry(ddConfig, Clock.SYSTEM)
    }

    @Provides
    @Singleton
    fun datadogConfig() : DataDogConfigImpl {
        // TODO: at the moment is not possible to load the configuration before creating a vertx
        // and since we depend on this one to create a
        Vertx.vertx()
        val ddConfig = JsonObject()
        ddConfig.put("host", System.getenv("DATADOG_HOST"))

        return DataDogConfigImpl(ddConfig)
    }
}

