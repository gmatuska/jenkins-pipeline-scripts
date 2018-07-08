def call(Map vars, Closure body=null) {
  vars = vars ?: [:]
  node {
    WithDockerRegistry(url: vars.get("registryUrl", "localhost:5000")) {
      wrap([$class: 'TimestamperBuildWrapper']) {
        wrap([$class: 'AnsiColorBuildWrapper']) {
          if (body) { body() }
        }
      }
    }
  }
}
