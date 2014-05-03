
package net.lab0.tools.cfg;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.InvalidPropertiesFormatException;
import java.util.Map;
import java.util.Properties;


/**
 * 
 * The class used to group multiple configuration classes
 * 
 * @author 116@lab0.net
 * 
 */
public class ConfigurationRoot
{
    /**
     * The file useed to load the various configuration files
     */
    private File                       confRootFile;
    
    private Properties                 propertiesList;
    
    /**
     * binding : parameters name -> parameters class
     */
    private Map<String, Configuration> properties;
    
    public ConfigurationRoot(File confRootFile, boolean load)
    throws InvalidPropertiesFormatException, FileNotFoundException, IOException, URISyntaxException
    {
        propertiesList = new Properties();
        properties = new HashMap<String, Configuration>();
        this.confRootFile = confRootFile;
        if (load)
        {
            propertiesList.loadFromXML(new FileInputStream(confRootFile));
        }
        
        for (String key : propertiesList.stringPropertyNames())
        {
            try
            {
                Configuration conf = new Configuration(new File(propertiesList.getProperty(key)), true);
                properties.put(key, conf);
            }
            catch (FileNotFoundException e)
            {
                System.err.println("File not found : " + propertiesList.getProperty(key));
            }
        }
        
        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    save();
                }
                catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }
    
    public Configuration getConfiguration(String name)
    {
        return properties.get(name);
    }
    
    /**
     * 
     * @param name
     *            the name of the configuration file to add
     * @param configuration
     *            the configuration file to add
     * @throws IllegalArgumentException
     *             if the configuration file name is already in use
     * @throws IOException
     * @throws FileNotFoundException
     */
    public void addConfiguration(String name, Configuration configuration)
    throws IllegalArgumentException, FileNotFoundException, IOException
    {
        if (properties.containsKey(name))
        {
            throw new IllegalArgumentException("Can't add a configuration file with a name already in use. Try addOrReplaceConfiguration() or removeConfiguration()");
        }
        properties.put(name, configuration);
        propertiesList.setProperty(name, configuration.getConfigurationFile().toString());
        propertiesList.storeToXML(new FileOutputStream(configuration.getConfigurationFile()), "", "UTF-8");
    }
    
    /**
     * saves the configuration objects to their respective save files.
     * 
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void save()
    throws FileNotFoundException, IOException
    {
        for (Configuration c : properties.values())
        {
            c.save();
        }
        propertiesList.storeToXML(new FileOutputStream(confRootFile), "", "UTF-8");
    }
    
    /**
     * saves the configuration objects to their respective save files.
     * 
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void reset()
    throws FileNotFoundException, IOException
    {
        for (Configuration c : properties.values())
        {
            c.reset();
        }
    }
    
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        for (String k : properties.keySet())
        {
            Configuration c = properties.get(k);
            sb.append("Configuration name : " + k).append("\n");
            sb.append("Configuration file : " + c.getConfigurationFile().getPath()).append("\n");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(baos);
            c.list(ps);
            sb.append(baos.toString()).append("\n");
        }
        return sb.toString();
    }
}
