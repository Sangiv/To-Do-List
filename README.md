Coverage: 74%
# # To-Do-List

To-Do-List Web Application capable of creating, reading updating and deleting Task Lists and thier Tasks within them. 

## Getting Started

Fork the repo and open as a maven project in your IDE.

### Prerequisites

To make changes, run or develope over this project you will need eclispe IDE and SpringBoot.
Eclipse: https://www.eclipse.org/downloads/packages/installer#:~:text=5%20Steps%20to%20Install%20Eclipse%201%20Download%20the,available%20to%20Eclipse%20users.%20...%20More%20items...%20

SpringBoot:
https://spring.io/tools

### Installing

To get a developement environment running after forking the repo:

1) Clone onto your PC and open in Eclipse (as maven project).
2) Run project as Spring application to use the application.
3) Open a crome tab and navigate to: https://localhost:8901.
4) Alternatively you can change the port you wan to run the web app on by changing the port number in src/main/resources/application.properties.

## Running the tests

To run the automated tests:

1) Open project using eclipse IDE
2) Navigate to src\test\java\com\qa\todo
3) Open up rest, service or selenium folder.
4) rest contains the CRUD Controller Unit and Integration tests.
5) service contains the Unit and Integration tests for the service of each entity.
6) Open the Test Class you'd like to test.
7) Right click on the workspace and select run-> as Junit test.
8) You can also run the whole project as a JUnit test by doin step 7 but right clicking on the IMS-Starter folder instead of the worksapce.
9) Please note you will need the Spring boot app running to run the selenium tests and in extension to execute step 8) properly. 

### Unit Tests 

Controller and Service - Tests the crud ontroller and service layer for the Task/ TaskList Entity and ensure they output as expected for the project.

### Integration Tests

Controller and Service - Testing that the Task/ TaskList service layer and controllers works in communicating with eachother and the repository by using mocked data.

### Selenium Tests

Testing the UI to ensure CRUD functionality in the front end works as is required by the specification.

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Versioning

We use [SemVer](http://semver.org/) for versioning.

## Authors

* **Sangiv Giovanni Karunakaran** - https://github.com/Sangiv

## License

This project is licensed under the MIT license - see the [LICENSE.md](LICENSE.md) file for details 

*For help in [Choosing a license](https://choosealicense.com/)*

## Acknowledgments

* Nick for the support and for troubleshooting some of the testing with me.
