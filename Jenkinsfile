pipeline {
    agent any
    tools {
        maven 'maven'
    }
    stages {
        stage('Build and Unit Tests') {
            steps {
                sh 'mvn clean package -Dmaven.test.skip=true'
                sh 'mvn -Dtest=com.worktracker.unit.** test'
            }
        }
        stage('Integration Test and SonarQube analysis') {
            when {
                anyOf {
                    branch 'develop'; branch 'master'
                }
            }
            steps {
                sh 'mvn -Dtest=com.worktracker.integration.** test'
                withSonarQubeEnv('sonarqube') {
                    catchError(buildResult: 'UNSTABLE', stageResult: 'FAILURE') {
                        sh 'mvn sonar:sonar'
                    }
                }
            }
        }
        stage {
            when {
                anyOf {
                    branch 'develop'; branch 'master'
                }
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