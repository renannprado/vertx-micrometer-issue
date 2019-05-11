package com.example.modules

import io.vertx.config.ConfigRetriever
import io.vertx.config.ConfigRetrieverOptions
import io.vertx.config.ConfigStoreOptions
import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.config.getConfigAwait
import io.vertx.kotlin.core.json.jsonObjectOf
import kotlinx.coroutines.runBlocking

object SystemModule {
    fun provideConfiguration(vertx: Vertx): JsonObject {
        val configStores = arrayListOf(
                ConfigStoreOptions()
                        .setType("file")
                        .setFormat("yaml")
                        .setConfig(jsonObjectOf(
                                "path" to "config.yaml"
                        ))
        )

        val options = ConfigRetrieverOptions()
                .apply {
                    configStores.forEach {
                        this.addStore(it)
                    }
                }

        val retriever = ConfigRetriever.create(vertx, options)

        return runBlocking {
            retriever.getConfigAwait()
        }
    }
}