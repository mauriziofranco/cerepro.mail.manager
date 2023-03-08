package org.proxima.common.mail;

/**
 * 
 * @author Antonio Iannaccone - Roma Academy VII 
 *
 */

public class TestMailWithAttachment {
	
	public static void main(String[] args) {

		String recipient = "youremail@gmail.com";
		String subject = "Prova allegato";
		String message = "Prova";
		String attachmentPath = "src/test/resources/test.txt";
		String attachmentName = "test.txt";

		boolean success = MailUtility.sendMailWithAttachment(recipient, subject, message, attachmentPath,
				attachmentName);

		if (success) { 
			System.out.println("E-mail inviata con successo");
		} else {
			System.out.println("Errore nell'invio dell'e-mail");
		}
	}

}
