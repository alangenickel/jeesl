package org.jeesl.interfaces.model.system.io.revision;

import java.io.Serializable;

import org.jeesl.interfaces.model.system.with.code.EjbWithCode;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbPersistable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionParent;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionVisible;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslRevisionScope<L extends UtilsLang,D extends UtilsDescription,
									RC extends UtilsStatus<RC,L,D>,
									RA extends JeeslRevisionAttribute<L,D,?,?,?>>
		extends Serializable,EjbPersistable,EjbSaveable,EjbRemoveable,
				EjbWithPositionVisible,EjbWithParentAttributeResolver,EjbWithPositionParent,
				EjbWithCode,
				EjbWithLang<L>,EjbWithDescription<D>,EjbWithRevisionAttributes<RA>
{		
	RC getCategory();
	void setCategory(RC category);
}