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

        HOST_PORT      = '8081'
        CONTAINER_PORT = '8081'
    }

    stages {

        stage('Checkout Source') {
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
                bat 'mvn clean package -DskipTests'
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

        stage('Remove Old Container') {
            steps {
                bat '''
                docker rm -f %CONTAINER_NAME% >nul 2>&1
                exit /b 0
                '''
            }
        }

        stage('Run Docker Container') {
            steps {
                bat '''
                docker run -d ^
                --name %CONTAINER_NAME% ^
                -p %HOST_PORT%:%CONTAINER_PORT% ^
                --restart unless-stopped ^
                %IMAGE_NAME%:%IMAGE_TAG%
                '''
            }
        }

        stage('Verify Deployment') {
            steps {
                bat 'docker ps -a'
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
            echo 'BUILD SUCCESSFUL'
            echo '=============================================='
            echo "Application : ${APP_NAME}"
            echo "Docker Image: ${IMAGE_NAME}:${IMAGE_TAG}"
            echo "Container   : ${CONTAINER_NAME}"
            echo "Host Port   : ${HOST_PORT}"
            echo "Build No    : ${BUILD_NUMBER}"
            echo '=============================================='
        }

        failure {
            echo '=============================================='
            echo 'BUILD FAILED'
            echo '=============================================='

            bat 'docker ps -a'
            bat 'docker logs %CONTAINER_NAME% || exit /b 0'

            echo "Application : ${APP_NAME}"
            echo "Build No    : ${BUILD_NUMBER}"
            echo '=============================================='
        }

        always {
            junit allowEmptyResults: true,
                  testResults: '**/target/surefire-reports/*.xml'

            cleanWs()
        }
    }
}