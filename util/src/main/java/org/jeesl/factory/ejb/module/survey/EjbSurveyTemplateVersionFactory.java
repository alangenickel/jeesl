package org.jeesl.factory.ejb.module.survey;

import java.util.Date;

import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyTemplateVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbSurveyTemplateVersionFactory<VERSION extends JeeslSurveyTemplateVersion<?,?,?>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbSurveyTemplateVersionFactory.class);
	
	final Class<VERSION> cVersion;
    
	public EjbSurveyTemplateVersionFactory(final Class<VERSION> cVersion)
	{       
        this.cVersion = cVersion;
	}
	
	public VERSION build()
	{
		VERSION ejb = null;
		try
		{
			ejb = cVersion.newInstance();
			ejb.setRecord(new Date());
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}