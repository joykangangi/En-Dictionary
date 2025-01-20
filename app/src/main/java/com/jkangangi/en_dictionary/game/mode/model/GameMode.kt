package com.jkangangi.en_dictionary.game.mode.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Retrieve complex data when navigating
 * It is strongly advised not to pass around complex data objects when navigating,
 * but instead pass the minimum necessary information,
 * such as a unique identifier or other form of ID, as arguments when performing navigation actions:
 *
 * // Pass only the user ID when navigating to a new destination as argument
 * navController.navigate(Profile(id = "user1234"))
 * Complex objects should be stored as data in a single source of truth,
 * such as the data layer. Once you land on your destination after navigating,
 * you can then load the required information from the single source of truth by using the passed ID.
 * To retrieve the arguments in your ViewModel that's responsible for accessing the data layer,
 */

@Parcelize
enum class GameMode: Parcelable {
    Easy,
    Medium,
    Hard
}
