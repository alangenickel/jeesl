package net.sf.ahtutils.report.revert.excel.validators;

import java.util.Hashtable;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;

import org.jeesl.api.controller.ValidationStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidateNonExistenceByCode implements ValidationStrategy {
	
	final static Logger logger = LoggerFactory.getLogger(ValidateNonExistenceByCode.class);
	
	private UtilsFacade facade;
	
	private Hashtable<String, Object> tempPropertyStore;
	public  Hashtable<String, Object> getTempPropertyStore() {return tempPropertyStore;}
	public void setTempPropertyStore(Hashtable<String, Object> tempPropertyStore) {this.tempPropertyStore = tempPropertyStore;}

	@Override
	public Boolean validate(Object object, String parameterClass, String property) {
		
		String code			 = (String) object;
		Boolean validated    = true;
                
		if(logger.isTraceEnabled()){logger.trace("Searching for Entity with Code " +code);}
		
		// Try to find the entity with given code in database
		if (facade != null)
		{
			try {
				Class lutClass = (Class) Class.forName(parameterClass);
				logger.info("lutClass " +lutClass.getName());
				facade.fByCode(lutClass, code);
                validated = false;
			} catch (Exception e) {
				if(logger.isTraceEnabled())
				{
					logger.trace("An error occured while trying to load entity with code " +code +" from database! " +e.getMessage());
				}

				// If the entity is not found or some other error occurs, mark it as validated
				validated = true;
			}
		}
		
    	// Return the result
    	return validated;
	}

	@Override
	public void setFacade(UtilsFacade facade) {
		this.facade = facade;
	}

}
