package org.jeesl.factory.ejb.system;

import org.jeesl.interfaces.model.system.JeeslFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EjbSystemFeatureFactory<F extends JeeslFeature>
{
	final static Logger logger = LoggerFactory.getLogger(EjbSystemFeatureFactory.class);
	
	private final Class<F> cFeature;
    
	public EjbSystemFeatureFactory(final Class<F> cFeature)
	{  
        this.cFeature = cFeature;
	}
	
	public static <F extends JeeslFeature>
		EjbSystemFeatureFactory<F> factory(final Class<F> cFeature)
	{
		return new EjbSystemFeatureFactory<F>(cFeature);
	}
    
	public F build()
	{
		F ejb = null;
		try
		{
			ejb = cFeature.newInstance();
		}
		catch (InstantiationException e) {e.printStackTrace();}
		catch (IllegalAccessException e) {e.printStackTrace();}
		
		return ejb;
	}
}