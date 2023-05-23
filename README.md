# Master Detail App

This app is created as an assignment for an interview

## My approach
I created an offline-first app in the sense that Repository information is stored in 
[a DB](feature/repositories-data/src/main/java/nl/michiel/feature/repositories/data/db) which
is updated by data from the [github API](feature/repositories-data/src/main/java/nl/michiel/feature/repositories/data/api)
at startup. I decided to not store the event data in the DB because it is fine for a user to wait a 
bit to see the activity when opening the details screen.

The implementation is a bit simplistic (because it's not a production app it doesn't make sense to
go to the full 100%). What I skipped is:
- removing deleted repositories from the database (not a very common use case anyway)
- paging the events: Now the app just loads the first 100 events, which is fine in most cases, 
  because only the activity in the last 90 days is available anyway, but for popular repo's we don't
  get all the data. I guess the right way to do it is to use the Paging library, but it impacts
  the preview and unit test code
- I added a sample of [unit tests for the RepoRepository](feature/repositories-data/src/test/java/nl/michiel/feature/repositories/data/RepoRepositoryImplTest.kt)
  but left other classes
- I only did [one UI test](app/src/androidTest/java/nl/michiel/assignment/HappyFlowTest.kt)
- I didn't do any automated view testing, but I made 
  [lots of](feature/repositories-presentation/src/main/java/nl/michiel/feature/repositories/presentation/view/RepoDetailScreen.kt)
  [previews](feature/repositories-presentation/src/main/java/nl/michiel/feature/repositories/presentation/view/RepoListScreen.kt),
  in a production app these can be used for screenshot testing with a library (or when AS will support it out of the box)

## How to build
Just use Android Studio to build the app, or use the gradle wrapper

## Assignment
Create a basic iOS/Android application with showing a
list and detail screen.
Just complete as much as you can before the next
interview we can review the code together.
Assignment:
Using the https://developer.github.com/v3/ old rest api
from GitHub create an app which list the repositories of
user “JakeWharton” and “infinum”.
When the user taps a repository it should open a detail
screen which shows information about the repository owner
(owner:login, owner:url), and selected repo (name, url)
and the last event the repository (type,
actor:display_login, actor:url).
To get repo list for a user
endpoint: https://api.github.com/users/:user/repos
example: https://api.github.com/users/JakeWharton/repos
To fetch the last events on a repo
endpoint: https://api.github.com/repos/:user/:repo/events
example: https://api.github.com/repos/JakeWharton/
butterknife/events
