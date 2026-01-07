pipeline {
    agent any
    
    tools {
        maven 'Maven-3.9'
        jdk 'JDK-11'
    }
    
    environment {
        PROJECT_NAME = 'PipeLine-BOUASSABOuassim'
        AUTHOR = 'BOUASSA Bouassim'
        JAVA_HOME = "${tool 'JDK-11'}"
        PATH = "${env.JAVA_HOME}/bin:${env.PATH}"
    }
    
    stages {
        stage('Checkout') {
            steps {
                echo '========================================='
                echo 'Etape 1 : Recuperation du code depuis GitHub'
                echo '========================================='
                checkout scm
                echo 'Code recupere avec succes'
            }
        }
        
        stage('Verification Environnement') {
            steps {
                echo '========================================='
                echo 'Verification de Java et Maven'
                echo '========================================='
                sh '''
                    echo "JAVA_HOME = $JAVA_HOME"
                    echo "PATH = $PATH"
                    java -version
                    mvn -version
                '''
            }
        }
        
        stage('Build') {
            steps {
                echo '========================================='
                echo 'Etape 2 : Compilation du projet'
                echo '========================================='
                sh 'mvn clean compile'
                echo 'Compilation reussie'
            }
        }
        
        stage('Test') {
            steps {
                echo '========================================='
                echo 'Etape 3 : Execution des tests unitaires'
                echo '========================================='
                sh 'mvn test'
                echo 'Tests executes avec succes'
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
                echo 'Package cree avec succes'
            }
        }
        
        stage('Archive') {
            steps {
                echo '========================================='
                echo 'Etape 5 : Archivage des artifacts'
                echo '========================================='
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                echo 'Artifacts archives avec succes'
            }
        }
        
        stage('Deploy') {
            steps {
                echo '========================================='
                echo 'Etape 6 : Deploiement de l application'
                echo '========================================='
                sh '''
                    echo "Simulation de deploiement..."
                    echo "Application deployee par ${AUTHOR}"
                    echo "Projet : ${PROJECT_NAME}"
                    java -jar target/projet-devops-bouassa-1.0-SNAPSHOT.jar
                '''
                echo 'Deploiement reussi'
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
        always {
            echo 'Pipeline termine - Nettoyage en cours...'
            cleanWs()
        }
        success {
            echo '========================================='
            echo 'SUCCES DU PIPELINE'
            echo '========================================='
            slackSend(
                channel: '#jenkins',
                color: 'good',
                message: "Pipeline ${env.JOB_NAME} #${env.BUILD_NUMBER} termine avec succes"
            )
        }
        failure {
            echo '========================================='
            echo 'ECHEC DU PIPELINE'
            echo '========================================='
            slackSend(
                channel: '#jenkins',
                color: 'danger',
                message: "Pipeline ${env.JOB_NAME} #${env.BUILD_NUMBER} a echoue"
            )
        }
    }
}