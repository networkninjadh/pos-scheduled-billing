pipeline {

    agent any

    stages {

        stage('Initialize') {
            steps {
                echo "PATH = ${PATH}"
            }

        }

        stage('Build') {
            steps {
                echo "Build Stage"
                sh 'mvn -D maven.test.failure.ignore=true install'
            }

        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
              post {
                success {
                    junit 'target/surefire-reports/**/*.xml'
                }
              }
        }

        stage('package') {
            steps {
                sh 'mvn clean package'
                sh 'docker build -t pos-scheduled-billing .'
                sh 'docker run --network="host" -d -p8085:8085 pos-scheduled-billing'
            }
        }

    }
}