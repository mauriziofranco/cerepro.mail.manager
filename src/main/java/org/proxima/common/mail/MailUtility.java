package org.proxima.common.mail;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Properties;
import java.util.function.Function;
import java.util.function.IntFunction;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

/**
 * Provides a series of utilities mail to send mail: simple, with cc and ccn
 * recipients.
 * 
 * @author DaniG - Milan Centauri Academy I
 * @author TraianC - Milano Centauri Academy III
 * @author Maurizio Franco - maurizio.franco@ymail.com
 * 
 * @version 3.0
 * 
 */
public final class MailUtility {

	private static StringWriter writer;
	static final Logger LOGGER = LogManager.getLogger(MailUtility.class);
	private static Properties props;
	
	//properties file keys
	public static final String MAILPROPS_FILENAME = "mail.properties";
	public final static String MAIL_CCN_RECIPIENT_KEY = "mail.ccn";
	public final static String MAIL_CC_RECIPIENT_KEY = "mail.cc";
	private static final String PWD_KEY = "mail.smtps.password";	

	private static String[] mailCcRecipient;
	private static String[] mailCcnRecipient;
	private static final String CHARSET = "text/html; charset=utf-8";
	private static String EMAIL_ACCOUNT_PWD;

	static {
		try {
			props = new Properties();
			props.load(MailUtility.class.getClassLoader().getResourceAsStream(MailUtility.MAILPROPS_FILENAME));
			mailCcnRecipient = (props.getProperty(MailUtility.MAIL_CCN_RECIPIENT_KEY)).split(",");
			mailCcRecipient = (props.getProperty(MailUtility.MAIL_CC_RECIPIENT_KEY)).split(",");
			EMAIL_ACCOUNT_PWD = props.getProperty(MailUtility.PWD_KEY);
		} catch (IOException ioe) {
			LOGGER.error(ioe.getMessage(), ioe);
		}
	}

	private MailUtility () { }
	
	/**
	 * Questo metodo permette di inviare una email specificando destinatario,
	 * oggetto e messaggio.
	 * 
	 * @param recipient, recipient email address
	 * @param subject,   email subject
	 * @param mess,      email text message
	 * 
	 * @return boolean, false if exception thrown. Else, true every time mail
	 *         correctly sent.
	 */
	public static boolean sendSimpleMail(String recipient, String subject, String mess) {
		String[] recipients = { recipient };
		return sendSimpleMail(recipients, subject, mess);
	}

	/**
	 * Provides to send simple mail to multiple recipients
	 * 
	 * @param recipient E-mail del destinatario
	 * @param subject   Oggetto della e-mail
	 * @param mess      Testo della e-mail
	 * 
	 * @return boolean Il metodo ritorna false solo se viene sollevata un'eccezione.
	 */
	public static boolean sendSimpleMail(String[] recipients, String subject, String mess) {
		return sendMail(recipients, null, null, subject, mess);
	}

	/**
	 * 
	 * Provides to send simple mail to single recipient and single cc recipient.
	 * 
	 * 
	 * @param recipient, single recipient email address
	 * @param cc,        single cc recipient email address
	 * @param subject,   email subject
	 * @param mess,      email text message
	 * 
	 * @return boolean, false if exception thrown. True every time mail correctly
	 *         sent.
	 */
	public static boolean sendSimpleMailWithCc(String recipient, String cc, String subject, String mess) {
		String[] recipients = { recipient };
		String[] ccRecipients = { cc };
		return sendMail(recipients, ccRecipients, null, subject, mess);
	}

	/**
	 * 
	 * Provides to send simple mail to multi recipients and multi cc recipients.
	 * 
	 * 
	 * @param recipients, multi recipients email addresses
	 * @param cc,         multi cc recipients email addresses
	 * @param subject,    email subject
	 * @param mess,       email text message
	 * 
	 * @return boolean, false if exception thrown. True every time mail correctly
	 *         sent.
	 */
	public static boolean sendSimpleMailWithCc(String[] recipients, String[] cc, String subject, String mess) {
		return sendMail(recipients, cc, null, subject, mess);
	}

	/**
	 * 
	 * Provides to send simple mail to multi recipients and a default cc recipient.
	 * 
	 * 
	 * @param recipients, multi recipients email addresses
	 * @param subject,    email subject
	 * @param mess,       email text message
	 * 
	 * @return boolean, false if exception thrown. True every time mail correctly
	 *         sent.
	 */
	public static boolean sendSimpleMailWithDefaultCc(String[] recipients, String subject, String mess) {
		return sendMail(recipients, mailCcRecipient, null, subject, mess);
	}

	/**
	 * Provides to send simple mail to multi recipients, multi cc recipients and
	 * multi ccn recipients.
	 * 
	 * 
	 * @param recipients, multi recipients email addresses
	 * @param cc,         multi cc recipients email addresses
	 * @param ccn,        multi ccn recipients email addresses
	 * @param subject,    email subject
	 * @param mess,       email text message
	 * 
	 * @return boolean, false if exception thrown. True every time mail correctly
	 *         sent.
	 */
	public static boolean sendSimpleMailWithCcAndCcn(String[] recipients, String[] cc, String[] ccn, String subject,
			String mess) {
		return sendMail(recipients, cc, ccn, subject, mess);
	}

	/**
	 * Provides to send simple mail to multi recipients, a default cc recipient and
	 * a default ccn recipient.
	 * 
	 * 
	 * @param recipients, multi recipients email addresses
	 * @param subject,    email subject
	 * @param mess,       email text message
	 * 
	 * @return boolean, false if exception thrown. True every time mail correctly
	 *         sent.
	 */

	public static boolean sendSimpleMailWithDefaultCcAndCcn(String[] recipients, String subject, String mess) {
		return sendMail(recipients, mailCcRecipient, mailCcnRecipient, subject, mess);
	}

	/**
	 * 
	 * Provides to send simple mail to multi recipients and multi cc recipients.
	 * 
	 * 
	 * @param recipient, multi recipients email addresses
	 * @param cc,        multi cc recipients email addresses
	 * @param subject,   email subject
	 * @param mess,      email text message
	 * 
	 * @return boolean, false if exception thrown. True every time mail correctly
	 *         sent.
	 */
	private static boolean sendMail(String[] recipients, String[] cc, String[] ccn, String subject, String mess) {
		LOGGER.info("sendMail - START");
		boolean sent = true;
		try {

			writer = new StringWriter();

			Session mailSession = openMailSession(mess);

			String messageContent = writer.toString();
			MimeMessage message = new MimeMessage(mailSession);
			Address[] iaRecipients = convertArray(recipients, (x -> {
				try {
					return new InternetAddress(x);
				} catch (AddressException e) {
					LOGGER.error(e.getMessage(), e);
					e.printStackTrace();
					return null;
				}
			}), Address[]::new);
			message.addRecipients(RecipientType.TO, iaRecipients);
			// cc - START
			if (cc != null) {
//				InternetAddress[] ccAddress = new InternetAddress[cc.length];
//				for (int i = 0; i < cc.length; i++) {
//					ccAddress[i] = new InternetAddress(cc[i]);
//				}
//				for (int i = 0; i < ccAddress.length; i++) {
//					message.addRecipient(RecipientType.CC, ccAddress[i]);
//				}
				Address[] ccAddress = convertArray(cc, (x -> {
					try {
						return new InternetAddress(x);
					} catch (AddressException e) {
						LOGGER.error(e.getMessage(), e);
						e.printStackTrace();
						return null;
					}
				}), Address[]::new);
				message.addRecipients(RecipientType.CC, ccAddress);
			}
			// cc - END
			// ccn - START
			if (ccn != null) {
//				InternetAddress[] ccnAddress = new InternetAddress[ccn.length];
//				for (int i = 0; i < ccn.length; i++) {
//					ccnAddress[i] = new InternetAddress(ccn[i]);
//				}
//				for (int i = 0; i < ccnAddress.length; i++) {
//					message.addRecipient(RecipientType.BCC, ccnAddress[i]);
//				}
				Address[] ccnAddress = convertArray(ccn, (x -> {
					try {
						return new InternetAddress(x);
					} catch (AddressException e) {
						LOGGER.error(e.getMessage(), e);
						e.printStackTrace();
						return null;
					}
				}), Address[]::new);
				message.addRecipients(RecipientType.BCC, ccnAddress);
			}
			// ccn - END
			message.setSubject(subject);
			message.setContent(messageContent, CHARSET);

			closeMailSession(mailSession, EMAIL_ACCOUNT_PWD, message);
		} catch (Exception e) {
			sent = false;
			LOGGER.error(e.getMessage(), e);
		}
		LOGGER.info("sendMail - END - return value: " + sent);
		return sent;
	}

	/**
	 * Provides to open a new javax.mail.Session
	 * 
	 * @param recipient E-mail del destinatario
	 * @param subject   Oggetto della e-mail
	 * @param mess      Testo della e-mail
	 * 
	 * @return session Il metodo ritorna la sessione.
	 */

	private static Session openMailSession(String mess) {
		LOGGER.info("openMailSession - START");
		Session mailSession = null;
		VelocityEngine ve = new VelocityEngine();
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		ve.init();
		Template t = ve.getTemplate("templates/template.logo.vm");
		VelocityContext context = new VelocityContext();
		context.put("message", mess);

		writer = new StringWriter();
		t.merge(context, writer);
		mailSession = Session.getDefaultInstance(props);
		LOGGER.info("openMailSession - END - return value: " + mailSession);
		return mailSession;
	}

	/**
	 * Questo metodo permette di chiudere la mailSession.
	 * 
	 * @param mailSession La sessione
	 * @param password    Password di connessione
	 * @param message     E-mail da inviare
	 * 
	 * @return session Il metodo chiude la sessione.
	 */

	private static void closeMailSession(Session mailSession, String password, Message message) throws NoSuchProviderException, MessagingException {
		LOGGER.info("closeMailSession - START");
//		try {
			LOGGER.info("Initialize mail session...");
			Transport tr = mailSession.getTransport();
			LOGGER.info("Initialize mail transport...");
			tr.connect(null, password);
			tr.sendMessage(message, message.getAllRecipients());
			tr.close();
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//		}
		LOGGER.info("closeMailSession - END");
	}

	/*
	 * From this stackoverflow.com post:
	 * https://stackoverflow.com/questions/23057549/lambda-expression-to-convert-array-list-of-string-to-array-list-of-integers
	 */
	private static <T, U> U[] convertArray(T[] from, Function<T, U> func, IntFunction<U[]> generator) {
		LOGGER.info("convertArray - START - array to convert length: " + from.length);
		return Arrays.stream(from).map(func).toArray(generator);
	}
}
