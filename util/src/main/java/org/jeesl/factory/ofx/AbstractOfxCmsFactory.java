package org.jeesl.factory.ofx;

import java.util.List;

import org.jeesl.api.facade.io.JeeslIoCmsFacade;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCms;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsContent;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsElement;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsSection;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsVisiblity;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.factory.xml.ofx.content.structure.XmlSectionFactory;
import org.openfuxml.factory.xml.ofx.content.text.XmlTitleFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public abstract class AbstractOfxCmsFactory <L extends UtilsLang,D extends UtilsDescription,
								CAT extends UtilsStatus<CAT,L,D>,
								CMS extends JeeslIoCms<L,D,CAT,CMS,V,S,E,T,C,M,LOC>,
								V extends JeeslIoCmsVisiblity<L,D,CAT,CMS,V,S,E,T,C,M,LOC>,
								S extends JeeslIoCmsSection<L,D,CAT,CMS,V,S,E,T,C,M,LOC>,
								E extends JeeslIoCmsElement<L,D,CAT,CMS,V,S,E,T,C,M,LOC>,
								T extends UtilsStatus<T,L,D>,
								C extends JeeslIoCmsContent<L,D,CAT,CMS,V,S,E,T,C,M,LOC>,
								M extends UtilsStatus<M,L,D>,
								LOC extends UtilsStatus<LOC,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(AbstractOfxCmsFactory.class);
	
	protected final String localeCode;
	protected final JeeslIoCmsFacade<L,D,CAT,CMS,V,S,E,T,C,M,LOC> fCms;
	
	public AbstractOfxCmsFactory(String localeCode, JeeslIoCmsFacade<L,D,CAT,CMS,V,S,E,T,C,M,LOC> fCms)
	{
		this.localeCode=localeCode;
		this.fCms = fCms;
	}
 
	public Section build(S section)
	{
		Section xml = XmlSectionFactory.build();
		xml.getContent().add(XmlTitleFactory.build(section.getName().get(localeCode).getLang()));
		
		List<E> elements = fCms.fCmsElements(section);
		for(E e : elements)
		{
			xml.getContent().add(build(e));
		}
		
		for(S child : section.getSections())
		{
			xml.getContent().add(this.build(child));
		}
		
		return xml;
	}
	
	protected abstract Section build(E element);
}