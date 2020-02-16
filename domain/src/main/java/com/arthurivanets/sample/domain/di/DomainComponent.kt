package com.arthurivanets.sample.domain.di

import com.arthurivanets.mvvm.commons.CoreComponent
import com.arthurivanets.sample.domain.di.modules.CharactersModule
import com.arthurivanets.sample.domain.di.modules.ComicsModule
import com.arthurivanets.sample.domain.di.modules.DomainUtilsModule
import com.arthurivanets.sample.domain.di.modules.EventsModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        CharactersModule::class,
        ComicsModule::class,
        EventsModule::class,
        DomainUtilsModule::class
    ],
    dependencies = [CoreComponent::class]
)
interface DomainComponent : ExposesDependencies