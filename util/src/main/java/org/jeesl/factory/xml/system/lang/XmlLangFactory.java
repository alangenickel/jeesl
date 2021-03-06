package org.jeesl.factory.xml.system.lang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;
import net.sf.ahtutils.xml.status.Lang;

public class XmlLangFactory<L extends UtilsLang>
{
	final static Logger logger = LoggerFactory.getLogger(XmlLangFactory.class);
		
	private Lang q;
	
	public XmlLangFactory(Lang q)
	{
		this.q=q;
	}
	
	public Lang getUtilsLang(L ejb)
	{
		Lang lang = new Lang();
		if(q.isSetKey()){lang.setKey(ejb.getLkey());}
		if(q.isSetTranslation()){lang.setTranslation(ejb.getLang());}
		return lang;
	}
	
	public static Lang create(String key, String translation)
	{
		Lang xml = new Lang();
		xml.setKey(key);
		xml.setTranslation(translation);
		return xml;
	}
	
	public static <T extends EjbWithLang<L>, L extends UtilsLang> String label(String localeCode, T ejb)
	{
		if(ejb.getName()!=null)
		{
			if(ejb.getName().containsKey(localeCode)){return ejb.getName().get(localeCode).getLang();}
			else
			{
				String msg = "No translation "+localeCode+" available in "+ejb;
				logger.warn(msg);
				return msg;
			}
		}
		else
		{
			String msg = "No @name available in "+ejb;
			logger.warn(msg);
			return msg;
		}
	}
}