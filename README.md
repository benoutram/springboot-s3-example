# Spring Boot S3 Example #

[![pipeline status](https://gitlab.com/benoutram/springboot-s3-example/badges/master/pipeline.svg)](https://gitlab.com/benoutram/springboot-s3-example/commits/master)

[![GitHub issues](https://img.shields.io/github/issues/benoutram/springboot-s3-example.svg)](https://github.com/benoutram/springboot-s3-example/issues)

[![GitHub forks](https://img.shields.io/github/forks/benoutram/springboot-s3-example.svg)](https://github.com/benoutram/springboot-s3-example/network)

[![GitHub stars](https://img.shields.io/github/stars/benoutram/springboot-s3-example.svg)](https://github.com/benoutram/springboot-s3-example/stargazers)

[![GitHub license](https://img.shields.io/github/license/benoutram/springboot-s3-example.svg)](https://github.com/benoutram/springboot-s3-example/blob/master/LICENSE)

[![Twitter Follow](https://img.shields.io/twitter/follow/benoutram.svg?style=social&label=Follow)](https://twitter.com/intent/follow?screen_name=benoutram)

## What is this repository for? ##

This is an example web application which is a dependency of the [Terraform AWS VPC Example](https://github.com/benoutram/terraform-aws-vpc-example) project. It's used to help visually demonstrate the successful deployment of 
infrastructure and software.

The [Terraform AWS VPC Example](https://github.com/benoutram/terraform-aws-vpc-example) project expects the built 
Spring Boot artifact to reside in Amazon S3 storage which is then fetched during provisioning of web server 
instances and set up to run as a service.

The project has a database dependency to demonstrate database connectivity. There is one sample user in the database:

| Username       | Password |
| -------------- | ---------|
| john@smith.com | password |

## Dependencies ##

  * Java 8
  * Docker Compose
  
Docker Compose is used to start and configure the MySQL database when developing locally by using the Gradle 
`composeUp` task.

## How do I get set up for local development? ##

Install the dependencies.

Stop any locally running MySQL on port 3306.

Pull the MySQL image - `gradlew composePull`.

Start the MySQL container - `gradlew composeUp`.
  
Test that MySQL is running using the MySQL command line client `mysql -uterraform -ppassword -h127.0.0.1 terraform_test_db`.

You are now ready to deploy the application locally.

## Local Deployment ##

`gradlew clean composeUp bootRun`

## Environment profile configuration ##

Configuration of the application has been externalized into the `main/resources/application.properties` file.

In addition to these default properties there is a `local` environment profile used for local development.

Edit `main/resources/application-local.properties` to specify local profile specific values.

These have precedence over those in `application.properties` when the Spring active profile is set.

If launching the JAR directly without Gradle the Spring active profile needs to be set using `-Dspring.profiles.active=local`.

The Gradle build has been configured to set `local` as the Spring active profile automatically.