package net.sf.ahtutils.prototype.web.mbean.admin.system.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsLockingException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.bean.FacesMessageBean;
import net.sf.ahtutils.interfaces.facade.UtilsSecurityFacade;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatusFixedCode;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityAction;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityActionTemplate;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityCategory;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityRole;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityUsecase;
import net.sf.ahtutils.interfaces.model.system.security.UtilsSecurityView;
import net.sf.ahtutils.interfaces.model.system.security.UtilsUser;
import net.sf.ahtutils.jsf.util.PositionListReorderer;
import net.sf.ahtutils.web.mbean.util.AbstractLogMessage;
import net.sf.exlp.util.io.StringUtil;

public class AbstractAdminSecurityRoleBean <L extends UtilsLang,
											D extends UtilsDescription,
											C extends UtilsSecurityCategory<L,D,C,R,V,U,A,AT,USER>,
											R extends UtilsSecurityRole<L,D,C,R,V,U,A,AT,USER>,
											V extends UtilsSecurityView<L,D,C,R,V,U,A,AT,USER>,
											U extends UtilsSecurityUsecase<L,D,C,R,V,U,A,AT,USER>,
											A extends UtilsSecurityAction<L,D,C,R,V,U,A,AT,USER>,
											AT extends UtilsSecurityActionTemplate<L,D,C,R,V,U,A,AT,USER>,
											USER extends UtilsUser<L,D,C,R,V,U,A,AT,USER>>
			extends AbstractAdminSecurityBean<L,D,C,R,V,U,A,AT,USER>
					implements Serializable
{
	private static final long serialVersionUID = 1L;
	final static Logger logger = LoggerFactory.getLogger(AbstractAdminSecurityRoleBean.class);
			
	private List<R> roles; public List<R> getRoles(){return roles;}

	private R role; public R getRole(){return role;} public void setRole(R role) {this.role = role;}
	
	private boolean denyRemove; public boolean isDenyRemove(){return denyRemove;}
	
	
	public void initSuper(String[] langs, UtilsSecurityFacade<L,D,C,R,V,U,A,AT,USER> fSecurity, FacesMessageBean bMessage, final Class<L> cLang, final Class<D> cDescription, final Class<C> cCategory, final Class<R> cRole, final Class<V> cView, final Class<U> cUsecase, final Class<A> cAction, final Class<AT> cTemplate, final Class<USER> cUser)
	{
		categoryType = UtilsSecurityCategory.Type.role;
		initSecuritySuper(langs,fSecurity,bMessage,cLang,cDescription,cCategory,cRole,cView,cUsecase,cAction,cTemplate,cUser);
		
		opViews = fSecurity.all(cView);
		Collections.sort(opViews, comparatorView);
		
		opActions = new ArrayList<A>();
		opUsecases = fSecurity.all(cUsecase);
		
		roles = new ArrayList<R>();
	}
	
	@Override public void categorySelected() throws UtilsNotFoundException
	{
		reloadRoles();
		role=null;
	}
	@Override protected void categorySaved() throws UtilsNotFoundException
	{
		reloadRoles();
	}
	
	private void reloadRoles() throws UtilsNotFoundException
	{
		roles.clear();
		logger.trace(StringUtil.stars());
		for(R r : fSecurity.allForCategory(cRole,cCategory,category.getCode()))
		{
			logger.trace("Role "+r.toString());
			if(r.isVisible() | uiShowInvisible){roles.add(r);}
		}
		Collections.sort(roles, comparatorRole);
		
		logger.info("Reloaded "+roles.size()+" (uiShowInvisible:"+uiShowInvisible+")");
	}
	
	public void selectRole()
	{
		logger.trace(AbstractLogMessage.selectEntity(role));
		updateRole();
		role = efLang.persistMissingLangs(fSecurity,langs,role);
		role = efDescription.persistMissingLangs(fSecurity,langs,role);		
		role = fSecurity.load(cRole,role);
		reloadActions();
		
		Collections.sort(role.getViews(),comparatorView);
		Collections.sort(role.getActions(),comparatorAction);
		Collections.sort(role.getUsecases(),comparatorUsecase);
		
		tblView=null;
		tblAction=null;
		tblUsecase=null;
		
		denyRemove = false;
		if(role instanceof UtilsStatusFixedCode)
		{
			for(String fixed : ((UtilsStatusFixedCode)role).getFixedCodes())
			{
				if(fixed.equals(role.getCode())){denyRemove=true;}
			}
		}
	}
	
	private void updateRole()
	{
		role = fSecurity.load(cRole, role);
//		opTemplateHandler.setTbList(role.getTemplates());
	}

	private void reloadActions()
	{
		for(V v : role.getViews())
		{
			v = fSecurity.load(cView,v);
			opActions.addAll(v.getActions());
		}
	}

	//Role
	public void addRole() throws UtilsConstraintViolationException
	{
		logger.info(AbstractLogMessage.addEntity(cRole));
		role = efRole.create(category,"");
		role.setName(efLang.createEmpty(langs));
		role.setDescription(efDescription.createEmpty(langs));
	}
	public void rmRole() throws UtilsConstraintViolationException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.rmEntity(role));
		fSecurity.rm(role);
		role=null;
		reloadRoles();
		roleUpdatePerformed();
	}
	public void saveRole() throws UtilsConstraintViolationException, UtilsLockingException, UtilsNotFoundException
	{
		logger.info(AbstractLogMessage.saveEntity(role));
		role.setCategory(fSecurity.find(cCategory, role.getCategory()));
		role = fSecurity.save(role);
		updateRole();
		reloadRoles();
		roleUpdatePerformed();
	}
	
	protected void roleUpdatePerformed(){}
	
	//OverlayPanel
	public void opAddView() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(!role.getViews().contains(opView))
		{
			role.getViews().add(opView);
			role = fSecurity.save(role);
			opView = null;
			selectRole();
		}
	}
	public void opAddAction() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(!role.getActions().contains(opAction))
		{
			role.getActions().add(opAction);
			role = fSecurity.save(role);
			opAction = null;
			selectRole();
		}
	}
	public void opAddUsecase() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(!role.getUsecases().contains(opUsecase))
		{
			role.getUsecases().add(opUsecase);
			role = fSecurity.save(role);
			opUsecase = null;
			selectRole();
		}
	}
	
	//Overlay-Rm
	public void opRmView() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(tblView!=null && role.getViews().contains(tblView))
		{
			role.getViews().remove(tblView);
			role = fSecurity.save(role);
			tblView = null;
			selectRole();
		}
	}
	public void opRmAction() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(tblAction!=null && role.getActions().contains(tblAction))
		{
			role.getActions().remove(tblAction);
			role = fSecurity.save(role);
			tblAction = null;
			selectRole();
		}
	}
	public void opRmUsecase() throws UtilsConstraintViolationException, UtilsLockingException
	{
		if(tblUsecase!=null && role.getUsecases().contains(tblUsecase))
		{
			role.getUsecases().remove(tblUsecase);
			role = fSecurity.save(role);
			tblUsecase = null;
			selectRole();
		}
	}
	
	//Order
	protected void reorderRoles() throws UtilsConstraintViolationException, UtilsLockingException {PositionListReorderer.reorder(fSecurity, roles);}
}