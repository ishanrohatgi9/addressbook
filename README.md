# AddressBook - CI/CD Pipeline with Jenkins and Docker

A Java web application deployed through an end-to-end CI/CD pipeline using Jenkins and Docker.

The application is built with Maven, tested with JUnit, packaged as a WAR file, and deployed as a Docker container on an AWS EC2 instance. The pipeline is defined in a `Jenkinsfile` and automates the build, image publishing, deployment, and verification process.

## What I Built

The project automates application delivery using Jenkins Pipeline as Code.

```text
Developer
    |
    v
  GitHub
    |
    v
Jenkins Pipeline (Jenkinsfile)
    |
    v
  Compile
    |
    v
   Test
    |
    v
 Package WAR
    |
    v
 Docker Build
    |
    v
 Docker Hub
    |
    v
 Deploy on AWS EC2
    |
    v
 Docker Container
    |
    v
HTTP Verification
```

The pipeline builds the application, creates a Docker image, publishes it to Docker Hub, and deploys the image as a container on the EC2 host.

## Pipeline Stages

The CI/CD workflow is defined in the repository's `Jenkinsfile`.

1. **Compile:** Compile the Java application using Maven.
2. **Test:** Run the application's JUnit tests.
3. **Package:** Package the application as a WAR file.
4. **Docker Build:** Build a Docker image containing the application.
5. **Docker Push:** Publish the image to Docker Hub.
6. **Deploy:** Run the application container on the EC2 deployment host.
7. **Verification:** Check the application endpoint over HTTP.

## Docker Deployment

The application is packaged as a WAR and deployed inside a Docker container.

The pipeline builds and publishes the Docker image to Docker Hub. The deployment stage uses the image to run the application on AWS EC2.

The container is named `addressbook`, with host port `8080` mapped to container port `8080`.

The application is available at:

```text
http://<EC2-PUBLIC-IP>:8080/addressbook/
```

The deployment process includes an HTTP check to verify that the application responds after deployment.

## Application

The application is a Java AddressBook web application.

It contains:

- Contact management classes
- AddressBook logic
- Web UI
- Maven build configuration
- JUnit tests

The application is packaged as:

```text
target/addressbook.war
```

## Technologies

- Java
- Maven
- JUnit
- Git and GitHub
- Jenkins Pipeline
- Jenkinsfile
- Docker
- Docker Hub
- Apache Tomcat
- AWS EC2
- Linux
- Bash
- CI/CD

## Screenshots

### Jenkins Pipeline

![Jenkins Pipeline](docs/jenkins-pipeline.png)

### Docker Hub Image

![Docker Hub Image](docs/dockerhub-image.png)

### Running Docker Container

![Docker Container](docs/docker-container.png)

### Deployment Verification

![Deployment Verification](docs/deployment-verification.png)

### Application

![AddressBook Application](addressbook_screenshot.png)

## Project Structure

```text
addressbook/
├── src/
│   ├── main/
│   └── test/
├── Dockerfile
├── Jenkinsfile
├── pom.xml
├── .gitignore
├── addressbook_screenshot.png
└── docs/
```

## What I Learned

- Defining CI/CD workflows using a Jenkinsfile
- Automating Maven builds and JUnit test execution
- Building and publishing Docker images
- Deploying containerized applications on AWS EC2
- Configuring Docker port mappings
- Verifying deployments through HTTP checks
- Separating application build and deployment stages

## Future Improvements

- Add automated image vulnerability scanning.
- Improve deployment rollback handling.
- Integrate pipeline notifications for build and deployment failures.
