# Deploy Locally 
* Prerequisites: 
<br>- Install git, mvn, JDK8 and MongoDB
<br>- Deploy parent-pom-sample: https://gitlab.com/kmponis-examples/parent-pom-sample.git
* Open CLI and move to your workspace: 
<br>`> git clone https://gitlab.com/kmponis-examples/web-service-sample.git`
* Open CLI and move to web-service-sample: 
<br>`> mvn clean install`
<br>`> java -jar target/web-service-sample.jar`
* Test Manually on browser:
<br>`> http://localhost:8880/swagger-ui.html#/sample-controller/`
* Test Automatically (Jacoco code coverage (100%)):
<br>- Open CLI and move to web-service-sample:
<br>`> mvn clean test`
<br>- Go to file explorer on directory:
<br>`C:/**/web-service-sample/target/jacoco-reports/index.html`

# Deploy using Docker
* Prerequisites: 
<br>- Install docker and docker-machine on host machine and check the versions.
<br>`> docker --version`
<br>`> docker-machine --version`
<br>- Create docker VM, assign it as default and save IP address (VM_IP)
<br>`> docker-machine create newvm`
<br>`> docker-machine ls`
<br>`> eval $(docker-machine env newvm)`
<br>- Test configuration
<br>`> docker info`
* Build application image
<br>- Make sure you are changing application.properties mongoDB host to VM_IP
<br>`> docker build -t webservicesample -f Dockerfile .`
<br>`> docker image ls`
* Run mongo in background
<br>`> docker pull mongo`
<br>`> docker run -d -p 27027:27017 -t mongo`
* Run image in background
<br>`> docker run -p 8880:8880 webservicesample`
<br>`> docker container ls`
