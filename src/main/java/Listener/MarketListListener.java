package Listener;

/*
* Created by shinixsensei-dev
* Github: https://github.com/shinixsensei-dev
* This code is owned by shinixsensei-dev/Miyaki Development
* Do NOT steal this code without asking!
*/

import Core.Conv;
import Core.Loader;
import Core.PokeKeyGetter;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.Timer;
import java.util.TimerTask;


public class MarketListListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        if(event.getMessage().getTextChannel().getGuild().getId().contains(Loader.getValue("guild"))
                && event.getMessage().getAuthor().getId().contains(Loader.getValue("targetid"))
                && event.getMessage().getAuthor().isBot()
                && event.getMessage().getEmbeds().size() == 1
                && event.getMessage().getEmbeds().get(0).getTitle().contains("has appeared!")) {
            String PokemonName;
            String ImageUrlMd5;
            try {
                ImageUrlMd5 = Conv.getMD5(event.getMessage().getEmbeds().get(0).getImage().getUrl());
                PokemonName = PokeKeyGetter.getValue(ImageUrlMd5);
                if(PokemonName == null) {
                    System.out.println("Pokemon not found!");
                }else {
                    event.getMessage().getTextChannel().sendMessage(
                            Loader.getValue("targetprefix") + "catch " + PokemonName
                    ).complete();
                    Timer myTimer1 = new Timer();
                    TimerTask task = new TimerTask() {
                        int secondsPassed = 0;
                        @Override
                        public void run() {
                            switch(secondsPassed){
                                case 0:  event.getMessage().getTextChannel().sendMessage(
                                        Loader.getValue("targetprefix") + "market list 1 5"
                                ).queue();
                                    secondsPassed++;
                                    break;
                                case 1: event.getTextChannel().sendMessage(Loader.getValue("targetprefix") + "confirmlist").queue();
                                    secondsPassed++;
                                    break;
                            }
                        }
                    };
                    myTimer1.schedule(task, 1000, 2000);
                }
            }catch (NullPointerException x) {
                try {
                    System.out.println("Pokemon not found!\n" +
                            "Searched for '" + Conv.getMD5(event.getMessage().getEmbeds().get(0).getImage().getUrl()) + "'");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

    }

}
