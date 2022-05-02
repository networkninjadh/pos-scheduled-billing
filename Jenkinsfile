pipeline {

    agent any
    environment {
        dockerhub=credentials('dockerhub')
    }

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
                sh 'docker build -t networkninjadh/pos-scheduled-billing .'
                sh 'echo $dockerhub_PSW | docker login -u $dockerhub_USR --password-stdin'
                sh 'docker push networkninjadh/pos-scheduled-billing:latest'
            }
        }

    }
}