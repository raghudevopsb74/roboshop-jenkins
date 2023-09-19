def compile() {
  stage('Compile Code') {
    if (env.codeType == "maven") {
      sh '/home/centos/maven/bin/mvn package'
    }

    if (env.codeType == "nodejs") {
      print 'NodeJS'
    }

    if (env.codeType == "python") {
      print 'pythn'
    }

    if (env.codeType == "static") {
      print 'static'
    }
  }

}

def test() {
  stage('Test Cases') {
    print 'Test'
  }
}

def codeQuality() {
  stage('Code Quality') {
    print 'Code Quality'
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

