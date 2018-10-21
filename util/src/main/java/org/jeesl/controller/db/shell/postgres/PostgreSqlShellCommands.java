package org.jeesl.controller.db.shell.postgres;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.exlp.exception.ExlpUnsupportedOsException;
import net.sf.exlp.shell.os.OsArchitectureUtil;

public class PostgreSqlShellCommands
{
	final static Logger logger = LoggerFactory.getLogger(PostgreSqlShellCommands.class);
	
	public static String createUser(String user, String dbUser, String dbPwd) throws ExlpUnsupportedOsException
	{
		StringBuilder sb = new StringBuilder();
		switch(OsArchitectureUtil.getArch())
		{
			case Win32:  sb.append("NYI");break;
			case OsX: 	 sb.append("psql");
							sb.append(" -U ").append(user);
							sb.append(" -c \"CREATE USER ").append(dbUser).append(" WITH PASSWORD '").append(dbPwd).append("';\"");
							break;
			default: OsArchitectureUtil.errorUnsupportedOS("rm dirX and rm dirY (only subdirectories)");break;
		}	
		return sb.toString();
	}
	
	public static String terminate(String user, String dbName) throws ExlpUnsupportedOsException
	{
		StringBuilder sb = new StringBuilder();
		switch(OsArchitectureUtil.getArch())
		{
			case Win32:  sb.append("NYI");break;
			case OsX: 	 sb.append("psql");
							sb.append(" -U ").append(user);
							sb.append(" -c \"SELECT pg_terminate_backend(pg_stat_activity.pid) FROM pg_stat_activity WHERE pg_stat_activity.datname = '").append(dbName).append("' AND pid <> pg_backend_pid();\"");
							break;
			default: OsArchitectureUtil.errorUnsupportedOS("rm dirX and rm dirY (only subdirectories)");break;
		}	
		return sb.toString();
	}

	public static String dropDatabase(String user, String db) throws ExlpUnsupportedOsException
	{
		StringBuilder sb = new StringBuilder();
		switch(OsArchitectureUtil.getArch())
		{
			case Win32:  sb.append("NYI");break;
			case OsX: 	 sb.append("psql");
							sb.append(" -U ").append(user);
							sb.append(" -c \"DROP DATABASE IF EXISTS ").append(db).append(";\"");
							;break;
			default: OsArchitectureUtil.errorUnsupportedOS("rm dirX and rm dirY (only subdirectories)");break;
		}	
		return sb.toString();
	}
	
	public static String createDatabase(String user, String dbName, String dbUser) throws ExlpUnsupportedOsException
	{
		StringBuilder sb = new StringBuilder();
		switch(OsArchitectureUtil.getArch())
		{
			case Win32:  sb.append("NYI");break;
			case OsX: 	 sb.append("psql");
							sb.append(" -U ").append(user);
							sb.append(" -c \"CREATE DATABASE ").append(dbName).append(" OWNER ").append(dbUser).append(" ENCODING 'UTF8';\"");
							;break;
			default: OsArchitectureUtil.errorUnsupportedOS("rm dirX and rm dirY (only subdirectories)");break;
		}	
		return sb.toString();
	}
	
	public static String postGis(String user, String dbName) throws ExlpUnsupportedOsException
	{
		StringBuilder sb = new StringBuilder();
		switch(OsArchitectureUtil.getArch())
		{
			case Win32:  sb.append("NYI");break;
			case OsX: 	 sb.append("psql");
							sb.append(" -U ").append(user);
							sb.append(" -d ").append(dbName);
							sb.append(" -c \"CREATE EXTENSION postgis;\"");
							break;
			default: OsArchitectureUtil.errorUnsupportedOS("rm dirX and rm dirY (only subdirectories)");break;
		}	
		return sb.toString();
	}
	
	public static String restoreDatabase(String user, String dbName, String dbFile) throws ExlpUnsupportedOsException
	{
		StringBuilder sb = new StringBuilder();
		switch(OsArchitectureUtil.getArch())
		{
			case Win32:  sb.append("NYI");break;
			case OsX: 	 sb.append("pg_restore");
							sb.append(" -U ").append(user);
							sb.append(" -d ").append(dbName);
							sb.append(" --no-owner --no-privileges --role=geojsf -n public ");
							sb.append(" \"").append(dbFile).append("\"");
							break;
			default: OsArchitectureUtil.errorUnsupportedOS("rm dirX and rm dirY (only subdirectories)");break;
		}	
		return sb.toString();
	}
	
	public static void work() throws IOException
	{
		ProcessBuilder pb = new ProcessBuilder("/usr/local/bin/mysql", "-u root", "-e \"DROP DATABASE IF EXISTS ofxdev;\"");
		Map<String, String> env = pb.environment();
		
		
		for(String kex : env.keySet())
		{
//			logger.info(kex+" "+env.get(kex));
		}
//		env.put("VAR1", "myValue");
//		env.remove("OTHERVAR");
//		env.put("VAR2", env.get("VAR1") + "suffix");
//		pb.directory(new File("/usr/local/bin"));
		File log = new File("log");

		Process p = pb.start();
		
		logger.info(IOUtils.toString(p.getErrorStream()));
	}
}