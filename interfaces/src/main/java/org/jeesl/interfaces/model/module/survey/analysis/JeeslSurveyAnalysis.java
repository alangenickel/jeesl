package org.jeesl.interfaces.model.module.survey.analysis;

import java.io.Serializable;

import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyTemplate;
import org.jeesl.interfaces.model.system.io.domain.JeeslDomain;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionAttribute;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.with.parent.EjbWithParentAttributeResolver;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPositionVisible;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;

public interface JeeslSurveyAnalysis<L extends UtilsLang, D extends UtilsDescription,
										TEMPLATE extends JeeslSurveyTemplate<L,D,?,TEMPLATE,?,?,?,?,?,?>,
										DOMAIN extends JeeslDomain<L,?>,
										DENTITY extends JeeslRevisionEntity<L,D,?,?,DATTRIBUTE>,
										DATTRIBUTE extends JeeslRevisionAttribute<L,D,?,?,?>>
			extends Serializable,EjbWithId,
						EjbSaveable,EjbRemoveable,
						EjbWithParentAttributeResolver,EjbWithPositionVisible,
						EjbWithLang<L>//,,EjbWithDescription<D>
{
	public enum Attributes{template}
	
	TEMPLATE getTemplate();
	void setTemplate(TEMPLATE template);
	
	DOMAIN getDomain();
	void setDomain(DOMAIN domain);
	
	DENTITY getEntity();
	void setEntity(DENTITY entity);
}