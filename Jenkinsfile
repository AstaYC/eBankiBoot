pipeline {
    agent any

    tools {
        jdk 'Java 11'
        maven 'Maven 3.6.3'
    }

    environment {
        DOCKER_IMAGE = 'your-dockerhub-username/ebankify'
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out code...'
                git branch: 'main', credentialsId: 'github-credentials-id', url: 'https://github.com/your-username/ebankify.git'
            }
        }

        stage('Build') {
            steps {
                echo 'Building project...'
                sh 'mvn clean package'
            }
        }

        stage('Unit Tests') {
            steps {
                echo 'Running tests...'
                sh 'mvn test'
            }
        }

        stage('Code Analysis') {
            steps {
                echo 'Analyzing code with SonarQube...'
                withSonarQubeEnv('SonarQube') {
                    sh 'mvn sonar:sonar'
                }
            }
        }

        stage('Docker Build and Push') {
            steps {
                echo 'Building Docker image...'
                sh 'docker build -t ${DOCKER_IMAGE} .'
                echo 'Pushing Docker image to DockerHub...'
                sh 'docker push ${DOCKER_IMAGE}'
            }
        }

        stage('Deploy') {
            steps {
                echo 'Deploying application...'
                input message: 'Deploy to production?', ok: 'Deploy'
                sh 'docker run -p 8081:8080 ${DOCKER_IMAGE}'
            }
        }
    }

    post {
        success {
            echo 'Build and deployment successful!'
        }
        failure {
            echo 'Build failed. Please check the logs.'
        }
    }
}
