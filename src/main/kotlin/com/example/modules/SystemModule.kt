package com.example.modules

import dagger.Module
import dagger.Provides
import io.vertx.config.ConfigRetriever
import io.vertx.config.ConfigRetrieverOptions
import io.vertx.config.ConfigStoreOptions
import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.config.getConfigAwait
import io.vertx.kotlin.core.json.jsonObjectOf
import kotlinx.coroutines.runBlocking
import javax.inject.Singleton

@Module
object SystemModule {
    @Provides
    @Singleton
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