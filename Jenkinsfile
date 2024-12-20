pipeline {
    agent any

    tools {
        jdk 'JDK 17'       // Adjusted to match Jenkins JDK configuration
        maven 'Maven'      // Adjusted to match Jenkins Maven configuration
    }

    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'main', url: 'https://github.com/AstaYC/eBankiBoot.git'
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
                sh 'docker build -t spring-boot-app .'
                sh 'docker run -d -p 8080:8080 spring-boot-app'
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
