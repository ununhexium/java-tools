
package net.lab0.tools.cfg;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;


/**
 * 
 * Parameters with a few helper methods
 * 
 * @author 116@lab0.net
 * 
 */
@SuppressWarnings("serial")
public class Configuration
extends Properties
{
    /**
     * The file to use to load and save the configuration parameters
     */
    private File configurationFile;
    
    /**
     * 
     * @param confFile
     *            The file to use to load and save the configuration parameters
     * @param load
     *            If <code>true</code>, tries to load the configuration from the given file, else only uses it to save
     * @throws InvalidPropertiesFormatException
     * @throws FileNotFoundException
     * @throws IOException
     */
    public Configuration(File confFile, boolean load)
    throws InvalidPropertiesFormatException, FileNotFoundException, IOException
    {
        this.configurationFile = confFile;
        if (load)
        {
            loadFromXML(new FileInputStream(confFile));
        }
    }
    
    /**
     * Builds a new configuration, copying the parameters from <code>configuration</code>
     * 
     * @param configuration
     *            The configuration to copy
     */
    public Configuration(Configuration configuration)
    {
        super(configuration);
        this.configurationFile = configuration.getConfigurationFile();
    }
    
    /**
     * Saves the comfiguration to the file designated by <code>confiurationFile</code>
     * 
     * @throws FileNotFoundException
     * @throws IOException
     */
    public synchronized void save()
    throws FileNotFoundException, IOException
    {
        if (!configurationFile.getParentFile().exists())
        {
            configurationFile.getParentFile().mkdirs();
        }
        storeToXML(new FileOutputStream(configurationFile), "");
    }
    
    /**
     * resets all the parameters values to their original one
     */
    public synchronized void reset()
    {
        for (Method m : getClass().getDeclaredMethods())
        {
            if (m.getName().startsWith("reset"))
            {
                try
                {
                    m.invoke(this);
                }
                catch (IllegalArgumentException e)
                {
                    e.printStackTrace();
                }
                catch (IllegalAccessException e)
                {
                    e.printStackTrace();
                }
                catch (InvocationTargetException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
    
    /**
     * 
     * @return the file used to load and save the configuration
     */
    public File getConfigurationFile()
    {
        return configurationFile;
    }
    
    protected String getAsString(String key)
    {
        return getProperty(key);
    }
    
    protected void setAsString(String key, String value)
    {
        setProperty(key, value);
    }
    
    protected Long getAsLong(String key)
    {
        return Long.parseLong(getProperty(key));
    }
    
    protected Integer getAsInteger(String key)
    {
        return Integer.parseInt(getProperty(key));
    }
    
    protected Short getAsShort(String key)
    {
        return Short.parseShort(getProperty(key));
    }
    
    protected File getAsFile(String key)
    {
        return new File(getProperty(key));
    }
    
    protected void setAsFile(String key, File file)
    {
        setProperty(key, file.getPath());
    }
}
