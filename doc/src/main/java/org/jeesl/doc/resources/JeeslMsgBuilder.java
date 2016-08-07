package org.jeesl.doc.resources;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import net.sf.ahtutils.doc.UtilsDocumentation;
import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.xml.status.Translations;
import net.sf.exlp.util.io.FileIO;
import net.sf.exlp.util.io.resourceloader.MultiResourceLoader;
import net.sf.exlp.util.xml.JDomUtil;
import net.sf.exlp.util.xml.JaxbUtil;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.io.IOUtils;
import org.jdom2.Comment;
import org.jdom2.Document;
import org.jdom2.output.Format;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JeeslMsgBuilder
{	
	public static final String generic = "aht-utils/msg/generic.xml";
	public static final String query = "aht-utils/msg/query.xml";
	public static final String entities = "aht-utils/msg/module/entities.xml";
	public static final String srcProject = "msg.aht-utils/project.xml";
	public static final String srcDate = "aht-utils/msg/date.xml";
	
	//Finance
	public static final String srcFinance = "jeesl/msg/finance/finance.xml";
	public static final String srcAmount = "jeesl/msg/finance/amount.xml";
	public static final String financePeriod = "jeesl/msg/finance/period.xml";
	
	//Modules
	public static final String timeseries = "aht-utils/msg/module/timeseries.xml";
	public static final String io = "aht-utils/msg/module/io.xml";
	public static final String monitoring = "aht-utils/msg/module/monitoring.xml";
	
	public static final String tooltip = "aht-utils/msg/tooltip.xml";

	public static final String srcWizard = "msg.aht-utils/wizard.xml";
	public static final String srcAdminAuditLog = "msg.aht-utils/admin/system/auditLog.xml";
	public static final String srcAdminSync = "msg.aht-utils/admin/system/sync.xml";
	public static final String srcAdminSystemProperty = "msg.aht-utils/admin/system/properties.xml";
	public static final String srcAdminSystemTraffic = "msg.aht-utils/admin/system/trafficLights.xml";
	public static final String srcAdminSystemDbStatistic = "msg.aht-utils/admin/system/dbStatistic.xml";
	public static final String srcAdminSystemDbDump = "msg.aht-utils/admin/system/dbDump.xml";
	public static final String msgAdminSystemOptionTables = "msg.aht-utils/admin/system/options.xml";
	public static final String srcAdminSecurity = "aht-utils/msg/module/security.xml";
	public static final String srcAdminUser = "aht-utils/msg/admin/user.xml";
	public static final String srcAdminStatus = "aht-utils/msg/admin/status.xml";
	public static final String srcRevision = "aht-utils/msg/module/revision.xml";
	public static final String srcConstraint = "msg.aht-utils/system/constraint.xml";
	public static final String facesMessages = "aht-utils/msg/facesMessages.xml";
	
	public static final String srcSurvey = "msg.aht-utils/survey.xml";
	public static final String devQa = "msg.aht-utils/development/qa.xml";
	
	final static Logger logger = LoggerFactory.getLogger(JeeslMsgBuilder.class);
		
	private MultiResourceLoader mrl;
	private File baseMsg;
	
	public JeeslMsgBuilder(Configuration config)
	{
		mrl = new MultiResourceLoader();
		baseMsg = new File(config.getString(UtilsDocumentation.keyBaseMsgDir));
		logger.info("Using msg.dir ("+UtilsDocumentation.keyBaseMsgDir+"): "+baseMsg.getAbsolutePath());
	}
	
	public void copy(String src, String dst) throws UtilsConfigurationException
	{
		try
		{
			InputStream is = mrl.searchIs(src);
			File fTarget = new File(baseMsg,dst);
			
			Translations t = JaxbUtil.loadJAXB(is, Translations.class);
			Document doc = JaxbUtil.toDocument(t);
			
			Comment comment = new Comment("Do not modify this file, it is automatically generated!");
			doc.addContent(0, comment);
			
			byte[] bytes = IOUtils.toByteArray(JDomUtil.toInputStream(doc, Format.getPrettyFormat()));
			FileIO.writeFileIfDiffers(bytes, fTarget);
			logger.info("Written "+dst);
		}
		catch (FileNotFoundException e) {throw new UtilsConfigurationException(e.getMessage());}
		catch (IOException e) {throw new UtilsConfigurationException(e.getMessage());}
	}
}