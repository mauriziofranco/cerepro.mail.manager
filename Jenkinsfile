pipeline {
    agent any
    stages {        
        stage("Compile") {
            steps {
                sh "./mvnw compile"
            }
        }  
        stage("Provides application property file for Unit test stage and DEV & STAGE environments") {
            steps {
                sh "rm ./src/main/resources/mail.properties"
                sh "copy /cerepro_resources/cerepro.mail.manager/mail.test.properties ./src/main/resources/mail.properties"
            }
        }      
        stage("Unit test") {
            steps {
                sh "./mvnw test"
            }
        }
    }
}