package Commands;

/*
* Created by shinixsensei-dev
* Github: https://github.com/shinixsensei-dev
* This code is owned by shinixsensei-dev/Miyaki Development
* Do NOT steal this code without asking!
*/

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.Timer;
import java.util.TimerTask;

public class Spammer implements RunInterface {
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, final MessageReceivedEvent event) {

        Timer myTimer1 = new Timer();
        TimerTask task = new TimerTask() {
            int secondsPassed = 0;
            @Override
            public void run() {
                switch(secondsPassed){
                    case 0: event.getMessage().getTextChannel().sendMessage("Hellou, i need sum pkmn xd").complete();
                        secondsPassed++;
                        break;
                    case 1: event.getMessage().getTextChannel().sendMessage("Look how cute this pencil is!").complete();
                        secondsPassed++;
                        break;
                    case 2: event.getMessage().getTextChannel().sendMessage("Zac, that's gay").complete();
                        secondsPassed++;
                        break;
                    case 3: event.getMessage().getTextChannel().sendMessage("hui is god").complete();
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
