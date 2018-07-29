def call(Map vars, Closure body=null) {
  vars = vars ?: [:]
  node {
    withDockerRegistry(url: vars.get("registryUrl", "localhost:5000")) {
      wrap([$class: 'TimestamperBuildWrapper']) {
        wrap([$class: 'AnsiColorBuildWrapper']) {
          if (vars.get('cleanWorkspace', false)) {
            // NOTE: `withChownWorkspace` uses docker. if our `label` doesn't have docker
            // or is misconfigured, these operations will fail and the exception will be
            // propogated.
            withChownWorkspace { echo "cleanWorkspace: Ensuring workspace is owned by ${env.USER}" }
            echo "Removing all docker containers"
            try {
              sh("for cid in \$(docker container ls -aq); do docker container rm -vf \$cid; done")
            } catch (Exception e) {
              println(e)
            }
            echo "Docker containers have been removed"

            echo "cleanWorkspace: Removing existing workspace"
            deleteDir()
            echo "cleanWorkspace: Workspace is clean."
          if (body) { body() }
        }
      }
    }
  }
}
