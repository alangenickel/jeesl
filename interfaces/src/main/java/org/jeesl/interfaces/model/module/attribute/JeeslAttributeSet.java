package org.jeesl.interfaces.model.module.attribute;

import java.io.Serializable;
import java.util.List;

import org.jeesl.interfaces.model.system.with.code.EjbWithCode;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPosition;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionParent;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslAttributeSet <L extends UtilsLang, D extends UtilsDescription,
									CATEGORY extends UtilsStatus<CATEGORY,L,D>,
									ITEM extends JeeslAttributeItem<?,?>>
		extends Serializable,EjbSaveable,EjbRemoveable,
				EjbWithPosition,EjbWithPositionParent,EjbWithCode,
				EjbWithLang<L>,EjbWithDescription<D>
{
	public enum Attributes{category,refId,position}
	
	CATEGORY getCategory();
	void setCategory(CATEGORY category);
	
	Long getRefId();
	void setRefId(Long refId);
	
	List<ITEM> getItems();
	void setItems(List<ITEM> items);
}