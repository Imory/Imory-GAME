import java.io.File;
import java.io.IOException;
import java.util.HashMap;


public class LoadLevel {
	private ConfigFileReader cfg;
	private HashMap<String, String> hm;
	private int slot;
	private boolean init;
	
	public LoadLevel(int slot)
	{
		if(slot >= 10)
		{
			this.init = false;
			return;
		}
		String file = "Saves/save_0"+slot+".cfg";
		File test = new File(file);
		System.out.println(test + (test.exists()? " is found " : " is missing "));
		if(test.exists() == false)
		{
			this.init = false;
			return;
		}
		try {
			cfg = new ConfigFileReader(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.init = true;
	}
	public boolean LoadThisLevel()
	{
		if(this.init == false)
			return false;
		this.hm = cfg.get();
		if(this.checkValid() == false)
		{
			this.init = false;
			return false;
		}
		return true;
	}
	public boolean checkValid()
	{
		if(this.hm.get("MAP_DIR") == null || this.hm.get("PLAYER_CONFIG") == null )
		{
			System.out.println(this.hm.get("MAP_DIR") + " " + this.hm.get("PLAYER_CONFIG"));
			System.out.println("The Save is Invalid!");
			return false;
		}
		File test = new File(this.hm.get("PLAYER_CONFIG"));
		System.out.println(test + (test.exists()? " is found " : " is missing "));
		if(test.exists() == false)
		{
			this.init = false;
			return false;
		}
		return true;
	}
	public String getMapDir()
	{
		if(this.init == false)
			return "Example/";
		return this.hm.get("MAP_DIR");
	}
	public int getPlayerPosX()
	{
		if(this.init == false)
			return -1;
		return Integer.parseInt(this.hm.get("PLAYER_POS_X"));
	}
	public int getPlayerPosY()
	{
		if(this.init == false)
			return -1;
		return Integer.parseInt(this.hm.get("PLAYER_POS_Y"));
	}
	public String getPlayerConfig()
	{
		if(this.init == false)
			return null;
		return this.hm.get("PLAYER_CONFIG");
	}
	public void Close()
	{
		this.hm = null;
		this.cfg = null;
		this.init = false;
	}
}
