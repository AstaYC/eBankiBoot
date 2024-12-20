pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'spring-boot-app'
        SONARQUBE_SERVER = 'SonarQube'
    }

    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'main', url: 'https://github.com/your-repo-url.git'
            }
        }

        stage('Build') {
            steps {
                sh './mvnw clean package -DskipTests'
            }
        }

        stage('Code Quality Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh './mvnw sonar:sonar'
                }
            }
        }

        stage('Unit Tests and Coverage') {
            steps {
                sh './mvnw test'
            }
            post {
                always {
                    jacoco execPattern: '**/target/jacoco.exec', classPattern: '**/target/classes', sourcePattern: '**/src/main/java'
                }
            }
        }

        stage('Docker Build and Deploy') {
            steps {
                sh 'docker build -t ${DOCKER_IMAGE} .'
                sh 'docker run -d -p 8080:8080 ${DOCKER_IMAGE}'
            }
        }
    }

    post {
        success {
            emailext subject: 'Build Successful', body: 'The build was successful!', recipientProviders: [[$class: 'DevelopersRecipientProvider']]
        }
        failure {
            emailext subject: 'Build Failed', body: 'The build failed. Please check Jenkins.', recipientProviders: [[$class: 'DevelopersRecipientProvider']]
        }
    }
}
