def compile() {
  if (env.codeType == "python" || env.codeType == "static") {
    return "Return, Do not need Compilation"
  }

  stage('Compile Code') {
    if (env.codeType == "maven") {
      sh '/home/centos/maven/bin/mvn package'
    }

    if (env.codeType == "nodejs") {
      sh 'npm install'
    }

  }

}

def test() {
  stage('Test Cases') {
    if (env.codeType == "maven") {
      //sh '/home/centos/maven/bin/mvn test'
      print 'OK'
    }

    if (env.codeType == "nodejs") {
      //sh 'npm test'
      print 'OK'
    }

    if (env.codeType == "python") {
      //sh 'python3.6 -m unittest'
      print 'OK'
    }

  }
}

def codeQuality() {
  stage('Code Quality') {
    sonaruser = sh (script: 'aws ssm get-parameter --name "sonarqube.user" --with-decryption --query="Parameter.Value"', returnStdout: true).trim()
    sonarpass = sh (script: 'aws ssm get-parameter --name "sonarqube.pass" --with-decryption --query="Parameter.Value"', returnStdout: true).trim()
    wrap([$class: "MaskPasswordsBuildWrapper", varPasswordPairs: [[password: sonarpass]]]) {
      sh "sonar-scanner -Dsonar.host.url=http://172.31.89.117:9000 -Dsonar.login=${sonaruser} -Dsonar.password=${sonarpass} -Dsonar.projectKey=${component} -Dsonar.qualitygate.wait=true"
    }
  }
}

def codeSecurity() {
  stage('Code Security') {
    print 'Code Security'
  }
}

def release() {
  stage('Release') {
    print 'Release'
  }
}

