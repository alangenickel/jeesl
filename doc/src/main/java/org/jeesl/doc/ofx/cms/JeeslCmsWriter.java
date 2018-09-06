package org.jeesl.doc.ofx.cms;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import org.jeesl.interfaces.model.system.io.cms.JeeslIoCms;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsContent;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsElement;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsSection;
import org.jeesl.interfaces.model.system.io.cms.JeeslIoCmsVisiblity;
import org.openfuxml.content.ofx.Section;
import org.openfuxml.content.ofx.Sections;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.exception.OfxConfigurationException;
import org.openfuxml.factory.xml.ofx.content.structure.XmlSectionsFactory;
import org.openfuxml.interfaces.configuration.ConfigurationProvider;
import org.openfuxml.renderer.latex.content.structure.LatexSectionRenderer;
import org.openfuxml.renderer.latex.preamble.LatexSrcBook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class JeeslCmsWriter<CMS extends JeeslIoCms<?,?,?,?,?>>
{	
	final static Logger logger = LoggerFactory.getLogger(JeeslCmsWriter.class);
	
	private final File latexBaseDir;
	private ConfigurationProvider cp;
	
	public JeeslCmsWriter(ConfigurationProvider cp, File latexBaseDir)
	{
		this.latexBaseDir=latexBaseDir;
		this.cp=cp;
	}
	
	public void render(CMS cms, Sections sections) throws OfxConfigurationException, IOException, OfxAuthoringException, UtilsConfigurationException
	{
		Writer w = new PrintWriter(new File(latexBaseDir,"srs.tex"));
		
		LatexSrcBook srcBook = new LatexSrcBook(cp);
		srcBook.reportPackages();
		srcBook.graphicsPath("pdf","png");
		srcBook.chapterSectionMarks();
//		logger.info("CMS: "+cms.getName().get("en").getLang());
//		srcBook.fancyHeader("AHT", cms.getName().get("en").getLang());
		srcBook.fancyHeader("AHT", "");
		srcBook.fancyFooter("");
//		srcBook.draft(false);
		srcBook.hyphenation();
		
		srcBook.beginDocument();
//		if(cms.getToc()) {srcBook.toc();}
		
		srcBook.toc();
		
		for(Section section : XmlSectionsFactory.toList(sections))
		{
			LatexSectionRenderer rSection = new LatexSectionRenderer(cp,0,srcBook);
			rSection.render(section);
			srcBook.addSectionRenderer(rSection);
		}
		
		srcBook.endDocument();
		srcBook.write(w);
//		jlr.copyPackages();
	}
}