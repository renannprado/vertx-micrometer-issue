package com.example.modules

import com.example.DummyRepository
import com.example.MainHttpServer
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object VerticlesModule {
    @Provides
    @Singleton
    fun mainHttpServerProvider(dummyRepository: DummyRepository): MainHttpServer {
        return MainHttpServer(dummyRepository)
    }
}