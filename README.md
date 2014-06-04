##Remote coverage - an example

This is an example of how you can use the JaCoCo Maven plugin with Sonar to capture and report the execution coverage of Java applications, specifically when the code is executed in a remote process. 

##Why should you be interested?

Ideally, you'd prefer your code to be tested before it goes live and that testing to be automated. Automated end-to-end testing is essential but comes at a cost - how can you make an informed decision about whether you've tested enough?

##Running the example
You will need the following installed:

- Java
- Maven
- SonarQube

Start SonarQube.

Clone this repo and then `cd` into it and execute the build:

	cd /Users/timg/sandboxes/investigations
	git clone git@github.com:timothygordon32/remote-coverage.git
	cd remote-coverage
	mvn install sonar:sonar -Djacoco.outputDir=/Users/timg/sandboxes/investigations/remote-coverage/target

Go into [SonarQube dashboard](http://localhost:9000/) and log in (default credentials are `admin`/`admin`). Select the parent link to go into the project dashboard. You will need to configure widgets and add the 'Integration Tests Coverage' widget under 'Tests' (add it under the 'Unit Tests Coverage' widget), then go 'Back to Dashboard'.

Clicking on coverage information should now also show integration test and combined coverage.
