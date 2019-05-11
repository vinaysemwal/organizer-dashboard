contestant_regitration_dashboard

test exercise

Table of Contents

i. Platform versions for app development and testing

ii. Application overview

i. Platform versions for app development and testing:

JDK version: 10.0.1

Spring boot starter version: 2.0.4.RELEASE

Kafka stream version: 2.2.0

Configuration file : config.properties

ii. Application overview:

The application module has the responsibility of pushing server sent events to the clients about the registration of the 
contestants for the bengaluru idol singing competition. The module is to be part of the microservices architecture based
application allowing users to register for the event.
The end users benefitting from the module are the organizers that will be able to check registration metrics based on the 
age of the contestant.
Not included in the scope of this module is the registration of the candidates which would be a separate spring application module
serving the purpose of candidate registration and producing kafka message about the contestant to the topic that will be consumed 
by this application module to calculate the metrics.

The application uses kafka streams for stream processing the topics that registration module will produce.

The SSE endpoint will be dashboard/stream/groups.