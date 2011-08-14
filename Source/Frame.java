import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.swing.*;

public class Frame extends JFrame implements KeyListener, MouseListener, ActionListener  {

	private static final long serialVersionUID = 1L;
	
	Draw draw;
	Map map;
	Player player;
	Monster monster;
	Sound sound;
	Timer timer, sprite_timer, repaint_timer;
	PersonScript pscript;
	boolean init = false;
	boolean draw_window;
	int window_type;
	boolean superdraw = false;
	int slot;
	public Frame(String title) throws IOException, MidiUnavailableException, InvalidMidiDataException
	{
		super(title);
		this.setSize(800,600);
	    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	    this.setVisible(true);
	    this.addKeyListener(this);	
	    timer = new Timer(100, this);
	    sprite_timer = new Timer(50, this);
	    repaint_timer = new Timer(10, this);
	    slot = 0; //muss nacher weggeamcht werden
	    LoadLevel ll = new LoadLevel(slot);
	    if(ll.LoadThisLevel() == false)
	    {
	    	ll.Close();
	    	SaveLevel sl = new SaveLevel(slot);
	    	if(sl.IsInit() == false)
	    	{
	    		System.out.print("Could not create new level Save at slot: " + slot);
	    	}
	    	map = new Map("Example/");
	    	player = new Player(map.getPlayerPos_X(), map.getPlayerPos_Y(), 1, map.getPlayerImages());
	    	sl.SaveThisLevel(map, player);
	    	sl.Close();
	    	ll = new LoadLevel(0);
	    }
	    if(ll.LoadThisLevel() == false)
	    {
	    	System.out.print("Fatal Error; Could not Load Level!");
	    	return;
	    }
		map = new Map(ll.getMapDir());
		draw = new Draw(map.getImageNames(), map.getPicturePath(), this);
		draw.setImages(map.getImageNames(), map.getPicturePath());		
		sound = new Sound();
		sound.playBackgroundMusic(map.getMusic());
		System.out.println(map.getMusic());
		player = new Player(ll.getPlayerPosX(), ll.getPlayerPosY(), 1, map.getPlayerImages());
		player.Configurate(ll.getPlayerConfig());
		draw.CreateNewWindow(2, 0, 0, ""+player.getHealthPoints(), true);
		init = true;
		
		monster = null;
		timer.start();
		sprite_timer.start();
		repaint_timer.start();
		
		
	}
	public void paint(Graphics g) {
		
		//super.paint(g);
		if(superdraw == true)
		{
			super.paint(g);
			superdraw = false;
		}
		if(init == true)
		{
			draw.UpdateMap(map.getMap());
			draw.UpdateObjects(map.getObjects());
			draw.UpdateObjects_Sec(map.getSecObjects());
			draw.UpdatePlayer(player.getRX(), player.getRY(), player.getPlayerImage());
			draw.UpdateMonster(monster);
			draw.DrawAll(g);
		}
		
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		if(arg0.getKeyCode() == KeyEvent.VK_S)
		{
			this.setTitle("Imory - Saving Level...");
			System.out.println("Saving Level at slot: " + this.slot);
			SaveLevel sl = new SaveLevel(this.slot);
			sl.SaveThisLevel(map, player);
			sl.Close();
			this.setTitle("Imory");
		}
		if(arg0.getKeyCode() == KeyEvent.VK_T)
		{
			monster = new Monster(0, 0, map, draw);
		}
		if(player == null)
			return;
		player.move(arg0);
		if(map.checkCollision(player.getNX(), player.getNY()) == false)
		{
//			player.setX(player.getNX());
//			player.setY(player.getNY());
			//System.out.println("PLayer moving; to: " + player.getNX() + " | " + player.getNY());
			player.setMoving(true);
			if(monster != null)
				monster.Move(player.getX(), player.getY());
		}
		else
		{
			//System.out.println("Kollision");
			player.setNX(player.getX());
			player.setNY(player.getY());
			sound.playActionSound("COLLISION");
		}
		if(player.getAction() == true)
		{
			player.setAction(false);
			if(pscript != null && pscript.getWaitAction() == true)
			{
				pscript.setWaitAction(false);
				sound.playActionSound("ACTION_CLICK_2");
				repaint();
				return;
			}
			switch(map.checkMapAction(player.getX(), player.getY()))
			{
			case 1: //ACTION_NEXT_MAP
			{
				sound.playActionSound("STEPS_1");
				map.destroy_map();
				try {
					
					player = new Player(map.getNextPlayerPos_X(map.getLastNextMapIndex()), map.getNextPlayerPos_Y(map.getLastNextMapIndex()), 1, map.getPlayerImages());
					sound.stopBackgroundMusic();
					sound.resetBackgroundMusic();
					map = new Map(map.getLastNextMap());
					draw.setImages(map.getImageNames(), map.getPicturePath());
					sound.playBackgroundMusic(map.getMusic());
					superdraw = true;					
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			}			
			case 2: //ACTION_PERSON
				//this.setTitle("Imory - Person");
				sound.playActionSound("ACTION_CLICK_1");
				try {
					if(pscript != null)
						pscript.destroy();
					pscript = new PersonScript(map.getPersonScriptPath(), map.getLastPersonScript(), player, draw, map, sound, this.getGraphics());
				} catch (IOException e) {
					e.printStackTrace();
				}
				this.timer = new Timer(pscript.getDelay(), this);
				this.timer.start();
				pscript.doCommand();
				break;
			case 0: //ACTION_NO_ACTION
				//this.setTitle("Imory");
				break;
			}
			//Checking Script Action
			
		}
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == this.timer)
		{
			if(pscript != null && pscript.isInit() == true)
			{
				pscript.doCommand();
			}
			repaint();
		}
		else if(arg0.getSource() == this.sprite_timer)
		{
			draw.UpdateSprites();
		}
		else if(arg0.getSource() == this.repaint_timer)
		{
			player.Moving();
			repaint();
		}
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		if(arg0.getButton() == MouseEvent.BUTTON1)
		{
			
		}
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}