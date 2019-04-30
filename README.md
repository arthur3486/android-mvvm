# Android MVVM Library + Comprehensive Demo Application

> ***Android library designed to greatly simplify the implementation process of an MVVM-based application by providing all the means necessary to solve the common problems and avoid the annoying boilerplate code.
<br><br> The library is accompanied by a comprehensive Demo Application that is built around MVVM and Clean Architecture concepts. The Demo Application utilizes such popular libraries as: [RxJava](https://github.com/ReactiveX/RxJava), [Dagger2](https://github.com/google/dagger), [Android Navigation Architecture Component](https://developer.android.com/guide/navigation), [OkHttp](https://github.com/square/okhttp), [Retrofit](https://github.com/square/retrofit), [Room](https://developer.android.com/topic/libraries/architecture/room), [Glide](https://github.com/bumptech/glide).***

**Android MVVM Library** will make the implementation of your MVVM-based application a trivial task thus allowing you to spend more time focusing on other important things.

[ ![Download](https://api.bintray.com/packages/arthurimsacc/maven/mvvm-core/images/download.svg) ](https://bintray.com/arthurimsacc/maven/mvvm-core/_latestVersion)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Platform](https://img.shields.io/badge/platform-Android-green.svg)](http://developer.android.com/index.html)
![](https://img.shields.io/badge/API-21%2B-green.svg?style=flat)

## Contents

* [Demo](#demo)
* [Getting Started](#getting-started)
* [Basic Implementation](#basic-implementation)
* [Dagger Based Implementation](#dagger-based-implementation)
* [Navigation Component Based Implementation](#navigation-component-based-implementation)
* [Navigation Component + Dagger Based Implementation](#navigation-component-+-dagger-based-implementation)
* [Contribution](#contribution)
* [Hall of Fame](#hall-of-fame)
* [License](#license)

## Demo



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

**3. Update your `compileSdkVersion` in the module-level `build.gradle` file to **28+**.**

````groovy
//...
android {
    //...
    compileSdkVersion 28
    //...
}
//...
````

**4. Replace your `com.android.support.appcompat.*` dependency with the new `androidx.appcompat.*` alternative.**

````groovy
//...
dependencies {
    //...
    implementation "androidx.appcompat:appcompat:1.0.1"
    //...
}
//...
````

//TODO RxJava + Android Lifecycle Dependencies

## Basic Implementation

//TODO

## Dagger Based Implementation

//TODO

## Navigation Component Based Implementation

//TODO

## Navigation Component + Dagger Based Implementation

//TODO

## Contribution

See the [CONTRIBUTING.md](CONTRIBUTING.md) file.

## Hall of Fame

> Using ARVI in your app and want it to get listed here? Email me at arthur.ivanets.l@gmail.com!

## License

ARVI is licensed under the [Apache 2.0 License](LICENSE).
