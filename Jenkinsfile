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
        APP_NAME       = 'managing-employee-profile'
        IMAGE_NAME     = 'managing-employee-profile'
        IMAGE_TAG      = "${BUILD_NUMBER}"
        CONTAINER_NAME = 'managing-employee-profile'
        APP_PORT       = '8081'
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Java & Maven Version') {
            steps {
                bat 'java -version'
                bat 'mvn -version'
            }
        }

        stage('Build Application') {
            steps {
                bat 'mvn -B clean package -DskipTests'
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

        stage('Stop Old Container') {
            steps {
                bat '''
                docker stop %CONTAINER_NAME% 2>nul
                docker rm %CONTAINER_NAME% 2>nul
                '''
            }
        }

        stage('Run Docker Container') {
            steps {
                bat '''
                docker run -d ^
                --name %CONTAINER_NAME% ^
                -p %APP_PORT%:%APP_PORT% ^
                --restart unless-stopped ^
                %IMAGE_NAME%:%IMAGE_TAG%
                '''
            }
        }

        stage('Verify Deployment') {
            steps {
                bat 'docker ps'
                bat 'docker images'
                bat 'docker logs --tail 50 %CONTAINER_NAME%'
            }
        }

        stage('Docker Cleanup') {
            steps {
                bat 'docker image prune -f'
            }
        }
    }

    post {

        success {
            echo '=============================================='
            echo ' BUILD SUCCESSFUL '
            echo '=============================================='
            echo "Application : ${APP_NAME}"
            echo "Docker Image: ${IMAGE_NAME}:${IMAGE_TAG}"
            echo "Container   : ${CONTAINER_NAME}"
            echo "Port        : ${APP_PORT}"
            echo "Build No    : ${env.BUILD_NUMBER}"
            echo '=============================================='
        }

        failure {
            echo '=============================================='
            echo ' BUILD FAILED '
            echo '=============================================='
            echo "Application : ${APP_NAME}"
            echo "Build No    : ${env.BUILD_NUMBER}"
            echo '=============================================='
        }

        always {
            junit testResults: '**/target/surefire-reports/*.xml',
                  allowEmptyResults: true

            cleanWs()
        }
    }
}