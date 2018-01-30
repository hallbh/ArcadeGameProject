package GameObjects;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundPlayer implements LineListener {

	private boolean playCompleted;
	private File audioFile;
	private Clip music;
	
	public SoundPlayer(String path) {
		this.audioFile = new File(path);
	}

	@Override
	public void update(LineEvent e) {
		if(e.getType() == LineEvent.Type.START){
			//do nothing
		} else if (e.getType() == LineEvent.Type.STOP) {
			this.playCompleted = true;
		}

	}
	
	public void play(){
		try {
			AudioInputStream stream = AudioSystem.getAudioInputStream(this.audioFile);
			AudioFormat format = stream.getFormat();
			DataLine.Info i = new DataLine.Info(Clip.class, format);
			this.music = (Clip) AudioSystem.getLine(i);
			
			this.music.addLineListener(this);
			this.music.open(stream);
			this.music.start();
			
		} catch (UnsupportedAudioFileException e) {
			System.out.println("Unsupported audio format");
		} catch (LineUnavailableException e) {
			System.out.println("Something broke :(");
		} catch (IOException e) {
			System.out.println("Error playing sound");
			e.printStackTrace();
		}
	}
	
	public void loop(){
		try {
			AudioInputStream stream = AudioSystem.getAudioInputStream(this.audioFile);
			AudioFormat format = stream.getFormat();
			DataLine.Info i = new DataLine.Info(Clip.class, format);
			this.music = (Clip) AudioSystem.getLine(i);
			
			this.music.addLineListener(this);
			this.music.open(stream);
			
			this.music.loop(100);
			
		} catch (UnsupportedAudioFileException e) {
			System.out.println("Unsupported audio format");
		} catch (LineUnavailableException e) {
			System.out.println("Something broke :(");
		} catch (IOException e) {
			System.out.println("Error playing sound");
			e.printStackTrace();
		}

	}
	
	public void setFramePosition(int frames) {
		this.music.setFramePosition(frames);
	}
	
	public void kill(){
		this.music.close();
	}

}
