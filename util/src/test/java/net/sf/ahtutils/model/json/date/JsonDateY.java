package net.sf.ahtutils.model.json.date;

import net.sf.ahtutils.interfaces.model.date.EjbWithYear;

public class JsonDateY implements EjbWithYear
{
	private int year;
	@Override public int getYear() {return year;}
	@Override public void setYear(int year) {this.year = year;}
}