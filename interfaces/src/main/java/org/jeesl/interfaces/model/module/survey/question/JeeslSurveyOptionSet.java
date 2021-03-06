package org.jeesl.interfaces.model.module.survey.question;

import java.io.Serializable;
import java.util.List;

import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyTemplate;
import org.jeesl.interfaces.model.system.with.code.EjbWithCode;

import net.sf.ahtutils.interfaces.model.behaviour.EjbSaveable;
import net.sf.ahtutils.interfaces.model.crud.EjbRemoveable;
import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.with.position.EjbWithPosition;
import net.sf.ahtutils.model.interfaces.with.EjbWithLang;
import net.sf.ahtutils.model.interfaces.with.EjbWithVisible;

public interface JeeslSurveyOptionSet<L extends UtilsLang, D extends UtilsDescription,
										TEMPLATE extends JeeslSurveyTemplate<L,D,?,TEMPLATE,?,?,?,?,?,?>,
										OPTION extends JeeslSurveyOption<L,D>>
			extends Serializable,EjbSaveable,EjbRemoveable,EjbWithCode,EjbWithPosition,EjbWithVisible,
					EjbWithLang<L>
{
	public enum Attributes{template}
	
	TEMPLATE getTemplate();
	void setTemplate(TEMPLATE template);
	
	List<OPTION> getOptions();
	void setOptions(List<OPTION> options);
}