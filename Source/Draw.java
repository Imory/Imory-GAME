import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Draw extends JFrame {

	boolean init = false;
	Image images[];
	String image_names[];
	int image_count;
	int map_copy[][];
	int objects_copy[][];
	int objects_sec_copy[][];
	int player_x, player_y;
	int player_image;
	JFrame jframe;
	
	public Draw(String image_names[])
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
		}
		System.out.println("Loaded Images: " + image_count);
		map_copy = new int[1][1];
		map_copy[0][0] = 0;
		init = true;
	}
	public void setImages(String image_names[])
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
		System.out.println("Loaded Images: " + image_count);
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
	public void DrawAll(Graphics g)
	{
		//Benötige die bild größe ...
		//muss mir irgendwie mitgeteilt werden
		int img_size_x = 20;
		int img_size_y = 20;
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
	}
	
}
