package org.proxima.common.mail;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.proxima.common.mail.MailUtility;

public class MailUtilitiesTest {

	private static final Logger logger = LogManager.getLogger(MailUtilitiesTest.class);

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
			logger.error(ioe.getMessage(), ioe);
		}
	}

	@Test
	public void sendSimpleMailOk() {

		logger.info("#########");
		logger.info("TEST - sendSimpleMail()");
		logger.info("#########");
		Boolean check = false;
		check = MailUtility.sendSimpleMail(to, "Prova e-mail semplice",
				"<p class=\"abcde\">Test send simple mail avvenuto con successo! </p>");
		assertTrue(check);
	}

//	@Test
//	public void sendSimpleMailKo() {
//
//		logger.info("#########");
//		logger.info("TEST - sendSimpleMail()");
//		logger.info("#########");
//		Boolean check = false;
//		String[] recipients = null;
//		check = MailUtility.sendSimpleMail(recipients, "Prova e-mail semplice",
//				"<p class=\"abcde\">Test send simple mail avvenuto con successo! </p>");
//		assertFalse(check);
//	}
//
//	// test cc
//	@Test
//	public void sendSimpleMailWithCc() {
//
//		logger.info("#########");
//		logger.info("TEST - sendSimpleMailWithCc()");
//		logger.info("#########");
//		Boolean check = false;
//		check = MailUtility.sendSimpleMailWithCc(to, cc, "Prova e-mail con CC",
//				"<p class=\"abcde\">Test send simple mail con CC avvenuto con successo!</p>");
//		assertTrue(check);
//	}
//
////	@Test
////	public void sendSimpleMailWithCcSingleOk() {
////
////		logger.info("#########");
////		logger.info("TEST - sendSimpleMailWithCcMultiOk()");
////		logger.info("#########");
////		Boolean check = false;
////		check = MailUtility.sendSimpleMailWithCc(singleRecipient, singleRecipient, "Prova e-mail con CC",
////				"<p class=\"abcde\">Test send simple mail con CC avvenuto con successo!</p>");
////		assertTrue(check);
////	}
//
//	// test bcc
//	@Test
//	public void sendSimpleMailWithCcAndCcn() {
//
//		logger.info("#########");
//		logger.info("TEST - sendSimpleMailWithCcAndCcn()");
//		logger.info("#########");
//		Boolean check = false;
//		check = MailUtility.sendSimpleMailWithCcAndCcn(to, cc, ccn, "Prova e-mail con CC e CCN",
//				"<p class=\"abcde\">Test send simple mail con CC e CCN avvenuto con successo!</p>");
//		assertTrue(check);
//	}
//
//	// test default cc
//	@Test
//	public void sendSimpleMailWithDefaultCc() {
//
//		logger.info("#########");
//		logger.info("TEST - sendSimpleMailWithDefaultCc()");
//		logger.info("#########");
//
//		Boolean check = false;
//		check = MailUtility.sendSimpleMailWithDefaultCc(to, "Prova e-mail con default CC",
//				"<p class=\"abcde\">Test send simple mail con default CC avvenuto con successo!</p>");
//		assertTrue(check);
//	}
//
//	// test default cc and ccn
//	@Test
//	public void sendSimpleMailWithDefaultCcAndCcn() {
//
//		logger.info("#########");
//		logger.info("TEST - sendSimpleMailWithDefaultCcAndCcn()");
//		logger.info("#########");
//
//		Boolean check = false;
//		check = MailUtility.sendSimpleMailWithDefaultCcAndCcn(to, "Prova e-email con default CC e CCN",
//				"<p class=\"abcde\">Test send simple mail con default CC e CCN avvenuto con successo!</p>");
//		assertTrue(check);
//	}

}