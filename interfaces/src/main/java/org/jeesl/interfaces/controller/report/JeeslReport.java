package org.jeesl.interfaces.controller.report;

import org.jeesl.interfaces.model.system.io.report.JeeslIoReport;

public interface JeeslReport <REPORT extends JeeslIoReport<?,?,?,?>>
{		
	REPORT getIoReport();
}