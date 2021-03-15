You need redis and a sql server for this program to work
default ports for the api endpoints
sql username and password is root



the tests for this back-end will create a test sql server and delete it once the junit tests are done 
To test you would need to change the properties files to change the schema used and the ddl-auto to create
the changes are already in the properties file you just need to comment them in and comment out the normal properties



this part of the program is just the server and back-end designed as api endpoints that can be used with any front-end.  The front end will be split between a website written with angular and an android app written in kotlin.
