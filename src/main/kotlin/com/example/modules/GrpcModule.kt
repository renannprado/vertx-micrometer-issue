package com.example.modules

import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import io.vertx.grpc.VertxServerBuilder

object GrpcModule {
    fun provideServerBuilder(vertx: Vertx, config: JsonObject): VertxServerBuilder {
        val grpcConfig = config.getJsonObject("grpc")

        return VertxServerBuilder.forAddress(vertx, grpcConfig.getString("host"), grpcConfig.getInteger("port"))
    }
}