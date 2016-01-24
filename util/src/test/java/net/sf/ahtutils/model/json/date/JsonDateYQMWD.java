package net.sf.ahtutils.model.json.date;

import net.sf.ahtutils.interfaces.model.date.EjbWithYear;
import net.sf.ahtutils.interfaces.model.date.EntityWithDay;
import net.sf.ahtutils.interfaces.model.date.EntityWithMonth;
import net.sf.ahtutils.interfaces.model.date.EntityWithQuarter;
import net.sf.ahtutils.interfaces.model.date.EntityWithWeek;

public class JsonDateYQMWD implements EjbWithYear,EntityWithQuarter,EntityWithMonth,EntityWithWeek,EntityWithDay
{
	private int year;
	@Override public int getYear() {return year;}
	@Override public void setYear(int year) {this.year = year;}
	
	private String quarter;
	@Override public String getQuarter() {return quarter;}
	@Override public void setQuarter(String quarter) {this.quarter = quarter;}

	private int month;
	@Override public int getMonth() {return month;}
	@Override public void setMonth(int month) {this.month = month;}
	
	private int week;
	@Override public int getWeek() {return week;}
	@Override public void setWeek(int week) {this.week = week;}
	
	private int day;
	@Override public int getDay() {return day;}
	@Override public void setDay(int day) {this.day = day;}
}