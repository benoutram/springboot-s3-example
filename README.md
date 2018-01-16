# Spring Boot S3 Example #

[![pipeline status](https://gitlab.com/benoutram/springboot-s3-example/badges/master/pipeline.svg)](https://gitlab.com/benoutram/springboot-s3-example/commits/master)

[![GitHub issues](https://img.shields.io/github/issues/benoutram/springboot-s3-example.svg)](https://github.com/benoutram/springboot-s3-example/issues)

[![GitHub forks](https://img.shields.io/github/forks/benoutram/springboot-s3-example.svg)](https://github.com/benoutram/springboot-s3-example/network)

[![GitHub stars](https://img.shields.io/github/stars/benoutram/springboot-s3-example.svg)](https://github.com/benoutram/springboot-s3-example/stargazers)

[![GitHub license](https://img.shields.io/github/license/benoutram/springboot-s3-example.svg)](https://github.com/benoutram/springboot-s3-example/blob/master/LICENSE)

[![Twitter Follow](https://img.shields.io/twitter/follow/benoutram.svg?style=social&label=Follow)](https://twitter.com/intent/follow?screen_name=benoutram)

## What is this repository for? ##

This is an example web application which is a dependency of the [Terraform AWS VPC Example](https://github.com/benoutram/terraform-aws-vpc-example) project. It's used to help visually demonstrate the successful deployment of infrastructure and software. It has a [GitLab](https://gitlab.com) `.gitlab-ci.yml` CI configuration file which can be used to build the project and copying it to Amazon S3 storage.

The [Terraform AWS VPC Example](https://github.com/benoutram/terraform-aws-vpc-example) project expects the built 
Spring Boot artifact to reside in Amazon S3 storage which is then fetched during provisioning of web server 
instances and set up to run as a service.

The project has a database dependency to demonstrate database connectivity. There is one sample user in the database:

| Username       | Password |
| -------------- | ---------|
| john@smith.com | password |

## Deployment to S3 ##

The [GitLab](https://gitlab.com) `.gitlab-ci.yml` CI configuration file can be used to build the project and copy it to a S3 bucket.

The file uses the `cp` command of the [AWS CLI](http://docs.aws.amazon.com/cli/latest/reference/s3/cp.html) to copy the file.

Read more about getting started with GitLab CI/CD  [here](https://docs.gitlab.com/ee/ci/).

The configuration depends on the following secret variables which need to be configured in GitLab CI / CD Settings:

- AWS_ACCESS_KEY_ID
- AWS_SECRET_ACCESS_KEY
- SPRING_DATASOURCE_PASSWORD

The AWS CLI expects the `AWS_ACCESS_KEY_ID` and `AWS_SECRET_ACCESS_KEY` to be populated with the Access Key ID and Secret Access Key of an AWS IAM user that has programmatic access enabled.

Configure the IAM user with a policy to allow copying files to the S3 bucket. In this example policy file the bucket name is `springboot-s3-example`. Replace this with your bucket name.

```json
{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Action": [
                "s3:PutObject",
                "s3:PutObjectAcl"
            ],
            "Resource": [
                "arn:aws:s3:::springboot-s3-example/*"
            ],
            "Effect": "Allow"
        }
    ]
}
```

`SPRING_DATASOURCE_PASSWORD` is required during the testing stage. It should be a random password that will be the MySQL password for the Terraform user account used by the web application.

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