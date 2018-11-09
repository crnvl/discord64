package Commands;

/*
* Created by shinixsensei-dev
* Github: https://github.com/shinixsensei-dev
* This code is owned by shinixsensei-dev/Miyaki Development
* Do NOT steal this code without asking!
*/

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class StopCommand implements RunInterface {
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {

        event.getTextChannel().sendMessage("Stopping...").queue();
        System.exit(-1);

    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
