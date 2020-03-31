package org.proxima.common.mail;

import java.io.*;
import javax.mail.internet.MimeMessage.RecipientType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.velocity.*;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 
 * @author DaniG - Milan Centauri Academy  
 * @author TraianC - Milano Centauri Academy III
 * 
 * @version 2.0
 * 
 *          La classe MailUtility fornisce:
 *          - un metodo statico per inviare una
 *          e-mail ad un utente specificando: email dell'utente, oggetto e corpo
 *          della e-mail.
 *           
 *          - due metodi statici per inviare una
 *          e-mail ad un utente specificando: email dell'utente, oggetto e corpo
 *          della e-mail, email degli utenti cc e ccn.
 *           
 *          - due metodi statici per inviare una
 *          e-mail ad un utente specificando: email dell'utente, oggetto e corpo
 *          della e-mail( Viene mandata una mail anche ai destinari cc e ccn presenti in mail properties).
 * 
 */
public class MailUtility {
	
	private static StringWriter writer;
	static final Logger logger = LogManager.getLogger(MailUtility.class);
	static final String MAILPROPS = "mail.properties";
	static final String OKSETSESSION = "setMailSession() OK";
	static final String OKCLOSESESSION = "closeSession() OK";
	static final String PW = "mail.smtps.password";
	static final String CHARSET = "text/html; charset=utf-8";

	
	//costruttore
	private MailUtility () {}
	
	/**
	 * Questo metodo permette di inviare una email specificando destinatario,
	 * oggetto e messaggio.
	 * 
	 * @param dest
	 *            E-mail del destinatario
	 * @param subject
	 *            Oggetto della e-mail
	 * @param mess
	 *            Testo della e-mail
	 *            
	 * @return boolean Il metodo ritorna false solo se viene sollevata un'eccezione.
	 */

	public static boolean sendSimpleMail(String dest, String subject, String mess) {
		boolean isSent = true;
		try {	
			Properties props = new Properties();
			props.load(MailUtility.class.getClassLoader().getResourceAsStream(MAILPROPS));
			writer=new StringWriter();
			
			Session mailSession = setMailSession(mess);
			logger.info(OKSETSESSION);
			String password = props.getProperty(PW);
			String messageContent = writer.toString();
			MimeMessage message = new MimeMessage(mailSession);
			message.addRecipient(RecipientType.TO, new InternetAddress(dest));
			message.setSubject(subject);
			message.setContent(messageContent, CHARSET);
			
			sendMailAndCloseSession(mailSession,password,message);
			logger.info(OKCLOSESESSION);

		}catch(Exception e) {
			isSent=false;			
			logger.error(e.getMessage(), e);
		}
		return isSent;
		}
		
	/**
	 * Questo metodo permette di inviare una email specificando destinatario,
	 * cc, oggetto e messaggio.
	 * 
	 * @param dest
	 *            E-mail del destinatario
	 * @param cc
	 *            E-mail del destinatario/i cc
	 * @param subject
	 *            Oggetto della e-mail
	 * @param mess
	 *            Testo della e-mail
	 
	 * @return boolean Il metodo ritorna false solo se viene sollevata un'eccezione.
	 */
	
	public static boolean sendSimpleMailWithCc(String dest, String[] cc, String subject, String mess) {
		boolean isSent = true;
		try {	
			Properties props = new Properties();
			props.load(MailUtility.class.getClassLoader().getResourceAsStream(MAILPROPS));
			
			Session mailSession = setMailSession(mess);
			logger.info(OKSETSESSION);

			String password = props.getProperty(PW);
			String messageContent = writer.toString();
			MimeMessage message = new MimeMessage(mailSession);
			message.addRecipient(RecipientType.TO, new InternetAddress(dest));
			
			InternetAddress[] ccAddress = new InternetAddress[cc.length];
            for( int i = 0; i < cc.length; i++ ) {
                ccAddress[i] = new InternetAddress(cc[i]);
            }
            for( int i = 0; i < ccAddress.length; i++) {
                message.addRecipient(RecipientType.CC, ccAddress[i]);
            }
            
            message.setSubject(subject);
			message.setContent(messageContent, CHARSET);
			logger.info("Ready to send mail...");
			sendMailAndCloseSession(mailSession,password,message);
			logger.info(OKCLOSESESSION);
		}catch(Exception e) {
			isSent=false;
			logger.error(e.getMessage(), e);
		}
		return isSent;
	}
	
	/**
	 * Questo metodo permette di inviare una email specificando destinatario,
	 * oggetto e messaggio. Invia la mail anche ai cc presenti in mail.properties.
	 * 
	 * @param dest
	 *            E-mail del destinatario
	 * @param subject
	 *            Oggetto della e-mail
	 * @param mess
	 *            Testo della e-mail
	 *            
	 * @return boolean Il metodo ritorna false solo se viene sollevata un'eccezione.
	 */
	
	public static boolean sendSimpleMailWithDefaultCc(String dest, String subject, String mess) {
		boolean isSent = false ;
		Properties props = new Properties();
		try {
			props.load(MailUtility.class.getClassLoader().getResourceAsStream(MAILPROPS));
			String[] cc = (props.getProperty("mail.cc")).split(",");
			isSent = sendSimpleMailWithCc(dest, cc, subject, mess);		
		} catch (Exception e) {
			isSent = false;
			logger.error(e.getMessage(), e);
		}
		return isSent;
	}
	
	/**
	 * Questo metodo permette di inviare una email specificando destinatario, cc, ccn, 
	 * oggetto e messaggio. 
	 * 
	 * @param dest
	 *            E-mail del destinatario
	 * @param cc
	 *            E-mail del destinatario/i cc
	 * @param ccn
	 *            E-mail del destinatario/i ccn
	 * @param subject
	 *            Oggetto della e-mail
	 * @param mess
	 *            Testo della e-mail
	 *            
	 * @return boolean Il metodo ritorna false solo se viene sollevata un'eccezione.
	 */
	
	public static boolean sendSimpleMailWithCcAndCcn(String dest, String[] cc, String[] ccn, String subject, String mess ) {
		
		boolean isSent = false ;
		Properties props = new Properties();
		try {
			props.load(MailUtility.class.getClassLoader().getResourceAsStream(MAILPROPS));
			isSent = sendSimpleMailWithCc(dest, cc, subject, mess);		
		
			Session mailSession = setMailSession(mess);

			String password = props.getProperty(PW);
			String messageContent = writer.toString();
			MimeMessage message = new MimeMessage(mailSession);
			message.addRecipient(RecipientType.TO, new InternetAddress(dest));

           InternetAddress[] bccAddress = new InternetAddress[ccn.length];
            for( int i = 0; i < ccn.length; i++ ) {
                bccAddress[i] = new InternetAddress(ccn[i]);
            }
            for( int i = 0; i < bccAddress.length; i++) {
                message.addRecipient(RecipientType.BCC, bccAddress[i]);}
            
            message.setSubject(subject);
			message.setContent(messageContent, CHARSET);
			
			sendMailAndCloseSession(mailSession,password,message);
			
		} catch (Exception e) {
			isSent = false;
			logger.error(e.getMessage(), e);
		}
		return isSent;
	}
	
	/**
	 * Questo metodo permette di inviare una email specificando destinatario,
	 * oggetto e messaggio. Invia la mail anche ai cc e ccn presenti in mail.properties.
	 * 
	 * @param dest
	 *            E-mail del destinatario
	 * @param subject          
	 *            Oggetto della e-mail
	 * @param mess
	 *            Testo della e-mail
	 *            
	 * @return boolean Il metodo ritorna false solo se viene sollevata un'eccezione.
	 */
	
	public static boolean sendSimpleMailWithDefaultCcAndCcn(String dest, String subject, String mess) {
		boolean isSent = false ;
		Properties props = new Properties();
		try {
			props.load(MailUtility.class.getClassLoader().getResourceAsStream(MAILPROPS));
			String[] cc = (props.getProperty("mail.cc")).split(",");
			String[] ccn = (props.getProperty("mail.ccn")).split(",");
			isSent = sendSimpleMailWithCcAndCcn(dest, cc, ccn, subject, mess);		
		} catch (Exception e) {
			isSent = false;
			logger.error(e.getMessage(), e);
		}
		return isSent;
	}
	
	/**
	 * Questo metodo permette di settare la mail session.
	 * 
	 * @param dest
	 *            E-mail del destinatario
	 * @param subject
	 *            Oggetto della e-mail
	 * @param mess
	 *            Testo della e-mail
	 *            
	 * @return session Il metodo ritorna la sessione.
	 */
	
	private static Session setMailSession(String mess) {
		Session mailSession=null ;
			VelocityEngine ve = new VelocityEngine();
			ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
			ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
			ve.init();
			Template t = ve .getTemplate("templates/template.logo.vm");
			VelocityContext context = new VelocityContext();
			context.put("message", mess);
			
			writer = new StringWriter();
			t.merge(context, writer);
			Properties props = new Properties();
			
			try {
				props.load(MailUtility.class.getClassLoader().getResourceAsStream(MAILPROPS));
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
			mailSession = Session.getDefaultInstance(props);
		
		return mailSession;
	}
	
	/**
	 * Questo metodo permette di chiudere la mailSession.
	 * 
	 * @param mailSession
	 *            La sessione
	 * @param password
	 *            Password di connessione
	 * @param message
	 *            E-mail da inviare
	 *            
	 * @return session Il metodo chiude la sessione.
	 */
	
	private static void sendMailAndCloseSession(Session mailSession, String password, Message message) {
		try{
			logger.info("Initialize mail session...");
			Transport tr = mailSession.getTransport();
			logger.info("Initialize mail transport...");
			tr.connect(null, password);			
			tr.sendMessage(message, message.getAllRecipients());
			tr.close();
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}

