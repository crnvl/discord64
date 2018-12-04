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
       

        Timer myTimer1 = new Timer();
        TimerTask task = new TimerTask() {
            int secondsPassed = 0;
            @Override
            public void run() {
                switch(secondsPassed){
                    case 0: event.getTextChannel().sendMessage(event.getTextChannel().getTopic()).queue();
                        secondsPassed++;
                        break;
                    case 1: event.getTextChannel().sendMessage(event.getTextChannel().getTopic()).queue();
                        secondsPassed++;
                        break;
                    case 2: event.getTextChannel().sendMessage(event.getTextChannel().getTopic()).queue();
                        secondsPassed++;
                        break;
                    case 3: event.getTextChannel().sendMessage(event.getTextChannel().getTopic()).queue();
                        secondsPassed++;
                        secondsPassed = 0;
                        break;
                }
            }
        };
        myTimer1.schedule(task, 1000, 2000);

    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
