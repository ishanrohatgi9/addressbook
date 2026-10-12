pipeline {

    agent {
        label 'AgentB'
    }

    environment {
        DOCKER_IMAGE   = 'ishanrohatgi/addressbook'
        CONTAINER_NAME = 'addressbook'
        HOST_PORT      = '8080'
        CONTAINER_PORT = '8080'
    }

    stages {

        stage('Compile') {
            steps {
                sh '''
                    echo "===== COMPILE ====="
                    mvn -B clean compile
                '''
            }
        }

        stage('Test') {
            steps {
                sh '''
                    echo "===== TEST ====="
                    mvn -B test
                '''
            }
        }

        stage('Package') {
            steps {
                sh '''
                    echo "===== PACKAGE ====="
                    mvn -B package -DskipTests
                    ls -lh target/addressbook.war
                '''
            }
        }

        stage('Docker Build') {
            steps {
                sh '''
                    echo "===== DOCKER BUILD ====="

                    docker build \
                        -t ${DOCKER_IMAGE}:${BUILD_NUMBER} \
                        -t ${DOCKER_IMAGE}:latest \
                        .

                    echo "===== BUILD COMPLETE ====="
                    docker images ${DOCKER_IMAGE}
                '''
            }
        }

        stage('Docker Login and Push') {
            steps {
                withCredentials([
                    usernamePassword(
                        credentialsId: 'dockerhub-creds',
                        usernameVariable: 'DOCKER_USERNAME',
                        passwordVariable: 'DOCKER_PASSWORD'
                    )
                ]) {
                    sh '''
                        set -e

                        echo "===== DOCKER LOGIN ====="

                        echo "$DOCKER_PASSWORD" | docker login \
                            -u "$DOCKER_USERNAME" \
                            --password-stdin

                        echo "===== DOCKER PUSH ====="

                        docker push "${DOCKER_IMAGE}:${BUILD_NUMBER}"
                        docker push "${DOCKER_IMAGE}:latest"

                        echo "===== IMAGE PUSHED SUCCESSFULLY ====="

                        docker logout
                    '''
                }
            }
        }

        stage('Deploy Container') {
            steps {
                sh '''
                    set -e

                    echo "===== DOCKER DEPLOY ====="

                    docker rm -f ${CONTAINER_NAME} || true

                    docker pull ${DOCKER_IMAGE}:${BUILD_NUMBER}

                    docker run -d \
                        --name ${CONTAINER_NAME} \
                        --restart unless-stopped \
                        -p ${HOST_PORT}:${CONTAINER_PORT} \
                        ${DOCKER_IMAGE}:${BUILD_NUMBER}

                    echo "===== CONTAINER STATUS ====="

                    docker ps --filter name=${CONTAINER_NAME}
                '''
            }
        }
    }
}
