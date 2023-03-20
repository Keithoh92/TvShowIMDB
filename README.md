# MovieDD

Android App <br />
App Objective: <br />
Download a list of top rated tv shows and display them to the user <br /> <br />

App features<br />
User can sort tv shows by alphabete, ratings and the date the tv show was aired
User can change the layout of the tv shows displayed from a vertical list to grid view list
Each tv show in the list has a description button with text, when clicked, this will collapse a description of the tv show

The app utilises the MVVM(Model-View-ViewModel) arhitectural pattern to separate the concernes of the UI (view) from the data and business logic (Model) 
and a layer in between (ViewModel) which is the bridge between the 2.

The app has a clean architecture with following structure: <br />
Common <br />
Data <br />
di (dependency injection) <br />
domain <br />
ui <br />
<br /><br />
Functionality: <br />
I am using MutableState to control the state of the UI, the viewModel acts as the state holder and any changes to the state are actioned from here.<br />
The state is passed in as a parameter to the main composable screen and any composables that require the state.
<br /><br />
Using a mutable state, I can update the mutableState object from the ViewModel and any observables on the UI will recompose accordingly when its state changes.<br />
<br />
To update the state from user events on the UI, I am using onEvent callbacks that are passed as params to the composables that require them.<br />
When an event is triggered the onEvent callback function on the viewModel will handle the event and call the appropriate method <br />
which will update the state accordingly from the ViewModel.<br />
<br />
I am using Retrofit to hit a single API endpoint and downloading the TvShows<br />
The app performs this on initialisation of TvShowScreenViewModel of the TvShow screen which is loaded from the Main Activity and entry point of the app.<br />
The app will parse the data required from the JSON response and pass it back to the VM through a TvShowAPI interface.<br />
<br />
Once downloaded successfully, I construct the image url for each tvShow and save the title and imageUrl to the Database.<br />
<br />
I use the DB as the single source of truth for any UI related data.<br />
<br />
The app uses Room as the DBMS, it has a tvShow database with a tvShows table that is used to store the titles of the downloaded tvShows and the url used<br /> to download the image.<br />
<br />
Once this process completes, I call the getTvShows method of the TvShowDBRepo <br />
which returns the list of tvShows from the DB. <br />
<br />
I construct a List of type ShowInfo and update the TvShowUIState.tvShows object.<br />
<br />
I am using the coil image library to download the image from the api using the saved url in the DB for each tvShow and <br />
loading them into an AsyncImage composable on the UI for each tvShow cardview.<br />
<br />
The app uses Dagger/Hilt for dependency injection.
<br />
I have not included RxJava in my implementation as I believe using MutableStates to be an appropriate use case for this particular app for observing and updating the state of the UI.
<br /><br />
TODO:<br />
Hide the API_KEY on GitHub when I figure out how to do it.<br /><br /><br />

What I would do if I had more time:<br />
Ensure the dark mode and light mode themes are properly configured and use those as the colouring schemes rather than the Colors.kt class
<br /><br />
I think I have implemented everything I wanted to with this app.<br />
I am always open to constructive criticism and feedback to see where I can improve from another devs perspective.<br />
<br />
I hope this app demonstrates my ability and skills adequetly.<br /><br />


Built using: <br />
Kotlin <br />
Jetpack compose <br />
MVVM <br />
Coroutines <br />
Room <br />
Retrofit <br />
Dagger/Hilt <br />
Clean Arhitecture <br />
Mockk unit testing <br />
