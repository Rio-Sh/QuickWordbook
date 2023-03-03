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

To achieve separation of responsibility, QuickWordbook has two layer, UI layer and Datalayer.   
  
<!-- <img src="https://developer.android.com/topic/libraries/architecture/images/mad-arch-overview.png" width=60%>  -->

QuickWordbook has two layers, UI layer and Data layer.  

In the UI layer, the structure follows the Unidirectional Data Flow (UDF) architecture (UDF: https://developer.android.com/jetpack/guide/ui-layer#udf).  
Each screen has a ViewModel that manages UI state and updates state by events from a Screen Composable.  

<!-- <img src="https://developer.android.com/topic/libraries/architecture/images/mad-arch-ui-udf.png" width=60%>  -->

In the Data layer, the repository pattern is used. This app has a repository that retrieves data from a local database (Room) and an HTTP client (Retrofit).

This HTTP client is used for the auto-translation feature in the app.   
It's called by view models, and sends the pre-translated text and receives the translated text from [Google Apps Script](https://www.google.com/script/start/).  
<!-- <img src="https://developer.android.com/topic/libraries/architecture/images/mad-arch-data-overview.png" width=60%>  -->
  
Also, in the "ml_kit_ver" branch, [ML Kit](https://developers.google.com/ml-kit) is used for auto-translation feature instead of Google Apps Script. It's complete the translation process at local.
Additionally, this version of the app has auto language identification feature using ML Kit.
  
# Dev environment
IDE : Android Studio Bumblebee | 2021.1.1 Patch 2  
Kotlin : 1.6.10  
Gradle : 7.0.2  
Java : 11  
minSdk : 23  
targetSdk : 31  
