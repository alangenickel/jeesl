package org.jeesl.interfaces.model.system.job;

import java.io.Serializable;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslJobRobot<L extends UtilsLang,D extends UtilsDescription>
		extends Serializable,EjbWithId,EjbSaveable,EjbRemoveable,
					EjbWithLang<L>,EjbWithDescription<D>
{	

}