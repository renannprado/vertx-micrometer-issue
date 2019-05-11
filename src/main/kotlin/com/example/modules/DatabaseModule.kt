package com.example.modules

import com.example.DummyRepository
import dagger.Module
import dagger.Provides
import io.reactiverse.kotlin.pgclient.PgClient
import io.reactiverse.kotlin.pgclient.pgPoolOptionsOf
import io.reactiverse.pgclient.PgConnection
import io.reactiverse.pgclient.PgPoolOptions
import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import io.vertx.kotlin.coroutines.dispatcher
import kotlinx.coroutines.*
import javax.inject.Singleton

@Module
object DatabaseModule {
    @Provides
    @Singleton
    fun providePgPoolOptions(config: JsonObject): PgPoolOptions {
        val databaseConfig = config.getJsonObject("sql")

        return pgPoolOptionsOf(
                port = databaseConfig.getInteger("port"),
                host = databaseConfig.getString("host"),
                database = databaseConfig.getString("database"),
                user = databaseConfig.getString("user"),
                password = databaseConfig.getString("password"),
                connectTimeout = 10,
                reconnectInterval = 10,
                reconnectAttempts = 10,
                maxSize = 10)
    }

    @Provides
    @Singleton
    fun providePgConnection(vertx: Vertx, pgPoolOptions: PgPoolOptions): PgConnection {
        return runBlocking(vertx.dispatcher()) {
            PgClient.connectAwait(vertx, pgPoolOptions)
        }
    }

    @Provides
    @Singleton
    fun provideRepository(pgConnection: PgConnection) : DummyRepository {
        return DummyRepository(pgConnection)
    }
}