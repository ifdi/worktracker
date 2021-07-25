pipeline {
    agent any
    tools {
        maven 'maven'
        dockerTool 'docker'
    }
    stages {
        stage('Build and Unit Tests') {
            steps {
                sh 'mvn clean package'
            }
        }
        stage('Integration Test and SonarQube analysis') {
            when {
                branch 'master'
            }
            steps {
                sh 'mvn verify -PIT'
                withSonarQubeEnv('sonarqube')
            }
        }
        stage('SonarQube Quality Gate') {
            when {
                branch 'master'
            }
            steps {
                timeout(time: 1, unit: 'HOURS') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
    }
}
