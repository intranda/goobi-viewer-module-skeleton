pipeline {

  agent {
    docker {
      image 'maven:3-eclipse-temurin-21'
      args '-v $HOME/.m2:/var/maven/.m2:z -u 1000 -ti -e _JAVA_OPTIONS=-Duser.home=/var/maven -e MAVEN_CONFIG=/var/maven/.m2'
    }
  }

  options {
    buildDiscarder logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '15', daysToKeepStr: '90', numToKeepStr: '')
  }

  stages {
    stage('prepare') {
      steps {
        sh 'git clean -fdx'
      }
    }
    stage('build develop') {
      when {
        not {
          anyOf {
            branch 'master';
            tag "v*"
          }
        }
      }
      steps {
              sh 'mvn -f goobi-viewer-module-*/pom.xml clean verify -U'
              recordIssues enabledForFailure: true, aggregatingResults: true, tools: [java(), javaDoc()]
      }
    }
    stage('build release') {
      when {
        anyOf {
          branch 'master';
          tag "v*"
        }
      }
      steps {
        sh 'mvn -f goobi-viewer-module-*/pom.xml -DfailOnSnapshot=true clean package -U'
      }
    }

    stage('deployment of artifacts to maven repository') {
      when {
        anyOf {
          tag "v*"
          branch 'develop'
        }
      }
      steps {
        sh 'mvn -f goobi-viewer-module-*/pom.xml deploy'
      }
    }
  }

  post {
    // always {
    //   junit "**/target/surefire-reports/*.xml"
    // }
    success {
      archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
    }
    changed {
      emailext(
        subject: '${DEFAULT_SUBJECT}',
        body: '${DEFAULT_CONTENT}',
        recipientProviders: [requestor(),culprits()],
        attachLog: true
      )
    }
  }
}
/* vim: set ts=2 sw=2 tw=120 et :*/
