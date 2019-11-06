# FlickrFindr

An Android app that displays photos returned from the Flickr search API

## Project Setup

This project is built with Gradle, the [Android Gradle plugin](http://tools.android.com/tech-docs/new-build-system/user-guide). Follow the steps below to setup the project localy.

* Clone [FlickrFindr](https://github.com/TheDancerCodes/FlickrFindr) inside your working folder.
* Start Android Studio
* Select "Open Project" and select the generated root Project folder
* You may be prompted with "Unlinked gradle project" -> Select "Import gradle project" and select
the option to use the gradle wrapper
* You may also be prompted to change to the appropriate SDK folder for your local machine
* Once the project has compiled -> run the project!

## Setup!

You need to secure your API key  without pushing to remote repository.

Here are the steps:

1. Create a file named `secrets.properties` in the root directory of the project.
2.  Paste your API KEY in secrets.properties using this format
    * `FLICKR_API_KEY=YOUR-API-KEY`
3. Sync the project or Rebuild.

## Screenshots
 ![Home View](art/home.png?raw=true "Home View")
 ![Search View](art/search-view.png?raw=true "Search View")
 ![Detail View](art/detail-view.png?raw=true "Detail View")

