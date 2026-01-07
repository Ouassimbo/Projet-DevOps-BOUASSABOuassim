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
                message: "✅ Pipeline ${env.JOB_NAME} #${env.BUILD_NUMBER} terminé avec succès !\nDuré>
            )
        }
        failure {
            echo '========================================='
            echo '❌ ÉCHEC DU PIPELINE'
            echo '========================================='
            slackSend(
                channel: '#jenkins',
                color: 'danger',
                messge: "❌ Pipeline ${env.JOB_NAME} #${env.BUILD_NUMBER} a échoué !\nVoir: ${env.BUIL>
            )
        }
    }
}

