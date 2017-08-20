package org.jeesl.doc.resources;

import org.jeesl.factory.xml.system.io.db.XmlSeedFactory;
import org.jeesl.interfaces.model.system.constraint.JeeslConstraintType;
import org.jeesl.interfaces.model.system.io.report.type.JeeslReportLayout;
import org.jeesl.interfaces.model.system.io.report.type.JeeslReportRowType;
import org.jeesl.interfaces.model.system.io.report.type.JeeslReportSetting;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphicStyle;
import org.jeesl.interfaces.model.system.symbol.JeeslGraphicType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.xml.dbseed.Db;

public class JeeslDbSeeds
{	
	final static Logger logger = LoggerFactory.getLogger(JeeslDbSeeds.class);
	
	public static void apply(String prefix, Db db)
	{
		applyRevision(db);
		applyGraphic(db);
		applyReport(db);
		applyMail(db);
		applySystem(prefix,db);
	}
	
	private static void applyGraphic(Db db)
	{
		db.getSeed().add(XmlSeedFactory.build(JeeslGraphicType.class.getName(),"jeesl/db/system/graphic/type.xml"));
		db.getSeed().add(XmlSeedFactory.build(JeeslGraphicStyle.class.getName(),"jeesl/db/system/graphic/style.xml"));
	}
	
	private static void applyRevision(Db db)
	{
		db.getSeed().add(XmlSeedFactory.build("RevisionScopeType","jeesl/db/system/io/revision/scopeType.xml"));
	}
	
	private static void applyReport(Db db)
	{
		db.getSeed().add(XmlSeedFactory.build(JeeslReportRowType.class.getName(),"jeesl/db/system/io/report/rowType.xml"));
		
		db.getSeed().add(XmlSeedFactory.build(JeeslReportLayout.class.getName()+":"+JeeslReportLayout.Code.columnWidth.toString(),"jeesl/db/system/io/report/style/alignment.xml"));
		db.getSeed().add(XmlSeedFactory.build(JeeslReportLayout.class.getName()+":"+JeeslReportLayout.Code.alignment.toString(),"jeesl/db/system/io/report/style/columnWidth.xml"));
		
		db.getSeed().add(XmlSeedFactory.build(JeeslReportSetting.class.getName()+":"+JeeslReportSetting.Type.filling,"jeesl/db/system/io/report/setting/filling.xml"));
		db.getSeed().add(XmlSeedFactory.build(JeeslReportSetting.class.getName()+":"+JeeslReportSetting.Type.transformation,"jeesl/db/system/io/report/setting/transformation.xml"));
		db.getSeed().add(XmlSeedFactory.build(JeeslReportSetting.class.getName()+":"+JeeslReportSetting.Type.implementation,"jeesl/db/system/io/report/setting/implementation.xml"));
	}
	
	private static void applyMail(Db db)
	{
		db.getSeed().add(XmlSeedFactory.build("IoMailRetention","jeesl/db/system/io/mail/retention.xml"));
		db.getSeed().add(XmlSeedFactory.build("IoMailStatus","jeesl/db/system/io/mail/status.xml"));
	}
	
	private static void applySystem(String prefix, Db db)
	{
		db.getSeed().add(XmlSeedFactory.build("SystemConstraintType",JeeslConstraintType.xmlResourceContainer));
		db.getSeed().add(XmlSeedFactory.build(prefix+"JobStatus","jeesl/db/system/job/status.xml"));
		db.getSeed().add(XmlSeedFactory.build(prefix+"JobType","jeesl/db/system/job/type.xml"));
		db.getSeed().add(XmlSeedFactory.build(prefix+"JobFeedbackType","jeesl/db/system/job/feedback.xml"));
		db.getSeed().add(XmlSeedFactory.build(prefix+"FeedbackStyle","jeesl/db/module/feedback/style.xml"));
	}
}