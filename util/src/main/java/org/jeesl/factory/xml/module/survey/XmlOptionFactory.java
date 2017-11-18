package org.jeesl.factory.xml.module.survey;

import org.jeesl.interfaces.model.module.survey.analysis.JeeslSurveyAnalysis;
import org.jeesl.interfaces.model.module.survey.analysis.JeeslSurveyAnalysisQuestion;
import org.jeesl.interfaces.model.module.survey.analysis.JeeslSurveyAnalysisTool;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurvey;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyScheme;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyScore;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyTemplate;
import org.jeesl.interfaces.model.module.survey.core.JeeslSurveyTemplateVersion;
import org.jeesl.interfaces.model.module.survey.correlation.JeeslSurveyCorrelation;
import org.jeesl.interfaces.model.module.survey.correlation.JeeslSurveyDomain;
import org.jeesl.interfaces.model.module.survey.correlation.JeeslSurveyDomainPath;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyAnswer;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyData;
import org.jeesl.interfaces.model.module.survey.data.JeeslSurveyMatrix;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOption;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyOptionSet;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveyQuestion;
import org.jeesl.interfaces.model.module.survey.question.JeeslSurveySection;
import org.jeesl.interfaces.model.system.io.revision.JeeslRevisionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.xml.survey.Option;

public class XmlOptionFactory<L extends UtilsLang,D extends UtilsDescription,
								SURVEY extends JeeslSurvey<L,D,SS,TEMPLATE,DATA>,
								SS extends UtilsStatus<SS,L,D>,
								SCHEME extends JeeslSurveyScheme<L,D,TEMPLATE,SCORE>,
								TEMPLATE extends JeeslSurveyTemplate<L,D,SCHEME,TEMPLATE,VERSION,TS,TC,SECTION,OPTIONS,ANALYSIS>,VERSION extends JeeslSurveyTemplateVersion<L,D,TEMPLATE>,
								TS extends UtilsStatus<TS,L,D>,
								TC extends UtilsStatus<TC,L,D>,SECTION extends JeeslSurveySection<L,D,TEMPLATE,SECTION,QUESTION>,QUESTION extends JeeslSurveyQuestion<L,D,SECTION,QE,SCORE,UNIT,OPTIONS,OPTION,AQ>, QE extends UtilsStatus<QE,L,D>, SCORE extends JeeslSurveyScore<L,D,SCHEME,QUESTION>,UNIT extends UtilsStatus<UNIT,L,D>,ANSWER extends JeeslSurveyAnswer<L,D,QUESTION,MATRIX,DATA,OPTION>, MATRIX extends JeeslSurveyMatrix<L,D,ANSWER,OPTION>,DATA extends JeeslSurveyData<L,D,SURVEY,ANSWER,CORRELATION>, OPTIONS extends JeeslSurveyOptionSet<L,D,TEMPLATE,OPTION>,
								OPTION extends JeeslSurveyOption<L,D>,CORRELATION extends JeeslSurveyCorrelation<L,D,DATA>, DOMAIN extends JeeslSurveyDomain<L,D,DENTITY>, PATH extends JeeslSurveyDomainPath<L,D,DOMAIN,PATH,DENTITY>, DENTITY extends JeeslRevisionEntity<L,D,?,?,?>, ANALYSIS extends JeeslSurveyAnalysis<L,D,TEMPLATE>, AQ extends JeeslSurveyAnalysisQuestion<L,D,QUESTION,ANALYSIS>, AT extends JeeslSurveyAnalysisTool<L,D,QE,AQ,ATT>, ATT extends UtilsStatus<ATT,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(XmlOptionFactory.class);
		
	private final String localeCode;
	private final Option q;
	
	public XmlOptionFactory(Option q){this(null,q);}
	
	public XmlOptionFactory(String localeCode, Option q)
	{
		this.localeCode=localeCode;
		this.q=q;
	}
	
	public Option build(OPTION ejb)
	{
		Option xml = new Option();
		if(q.isSetId()){xml.setId(ejb.getId());}
		if(q.isSetCode()) {xml.setCode(ejb.getCode());}
		if(q.isSetLabel() && localeCode!=null && ejb.getName().containsKey(localeCode)) {xml.setLabel(ejb.getName().get(localeCode).getLang());}
		
		return xml;
	}
}