# QuickWordbook

"QuickWordbook" is a English vocabulary wordbook app for Android.  

You can see the feature and demo movie at https://rio-sh.github.io/riosh_works/posts/quick_wordbook/.

# Package structure
|Package: com.io.github.rio_sh.quickwordbook||
| ------------------ | :---------------------------- |
|.data| The data layer for the app |
|.di| Code for dipendency injection (Hilt)  |
|.network| Code for http client (Retrofit) |
|.ui| The UI layer for the app. Each screens has a viewModel|

# Architecture

Architecture of QuickWordbook refer to [Guide to app architecture](https://developer.android.com/jetpack/guide) by Google.  

In the above document, it's recomended that a app be divided into the following layers.  

<img src="https://developer.android.com/topic/libraries/architecture/images/mad-arch-overview.png" width=60%>  
  

QuickWordbook has two layers, UI layer and Data layer.

In the UI layer, it follows Unidirectional Data Flow (UDF),an architecture achieve separation of responsibility.    
(For details about UDF, please refer to https://developer.android.com/jetpack/guide/ui-layer#udf.)　　

Each screen has a ViewModel that manages UI state and updates state by events from a Screen Composable.  

<img src="https://developer.android.com/topic/libraries/architecture/images/mad-arch-ui-udf.png" width=60%>  

In the Data layer, it follows repository pattern.  
This app has a repository and retrive data from a local database (Room) and a http client (Retrofit).  

<img src="https://developer.android.com/topic/libraries/architecture/images/mad-arch-data-overview.png" width=60%>  

This http client is used for the auto translation feature in the app. It accesses to [Google Apps Script](https://www.google.com/script/start/)

# Dev environment
IDE : Android Studio Bumblebee | 2021.1.1 Patch 2  
Kotlin : 1.6.10  
Gradle : 7.0.2  
Java : 11  
minSdk : 23  
targetSdk : 31  