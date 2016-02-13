package net.sf.ahtutils.report.revision;

import org.apache.commons.jxpath.JXPathContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.facade.UtilsRevisionFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevision;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionAttribute;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionEntity;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionEntityMapping;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionScope;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionView;
import net.sf.ahtutils.interfaces.model.system.revision.UtilsRevisionViewMapping;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityAction;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityActionTemplate;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityCategory;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityRole;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityUsecase;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityView;
import net.sf.ahtutils.interfaces.model.system.security.UtilsUser;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.xml.audit.Scope;

public class RevisionEngineScopeResolver<L extends UtilsLang,D extends UtilsDescription,
							RC extends UtilsStatus<RC,L,D>,
							RV extends UtilsRevisionView<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
							RVM extends UtilsRevisionViewMapping<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
							RS extends UtilsRevisionScope<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
							RST extends UtilsStatus<RST,L,D>,
							RE extends UtilsRevisionEntity<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
							REM extends UtilsRevisionEntityMapping<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
							RA extends UtilsRevisionAttribute<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT>,
							RAT extends UtilsStatus<RAT,L,D>,
							REV extends UtilsRevision,
							C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
							R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,
							V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,
							U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
							A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,
							AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,
							USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
{
	final static Logger logger = LoggerFactory.getLogger(RevisionEngineScopeResolver.class);
	
	private UtilsRevisionFacade<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT> fRevision;
	
	private RevisionEngineAttributeResolver<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT,REV,C,R,V,U,A,AT,USER> rear;
	
	public RevisionEngineScopeResolver(UtilsRevisionFacade<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT> fRevision, RevisionEngineAttributeResolver<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RAT,REV,C,R,V,U,A,AT,USER> rear)
	{
		this.fRevision=fRevision;
		this.rear=rear;
	}
	
	public Scope build(String lang, RVM rvm, JXPathContext context, Object oChild)
	{
		UtilsRevisionEntityMapping.Type type = UtilsRevisionEntityMapping.Type.valueOf(rvm.getEntityMapping().getType().getCode());
		try{
		switch(type)
		{
			case xpath: return xpath(lang,rvm,context,oChild);
			case jpqlTree: return jpqlTree(lang,rvm,context,oChild);
			default: return null;
		}
		}
		catch (ClassNotFoundException e){e.printStackTrace();} catch (UtilsNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private Scope xpath(String lang, RVM rvm, JXPathContext context, Object oChild)
	{
		Object oScope = getXPathScopeObject(rvm,context,oChild);
		JXPathContext ctx = getXPathContext(rvm,context,oScope); 
		return build(lang,oScope,rvm.getEntityMapping().getScope(),ctx);
	}
	
	private Scope build(String lang, Object oScope, RS scope, JXPathContext ctx)
	{
		Scope xScope = new Scope();
		xScope.setClazz(oScope.getClass().getName());
		xScope.setCategory(scope.getCategory().getName().get(lang).getLang());
		
		if(oScope instanceof EjbWithId){xScope.setId(((EjbWithId)oScope).getId());}
		StringBuffer sb = new StringBuffer();
		for(RA attribute : scope.getAttributes())
		{
			if(attribute.isShowPrint())
			{
				sb.append(rear.build(attribute, ctx));
				sb.append(" ");
			}
		}
		xScope.setEntity(sb.toString().trim());
		return xScope;
	}
	
	private Object getXPathScopeObject(RVM rvm, JXPathContext context, Object oChild)
	{
		if(rvm.getEntityMapping().getXpath().trim().length()==0){return oChild;}
		else{return context.getValue(rvm.getEntityMapping().getXpath());}
	}
	
	private JXPathContext getXPathContext(RVM rvm, JXPathContext context, Object oScope)
	{
		if(rvm.getEntityMapping().getXpath().trim().length()==0) {return context;}
		else {return JXPathContext.newContext(oScope);}
	}
	
	@SuppressWarnings("unchecked")
	private Scope jpqlTree(String lang, RVM rvm, JXPathContext context, Object oChild) throws ClassNotFoundException, UtilsNotFoundException
	{
		Long id = (Long)getXPathScopeObject(rvm,context,oChild);
		Class<EjbWithId> c = (Class<EjbWithId>)Class.forName(rvm.getEntityMapping().getScope().getCode()).asSubclass(EjbWithId.class);
		
		EjbWithId oScope = fRevision.jpaTree(c, rvm.getEntityMapping().getJpqlTree(), id);
		return build(lang,oScope,rvm.getEntityMapping().getScope(),JXPathContext.newContext(oScope));
	}
}