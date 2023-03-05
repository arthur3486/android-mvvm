package com.arthurivanets.sample.domain.di

import com.arthurivanets.sample.domain.repositories.characters.CharactersRepository
import com.arthurivanets.sample.domain.repositories.comics.ComicsRepository
import com.arthurivanets.sample.domain.repositories.events.EventsRepository

interface ExposesDependencies {

    fun exposeCharactersRepository(): CharactersRepository

    fun exposeComicsRepository(): ComicsRepository

    fun exposeEventsRepository(): EventsRepository

}
