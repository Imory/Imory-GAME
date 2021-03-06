import java.awt.Graphics;
import java.io.IOException;
import java.util.HashMap;


public class PersonScript {

	private HashMap<String, String> script_language;
	private String commands[][];
	private int delay;
	private int count;
	private int step;
	private Player player;
	private Draw draw;
	private Map map;
	private Sound sound;
	private Graphics g;
	private boolean init;
	private boolean wait_action;
	
	private int speaking_window;
	private boolean init_sp_window;
	
	public PersonScript(String dir, String file, Player player, Draw draw, Map map, Sound sound, Graphics g) throws IOException
	{
		
		ConfigFileReader cfg = new ConfigFileReader("script_language.cfg");
		this.script_language = cfg.get();
		cfg = new ConfigFileReader(file);
		HashMap<String, String> hm = cfg.get();
		this.count = Integer.parseInt(hm.get("COMMAND_COUNT"));
		this.delay = Integer.parseInt(hm.get("COMMAND_DELAY"));
		String script = hm.get("SCRIPT");
		Splitter split = new Splitter(dir + script, "=");
		commands = split.splitIt();
		step = 0;
		this.sound = sound;
		this.draw = draw;
		this.player = player;
		this.map = map;
		this.g = g;
		this.init = true;
		this.init_sp_window = false;
		this.wait_action = false;
		this.player.setScriptedScene();
	}
	public void doCommand()
	{
		if(this.wait_action == true)
		{
			//System.out.println("Waiting for action");
			return;
		}
		if(step < 0 || step >= commands.length)
		{
			if(this.init == true)
				this.destroy();
			return;
		}
		System.out.println(commands[step][0]);
		int i = Integer.parseInt(this.script_language.get(commands[step][0]));
		switch(i)
		{
		case 0:
		{
			String tmp[] = commands[step][1].split("\\|");
			this.player.setPos(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]));
			this.player.setNPos(this.player.getX(), this.player.getY());
			break;
		}
		case 1:
		{
			if(this.init_sp_window == false)
			{
				this.init_sp_window = true;
				this.speaking_window = draw.CreateNewWindow(1, 0, 0, commands[step][1]+"\nPress 'G'", true); 	//creates a new speak window, x and y are unimportant the window has always the same position,
																								//window_text is our speak text set window visible,
			}
			else
			{
				draw.SetWindowText(this.speaking_window, commands[step][1]+"\nPress 'G'");
			}
			this.setWaitAction(true);
			break;
		}
		case 2:
		{
			String tmp[] = commands[step][1].split("\\|");
			this.map.setObjectsPosTo(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]), Integer.parseInt(tmp[2]));
			break;	
		}
		case 3:
		{
			String tmp = commands[step][1];
			this.sound.playActionSound(tmp);
		}
		}
		step++;
		//System.out.println("Next Person Script Step: " + step);
		return;
	}
	public void destroy()
	{
		this.player.setNormal();
		this.commands = null;
		this.script_language = null;
		this.init = false;
		this.draw.DestroyWindow(this.speaking_window);
	}
	public boolean isInit()
	{
		return this.init;
	}
	public int getDelay()
	{
		return this.delay;
	}
	public void setWaitAction(boolean wait_action)
	{
		this.wait_action = wait_action;
	}
	public boolean getWaitAction()
	{
		return this.wait_action;
	}
}
