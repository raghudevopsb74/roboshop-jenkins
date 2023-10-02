def call(){
  pipeline {
    agent any

    parameters {
      string(name: 'COMPONENT', defaultValue: '', description: 'Which Component to Deploy?')
      string(name: 'VERSION', defaultValue: '', description: 'Which Version to Deploy?')
      string(name: 'ENV', defaultValue: '', description: 'Which Environment to Deploy?')
    }

    stage {
      stage('Deploy') {
        steps {
          echo 'OK'
        }
      }
    }

  }
}