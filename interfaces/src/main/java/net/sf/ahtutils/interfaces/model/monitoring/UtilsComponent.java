package net.sf.ahtutils.interfaces.model.monitoring;

import net.sf.ahtutils.model.interfaces.with.EjbWithCode;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithName;

public interface UtilsComponent<C extends UtilsComponent<C,I>,I extends UtilsIndicator<C,I>>
		extends EjbWithId,EjbWithName,EjbWithCode
{

}