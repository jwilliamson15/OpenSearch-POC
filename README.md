# POC - OpenSearch API testing

A proof of concept app to show Cucumber testing against local OpenSearch instance.

# Pre-requisites
 - Run docker compose to create local OpenSearch environment
 - This app assumes default ports
 - All data is loaded by Cucumber (see [Hooks.java](src/test/java/cucumber/Hooks.java))

# Tech Debt
 - Remove Spring which is left over from running Spring Initilzr to create Maven project.
 - Investigate moving test data load to docker file.

### Reference Documentation

OpenSearch Dockerfile downloaded from [OpenSearch Homepage](https://opensearch.org/)
