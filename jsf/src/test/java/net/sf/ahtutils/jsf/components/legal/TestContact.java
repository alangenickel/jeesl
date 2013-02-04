package net.sf.ahtutils.jsf.components.legal;

import java.util.Scanner;

import net.sf.ahtutils.test.AbstractAhtUtilsJsfTst;
import net.sf.ahtutils.test.UtilsJsfTstBootstrap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestContact extends AbstractAhtUtilsJsfTst
{
	final static Logger logger = LoggerFactory.getLogger(TestContact.class);
	
	private Contact contact;
	
	@Before
	public void init()
	{
		contact = new Contact();
	}
	
	@Test
	public void testInit()
	{
		String input = "Mein Test";
		String base64 = contact.encode(input);
		String back = contact.decode(base64);
		
		Assert.assertEquals("TWVpbiBUZXN0", base64);
		Assert.assertEquals(input, back);
		
		logger.debug("Input: "+input);
		logger.debug("Base64: "+base64);
		logger.debug("Clear: "+back);
	}
	
	public static void main(String[] args)
    {
		UtilsJsfTstBootstrap.init();		
		Scanner sc = new Scanner(System.in);
		Contact c = new Contact();
		
		logger.info("Enter name");
		String name = sc.nextLine();
		
		logger.info("Street:");
		String street = sc.nextLine();
		
		logger.info("City");
		String city = sc.nextLine();
		
		logger.info("Phone");
		String phone = sc.nextLine();
		
		logger.info("Mail");
		String mail = sc.nextLine();
		
		StringBuffer sb = new StringBuffer();
		sb.append("<l:contact");
		if(name!=null && name.length()>0){sb.append(" name=\"").append(c.encode(name)).append("\"");}
		if(street!=null && street.length()>0){sb.append(" street=\"").append(c.encode(street)).append("\"");}
		if(city!=null && city.length()>0){sb.append(" city=\"").append(c.encode(city)).append("\"");}
		if(phone!=null && phone.length()>0){sb.append(" phone=\"").append(c.encode(phone)).append("\"");}
		if(mail!=null && mail.length()>0){sb.append(" mail=\"").append(c.encode(mail)).append("\"");}
		sb.append("/>");
		
		System.out.println(sb.toString());
    }
}