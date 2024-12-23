pipeline {
    agent any

    tools {
        jdk 'JDK 17'
        maven 'Maven'
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


        stage('Code Quality Analysis') {
            steps {
                withSonarQubeEnv('SonarQubeServer') {
                        sh './mvnw sonar:sonar'
                }
            }
        }

        stage('Unit Tests and Coverage') {
            steps {
                echo "Running Unit Tests and Coverage Analysis"
                script {
                    // Run tests with Maven
                    def testResult = sh(script: "mvn test -Dspring.profiles.active=test", returnStatus: true)

                    // Check test result and act accordingly
                    if (testResult == 0) {
                        echo "Tests passed successfully!"
                    } else {
                        error "Unit tests failed. Please check the logs for details."
                    }
                }
            }
        }


        stage('Manual Approval') {
            steps {
                input 'Deploy to Docker?'
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
                mail to: 'lferda7rad@gmail.com',
                     subject: "Build Successful: ${env.JOB_NAME} [${env.BUILD_NUMBER}]",
                     body: "The build was successful!\n\nCheck it here: ${env.BUILD_URL}"
            }
            failure {
                mail to: 'lferda7rad@gmail.com',
                     subject: "Build Failed: ${env.JOB_NAME} [${env.BUILD_NUMBER}]",
                     body: "The build failed!\n\nCheck it here: ${env.BUILD_URL}"
            }
        }
    }
