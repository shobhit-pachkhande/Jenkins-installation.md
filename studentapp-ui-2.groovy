pipeline {
    agent any
    stages {
        stage ('pull') {
            steps {
               git 'https://github.com/SurajBele/studentapp.ui.git'
               echo 'pull sucessful'
            }
         }
         /*
         stage ('build') {
            steps {
               sh '/opt/apache-maven-3.9.3/bin/mvn clean package'
               echo 'build sucessful'
            }
         }
         
         stage ('test') {
            steps {
               sh '/opt/apache-maven-3.9.3/bin/mvn sonar:sonar   -Dsonar.projectKey=studentapp   -Dsonar.host.url=http://13.127.12.209:9000   -Dsonar.login=39f2d57f571f00403e40e563fd256b4e13a07032
            }
         }
         */
             stage ('build-test') {
            steps {
               withSonarQubeEnv(installationName: 'sonar-server', credentialsId: 'sonar-token') {
                  sh '/opt/apache-maven-3.9.3/bin/mvn clean package sonar:sonar   -Dsonar.projectKey=studentapp'
                  echo ' build-test completed'
               }
            }
         }
            stage("Quality Gate") {
               steps { 
                  waitForQualityGate abortPipeline: true
                  echo ' Quality Gate completed'
               }
            }
          stage ('deploy') {
            steps {
               deploy adapters: [tomcat8(credentialsId: 'tomcat-cred', path: '', url: 'http://43.205.215.159:8080/')], contextPath: '/', war: '**/*.war'
               echo 'deploy completed'
            }
         }
    }
}
