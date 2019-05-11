package com.example.modules

import dagger.Module
import dagger.Provides
import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import io.vertx.grpc.VertxServerBuilder
import javax.inject.Singleton

@Module
object GrpcModule {
    @Provides
    @Singleton
    fun provideServerBuilder(vertx: Vertx, config: JsonObject): VertxServerBuilder {
        val grpcConfig = config.getJsonObject("grpc")

        return VertxServerBuilder.forAddress(vertx, grpcConfig.getString("host"), grpcConfig.getInteger("port"))
    }
}