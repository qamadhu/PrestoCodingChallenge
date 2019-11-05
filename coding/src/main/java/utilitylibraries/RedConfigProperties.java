package utilitylibraries;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class RedConfigProperties {
	
Properties configProp;
	
	public RedConfigProperties (String configFilePath) {
	     
		configProp = new Properties();
	     
	    try {
	        FileInputStream fis = new FileInputStream(configFilePath);
	        configProp.load(fis);
	        fis.close();
	    }catch (IOException e) {
	        System.out.println(e.getMessage());
	        System.out.println("property file '" + configFilePath + "' not found in the classpath\n"+e.getMessage());
	    }
	  }
	public String getConfigProperty(String propertyName){
		 String propName = configProp.getProperty(propertyName);
		 return propName;
	}

}
