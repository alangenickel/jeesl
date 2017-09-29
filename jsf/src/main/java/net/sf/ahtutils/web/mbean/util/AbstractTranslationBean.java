package net.sf.ahtutils.web.mbean.util;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jeesl.api.bean.JeeslTranslationBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.msgbundle.TranslationFactory;
import net.sf.ahtutils.msgbundle.TranslationMap;
import net.sf.exlp.util.xml.JaxbUtil;
import net.sf.exlp.xml.io.Dir;

public class AbstractTranslationBean implements Serializable,JeeslTranslationBean
{
	final static Logger logger = LoggerFactory.getLogger(AbstractTranslationBean.class);
	private static final long serialVersionUID = 1L;
	
	private TranslationMap tm;
	protected List<String> langKeys; public List<String> getLangKeys(){return langKeys;}
	
 
	protected void initMap(ClassLoader cl, String fXml)
    {
		StringBuilder sb = new StringBuilder();
		sb.append("Init "+TranslationMap.class.getSimpleName()+" with "+fXml);
		try
		{
			Dir dir = JaxbUtil.loadJAXB(cl,fXml, Dir.class);
			TranslationFactory tFactory = new TranslationFactory();
			for(net.sf.exlp.xml.io.File f : dir.getFile())
			{
				tFactory.add(cl,dir.getName()+"/"+f.getName());
			}
			tm = tFactory.gettMap();
			langKeys = tm.getLangKeys();
			sb.append(" ").append(StringUtils.join(langKeys, ", " ));
			logger.info(sb.toString());
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			sb.append(" ").append(e.getMessage());
			logger.error(sb.toString());
		}
    }
	
	public void overrideLangKeys(String... key)
	{
	    	langKeys.clear();
	    	for(String s : key)
	    	{
	    		langKeys.add(s);
	    	}
	}
    
    @Override public String get(String lang, String key){return tm.translate(lang, key);}
}