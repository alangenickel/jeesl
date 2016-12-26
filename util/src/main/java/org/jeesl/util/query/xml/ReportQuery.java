package org.jeesl.util.query.xml;

import java.util.Hashtable;
import java.util.Map;

import org.jeesl.factory.xml.system.io.report.XmlQueriesFactory;
import org.jeesl.factory.xml.system.io.report.XmlSheetsFactory;
import org.jeesl.factory.xml.system.status.XmlCategoryFactory;
import org.jeesl.factory.xml.system.status.XmlImplementationFactory;

import net.sf.ahtutils.xml.aht.Query;
import net.sf.ahtutils.xml.report.ColumnGroup;
import net.sf.ahtutils.xml.report.Report;
import net.sf.ahtutils.xml.report.XlsColumn;
import net.sf.ahtutils.xml.report.XlsSheet;
import net.sf.ahtutils.xml.report.XlsWorkbook;

public class ReportQuery
{
	public static enum Key {exReport}
	
	private static Map<Key,Query> mQueries;
	
	public static Query get(Key key){return get(key,null);}
	public static Query get(Key key,String lang)
	{
		if(mQueries==null){mQueries = new Hashtable<Key,Query>();}
		if(!mQueries.containsKey(key))
		{
			Query q = new Query();
			switch(key)
			{
				case exReport: q.setReport(exReport());break;
			}
			mQueries.put(key, q);
		}
		Query q = mQueries.get(key);
		q.setLang(lang);
		return q;
	}
	
	private static Report exReport()
	{
		Report xml = new Report();
		xml.setCode("");
		xml.setPosition(0);
		xml.setVisible(true);
		
		xml.setLangs(StatusQuery.langs());
		xml.setDescriptions(StatusQuery.descriptions());
		
		xml.setCategory(XmlCategoryFactory.create(""));
		xml.setImplementation(XmlImplementationFactory.build(""));
		
		xml.setXlsWorkbook(exportWorkbook());
		
		return xml;
	}
	
	private static XlsWorkbook exportWorkbook()
	{
		XlsWorkbook xml = new XlsWorkbook();
		xml.setXlsSheets(XmlSheetsFactory.build());
		xml.getXlsSheets().getXlsSheet().add(exportSheet());
		return xml;
	}
	
	private static XlsSheet exportSheet()
	{
		XlsSheet xml = new XlsSheet();
		xml.setCode("");
		xml.setPosition(0);
		xml.setVisible(true);
		
		xml.getContent().add(StatusQuery.langs());
		xml.getContent().add(StatusQuery.descriptions());
		xml.getContent().add(exportColumnGroup());
		xml.getContent().add(XmlQueriesFactory.build());
//		xml.setLangs(StatusQuery.langs());
//		xml.setDescriptions(StatusQuery.descriptions());
		
		return xml;
	}
	
	private static ColumnGroup exportColumnGroup()
	{
		ColumnGroup xml = new ColumnGroup();
		xml.setCode("");
		xml.setPosition(0);
		xml.setVisible(true);
		xml.setShowLabel(true);
		
		xml.setLangs(StatusQuery.langs());
		xml.setDescriptions(StatusQuery.descriptions());
		
		xml.getXlsColumn().add(exportColumn());
		return xml;
	}
	
	private static XlsColumn exportColumn()
	{
		XlsColumn xml = new XlsColumn();
		xml.setCode("");
		xml.setPosition(0);
		xml.setVisible(true);
		
		xml.setQueries(XmlQueriesFactory.build());
		
		xml.setLangs(StatusQuery.langs());
		xml.setDescriptions(StatusQuery.descriptions());
		return xml;
	}
}