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

public class PokecordListener extends ListenerAdapter {

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
