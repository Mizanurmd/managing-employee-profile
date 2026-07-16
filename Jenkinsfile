pipeline {
    agent any

    options {
        timestamps()
        buildDiscarder(logRotator(
                numToKeepStr: '10',
                artifactNumToKeepStr: '10'
        ))
        skipDefaultCheckout(true)
    }

    environment {
        APP_NAME = 'managing-employee-profile'
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

    }

    post {

        always {
            junit '**/target/surefire-reports/*.xml'
            cleanWs()
        }

        success {
            echo '======================================='
            echo 'BUILD SUCCESSFUL'
            echo "Application : ${APP_NAME}"
            echo "Build No    : ${env.BUILD_NUMBER}"
            echo "Branch      : ${env.BRANCH_NAME}"
            echo '======================================='
        }

        failure {
            echo '======================================='
            echo 'BUILD FAILED'
            echo "Application : ${APP_NAME}"
            echo "Build No    : ${env.BUILD_NUMBER}"
            echo '======================================='
        }
    }
}