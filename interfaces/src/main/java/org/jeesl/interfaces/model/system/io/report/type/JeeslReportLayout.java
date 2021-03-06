package org.jeesl.interfaces.model.system.io.report.type;

public interface JeeslReportLayout
{
	public static enum Code{columnWidth,alignment}
	
	public static enum ColumnWidth{none,auto,min,max}
	public static enum Style{header,cell,footer}
	public static enum Color{background}
	public static enum Alignment{left,center,right}
	
	public static enum Data{string,dble,lng,intgr,dte,bool}
}