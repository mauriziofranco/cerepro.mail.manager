package org.proxima.common.mail;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Test;
import org.proxima.common.mail.MailUtility;

public class MailUtilitiesTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(MailUtilitiesTest.class);

	private static String[] cc, ccn, to;
	private static String singleRecipient;

	static {
		try {
			Properties props = new Properties();
			props.load(MailUtility.class.getClassLoader().getResourceAsStream(MailUtility.MAILPROPS_FILENAME));
			ccn = (props.getProperty(MailUtility.MAIL_CCN_RECIPIENT_KEY)).split(",");
			cc = (props.getProperty(MailUtility.MAIL_CC_RECIPIENT_KEY)).split(",");
			to = (props.getProperty(MailUtility.MAIL_CC_RECIPIENT_KEY)).split(",");
			singleRecipient = cc[0];
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
		check = MailUtility.sendSimpleMail(to, "Prova e-mail semplice",
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
		check = MailUtility.sendSimpleMail(singleRecipient, "Prova e-mail semplice",
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
		check = MailUtility.sendSimpleMail(recipients, "Prova e-mail semplice",
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

}
