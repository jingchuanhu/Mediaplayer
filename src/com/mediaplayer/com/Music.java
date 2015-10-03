package com.mediaplayer.com;

import java.io.FileDescriptor;
import java.io.IOException;

import com.mediaplayer.db.SongInfoDatabase;
import com.mediaplayer.utility.SongsHolder;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.media.RingtoneManager;
import android.net.Uri;
import android.provider.MediaStore;

public class Music {

	MediaPlayer mediaPlayer;
	MusicChangeListeners listener;

	public Music(Activity context) {
		mediaPlayer = MediaPlayer.create(context, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
		mediaPlayer.reset();
	}

	public void setFileDescriptor(FileDescriptor fileDescriptor) {
		try {
			if(mediaPlayer.isPlaying()) {
				stop();
			}
			mediaPlayer.setDataSource(fileDescriptor);
			play();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException("Couldn't load music, uh oh!");
		}
	}

	public void onCompletion(MediaPlayer mediaPlayer) {
			if(listener!=null){
				listener.onSongCompleted();
			}
	}

	public void play() {
		try {
			synchronized (this) {
				mediaPlayer.prepare();
				mediaPlayer.start();
			}
		} catch (IllegalStateException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void resume(){
		mediaPlayer.start();
	}
	public void stop() {
		mediaPlayer.stop();
		mediaPlayer.reset();
	}

	public int getCurrentPosition() {
		return mediaPlayer.getCurrentPosition();
	}

	public void switchTracks() {
		mediaPlayer.seekTo(0);
		mediaPlayer.pause();
	}

	public void pause() {
		mediaPlayer.pause();
	}

	public boolean isPlaying() {
		return mediaPlayer.isPlaying();
	}

	public boolean isLooping() {
		return mediaPlayer.isLooping();
	}

	public void setLooping(boolean isLooping) {
		mediaPlayer.setLooping(isLooping);
	}

	public void setVolume(float volumeLeft, float volumeRight) {
		mediaPlayer.setVolume(volumeLeft, volumeRight);
	}

	public void dispose() {
		if (mediaPlayer.isPlaying()) {
			stop();
		}
		mediaPlayer.release();
	}

	public void seekTo(int position) {
		mediaPlayer.seekTo(position);
	}

	public interface MusicChangeListeners{
		void onSongStarted(SongInfo songInfo);
		void onSongCompleted();
	}
}