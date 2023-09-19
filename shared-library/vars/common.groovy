def compile() {
  if (env.codeType == "maven") {
    print 'Maven'
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

