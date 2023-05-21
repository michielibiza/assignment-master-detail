# Master Detail App

This app is created as an assignment for an interview

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
