package com.example

import io.micrometer.statsd.StatsdConfig
import io.micrometer.statsd.StatsdFlavor
import io.vertx.core.json.JsonObject

class DataDogConfigImpl(private val ddConfig: JsonObject) : StatsdConfig {
    override fun prefix(): String {
        return ""
    }

    override fun get(key: String): String? {
        return ddConfig.getString(key.removePrefix("."))
    }

    override fun flavor(): StatsdFlavor {
        return StatsdFlavor.DATADOG
    }
}