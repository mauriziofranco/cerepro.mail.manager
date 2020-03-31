package org.proxima.common.mail;

import static org.junit.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.proxima.common.mail.MailUtility;

public class MailUtilitiesTest {
	
	String mailRecipient = "m.franco@proximanetwork.it" ;
	String mailRecipientCC = "m.franco@proximanetwork.it, maurizio.franco@ymail.com" ;
	
	final Logger logger = LogManager.getLogger(MailUtilitiesTest.class);
	@Test
	public void sendSimpleMail() {
		
		logger.info("#########");
		logger.info("TEST - sendSimpleMail()");
		logger.info("#########");
		Boolean check=false;
		try {
			check = MailUtility.sendSimpleMail(mailRecipient, "Prova e-mail semplice", "<p class=\"abcde\">Test send simple mail avvenuto con successo! </p>");
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		assertTrue(check);
	}
	
	//test cc
	@Test
	public void sendSimpleMailWithCc() {
		
		logger.info("#########");
		logger.info("TEST - sendSimpleMailWithCc()");
		logger.info("#########");
		Boolean check=false;
		try {
			check = MailUtility.sendSimpleMailWithCc(mailRecipient, mailRecipientCC.split(","), "Prova e-mail con CC", "<p class=\"abcde\">Test send simple mail con CC avvenuto con successo!</p>");
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		assertTrue(check);
	}

	//test bcc
	@Test
	public void sendSimpleMailWithCcAndCcn() {
	
	logger.info("#########");
	logger.info("TEST - sendSimpleMailWithCcAndCcn()");
	logger.info("#########");
	Boolean check=false;
	try {
		check = MailUtility.sendSimpleMailWithCcAndCcn(mailRecipient, mailRecipientCC.split(","), mailRecipientCC.split(","), "Prova e-mail con CC e CCN", "<p class=\"abcde\">Test send simple mail con CC e CCN avvenuto con successo!</p>");
	} catch (Exception e) 
	{
		e.printStackTrace();
	}
	assertTrue(check);
}
	
	//test default cc
	@Test 
	public void sendSimpleMailWithDefaultCc() {
		
		logger.info("#########");
		logger.info("TEST - sendSimpleMailWithDefaultCc()");
		logger.info("#########");
		
		Boolean check=false;
		try {
			check = MailUtility.sendSimpleMailWithDefaultCc(mailRecipient, "Prova e-mail con default CC", "<p class=\"abcde\">Test send simple mail con default CC avvenuto con successo!</p>");
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		assertTrue(check);
	}
	
	//test default cc and ccn
	@Test 
	public void sendSimpleMailWithDefaultCcAndCcn() {
		
		logger.info("#########");
		logger.info("TEST - sendSimpleMailWithDefaultCcAndCcn()");
		logger.info("#########");
		
		Boolean check=false;
		try {
			check = MailUtility.sendSimpleMailWithDefaultCcAndCcn(mailRecipient, "Prova e-email con default CC e CCN", "<p class=\"abcde\">Test send simple mail con default CC e CCN avvenuto con successo!</p>");
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		assertTrue(check);
	}
}