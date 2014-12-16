# Java TMS SMS Forward Command Example

This repo provides an example of how to write a web service for use by a TMS 
Keyword Forward Command. It contains an Embedded Tomcat Java Web Application 
that calls and transforms the OpenWeatherMap API in order to provide users with
current weather conditions via SMS.

An instance of this sample application is deployed at 
https://tms-fwd-cmd-example-java.herokuapp.com/

## Install It

```
git clone git@github.com:govdelivery/forward_command_example_java.git
cd forward_command_example_java
mvn package
```

## Run It

```
sh target/bin/webapp
```

## Manually Use It

```
curl -X POST http://localhost:8080/weather -d 'sms_body=St.%20Paul'
```

# Configure TMS To Use It

First, you will need an authorization token for your TMS account. If you do not
have one, you can obtain a token by contacting GovDelivery Customer Support.

Next, you will need to add a Command to a Keyword that calls this webservice 
when a user sends the keyword to your TMS phone number. You can either create 
a specific keyword (e.g. "weather"), which will require your users to start 
their text messages with that keyword (e.g. "weather St. Paul"), or you can 
configure the default keyword, so that users simply need to text their city 
name (e.g. "St. Paul").

## Configure TMS via TMS Client

Rake tasks are provided as an example of how to use the TMS Client to 
configure a specific keyword for use with this web application, or to configure
the default keyword for use with this application. To use these Rake tasks, 
first edit the tms section of config/settings.yml as needed. Then run either:

```
TMS_TOKEN=<YourAuthToken> bundle exec rake demo:config_specific_keyword
```

or

```
TMS_TOKEN=<yourAuthToken> bundle exec rake demo:config_default_keyword
```
