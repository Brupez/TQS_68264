# Exercise f)

## $ mvn test
- run only unit tests in project

## $ mvn package
- Compiles code
- runs tests
- packages the compiled code into a JAR file

## $ mvn package -DskipTests=true
- Same as above but skips execution of tests

## $ mvn failsafe:integration-test
- integration tests requiring for the app to be fully built and deployed

## $ mvn install
- Compiles
- Run tests
- Packages code
- Installs the package into the local Maven Repository (making available for other projects in the same machine)

