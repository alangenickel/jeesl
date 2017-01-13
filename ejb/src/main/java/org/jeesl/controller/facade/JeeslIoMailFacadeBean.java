package org.jeesl.controller.facade;

import javax.persistence.EntityManager;

import org.jeesl.factory.ejb.system.io.mail.EjbIoMailFactory;
import org.jeesl.factory.factory.MailFactoryFactory;
import org.jeesl.interfaces.facade.JeeslIoMailFacade;
import org.jeesl.interfaces.model.system.io.mail.JeeslIoMail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.controller.facade.UtilsFacadeBean;
import net.sf.ahtutils.exception.ejb.UtilsConstraintViolationException;
import net.sf.ahtutils.exception.ejb.UtilsNotFoundException;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class JeeslIoMailFacadeBean<L extends UtilsLang,D extends UtilsDescription,
									CATEGORY extends UtilsStatus<CATEGORY,L,D>,
									MAIL extends JeeslIoMail<L,D,CATEGORY,MAIL,STATUS>, STATUS extends UtilsStatus<STATUS,L,D>>
					extends UtilsFacadeBean
					implements JeeslIoMailFacade<L,D,CATEGORY,MAIL,STATUS>
{	
	final static Logger logger = LoggerFactory.getLogger(JeeslIoMailFacadeBean.class);
		
//	private final Class<CATEGORY> cCategory;
	private final Class<MAIL> cMail;
	private final Class<STATUS> cStatus;
	
	private EjbIoMailFactory<L,D,CATEGORY,MAIL,STATUS> efMail;
	
	public JeeslIoMailFacadeBean(EntityManager em, final Class<CATEGORY> cCategory, final Class<MAIL> cMail, final Class<STATUS> cStatus)
	{
		super(em);
//		this.cCategory=cCategory;
		this.cMail=cMail;
		this.cStatus=cStatus;
		MailFactoryFactory<L,D,CATEGORY,MAIL,STATUS> ff = MailFactoryFactory.factory(cMail);
		efMail = ff.mail();
	}
	
	@Override public void spoolMail(CATEGORY category, org.jeesl.model.xml.system.io.mail.Mail mail) throws UtilsConstraintViolationException, UtilsNotFoundException
	{
		STATUS status = this.fByCode(cStatus, JeeslIoMail.Status.queue);
		MAIL ejb = efMail.build(category,status,mail);
		ejb = this.persist(ejb);
		logger.info(cMail.getSimpleName()+" spooled with id="+ejb.getId());
	}
	
}