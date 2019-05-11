package com.example.modules

import io.reactiverse.kotlin.pgclient.pgPoolOptionsOf
import io.reactiverse.pgclient.PgPool
import io.reactiverse.pgclient.PgPoolOptions
import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject

object DatabaseModule {
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

    fun providePgConnectionPool(vertx: Vertx, pgPoolOptions: PgPoolOptions): PgPool {
        throw Exception("error while connecting to the database")
    }

    fun provideMyRepsoitory(pgPool: PgPool): MyRepsoitory {
        return MyRepsoitory(pgPool)
    }
}

class MyRepsoitory(private val pgPool: PgPool) {

}