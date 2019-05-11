package com.example

import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.http.HttpServer

class MainHttpServer(private val dummyRepository: DummyRepository) : AbstractVerticle() {

    lateinit var httpServer: HttpServer

    override fun start() {
        // Start the server

    }

    override fun start(startFuture: Future<Void>) {
        this.httpServer = vertx.createHttpServer()
                .requestHandler {
                    it.response().end("hello")
                }
                .listen(8080, "localhost") {
                    if (it.failed()) {
                        startFuture.fail(it.cause())
                    } else {
                        startFuture.complete()
                    }
                }
    }

    override fun stop(stopFuture: Future<Void>?) {
        httpServer.close(stopFuture)
    }
}

