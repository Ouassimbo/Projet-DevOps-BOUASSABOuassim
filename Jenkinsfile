pipeline {
    agent any
    
    tools {
        maven 'Maven-3.9'
        jdk 'JDK-11'
    }
    
    environment {
        JAVA_HOME = tool 'JDK-11'
        PATH = "${JAVA_HOME}/bin:${env.PATH}"
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
                echo 'Verification Java et Maven'
                echo '========================================='
                sh '''
                    export JAVA_HOME=${JAVA_HOME}
                    export PATH=${JAVA_HOME}/bin:${PATH}
                    echo "JAVA_HOME = ${JAVA_HOME}"
                    ${JAVA_HOME}/bin/java -version
                    ${JAVA_HOME}/bin/javac -version
                    mvn -version
                '''
            }
        }
        
        stage('Build') {
            steps {
                echo '========================================='
                echo 'Compilation'
                echo '========================================='
                sh '''
                    export JAVA_HOME=${JAVA_HOME}
                    export PATH=${JAVA_HOME}/bin:${PATH}
                    mvn clean compile
                '''
            }
        }
        
        stage('Test') {
            steps {
                echo '========================================='
                echo 'Tests'
                echo '========================================='
                sh '''
                    export JAVA_HOME=${JAVA_HOME}
                    export PATH=${JAVA_HOME}/bin:${PATH}
                    mvn test
                '''
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
                echo 'Package'
                echo '========================================='
                sh '''
                    export JAVA_HOME=${JAVA_HOME}
                    export PATH=${JAVA_HOME}/bin:${PATH}
                    mvn package -DskipTests
                '''
            }
        }
        
        stage('Archive') {
            steps {
                echo '========================================='
                echo 'Archive'
                echo '========================================='
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }
        
        stage('Deploy') {
            steps {
                echo '========================================='
                echo 'Deploy'
                echo '========================================='
                sh '''
                    export JAVA_HOME=${JAVA_HOME}
                    export PATH=${JAVA_HOME}/bin:${PATH}
                    echo "Application deployee par BOUASSA Bouassim"
                    ${JAVA_HOME}/bin/java -jar target/projet-devops-bouassa-1.0-SNAPSHOT.jar
                '''
            }
        }
        
        stage('Notify Slack') {
            steps {
                echo 'Notification Slack'
                slackSend(
                    channel: '#jenkins',
                    color: 'good',
                    message: "Build ${env.BUILD_NUMBER} reussi!"
                )
            }
        }
    }
    
    post {
        success {
            echo 'SUCCES!'
            slackSend(
                channel: '#jenkins',
                color: 'good',
                message: "Pipeline ${env.JOB_NAME} #${env.BUILD_NUMBER} reussi"
            )
        }
        failure {
            echo 'ECHEC!'
            slackSend(
                channel: '#jenkins',
                color: 'danger',
                message: "Pipeline ${env.JOB_NAME} #${env.BUILD_NUMBER} echoue"
            )
        }
        always {
            cleanWs()
        }
    }
}
