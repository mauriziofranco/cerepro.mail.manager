package org.proxima.common.mail;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Properties;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MailUtilitiesTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(MailUtilitiesTest.class);

	private static String[] cc, ccn, to;
	private static String singleRecipient;
	private static String simple_message_object ;
	private static String simple_message_with_attachment_object ;
	private static String attachmentSourcePathFileName ;
	private static String attachmentTargetFileName ;
	private static String simple_message_with_attachment_body ;
	
	private final static String MAIL_SIMPLE_OBJECT_KEY = "mail.simple.object";
	private final static String MAIL_WITH_ATTACHMENTSIMPLE_OBJECT_KEY = "mail.simple.object.with.attachment";
	private final static String MAIL_WITH_ATTACHMENT_SOURCE_RELATIVE_PATH_FILE_NAME_KEY = "mail.simple.object.with.attachment.source.relative.path.file.name";
	private final static String MAIL_WITH_ATTACHMENT_FILE_NAME_KEY = "mail.simple.object.with.attachment.target.filename";
	
	private final static String MAIL_WITH_ATTACHMENT_MESSAGE_KEY = "mail.simple.object.with.attachment.message";
	
	static {
		try {
			Properties props = new Properties();
			props.load(MailUtility.class.getClassLoader().getResourceAsStream(MailUtility.MAILPROPS_FILENAME));
			ccn = (props.getProperty(MailUtility.MAIL_CCN_RECIPIENT_KEY)).split(",");
			cc = (props.getProperty(MailUtility.MAIL_CC_RECIPIENT_KEY)).split(",");
			to = (props.getProperty(MailUtility.MAIL_CC_RECIPIENT_KEY)).split(",");			
			singleRecipient = cc[0];
			simple_message_object = props.getProperty(MAIL_SIMPLE_OBJECT_KEY);
			simple_message_with_attachment_object = props.getProperty(MAIL_WITH_ATTACHMENTSIMPLE_OBJECT_KEY);
			attachmentSourcePathFileName = props.getProperty(MAIL_WITH_ATTACHMENT_SOURCE_RELATIVE_PATH_FILE_NAME_KEY);
			attachmentTargetFileName = props.getProperty(MAIL_WITH_ATTACHMENT_FILE_NAME_KEY);
			simple_message_with_attachment_body=props.getProperty(MAIL_WITH_ATTACHMENT_MESSAGE_KEY);
		} catch (IOException ioe) {
			LOGGER.error(ioe.getMessage(), ioe);
		}
	}

	@Test
	public void sendSimpleMailOk() {
		LOGGER.info("#########");
		LOGGER.info("TEST - sendSimpleMail()");
		LOGGER.info("#########");
		Boolean check = false;
		check = MailUtility.sendSimpleMail(to, simple_message_object,
				"<p class=\"abcde\">Test send simple mail avvenuto con successo! </p>");
		assertTrue(check);
	}
	
	@Test
	public void sendSimpleMailSingleOk() {
		//-
		LOGGER.info("#########");
		LOGGER.info("TEST - sendSimpleMail()");
		LOGGER.info("#########");
		Boolean check = false;
		check = MailUtility.sendSimpleMail(singleRecipient, simple_message_object,
				"<p class=\"abcde\">Test send simple mail avvenuto con successo! </p>");
		assertTrue(check);
	}
	

	@Test
	public void sendSimpleMailKo() {

		LOGGER.info("#########");
		LOGGER.info("TEST - sendSimpleMailKo()");
		LOGGER.info("#########");
		Boolean check = false;
		String[] recipients = null;
		check = MailUtility.sendSimpleMail(recipients, simple_message_object,
				"<p class=\"abcde\">Test send simple mail avvenuto con successo! </p>");
		assertFalse(check);
	}

	// test cc
	@Test
	public void sendSimpleMailWithCc() {

		LOGGER.info("#########");
		LOGGER.info("TEST - sendSimpleMailWithCc()");
		LOGGER.info("#########");
		Boolean check = false;
		check = MailUtility.sendSimpleMailWithCc(to, cc, "Prova e-mail con CC",
				"<p class=\"abcde\">Test send simple mail con CC avvenuto con successo!</p>");
		assertTrue(check);
	}

	
	@Test
	public void sendSimpleMailWithCcSingleOk() {
		LOGGER.info("#########");
		LOGGER.info("TEST - sendSimpleMailWithCcMultiOk()");
		LOGGER.info("#########");
		Boolean check = false;
		check = MailUtility.sendSimpleMailWithCc(singleRecipient, singleRecipient, "Prova e-mail con CC",
				"<p class=\"abcde\">Test send simple mail con CC avvenuto con successo!</p>");
		assertTrue(check);
	}

	// test bcc
	@Test
	public void sendSimpleMailWithCcAndCcn() {

		LOGGER.info("#########");
		LOGGER.info("TEST - sendSimpleMailWithCcAndCcn()");
		LOGGER.info("#########");
		Boolean check = false;
		check = MailUtility.sendSimpleMailWithCcAndCcn(to, cc, ccn, "Prova e-mail con CC e CCN",
				"<p class=\"abcde\">Test send simple mail con CC e CCN avvenuto con successo!</p>");
		assertTrue(check);
	}

	// test default cc
	@Test
	public void sendSimpleMailWithDefaultCc() {

		LOGGER.info("#########");
		LOGGER.info("TEST - sendSimpleMailWithDefaultCc()");
		LOGGER.info("#########");

		Boolean check = false;
		check = MailUtility.sendSimpleMailWithDefaultCc(to, "Prova e-mail con default CC",
				"<p class=\"abcde\">Test send simple mail con default CC avvenuto con successo!</p>");
		assertTrue(check);
	}

	// test default cc and ccn
	@Test
	public void sendSimpleMailWithDefaultCcAndCcn() {

		LOGGER.info("#########");
		LOGGER.info("TEST - sendSimpleMailWithDefaultCcAndCcn()");
		LOGGER.info("#########");

		Boolean check = false;
		check = MailUtility.sendSimpleMailWithDefaultCcAndCcn(to, "Prova e-email con default CC e CCN",
				"<p class=\"abcde\">Test send simple mail con default CC e CCN avvenuto con successo!</p>");
		assertTrue(check);
	}
	
	@Test
	public void sendSimpleMailWithAttachmentOk() {
		LOGGER.info("#########");
		LOGGER.info("TEST - sendSimpleMailWithAttachmentOk()");
		LOGGER.info("#########");
		boolean sendMailResult = MailUtility.sendMailWithAttachment(to, simple_message_with_attachment_object, simple_message_with_attachment_body, attachmentSourcePathFileName,
				attachmentTargetFileName);
		assertTrue(sendMailResult);
	}

}
