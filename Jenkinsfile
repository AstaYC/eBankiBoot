pipeline {
    agent any

    tools {
        jdk 'JDK 17'       // Adjusted to match Jenkins JDK configuration
        maven 'Maven'      // Adjusted to match Jenkins Maven configuration
    }

    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'Pipeline', url: 'https://github.com/AstaYC/eBankiBoot.git'
            }
        }

        stage('Build') {
            steps {
                sh 'ls -l mvnw'  // Add this command here for debugging
                sh 'chmod +x mvnw'  // Ensure mvnw is executable
                sh './mvnw clean package -DskipTests'
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
