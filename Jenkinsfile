pipeline {
    agent any
    
    tools {
        maven 'Maven-3.9'
        jdk 'JDK-11'
    }
    
    stages {
        stage('Checkout') {
            steps {
                echo '========================================='
                echo 'Étape 1 : Récupération du code depuis GitHub'
                echo '========================================='
                checkout scm
                echo 'Code récupéré avec succès ✓'
            }
        }
        
        stage('Vérification Environnement') {
            steps {
                echo '========================================='
                echo 'Vérification des outils'
                echo '========================================='
                script {
                    def javaHome = tool name: 'JDK-11', type: 'jdk'
                    env.JAVA_HOME = "${javaHome}"
                    env.PATH = "${javaHome}/bin:${env.PATH}"
                }
                sh 'echo "JAVA_HOME = $JAVA_HOME"'
                sh 'echo "PATH = $PATH"'
                sh 'which java'
                sh 'java -version'
                sh 'mvn -version'
            }
        }
        
        stage('Build') {
            steps {
                echo '========================================='
                echo 'Étape 2 : Compilation du projet'
                echo '========================================='
                sh 'mvn clean compile'
                echo 'Compilation réussie ✓'
            }
        }
        
        stage('Test') {
            steps {
                echo '========================================='
                echo 'Étape 3 : Exécution des tests'
                echo '========================================='
                sh 'mvn test'
                echo 'Tests exécutés ✓'
            }
        }
        
        stage('Package') {
            steps {
                echo '========================================='
                echo 'Étape 4 : Création du package JAR'
                echo '========================================='
                sh 'mvn package -DskipTests'
                echo 'Package créé ✓'
            }
        }
        
        stage('Archive') {
            steps {
                echo '========================================='
                echo 'Étape 5 : Archivage des artefacts'
                echo '========================================='
                archiveArtifacts artifacts: '**/target/*.jar', allowEmptyArchive: true
                echo 'Artefacts archivés ✓'
            }
        }
        
        stage('Deploy') {
            steps {
                echo '========================================='
                echo 'Étape 6 : Déploiement (simulation)'
                echo '========================================='
                echo 'Déploiement simulé ✓'
            }
        }
        
        stage('Notify Slack') {
            steps {
                echo '========================================='
                echo 'Étape 7 : Notification Slack'
                echo '========================================='
                slackSend(
                    channel: '#jenkins',
                    color: 'good',
                    message: "✅ Build #${env.BUILD_NUMBER} réussi ! - ${env.JOB_NAME}"
                )
            }
        }
    }
    
    post {
        always {
            echo 'Pipeline terminé - Nettoyage en cours...'
            cleanWs()
        }
        success {
            echo '========================================='
            echo '✅ SUCCÈS DU PIPELINE'
            echo '========================================='
            slackSend(
                channel: '#jenkins',
                color: 'good',
                message: "✅ Pipeline ${env.JOB_NAME} #${env.BUILD_NUMBER} terminé avec succès !\nDurée: ${currentBuild.durationString}"
            )
        }
        failure {
            echo '========================================='
            echo '❌ ÉCHEC DU PIPELINE'
            echo '========================================='
            slackSend(
                channel: '#jenkins',
                color: 'danger',
                message: "❌ Pipeline ${env.JOB_NAME} #${env.BUILD_NUMBER} a échoué !\nVoir: ${env.BUILD_URL}"
            )
        }
    }
}