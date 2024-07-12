pipeline {
    agent any

    triggers {
        githubPullRequests()
    }

    stages {
        stage('Checkout') {
            steps {
                script {
                    echo 'Checking out the source code...'
                    checkout scm
                }
            }
        }

        stage('Build and Test') {
            steps {
                script {
                    echo 'Building the project...'
                    sh 'mvn clean package'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    echo 'Building the Docker image...'
                    sh 'docker build -t raniabenabdallah11/timetracking-backend:latest .'
                }
            }
        }
        
        stage('Push Docker Image') {
            steps {
                script {
                    echo 'Pushing the Docker image to Docker Hub...'
                    withDockerRegistry(credentialsId: 'dockerhub-credentials-fnz-id', url: 'https://index.docker.io/v1/') {
                        sh 'docker push raniabenabdallah11/timetracking-backend:latest'
                    }
                }
            }
        }
        
        stage('Deploy Docker Container') {
            steps {
                script {
                    echo 'Stopping and removing existing Docker container...'
                    sh '''
                    docker stop springboot || true
                    docker rm springboot || true
                    '''
                    echo 'Running the new Docker container...'
                    sh 'docker run -d --name springboot -p 8081:8080 raniabenabdallah11/timetracking-backend:latest'
                }
            }
        }
    }
}

