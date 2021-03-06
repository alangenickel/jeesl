package org.jeesl.factory.ejb.module.ts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jeesl.interfaces.model.module.ts.JeeslTimeSeries;
import org.jeesl.interfaces.model.module.ts.JeeslTsBridge;
import org.jeesl.interfaces.model.module.ts.JeeslTsData;
import org.jeesl.interfaces.model.module.ts.JeeslTsEntityClass;
import org.jeesl.interfaces.model.module.ts.JeeslTsSample;
import org.jeesl.interfaces.model.module.ts.JeeslTsScope;
import org.jeesl.interfaces.model.module.ts.JeeslTsTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.ahtutils.interfaces.model.status.UtilsDescription;
import net.sf.ahtutils.interfaces.model.status.UtilsLang;
import net.sf.ahtutils.interfaces.model.status.UtilsStatus;
import net.sf.ahtutils.interfaces.model.with.EjbWithLangDescription;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;

public class EjbTsFactory<L extends UtilsLang,D extends UtilsDescription,
							CAT extends UtilsStatus<CAT,L,D>,
							SCOPE extends JeeslTsScope<L,D,CAT,?,UNIT,EC,INT>,
							UNIT extends UtilsStatus<UNIT,L,D>,
							TS extends JeeslTimeSeries<SCOPE,BRIDGE,INT>,
							TRANSACTION extends JeeslTsTransaction<SOURCE,DATA,USER>,
							SOURCE extends EjbWithLangDescription<L,D>, 
							BRIDGE extends JeeslTsBridge<EC>,
							EC extends JeeslTsEntityClass<L,D,CAT>,
							INT extends UtilsStatus<INT,L,D>,
							DATA extends JeeslTsData<TS,TRANSACTION,SAMPLE,WS>,
							SAMPLE extends JeeslTsSample, 
							USER extends EjbWithId, 
							WS extends UtilsStatus<WS,L,D>,
							QAF extends UtilsStatus<QAF,L,D>>
{
	final static Logger logger = LoggerFactory.getLogger(EjbTsFactory.class);
	
	final Class<TS> cTs;
    
	public EjbTsFactory(final Class<TS> cTs)
	{       
        this.cTs=cTs;
	}
	
	public TS build(SCOPE scope, INT interval, BRIDGE bridge)
	{
		TS ejb = null;
		try
		{
			ejb = cTs.newInstance();
			ejb.setScope(scope);
			ejb.setInterval(interval);
			ejb.setBridge(bridge);
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		return ejb;
	}
	
	public List<Long> toBridgeIds(List<TS> list)
	{
		List<Long> result = new ArrayList<Long>();
		for(TS ts : list) {result.add(ts.getBridge().getRefId());}
		return result;
	}
	
	public Map<Long,TS> toMapBridgeTs(List<TS> list)
	{
		Map<Long,TS> map = new HashMap<Long,TS>();
		for(TS ts : list) {map.put(ts.getBridge().getRefId(),ts);}
		return map;
	}
}