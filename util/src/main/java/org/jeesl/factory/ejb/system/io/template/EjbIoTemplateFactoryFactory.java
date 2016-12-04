package org.jeesl.factory.ejb.system.io.template;

import org.jeesl.interfaces.model.system.io.templates.JeeslIoTemplate;
import org.jeesl.interfaces.model.system.io.templates.JeeslIoTemplateDefinition;
import org.jeesl.interfaces.model.system.io.templates.JeeslIoTemplateToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class EjbIoTemplateFactoryFactory<L extends UtilsLang,D extends UtilsDescription,
								CATEGORY extends UtilsStatus<CATEGORY,L,D>,
								TYPE extends UtilsStatus<TYPE,L,D>,
								TEMPLATE extends JeeslIoTemplate<L,D,CATEGORY,TYPE,TEMPLATE,DEFINITION,TOKEN>,
								DEFINITION extends JeeslIoTemplateDefinition<L,D,CATEGORY,TYPE,TEMPLATE,DEFINITION,TOKEN>,
								TOKEN extends JeeslIoTemplateToken<L,D,CATEGORY,TYPE,TEMPLATE,DEFINITION,TOKEN>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbIoTemplateFactoryFactory.class);
	
	final Class<L> cL;
	final Class<D> cD;
	final Class<TEMPLATE> cTemplate;
	final Class<DEFINITION> cDefinition;
	final Class<TOKEN> cToken;
    
	private EjbIoTemplateFactoryFactory(final Class<L> cL,final Class<D> cD,final Class<TEMPLATE> cTemplate, final Class<DEFINITION> cDefinition, final Class<TOKEN> cToken)
	{       
		this.cL = cL;
        this.cD = cD;
        this.cTemplate = cTemplate;
        this.cDefinition = cDefinition;
        this.cToken = cToken;
	}
	
	public static <L extends UtilsLang,D extends UtilsDescription,
					CATEGORY extends UtilsStatus<CATEGORY,L,D>,
					TYPE extends UtilsStatus<TYPE,L,D>,
					TEMPLATE extends JeeslIoTemplate<L,D,CATEGORY,TYPE,TEMPLATE,DEFINITION,TOKEN>,
					DEFINITION extends JeeslIoTemplateDefinition<L,D,CATEGORY,TYPE,TEMPLATE,DEFINITION,TOKEN>,
					TOKEN extends JeeslIoTemplateToken<L,D,CATEGORY,TYPE,TEMPLATE,DEFINITION,TOKEN>>
	EjbIoTemplateFactoryFactory<L,D,CATEGORY,TYPE,TEMPLATE,DEFINITION,TOKEN> factory(final Class<L> cL,final Class<D> cD,final Class<TEMPLATE> cTemplate, final Class<DEFINITION> cDefinition, final Class<TOKEN> cToken)
	{
		return new EjbIoTemplateFactoryFactory<L,D,CATEGORY,TYPE,TEMPLATE,DEFINITION,TOKEN>(cL,cD,cTemplate,cDefinition,cToken);
	}
	
	public EjbIoTemplateFactory<L,D,CATEGORY,TYPE,TEMPLATE,DEFINITION,TOKEN> template()
	{
		return new EjbIoTemplateFactory<L,D,CATEGORY,TYPE,TEMPLATE,DEFINITION,TOKEN>(cL,cD,cTemplate);
	}
	
	public EjbIoTemplateDefinitionFactory<L,D,CATEGORY,TYPE,TEMPLATE,DEFINITION,TOKEN> definition()
	{
		return new EjbIoTemplateDefinitionFactory<L,D,CATEGORY,TYPE,TEMPLATE,DEFINITION,TOKEN>(cD,cDefinition);
	}
	
	public EjbIoTemplateTokenFactory<L,D,CATEGORY,TYPE,TEMPLATE,DEFINITION,TOKEN> token()
	{
		return new EjbIoTemplateTokenFactory<L,D,CATEGORY,TYPE,TEMPLATE,DEFINITION,TOKEN>(cL,cD,cToken);
	}
}