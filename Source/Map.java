import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;


public class Map {

	private String MapDir;
	private String picture_dir;
	private int size_x, size_y;
	private int next_map_pos_x[], next_map_pos_y[];
	private int next_map_pos_count;
	private int next_map_set_pos_x[], next_map_set_pos_y[];
	private int persons_pos_x[], persons_pos_y[];
	private int persons_pos_count;
	private int map[][];
	private int objects[][];
	private int objects_sec[][]; //Objekte ohne Kollision
	
	private int last_next_map;
	private int last_person;
	
	private String next_maps[];
	private String person_scripts[];
	private String person_script_path;
	private String person_path;
	
	private String image_names[];
	private int player_images[];
	
	private String music;
	
	private int PlayerPos[];
	//noch irgewndwas für die person action
	
	
	final static int ACTION_NO_ACTION = 0;
	final static int ACTION_NEXT_MAP = 1;
	final static int ACTION_PERSON = 2;
	final static int ACTION_UNUSED = 3;
	
	public Map(String dir) throws IOException
	{
		this.MapDir = dir;
		MapRead.init(dir);
		this.map = MapRead.getLayer0();
		this.objects = MapRead.getLayer1();
		this.objects_sec = MapRead.getlayer2();
		this.image_names = MapRead.getFields();
		this.PlayerPos = MapRead.getPlayerStartPos();
		this.music = MapRead.getBackgroundMusic();
		this.picture_dir = MapRead.getTerrainDir();
		String tmp[][] = MapRead.getNextMaps();
		this.next_map_pos_count = tmp.length;
		this.next_maps = new String[this.next_map_pos_count];
		this.next_map_pos_x = new int[this.next_map_pos_count];
		this.next_map_pos_y = new int[this.next_map_pos_count];
		this.next_map_set_pos_x = new int[this.next_map_pos_count];
		this.next_map_set_pos_y = new int[this.next_map_pos_count];
		
		System.out.println("NextMaps: " + this.next_map_pos_count);
		for(int l = 0; l < this.next_map_pos_count; l++)
		{
			this.next_maps[l] = tmp[l][4];
			this.next_map_pos_x[l] = Integer.parseInt(tmp[l][0]);
			this.next_map_pos_y[l] = Integer.parseInt(tmp[l][1]);
			this.next_map_set_pos_x[l] = Integer.parseInt(tmp[l][2]);
			this.next_map_set_pos_y[l] = Integer.parseInt(tmp[l][3]);
			System.out.println("Next Map at Pos: " + this.next_map_pos_x[l] + " " + this.next_map_pos_y[l]);
		}
		
		this.size_x = this.map.length;
		if(this.size_x != 0)
			this.size_y = this.map[0].length;
		System.out.println("Mapsize X: " + map.length);
		System.out.println("Mapsize Y: " + map[0].length);
		this.player_images = MapRead.getPlayerImages();
		
		this.person_path = MapRead.getPersonDir();
		this.person_script_path = MapRead.initPersons();
		this.persons_pos_count = MapRead.getPersonCount();
		this.persons_pos_x = new int[this.persons_pos_count];
		this.persons_pos_y = new int[this.persons_pos_count];
		this.person_scripts = new String[this.persons_pos_count];
		String tmp1[];
		for(int l = 0; l < this.persons_pos_count; l++)
		{
			tmp1 = MapRead.getPersonInfo(l);
			this.persons_pos_x[l] = Integer.parseInt(tmp1[0]);
			this.persons_pos_y[l] = Integer.parseInt(tmp1[1]);
			this.person_scripts[l] = dir + this.person_path + this.person_script_path + tmp1[2];
		}
	}
	public String getMapDir()
	{
		return this.MapDir;
	}
	public int getSizeX()
	{
		return this.size_x;
	}
	public int getSizeY()
	{
		return this.size_y;
	}
	public int[][] getMap()
	{
		return this.map;
	}
	public int[][] getObjects()
	{
		return this.objects;
	}
	public int[][] getSecObjects()
	{
		return this.objects_sec;
	}
	public String[] getImageNames()
	{
		return this.image_names;
	}
	public int[] getPlayerImages()
	{
		return this.player_images;
	}
	public int getPlayerPos_X()
	{
		if(PlayerPos.length == 0)
			return -1;
		return this.PlayerPos[0];
	}
	public int getPlayerPos_Y()
	{
		if(PlayerPos.length == 0)
			return -1;
		return this.PlayerPos[1];
	}
	public int getNextPlayerPos_X(int index)
	{
		return this.next_map_set_pos_x[index];
	}
	public int getNextPlayerPos_Y(int index)
	{
		return this.next_map_set_pos_y[index];
	}
	public int getLastNextMapIndex()
	{
		return this.last_next_map;
	}
	public int getLastPersonScriptIndex()
	{
		return this.last_person;
	}
	public String getPersonScript(int index)
	{
		return this.person_scripts[index];
	}
	public String getNextMap(int index)
	{
		return this.next_maps[index];
	}
	public String getLastPersonScript()
	{
		return this.person_scripts[this.last_person];
	}
	public String getLastNextMap()
	{
		return this.next_maps[this.last_next_map];
	}
	public String getMusic()
	{
		return this.music;
	}
	public String getPersonScriptPath()
	{
		return this.MapDir + this.person_path + this.person_script_path;
	}
	
	public String getPicturePath()
	{
		return this.MapDir + this.picture_dir;
	}
	public void setMapPosTo(int x, int y, int value)
	{
		this.map[x][y] = value;
	}
	public void setObjectsPosTo(int x, int y, int value)
	{
		this.objects[x][y] = value;
	}
	public void setObjectsSecPosTo(int x, int y, int value)
	{
		this.objects_sec[x][y] = value;
	}
	public int getMapPosTo(int x, int y)
	{
		return this.map[x][y];
	}
	public int getObjectsPosTo(int x, int y)
	{
		return this.objects[x][y];
	}
	public int getObjectsSecPosTo(int x, int y)
	{
		return this.objects_sec[x][y];
	}
	public boolean checkCollision(int x, int y) //Nur Objekte können Kollisionen erzeugen
	{
		if(x < 0 || y < 0)
		{
			return true;
		}
		else if(x >= this.size_x || y >= this.size_y)
		{
			return true;
		}
		else if(objects[x][y] != 0)////Hier müssen einfach alle Kollisions Objeckte hin...
			return true;
		else
			return false;
	}
	public boolean checkAround(int ax, int ay, int x, int y)
	{
		if(x-1 == ax && ay == y)
		{
			return true;
		}
		else if(x+1 == ax && ay == y)
		{
			return true;
		}
		else if(y-1 == ay && ax == x)
		{
			return true;
		}
		else if(y+1 == ay && ax == x)
		{
			return true;
		}
		return false;
	}
	public boolean checkNextMap(int x, int y)
	{
		for(int l = 0; l < this.next_map_pos_count; l++)
		{
			if(x == this.next_map_pos_x[l] && y == this.next_map_pos_y[l])
			{
				this.last_next_map = l;
				return true;
			}
		}
		return false;
	}
	public boolean checkPersonAction(int x, int y)
	{
		for(int l = 0; l < this.persons_pos_count; l++)
		{
			if(checkAround(x, y, this.persons_pos_x[l], this.persons_pos_y[l]))
			{
				this.last_person = l;
				return true;
			}
		}
		return false;
	}
	public int checkMapAction(int x, int y)
	{
		if(checkNextMap(x, y) == true)
			return this.ACTION_NEXT_MAP;
		else if(checkPersonAction(x, y) == true)
			return this.ACTION_PERSON;
		return this.ACTION_NO_ACTION;
	}	
	public void destroy_map()
	{
		this.map = null;
		this.objects = null;
		this.objects_sec = null;
		this.next_map_pos_count = 0;
		this.persons_pos_count = 0;
		return;
	}
	
	
}
