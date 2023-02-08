pipeline {
    agent any

    stages {
        stage('Setup') {
            steps {
                echo "Hello"
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean install'
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
