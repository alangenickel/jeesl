package org.jeesl.interfaces.model.system.io.report;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.code.EjbWithCode;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionParent;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionVisible;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslReportColumn<L extends UtilsLang,D extends UtilsDescription,
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
									ATTRIBUTE extends EjbWithId>

		extends EjbWithId,EjbSaveable,
				EjbWithCode,EjbWithPositionVisible,EjbWithPositionParent,
				EjbWithLang<L>,EjbWithDescription<D>
{					
	GROUP getGroup();
	void setGroup(GROUP group);
	
	Boolean getShowLabel();
	void setShowLabel(Boolean showLabel);
	
	Boolean getShowWeb();
	void setShowWeb(Boolean showWeb);
	
	Boolean getShowHint();
	void setShowHint(Boolean showHint);
	
	CDT getDataType();
	void setDataType(CDT dataType);
	
	String getQueryHeader();
	void setQueryHeader(String queryHeader);
	
	String getQueryCell();
	void setQueryCell(String queryCell);
	
	String getQueryFooter();
	void setQueryFooter(String queryFooter);
	
	CW getColumWidth();
	void setColumWidth(CW columWidth);
	
	Integer getColumSize();
	void setColumSize(Integer columSize);
	
	STYLE getStyleHeader();
	void setStyleHeader(STYLE styleHeader);
	
	STYLE getStyleCell();
	void setStyleCell(STYLE styleCell);
	
	STYLE getStyleFooter();
	void setStyleFooter(STYLE styleFooter);
}