package net.sf.ahtutils.interfaces.model.system.revision;

import org.hibernate.envers.RevisionType;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityAction;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityCategory;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityRole;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityUsecase;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityView;
import net.sf.ahtutils.interfaces.model.system.security.UtilsUser;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public interface UtilsRevisionContainer <REV extends UtilsRevision,
										T extends EjbWithId,
										L extends UtilsLang,
										D extends UtilsDescription,
										C extends UtilsSecurityCategory<L,D,C,R,V,U,A,USER>,
										R extends UtilsSecurityRole<L,D,C,R,V,U,A,USER>,
										V extends UtilsSecurityView<L,D,C,R,V,U,A,USER>,
										U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,USER>,
										A extends UtilsSecurityAction<L,D,C,R,V,U,A,USER>,
										USER extends UtilsUser<L,D,C,R,V,U,A,USER>>
{					
	REV getInfo();
	
	RevisionType getType();
	
	USER getUser();
	void setUser(USER user);
	
	T getEntity();
}