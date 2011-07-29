import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.swing.*;

public class Frame extends JFrame implements KeyListener, ActionListener  {

	private static final long serialVersionUID = 1L;
	
	Draw draw;
	Map map;
	Player player;
	Sound sound;
	Timer timer;
	PersonScript pscript;
	boolean init = false;
	boolean draw_window;
	int window_type;
	boolean superdraw = false;
	public Frame(String title) throws IOException, MidiUnavailableException, InvalidMidiDataException
	{
		super(title);
		this.setSize(800,600);
	    this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	    this.setVisible(true);
	    this.addKeyListener(this);	
	    timer = new Timer(100, this);
		map = new Map("Example\\");
		draw = new Draw(map.getImageNames(), map.getPicturePath());
		draw.setImages(map.getImageNames(), map.getPicturePath());
		player = new Player(map.getPlayerPos_X(), map.getPlayerPos_Y(), 1, map.getPlayerImages()); //Hier is halt auch noch die veränderung dirn
		sound = new Sound();
		sound.playBackgroundMusic(map.getMusic());
		System.out.println(map.getMusic());
		init = true;
		
		timer.start();
		
		
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
			draw.UpdatePlayer(player.getX(), player.getY(), player.getPlayerImage());
			draw.DrawAll(g);
		}
		
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		player.move(arg0);
		if(map.checkCollision(player.getNX(), player.getNY()) == false)
		{
			player.setX(player.getNX());
			player.setY(player.getNY());
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
			switch(map.checkMapAction(player.getX(), player.getY()))
			{
			case 1: //ACTION_NEXT_MAP
			{
				map.destroy_map();
				try {
					
					player = new Player(map.getNextPlayerPos_X(map.getLastNextMapIndex()), map.getNextPlayerPos_Y(map.getLastNextMapIndex()), 1, map.getPlayerImages());
					sound.stopBackgroundMusic();
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
				try {
					if(pscript != null)
						pscript.destroy();
					pscript = new PersonScript(map.getPersonScriptPath(), map.getLastPersonScript(), player, draw, this.getGraphics());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.timer = new Timer(pscript.getDelay(), this);
				this.timer.start();
				pscript.doCommand();
				break;
			case 0: //ACTION_NO_ACTION
				this.setTitle("Imory");
				break;
			}
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
	}
}