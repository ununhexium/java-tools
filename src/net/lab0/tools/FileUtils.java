package net.lab0.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class FileUtils
{
    /**
     * Recursively deleted a folder.
     * 
     * @param path
     *            The path to the folder that has to be deleted.
     * @throws IOException
     */
    public static void removeRecursive(final Path path)
    throws IOException
    {
        removeRecursive(path, true);
    }
    
    /**
     * Recursively deleted a folder.
     * 
     * @param path
     *            The path to the folder that has to be deleted.
     * @param deleteItself
     *            If true, delete the folder indicated by <code>path</code>
     * @throws IOException
     */
    public static void removeRecursive(final Path path, final boolean deleteItself)
    throws IOException
    {
        Files.walkFileTree(path, new SimpleFileVisitor<Path>()
        {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
            throws IOException
            {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }
            
            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc)
            throws IOException
            {
                // try to delete the file anyway, even if its attributes
                // could not be read, since delete-only access is
                // theoretically possible
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }
            
            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc)
            throws IOException
            {
                if (exc == null)
                {
                    if (dir.equals(path))
                    {
                        if (deleteItself)
                        {
                            Files.delete(dir);
                        }
                    }
                    else
                    {
                        Files.delete(dir);
                    }
                    return FileVisitResult.CONTINUE;
                }
                else
                {
                    // directory iteration failed; propagate exception
                    throw exc;
                }
            }
        });
    }
    
    /**
     * Searches for the next available folder name.
     * 
     * @param basePath
     *            The parent folder in which you want to create the new folder.
     * @param baseName
     *            The base name of the new folder.
     * @return The number to add to the baseName to be able to create a new folder that doesn't already exist.
     */
    public static int getNextAvailablePath(Path basePath, String baseName)
    {
        int i = 0;
        Path path = null;
        do
        {
            path = FileSystems.getDefault().getPath(basePath.toString(), baseName + ++i);
        } while (path.toFile().exists());
        
        return i;
    }
    
    /**
     * Searches for the next available folder name and creates the corresponding folder.
     * 
     * @param basePath
     *            The parent folder in which you want to create the new folder.
     * @param baseName
     *            The base name of the new folder.
     * @return The path to the new folder.
     */
    public static Path getNextAvailablePathAndCreate(Path basePath, String baseName)
    {
        int i = getNextAvailablePath(basePath, baseName);
        Path p = basePath.resolve(baseName + i);
        return p;
    }
    
    /**
     * Reads a text file line by line
     * 
     * @param input
     *            The file to read
     * @return a List of Strings, the data read in the file, line by line
     * @throws IOException
     */
    public static List<String> readFileAsText(File input)
    throws IOException
    {
        FileReader fileReader = new FileReader(input);
        BufferedReader bufferedReader = null;
        
        List<String> lines = new ArrayList<String>();
        String line = null;
        try
        {
            bufferedReader = new BufferedReader(fileReader);
            while ((line = bufferedReader.readLine()) != null)
            {
                lines.add(line);
            }
        }
        finally
        {
            if (bufferedReader != null)
            {
                bufferedReader.close();
            }
        }
        return lines;
    }
}
