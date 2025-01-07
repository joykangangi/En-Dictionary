package com.jkangangi.en_dictionary.game

import com.jkangangi.en_dictionary.game.mode.model.GameModeParam
import com.jkangangi.en_dictionary.game.mode.model.GameSummaryStats
import kotlinx.serialization.Serializable


@Serializable
sealed class GameRoute {

    @Serializable
    data object Root : GameRoute()

    @Serializable
    data object GameIntroRoute : GameRoute()

    @Serializable
    data class GameModeRoute(val gameMode: GameModeParam) : GameRoute()

    @Serializable
    data object EasyGameModeRoute : GameRoute()

    @Serializable
    data object MediumGameModeRoute : GameRoute()

    @Serializable
    data object HardGameModeRoute : GameRoute()

    @Serializable
    data class GameSummaryDialogRoute(val gameSummaryStats: GameSummaryStats) : GameRoute()
}
