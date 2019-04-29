/*
 * Copyright 2018 Arthur Ivanets, arthur.ivanets.l@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:JvmName("CharacterInfoFragmentUtils")

package com.arthurivanets.sample.ui.characters.info

import android.os.Bundle
import androidx.core.os.bundleOf
import com.arthurivanets.sample.domain.entities.Character


internal val extrasExtractor : (Bundle.() -> Extras) = {
    Extras(
        character = (this.getParcelable(ExtrasKeys.CHARACTER) ?: Character())
    )
}


internal val stateExtractor : (Bundle.() -> State) = {
    State(
        character = (this.getParcelable(StateKeys.CHARACTER) ?: Character())
    )
}


internal object ExtrasKeys {
    
    const val CHARACTER = "character"
    
}


internal object StateKeys {

    const val CHARACTER = "character"
    
}


internal data class Extras(
    val character : Character = Character()
)


internal data class State(
    val character : Character
)


internal fun Bundle.saveState(state : State) {
    this.putParcelable(StateKeys.CHARACTER, state.character)
}


fun CharacterInfoFragment.Companion.newBundle(character : Character) : Bundle {
    return bundleOf(
        ExtrasKeys.CHARACTER to character
    )
}