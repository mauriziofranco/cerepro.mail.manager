pipeline {
    agent any
    stages {        
        stage("Compile") {
            steps {
                sh "./mvnw clean compile"
            }
        }  
        stage("Provides application property file for Integration tests(trying to send mails))") {
            steps {
                sh "rm ./src/test/resources/mail.properties"
                echo "Original ./src/test/resources/mail.properties successfully removed!!"
                sh "cp /cerepro_resources/properties/cerepro.mail.manager/mail.test.properties ./src/test/resources/mail.properties"
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
				        reportDir: 'target/site/jacoco',
				        reportFiles: 'index.html',
				        reportName: "JaCoCo Report"
				    ]
				)
            }
        }
        stage("Build and publish code check-style report") {
            steps {
                sh "./mvnw site"
                publishHTML (target: [
					reportDir: 'target/site/',
					reportFiles: 'checkstyle.html',
					reportName: "Checkstyle Report"
				])
            }
        }
        stage("Install for All Environments") {
            steps {              
				sh "./mvnw install -DskipTests"
            }
        }
    }
    post {
		always {
		
			emailext body: 'A Test EMail', 
				recipientProviders: [[$class: 'DevelopersRecipientProvider'], 
					[$class: 'RequesterRecipientProvider']], 
					subject: 'Test'
		}
	}
}