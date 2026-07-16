pipeline {
    agent any

    environment {
        APP_NAME = 'managing-employee-profile'
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/Mizanurmd/managing-employee-profile.git'
            }
        }

        stage('Clean') {
            steps {
                bat 'mvn clean'
            }
        }

        stage('Compile') {
            steps {
                bat 'mvn compile'
            }
        }

        stage('Test') {
            steps {
                bat 'mvn test'
            }
        }

        stage('Package') {
            steps {
                bat 'mvn package'
            }
        }

        stage('Archive Artifact') {
            steps {
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }
    }

    post {
        success {
            echo 'Build Successful'
        }

    post {
        always {
           junit '**/target/surefire-reports/*.xml'
            }
        }

        failure {
            echo 'Build Failed'
        }

        always {
            cleanWs()
        }
    }
}