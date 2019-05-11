package com.example

import com.example.modules.MyRepsoitory
import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.grpc.VertxServer
import io.vertx.grpc.VertxServerBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class GrpcAPIVerticle constructor(private val vertxServerBuilder: VertxServerBuilder, private val dummyRepository: MyRepsoitory) : AbstractVerticle() {

    lateinit var rpcServer: VertxServer
    val log: Logger = LoggerFactory.getLogger(GrpcAPIVerticle::class.java)

    override fun start(startFuture: Future<Void>) {
        rpcServer = vertxServerBuilder.build()
        rpcServer.start {
            if (it.failed()) {
                startFuture.fail(it.cause())
            }

            log.info("gRPC server listening on ${rpcServer.port}")

            startFuture.complete()
        }
    }

    override fun stop(stopFuture: Future<Void>) {
        rpcServer.shutdown(stopFuture)
    }
}