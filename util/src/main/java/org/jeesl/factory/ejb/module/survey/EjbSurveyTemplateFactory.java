package org.jeesl.factory.ejb.module.survey;

import java.util.ArrayList;
import java.util.List;

import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyTemplate;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyQuestion;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveySection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.xml.survey.Template;

public class EjbSurveyTemplateFactory<L extends UtilsLang, D extends UtilsDescription,
				TEMPLATE extends JeeslSurveyTemplate<L,D,?,TEMPLATE,?,TS,TC,SECTION,?,?>,
				TS extends UtilsStatus<TS,L,D>,
				TC extends UtilsStatus<TC,L,D>,
				SECTION extends JeeslSurveySection<L,D,TEMPLATE,SECTION,QUESTION>,
				QUESTION extends JeeslSurveyQuestion<L,D,SECTION,?,?,?,?,?,?,?,?>
				>
{
	final static Logger logger = LoggerFactory.getLogger(EjbSurveyTemplateFactory.class);
	
	final Class<TEMPLATE> cTemplate; public Class<TEMPLATE> getClassTemplate() {return cTemplate;}
    
	public EjbSurveyTemplateFactory(final Class<TEMPLATE> cTemplate)
	{       
        this.cTemplate = cTemplate;
	}
    
	public TEMPLATE build(TC category,TS status, Template xTemplate)
	{
		return build(category,status,xTemplate.getDescription().getValue());
	}
	
	public TEMPLATE build(TC category,TS status, String name)
	{
		TEMPLATE ejb = null;
		try
		{
			ejb = cTemplate.newInstance();
			ejb.setName(name);
			ejb.setCategory(category);
			ejb.setStatus(status);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
	
	public TEMPLATE toVisible(TEMPLATE template, boolean withQuestions)
	{
		List<SECTION> sections = new ArrayList<SECTION>();
		for(SECTION section : template.getSections())
		{
			if(section.isVisible())
			{
				if(withQuestions)
				{
					List<QUESTION> questions = new ArrayList<QUESTION>();
					for(QUESTION question : section.getQuestions())
					{
						if(question.isVisible())
						{
							questions.add(question);
						}
					}
					section.getQuestions().clear();
					section.setQuestions(questions);
					
				}
				
				sections.add(section);
			}
		}
		
		template.getSections().clear();
		template.setSections(sections);
		return template;
	}
}