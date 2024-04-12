pipeline {
    agent any

    stages {
        stage('Pull') {
            steps {
                git credentialsId: 'chetansomkuwar254', url: 'https://github.com/chetansomkuwar254/studentapp.ui.git'
                echo 'Hello World'
            }
        }
                stage('Build') {
            steps {
                sh '/opt/apache-maven-3.9.6/bin/mvn clean package'
                echo 'Here we are deploying the code'
            }
        }
                stage('Test') {
            steps {
                sh '/opt/apache-maven-3.9.6/bin/mvn sonar:sonar -Dsonar.projectKey=studentapp-ui -Dsonar.host.url=http://54.221.44.130:9000 -Dsonar.login=f316d47655ec5d539cb484f38aa1f1666c491815'
                echo 'Testing done'
            }
        }
                stage('Deploy') {
            steps {
                echo 'Deploy done'
            }
        }
    }
}
