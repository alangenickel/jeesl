package org.jeesl.factory.xml.system.io.ssi;

import org.jeesl.interfaces.model.system.io.ssi.JeeslIoSsiSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlSystemFactory<SYSTEM extends JeeslIoSsiSystem>
{
	final static Logger logger = LoggerFactory.getLogger(XmlSystemFactory.class);

	private final org.jeesl.model.xml.system.io.ssi.System q;

	public XmlSystemFactory(org.jeesl.model.xml.system.io.ssi.System q)
	{
		this.q=q;
	}
	
	public org.jeesl.model.xml.system.io.ssi.System build(SYSTEM system)
	{
		org.jeesl.model.xml.system.io.ssi.System xml = new org.jeesl.model.xml.system.io.ssi.System();

		if(q.isSetId()){xml.setId(system.getId());}
		if(q.isSetCode()){xml.setCode(system.getCode());}
	
		return xml;
	}
}