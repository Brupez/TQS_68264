## SonarQube dashboard

http://127.0.0.1:9001/

## SonarQube inicialization

```bash
mvn clean verify sonar:sonar  
 -Dsonar.projectKey=HW1   
 -Dsonar.projectName='HW1'   
 -Dsonar.host.url=http://127.0.0.1:9001   
 -Dsonar.token={your_token}