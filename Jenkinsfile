pipeline {
    agent any
    
    tools {
        maven 'Maven-3.9'
    }
    
    stages {
        stage('Checkout') {
            steps {
                echo '========================================='
                echo 'Etape 1 : Recuperation du code'
                echo '========================================='
                checkout scm
            }
        }
        
        stage('Verification Environnement') {
            steps {
                echo '========================================='
                echo 'Verification de l environnement'
                echo '========================================='
                sh 'java -version'
                sh 'javac -version'
                sh 'mvn -version'
            }
        }
        
        stage('Build') {
            steps {
                echo '========================================='
                echo 'Etape 2 : Compilation du projet'
                echo '========================================='
                sh 'mvn clean compile'
            }
        }
        
        stage('Test') {
            steps {
                echo '========================================='
                echo 'Etape 3 : Execution des tests'
                echo '========================================='
                sh 'mvn test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }
        
        stage('Package') {
            steps {
                echo '========================================='
                echo 'Etape 4 : Creation du package JAR'
                echo '========================================='
                sh 'mvn package -DskipTests'
            }
        }
        
        stage('Archive') {
            steps {
                echo '========================================='
                echo 'Etape 5 : Archivage des artifacts'
                echo '========================================='
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }
        
        stage('Deploy') {
            steps {
                echo '========================================='
                echo 'Etape 6 : Deploiement'
                echo '========================================='
                sh 'echo "Application deployee par BOUASSA Bouassim"'
                sh 'java -jar target/projet-devops-bouassa-1.0-SNAPSHOT.jar'
            }
        }
        
        stage('Notify Slack') {
            steps {
                echo '========================================='
                echo 'Etape 7 : Notification Slack'
                echo '========================================='
                slackSend(
                    channel: '#jenkins',
                    color: 'good',
                    message: "Build reussi! Projet: ${env.JOB_NAME} Build: #${env.BUILD_NUMBER}"
                )
            }
        }
    }
    
    post {
        success {
            echo '========================================='
            echo 'PIPELINE REUSSI!'
            echo '========================================='
            slackSend(
                channel: '#jenkins',
                color: 'good',
                message: "Pipeline ${env.JOB_NAME} #${env.BUILD_NUMBER} termine avec succes"
            )
        }
        failure {
            echo '========================================='
            echo 'PIPELINE ECHOUE'
            echo '========================================='
            slackSend(
                channel: '#jenkins',
                color: 'danger',
                message: "Pipeline ${env.JOB_NAME} #${env.BUILD_NUMBER} a echoue"
            )
        }
        always {
            cleanWs()
        }
    }
}
