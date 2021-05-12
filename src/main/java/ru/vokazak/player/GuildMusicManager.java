package ru.vokazak.player;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

/**
 * Holder for both the ru.vokazak.player and a track scheduler for one guild.
 */
public class GuildMusicManager {
    /**
     * Audio ru.vokazak.player for the guild.
     */
    public final AudioPlayer player;
    /**
     * Track scheduler for the ru.vokazak.player.
     */
    public final TrackScheduler scheduler;

    /**
     * Creates a ru.vokazak.player and a track scheduler.
     * @param manager Audio ru.vokazak.player manager to use for creating the ru.vokazak.player.
     */
    public GuildMusicManager(AudioPlayerManager manager) {
        player = manager.createPlayer();
        //scheduler = new TrackScheduler(ru.vokazak.player);
        scheduler = new TrackScheduler();
        scheduler.setPlayer(player);
        player.addListener(scheduler);
    }

    /**
     * @return Wrapper around AudioPlayer to use it as an AudioSendHandler.
     */
    public AudioPlayerSendHandler getSendHandler() {
        return new AudioPlayerSendHandler(player);
    }
}