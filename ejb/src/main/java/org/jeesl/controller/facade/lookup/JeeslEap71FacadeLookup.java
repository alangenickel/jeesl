package org.jeesl.controller.facade.lookup;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.jeesl.api.facade.JeeslFacadeLookup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JeeslEap71FacadeLookup implements JeeslFacadeLookup
{
	final static Logger logger = LoggerFactory.getLogger(JeeslEap71FacadeLookup.class);
	
	private Context context;

	private final String appName;
	private final String moduleName;

	private int port; public int getPort() {return port;} public void setPort(int port) {this.port = port;}
	
	public JeeslEap71FacadeLookup(String appName)
	{
		this(appName,null);
	}
	public JeeslEap71FacadeLookup(String appName, String moduleName)
	{
		this.appName=appName;
		this.moduleName=moduleName;
		port = 8080;
	}

	@SuppressWarnings("unchecked")
	@Override public <F> F lookup(Class<F> facade) throws NamingException
	{
		buildContext();
		
		StringBuilder sb = new StringBuilder();
		sb.append("ejb:");
		if(moduleName==null) {sb.append("/");}
		sb.append(appName);
		if(moduleName!=null) {sb.append("/").append(moduleName);}
		sb.append("/").append(facade.getSimpleName()).append("Bean");
		sb.append("!").append(facade.getName());	
		logger.info("Looking up: "+sb.toString());
		
		return (F) context.lookup(sb.toString());
	}
	
	private void buildContext() throws NamingException
	{
		if(context==null)
		{
			Properties props = new Properties();
			props.put(Context.INITIAL_CONTEXT_FACTORY,  "org.wildfly.naming.client.WildFlyInitialContextFactory");
			props.put(Context.PROVIDER_URL, String.format("%s://%s:%d", "remote+http", "localhost", port));
			props.put("jboss.naming.client.ejb.context", true);
//			props.put(Context.SECURITY_PRINCIPAL, username)
//			props.put(Context.SECURITY_CREDENTIALS, password);
			context =  new InitialContext(props);
		}
	}
}