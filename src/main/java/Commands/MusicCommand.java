package Commands;

import AudioCore.AudioInfo;
import AudioCore.PlayerSendHandler;
import AudioCore.TrackManager;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class MusicCommand implements RunInterface  {

    private static final int PLAYLIST_LIMIT = 1;
    private static Guild guild;
    private static final AudioPlayerManager MANAGER = new DefaultAudioPlayerManager();
    private static final Map<Guild, Map.Entry<AudioPlayer, TrackManager>> PLAYERS = new HashMap<>();
    private static String clueURL = "https://www.youtube.com/watch?v=OZRqvsBKVak";
    private static final String NOTE = ":musical_note:  ";
    private static final AudioPlayerManager myManager = new DefaultAudioPlayerManager();
    private static final Map<String, Map.Entry<AudioPlayer, TrackManager>> players = new HashMap<>();

    public MusicCommand() {
        AudioSourceManagers.registerRemoteSources(MANAGER);
    }

    private AudioPlayer createPlayer(Guild g) {
        AudioPlayer p = MANAGER.createPlayer();
        TrackManager m = new TrackManager(p);
        p.addListener(m);

        guild.getAudioManager().setSendingHandler(new PlayerSendHandler(p));

        PLAYERS.put(g, new AbstractMap.SimpleEntry<>(p, m));

        return p;
    }

    private boolean hasPlayer(Guild g) {
        return PLAYERS.containsKey(g);
    }

    private AudioPlayer getPlayer(Guild g) {
        if (hasPlayer(g))
            return PLAYERS.get(g).getKey();
        else
            return createPlayer(g);
    }

    private TrackManager getManager(Guild g) {
        return PLAYERS.get(g).getValue();
    }

    private boolean isIdle(Guild g) {
        return !hasPlayer(g) || getPlayer(g).getPlayingTrack() == null;
    }

    private void loadTrack(String identifier, Member author, Message msg) {

        Guild guild = author.getGuild();
        getPlayer(guild);

        MANAGER.setFrameBufferDuration(5000);
        MANAGER.loadItemOrdered(guild, identifier, new AudioLoadResultHandler() {

            @Override
            public void trackLoaded(AudioTrack track) {
                getManager(guild).queue(track, author);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                for (int i = 0; i < (playlist.getTracks().size() > PLAYLIST_LIMIT ? PLAYLIST_LIMIT : playlist.getTracks().size()); i++) {
                    getManager(guild).queue(playlist.getTracks().get(i), author);
                }

            }

            @Override
            public void noMatches() {

            }

            @Override
            public void loadFailed(FriendlyException exception) {
                exception.printStackTrace();
            }
        });

    }

    private void skip(Guild g) {
        getPlayer(g).stopTrack();
    }

    private String getTimestamp(long milis) {
        long seconds = milis / 1000;
        long hours = Math.floorDiv(seconds, 3600);
        seconds = seconds - (hours * 3600);
        long mins = Math.floorDiv(seconds, 60);
        seconds = seconds - (mins * 60);
        return (hours == 0 ? "" : hours + ":") + String.format("%02d", mins) + ":" + String.format("%02d", seconds);
    }

    private String buildQueueMessage(AudioInfo info) {
        AudioTrackInfo trackInfo = info.getTrack().getInfo();
        String title = trackInfo.title;
        long length = trackInfo.length;
        return "`[ " + getTimestamp(length) + " ]` " + title + "\n";
    }

    private void sendErrorMsg(MessageReceivedEvent event, String content) {
        event.getTextChannel().sendMessage(
                new EmbedBuilder()
                        .setColor(Color.red)
                        .setDescription(content)
                        .build()
        ).queue();
    }


    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return false;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {


        guild = event.getGuild();

        if (args.length < 1) {
            return;
        }

        switch (args[0].toLowerCase()) {

            case "play":
            case "p":
                if (args.length < 2) {
                    sendErrorMsg(event, "Please enter a valid source!");
                    return;
                }
                if (event.getMember().getVoiceState().inVoiceChannel()) {
                    String input = Arrays.stream(args).skip(1).map(s -> " " + s).collect(Collectors.joining()).substring(1);
                    event.getTextChannel().sendMessage("Searching ``" + input + "``").queue();
                    event.getTextChannel().sendMessage(NOTE + "``" + input + "`` has been added to the queue!").queueAfter(2, TimeUnit.SECONDS);

                    if (!(input.startsWith("http://") || input.startsWith("https://")))

                        input = "ytsearch: " + input;
                    loadTrack(input, event.getMember(), event.getMessage());
                }else {
                    event.getTextChannel().sendMessage(
                            new EmbedBuilder().setColor(Color.RED)
                                    .setTitle("ERROR")
                                    .setDescription("You have to be in a Voice channel to use this command!").build()).queue();
                }
                break;


            case "skip":
            case "s":

                if (isIdle(guild)) return;
                if (event.getMember().getVoiceState().inVoiceChannel()) {
                    for (int i = (args.length > 1 ? Integer.parseInt(args[1]) : 1); i == 1; i--) {
                        skip(guild);
                        event.getTextChannel().sendMessage("Skipping...").queue();
                    }
                }else {
                    event.getTextChannel().sendMessage(
                            new EmbedBuilder().setColor(Color.RED)
                                    .setTitle("ERROR")
                                    .setDescription("You have to be in a Voice channel to use this command!").build()).queue();
                }
                break;


            case "stop":

                if (isIdle(guild)) return;
                if (event.getMember().getVoiceState().inVoiceChannel()) {
                    getManager(guild).purgeQueue();
                    skip(guild);
                    guild.getAudioManager().closeAudioConnection();
                    event.getTextChannel().sendMessage("The Playback has ended").queue();
                }else {
                    event.getTextChannel().sendMessage(
                            new EmbedBuilder().setColor(Color.RED)
                                    .setTitle("ERROR")
                                    .setDescription("You have to be in a Voice channel to use this command!").build()).queue();
                }
                break;

            case "shuffle":
                if (isIdle(guild)) return;
                if (event.getMember().getVoiceState().inVoiceChannel()) {
                    getManager(guild).shuffleQueue();
                    event.getTextChannel().sendMessage("Shuffling...").queue();
                }else {
                    event.getTextChannel().sendMessage(
                            new EmbedBuilder().setColor(Color.RED)
                                    .setTitle("ERROR")
                                    .setDescription("You have to be in a Voice channel to use this command!").build()).queue();
                }
                break;


            case "now":
            case "info":
            case "np":

                if (isIdle(guild)) return;
                if (event.getMember().getVoiceState().inVoiceChannel()) {
                    AudioTrack track = getPlayer(guild).getPlayingTrack();
                    AudioTrackInfo info = track.getInfo();

                    event.getTextChannel().sendMessage(
                            new EmbedBuilder()
                                    .setDescription("**Current Song!:**")
                                    .addField("Title","[" + info.title + "](" + info.uri + ")", false)
                                    .addField("Length", "`[ " + getTimestamp(track.getPosition()) + "/ " + getTimestamp(track.getDuration()) + " ]`", false)
                                    .addField("Uploaded by", info.author, false)
                                    .build()
                    ).queue();
                }else {
                    event.getTextChannel().sendMessage(
                            new EmbedBuilder().setColor(Color.RED)
                                    .setTitle("ERROR")
                                    .setDescription("You have to be in a Voice channel to use this command!").build()).queue();
                }
                break;

        }
    }

    @Override
    public void executed ( boolean sucess, MessageReceivedEvent event){

    }

}
