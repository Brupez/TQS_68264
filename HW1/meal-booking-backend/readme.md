## SonarQube dashboard

http://127.0.0.1:9001/

## SonarQube inicialization

```bash

docker run -d --name sonarqube -e SONAR_ES_BOOTSTRAP_CHECKS_DISABLE=true -p 9000:9000 sonarqube:latest

mvn clean verify sonar:sonar \
  -Dsonar.projectKey=HW1 \
  -Dsonar.projectName='HW1' \
  -Dsonar.host.url=http://localhost:9001 \
  -Dsonar.token=sqp_622b52d0f238668e374c001a0bcae40c7b952b1b