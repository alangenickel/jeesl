package net.sf.ahtutils.doc.ofx.util;

import java.util.ArrayList;
import java.util.List;

import org.openfuxml.content.ofx.Paragraph;
import org.openfuxml.content.ofx.Title;
import org.openfuxml.content.table.Cell;
import org.openfuxml.exception.OfxAuthoringException;
import org.openfuxml.factory.xml.ofx.content.structure.XmlParagraphFactory;
import org.openfuxml.factory.xml.ofx.content.text.XmlTitleFactory;
import org.openfuxml.factory.xml.table.OfxCellFactory;
import org.openfuxml.factory.xml.text.OfxTextFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.aht.Aht;
import net.sf.ahtutils.xml.status.Description;
import net.sf.ahtutils.xml.status.Descriptions;
import net.sf.ahtutils.xml.status.Lang;
import net.sf.ahtutils.xml.status.Langs;
import net.sf.ahtutils.xml.status.Status;
import net.sf.ahtutils.xml.xpath.StatusXpath;
import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;

public class OfxMultiLangFactory
{	
	final static Logger logger = LoggerFactory.getLogger(OfxMultiLangFactory.class);
	
	public static Title title(String[] keys, Langs langs){return title(keys,langs,null,null);}
	
	public static Title title(String[] keys, Langs langs, String prefix, String suffix)
	{
		Title title = XmlTitleFactory.build();
		for(String key : keys)
		{
			String text = "!!!No-Translation!!!";
			try
			{
				Lang lCaption = StatusXpath.getLang(langs,key);
				text = lCaption.getTranslation();
			}
			catch (ExlpXpathNotFoundException e) {e.printStackTrace();}
			catch (ExlpXpathNotUniqueException e) {e.printStackTrace();}
			
			title.getContent().add(OfxTextFactory.build(key,text));
		}
		return title;
	}
	
	public static Cell cell(String[] langs, String code, Aht aht)
	{
		if(aht!=null)
		{
			try
			{
				Status status = StatusXpath.getStatus(aht.getStatus(), code);
				return cell(langs,status.getLangs());
			}
			catch (ExlpXpathNotFoundException e) {}
			catch (ExlpXpathNotUniqueException e) {}
		}
		return OfxCellFactory.createParagraphCell(code);
	}
	
	public static Cell cell(String[] keys, Langs langs)
	{
		Cell cell = OfxCellFactory.build();
		cell.getContent().addAll(paragraph(keys,langs));
		return cell;
	}
	
	public static Cell cell(String[] keys, Descriptions descriptions) throws OfxAuthoringException
	{
		Cell cell = OfxCellFactory.build();
		cell.getContent().addAll(paragraph(keys,descriptions));
		return cell;
	}
	
	public static List<Paragraph> paragraph(String[] keys, Langs langs)
	{
		List<Paragraph> paragraphs = new ArrayList<Paragraph>();
		
		for(String key : keys)
		{
			Paragraph p = XmlParagraphFactory.build(key);
			String text = "!!!No-Translation!!!";
			try
			{
				Lang lCaption = StatusXpath.getLang(langs,key);
				text = lCaption.getTranslation();
			}
			catch (ExlpXpathNotFoundException e) {e.printStackTrace();}
			catch (ExlpXpathNotUniqueException e) {e.printStackTrace();}
			p.getContent().add(text);
			paragraphs.add(p);
		}
		return paragraphs;
	}
	
	public static List<Paragraph> paragraph(String[] keys, Descriptions descriptions) throws OfxAuthoringException
	{
		List<Paragraph> paragraphs = new ArrayList<Paragraph>();
		
		for(String key : keys)
		{
			Paragraph p = XmlParagraphFactory.build(key);
			String text = "!!!No-Translation!!!";
			try
			{
				Description d = StatusXpath.getDescription(descriptions, key);
				text = d.getValue();
			}
			catch (ExlpXpathNotFoundException e) {throw new OfxAuthoringException(e.getMessage());}
			catch (ExlpXpathNotUniqueException e) {throw new OfxAuthoringException(e.getMessage());}
			p.getContent().add(text);
			paragraphs.add(p);
		}
		return paragraphs;
	}
}