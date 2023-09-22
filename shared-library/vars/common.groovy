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
    env.sonaruser = sh (script: 'aws ssm get-parameter --name "sonarqube.user" --with-decryption --query="Parameter.Value" |xargs', returnStdout: true).trim()
    env.sonarpass = sh (script: 'aws ssm get-parameter --name "sonarqube.pass" --with-decryption --query="Parameter.Value" |xargs', returnStdout: true).trim()
    wrap([$class: "MaskPasswordsBuildWrapper", varPasswordPairs: [[password: sonarpass]]]) {
      if(env.codeType == "maven") {
        //sh 'sonar-scanner -Dsonar.host.url=http://172.31.89.117:9000 -Dsonar.login=${sonaruser} -Dsonar.password=${sonarpass} -Dsonar.projectKey=${component} -Dsonar.qualitygate.wait=true -Dsonar.java.binaries=./target'
        print 'OK'
      }else {
        //sh 'sonar-scanner -Dsonar.host.url=http://172.31.89.117:9000 -Dsonar.login=${sonaruser} -Dsonar.password=${sonarpass} -Dsonar.projectKey=${component} -Dsonar.qualitygate.wait=true'
        print 'OK'
      }
    }
  }
}

def codeSecurity() {
  stage('Code Security') {
    print 'Code Security'

    // IN code security we will generally used SAST & SCA checks
    // You can say that your company is using checkmarx sast and checkmarx sca for this,
  }
}

def release() {
  stage('Release') {
    env.nexususer = sh (script: 'aws ssm get-parameter --name "nexus.username" --with-decryption --query="Parameter.Value" |xargs', returnStdout: true).trim()
    env.nexuspass = sh (script: 'aws ssm get-parameter --name "nexus.password" --with-decryption --query="Parameter.Value" |xargs', returnStdout: true).trim()
    wrap([$class: "MaskPasswordsBuildWrapper", varPasswordPairs: [[password: nexuspass]]]) {
      if(env.codeType == "nodejs") {
        sh 'zip -r ${component}-${TAG_NAME}.zip server.js node_modules'
      } else if(env.codeType == "maven") {
        sh 'cp target/${component}-1.0.jar ${component}.jar; zip -r ${component}-${TAG_NAME}.zip ${component}.jar'
      } else {
        sh 'zip -r ${component}-${TAG_NAME}.zip *'
      }

      sh 'curl -v -u ${nexususer}:${nexuspass} --upload-file ${component}-${TAG_NAME}.zip http://172.31.24.24:8081/repository/${component}/${component}-${TAG_NAME}.zip'
    }
  }
}

