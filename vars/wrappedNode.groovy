url: vars.get("registryUrl", "http://localhost:5000")) {
      wrap([$class: 'TimestamperBuildWrapper']) {
        wrap([$class: 'AnsiColorBuildWrapper']) {
          if (body) { body() }
        }
      }
    }
  }
}
