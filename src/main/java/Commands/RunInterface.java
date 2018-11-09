package Commands;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public interface RunInterface {

    boolean called(String[] args, MessageReceivedEvent event);
    void action(String[] args, MessageReceivedEvent event);
    void executed(boolean success, MessageReceivedEvent event);

}
