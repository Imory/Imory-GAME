import java.applet.Applet;
import java.applet.AudioClip;
import java.io.*;
import java.util.HashMap;
import java.net.MalformedURLException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;


public class Sound {

	
	private AudioClip bgm_wave_sound[];
	private Sequencer bgm_midi_sound[];
	
	private AudioClip ac_wave_sound[];
	private Sequencer ac_midi_sound[];
		
	private String bgm_midi_sounds[];
	private String bgm_wave_sounds[];
	private String ac_midi_sounds[];
	private String ac_wave_sounds[];
	
	private boolean bgm_playing_midi;
	private int bgm_playing_index;
	
	private HashMap<String, String> bgm_midi;
	private HashMap<String, String> bgm_wave;
	private HashMap<String, String> ac_midi;
	private HashMap<String, String> ac_wave;
	
	public Sound() throws InvalidMidiDataException, IOException, MidiUnavailableException
	{
		String bgm_path;
		String ac_path;
		String midi_config;
		String wave_config;
		String tmp1[];
		String tmp2[];
		ConfigFileReader cfg = new ConfigFileReader("Sound/general.cfg");
		HashMap<String, String> hm = cfg.get();
		bgm_path = hm.get("BackgroundMusicPath");
		ac_path = hm.get("ActionPath");
		
		cfg = new ConfigFileReader("Sound/" + bgm_path + "general.cfg");
		hm = cfg.get();
		midi_config = hm.get("MidiConfig");
		wave_config = hm.get("WaveConfig");
		cfg = new ConfigFileReader("Sound/" + bgm_path + midi_config);
		tmp2 = cfg.getKeys();
		this.bgm_midi = new HashMap<String, String>();
		this.bgm_midi_sounds = cfg.getValues();
		cfg = new ConfigFileReader("Sound/" + bgm_path + wave_config);
		tmp1 = cfg.getKeys();
		this.bgm_wave = new HashMap<String, String>();
		this.bgm_wave_sounds = cfg.getValues();
		
		this.initBackgroundMusicMidi(this.bgm_midi_sounds.length-1);
		for(int l = 0; l < this.bgm_midi_sounds.length-1; l++)
		{
			this.bgm_midi_sounds[l] = "Sound/" + bgm_path + "Midi/" + this.bgm_midi_sounds[l];
			this.loadBackgroundMusicMidi(this.bgm_midi_sounds[l], l);
			this.bgm_midi.put(tmp2[l], ""+l);
		}
		this.initBackgroundMusicWave(this.bgm_wave_sounds.length-1);
		for(int l = 0; l < this.bgm_wave_sounds.length-1; l++)
		{
			this.bgm_wave_sounds[l] = "Sound/" + bgm_path + "Wave/" + this.bgm_wave_sounds[l];
			this.loadBackgroundMusicWave(this.bgm_wave_sounds[l], l);
			this.bgm_wave.put(tmp1[l], ""+l);
		}
		
		cfg = new ConfigFileReader("Sound/" + ac_path + "general.cfg");
		hm = cfg.get();
		midi_config = hm.get("MidiConfig");
		wave_config = hm.get("WaveConfig");
		cfg = new ConfigFileReader("Sound/" + ac_path + midi_config);
		tmp2 = cfg.getKeys();
		this.ac_midi = new HashMap<String, String>();
		this.ac_midi_sounds = cfg.getValues();
		cfg = new ConfigFileReader("Sound/" + ac_path + wave_config);
		tmp1 = cfg.getKeys();
		this.ac_wave = new HashMap<String, String>();
		this.ac_wave_sounds = cfg.getValues();
		
		this.initActionMidiSound(this.ac_midi_sounds.length-1);
		for(int l = 0; l < this.ac_midi_sounds.length-1; l++)
		{
			this.ac_midi_sounds[l] = "Sound/" + ac_path + "Midi/" + this.ac_midi_sounds[l];
			this.loadActionMidiSound(this.ac_midi_sounds[l], l);
			this.ac_midi.put(tmp2[l], ""+l);
		}
		this.initActionWaveSound(this.ac_wave_sounds.length-1);
		for(int l = 0; l < this.ac_wave_sounds.length-1; l++)
		{
			this.ac_wave_sounds[l] = "Sound/" + ac_path + "Wave/" + this.ac_wave_sounds[l];
			this.loadActionWaveSound(this.ac_wave_sounds[l], l);
			this.ac_wave.put(tmp1[l], ""+l);
		}
		
		
		
//		loadBackgroundMusic("Sound/BackgroundMusic/remember.mid");
//		loadCollisionSound("Sound/Action/collision.wav");
	}
	public void initActionWaveSound(int count)
	{
		this.ac_wave_sound = new AudioClip[count];
	}
	public void initActionMidiSound(int count)
	{
		this.ac_midi_sound = new Sequencer[count];
	}
	public boolean loadActionWaveSound(String sound, int index) throws MalformedURLException
	{
		File f = new File(sound);
		if(f == null)
		{
			return false;
		}
		this.ac_wave_sound[index] = Applet.newAudioClip(f.toURL());
		return true;
	}
	public boolean loadActionMidiSound(String sound, int index) throws MidiUnavailableException, InvalidMidiDataException, IOException
	{	
		InputStream midifile = new FileInputStream(sound);
		if(midifile == null)
		{
			return false;
		}
		this.ac_midi_sound[index] = MidiSystem.getSequencer();
		this.ac_midi_sound[index].open();		
		this.ac_midi_sound[index].setSequence( MidiSystem.getSequence(midifile) ); 
		return true;
	}
	public void playActionSound(String sound)
	{
		String play;
		if((play = this.ac_wave.get(sound)) == null)
		{
			if((play = this.ac_midi.get(sound)) == null)
				return;
			int i = Integer.parseInt(play);
			this.ac_midi_sound[i].start();
		}
		else
		{
			int i = Integer.parseInt(play);
			this.ac_wave_sound[i].play();
		}
	}
	public void initBackgroundMusicMidi(int count)
	{
		this.bgm_midi_sound = new Sequencer[count];
	}
	public void initBackgroundMusicWave(int count)
	{
		this.bgm_wave_sound = new AudioClip[count];
	}
	public boolean loadBackgroundMusicWave(String sound, int index) throws MalformedURLException
	{
		File f = new File(sound);
		if(f == null)
		{
			return false;
		}
		this.bgm_wave_sound[index] = Applet.newAudioClip(f.toURL());
		return true;
	}
	public boolean loadBackgroundMusicMidi(String sound, int index) throws MidiUnavailableException, InvalidMidiDataException, IOException
	{	
		InputStream midifile = new FileInputStream(sound);
		if(midifile == null)
		{
			return false;
		}
		this.bgm_midi_sound[index] = MidiSystem.getSequencer();
		this.bgm_midi_sound[index].open();		
		this.bgm_midi_sound[index].setSequence( MidiSystem.getSequence(midifile) ); 
		return true;
	}
	public void playBackgroundMusic(String sound)
	{
		String play;
		if((play = this.bgm_wave.get(sound)) == null)
		{
			if((play = this.bgm_midi.get(sound)) == null)
				return;
			int i = Integer.parseInt(play);
			this.bgm_midi_sound[i].start();
			this.bgm_midi_sound[i].setLoopCount(100);
			this.bgm_playing_index = i;
			this.bgm_playing_midi = true;
		}
		else
		{
			int i = Integer.parseInt(play);
			this.bgm_wave_sound[i].play();
			this.bgm_wave_sound[i].loop();
			this.bgm_playing_index = i;
			this.bgm_playing_midi = false;
		}
	}
	public void stopBackgroundMusic() //this pauses the music to reset call the reset method
	{
		if(this.bgm_playing_midi == true)
		{
			this.bgm_midi_sound[this.bgm_playing_index].stop();
		}
		else
		{
			this.bgm_wave_sound[this.bgm_playing_index].stop();
		}
	}
	public void resetBackgroundMusic()
	{
		if(this.bgm_playing_midi == true)
		{
			this.bgm_midi_sound[this.bgm_playing_index].setMicrosecondPosition(0);
		}
	}
}

