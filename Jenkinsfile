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
                echo "Original ./src/main/resources/mail.properties successfully removed!!"
                sh "cp /cerepro_resources/properties/cerepro.mail.manager/mail.test.properties ./src/main/resources/mail.properties"
            }
        }      
        stage("Unit test") {
            steps {
                sh "./mvnw test"
            }
        }
        stage("Code coverage") {
            steps {              
				jacoco(execPattern: 'target/jacoco.exec')
				publishHTML (target: [
				        reportDir: 'build/reports/jacoco/html',
				        reportFiles: 'index.html',
				        reportName: "JaCoCo Report"
				    ]
				)
            }
        }
        stage("Install for DEV Environment") {
            steps {              
				sh "./mvnw install -DskipTests"
            }
        }
    }
}