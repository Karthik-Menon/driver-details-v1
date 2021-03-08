# driver-details-v1
API to handle driver details

## Prerequisites
Maven version - apache-maven-3.6.3
Java 8

URL: /driver/create 
An endpoint to allow new drivers to be created and stored. 
This is a POST request, with a body containing a JSON object containing the driver first name, last name and date of birth in key value pairs, 
Request - { "firstname": "John", "lastname": "Smith", "date_of_birth": "22-11-1993" }


URL: /drivers
A GET endpoint which returns a list of all existing drivers in JSON format

URL: /drivers/byDate?date=<date> 
A GET endpoint which returns a list of all drivers created after the specified date. 

Build command
mvn clean install

Run command
mvn spring-boot:run
