pipeline {
    agent any

    environment {
         DOCKERHUB_CREDENTIALS = credentials('DockerID')
         VERSION = "${env.BUILD_ID}"
    }

    tools {
        maven "Maven"
    }

  stages {

    stage('Maven Build'){
        steps{
        sh 'mvn clean package  -DskipTests'
        }
    }

     stage('Run Tests') {
      steps {
        sh 'mvn test'
      }
    }

    stage('Run Code Coverage') {
          steps {
            sh 'mvn test'
          }
        }

    stage('Docker Build and Push') {
          steps {
              sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
              sh 'docker build -t deexith/restaurant-service:${VERSION} .'
              sh 'docker push deexith/restaurant-service:${VERSION}'
          }
        }


         stage('Cleanup Workspace') {
          steps {
            deleteDir()
          }
        }



        stage('Update Image Tag in GitOps') {
          steps {
             checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[ credentialsId: 'jenkins', url: 'git@github.com:FoodOrdering-Deexith/restaurant-service.git']])
            script {
           sh '''
              sed -i "s/image:.*/image: deexith\\/restaurant-service:${VERSION}/" deployments/deployment.yml
            '''
              sh 'git checkout main'
              sh 'git add .'
              sh 'git commit -m "Update image tag"'
            sshagent(['git-ssh'])
                {
                      sh('git push')
                }
            }
          }
        }

      }
}