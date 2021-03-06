package org.jeesl.factory.txt.system.io.report;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;

public class TxtLabelsFactory
{
	final static Logger logger = LoggerFactory.getLogger(TxtLabelsFactory.class);
		
	public static <S extends UtilsStatus<S,L,D>, L extends UtilsLang, D extends UtilsDescription>
		String aggregationGroups(String localeCode, List<S> aggregations)
	{
		List<String> labels = new ArrayList<String>();
		for(S aggregation : aggregations)
		{
			labels.add(aggregation.getName().get(localeCode).getLang());
		}
		return StringUtils.join(labels, ", ");
	}
	
	public static List<String> toList(Map<Long,String> mapAggregationLabels)
	{
		List<String> labels = new ArrayList<String>();
		List<Long> keys = new ArrayList<Long>(mapAggregationLabels.keySet());
		Collections.sort(keys);
		for(Long key : keys)
		{
			labels.add(mapAggregationLabels.get(key));
		}
		return labels;
	}
}