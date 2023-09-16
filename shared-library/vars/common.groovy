def compile() {
  if (env.codetype == "maven") {
    print 'Maven'
  }

  if (env.codetype == "nodejs") {
    print 'NodeJS'
  }

  if (env.codetype == "python") {
    print 'pythn'
  }

  if (env.codetype == "static") {
    print 'static'
  }
}

