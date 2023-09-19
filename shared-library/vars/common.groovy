def compile() {
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

