# En-Dictionary

English Dictionary App. Search phrases or a word

### Screenshots

<table>
  <tr>
      <td>Light Theme</td>
      <td>Dark Theme</td>
  </tr> 
  <tr>
   <td><img src = "https://github.com/joykangangi/MyResume/assets/64706463/a79fbe22-b790-4e99-99f3-ad5dc1fb2f56" alt = "mainlight"></td>
   <td><img src = "https://github.com/joykangangi/MyResume/assets/64706463/14b1baa9-05e6-441e-ae21-d4006d19e27a" alt = "maindark"></td>

  </tr> 
  <tr>
      <td><img src = "https://github.com/joykangangi/MyResume/assets/64706463/ae125b54-6e6c-4d82-a9a4-ab65decb88d1" alt = "wordlight"></td>
      <td><img src = "https://github.com/joykangangi/MyResume/assets/64706463/4d994be6-ab08-432c-a7af-ea4daf06c559" alt = "worddark"></td>

  </tr>
  <tr>
      <td><img src = "https://github.com/joykangangi/MyResume/assets/64706463/eb981b48-d093-4e6e-8da3-5b78f1fd0c24" alt = "phraselight"></td>
      <td><img src="https://github.com/joykangangi/MyResume/assets/64706463/7472538b-4317-4272-819a-95ffbbb9eb5f" alt ="gamedark"></td>
  </tr>
  <tr>
      <td><img src = "https://github.com/joykangangi/MyResume/assets/64706463/d21ea281-f0ef-43eb-82b5-c129e6c95954" alt = "fontlight"></td>
      <td><img src="https://github.com/joykangangi/MyResume/assets/64706463/959ea212-20ae-4e82-9240-daff4b988fbb" alt ="historydark"></td>
  </tr>

</table>

### Why English Dictionary

- I recently was asked to read an excerpt in public, but I was not sure of how to pronounce some
  words. Unfortunately, the dictionary app that I was using then did not have phonetics.
- Hence, the idea for creating my own version of a dictionary was born.
- The application consumes
  this [Dictionary API](https://rapidapi.com/xf-innovations-xf-innovations-default/api/xf-english-dictionary1)

### Libraries Used:

- Jetpack Compose - for UI creation
- Appyx - for navigation (It is is very straightforward, no ambiguous annotations)
- Ktor Client - for establishing a network connection between the API and the mobile client. (This
  too is very straightforward to write)
- Room - for storing searched words / caching
- Jsoup - for parsing HTML content from the API to readable text in the screen
- Datastore-Preferences - storing / managing user preferences in the application, such as Theme
  Options or Font Options.

### App Functionalities /how it works

- One can search for an phrase/idiom e.g `kick the bucket` or a word `aseity` via a network.
- One can read definitions and examples of their search queries.
- One can read and listen to phonetics of words or phrases.
- The app works offline with the help of caching
- One can see and delete their search history
- One can play a personalized word game from their search history. 😀

### Useful Resources / Libraries Learnt

- [Understanding Ktor-Client](https://blog.devgenius.io/out-with-retrofit-and-in-with-ktor-client-e8b52f205139#:~:text=What's%20KTor%20Client%20and%20how,and%20is%20completely%20Kotlin%20powered.)
- [Introduction to Kotlin Flows](https://abhiappmobiledeveloper.medium.com/introduction-to-kotlin-flows-flow-mutablesharedflow-and-sharedflow-23f109dc0dae)
- [Kotlin Flows VS suspend functions](https://stackoverflow.com/questions/76030366/when-to-use-suspend-function-and-flow-together-or-seperate-in-kotlin)
- [StateFlow and SharedFlow](https://www.valueof.io/blog/stateflow-sharedflow-flow-viewmodel-lifecycle) -
  Excellent write-up!!
- [Solving Cursor Issue](https://medium.com/androiddevelopers/effective-state-management-for-textfield-in-compose-d6e5b070fbe5) -
  At some point, the TextFields in `SearchScreen` had a cursor issue and this article came in handy
  in directing me on what could be the solution. In the end I used
  separate [StateFlows](https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-state-flow/)
  one for the 3 TextFields and another for the NetworkState, before they were all merged.
- [An App using Appyx-Navigation](https://github.com/KotlinGeekDev/Nosky/tree/main)

### Challenges faced

- Finding a free API with content I wanted.
- Managing UI states.
- Learning new libraries which at the time did not not have many referrals.
- Most of the challenges were solved after trial-and-error methods OR reading through the Useful
  Resources.

#### Appyx

Model driven navigation library library that provides a declarative approach to handle navigation in
your Android app.
It follows the principles of the Model-View-Intent (MVI) architecture, separating the navigation
logic from the UI components.
The "model"  refers to the destination models that represent the screens or components of your app
and are used to define the navigation structure
and handle navigation actions within the library.

Here's an overview of how Appyx works:

Model: Appyx uses a model to represent the navigation state of your app. This model typically
consists of a back stack that holds the history of navigated screens or destinations. The model is
responsible for managing the current active screen and handling navigation actions.

Node: Nodes are components in your app that represent screens, fragments, or other UI elements. Each
Node is associated with a specific route or destination in the navigation structure.

Navigation Structure: The navigation structure defines all the possible screens or destinations in
your app. It is often represented using a sealed class or an enum class that includes subclasses or
enum values for each route.

View: The view layer observes the changes in the navigation model and updates the UI accordingly.



