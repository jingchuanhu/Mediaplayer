package com.mediaplayer.com;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.os.Environment;
import android.provider.MediaStore;

import com.mediaplayer.db.SongInfoDatabase;
import com.mediaplayer.utility.SongsHolder;

public class SongsManager {
	static SongsManager  manager;
	private  Activity context;
	SongsHolder holder;
	Music music;
	SongInfoDatabase database;
	public static SongsManager getInstance(){
		if(manager==null){
			manager = new SongsManager();
		}
		return manager;
	}
	final String MEDIA_PATH = new String(Environment.getExternalStorageDirectory().getPath());

	public void pause(){
		music.pause();
	}

	public void play(){
		SongInfo currentSongInfo  = holder.getCurrentSongInfo();
		FileDescriptor fd = getFileDescriptor(currentSongInfo);
		music.setFileDescriptor(fd);
		music.play();
	}
	public void resume(){
		music.resume();
	}
	public void play(SongInfo info){
		holder.setCurrentSongInfo(info);
		play();
	}
	public void playNextSong() {
		database = new SongInfoDatabase(context);
		database.open();
		SongInfo nextSong = database.getNextSong(holder.getCurrentSongInfo());
		database.close();
		holder.addSongToQueue(nextSong);
		play(nextSong);
	}
	public void playPreviousSong(){
		int currentSongIndex = holder.getSongQueue().indexOf(holder.getCurrentSongInfo());
		SongInfo prevSong;
		if (currentSongIndex > 0) {
			prevSong = holder.getSongQueue().get(currentSongIndex - 1);
		} else {

			prevSong = holder.getSongQueue().getLast();
		}
		play(prevSong);
	}
	public void setContext(Activity context) {
		this.context = context;
		if(holder==null){
			holder = new SongsHolder();
		}
		if(music == null){
			music = new Music(context);
		}
	}

	private void randomSong(){

	}

	private void repeatSong(){

	}

	private FileDescriptor getFileDescriptor(SongInfo songInfo){
		FileInputStream fis = null;
		FileDescriptor fileDescriptor = null;
		try {
			fis = new FileInputStream(new File(songInfo.getData()));
			 fileDescriptor = fis.getFD();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	return fileDescriptor;
	}

}
