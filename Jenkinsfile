pipeline {
    agent any
    triggers {
    eventTrigger jmespathQuery(
        "((action == 'opened' || action == 'reopened' || action == 'synchronize') && pull_request.base.ref == '${githubBranch}' && contains(repository.clone_url, '${githubRepo}'))"
    )
}
    stages {
        stage('Checkout') {
            steps {
                script {
                    checkout scm
                }
            }
        }

        stage('Build and Test') {
            steps {
                script {
                    sh 'mvn clean package'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    sh 'docker build -t raniabenabdallah11/timetracking-backend:latest .'
                }
            }
        }
        
        stage('Push Docker Image') {
            steps {
                script {
                    withDockerRegistry(credentialsId: 'dockerhub-credentials-fnz-id', url: 'https://index.docker.io/v1/') {
                        sh 'docker push raniabenabdallah11/timetracking-backend:latest'
                    }
                }
            }
        }
        
        stage('Deploy Docker Container') {
            steps {
                script {
                    sh '''
                    docker stop springboot || true
                    docker rm springboot || true
                    docker run -d --name springboot -p 8081:8080 raniabenabdallah11/timetracking-backend:latest
                    '''
                }
            }
        }
    }
}
