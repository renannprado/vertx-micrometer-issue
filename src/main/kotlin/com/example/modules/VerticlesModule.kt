package com.example.modules

import com.example.GrpcAPIVerticle
import com.example.MainHttpServer
import io.vertx.grpc.VertxServerBuilder

object VerticlesModule {
    fun mainHttpServerProvider(dummyRepository: MyRepsoitory): MainHttpServer {
        return MainHttpServer(dummyRepository)
    }

    fun grpcServerVerticle(serverBuilder: VertxServerBuilder, dummyRepository: MyRepsoitory): GrpcAPIVerticle {
        return GrpcAPIVerticle(serverBuilder, dummyRepository)
    }
}