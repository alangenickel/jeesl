package net.sf.ahtutils.controller.factory.ejb.tracker;

import java.util.Date;

import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.model.interfaces.tracker.UtilsActionTracker;

public class EjbActionTrackerFactory<L extends UtilsLang,
				D extends UtilsDescription,
				C extends JeeslSecurityCategory<L,D>,
				R extends JeeslSecurityRole<L,D,C,V,U,A,USER>,
				V extends JeeslSecurityView<L,D,C,R,U,A>,
				U extends JeeslSecurityUsecase<L,D,C,R,V,A>,
				A extends JeeslSecurityAction<L,D,R,V,U,AT>,
				AT extends JeeslSecurityTemplate<L,D,C>,
				USER extends JeeslUser<R>,
				T extends UtilsActionTracker<L,D,C,R,V,U,A,AT,USER>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbActionTrackerFactory.class);
	
    final Class<T> clTracker;
	
    public static <L extends UtilsLang,
				D extends UtilsDescription,
				C extends JeeslSecurityCategory<L,D>,
				R extends JeeslSecurityRole<L,D,C,V,U,A,USER>,
				V extends JeeslSecurityView<L,D,C,R,U,A>,
				U extends JeeslSecurityUsecase<L,D,C,R,V,A>,
				A extends JeeslSecurityAction<L,D,R,V,U,AT>,
				AT extends JeeslSecurityTemplate<L,D,C>,
				USER extends JeeslUser<R>,
				T extends UtilsActionTracker<L,D,C,R,V,U,A,AT,USER>>
    	EjbActionTrackerFactory<L,D,C,R,V,U,A,AT,USER,T> createFactory(final Class<T> clTracker)
    {
        return new EjbActionTrackerFactory<L,D,C,R,V,U,A,AT,USER,T>(clTracker);
    }
    
    public EjbActionTrackerFactory(final Class<T> clTracker)
    {
        this.clTracker = clTracker;
    } 
    
    public T create(USER user)
    {
    	T ejb = null;
    	
    	try
    	{
			ejb = clTracker.newInstance();
			ejb.setUser(user);
			ejb.setRecord(new Date());
		}
    	catch (InstantiationException e) {e.printStackTrace();}
    	catch (IllegalAccessException e) {e.printStackTrace();}
    	
    	return ejb;
    }
}