pipeline {

    agent any



    environment {

        DOCKER_CREDENTIALS_ID = 'dockerhub-credentials-fnz-id'

        IMAGE_NAME = 'raniabenabdallah11/springboot-backend'

    }



    stages {

        stage('Checkout') {

            steps {

                git branch: 'main', url: 'https://github.com/baccartaoufik/FNZ-TimeTracking-Backend.git'

            }

        }

        stage('Build') {

            steps {

                script {

                    dockerImage = docker.build("${env.IMAGE_NAME}:${env.BUILD_ID}")

                }

            }

        }

        stage('Push') {

            steps {

                script {

                    docker.withRegistry('https://index.docker.io/v1/', "${env.DOCKER_CREDENTIALS_ID}") {

                        dockerImage.push("${env.BUILD_ID}")

                        dockerImage.push('latest')

                    }

                }

            }

        }

        stage('Deploy') {

            steps {

                script {

                    sh 'docker stop springboot-backend || true && docker rm springboot-backend || true && docker run -d --name springboot-backend -p 8080:8080 ${env.IMAGE_NAME}:latest'

                }

            }

        }

    }

}
