# QuickWordbook

"QuickWordbook" is a English vocaburaly wordbook app for Android.  

You can see the feature and demo movie at https://rio-sh.github.io/riosh_works/posts/quick_wordbook/.

# Architecture

Architecture of QuickWordbook refer to [Guide to app architecture](https://developer.android.com/jetpack/guide) by Google.  

In the above document, it's recomended that a app be divided into the following layers.  

<img src="https://developer.android.com/topic/libraries/architecture/images/mad-arch-overview.png" width=50%>  
  

QuickWordbook has two layers, UI layer and Data layer.

In the UI layer, it follows Unidirectional Data Flow (UDF),an architecture achieve separation of responsibility.    
(For details about UDF, please refer to https://developer.android.com/jetpack/guide/ui-layer#udf.)　　

Each screen has a ViewModel that manages UI state and updates state by events from a Screen Composable.  

<img src="https://developer.android.com/topic/libraries/architecture/images/mad-arch-ui-udf.png" width=50%>  

In the Data layer, it follows repository pattern.  
This app has a repository and retrive data from a local database (Room) and a http client (Retrofit).  

<img src="https://developer.android.com/topic/libraries/architecture/images/mad-arch-data-overview.png" width=50%>  

This http client is used for the auto translation feature in the app. It accesses to [Google Apps Script](https://www.google.com/script/start/)
