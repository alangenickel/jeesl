package org.jeesl.factory.builder.io;

import org.jeesl.factory.builder.AbstractFactoryBuilder;
import org.jeesl.factory.ejb.system.revision.EjbRevisionAttributeFactory;
import org.jeesl.factory.ejb.system.revision.EjbRevisionEntityFactory;
import org.jeesl.factory.ejb.system.revision.EjbRevisionMappingEntityFactory;
import org.jeesl.factory.ejb.system.revision.EjbRevisionMappingViewFactory;
import org.jeesl.factory.ejb.system.revision.EjbRevisionScopeFactory;
import org.jeesl.factory.ejb.system.revision.EjbRevisionViewFactory;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntityMapping;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionScope;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionView;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionViewMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class IoRevisionFactoryBuilder<L extends UtilsLang, D extends UtilsDescription,
									RC extends UtilsStatus<RC,L,D>,
									RV extends JeeslRevisionView<L,D,RVM>,
									RVM extends JeeslRevisionViewMapping<RV,RE,REM>,
									RS extends JeeslRevisionScope<L,D,RC,RA>,
									RST extends UtilsStatus<RST,L,D>,
									RE extends JeeslRevisionEntity<L,D,RC,REM,RA>,
									REM extends JeeslRevisionEntityMapping<RS,RST,RE>,
									RA extends JeeslRevisionAttribute<L,D,RE,RER,RAT>,
									RER extends UtilsStatus<RER,L,D>,
									RAT extends UtilsStatus<RAT,L,D>>
				extends AbstractFactoryBuilder<L,D>
{
	final static Logger logger = LoggerFactory.getLogger(IoRevisionFactoryBuilder.class);

	private final Class<RC> cCategory; public Class<RC> getClassCategory(){return cCategory;}
	private final Class<RV> cView; public Class<RV> getClassView(){return cView;}
	private final Class<RVM> cViewMapping; public Class<RVM> getClassViewMapping(){return cViewMapping;}
	private final Class<RS> cScope; public Class<RS> getClassScope(){return cScope;}
	private final Class<RST> cScopeType; public Class<RST> getClassScopeType(){return cScopeType;}
	private final Class<RE> cEntity; public Class<RE> getClassEntity(){return cEntity;}
	private final Class<REM> cMappingEntity; public Class<REM> getClassEntityMapping(){return cMappingEntity;}
	private final Class<RA> cAttribute; public Class<RA> getClassAttribute(){return cAttribute;}
	private final Class<RER> cRelation; public Class<RER> getClassRelation(){return cRelation;}
	private final Class<RAT> cRat; public Class<RAT> getClassAttributeType(){return cRat;}
    
	public IoRevisionFactoryBuilder(final Class<L> cL, final Class<D> cD, Class<RC> cCategory,Class<RV> cView,Class<RVM> cViewMapping, Class<RS> cScope, Class<RST> cScopeType, Class<RE> cEntity, Class<REM> cMappingEntity, Class<RA> cAttribute, final Class<RER> cRelation, Class<RAT> cRat)
	{
		super(cL,cD);
		this.cCategory=cCategory;
		this.cView=cView;
		this.cViewMapping=cViewMapping;
		this.cScope=cScope;
		this.cScopeType=cScopeType;
		this.cEntity=cEntity;
		this.cMappingEntity=cMappingEntity;
		this.cAttribute=cAttribute;
		this.cRelation=cRelation;
		this.cRat=cRat;
	}
	
	public EjbRevisionViewFactory<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RER,RAT> ejbView()
	{
		return EjbRevisionViewFactory.factory(cView);
	}
	
	public EjbRevisionMappingViewFactory<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RER,RAT> ejbMappingView()
	{
		return EjbRevisionMappingViewFactory.factory(cViewMapping);
	}
	
	public EjbRevisionScopeFactory<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RER,RAT> ejbScope()
	{
		return EjbRevisionScopeFactory.factory(cScope);
	}
	
	public EjbRevisionEntityFactory<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RER,RAT> ejbEntity()
	{
		return EjbRevisionEntityFactory.factory(cL,cD,cEntity);
	}
	
	public EjbRevisionMappingEntityFactory<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RER,RAT> ejbMappingEntity()
	{
		return EjbRevisionMappingEntityFactory.factory(cMappingEntity);
	}
	
	public EjbRevisionAttributeFactory<L,D,RC,RV,RVM,RS,RST,RE,REM,RA,RER,RAT> ejbAttribute()
	{
		return EjbRevisionAttributeFactory.factory(cAttribute);
	}
}