package Listener;

/*
* Created by shinixsensei-dev
* Github: https://github.com/shinixsensei-dev
* This code is owned by shinixsensei-dev/Miyaki Development
* Do NOT steal this code without asking!
*/

import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class ReadyListener extends ListenerAdapter {

    @Override
    public void onReady(ReadyEvent event) {
        System.out.println("Connected with Discord user " + event.getJDA().getSelfUser().getName()+ "\n" +
                            "Detected Email: " + event.getJDA().getSelfUser().getEmail() + "\n" +
                        "Discord tag: " + event.getJDA().getSelfUser().getName() + "#" + event.getJDA().getSelfUser().getDiscriminator());
    }
}
