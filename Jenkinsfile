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
                withSonarQubeEnv('sonarqube') {
                    catchError(buildResult: 'UNSTABLE', stageResult: 'FAILURE') {
                        sh 'mvn sonar:sonar'
                    }
                }
            }
        }
        stage('SonarQube Quality Gate') {
            when {
                branch 'master'
            }
            steps {
                timeout(time: 1, unit: 'HOURS') {
                    catchError(buildResult: 'UNSTABLE', stageResult: 'FAILURE') {
                        waitForQualityGate abortPipeline: false
                    }
                }
            }
        }
    }
}
