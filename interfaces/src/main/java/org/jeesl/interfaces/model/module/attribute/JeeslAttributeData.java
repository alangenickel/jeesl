package org.jeesl.interfaces.model.module.attribute;

import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface JeeslAttributeData <CRITERIA extends JeeslAttributeCriteria<?,?,?,?>,CONTAINER extends JeeslAttributeContainer<?,?>>
		extends EjbWithId
{
	CONTAINER getContainer();
	void setContainer(CONTAINER container);
	
	CRITERIA getCriteria();
	void setCriteria(CRITERIA criteria);

	String getValueString();
	void setValueString(String valueString);
	
	Integer getValueInteger();
	void setValueInteger(Integer valueInteger);
	
	Boolean getValueBoolean();
	void setValueBoolean(Boolean valueBoolean);
}