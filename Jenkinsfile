pipeline {
    agent any

    tools {
        jdk 'JDK21'
        maven 'Maven'
    }

    options {
        timestamps()

        skipDefaultCheckout(true)

        buildDiscarder(logRotator(
            numToKeepStr: '10',
            artifactNumToKeepStr: '10'
        ))
    }

    environment {
        APP_NAME = 'managing-employee-profile'
        IMAGE_NAME = 'managing-employee-profile'
        IMAGE_TAG = "${BUILD_NUMBER}"
        CONTAINER_NAME = 'managing-employee-profile'
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean package'
            }
        }

        stage('Archive Artifact') {
            steps {
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }

        stage('Build Docker Image') {
            steps {
                bat '''
                docker build ^
                -t %IMAGE_NAME%:latest ^
                -t %IMAGE_NAME%:%IMAGE_TAG% ^
                .
                '''
            }
        }

        stage('Deploy Docker Container') {
            steps {
                bat '''
                docker rm -f %CONTAINER_NAME% 2>nul

                docker run -d ^
                --name %CONTAINER_NAME% ^
                -p 8081:8081 ^
                --restart unless-stopped ^
                %IMAGE_NAME%:latest
                '''
            }
        }

        stage('Verify Docker') {
            steps {
                bat 'docker images'
                bat 'docker ps'
            }
        }
    }

    post {

        always {
            junit testResults: '**/target/surefire-reports/*.xml',
                  allowEmptyResults: true

            cleanWs()
        }

        success {
            echo '======================================='
            echo 'BUILD SUCCESSFUL'
            echo "Application : ${APP_NAME}"
            echo "Image       : ${IMAGE_NAME}:${IMAGE_TAG}"
            echo "Container   : ${CONTAINER_NAME}"
            echo "Build No    : ${BUILD_NUMBER}"
            echo "Branch      : ${BRANCH_NAME}"
            echo '======================================='
        }

        failure {
            echo '======================================='
            echo 'BUILD FAILED'
            echo "Application : ${APP_NAME}"
            echo "Build No    : ${BUILD_NUMBER}"
            echo '======================================='
        }
    }
}