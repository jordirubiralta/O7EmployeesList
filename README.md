# O7EmployeesList
This repository is for the Outfit7 technical test.

## How to run it
Simply clone the project, sync, compile and install the app.
When the app is launched for the first time, some employees are added on the DDBB by default to see some results on the list. 

## About
This app consists of 3 principal activities:
- **Home**: the home has a view pager with two windows: first one is a employee list with some information about them and the second one show analytics about the registered employees. 
- **Add new employee**: To access this screen we have to click on the floating button on the bottom of home. This activity has a form to complete with some data about the employee (name, birthday, salary and gender). To save the employee all the fields are required. Also there is a cancel button to back to home. 
- **Headers**: when we click to one employee from the list, the app navigate to another screen. The first 5 google searches for the worker's name will appear on that screen. 

## Libraries
**DDBB**:
- Room

**Services**:
- Retrofit2
- Gson
- okhttp3

**Dependency injection**:
- Hilt
