package org.jeesl.web.mbean.prototype.user.selector;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.jeesl.interfaces.model.system.io.report.JeeslIoReport;
import org.jeesl.interfaces.model.system.io.report.JeeslReportCell;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumn;
import org.jeesl.interfaces.model.system.io.report.JeeslReportColumnGroup;
import org.jeesl.interfaces.model.system.io.report.JeeslReportRow;
import org.jeesl.interfaces.model.system.io.report.JeeslReportSheet;
import org.jeesl.interfaces.model.system.io.report.JeeslReportStyle;
import org.jeesl.interfaces.model.system.io.report.JeeslReportTemplate;
import org.jeesl.interfaces.model.system.io.report.JeeslReportWorkbook;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionAttribute;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionEntity;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionEntityMapping;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionScope;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionView;
import org.jeesl.interfaces.model.system.revision.UtilsRevisionViewMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;

public class AbstractReportSelectorBean <L extends UtilsLang,D extends UtilsDescription,
										CATEGORY extends UtilsStatus<CATEGORY,L,D>,
										REPORT extends JeeslIoReport<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE>,
										IMPLEMENTATION extends UtilsStatus<IMPLEMENTATION,L,D>,
										WORKBOOK extends JeeslReportWorkbook<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE>,
										SHEET extends JeeslReportSheet<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE>,
										GROUP extends JeeslReportColumnGroup<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE>,
										COLUMN extends JeeslReportColumn<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE>,
										ROW extends JeeslReportRow<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE>,
										TEMPLATE extends JeeslReportTemplate<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE>,
										CELL extends JeeslReportCell<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE>,
										STYLE extends JeeslReportStyle<L,D,CATEGORY,REPORT,IMPLEMENTATION,WORKBOOK,SHEET,GROUP,COLUMN,ROW,TEMPLATE,CELL,STYLE,CDT,CW,RT,ENTITY,ATTRIBUTE>,
										CDT extends UtilsStatus<CDT,L,D>,
										CW extends UtilsStatus<CW,L,D>,
										RT extends UtilsStatus<RT,L,D>,
										ENTITY extends EjbWithId,
										ATTRIBUTE extends EjbWithId,
										FILLING extends UtilsStatus<FILLING,L,D>,
										TRANSFORMATION extends UtilsStatus<TRANSFORMATION,L,D>,
										RC extends UtilsStatus<RC,L,D>,
										RV extends UtilsRevisionView<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,CDT>,
										RVM extends UtilsRevisionViewMapping<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,CDT>,
										RS extends UtilsRevisionScope<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,CDT>,
										RST extends UtilsStatus<RST,L,D>,
										RE extends UtilsRevisionEntity<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,CDT>,
										REM extends UtilsRevisionEntityMapping<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,CDT>,
										RA extends UtilsRevisionAttribute<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,CDT>
										>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractReportSelectorBean.class);
	
	
	protected Map<FILLING,Boolean> mapFilling; public Map<FILLING,Boolean> getMapFilling() {return mapFilling;}
	protected Map<TRANSFORMATION,Boolean> mapTransformation; public Map<TRANSFORMATION, Boolean> getMapTransformation() {return mapTransformation;}

	protected FILLING filling; public FILLING getFilling() {return filling;} public void setFilling(FILLING grouping) {this.filling = grouping;}
	protected TRANSFORMATION transformation; public TRANSFORMATION getTransformation() {return transformation;} public void setTransformation(TRANSFORMATION aggregation) {this.transformation = aggregation;}

	
	protected AbstractReportSelectorBean()
	{
		mapFilling = new HashMap<FILLING,Boolean>();
		mapTransformation = new HashMap<TRANSFORMATION,Boolean>();
	}
	
	public void toggleFilling(FILLING item)
	{
		logger.info(AbstractLogMessage.toggle(item));
		filling=item;
		mapFilling.clear();
		mapFilling.put(item, Boolean.TRUE);
	}
	
	public void toggleTransformation(TRANSFORMATION item)
	{
		logger.info(AbstractLogMessage.toggle(item));
		transformation=item;
		mapTransformation.clear();
		mapTransformation.put(item, Boolean.TRUE);
	}

}