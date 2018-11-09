package Commands;

/*
* Created by shinixsensei-dev
* Github: https://github.com/shinixsensei-dev
* This code is owned by shinixsensei-dev/Miyaki Development
* Do NOT steal this code without asking!
*/

import Core.Loader;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class EchoCommand implements RunInterface {
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {

        String Message = event.getMessage().getContentRaw().replace(Loader.getValue("prefix") + "echo ", "");
        event.getMessage().getTextChannel().sendMessage(Message).complete();

    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
