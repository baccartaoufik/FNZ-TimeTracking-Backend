pipeline {
    agent any
    triggers {
        githubPush()
    }
    environment {
        MAVEN_SETTINGS = '/usr/share/maven/conf/settings.xml'
        DOCKER_REGISTRY = 'docker.io/raniabenabdallah11'
        IMAGE_NAME = 'timetracking-backend'
        IMAGE_TAG = 'latest'
        SPLUNK_HEC_URL = 'https://172.16.4.16:8088/services/collector/event'
        SPLUNK_HEC_TOKEN = '687ac127-b1bb-4128-bdc9-e9c7d3e7e3a9'
	SONARQUBE_URL = 'http://172.16.4.17:9000'
        SONARQUBE_TOKEN = 'squ_f1a4c50a1f8989164fc530b578e26bd6a069237c'
        SONAR_SCANNER_HOME = tool 'SonarQubeScanner'
    }
    stages {
        stage('Checkout') {
            steps {
                script {
                    echo 'Checking out the source code...'
                    sh "curl -k ${SPLUNK_HEC_URL} -H 'Authorization: Splunk ${SPLUNK_HEC_TOKEN}' -d '{\"event\": \"Checkout started\", \"sourcetype\": \"_json\"}'"
                    checkout scm
                    sh "curl -k ${SPLUNK_HEC_URL} -H 'Authorization: Splunk ${SPLUNK_HEC_TOKEN}' -d '{\"event\": \"Checkout completed\", \"sourcetype\": \"_json\"}'"
                }
            }
        }
	stage('SonarQube Analysis') {
            steps {
		script {
                    // Notify Splunk
                    sh "curl -k ${SPLUNK_HEC_URL} -H 'Authorization: Splunk ${SPLUNK_HEC_TOKEN}' -d '{\"event\": \"SonarQube Analysis started\", \"sourcetype\": \"_json\"}'"
                }
                withSonarQubeEnv('SonarQube') {
                    sh "./mvnw sonar:sonar"
                }
            }
        }
        stage('Quality Gate') {
            steps {
                timeout(time: 1, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Build and Test') {
            steps {
                script {
                    echo 'Building the project...'
                    sh "curl -k ${SPLUNK_HEC_URL} -H 'Authorization: Splunk ${SPLUNK_HEC_TOKEN}' -d '{\"event\": \"Build and Test started\", \"sourcetype\": \"_json\"}'"
                    sh "cat ${env.MAVEN_SETTINGS}"
                    sh "mvn clean package -s ${env.MAVEN_SETTINGS}"
                    sh "curl -k ${SPLUNK_HEC_URL} -H 'Authorization: Splunk ${SPLUNK_HEC_TOKEN}' -d '{\"event\": \"Build and Test completed\", \"sourcetype\": \"_json\"}'"
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    echo 'Building the Docker image...'
                    sh "curl -k ${SPLUNK_HEC_URL} -H 'Authorization: Splunk ${SPLUNK_HEC_TOKEN}' -d '{\"event\": \"Build Docker Image started\", \"sourcetype\": \"_json\"}'"
                    sh 'docker build -t raniabenabdallah11/timetracking-backend:latest .'
                    sh "cat ${env.MAVEN_SETTINGS}"
                    sh "mvn clean install -s ${env.MAVEN_SETTINGS}"
                    sh "curl -k ${SPLUNK_HEC_URL} -H 'Authorization: Splunk ${SPLUNK_HEC_TOKEN}' -d '{\"event\": \"Build Docker Image completed\", \"sourcetype\": \"_json\"}'"
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    echo 'Pushing the Docker image to Docker Hub...'
                    sh "curl -k ${SPLUNK_HEC_URL} -H 'Authorization: Splunk ${SPLUNK_HEC_TOKEN}' -d '{\"event\": \"Push Docker Image started\", \"sourcetype\": \"_json\"}'"
                    withDockerRegistry(credentialsId: 'dockerhub-credentials-fnz-id', url: 'https://index.docker.io/v1/') {
                        sh 'docker push raniabenabdallah11/timetracking-backend:latest'
                    }
                    sh "curl -k ${SPLUNK_HEC_URL} -H 'Authorization: Splunk ${SPLUNK_HEC_TOKEN}' -d '{\"event\": \"Push Docker Image completed\", \"sourcetype\": \"_json\"}'"
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
                    sh "curl -k ${SPLUNK_HEC_URL} -H 'Authorization: Splunk ${SPLUNK_HEC_TOKEN}' -d '{\"event\": \"Deploy Docker Container started\", \"sourcetype\": \"_json\"}'"
                    sh 'docker run -d --name springboot -p 8081:8081 raniabenabdallah11/timetracking-backend:latest'
                    sh "cat ${env.MAVEN_SETTINGS}"
                    sh "mvn deploy -s ${env.MAVEN_SETTINGS}"
                    sh "curl -k ${SPLUNK_HEC_URL} -H 'Authorization: Splunk ${SPLUNK_HEC_TOKEN}' -d '{\"event\": \"Deploy Docker Container completed\", \"sourcetype\": \"_json\"}'"
                }
            }
        }
    }
    
    post {
        success {
            script {
                echo 'Pipeline executed successfully!'
                sh "curl -k ${SPLUNK_HEC_URL} -H 'Authorization: Splunk ${SPLUNK_HEC_TOKEN}' -d '{\"event\": \"Pipeline executed successfully!\", \"sourcetype\": \"_json\"}'"
            }
        }
        failure {
            script {
                echo 'Pipeline failed.'
                sh "curl -k ${SPLUNK_HEC_URL} -H 'Authorization: Splunk ${SPLUNK_HEC_TOKEN}' -d '{\"event\": \"Pipeline failed.\", \"sourcetype\": \"_json\"}'"
            }
        }
    }
}

