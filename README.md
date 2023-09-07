# En-Dictionary
English Dictionary App. Search phrases or a word
### Why English Dictionary
- I recently was asked to read an excerpt in public, but I was not sure of how to pronounce some words. Unfortunately, the dictionary app that I was using then did not have phonetics.
-  Hence, the idea for creating my own version of a dictionary was born.
- The application consumes this [Dictionary API](https://rapidapi.com/xf-innovations-xf-innovations-default/api/xf-english-dictionary1)

### Libraries Used:
- Jetpack Compose - for UI creation
- Appyx - for navigation (It is is very straightforward, no ambiguous annotations)
- Ktor Client - for establishing a network connection between the API and the mobile client. (This too is very straightforward to write)
- Room - for storing searched words / caching
- Jsoup- for parsing HTML content from the API to readable text in the screen

### App Functionalities /how it works
- One can search for a phrase e.g `kick the bucket` or a word `aseity` via a network.
- One can read definitions and examples of their search queries.
- One can read and listen to phonetics of words or phrases.
- The app works offline with the help of caching
- One can see and delete their search history
- One can play a personalized word game from their search history. 😀
  
### Appyx 
Model driven navigation library library that provides a declarative approach to handle navigation in your Android app.
It follows the principles of the Model-View-Intent (MVI) architecture, separating the navigation logic from the UI components.
The "model"  refers to the destination models that represent the screens or components of your app and are used to define the navigation structure
and handle navigation actions within the library.

Here's an overview of how Appyx works:

Model: Appyx uses a model to represent the navigation state of your app. This model typically consists of a back stack that holds the history of navigated screens or destinations. The model is responsible for managing the current active screen and handling navigation actions.

Node: Nodes are components in your app that represent screens, fragments, or other UI elements. Each Node is associated with a specific route or destination in the navigation structure.

Navigation Structure: The navigation structure defines all the possible screens or destinations in your app. It is often represented using a sealed class or an enum class that includes subclasses or enum values for each route.

Actions: Actions represent user interactions or events that trigger navigation. For example, clicking a button to navigate to a different screen. Actions are dispatched to the model to initiate navigation.

Model Update: When an action is dispatched, the model processes the action, updates the navigation state, and determines the next active screen or destination.

View: The view layer observes the changes in the navigation model and updates the UI accordingly. It can be implemented using a reactive UI framework like Jetpack Compose or SwiftUI, or a traditional UI framework like Android Views.

Intent: Intents are emitted by the view layer to signal user interactions or events. These intents are observed by the model, and based on the intent, the model updates the navigation state.

Back Stack Management: The model manages the back stack, allowing for forward and backward navigation. It ensures that the correct screen or destination is displayed when navigating back or forward.

By following this model-driven approach, Appyx helps you decouple the navigation logic from the view layer and provides a structured way to handle navigation in your app. It promotes a single source of truth for the navigation state and simplifies the management of complex navigation flows.

### Challenges faced
- Finding a free API with content I wanted.
- Managing UI states.
- Learning new libraries which at the time did not not have many referrals. Special thanks to the owner of [this repo](https://github.com/KotlinGeekDev/Nosky/tree/main), their work was of great help in understanding Appyx.
