package org.jeesl.interfaces.model.system.io.domain;

import java.io.Serializable;
import java.util.List;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionParent;
import net.sf.ahtutils.model.interfaces.with.EjbWithDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslDomainQuery<L extends UtilsLang, D extends UtilsDescription,
										DOMAIN extends JeeslDomain<L,?>,
										PATH extends JeeslDomainPath<?,?,?,?,?>
										>
			extends Serializable,EjbWithId,
					EjbSaveable,EjbRemoveable,
					EjbWithPositionParent,EjbWithLang<L>,EjbWithDescription<D>
{
	public enum Attributes{domain}
	
	DOMAIN getDomain();
	void setDomain(DOMAIN domain);

	List<PATH> getPaths();
	void setPaths(List<PATH> paths);
}