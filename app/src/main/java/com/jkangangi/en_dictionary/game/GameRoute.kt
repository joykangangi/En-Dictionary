package com.jkangangi.en_dictionary.game

import kotlinx.serialization.Serializable


@Serializable
sealed class GameRoute {

    @Serializable
    data object Root : GameRoute()

    @Serializable
    data object GameIntro : GameRoute()

    @Serializable
    data object EasyGameMode : GameRoute()

    @Serializable
    data object WrongAnsDialog : GameRoute()

    @Serializable
    data object CorrectAnsDialog: GameRoute()

    @Serializable data object GameOverDialog: GameRoute()

    @Serializable
    data object MediumGameMode : GameRoute()

    @Serializable
    data object HardGameMode : GameRoute()
}
