package Commands;

/*
* Created by shinixsensei-dev
* Github: https://github.com/shinixsensei-dev
* This code is owned by shinixsensei-dev/Miyaki Development
* Do NOT steal this code without asking!
*/

import Core.Loader;
import Listener.MarketListListener;
import Listener.PokecordListener;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.Objects;

public class RefreshConfig implements RunInterface {
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        Loader.init();
        if(Loader.getValue("type").startsWith("1")) {
            event.getJDA().addEventListener(new PokecordListener());
        }else if (Loader.getValue("type").startsWith("2")) {
            event.getJDA().addEventListener(new MarketListListener());
        }
        event.getTextChannel().sendMessage("Configuration has been reloaded!").queue();

    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
