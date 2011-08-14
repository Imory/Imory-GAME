import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.HashMap;


public class Player {

	private int x, y;
	private int nx, ny;
	private int rx, ry;
	private int player_image;
	private int player_direction; 
	private int[] images;
	private boolean action;
	private boolean moving;
	private boolean script;
	private boolean movable;
	
	private long last_ticks;
	private int img_size_x, img_size_y;
	
	private int health_points;
	private int experience_points;
	private int armor;
	private int attack;
	private int speed;
	
	private Inventory inv;
	
	
	
	public Player(int x, int y, int player_direction, int[] images)
	{
		this.img_size_x = 20;
		this.img_size_y = 20;
		
		this.speed = 20;
		this.health_points = 100;
		this.experience_points = 0;
		
		this.rx = x*this.img_size_x;
		this.ry = y*this.img_size_y;
		this.x = x;
		this.y = y;
		this.nx = x;
		this.ny = y;
		this.action = false;
		this.player_direction = player_direction;
		this.images = images; //hatte keine ahnung wie ich die bidler ids sonst zu ordnen sollte
		this.moving = false;
		this.script = false;
		this.movable = true;
		player_image = this.getImageByDirection();
	}
	public void Configurate(String config) throws IOException
	{
		ConfigFileReader cfg = new ConfigFileReader(config);
		HashMap<String, String> hm = cfg.get();
		this.x = Integer.parseInt(hm.get("PLAYER_X"));
		this.y = Integer.parseInt(hm.get("PLAYER_Y"));
		this.health_points = Integer.parseInt(hm.get("PLAYER_HP"));
		this.experience_points = Integer.parseInt(hm.get("PLAYER_EP"));
		
	}
	public int getX()
	{
		return this.x;
	}
	public int getY()
	{
		return this.y;
	}
	public void setX(int x)
	{
		this.x = x;
	}
	public void setY(int y)
	{
		this.y = y;
	}
	public int getRX()
	{
		return this.rx;
	}
	public int getRY()
	{
		return this.ry;
	}
	public void setRX(int rx)
	{
		this.rx = rx;
	}
	public void setRY(int ry)
	{
		this.ry = ry;
	}
	public int getNX()
	{
		return this.nx;
	}
	public void setPos(int x, int y)
	{
		this.setX(x);
		this.setY(y);
	}
	public int getNY()
	{
		return this.ny;
	}
	public void setNX(int nx)
	{
		this.nx = nx;
	}
	public void setNY(int ny)
	{
		this.ny = ny;
	}
	public void setNPos(int nx, int ny)
	{
		this.setNX(nx);
		this.setNY(ny);
	}
	public boolean getAction()
	{
		return this.action;
	}
	public void setAction(boolean action)
	{
		this.action = action;
	}
	public int getHealthPoints() {
		return this.health_points;
	}
	public void setHealthPoints(int health_points) {
		this.health_points = health_points;
	}
	public int getExperiencePoints() {
		return experience_points;
	}
	public void setExperiencePoints(int experience_points) {
		this.experience_points = experience_points;
	}
	public int getPlayerImage()
	{
		return this.player_image;
	}
	public int getPlayerDirection()
	{
		return this.player_direction;
	}
	public boolean getMoving()
	{
		return this.moving;
	}
	public void setMoving(boolean moving)
	{
		this.moving = moving;
	}
	public boolean getScript()
	{
		return this.script;
	}
	public void setScript(boolean script)
	{
		this.script = script;
	}
	public boolean getMovable()
	{
		return this.movable;
	}
	public void setMovable(boolean movable)
	{
		this.movable = movable;
	}
	public void setScriptedScene()
	{
		this.script = true;
		this.movable = false;
	}
	public void setNormal()
	{
		this.script = false;
		this.movable = true;
	}
	public int  getImageByDirection() //Ist die Frage wie das mit den BilderIDs geregelt wird der Player hat ja verschiedene Richtungen
	{
		//Nach Unten = 1
		//Nach Links = 2
		//Nach Rechts = 3
		//Nach Oben = 4
		
		return this.images[this.player_direction-1];
	}
	public void move(KeyEvent e)
	{
		int key = e.getKeyCode();
		if(key == KeyEvent.VK_G) //Has to be first checking action in PersonScript
		{
			this.action = true;
		}
		if(movable == false)
			return;
		if(key == KeyEvent.VK_DOWN)
		{
			this.ny = this.y + 1;
			this.player_direction = 4;
		} 
		else if(key == KeyEvent.VK_LEFT)
		{
			this.nx = this.x - 1;
			this.player_direction = 2;
		}
		else if(key == KeyEvent.VK_RIGHT)
		{
			this.nx = this.x + 1;
			this.player_direction = 3;
		}
		else if(key == KeyEvent.VK_UP)
		{
			this.ny = this.y - 1;
			this.player_direction = 1;
		}
		this.player_image = getImageByDirection();
		//System.out.println("Player Pos: " + this.x + " " + this.y);
	}
	public boolean checkRNByDirection()
	{
		switch(this.player_direction)
		{
		case 1:
			if(this.ry <= this.ny*this.img_size_y)
				return true;
			break;
		case 2:
			if(this.rx <= this.nx*this.img_size_x)
				return true;
			break;
		case 3:
			if(this.rx >= this.nx*this.img_size_x)
				return true;
			break;
		case 4:
			if(this.ry >= this.ny*this.img_size_y)
				return true;
			break;
		}
		return false;
	}
	public void Moving()
	{
		if(this.moving == false)
			return;
		long tmp;
		if((tmp =System.currentTimeMillis()) >= last_ticks+speed)
		{
			last_ticks = tmp;
			switch(this.player_direction)
			{
			case 1:
				this.ry -= 2;
				break;
			case 2:
				this.rx -= 2;
				break;
			case 3:
				this.rx += 2;
				break;
			case 4:
				this.ry += 2;
				break;
			}
			if(this.checkRNByDirection())
			{
				this.setMoving(false);
				//System.out.println("Setting state..." + this.x + " " + this.y + " "+ this.nx + " " + this.ny);
				this.x = this.nx;
				this.y = this.ny;
				this.rx = this.x*this.img_size_x;
				this.ry = this.y*this.img_size_y;
			}
		}
	}
	
}
