package org.jeesl.factory.xml.system.io.report;

import org.jeesl.interfaces.model.system.io.report.JeeslIoReport;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumn;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumnGroup;
import org.jeesl.interfaces.model.system.io.report.JeeslReportQueryType;
import org.jeesl.interfaces.model.system.io.report.JeeslReportRow;
import org.jeesl.interfaces.model.system.io.report.JeeslReportSheet;
import org.jeesl.interfaces.model.system.io.report.JeeslReportWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.factory.xml.status.XmlDescriptionsFactory;
import net.sf.ahtutils.factory.xml.status.XmlLangsFactory;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.xml.report.Queries;
import net.sf.ahtutils.xml.report.XlsColumn;

public class XmlColumnFactory <L extends UtilsLang,D extends UtilsDescription,
								CATEGORY extends UtilsStatus<CATEGORY,L,D>,
								REPORT extends JeeslIoReport<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RO,ENTITY,ATTRIBUTE>,
								IMPLEMENTATION extends UtilsStatus<IMPLEMENTATION,L,D>,
								WORKBOOK extends JeeslReportWorkbook<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RO,ENTITY,ATTRIBUTE>,
								SHEET extends JeeslReportSheet<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RO,ENTITY,ATTRIBUTE>,
								GROUP extends JeeslReportColumnGroup<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RO,ENTITY,ATTRIBUTE>,
								COLUMN extends JeeslReportColumn<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RO,ENTITY,ATTRIBUTE>,
								ROW extends JeeslReportRow<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,CDT,RO,ENTITY,ATTRIBUTE>,
								CDT extends UtilsStatus<CDT,L,D>,
								RO extends UtilsStatus<RO,L,D>,
								ENTITY extends EjbWithId,
								ATTRIBUTE extends EjbWithId,
								FILLING extends UtilsStatus<FILLING,L,D>,
								TRANSFORMATION extends UtilsStatus<TRANSFORMATION,L,D>
								>
{
	final static Logger logger = LoggerFactory.getLogger(XmlColumnFactory.class);
	
	private XlsColumn q;
	
	private XmlLangsFactory<L> xfLangs;
	private XmlDescriptionsFactory<D> xfDescriptions;
	
	public XmlColumnFactory(String localeCode, XlsColumn q)
	{
		this.q=q;
		if(q.isSetLangs()){xfLangs = new XmlLangsFactory<L>(q.getLangs());}
		if(q.isSetDescriptions()){xfDescriptions = new XmlDescriptionsFactory<D>(q.getDescriptions());}
	}
	
	public XlsColumn build(COLUMN column)
	{
		XlsColumn xml = new XlsColumn();
		
		if(q.isSetCode()){xml.setCode(column.getCode());}
		if(q.isSetVisible()){xml.setVisible(column.isVisible());}
		if(q.isSetPosition()){xml.setPosition(column.getPosition());}
		
		if(q.isSetQueries()){xml.setQueries(queries(column));}
		
		if(q.isSetLangs()){xml.setLangs(xfLangs.getUtilsLangs(column.getName()));}
		if(q.isSetDescriptions()){xml.setDescriptions(xfDescriptions.create(column.getDescription()));}
						
		return xml;
	}
	
	private Queries queries(COLUMN column)
	{
		Queries xml = XmlQueriesFactory.build();
		if(column.getQueryHeader()!=null){xml.getQuery().add(XmlQueryFactory.build(JeeslReportQueryType.Column.header, column.getQueryHeader()));}
		if(column.getQueryCell()!=null){xml.getQuery().add(XmlQueryFactory.build(JeeslReportQueryType.Column.cell, column.getQueryCell()));}
		if(column.getQueryFooter()!=null){xml.getQuery().add(XmlQueryFactory.build(JeeslReportQueryType.Column.footer, column.getQueryFooter()));}
		return xml;
	}
}