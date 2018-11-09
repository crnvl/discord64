package Core;

import java.io.*;
import java.util.Properties;

public class PokeKeyGetter {

    private static Properties properties;
    static File file = new File("pokes.properties");

    public static void init(){
        properties = new Properties();
        try {
            InputStream in = new FileInputStream(file);
            properties.load(in);
            in.close();
            //load();
        } catch (IOException e) {
            System.out.println("Settings File '" + file.getName() + "' (" + file.getAbsolutePath() + ") not found!\n");
            fileNotFoundAction(file);
        }
    }

    public static boolean propExist(String key) {
        if (properties.getProperty(key) == null) {
            return false;
        } else {
            return true;
        }
    }

    public static String getValue(String key) {
        return properties.getProperty(key);
    }

    public static void addKey(String hash, String name) {
        properties.put(hash, name);
        try {
            properties.store(new FileOutputStream(file), "discord64 Configuration");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void fileNotFoundAction(File f){

    }

}
