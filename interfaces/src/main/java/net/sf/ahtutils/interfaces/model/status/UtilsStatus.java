package net.sf.ahtutils.interfaces.model.status;

import org.jeesl.interfaces.model.system.with.code.EjbWithCode;

import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.with.EjbWithLangDescription;
import net.sf.ahtutils.interfaces.model.with.image.EjbWithImage;
import net.sf.ahtutils.interfaces.model.with.image.EjbWithImageAlt;
import net.sf.ahtutils.interfaces.model.with.parent.EjbWithParent;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionVisible;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface UtilsStatus<S extends UtilsStatus<S,L,D>, L extends UtilsLang, D extends UtilsDescription>
			extends EjbRemoveable,EjbWithId,EjbWithCode,EjbWithPositionVisible,EjbWithImage,EjbWithImageAlt,
						EjbWithLangDescription<L,D>,EjbWithParent
{	
	enum EjbAttributes{code,parent}
	
	String getStyle();
	void setStyle(String style);
	
	String getSymbol();
	void setSymbol(String symbol);
}