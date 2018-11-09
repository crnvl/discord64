package Core;

/*
* Created by shinixsensei-dev
* Github: https://github.com/shinixsensei-dev
* This code is owned by shinixsensei-dev/Miyaki Development
* Do NOT steal this code without asking!
*/

import Commands.*;
import Listener.CommandListener;
import Listener.MarketListListener;
import Listener.PokecordListener;
import Listener.ReadyListener;
import Worker.CommandHandler;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import javax.security.auth.login.LoginException;


public class Main {

    public static JDABuilder builder;
    public static JDA jda;

    public static void main(String[] Args) throws LoginException, InterruptedException {
        Loader.init();
        PokeKeyGetter.init();
        builder = new JDABuilder(AccountType.CLIENT);
        builder.setToken(System.getenv("TOKEN"));
        builder.setAutoReconnect(true);
        builder.setAudioEnabled(true);
        builder.addEventListener(new CommandListener());
        builder.addEventListener(new ReadyListener());
        if(Loader.getValue("type").startsWith("1")) {
            builder.addEventListener(new PokecordListener());
        }else if (Loader.getValue("type").startsWith("2")) {
            builder.addEventListener(new MarketListListener());
        }
        addCommands();

        try {
            JDA jda = builder.buildBlocking();
        } catch (LoginException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static void addCommands() {
        CommandHandler.commands.put("spammer", new Spammer());
        CommandHandler.commands.put("echo", new EchoCommand());
        CommandHandler.commands.put("music", new MusicCommand());
        CommandHandler.commands.put("reload", new RefreshConfig());
        CommandHandler.commands.put("exit", new StopCommand());
    }

}
