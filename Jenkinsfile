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
        
        stage('Build & Package spring app') {
            steps {
                dir('springboot-backend') {
                  sh 'mvn clean '
                  sh 'mvn install -DskipTests '
                }
            }
        }

        stage('Build images of both app') {
            steps {
                dir('springboot-backend') {
                  sh 'docker build -t springboot-backend:$BUILD_NUMBER . '
                }
                
                dir('react-frontend') {
                  sh 'docker build -t react-frontend:$BUILD_NUMBER . '
                }
                
            }
        }

        stage('Push images to hub') {
            steps {
                sh 'echo $dockercredentials_PSW | docker login -u $dockercredentials_USR --password-stdin '
                sh 'docker image tag springboot-backend:$BUILD_NUMBER lugar2020/springboot-backend:$BUILD_NUMBER'
                sh 'docker image push lugar2020/springboot-backend:$BUILD_NUMBER'
                
                sh 'docker image tag react-frontend:$BUILD_NUMBER lugar2020/react-frontend:$BUILD_NUMBER'
                sh 'docker image push lugar2020/react-frontend:$BUILD_NUMBER'
            }
        }

        stage('deploy on kubernetes') {
            steps {
                dir('kubernetes'){
                    sh ''' final_tag=$(echo $BUILD_NUMBER | tr -d ' ') 
                     sed -i "s/docker_tag/$final_tag/g" backend.yml
                    '''

                    sh ''' final_tag=$(echo $BUILD_NUMBER | tr -d ' ') 
                     sed -i "s/docker_tag/$final_tag/g" frontend.yml
                    '''
                }
                dir('ansible') {                  
                  ansiblePlaybook become: true, credentialsId: 'masternodeid', installation: 'ansible', inventory: 'hosts.ini', playbook: 'deploy-playbook.yml'
                }
            }
        }

        stage('Clean up') {
            steps {
                sh 'docker rmi lugar2020/springboot-backend:$BUILD_NUMBER --force'
                sh 'docker rmi springboot-backend:$BUILD_NUMBER --force'
                
                sh 'docker rmi lugar2020/react-frontend:$BUILD_NUMBER --force'
                sh 'docker rmi react-frontend:$BUILD_NUMBER --force'
            }
        }
    }
}
