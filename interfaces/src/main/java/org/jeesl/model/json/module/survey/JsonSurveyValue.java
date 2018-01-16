package org.jeesl.model.json.module.survey;

import java.io.Serializable;

import org.apache.commons.lang.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class JsonSurveyValue implements Serializable, EjbWithId
{
	public static final long serialVersionUID=1;

	public JsonSurveyValue(){}

	@JsonProperty("Id")
	private long id;
	@Override public long getId() {return id;}
	@Override public void setId(long id) {this.id = id;}
	
	@JsonProperty("Count")
	private Long count;
	public Long getCount() {return count;}
	public void setCount(Long count) {this.count = count;}
	
	@JsonProperty("Ejb")
	private EjbWithId ejb;
	public EjbWithId getEjb() {return ejb;}
	public void setEjb(EjbWithId ejb) {this.ejb = ejb;}
	
	
	@Override public String toString()
	{
		StringBuffer sb = new StringBuffer();
		
		return sb.toString();
	}
	
	@Override public boolean equals(Object object) {return (object instanceof JsonSurveyValue) ? id == ((JsonSurveyValue) object).getId() : (object == this);}
	@Override public int hashCode() {return new HashCodeBuilder(17,51).append(id).toHashCode();}
}