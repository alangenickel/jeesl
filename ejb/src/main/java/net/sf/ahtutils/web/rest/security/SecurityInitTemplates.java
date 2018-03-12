package net.sf.ahtutils.web.rest.security;

import org.jeesl.api.facade.system.JeeslSecurityFacade;
import org.jeesl.api.rest.system.security.JeeslSecurityRestTemplateImport;
import org.jeesl.controller.db.updater.JeeslDbCodeEjbUpdater;
import org.jeesl.factory.xml.system.io.sync.XmlDataUpdateFactory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityView;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityTemplate;
import org.jeesl.interfaces.model.system.security.user.JeeslUser;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityAction;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityUsecase;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityCategory;
import org.jeesl.interfaces.model.system.security.framework.JeeslSecurityRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.xml.security.Security;
import net.sf.ahtutils.xml.sync.DataUpdate;

public class SecurityInitTemplates <L extends UtilsLang,
 								D extends UtilsDescription, 
 								C extends JeeslSecurityCategory<L,D>,
 								R extends JeeslSecurityRole<L,D,C,V,U,A,USER>,
 								V extends JeeslSecurityView<L,D,C,R,U,A>,
 								U extends JeeslSecurityUsecase<L,D,C,R,V,A>,
 								A extends JeeslSecurityAction<L,D,R,V,U,AT>,
 								AT extends JeeslSecurityTemplate<L,D,C>,
 								USER extends JeeslUser<R>>
		extends AbstractSecurityInit<L,D,C,R,V,U,A,AT,USER>
		implements JeeslSecurityRestTemplateImport
{
	final static Logger logger = LoggerFactory.getLogger(SecurityInitTemplates.class);
	
	private JeeslDbCodeEjbUpdater<R> updateRole;
	
	public SecurityInitTemplates(final Class<L> cL, final Class<D> cD,final Class<C> cC,final Class<R> cR, final Class<V> cV,final Class<U> cU,final Class<A> cA,final Class<AT> cT,final Class<USER> cUser,JeeslSecurityFacade<L,D,C,R,V,U,A,AT,USER> fAcl)
	{       
        super(cL,cD,cC,cR,cV,cU,cA,cT,cUser,fAcl);
	}
	
	public DataUpdate iuSecurityTemplates(Security security)
	{
		updateRole = JeeslDbCodeEjbUpdater.createFactory(cR);
		updateRole.dbEjbs(fSecurity.all(cR));

		DataUpdate du = XmlDataUpdateFactory.build();
		logger.warn("NYI iuSecurityTemplates");
/*		try
		{
			iuCategory(security, UtilsSecurityCategory.Type.role);
			du.setResult(XmlResultFactory.buildOk());
		}
		catch (UtilsConfigurationException e)
		{
			e.printStackTrace();
			du.setResult(XmlResultFactory.buildFail());
		}
		
		updateRole.remove(fSecurity);
		logger.trace("iuRoles finished");
*/
		return du;
	}
	
	@Override protected void iuChilds(C category, net.sf.ahtutils.xml.security.Category templates) throws UtilsConfigurationException
	{
		if(templates.isSetTemplates() && templates.getTemplates().isSetTemplate())
		{
			for(net.sf.ahtutils.xml.security.Template template : templates.getTemplates().getTemplate())
			{
				updateRole.actualAdd(template.getCode());
				iuTemplate(category, template);
			}
		}
	}
	
	private void iuTemplate(C category, net.sf.ahtutils.xml.security.Template role) throws UtilsConfigurationException
	{
		AT ejb;
		try
		{
			ejb = fSecurity.fByCode(cT,role.getCode());
			ejbLangFactory.rmLang(fSecurity,ejb);
			ejbDescriptionFactory.rmDescription(fSecurity,ejb);
		}
		catch (UtilsNotFoundException e)
		{
			try
			{
				ejb = cT.newInstance();
				ejb.setCategory(category);
				ejb.setCode(role.getCode());
				ejb = fSecurity.persist(ejb);				
			}
			catch (InstantiationException e2) {throw new UtilsConfigurationException(e2.getMessage());}
			catch (IllegalAccessException e2) {throw new UtilsConfigurationException(e2.getMessage());}
			catch (UtilsConstraintViolationException e2) {throw new UtilsConfigurationException(e2.getMessage());}
		}
		
		try
		{
			if(role.isSetVisible()){ejb.setVisible(role.isVisible());}else{ejb.setVisible(true);}
			if(role.isSetPosition()){ejb.setPosition(role.getPosition());}else{ejb.setPosition(0);}
			
			ejb.setName(ejbLangFactory.getLangMap(role.getLangs()));
			ejb.setDescription(ejbDescriptionFactory.create(role.getDescriptions()));
			ejb.setCategory(category);
			ejb=fSecurity.update(ejb);
		}
		catch (UtilsConstraintViolationException e) {logger.error("",e);}
		catch (UtilsLockingException e) {logger.error("",e);}
	}
}