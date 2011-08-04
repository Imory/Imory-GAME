import java.awt.Image;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.ImageIcon;


public class Sprite {
	
	private Image images[];
	private int image_count;
	private int image_index;
	private int index;
	private int delay;
	private long last_ticks;
	
	public Sprite(String dir, int index) throws IOException
	{
		this.index = index;
		this.image_index = 0;
		this.last_ticks = System.currentTimeMillis();
		String tmp;
		ImageIcon img_icon;
		
		if(dir.indexOf(".sprite") != -1)
		{
			dir = dir.substring(0, dir.indexOf(".sprite"));
			dir += "/";
		}
		ConfigFileReader cfg = new ConfigFileReader(dir + "sprite.cfg");
		HashMap<String, String> hm = cfg.get();
		this.image_count = Integer.parseInt(hm.get("IMAGES"));
		this.delay = Integer.parseInt(hm.get("DELAY"));
		this.images = new Image[this.image_count];
		
		
		for(int l = 0; l < this.image_count; l++)
		{
			tmp = hm.get("IMAGE_" + l);
			if(tmp == null)
			{
				System.out.println("Config Error; in File: " + dir + "sprite.cfg; at: IMAGE_" + l);
				System.out.println("\t-> Error caused by value: " + tmp);
				System.out.println("\t-> at step: " + l);
				break;
			}
			System.out.println("Loaded Sprite Image: " + dir+tmp);
			img_icon = new ImageIcon(dir+tmp);
			images[l] = img_icon.getImage();						
		}
		
	}
	public void Update()
	{
		if((this.last_ticks+this.delay) <= System.currentTimeMillis())
		{
			this.last_ticks = System.currentTimeMillis();
			this.image_index++;
			//System.out.println("Sprite Index: " + this.image_index);
			if(this.image_index >= this.image_count)
				this.image_index = 0;
		}
	}
	public Image getImage()
	{
		return this.images[this.image_index];
	}
	public int getIndex()
	{
		return this.index;
	}

}
