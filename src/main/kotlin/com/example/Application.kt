package com.example

import com.example.modules.*
import dagger.Component
import io.micrometer.statsd.StatsdMeterRegistry
import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.core.closeAwait
import io.vertx.kotlin.core.deployVerticleAwait
import javax.inject.Inject
import javax.inject.Singleton

suspend fun main() {
    var applicationComponent: ApplicationComponent? = null
    try {
        applicationComponent = DaggerApplicationComponent.builder()
                .metricsModule(MetricsModule)
                .databaseModule(DatabaseModule)
                .systemModule(SystemModule)
                .verticlesModule(VerticlesModule)
                .vertxModule(VertxModule)
                .build()

        applicationComponent.application().start()
    } catch (e: Exception) {
        e.printStackTrace()

        applicationComponent?.statsdMeterRegistry()?.close()

        applicationComponent?.vertx()?.closeAwait()
    }
}

class Application @Inject constructor(private val vertx: Vertx, private val mainHttpServer: MainHttpServer) {
    suspend fun start() {
        vertx.deployVerticleAwait(mainHttpServer)
    }
}

@Singleton
@Component(
        modules = [
            VertxModule::class,
            SystemModule::class,
            GrpcModule::class,
            DatabaseModule::class,
            MetricsModule::class,
            VerticlesModule::class
        ]
)
interface ApplicationComponent {
    fun vertx(): Vertx
    fun statsdMeterRegistry(): StatsdMeterRegistry
    fun application(): Application
    fun config(): JsonObject
}
