pipeline {
    agent any
    
    tools{
        maven '3.8.6'
        nodejs '16.10.0'
    }

    environment{
        dockercredentials=credentials('dockerhubid') 
    }

    stages {

        stage('show directory') {
            steps {
                sh 'ls'
            }
        }
        
        stage('Build & Package') {
            steps {
                sh 'cd springboot-backend'
                sh 'mvn clean '
                sh 'mvn install -DskipTests '
            }
        }

        stage('Build image') {
            steps {
                sh 'docker build -t springboot-backend:$BUILD_NUMBER . '
            }
        }

        stage('Push image') {
            steps {
                sh 'echo $dockercredentials_PSW | docker login -u $dockercredentials_USR --password-stdin '
                sh 'docker image tag springboot-backend:$BUILD_NUMBER lugar2020/springboot-backend:$BUILD_NUMBER'
                sh 'docker image push lugar2020/springboot-backend:$BUILD_NUMBER'
            }
        }
    }
}
