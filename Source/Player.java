import java.awt.event.KeyEvent;


public class Player {

	private int x, y;
	private int nx, ny;
	private int player_image;
	private int player_direction; 
	private int[] images;
	private boolean action;
	private boolean moving;
	private boolean script;
	private boolean movable;
	
	private int health_points;
	private int experience_points;
	private int armor;
	private int attack;
	private int speed;
	
	private Inventory inv;
	
	
	public Player(int x, int y, int player_direction, int[] images)
	{
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
		if(movable == false)
			return;
		int key = e.getKeyCode();
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
		else if(key == KeyEvent.VK_G)
		{
			this.action = true;
		}
		this.player_image = getImageByDirection();
		//System.out.println("Player Pos: " + this.x + " " + this.y);
	}
	
}
