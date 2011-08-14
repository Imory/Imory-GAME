
public class SaveLevel {
	private WriteFile file, player_file;
	private int slot;
	private boolean init;
	
	public SaveLevel(int slot) //slot where level should be saved into like: Save 1; Save 2; 
	{
		if(slot >= 10)
		{
			this.init = false;
			return;
		}
		String file = "Saves/save_0"+slot+".cfg";
		this.slot = slot;
		this.file = new WriteFile(file);
		System.out.println("Init OUTPUT for file: " + file + " is: " + this.file.IsInit());
		file = "Saves/player_save_0"+slot+".cfg";
		this.player_file = new WriteFile(file);
		System.out.println("Init OUTPUT for file: " + file + " is: " + this.player_file.IsInit());
		this.init = this.file.IsInit();
		if(this.init == true)
			this.init = this.player_file.IsInit();
	}
	public boolean SaveThisLevel(Map map, Player player)
	{
		if(this.init == false)
			return false;
		this.file.Write("[Level Save 0" + slot + " Conifg]\n");
		this.file.Write("MAP_DIR="+map.getMapDir()+"\n");
		this.file.Write("PLAYER_POS_X="+player.getX()+"\n");
		this.file.Write("PLAYER_POS_Y="+player.getY()+"\n");
		this.file.Write("PLAYER_CONFIG=Saves/player_save_0"+slot+".cfg\n");
		this.player_file.Write("[Player Save 0" + slot + " Config\n");
		this.player_file.Write("PLAYER_X="+player.getX()+"\n");
		this.player_file.Write("PLAYER_Y="+player.getY()+"\n");
		this.player_file.Write("PLAYER_HP="+player.getHealthPoints()+"\n");
		this.player_file.Write("PLAYER_EP="+player.getExperiencePoints()+"\n");
		System.out.println("Saved Level at slot: " + this.slot);
		return true;
	}
	public boolean IsInit()
	{
		return this.init;
	}
	public void Close()
	{
		this.init = false;
		this.file.Close();
		this.player_file.Close();
	}
}
