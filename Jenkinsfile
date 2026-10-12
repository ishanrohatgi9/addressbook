pipeline {
	
	agent {
		label 'AgentB'
	}

	environment {
		DOCKER_IMAGE = 'ishanrohatgi/addressbook'
		CONTAINER_NAME = 'addressbook'
		HOST_PORT = '8080'
		CONTAINER_PORT = '8080'
	}	

	stages {

		stage('Compile') {
			sh '''
				echo "====COMPILE====="
				mvn -B clean compile
			'''
		}


		stage('Test') {
			sh '''
				echo "====TEST===="
				mvn -B test
			'''
		}

		stage('Package') {
			sh ''' 
				echo "=====PACKAGE======"
				mvn -B package -DskipTests
				ls -lh target/addressbook.war
			'''
		}

		stage('Docker Build') {
			sh '''
				echo "====DOCKER BUILD===="

				docker build \
				 -t ${DOCKER_IMAGE} : ${BUILD_NUMBER} \
				 -t ${DOCKER_IMAGE} : latest \
			'''
		}

		stage('Docker login and push') {
			steps {
				withCredentials([
					usernamePassword(
						credentialsId: 'dockerhub-creds'
						usernameVariable: 'DOCKER_USERNAME'
						passwordVariable: 'DOCKER_PASSWORD' 
					)
				]) {
					sh '''
						echo "====DOCKER LOGIN===="

						echo "$DOCKER_PASSWORD" | docker login \
							-u "$DOCKER_USERNAME" \
							--password-stdin 

						echo "====DOCKER PUSH===="
						docker push $(DOCKER_IMAGE):$(BUILD_NUMBER)
						docker push $(DOCKER_IMAGE): latest

						docker logout
					'''
				}
			}
		}

		stage('Deploy Container') {
			steps {
				sh '''	
					echo "====DOCKER DEPLOY CONTAINER====''
					docker rm -f ${CONTAINER_NAME} || true
					docker pull ${DOCKER_IMAGE}:${BUILD_NUMBER}

					docker run -d \
					--name ${CONTAINER_NAME} \
					-p ${HOST_PORT}:${CONTAINER_PORT} \
					${DOCKER_IMAGE}:${BUILD_NUMBER}

					echo "====CONTAINER===="

					docker ps 
				'''
			}
			
		}

	}
}	

