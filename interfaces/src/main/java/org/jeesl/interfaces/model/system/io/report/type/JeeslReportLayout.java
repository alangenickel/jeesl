package org.jeesl.interfaces.model.system.io.report.type;

public interface JeeslReportLayout
{
	public static enum Code{columnWidth}
	public static enum ColumnWidth{none,auto,min}
	public static enum Style{header,cell}
	public static enum Color{background}
	
	public static enum Data{string,dble}
}