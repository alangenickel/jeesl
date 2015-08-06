package net.sf.ahtutils.controller.jboss;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.filter.Filters;
import org.jdom2.xpath.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.controller.jboss.JbossModuleConfigurator.Product;
import net.sf.exlp.util.io.resourceloader.MultiResourceLoader;
import net.sf.exlp.util.xml.JDomUtil;


public class JbossConfigConfigurator
{
	final static Logger logger = LoggerFactory.getLogger(JbossConfigConfigurator.class);
	
	private static final String srcBaseDir = "listing.aht-utils/admin/installation/jboss/config";
	
	private MultiResourceLoader mrl;
	
	private File jbossBaseDir;
	
	private Document doc;
	
	public JbossConfigConfigurator(Product product, String version,String jbossDir)
	{
		jbossBaseDir = new File(jbossDir);
		mrl = new MultiResourceLoader();
		
		String reference = srcBaseDir+"/"+product+"/"+version+"/standalone.xml";
		
		if(mrl.isAvailable(reference))
		{
			try {doc = JDomUtil.load(mrl.searchIs(reference));}
			catch (FileNotFoundException e) {e.printStackTrace();}
		}
		else
		{
			try
			{
				String local = "../doc/src/main/resources/"+reference;
				doc = JDomUtil.load(mrl.searchIs(local));
			}
			catch (FileNotFoundException e){logger.warn("You need to set the base-dir to doc in IntelliJ");e.printStackTrace();}
		}	
	}
	
	public void addDs(Element element)
	{
        XPathExpression<Element> xpee = XPathFactory.instance().compile("/ns1:server/ns1:profile/ns2:subsystem/ns2:datasources", Filters.element(), null, getNamespaceList());
        xpee.evaluateFirst(doc).addContent(0, element);
    }


	
	public void addDbDriver(Element element)
	{
        XPathExpression<Element> xpee = XPathFactory.instance().compile("/ns1:server/ns1:profile/ns2:subsystem/ns2:datasources/ns2:drivers", Filters.element(), null, getNamespaceList());
        xpee.evaluateFirst(doc).addContent(element);
	}
	
	public void changePublicInterface()
	{
        Element interfacePublic = new Element("any-address");
        XPathExpression<Element> xpe = XPathFactory.instance().compile("/ns1:server/ns1:interfaces", Filters.element(), null, Namespace.getNamespace("ns1", doc.getRootElement().getNamespaceURI()));
        Element ele = xpe.evaluateFirst(doc);
        for(Element e : ele.getChildren())
        {
            if (e.getAttribute("name").getValue().equals("public"))
            {
                e.getChildren().clear();
                e.addContent(interfacePublic);
                e.getChildren().get(0).setNamespace(e.getNamespace());
            }
        }
    }

    private List<Namespace> getNamespaceList()
    {
        List<Namespace> ns = new ArrayList<Namespace>();
        ns.add(Namespace.getNamespace("ns1", doc.getRootElement().getNamespaceURI()));
        ns.add(Namespace.getNamespace("ns2", doc.getRootElement().getChildren().get(2).getChildren().get(1).getNamespaceURI()));
        return ns;
    }

	
	public void write(File fTarget)
	{
		JDomUtil.debug(doc);
		logger.info("Writing to "+jbossBaseDir.getAbsolutePath());
	}
}