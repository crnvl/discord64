package Commands;

import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.Timer;
import java.util.TimerTask;

public class CountKazuma implements RunInterface{
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        event.getMessage().delete().queue();
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            int secondsPassed = 0;
            @Override
            public void run() {
                switch(secondsPassed){
                    case 0: event.getTextChannel().sendMessage(event.getTextChannel().getTopic()).queue();
                        secondsPassed = 0;
                        break;
                }
            }
        };
        timer.schedule(task, 2000, 2000);
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
