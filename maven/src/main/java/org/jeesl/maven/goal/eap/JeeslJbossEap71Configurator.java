package org.jeesl.maven.goal.eap;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.jboss.as.controller.client.ModelControllerClient;
import org.jeesl.controller.config.jboss.JbossConfigurator;
import org.jeesl.controller.config.jboss.JbossModuleConfigurator;

import net.sf.exlp.exception.ExlpConfigurationException;
import net.sf.exlp.util.config.ConfigLoader;
import net.sf.exlp.util.io.ExlpCentralConfigPointer;

@Mojo(name="eap71Config")
public class JeeslJbossEap71Configurator extends AbstractMojo
{
	@Parameter(defaultValue = "INFO")
    private String log;
	
	private enum DbType {mysql}
	private final Set<DbType> setFiles;
	
	public JeeslJbossEap71Configurator()
	{
		setFiles = new HashSet<DbType>();
	}
	
    public void execute() throws MojoExecutionException
    {
    	BasicConfigurator.configure();
    	org.apache.log4j.Logger.getRootLogger().setLevel(Level.toLevel(log));

    	Configuration config = config();
    	
    	String jbossDir = config.getString("eap.dir","/Volumes/ramdisk/jboss");
		File f = new File(jbossDir);
		getLog().info("JBoss EAP 7.1 directoy: "+f.getAbsolutePath());
    	
		ModelControllerClient client;
    	JbossModuleConfigurator jbossModule = new JbossModuleConfigurator(JbossModuleConfigurator.Product.eap,"7.1",jbossDir);
    	try {client = ModelControllerClient.Factory.create(InetAddress.getByName("localhost"), 9990);}
    	catch (UnknownHostException e) {throw new MojoExecutionException(e.getMessage());}
    	
    	JbossConfigurator jbossConfig = new JbossConfigurator(client);
	    	
	    String key = config.getString("eap.configurations");
	    getLog().warn("Keys: "+key);
	    String[] keys = key.split("-");
	    
	    try
	    {
	    	dbFiles(keys,config,jbossModule);
	    	dbDrivers(keys,config,jbossConfig);
	    	dbDs(keys,config,jbossConfig);
	    }
	    catch (IOException e) {throw new MojoExecutionException(e.getMessage());}
    }
    
    private Configuration config()
    {
    	try
		{
			String cfn = ExlpCentralConfigPointer.getFile("jeesl","eapConfig").getAbsolutePath();
			ConfigLoader.add(cfn);
			getLog().info("Using config in: "+cfn );
		}
		catch (ExlpConfigurationException e) {getLog().error("No additional "+ExlpCentralConfigPointer.class.getSimpleName()+" "+e.getMessage());}
		
		Configuration config = ConfigLoader.init();					
		return config;
    }
    
    private void dbFiles(String[] keys, Configuration config, JbossModuleConfigurator jbossModule) throws IOException
    {
    	for(String key : keys)
    	{
    		String type = config.getString("db."+key+".type");
        	DbType dbType = DbType.valueOf(type);
        	switch(dbType)
        	{
        		case mysql: if(!setFiles.contains(dbType))
        					{
        						jbossModule.mysql();
        						getLog().info("DB: MySQL ... files copied");
        						setFiles.add(dbType);
        					}
        					break;
        	}
    	}
    }
    
    private void dbDrivers(String[] keys, Configuration config, JbossConfigurator jbossConfig) throws IOException
    {
    	List<String> log = new ArrayList<String>();
    	for(String key : keys)
    	{
    		String type = config.getString("db."+key+".type");
        	DbType dbType = DbType.valueOf(type);
        	switch(dbType)
        	{
        		case mysql: if(!jbossConfig.driverExists("mysql"))
        					{
        						jbossConfig.createMysqlDriver();;
        						getLog().info("MySQL added");
        						log.add("mysql");
        					}
        					break;
        	}
    	}
    	getLog().info("DB Drivers: "+StringUtils.join(log, ", "));
    }
    
    private void dbDs(String[] keys, Configuration config, JbossConfigurator jbossConfig) throws IOException
    {
    	for(String key : keys)
    	{
    		String type = config.getString("db."+key+".type");
        	DbType dbType = DbType.valueOf(type);
        	switch(dbType)
        	{
        		case mysql: String ds = jbossConfig.createMysqlDatasource(config,key);
        								if(ds!=null)
        								{getLog().info("DS: "+ds);}
        	}
    	}
    	
    }
}