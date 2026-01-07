pipeline {
    agent any
    
    tools {
        maven 'Maven-3.8'
        jdk 'JDK-11'
    }
    
    environment {
        PROJECT_NAME = 'PipeLine-BOUASSABOuassim'
        AUTHOR = 'BOUASSA Bouassim'
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
                echo 'Étape 3 : Exécution des tests unitaires'
                echo '========================================='
                sh 'mvn test'
                echo 'Tests exécutés avec succès ✓'
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
                echo 'Étape 4 : Création du package JAR'
                echo '========================================='
                sh 'mvn package -DskipTests'
                echo 'Package créé avec succès ✓'
            }
        }
        
        stage('Archive') {
            steps {
                echo '========================================='
                echo 'Étape 5 : Archivage des artifacts'
                echo '========================================='
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                echo 'Artifacts archivés avec succès ✓'
            }
        }
        
        stage('Deploy') {
            steps {
                echo '========================================='
                echo 'Étape 6 : Déploiement de l\'application'
                echo '========================================='
                sh '''
                    echo "Simulation de déploiement..."
                    echo "Application déployée par ${AUTHOR}"
                    echo "Projet : ${PROJECT_NAME}"
                    java -jar target/projet-devops-bouassa-1.0-SNAPSHOT.jar
                '''
                echo 'Déploiement réussi ✓'
            }
        }
        
        stage('Notify Slack') {
            steps {
                echo '========================================='
                echo 'Étape 7 : Notification Slack'
                echo '========================================='
                script {
                    def message = """
                        *Build Réussi!* ✅
                        *Projet:* ${env.JOB_NAME}
                        *Build:* #${env.BUILD_NUMBER}
                        *Auteur:* ${AUTHOR}
                        *Branche:* ${env.GIT_BRANCH}
                    """.stripIndent()
                    
                    slackSend(
                        color: 'good',
                        message: message
                    )
                }
                echo 'Notification envoyée à Slack ✓'
            }
        }
    }
    
    post {
        success {
            echo '========================================='
            echo '✅ PIPELINE EXÉCUTÉ AVEC SUCCÈS!'
            echo '========================================='
            echo "Tous les stages ont été complétés"
            echo "Projet: ${PROJECT_NAME}"
            echo "Build: #${env.BUILD_NUMBER}"
        }
        failure {
            echo '========================================='
            echo '❌ ÉCHEC DU PIPELINE'
            echo '========================================='
            slackSend(
                color: 'danger',
                message: "*Build Échoué!* ❌\n*Projet:* ${env.JOB_NAME}\n*Build:* #${env.BUILD_NUMBER}"
            )
        }
        always {
            echo 'Pipeline terminé - Nettoyage en cours...'
            cleanWs()
        }
    }
}
