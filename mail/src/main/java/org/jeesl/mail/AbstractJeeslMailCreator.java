package org.jeesl.mail;

import net.sf.ahtutils.exception.processing.UtilsConfigurationException;

import org.jeesl.model.xml.system.io.mail.EmailAddress;
import org.jeesl.model.xml.system.io.mail.From;
import org.jeesl.model.xml.system.io.mail.Header;
import org.jeesl.model.xml.system.io.mail.To;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractJeeslMailCreator
{
	final static Logger logger = LoggerFactory.getLogger(AbstractJeeslMailCreator.class);
		
	protected String lang;
	protected Header header;
	protected String mailCode;
	
	protected boolean doneInjection,doneWork;
	
	protected EmailAddress fromAddress;
	protected String subject;
	
	public AbstractJeeslMailCreator(String mailCode)
	{
		lang = "de";
		this.mailCode=mailCode;
		doneInjection=false;
		doneWork=false;
	}
	
	protected void setInjections(){doneInjection=true;}
	protected void setWork(){doneWork=true;}
	
	protected void checkPreconditions() throws UtilsConfigurationException
	{
		if(doneInjection==false){throw new UtilsConfigurationException("No Injections done");}
		if(doneWork==false){throw new UtilsConfigurationException("No Work defined");}
	}
	
	protected void createHeader()
	{
		header = new Header();
		header.setSubject(subject);
				
		From from = new From();
		from.setEmailAddress(fromAddress);
		header.setFrom(from);		
		
		header.setTo(new To());
	}
}