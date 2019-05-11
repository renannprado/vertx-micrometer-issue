buildscript {
    extra.apply {
        set("kotlinVersion", "1.3.21")
        set("vertxVersion", "3.7.0")
        set("junitJupiterEngineVersion", "5.4.0")
    }
}

plugins {
    // Apply the Kotlin JVM plugin to add support for Kotlin on the JVM.
    kotlin("jvm").version("${extra["kotlinVersion"]}")
    id("com.github.johnrengelman.shadow").version("5.0.0")
    // Apply the application plugin to add support for building a CLI application.
    application
    idea
}

repositories {
    mavenCentral()
    jcenter()
}

val mainJarClassName = "com.example.ApplicationKt"

configurations {
    testCompile.exclude("module" to "junit")
}

dependencies {

    // Use the Kotlin JDK 8 standard library.
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("io.reactiverse:reactive-pg-client:0.11.3")
    implementation("com.healthmarketscience.sqlbuilder:sqlbuilder:3.0.0")
    implementation("io.vertx:vertx-core:${extra["vertxVersion"]}")
    implementation("io.vertx:vertx-web:${extra["vertxVersion"]}")

    implementation("io.vertx:vertx-grpc:${extra["vertxVersion"]}")
    implementation("io.vertx:vertx-lang-kotlin:${extra["vertxVersion"]}")
    implementation("io.vertx:vertx-lang-kotlin-coroutines:${extra["vertxVersion"]}")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.2.1")

    // for configuration
    implementation("io.vertx:vertx-config:${extra["vertxVersion"]}")
    implementation("io.vertx:vertx-config-yaml:${extra["vertxVersion"]}")

    // missing in JDK11
    implementation("javax.annotation:javax.annotation-api:1.3.1")
    // Use the Kotlin test library.
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    // Use the Kotlin JUnit integration.
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")

    testImplementation("io.vertx:vertx-junit5:${extra["vertxVersion"]}")
    testImplementation("org.assertj:assertj-core:3.12.2")

    implementation("io.zipkin.brave:brave-instrumentation-grpc:5.6.3")
    // for datadog
    implementation("org.msgpack:jackson-dataformat-msgpack:0.8.16")
    implementation("org.slf4j:jcl-over-slf4j:1.7.20")
    implementation("ch.qos.logback:logback-classic:1.1.7")

    implementation("io.vertx:vertx-micrometer-metrics:${extra["vertxVersion"]}")
    // this is needed otherwise vertx will throw a ClassNotFoundException due to missing class related to prometheus
    // this is probably also a bug
    implementation("io.micrometer:micrometer-registry-prometheus:1.1.4")
    implementation("io.micrometer:micrometer-registry-statsd:1.1.4")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks.compileKotlin {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

tasks.compileTestKotlin {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
}

tasks.shadowJar {
    classifier = "fat"

    mergeServiceFiles {
        include("META-INF/services/io.vertx.core.spi.VerticleFactory")
    }
}

task<JavaExec>("dev") {
    main = "io.vertx.core.Launcher"
    classpath = sourceSets["main"].runtimeClasspath
    args = listOf("run", "--launcher-class=$mainJarClassName", "--redeploy=src/**/*", "--on-redeploy=./gradlew classes")
}

application {
    // Define the main class for the application.
    mainClassName = mainJarClassName
}

tasks.test {
    // to enable support to JUnit5 in gradle
    useJUnitPlatform {
        includeEngines = setOf("junit-jupiter", "junit-vintage")
    }
}
