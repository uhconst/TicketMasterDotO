# MVVM-KOTLIN
This Android app shows a list of events by fetching them from an API and storing them in a local database. 
It also includes a Chatbot and an About screen. Since this is a showcase app using clean architecture and modern frameworks, I added these extra screens so you can learn more about me and my work.

Tapping add to favourite will move the event to the top of the list in a separate
“Favourites” section and also to the “Favourites Events” screen.

## Architecture
- **Modularised Structure**: The project is organised into several module types to ensure a clean separation of concerns and improved maintainability.
  - **Feature Modules**: contain UI logic and specific screen implementations (e.g., `feature-events`, `feature-chatbot`).
  - **Domain Modules**: handle business logic and use cases, acting as a bridge between data and UI (e.g., `domain-events`).
  - **Data Modules**: responsible for data retrieval and persistence, including API calls and database management (e.g., `api-events`, `repo-favourites`).
  - **Library Modules**: standalone modules (found in the `libraries/` directory) designed as pure Kotlin/Android utilities. These modules are strictly isolated: they do not depend on any other project modules and do not depend on each other. This ensures they provide reusable, independent functionality (e.g., `lib-network-utils`, `lib-compose-utils`).
- **MVVM Pattern**: Follows the Model-View-ViewModel architecture to separate UI from business logic.
- **Jetpack Compose**: Modern toolkit for building native UI using a declarative approach.

## Libraries used in this project
- [Gson](https://github.com/google/gson)
- [Koin](https://insert-koin.io/)
- [LiveData](https://developer.android.com/topic/libraries/architecture/livedata.html)
- [OkHttp](https://github.com/square/okhttp)
- [Coil](https://coil-kt.github.io/coil/compose/)
- [Retrofit](https://square.github.io/retrofit/)
- [Room](https://developer.android.com/topic/libraries/architecture/room.html)
- [Data Store](https://developer.android.com/topic/libraries/architecture/datastore)
- [Coroutines](https://developer.android.com/kotlin/coroutines)
- [Robolectric](https://github.com/robolectric/robolectric)
- [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel.html)

## Events Screen
- Displays a list of upcoming events from the Ticketmaster API.
- Each item shows: Event Name, Image, Venue Name, and Event Dates.
- **Interactive Features**:
  - Add to Favourites directly from the list.
  - Swipe-to-refresh to fetch the latest event updates.
  - Smooth navigation to the details screen using shared element transitions.

## Events Details Screen
- Detailed view of a selected event including its name, image, venue, and dates.
- Utilises Shared Element Transitions for a smooth visual experience when navigating from the list.

## Chatbot Screen
- Interactive chat with "UryBot", powered by Gemini AI.
- Users can provide their own Gemini API Key via a settings dialog.
- API Key is stored using Jetpack DataStore.
- Features an auto-scrolling message list for a seamless conversation.

## About Screen
- Information about the developer, including name, role, and bio.
- Links to GitHub and LinkedIn profiles.
- Description of the Ticketmaster DotO application.

## Tests
- Layout
- View Model
- Use Case
- Dao
- Modules

## What you should expect
- Display a list of the next 50 events in London from the [Ticketmaster API]
  (http://developer.ticketmaster.com/products-and-docs/apis/discovery-api/v2)
- MVVM architecture pattern
- Storing favourites events using Room DB
- DEBUG and RELEASE mode
- Handling screen rotation
- Swipe to refresh
- Project hosted on [GitHub](https://github.com/uhconst/Ticket_Master.git)

## Developed by
Uryel Constancio - [uryelhenrique.c@gmail.com](uryelhenrique.c@gmail.com)