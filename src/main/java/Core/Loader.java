package Core;

import java.io.*;
import java.util.Properties;

public class Loader {

    private static Properties properties;
    static File file = new File("config.properties");

    public static void init(){
        properties = new Properties();
        try {
            InputStream in = new FileInputStream(file);
            properties.load(in);
            in.close();
            //load();
        } catch (IOException e) {
            System.out.println("Settings File '" + file.getName() + "' (" + file.getAbsolutePath() + ") not found\n" +
                                "Please restart!");
            fileNotFoundAction(file);
            System.exit(0);
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
        properties.put("token", "");
        properties.put("ownerid","" );
        properties.put("prefix", "");
        properties.put("guild", "");
        properties.put("targetid", "");
        properties.put("targetprefix", "");
        properties.put("type", "1");
        try {
            properties.store(new FileOutputStream(f), "discord64 Configuration\n" +
                    "Setup\n" +
                    "'token' = Paste your Account token here (I recommend to use an alt account)\n" +
                    "'ownerid' = Paste the id of the account, that is able to use the selfbot commands\n" +
                    "'prefix' = The prefix for the selfbot commands\n" +
                    "'guild' = Your target guild id\n" +
                    "'targetid' = Paste the id of the bot, you want to use discord64 on, here\n" +
                    "'targetprefix' = The Prefix of your target bot\n" +
                    "'type' = Select the target bot (1 = Pokecord, 0 = No Configuration, 2 = Advanced Autocatcher (Lists your pokemon on the market automatically))");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
