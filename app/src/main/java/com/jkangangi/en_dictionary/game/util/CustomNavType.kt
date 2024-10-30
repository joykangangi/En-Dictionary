package com.jkangangi.en_dictionary.game.util

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import com.jkangangi.en_dictionary.game.mode.model.GameSummaryStats
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


/**
 * if class not implemented throws this:
 * java.lang.IllegalArgumentException:
 * Cannot cast gameSummaryStats of type GameSummaryStats to a NavType.
 * Make sure to provide custom NavType for this argument.
 */

object CustomNavType {

    val GameSummaryType = object : NavType<GameSummaryStats>(
        isNullableAllowed = false
    ) {
        override fun serializeAsValue(value: GameSummaryStats): String {
            return Uri.encode(Json.encodeToString(value))
        }

        override fun parseValue(value: String): GameSummaryStats {
            return Json.decodeFromString(Uri.decode(value))
        }


        override fun get(bundle: Bundle, key: String): GameSummaryStats? {
            return Json.decodeFromString(bundle.getString(key) ?: return null)
        }

        override fun put(bundle: Bundle, key: String, value: GameSummaryStats) {
            bundle.putString(key,Json.encodeToString(value))
        }
    }
}