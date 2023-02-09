pipeline {
    agent any

    stages {
        stage('Setup') {
            steps {
                echo "Setup phase"
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Publish Report') {
            steps {
                  publishHTML([allowMissing: false, alwaysLinkToLastBuild: false, includes: 'extended-report.html', keepAll: false, reportDir: 'target/surefire-reports/html/', reportFiles: '', reportName: 'HTML Report', reportTitles: ''])
            }
        }
    }
}
