
public class Monster {

	private int x, y;
	private int nx, ny;
	private int xp, yp;
	private int rx, ry;
	private Map map;
	
	private int image;
	
	//Moving variables
	private boolean stop_moving;
	private boolean step_bf, ant, setant;
	private int step_x, step_y;
	private int player_x, player_y;
	private int lvalue;
	
	public Monster(int x, int y, Map map, Draw draw)
	{
		this.x = x;
		this.y = y;
		this.xp = -1;
		this.yp = -1;
		this.nx = x;
		this.ny = y;
		this.map = map;
		this.image = 16;
		this.stop_moving = false;
		this.step_bf = false;
		this.ant = false;
		this.setant = false;
		this.lvalue = 0;
	}
	public void setX(int x)
	{
		this.x = x;
	}
	public int getX()
	{
		return this.x;
	}
	public void setY(int y)
	{
		this.y = y;
	}
	public int getY()
	{
		return this.y;
	}
	public void setImage(int image)
	{
		this.image = image;
	}
	public int getImage()
	{
		return this.image;
	}
	public void setDirection()
	{
		this.step_x = this.player_x - this.x;
		this.step_y = this.player_y - this.y;
		if(ant == true)
		{
			this.step_x = this.x - this.player_x;
			this.step_y = this.y - this.player_y;
		}
		
		if(this.step_x > 0)
			this.step_x = 1;
		else if(this.step_x < 0)
			this.step_x = -1;
		else
			this.step_x = 0;
		if(this.step_y > 0)
			this.step_y = 1;
		else if(this.step_y < 0)
			this.step_y = -1;
		else
			this.step_y = 0;
	}
	public void setDirectionV(int value_x, int value_y)
	{
		this.step_x = value_x - this.x;
		this.step_y = value_y - this.y;
		if(ant == true)
		{
			this.step_x = this.x - this.player_x;
			this.step_y = this.y - this.player_y;
		}
		
		if(this.step_x > 0)
			this.step_x = 1;
		else if(this.step_x < 0)
			this.step_x = -1;
		else
			this.step_x = 0;
		if(this.step_y > 0)
			this.step_y = 1;
		else if(this.step_y < 0)
			this.step_y = -1;
		else
			this.step_y = 0;
	}	
	public boolean checkAround()
	{
		if(map.checkCollision(x-1, y) == false)
		{
			this.nx -= 1;
			//Draw();
			this.x = this.nx;
			this.ant = true;
			return true;
		}
		else if(map.checkCollision(x+1, y) == false)
		{
			this.nx += 1;
			//Draw();
			this.x = this.nx;
			this.ant = true;
			return true;
		}
		else if(map.checkCollision(x, y-1) == false)
		{
			this.ny -= 1;
			//Draw();
			this.y = this.ny;
			this.ant = true;
			return true;
		}
		else if(map.checkCollision(x, y+1) == false)
		{
			this.ny += 1;
			//Draw();
			this.y = this.ny;
			this.ant = true;
			return true;
		}
		return false;
	}
	public boolean getDirectionY()
	{
		if(this.step_y > 0)
			return false;
		return true;
	}
	public boolean getDirectionX()
	{
		if(this.step_x > 0)
			return false;
		return true;
	}
	public int SearchWayY(boolean updown) //up = true; down = false
	{
//		if(updown)
//			System.out.println("Search Way - UP");
//		else
//			System.out.println("Search Way - DOWN");
		int z = 0;
		int y1 = this.y;
		if(updown)
			z = -1;
		else
			z = 1;
		y1 += z;
		
		int xp = -1; //Position an der der weg frei ist
		int mc[][] = map.getObjects();
		for(int x = 0; x < map.getSizeX(); x++)
		{
			if(mc[x][this.y] != 0 && this.x < x)
				break;
			else if(mc[x][this.y] == 0 && mc[x][y1] == 0)
			{
				xp = x;
				if(this.player_x < x)
					break;
			}
		}
		return xp;
	}
	public int SearchWayX(boolean leftright) //left = true; right = false
	{
//		if(leftright)
//			System.out.println("Search Way - LEFT");
//		else
//			System.out.println("Search Way - RIGHT");
		int z = 0;
		int x1 = this.x;
		if(leftright)
			z = -1;
		else
			z = 1;
		x1 += z;
		
		int yp = -1; //Position an der der weg frei ist
		int mc[][] = map.getObjects();
		for(int y = 0; y < map.getSizeY(); y++)
		{
			if(mc[this.x][y] != 0 && this.y < y)
				break;
			else if(mc[this.x][y] == 0 && mc[x1][y] == 0)
			{
				yp = y;
				if(this.player_y < y)
					break;
			}
		}
		return yp;
	}
	public void Draw()
	{
		map.setObjectsPosTo(this.x, this.y, this.lvalue);
		this.lvalue = map.getObjectsPosTo(this.nx, this.ny);
		map.setObjectsPosTo(this.nx, this.ny, 16);
	}
	public void Reset()
	{
		this.stop_moving = false;
		this.step_bf = false;
		this.ant = false;
		this.setant = false;
	}
	public void setMove()
	{
		//this.lx = this.x;
		this.x = this.nx;
		//this.ly = this.y;
		this.y = this.ny;
	}
	public boolean MoveX()
	{
		if(this.step_x != 0)
		{
			if(this.yp == this.y)
				this.yp = -1;
			if(yp == -1)
				setDirection();
			else
				setDirectionV(this.player_x, this.yp);
			this.nx = this.x + this.step_x;
			if(map.checkCollision(this.nx, this.ny) == true)
			{
				this.yp = SearchWayX(getDirectionX());
				if(this.yp != -1)
					setDirectionV(this.player_x, this.yp);
				this.nx = this.x;
				this.ny = this.y + this.step_y;
				if(map.checkCollision(this.nx, this.ny) == true)
				{
					this.ny = this.y;
					if(this.checkAround() == false)
						this.stop_moving = true;
				}
				else
				{
					setMove();
					this.ant = false;
				}
					
			}
			else
			{
				setMove();
				this.step_bf = !this.step_bf;
				this.ant = false;
			}
			return true;
		}
		return false;
	}
	public boolean MoveY()
	{
		if(this.step_y != 0)
		{
			if(this.xp == this.x)
				this.xp = -1;
			if(xp == -1)
				setDirection();
			else
				setDirectionV(this.xp, this.player_y);
			this.ny = this.y + this.step_y;
			if(map.checkCollision(this.nx, this.ny) == true)
			{
				this.xp = SearchWayY(getDirectionY());
				if(this.xp != -1)
					setDirectionV(this.xp, this.player_y);
				this.ny = this.y;
				this.nx = this.x + this.step_x;
				if(map.checkCollision(this.nx, this.ny) == true)
				{
					this.nx = this.x;
					if(this.checkAround() == false)
						this.stop_moving = true;
				}
				else
				{
					setMove();
					this.ant = false;
				}
			}
			else
			{
				setMove();
				this.step_bf = !this.step_bf;
				this.ant = false;
			}
			return true;
		}
		return false;
	}
	public void RMove()
	{
		if(this.stop_moving == true)
			return;
		if(this.step_bf)
		{
			if(MoveY() == false)
				MoveX();			
		}
		else
		{
			if(MoveX() == false)
				MoveY();
		}
	}
	public void Move(int player_x, int player_y)
	{
		this.player_x = player_x;
		this.player_y = player_y;
		this.setDirection();
		this.RMove();
		if(stop_moving == true)
			Reset();
	}
	
}
