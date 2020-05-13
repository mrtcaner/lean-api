# lean-api

## Components
    
- Jersey, Guice, Hibernate, H2, Lombok
    
  - There is some integration and unit tests. Integration tests are in a different source base and build-helper is used
    to integrate it to project.
    
  - There is failsafe plugin for integration tests and maven-surefire for unit tests. 
    
  - There are jacoco and jacoco-check. Limit for check is on bundle level and 20%.
    
    
## Build & Run

    - mvn clean install
    
     then in project root
     
    - java -jar .\target\lean-api-1.0-SNAPSHOT.jar
    
    application runs on http://localhost:8080
    
    There is postman collection at project root
    
## Test & reports

- There are 3 outputs for test results under target/site folder. Run;
    
    - mvn clean verify
    
    and then;
    
    - jacoco-unit-test-coverage-report
    - jacoco-integration-test-coverage-report
    - jacoco-merged-test-coverage-report
    
    folders will be created. "jacoco-merged-test-coverage-report" folder contains merged results of unit and 
    integrations tests.
    
- There are no test for controllers because there is no logic and there are integration tests.

## Notes

- There are business requirements like tracking car during usage, keeping whereabouts data, decuting payments through queues and more were not
my concerns during implementation but I was still aware of.

