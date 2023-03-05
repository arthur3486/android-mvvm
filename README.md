# Android MVVM Library + Comprehensive Demo Application

> ***Android library designed to greatly simplify the implementation process of an MVVM-based application by providing all the means necessary to solve the common problems and avoid the annoying boilerplate code.
<br><br> The library is accompanied by a comprehensive Demo Application that is built around MVVM and Clean Architecture concepts. The Demo Application utilizes such popular libraries as: [RxJava](https://github.com/ReactiveX/RxJava), [Dagger2](https://github.com/google/dagger), [Android Navigation Architecture Component](https://developer.android.com/guide/navigation), [OkHttp](https://github.com/square/okhttp), [Retrofit](https://github.com/square/retrofit), [Room](https://developer.android.com/topic/libraries/architecture/room), [Glide](https://github.com/bumptech/glide).***

**Android MVVM Library** will make the implementation of your MVVM-based application a trivial task thus allowing you to spend more time focusing on other important things.

[ ![Download](https://api.bintray.com/packages/arthurimsacc/maven/mvvm-core/images/download.svg) ](https://bintray.com/arthurimsacc/maven/mvvm-core/_latestVersion)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Platform](https://img.shields.io/badge/platform-Android-green.svg)](http://developer.android.com/index.html)
![](https://img.shields.io/badge/API-18%2B-green.svg?style=flat)
![](https://travis-ci.org/arthur3486/android-mvvm.svg?branch=master)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Android%20MVVM%20Library-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/7719)

## Contents

* [Demo](#demo)
* [Getting Started](#getting-started)
* [Modules](#modules)
* [Basic Implementation](#basic-implementation)
* [Dagger Based Implementation](#dagger-based-implementation)
* [Navigation Component Based Implementation](#navigation-component-based-implementation)
* [Navigation Component and Dagger Based Implementation](#navigation-component-and-dagger-based-implementation)
* [Compilation](#compilation)
* [Contribution](#contribution)
* [Hall of Fame](#hall-of-fame)
* [License](#license)

## Demo

***Marvel Universe*** Application is an application built around MVVM and Clean Architecture concepts, as well as the data provided by the [Marvel API](https://developer.marvel.com/). The application allows you to browse through the Marvel comics, events and characters; each of the aforementioned entities is accompanied by a corresponding detailed overview screen, which gives you even more insight into the Marvel Universe.

### Screenshots

<div style="dispaly:flex">
    <img src="https://github.com/arthur3486/android-mvvm/blob/master/screenshot_1.jpg" width="30%">
    <img src="https://github.com/arthur3486/android-mvvm/blob/master/screenshot_2.jpg" width="30%">
    <img src="https://github.com/arthur3486/android-mvvm/blob/master/screenshot_3.jpg" width="30%">
</div>
<div style="dispaly:flex">
    <img src="https://github.com/arthur3486/android-mvvm/blob/master/screenshot_4.jpg" width="30%">
    <img src="https://github.com/arthur3486/android-mvvm/blob/master/screenshot_5.jpg" width="30%">
    <img src="https://github.com/arthur3486/android-mvvm/blob/master/screenshot_6.jpg" width="30%">
</div>

### Application Architecture

![Application Architecture](https://github.com/arthur3486/android-mvvm/blob/master/app_architecture.png)

## Getting Started

### Prerequisites

**1. Make sure that you've added the `jcenter()` repository to your top-level `build.gradle` file.**

````groovy
buildscript {
    //...
    repositories {
        //...
        jcenter()
    }
    //...
}
````

**2. Enable the **jetifier** and **androidX** support in the top-level `gradle.properties` file.**

````groovy
//...
android.enableJetifier=true
android.useAndroidX=true
//....
````

**3. Update your `compileSdkVersion` in the module-level `build.gradle` file to **29+**.**

````groovy
//...
android {
    //...
    compileSdkVersion 29
    //...
}
//...
````

**4. Enable the Data Binding in the module-level `build.gradle` file.**

````groovy
//...
android {
    //...
    dataBinding {
        enabled true
    }
    //...
}
//...
````

**5. Replace your `com.android.support.appcompat.*` dependency with the new `androidx.appcompat.*` alternative.**

````groovy
//...
dependencies {
    //...
    implementation "androidx.appcompat:appcompat:1.0.2"
    //...
}
//...
````

**6. Add the [Android Lifecycle (ViewModel)](https://developer.android.com/topic/libraries/architecture/viewmodel), [RxJava](https://github.com/ReactiveX/RxJava) and [RxBus](https://github.com/arthur3486/rxbus) dependencies to the module-level `build.gradle` file.**

````groovy
//...
dependencies {
    //...
    implementation "androidx.lifecycle:lifecycle-viewmodel:2.0.0"
    implementation "io.reactivex.rxjava2:rxjava:2.2.12"
    implementation "io.reactivex.rxjava2:rxandroid:2.1.1"
    implementation "com.arthurivanets.rxbus:rxbus:1.1.0"
    //...
}
//...
````

### Android MVVM Dependencies

The basic implementation must include the core module
> ***Latest version:*** [ ![Download](https://api.bintray.com/packages/arthurimsacc/maven/mvvm-core/images/download.svg) ](https://bintray.com/arthurimsacc/maven/mvvm-core/_latestVersion)

`implementation "com.arthurivanets.mvvm:mvvm-core:X.Y.Z"`

Which should be added to your module-level `build.gradle` file.

````groovy
ext {
    //...
    androidMvvmLibraryVersion = "1.3.1"
}

dependencies {
    //...
    implementation "com.arthurivanets.mvvm:mvvm-core:$androidMvvmLibraryVersion"
}
````

After that you can proceed with further implementation.
> ***See: [Basic Implementation](#basic-implementation), [Dagger Based Implementation](#dagger-based-implementation), [Navigation Component Based Implementation](#navigation-component-based-implementation), [Navigation Component and Dagger Based Implementation](#navigation-component-and-dagger-based-implementation)***

## Modules

The library is comprised of several modules, namely:

* [`mvvm-core`](https://github.com/arthur3486/android-mvvm/tree/master/mvvm/src/main/java/com/arthurivanets/mvvm) - core implementation (Required)
* [`mvvm-dagger`](https://github.com/arthur3486/android-mvvm/tree/master/mvvm-dagger/src/main/java/com/arthurivanets/mvvm/dagger) - Dagger DI based implementation (Optional)
* [`mvvm-navigation`](https://github.com/arthur3486/android-mvvm/tree/master/mvvm-navigation/src/main/java/com/arthurivanets/mvvm/navigation) - Android Navigation Component based implementation (Optional)
* [`mvvm-navigation-dagger`](https://github.com/arthur3486/android-mvvm/tree/master/mvvm-navigation-dagger/src/main/java/com/arthurivanets/mvvm/navigation/dagger) - Android Navigation Component + Dagger DI based implementation (Optional)

The [`mvvm-core`](https://github.com/arthur3486/android-mvvm/tree/master/mvvm/src/main/java/com/arthurivanets/mvvm) module is a core module the other modules depend on. It provides all the means necessary to create the MVVM-based Fragments and Activities, as well the corresponding ViewModels. (***See: [`MvvmFragment`](https://github.com/arthur3486/android-mvvm/blob/master/mvvm/src/main/java/com/arthurivanets/mvvm/MvvmFragment.kt), [`MvvmActivity`](https://github.com/arthur3486/android-mvvm/blob/master/mvvm/src/main/java/com/arthurivanets/mvvm/MvvmActivity.kt), [`BaseViewModel`](https://github.com/arthur3486/android-mvvm/blob/master/mvvm/src/main/java/com/arthurivanets/mvvm/BaseViewModel.kt), [`AbstractViewModel`](https://github.com/arthur3486/android-mvvm/blob/master/mvvm/src/main/java/com/arthurivanets/mvvm/AbstractViewModel.kt), [`Command`](https://github.com/arthur3486/android-mvvm/blob/master/mvvm/src/main/java/com/arthurivanets/mvvm/events/Command.kt), [`ViewState`](https://github.com/arthur3486/android-mvvm/blob/master/mvvm/src/main/java/com/arthurivanets/mvvm/events/ViewState.kt), [`Route`](https://github.com/arthur3486/android-mvvm/blob/master/mvvm/src/main/java/com/arthurivanets/mvvm/events/Route.kt)***)

The [`mvvm-dagger`](https://github.com/arthur3486/android-mvvm/tree/master/mvvm-dagger/src/main/java/com/arthurivanets/mvvm/dagger) module is a module that provides the [`MvvmActivity`](https://github.com/arthur3486/android-mvvm/blob/master/mvvm-dagger/src/main/java/com/arthurivanets/mvvm/dagger/MvvmActivity.kt) and [`MvvmFragment`](https://github.com/arthur3486/android-mvvm/blob/master/mvvm-dagger/src/main/java/com/arthurivanets/mvvm/dagger/MvvmFragment.kt) implementations that automatically handle the injection of the [`Dagger DI`](https://github.com/google/dagger) dependencies.

The [`mvvm-navigation`](https://github.com/arthur3486/android-mvvm/tree/master/mvvm-navigation/src/main/java/com/arthurivanets/mvvm/navigation) module is a module that provides the [`MvvmActivity`](https://github.com/arthur3486/android-mvvm/blob/master/mvvm-navigation/src/main/java/com/arthurivanets/mvvm/navigation/MvvmActivity.kt) and [`MvvmFragment`](https://github.com/arthur3486/android-mvvm/blob/master/mvvm-navigation/src/main/java/com/arthurivanets/mvvm/navigation/MvvmFragment.kt) implementations with built-in support for the [`Android Navigation Component`](https://developer.android.com/guide/navigation) based navigation.

[`mvvm-navigation-dagger`](https://github.com/arthur3486/android-mvvm/tree/master/mvvm-navigation-dagger/src/main/java/com/arthurivanets/mvvm/navigation/dagger) module is a module that provides the [`MvvmActivity`](https://github.com/arthur3486/android-mvvm/blob/master/mvvm-navigation-dagger/src/main/java/com/arthurivanets/mvvm/navigation/dagger/MvvmActivity.kt) and [`MvvmFragment`](https://github.com/arthur3486/android-mvvm/blob/master/mvvm-navigation-dagger/src/main/java/com/arthurivanets/mvvm/navigation/dagger/MvvmFragment.kt) implementations that have both the built-in support for the [`Android Navigation Component`](https://developer.android.com/guide/navigation) based navigation and automatic handling of the injection of the [`Dagger DI`](https://github.com/google/dagger) dependencies.

## Basic Implementation

The basic implementation consists of 5 simple steps, namely:
1) Creation of the ViewModel
2) Creation of the ViewModel-specific View States & Commands
3) Creation of the application screen routes
4) Creation of the `layout.xml` for the Activity/Fragment
5) Implementation of the Activity/Fragment

<br>

So, let's start with the creation of the ViewModel for our Activity and/or Fragment.

<details><summary><b>SimpleViewModel.kt [contract] (click to expand)</b></summary>
<p>

````kotlin
interface SimpleViewModel : BaseViewModel {

    // The rest of your observable fields and event propagation methods should be defined here

}
````

</p></details>

> The ViewModel contract should implement the [`BaseViewModel`](https://github.com/arthur3486/android-mvvm/blob/master/mvvm/src/main/java/com/arthurivanets/mvvm/BaseViewModel.kt) interface.

<details><summary><b>SimpleViewModelImpl.kt [concrete implementation] (click to expand)</b></summary>
<p>

````kotlin
class SimpleViewModelImpl : AbstractViewModel(), SimpleViewModel {

    // Your concrete implementation...

}
````

</p></details>

> The concrete implementation of the ViewModel should extend the [`AbstractViewModel`](https://github.com/arthur3486/android-mvvm/blob/master/mvvm/src/main/java/com/arthurivanets/mvvm/AbstractViewModel.kt) class and implement the corresponding contract interface.

<br>

Then, create the ViewModel-specific View States & Commands.

<details><summary><b>GeneralViewStates.kt (click to expand)</b></summary>
<p>

````kotlin
sealed class GeneralViewStates<T>(payload : T? = null) : ViewState<T>(payload) {

    class Initial : GeneralViewStates<Unit>()

    class Loading<T>(payload : T? = null) : GeneralViewStates<T>(payload)

    class Success<T>(payload : T? = null) : GeneralViewStates<T>(payload)

    class Error<T>(payload : T? = null) : GeneralViewStates<T>(payload)

    // The rest of your View State go here...

}
````

</p></details>

> The implementation of the ViewModel-specific View States should be based upon the [`ViewState`](https://github.com/arthur3486/android-mvvm/blob/master/mvvm/src/main/java/com/arthurivanets/mvvm/events/ViewState.kt) class.

<details><summary><b>GeneralViewModelCommands.kt (click to expand)</b></summary>
<p>

````kotlin
sealed class GeneralViewModelCommands<T>(payload : T? = null) : Command<T>(payload) {

    class ShowToast(text : String) : GeneralViewModelCommands<String>(text)

    class RestartApplication : GeneralViewModelCommands<Unit>()

    // The rest of your ViewModel Commands go here...

}
````

</p></details>

> The implementation of the ViewModel-specific Commands should be based upon the [`Command`](https://github.com/arthur3486/android-mvvm/blob/master/mvvm/src/main/java/com/arthurivanets/mvvm/events/Command.kt) class.

<br>

Then, create the application screen routes.

<details><summary><b>MarvelRoutes.kt (click to expand)</b></summary>
<p>

````kotlin
sealed class MarvelRoutes<T>(payload : T? = null) : Route<T>(payload) {

    class CharacterInfoScreen(character : Character) : MarvelRoutes<Character>(character)

    class ComicsInfoScreen(comics : Comics) : MarvelRoutes<Comics>(comics)

    class EventInfoScreen(event : Event) : MarvelRoutes<Event>(event)

    // The rest of your Application Routes go here...

}
````

</p></details>

> The implementation of the Application Screen Routes should be based upon the [`Route`](https://github.com/arthur3486/android-mvvm/blob/master/mvvm/src/main/java/com/arthurivanets/mvvm/events/Route.kt) class.

<br>

After that, let's create the `layout.xml` files for both our Activity and Fragment.

<details><summary><b>activity_simple_mvvm.xml + fragment_simple_mvvm.xml (click to expand)</b></summary>
<p>

````xml
<?xml version="1.0" encoding="utf-8"?>
<layout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">

    <!-- Data-binding-related -->

    <data>

        <variable
            name="viewModel"
            type="com.yourapplication.sample.SimpleViewModel"/>

    </data>

    <!-- The Content Layout -->
    <!-- Your content layout goes here... -->

</layout>
````

</p></details><br>

Finally, let's implement the MVVM-based Activity and Fragment.

<details><summary><b>SimpleMvvmActivity.kt (click to expand)</b></summary>
<p>

````kotlin
import com.arthurivanets.mvvm.MvvmActivity

class SimpleMvvmActivity : MvvmActivity<ActivitySimpleMvvmBinding, SimpleViewModel>() {

    private lateinit var localViewModel : SimpleViewModel

    override fun injectDependencies() {
        // Initialize your View Model here...
        localViewModel = SimpleViewModelImpl()
    }

    // The rest of the Activity Initialization goes here...

    override fun onRegisterObservables() {
        // Register your ViewModel's observable fields here...
    }

    override fun onHandleCommand(command : Command<*>) {
        // handle the ViewModel-specific command here... (e.g. Restart the Application, Show Toast, etc.)
    }

    override fun onViewStateChanged(state : ViewState<*>) {
        // handle the View State Change here... (adjust your UI correspondingly)
    }

    override fun onRoute(route : Route<*>) {
        // handle the Application Route here... (navigate to the corresponding screen)
    }

    override fun getLayoutId() : Int {
        return R.layout.activity_simple_mvvm
    }

    override fun getBindingVariable() : Int {
        return BR.viewModel
    }

    override fun getViewModel() : SimpleViewModel {
        return localViewModel
    }

}
````

</p></details>

> The implementation of the MVVM Activity should be based upon the Core [`MvvmActivity`](https://github.com/arthur3486/android-mvvm/blob/master/mvvm/src/main/java/com/arthurivanets/mvvm/MvvmActivity.kt) class.

<details><summary><b>SimpleMvvmFragment.kt (click to expand)</b></summary>
<p>

````kotlin
import com.arthurivanets.mvvm.MvvmFragment

class SimpleMvvmFragment : MvvmFragment<FragmentSimpleMvvmBinding, SimpleViewModel>() {

    private lateinit var localViewModel : SimpleViewModel

    override fun injectDependencies() {
        // Initialize your View Model here...
        localViewModel = SimpleViewModelImpl()
    }

    // The rest of the Fragment Initialization goes here...

    override fun onRegisterObservables() {
        // Register your ViewModel's observable fields here...
    }

    override fun onHandleCommand(command : Command<*>) {
        // handle the ViewModel-specific command here... (e.g. Restart the Application, Show Toast, etc.)
    }

    override fun onViewStateChanged(state : ViewState<*>) {
        // handle the View State Change here... (adjust your UI correspondingly)
    }

    override fun onRoute(route : Route<*>) {
        // handle the Application Route here... (navigate to the corresponding screen)
    }

    override fun getLayoutId() : Int {
        return R.layout.fragment_simple_mvvm
    }

    override fun getBindingVariable() : Int {
        return BR.viewModel
    }

    override fun getViewModel() : SimpleViewModel {
        return localViewModel
    }

}
````

</p></details>

> The implementation of the MVVM Fragment should be based upon the Core [`MvvmFragment`](https://github.com/arthur3486/android-mvvm/blob/master/mvvm/src/main/java/com/arthurivanets/mvvm/MvvmFragment.kt) class.

<br>

The [`MvvmActivity`](https://github.com/arthur3486/android-mvvm/blob/master/mvvm/src/main/java/com/arthurivanets/mvvm/MvvmActivity.kt), [`MvvmFragment`](https://github.com/arthur3486/android-mvvm/blob/master/mvvm/src/main/java/com/arthurivanets/mvvm/MvvmFragment.kt) and [`AbstractViewModel`](https://github.com/arthur3486/android-mvvm/blob/master/mvvm/src/main/java/com/arthurivanets/mvvm/AbstractViewModel.kt) classes provide many convenience methods for dealing with the lifecycle of the ObservableField subscriptions and Rx disposbles, so it's definitely a good idea to look through the implementations in order to familiarize yourself with the available APIs.

> ***See: [`MvvmActivity`](https://github.com/arthur3486/android-mvvm/blob/master/mvvm/src/main/java/com/arthurivanets/mvvm/MvvmActivity.kt), [`MvvmFragment`](https://github.com/arthur3486/android-mvvm/blob/master/mvvm/src/main/java/com/arthurivanets/mvvm/MvvmFragment.kt), [`BaseViewModel`](https://github.com/arthur3486/android-mvvm/blob/master/mvvm/src/main/java/com/arthurivanets/mvvm/BaseViewModel.kt), [`AbstractViewModel`](https://github.com/arthur3486/android-mvvm/blob/master/mvvm/src/main/java/com/arthurivanets/mvvm/AbstractViewModel.kt), [`Command`](https://github.com/arthur3486/android-mvvm/blob/master/mvvm/src/main/java/com/arthurivanets/mvvm/events/Command.kt), [`ViewState`](https://github.com/arthur3486/android-mvvm/blob/master/mvvm/src/main/java/com/arthurivanets/mvvm/events/ViewState.kt), [`Route`](https://github.com/arthur3486/android-mvvm/blob/master/mvvm/src/main/java/com/arthurivanets/mvvm/events/Route.kt), [`GeneralViewStates`](https://github.com/arthur3486/android-mvvm/blob/master/app/src/main/java/com/arthurivanets/sample/ui/base/GeneralViewStates.kt), [`MarvelRoutes`](https://github.com/arthur3486/android-mvvm/blob/master/app/src/main/java/com/arthurivanets/sample/ui/base/MarvelRoutes.kt)***

## Dagger Based Implementation

The Dagger-based implementation process is almost identical to the one of the [Basic Implementation](basic-implementation), the only thing that's different here is the fact that you need to use the [`MvvmActivity`](https://github.com/arthur3486/android-mvvm/blob/master/mvvm-navigation-dagger/src/main/java/com/arthurivanets/mvvm/navigation/dagger/MvvmActivity.kt) and [`MvvmFragment`](https://github.com/arthur3486/android-mvvm/blob/master/mvvm-navigation-dagger/src/main/java/com/arthurivanets/mvvm/navigation/dagger/MvvmFragment.kt) provided by the [`mvvm-dagger`](https://github.com/arthur3486/android-mvvm/tree/master/mvvm-navigation-dagger/src/main/java/com/arthurivanets/mvvm/navigation/dagger) module instead of the ones coming from the [`mvvm-core`](https://github.com/arthur3486/android-mvvm/tree/master/mvvm/src/main/java/com/arthurivanets/mvvm) module.

<details><summary><b>SimpleMvvmActivity.kt (click to expand)</b></summary>
<p>

````kotlin
import com.arthurivanets.mvvm.dagger.MvvmActivity

class SimpleMvvmActivity : MvvmActivity<ActivitySimpleMvvmBinding, SimpleViewModel>() {

    @Inject
    private lateinit var localViewModel : SimpleViewModel

    // The rest of the Activity Initialization goes here...

    override fun onRegisterObservables() {
        // Register your ViewModel's observable fields here...
    }

    override fun onHandleCommand(command : Command<*>) {
        // handle the ViewModel-specific command here... (e.g. Restart the Application, Show Toast, etc.)
    }

    override fun onViewStateChanged(state : ViewState<*>) {
        // handle the View State Change here... (adjust your UI correspondingly)
    }

    override fun onRoute(route : Route<*>) {
        // handle the Application Route here... (navigate to the corresponding screen)
    }

    override fun getLayoutId() : Int {
        return R.layout.activity_simple_mvvm
    }

    override fun getBindingVariable() : Int {
        return BR.viewModel
    }

    override fun getViewModel() : SimpleViewModel {
        return localViewModel
    }

}
````

</p></details>

> The implementation of the MVVM Activity should be based upon the Dagger [`MvvmActivity`](https://github.com/arthur3486/android-mvvm/blob/master/mvvm-dagger/src/main/java/com/arthurivanets/mvvm/dagger/MvvmActivity.kt) class.

<details><summary><b>SimpleMvvmFragment.kt (click to expand)</b></summary>
<p>

````kotlin
import com.arthurivanets.mvvm.dagger.MvvmFragment

class SimpleMvvmFragment : MvvmFragment<FragmentSimpleMvvmBinding, SimpleViewModel>() {

    @Inject
    private lateinit var localViewModel : SimpleViewModel

    // The rest of the Fragment Initialization goes here...

    override fun onRegisterObservables() {
        // Register your ViewModel's observable fields here...
    }

    override fun onHandleCommand(command : Command<*>) {
        // handle the ViewModel-specific command here... (e.g. Restart the Application, Show Toast, etc.)
    }

    override fun onViewStateChanged(state : ViewState<*>) {
        // handle the View State Change here... (adjust your UI correspondingly)
    }

    override fun onRoute(route : Route<*>) {
        // handle the Application Route here... (navigate to the corresponding screen)
    }

    override fun getLayoutId() : Int {
        return R.layout.fragment_simple_mvvm
    }

    override fun getBindingVariable() : Int {
        return BR.viewModel
    }

    override fun getViewModel() : SimpleViewModel {
        return localViewModel
    }

}
````

</p></details>

> The implementation of the MVVM Fragment should be based upon the Dagger [`MvvmFragment`](https://github.com/arthur3486/android-mvvm/blob/master/mvvm-dagger/src/main/java/com/arthurivanets/mvvm/dagger/MvvmFragment.kt) class.

> ***See: [`MvvmActivity`](https://github.com/arthur3486/android-mvvm/blob/master/mvvm-dagger/src/main/java/com/arthurivanets/mvvm/dagger/MvvmActivity.kt), [`MvvmFragment`](https://github.com/arthur3486/android-mvvm/blob/master/mvvm-dagger/src/main/java/com/arthurivanets/mvvm/dagger/MvvmFragment.kt)***

## Navigation Component Based Implementation

The Navigation Component based implementation process has many things in common with the [Basic Implementation](basic-implementation); the differences are shown in the code snippets below.

<details><summary><b>activity_host.xml (click to expand)</b></summary>
<p>

````xml
<?xml version="1.0" encoding="utf-8"?>
<layout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">

    <!-- Data-binding-related -->

    <data>

        <variable
            name="viewModel"
            type="com.yourapplication.sample.StubViewModel"/>

    </data>

    <!-- The Actual Layout -->

    <com.google.android.material.internal.ScrimInsetsFrameLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:fitsSystemWindows="true">

        <fragment
            android:id="@+id/nav_host_fragment"
            android:name="com.arthurivanets.mvvm.navigation.MvvmNavHostFragment"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            app:defaultNavHost="true"
            app:navGraph="@navigation/your_navigation_graph"/>

    </com.google.android.material.internal.ScrimInsetsFrameLayout>

</layout>
````

</p></details>

> The [`MvvmNavHostFragment`](https://github.com/arthur3486/android-mvvm/blob/master/mvvm-navigation/src/main/java/com/arthurivanets/mvvm/navigation/MvvmNavHostFragment.kt) should be used as a Navigation Host Fragment of the Host Activity.

<details><summary><b>HostActivity.kt (click to expand)</b></summary>
<p>

````kotlin
import com.arthurivanets.mvvm.navigation.MvvmActivity

class HostActivity : MvvmActivity<ActivityHostBinding, StubViewModel>() {

    private var localViewModel : StubViewModel

    override fun injectDependencies() {
        localViewModel = StubViewModelImpl()
    }

    override fun getLayoutId() : Int {
        return R.layout.activity_host
    }

    override fun getBindingVariable() : Int {
        return BR.viewModel
    }

    override fun getViewModel() : HostActivityViewModel {
        return localViewModel
    }

    override fun getNavigationGraphId() : Int {
        return R.navigation.your_navigation_graph
    }

}
````

</p></details>

> The implementation of the Host Activity should be based upon the Navigation [`MvvmActivity`](https://github.com/arthur3486/android-mvvm/blob/master/mvvm-navigation/src/main/java/com/arthurivanets/mvvm/navigation/MvvmActivity.kt) class.

<details><summary><b>SimpleMvvmFragment.kt (click to expand)</b></summary>
<p>

````kotlin
import com.arthurivanets.mvvm.navigation.MvvmFragment

class SimpleMvvmFragment : MvvmFragment<FragmentSimpleMvvmBinding, SimpleViewModel>() {

    private lateinit var localViewModel : SimpleViewModel

    override fun injectDependencies() {
        // Initialize your View Model here...
        localViewModel = SimpleViewModelImpl()
    }

    // The rest of the Fragment Initialization goes here...

    override fun onRegisterObservables() {
        // Register your ViewModel's observable fields here...
    }

    override fun onHandleCommand(command : Command<*>) {
        // handle the ViewModel-specific command here... (e.g. Restart the Application, Show Toast, etc.)
    }

    override fun onViewStateChanged(state : ViewState<*>) {
        // handle the View State Change here... (adjust your UI correspondingly)
    }

    override fun onRoute(route : Route<*>) {
        // handle the Application Route here... (navigate to the corresponding screen)
    }

    override fun getLayoutId() : Int {
        return R.layout.fragment_simple_mvvm
    }

    override fun getBindingVariable() : Int {
        return BR.viewModel
    }

    override fun getViewModel() : SimpleViewModel {
        return localViewModel
    }

}
````

</p></details>

> The implementation of the MVVM Fragment should be based upon the Navigation [`MvvmFragment`](https://github.com/arthur3486/android-mvvm/blob/master/mvvm-navigation/src/main/java/com/arthurivanets/mvvm/navigation/MvvmFragment.kt) class.

> ***See: [`MvvmActivity`](https://github.com/arthur3486/android-mvvm/blob/master/mvvm-navigation/src/main/java/com/arthurivanets/mvvm/navigation/MvvmActivity.kt), [`MvvmFragment`](https://github.com/arthur3486/android-mvvm/blob/master/mvvm-navigation/src/main/java/com/arthurivanets/mvvm/navigation/MvvmFragment.kt), [`MvvmNavHostFragment`](https://github.com/arthur3486/android-mvvm/blob/master/mvvm-navigation/src/main/java/com/arthurivanets/mvvm/navigation/MvvmNavHostFragment.kt)***

## Navigation Component and Dagger Based Implementation

Shares many implementation-specific aspects with the previously described implementation types and is used in the [`Demo Application`](https://github.com/arthur3486/android-mvvm/tree/master/app/src/main).

> ***See: [`MvvmActivity`](https://github.com/arthur3486/android-mvvm/blob/master/mvvm-navigation-dagger/src/main/java/com/arthurivanets/mvvm/navigation/dagger/MvvmActivity.kt), [`MvvmFragment`](https://github.com/arthur3486/android-mvvm/blob/master/mvvm-navigation-dagger/src/main/java/com/arthurivanets/mvvm/navigation/dagger/MvvmFragment.kt)***

## Compilation

In order to compile the [`app`](https://github.com/arthur3486/android-mvvm/tree/master/app/src/main) module you need to obtain the `PUBLIC_API_KEY` and `PRIVATE_API_KEY` from the [`Marvel API`](https://developer.marvel.com/) portal, which should be saved in either global `gradle.properties` file or the project-specific one thereafter.

***gradle.properties***
````groovy
marvelApiPublicKey=PUBLIC_API_KEY
marvelApiPrivateKey=PRIVATE_API_KEY
````

## Contribution

See the [CONTRIBUTING.md](CONTRIBUTING.md) file.

## Hall of Fame

> Using Android MVVM Library in your app and want it to get listed here? Email me at arthur.ivanets.work@gmail.com!

## License

 Android MVVM Library is licensed under the [Apache 2.0 License](LICENSE).
