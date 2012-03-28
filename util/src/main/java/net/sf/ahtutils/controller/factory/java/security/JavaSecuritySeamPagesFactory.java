package net.sf.ahtutils.controller.factory.java.security;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import net.sf.ahtutils.exception.processing.UtilsConfigurationException;
import net.sf.ahtutils.xml.access.Category;
import net.sf.ahtutils.xml.access.View;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.TemplateException;

@SuppressWarnings({"rawtypes","unchecked"})
public class JavaSecuritySeamPagesFactory extends AbstractJavaSecurityFileFactory
{
	final static Logger logger = LoggerFactory.getLogger(JavaSecuritySeamPagesFactory.class);
		
	private Map<String,List<View>> mViews;
	private Map<String,String> mCategoryPackage;
	private File srcDir;
	private String sLoginView,sAccessDeniedView;
	private String viewQualifierBasePackage;

	public JavaSecuritySeamPagesFactory(File fTmpDir, String classPrefix, File srcDir, String sLoginView, String sAccessDeniedView, String viewQualifierBasePackage)
	{
		super(fTmpDir,classPrefix);
		this.srcDir=srcDir;
		this.sLoginView=sLoginView;
		this.sAccessDeniedView=sAccessDeniedView;
		this.viewQualifierBasePackage=viewQualifierBasePackage;
		mViews = new Hashtable<String,List<View>>();
		mCategoryPackage = new Hashtable<String,String>();
	}

	@Override
	protected void processCategories(List<Category> lCategory) throws UtilsConfigurationException
	{
		for(Category category : lCategory)
		{
			if(category.isSetViews())
			{
				for(View view : category.getViews().getView())
				{
					if(!view.isSetNavigation()){throw new UtilsConfigurationException("No <navigation> defined for view@code="+view.getCode());}
					if(!view.getNavigation().isSetPackage()){throw new UtilsConfigurationException("No <navigation@package> defined for view@code="+view.getCode());}
					if(!view.getNavigation().isSetViewPattern()){throw new UtilsConfigurationException("No <navigation.viewPattern> defined for view@code="+view.getCode());}
					if(!view.getNavigation().isSetUrlMapping()){throw new UtilsConfigurationException("No <navigation.urlMapping> defined for view@code="+view.getCode());}
					
					mCategoryPackage.put(view.getCode(), category.getCode());
					getViewForPackage(view.getNavigation().getPackage()).add(view);
				}
			}
		}
		processPackage();
/*		try
		{
			this.createFile(fJavaRestrictor, "security.ahtutils-util/viewRestrictor.ftl");
		}
		catch (IOException e) {e.printStackTrace();}
		catch (TemplateException e) {e.printStackTrace();}
*/	}
	
	private void processPackage() throws UtilsConfigurationException
	{
		for(String key : mViews.keySet())
		{
			File fPackage = new File(srcDir,mViews.get(key).get(0).getNavigation().getPackage().replaceAll("\\.", "/"));
			if(!fPackage.exists() || !fPackage.isDirectory()){{throw new UtilsConfigurationException("Package directory "+fPackage.getAbsolutePath()+" does not exist");}}
			try {createPages(fPackage,mViews.get(key));}
			catch (IOException e) {throw new UtilsConfigurationException(e.getMessage());}
			catch (TemplateException e) {throw new UtilsConfigurationException(e.getMessage());}
		}
	}
	
	private void createPages(File fPackage, List<View> lViews) throws IOException, TemplateException
	{
		logger.debug("Process ... "+lViews.size()+" "+fPackage.getAbsolutePath());
		freemarkerNodeModel.clear();
		freemarkerNodeModel.put("packageName", lViews.get(0).getNavigation().getPackage());
		freemarkerNodeModel.put("loginView", sLoginView);
		freemarkerNodeModel.put("accessDeniedView", sAccessDeniedView);
		
		List<Map> mViews = new ArrayList<Map>();
		for(View v : lViews)
		{
			StringBuffer sbImport = new StringBuffer();
			sbImport.append(viewQualifierBasePackage).append(".");
			sbImport.append(mCategoryPackage.get(v.getCode())).append(".");
			sbImport.append(createClassName(v.getCode()));
			
			Map m = new HashMap();
			m.put("import", sbImport.toString());
			m.put("viewPattern", v.getNavigation().getViewPattern().getValue());
			m.put("urlMapping", v.getNavigation().getUrlMapping().getValue());
			m.put("identifier", createClassName(v.getCode()));
			m.put("enum", "ENUM"+v.getCode().toUpperCase());
			mViews.add(m);
		}
		
		freemarkerNodeModel.put("views", mViews);
		
		File fJava = new File(fPackage,"SeamPages.java");
		this.createFile(fJava, "security.ahtutils-util/seamPages.ftl");
	}
	
	private List<View> getViewForPackage(String packageName)
	{
		if(!mViews.containsKey(packageName)){mViews.put(packageName, new ArrayList<View>());}
		return mViews.get(packageName);
	}
	
	protected Map<String, List<View>> getmViews() {return mViews;}
}