package Listener;


import Core.Loader;
import Worker.CommandHandler;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.Objects;

public class CommandListener extends ListenerAdapter {

    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getMessage().getContentRaw().startsWith(Loader.getValue("prefix"))
                && !Objects.equals(event.getMessage().getAuthor().getId(), event.getJDA().getSelfUser().getId())
                && !event.getMessage().getAuthor().isBot()
                && event.getAuthor().getId().contains(Loader.getValue("ownerid"))) {
            CommandHandler.handleCommand(CommandHandler.parse.parser(event.getMessage().getContentRaw(), event, Loader.getValue("prefix")));
        }

}}
