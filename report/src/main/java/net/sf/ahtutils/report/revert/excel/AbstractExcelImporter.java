package net.sf.ahtutils.report.revert.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import net.sf.ahtutils.interfaces.controller.report.UtilsXlsDefinitionResolver;

import net.sf.ahtutils.interfaces.facade.UtilsFacade;
import net.sf.ahtutils.model.interfaces.with.EjbWithId;
import net.sf.ahtutils.report.util.DataUtil;
import net.sf.ahtutils.util.reflection.ReflectionsUtil;
import net.sf.ahtutils.xml.report.DataAssociation;
import net.sf.ahtutils.xml.report.ImportStructure;
import net.sf.ahtutils.xml.report.ImportType;
import net.sf.ahtutils.xml.report.XlsSheet;
import net.sf.ahtutils.xml.xpath.ReportXpath;
import net.sf.exlp.exception.ExlpXpathNotFoundException;
import net.sf.exlp.exception.ExlpXpathNotUniqueException;
import org.apache.commons.lang.reflect.MethodUtils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractExcelImporter <C extends Serializable, I extends ImportStrategy> {
	
	final static Logger logger = LoggerFactory.getLogger(AbstractExcelImporter.class);
	
	protected File                       excelFile;
	protected XSSFWorkbook               workbook;
	protected Sheet                      activeSheet;
	protected String                     activeColumn;
	public  UtilsFacade                  facade;
	protected Map<String, String>        propertyRelations;
	protected Map<String, Class>         strategies;
	protected Map<String, Class>		 validators;
	protected Map<String, Class>		 targetClasses;
	protected Map<String, Boolean>		 isList;
	protected short                      primaryKey;
	protected Hashtable<String, C>       entities          = new Hashtable<String, C>();
	protected Hashtable<String, Object>  tempPropertyStore = new Hashtable<String, Object>();
	protected Boolean                    hasPrimaryKey     = false;
	protected XlsSheet definition;
	protected ImportStructure structure;
	
	public AbstractExcelImporter(UtilsXlsDefinitionResolver resolver, String reportCode, InputStream is) throws IOException, ClassNotFoundException, ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		// Read Excel workbook from given file(name)
		this.workbook       = new XSSFWorkbook(is);
		
		// Read information to import taken from Resolver
		definition = resolver.definition(reportCode).getXlsSheet().get(0);
		structure = ReportXpath.getImportStructure(definition.getContent());
		
		// Prepare the row import definitions
		// According to this post http://stackoverflow.com/questions/18231991/class-forname-caching
		// Caching is most probably not important for classes, but to minimize JXPath searches
		propertyRelations = new HashMap<String, String>();
		strategies		  = new HashMap<String, Class>();
		validators		  = new HashMap<String, Class>();
		targetClasses	  = new HashMap<String, Class>();
		isList			  = new HashMap<String, Boolean>();
		for (DataAssociation association : structure.getDataAssociations().getDataAssociation())
		{
			String column = association.getColumn();
			propertyRelations.put(column, association.getProperty());
			if (association.isSetHandledBy())	{strategies.put(column, Class.forName(association.getHandledBy()));
													if (association.isSetType())
													{
														if(association.getType().equals(ImportType.LIST))
														{
															isList.put(column, true);
														}
													}
												}
			if (association.isSetValidatedBy()) {validators.put(column, Class.forName(association.getValidatedBy()));}
			if (association.isSetTargetClass()) {targetClasses.put(column, Class.forName(association.getTargetClass()));}
		}
	}
	
	public AbstractExcelImporter(UtilsXlsDefinitionResolver resolver, String reportCode, String filename) throws IOException, ClassNotFoundException, ExlpXpathNotFoundException, ExlpXpathNotUniqueException
	{
		// Prepare file to be read
		this.excelFile      = new File(filename);
		FileInputStream fis = new FileInputStream(excelFile);
		
		// Read Excel workbook from given file(name)
		this.workbook       = new XSSFWorkbook(fis);
		
		// Read information to import taken from Resolver
		definition = resolver.definition(reportCode).getXlsSheet().get(0);
		structure = ReportXpath.getImportStructure(definition.getContent());
		
		// Prepare the row import definitions
		// According to this post http://stackoverflow.com/questions/18231991/class-forname-caching
		// Caching is most probably not important for classes, but to minimize JXPath searches
		propertyRelations = new HashMap<String, String>();
		strategies		  = new HashMap<String, Class>();
		validators		  = new HashMap<String, Class>();
		targetClasses	  = new HashMap<String, Class>();
		isList			  = new HashMap<String, Boolean>();
		for (DataAssociation association : structure.getDataAssociations().getDataAssociation())
		{
			String column = association.getColumn();
			propertyRelations.put(column, association.getProperty());
			if (association.isSetHandledBy())	{strategies.put(column, Class.forName(association.getHandledBy()));
													if (association.isSetType())
													{
														if(association.getType().equals(ImportType.LIST))
														{
															isList.put(column, true);
														}
													}
												}
			if (association.isSetValidatedBy()) {validators.put(column, Class.forName(association.getValidatedBy()));}
			if (association.isSetTargetClass()) {targetClasses.put(column, Class.forName(association.getTargetClass()));}
		}
	}
	
	public void setFacade(UtilsFacade facade){this.facade = facade;}
	public void setPrimaryKey(Integer columnNumber)
	{
		if(columnNumber == null) 
		{
			this.hasPrimaryKey = false;
		}
		else
		{
			this.primaryKey    = columnNumber.shortValue();
			this.hasPrimaryKey = true;
		}
	}
	
	// Select a sheet from the Excel workbook by name
	public void selectSheetByName(String name)	{activeSheet         = workbook.getSheet(name);}
	
	// Select a sheet from the Excel workbook by name
	public void selectFirstSheet()				{activeSheet         = workbook.getSheetAt(0);}
	
	// Debugging of sheet data
	public void debugHeader()       {DataUtil.debugRow(activeSheet, 0);}
	public void debugFirstRow()     {DataUtil.debugRow(activeSheet, 1);}
		
	public Map<C,ArrayList<String>> execute(Boolean skipTitle) throws Exception
	{
		// Create a list to hold the Entity classes to be created
		Hashtable<C,ArrayList<String>> importedEntities = new Hashtable<C,ArrayList<String>>();
                
        // Define the rows to begin with and to end with, whether with or without first row
		Integer end   = activeSheet.getLastRowNum();
		Integer start = activeSheet.getFirstRowNum();
		
		logger.info("Sheet goes from " +start +" to " +end);
		if (skipTitle) {start++;}
		
		// Iterate through all given rows
		for (int i = start; i < end+1; i++)
		{
			// Get the next row
			Row row = activeSheet.getRow(i);
			
			// Create a new Entity
			C entity = (C) Class.forName(structure.getTargetClass()).newInstance();
            if (entity instanceof EjbWithId)
			{
				Long currentId = new Long(1);
				if (tempPropertyStore.containsKey("currentId")) 
					{
						currentId = (Long) tempPropertyStore.get("currentId");
					}
				((EjbWithId) entity).setId(currentId + 1);
				tempPropertyStore.put("currentId", currentId +1);
			}
			
			// Create a list of properties that falied the validation
			// This can be used for staging purposes later on
			ArrayList<String> failedValidations = new ArrayList<String>();
                        
			if (hasPrimaryKey)
			{
				// See if there is already an instance created for this key, otherwise create a new one
				String entityKey = DataUtil.getStringValue(DataUtil.getCellValue(row.getCell(primaryKey)));
				if ( this.entities.containsKey(entityKey))
				{
					entity = this.entities.get(entityKey);
				}
			}
			
			// Iterate through the columns and assign data as given in the association table
			for (short j = row.getFirstCellNum() ; j < row.getLastCellNum() ; j++)
			{
				Cell cell = row.getCell(j);
				activeColumn = j +"";
				
				// Get the Cell Value as Object
				Object object = DataUtil.getCellValue(cell);
				
				// Read the name of the property that should be filled with the data from this column
				String propertyName = propertyRelations.get(j +"");
			
			    // Assign the data to the entity using the setter
				logger.trace("Cell " +row.getRowNum() +"," +j +" should store " +propertyName +", value as String is " +object.toString());
				if (propertyName!=null && !object.getClass().getCanonicalName().endsWith("java.lang.Object"))
				{
					String property = propertyName;
					if(logger.isTraceEnabled()){logger.trace("Setting " +property + " to " +object.toString() +" type: " +object.getClass().getCanonicalName() +")");}
					tempPropertyStore.put(property, object.toString());
					Class handler = strategies.get(activeColumn);
					Boolean validated = invokeSetter(property,
														new Object[] { object },
														entity.getClass(),
														entity,
														handler);
					if (!validated)
					{
						failedValidations.add(property);
					}
				}
			}
                        
			//facade.save(entity);
			importedEntities.put(entity, failedValidations);
			if (hasPrimaryKey)
			{
				String entityKey = DataUtil.getStringValue(DataUtil.getCellValue(row.getCell(primaryKey)));
				entities.put(entityKey, entity);
			}
		}
		return importedEntities;
		
	}
	
	
		
	 protected Boolean invokeSetter(String   property, 
			 							Object[] parameters,
			 							Class    targetClass,
			 							Object   target,
										Class    handler)        throws Exception
	 {
		if (isList.containsKey(activeColumn))
		{
			List list = (List) MethodUtils.invokeMethod(target, "get" +property, null);
			
			// Instantiate new strategy to handle import
			ImportStrategy strategy = (ImportStrategy) handler.newInstance();

			// Pass database connection and current set of temporary properties
			strategy.setFacade(facade);
			strategy.setTempPropertyStore(tempPropertyStore);

			// Process import step - Parameterclass is not requrired here
			Object value  = strategy.handleObject(parameters[0], "", property);
			parameters[0] =  value;
			
			list.add(value);
			return true;
		}
		else
		{
		
			Boolean validated		= false;
			String methodName		= "set" +property;
			String valueFromCell	= parameters[0].toString();
			logger.trace("Invoking " +methodName);

			// Now find the correct method
			Method[] methods = targetClass.getMethods();
			Class parameter  = null;
			Method m         = null;
			for (Method method : methods)
			{
				if (method.getName().equals(methodName))
				{
					parameter = method.getParameterTypes()[0];
					m = method;
				}
			}
			
			if (Modifier.isPrivate(m.getModifiers()))
			{
				m.setAccessible(true);
			}

			// Determine parameter type of setter
			// Type t = m.getGenericParameterTypes()[0];
			// String parameterClass = t.getTypeName();

			String parameterClass = parameter.getCanonicalName();
		 //   logger.info(parameterClass);
		 //   logger.info(parameters[0].getClass().getCanonicalName());

			// Lets see if the setter is accepting a data type that is available in Excel (String, Double, Date)
			// Otherwise assume that it is used with a lookup table

			Boolean isHandled = (handler != null);
			if (ReflectionsUtil.hasMethod(target, methodName))
			{
				if (isHandled)
				{
					logger.trace("Loading import strategy for " +parameterClass +": " +handler.getCanonicalName() +".");

					// Instantiate new strategy to handle import
					ImportStrategy strategy = (ImportStrategy) handler.newInstance();

					// Pass database connection and current set of temporary properties

					strategy.setFacade(facade);
					strategy.setTempPropertyStore(tempPropertyStore);

					// Process import step
					Object value  = strategy.handleObject(parameters[0], parameterClass, property);
					parameters[0] =  value;






					// Sync new temporary properties if any added
					tempPropertyStore = strategy.getTempPropertyStore();

					// Add the current property/value pair, can be useful when inspecting IDs (overwritten for new lines for examples)
					if(logger.isTraceEnabled()){logger.trace("Set " +property + " to " + value.toString());}
					tempPropertyStore.put(property, value);
				} 

				// Needed to correct the Class of the general number
				else if (parameterClass.equals("long"))
				{
					Number number = (Number) parameters[0];
					parameters[0] = number.longValue();
				}
				
				// Needed to correct the Class of the general number
				else if (parameterClass.equals("int"))
				{
					Number number = (Number) parameters[0];
					parameters[0] = number.intValue();
				}
				
				// Needed to correct the Class of the general number
				else if (parameterClass.equals("java.lang.Integer"))
				{
					Number number = (Number) parameters[0];
					parameters[0] = new Integer(number.intValue());
				}
                                
                                // Needed to correct the Class of the general number
				else if (parameterClass.equals("java.lang.Double"))
				{
					Number number = (Number) parameters[0];
					parameters[0] = new Double(number.doubleValue());
				}

				// This is important if the String is a Number, Excel will format the cell to be a "general number"
				else if (parameterClass.equals("java.lang.String"))
				{
					if (parameters[0].getClass().getName().equals("java.lang.Double"))
					{
						Double n			= (Double) parameters[0];
						if (n % 1 == 0)
						{
							parameters[0]	= "" +n.intValue();
						}
						else
						{
							parameters[0]	= "" +n;
						}
					}
					else
					{
						parameters[0] = parameters[0] +"";
					}
				}
				
				// This is important if the String is a Number, Excel will format the cell to be a "general number"
				else if (parameterClass.equals("java.lang.Boolean") || parameterClass.equals("boolean"))
				{
					Number number = (Number) parameters[0];
					Boolean b     = true;
					if (number.intValue() == 0)
					{
						b = false;
					}
					parameters[0] = b;
				}

				// Now invoke the method with the parameter from the Excel sheet
                                if (logger.isTraceEnabled())
                                {
                                    logger.trace("Parameter Class is " +parameters[0].getClass().getSimpleName() + " and original entity property is " +parameterClass);
                                }
				m.invoke(target, parameters);

			}
			else
			{
				logger.trace("Entity does not have the method " +methodName +". Initiating special treatment.");

			}
			if (validators.containsKey(activeColumn))
					{
						logger.info("Found " +property +" validator");
						// Instantiate new strategy to handle import
						ValidationStrategy validator = (ValidationStrategy) validators.get(activeColumn).newInstance();
						validator.setFacade(facade);
						logger.info("Using " +validator.getClass().getCanonicalName());
						// Validate the loaded value
						if (targetClasses.containsKey(activeColumn)) 
						{
							validated = validator.validate(valueFromCell, targetClasses.get(activeColumn).getCanonicalName(), property);
						}
						else
						{
							validated = validator.validate(valueFromCell, "", property);
						}

						logger.info("Validation result: " +validated);
					}
					else
					{
						validated = true;
					}
				return validated;
			}
			
		}
	 
	 
	public Hashtable<String, Object> getTempPropertyStore() {return tempPropertyStore;}
	public void setTempPropertyStore(Hashtable<String, Object> tempPropertyStore) {this.tempPropertyStore = tempPropertyStore;}
}
