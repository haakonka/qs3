# QS3
Project for IDATT2105

##INTRO

This application aims to provide a queue system for a students who want to get their assignments approved
by student assistance. The application is focused on having the user being able to "get in line" for getting 
their assignment approved by a student assistant on their phones.

The application also alowees admins to add subject, assignments, and to create new students.
This can by simply importing a csv file of all the students that they want to add to any specific subject.

This application consists 3 components. The frontend which consists of a Vue application,
the backend which consists of a SpringBoot application, and a database. In our case this is a locally hosted
MySQL database which is hosted on a server PC in the apartment of one of the developers.


##Implemented functionality
* Communication to a database
* Responsive frontend information
* Database manipulation through frontend
* Authentication by implementing a login
* Authorization on different API calls to backend application (JWT token)
* Email communication to newly added students

## Future work
* Have secure methode of adding more admins. As of now it can only be done through directly manipulation of the database
* Redo API calls to GET requests rather than POST
* Add possibility to make a student into a student assistant of a given subject
* Have more desktop friendly layout for admin
* Make active queues be responsive through websockets rather than continually spamming the backend with API calls
* Make CSV import not spam the backend with API calls
* Possibility to add a singular student instead of csv importing.
* Add test for frontend. This was not prioritized as we had a limited time constraint. 
* Implement the ability for admin to archive subjects, or delete them.
* Let students 

  


## Dependencies

## Installation Guide And How To Use

## API docs
When running the backend (the SpringBoot application) you can find the api documentation in this swagger link.
http://localhost:8081/swagger-ui/index.html