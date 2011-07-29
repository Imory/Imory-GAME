import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Draw extends JFrame {

	private boolean init = false;
	private Image images[];				//the actual images in an array
	private Image window_images[];		//array of the window images
	private String path;				//path of the images
	private String image_names[];		//names of the images in a array
	private int image_count;			//count of the i
	private int map_copy[][];			//copy of the 1. layer
	private int objects_copy[][];		//copy of the 2. layer
	private int objects_sec_copy[][];	//copy of the 3. layer
	private int player_x, player_y; 	//x and y coordinates of the player (in map fragments -> real position e.g. x*img_size_x 
	private int player_image;    		//contains the index of the player images in the images array
	private JFrame jframe;       		//this needs the draw function
	private int img_size_x = 20; 		//x size of the images
	private int img_size_y = 20; 		//y size of the images
	
	//Windows
	private Font font;
	private int window_count; 			//count of the windows which should be drawn
	private boolean show_window[]; 		//true if windows should be drawn
	private int[] window_type; 			//array which contains the type of each window which should be drawn
	private int[] window_x, window_y;	//x and y coordinates of the window
	private String window_text[];
	
	public Draw(String image_names[], String dir)
	{
		this.image_count = image_names.length;
		this.images = new Image[this.image_count];
		this.image_names = image_names;
		this.jframe = jframe;
		ImageIcon img_tmp;
		for(int l = 0; l < this.image_count; l++)
		{
			img_tmp = new ImageIcon(this.image_names[l]);
			this.images[l] = img_tmp.getImage();
		}
		System.out.println("Loaded Images: " + this.image_count);
		this.map_copy = new int[1][1];
		this.map_copy[0][0] = 0;
		this.init = true;
		this.window_images = new Image[3];
		this.path = dir;
		img_tmp = new ImageIcon(this.path + "window_speak_left.png");
		this.window_images[0] = img_tmp.getImage();
		img_tmp = new ImageIcon(this.path + "window_speak_right.png");
		this.window_images[1] = img_tmp.getImage();
		img_tmp = new ImageIcon(this.path + "window_speak_middle.png");
		this.window_images[2] = img_tmp.getImage();
		this.window_count = 0;
		this.window_type = new int[0];
		this.window_x = new int[0];
		this.window_y = new int[0];
		this.show_window = new boolean[0];
		this.window_text = new String[0];
		this.font = new Font("Lucida Calligraphy", Font.PLAIN, 12);
		
	}
	public void setImages(String image_names[], String dir)
	{
		this.image_count = image_names.length;
		images = new Image[image_count];
		this.image_names = image_names;
		this.jframe = jframe;
		ImageIcon img_tmp;
		for(int l = 0; l < image_count; l++)
		{
			img_tmp = new ImageIcon(this.image_names[l]);
			images[l] = img_tmp.getImage();
			System.out.println(this.image_names[l]);
		}
		System.out.println("Loaded Images: " + this.image_count);
	}
	public int getImageCount()
	{
		return this.image_count;
	}
	public void UpdateMap(int map_copy[][]) 
	{
		this.map_copy = map_copy;
	}
	public void UpdateObjects(int objects_copy[][])
	{
		this.objects_copy = objects_copy;
	}
	public void UpdateObjects_Sec(int objects_sec_copy[][])
	{
		this.objects_sec_copy = objects_sec_copy;
	}
	public void UpdatePlayer(int player_x, int player_y, int player_image) //Player aht immmer die selbe "stufe" mittelgrund 
	{
		this.player_x = player_x;
		this.player_y = player_y;
		this.player_image = player_image;
	}
	public int CreateNewWindow(int window_type, int window_x, int window_y, String window_text, boolean show_window)
	{
		this.window_count++;
		int[] tmp = new int[window_count];
		if(this.window_type.length != 0)
			System.arraycopy(this.window_type, 0, tmp, 0, this.window_type.length);
		tmp[this.window_type.length] = window_type;
		this.window_type = tmp;
		
		tmp = new int[window_count];
		if(this.window_x.length != 0)
			System.arraycopy(this.window_x, 0, tmp, 0, this.window_x.length);
		tmp[this.window_x.length] = window_type;
		this.window_x = tmp;
		
		tmp = new int[window_count];
		if(this.window_y.length != 0)
			System.arraycopy(this.window_y, 0, tmp, 0, this.window_y.length);
		tmp[this.window_y.length] = window_type;
		this.window_y = tmp;
		
		boolean[] temp = new boolean[window_count];
		if(this.show_window.length != 0)
			System.arraycopy(this.show_window, 0, temp, 0, this.show_window.length);
		temp[this.show_window.length] = show_window;
		this.show_window = temp;
		
		String str[] = new String[window_count];
		if(this.window_text.length != 0)
			System.arraycopy(this.window_text, 0, str, 0, this.window_text.length);
		str[this.window_text.length] = window_text;
		this.window_text = str;
		
		return window_count-1; //returns the index of the window used as a handle, in DestroyWindow
	}
	public boolean DestroyWindow(int index)
	{
		if(index < 0 || index >= window_count)
			return false;
		this.window_count--;
		int[] tmp = new int[this.window_type.length-1];
		if(index != 0)
			System.arraycopy(this.window_type, 0, tmp, 0, index);
		System.arraycopy(this.window_type, index+1, tmp, index, tmp.length-index);
		this.window_type = tmp;	
		
		tmp = new int[this.window_count];
		if(index != 0)
			System.arraycopy(this.window_x, 0, tmp, 0, index);
		System.arraycopy(this.window_x, index+1, tmp, index, tmp.length-index);
		this.window_x = tmp;	
		
		tmp = new int[this.window_count];
		if(index != 0)
			System.arraycopy(this.window_y, 0, tmp, 0, index);
		System.arraycopy(this.window_y, index+1, tmp, index, tmp.length-index);
		this.window_y = tmp;	
		
		boolean[] temp = new boolean[this.window_count];
		if(index != 0)
			System.arraycopy(this.show_window, 0, temp, 0, index);
		System.arraycopy(this.show_window, index+1, temp, index, temp.length-index);
		this.show_window = temp;	
		
		String[] str = new String[this.window_count];
		if(index != 0)
			System.arraycopy(this.window_text, 0, str, 0, index);
		System.arraycopy(this.window_text, index+1, str, index, str.length-index);
		this.window_text = str;	
		
		return true;
	}
	public void ShowWindow(int index, boolean show_window)
	{
		if(index < 0 || index >= this.window_count)
			return;
		this.show_window[index] = show_window;
	}
	public void SetWindowText(int index, String window_text)
	{
		if(index < 0 || index >= this.window_count)
			return;
		this.window_text[index] = window_text;
	}
	public void WriteWindowText(int x, int y, int cx, int cy, String window_text, Graphics g) //Syntax x, y, coordinates starting point, cx, cy x and y size
	{
		int lpl = cx/7; //Letters per Line
		String str;
		int z;
		int n;
		int line = 0;
		for(int l = 0; l < (window_text.length()/lpl)+1; l++)
		{
			n = window_text.length()-(52*l);
			if(n >= 52)
				z = 52;
			else
				z = n;
			str = window_text.substring(52*l, 52*l+z);
			//System.out.println(str);
			g.drawString(str, x, 100+this.img_size_y*(y+1)+line);
			line += 25;
		}
		
	}
	public void DrawSpeakWindow(Graphics g, String window_text)
	{
		int z = this.map_copy.length - 5;
		int a = this.map_copy[0].length - 4;
		g.drawImage(this.window_images[0], 100, 100+this.img_size_y*a, this.jframe);
		g.drawImage(this.window_images[1], 100+this.img_size_x*z, 100+this.img_size_y*a, this.jframe);
		z = z - 5;
		for(int l = 0; l < z; l++)
		{
			g.drawImage(this.window_images[2], 100+this.img_size_x*(5+l), 100+this.img_size_y*a, this.jframe);
		}
		this.WriteWindowText(100+this.img_size_x, a, (this.map_copy.length-2)*this.img_size_x, 80, window_text, g);
	}
	public void DrawAll(Graphics g)
	{
		//Benötige die bild größe ...
		//muss mir irgendwie mitgeteilt werden

	
		for(int l = 0;  l < this.map_copy.length; l++)
		{
			for(int m = 0; m < this.map_copy[l].length; m++)
			{
				if(this.images[this.map_copy[l][m]] == null)
				{
					System.out.println("Fatal Error");
				}
				g.drawImage(this.images[this.map_copy[l][m]], 100+img_size_x*l, 100+img_size_y*m, jframe); ///Hier bruache ich ein Handle von einem JFrame
				if(this.objects_copy[l][m] != 0)
				{
					g.drawImage(this.images[this.objects_copy[l][m]], 100+img_size_x*l, 100+img_size_y*m, jframe);
				}
					
			}
		}
		g.drawImage(this.images[this.player_image], 100+this.player_x*img_size_x, 100+this.player_y*img_size_y, jframe);
		for(int l = 0;  l < this.objects_sec_copy.length; l++)
		{
			for(int m = 0; m < this.objects_sec_copy[0].length; m++)
			{
				if(this.objects_sec_copy[l][m] != 0)
				{
					g.drawImage(this.images[this.objects_sec_copy[l][m]], 100+img_size_x*l, 100+img_size_y*m, jframe);
				}
					
			}
		}
		g.setFont(this.font);
		for(int l = 0; l < this.window_count; l++)
		{
			if(this.show_window[l] == false)
				continue;
			switch(this.window_type[l])
			{
			case 1:
				DrawSpeakWindow(g, this.window_text[l]);
				break;
			}
		}
	}
	
}
