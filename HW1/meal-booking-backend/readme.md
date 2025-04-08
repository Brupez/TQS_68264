## SonarQube dashboard

http://127.0.0.1:9001/

## SonarQube inicialization

```bash

docker run -d --name sonarqube -e SONAR_ES_BOOTSTRAP_CHECKS_DISABLE=true -p 9000:9000 sonarqube:latest

sudo mvn clean verify sonar:sonar \
  -Dsonar.projectKey=HW1 \
  -Dsonar.projectName='HW1' \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.token=sqp_337d43d121b527eb7c192ede54a6d6c027c7c38d