package org.jeesl.factory.builder.io;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.factory.ejb.system.io.mail.EjbIoMailFactory;
import org.jeesl.interfaces.model.system.io.mail.core.JeeslIoMail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class IoMailFactoryBuilder<L extends UtilsLang,D extends UtilsDescription,
								CATEGORY extends UtilsStatus<CATEGORY,L,D>,
								MAIL extends JeeslIoMail<L,D,CATEGORY,MAIL,STATUS,RETENTION>,
								STATUS extends UtilsStatus<STATUS,L,D>,
								RETENTION extends UtilsStatus<RETENTION,L,D>>
		extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(IoMailFactoryBuilder.class);
	
	private final Class<CATEGORY> cCategory; public Class<CATEGORY> getClassCategory(){return cCategory;}
	private final Class<MAIL> cMail; public Class<MAIL> getClassMail(){return cMail;}
	private final Class<STATUS> cStatus; public Class<STATUS> getClassStatus(){return cStatus;}
	private final Class<RETENTION> cRetention;  public Class<RETENTION> getClassRetention(){return cRetention;}
	
	public IoMailFactoryBuilder(final Class<L> cL, final Class<D> cD, final Class<CATEGORY> cCategory, final Class<MAIL> cMail, final Class<STATUS> cStatus, final Class<RETENTION> cRetention)
	{
		super(cL,cD);
		this.cCategory=cCategory;
		this.cMail = cMail;
		this.cStatus=cStatus;
		this.cRetention=cRetention;
	}
	
	public EjbIoMailFactory<L,D,CATEGORY,MAIL,STATUS,RETENTION> mail()
	{
		return new EjbIoMailFactory<L,D,CATEGORY,MAIL,STATUS,RETENTION>(cMail);
	}
}