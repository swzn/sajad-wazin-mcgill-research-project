package sajad.wazin.mcgill.ca.utils;


import sajad.wazin.mcgill.ca.FacebookWebScraper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import static sajad.wazin.mcgill.ca.FacebookWebScraper.LOGGER;


/**
 * @author Sajad Wazin @ https://github.com/swzn
 * @project FacebookWebScraper
 * @mail sajad.wazin@mail.mcgill.ca
 */

public class ResourcesManager {

    public static ResourcesManager RESOURCES = null;

    public static String ICON_PATH;

    private Properties properties;

    private Path currTmpDir;

    /*
    * Resources Manager is responsible for internal I/O. It is as singleton class
    * */

    private ResourcesManager() {
        properties = new Properties();

        try {
            currTmpDir = Files.createTempDirectory("fws");

            InputStream exeChromeDriver = FacebookWebScraper.class.getClassLoader().getResourceAsStream("chromedriver.exe");
            ICON_PATH = FacebookWebScraper.class.getClassLoader().getResource("fws_icon64x64.png").toString();
            if(exeChromeDriver == null) {
                System.out.println("Oups");
            }

            else Files.copy(exeChromeDriver, Path.of(currTmpDir.toAbsolutePath().toString() + "\\chromedriver.exe"));

            properties.load(FacebookWebScraper.class.getClassLoader().getResourceAsStream("FacebookWebScraper.properties"));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // gets value of "resourceKey" in FacebookWebScraper.properties
    public String getResource(String resourceKey) {
        return properties.getProperty(resourceKey);
    }

    // gets the path of the temporary chromedriver.exe file on disk
    public String getChromeDriverPath(){
        return currTmpDir.toAbsolutePath().toString() + "\\chromedriver.exe";
    }

    // Singleton instantiation
    public static ResourcesManager getResourceManager() {
        if(RESOURCES == null) {
            RESOURCES = new ResourcesManager();
        }
        return RESOURCES;
    }

    public Path getTempDir(){
        return currTmpDir;
    }

    // Delete temporary files after execution has completed
    public void deleteTemp(){
        LOGGER.log("Deleting temporary files...");
        if(getTempDir().toFile().exists()) {
            for (String innerFile : getTempDir().toFile().list()){
                File currentDeletable = new File(getTempDir().toFile().getPath(), innerFile);
                if(currentDeletable.delete()) {
                    if(getTempDir().toFile().delete()) {
                        LOGGER.log("Deleted temporary file " +  currentDeletable.getName() + "!");
                    }
                    else {
                        LOGGER.log("Error deleting temporary file at path:" + currentDeletable.getAbsolutePath());
                    }
                }
            }
        }
    }

}
