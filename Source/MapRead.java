import java.io.IOException;
import java.util.HashMap;


public class MapRead {
	public static String MapDir = "./maps/standard/";
	public static HashMap<String, String> genCFG;
	public static HashMap<String, String> personCFG;
	public static boolean person_init = false;
	
	public static void init() throws IOException{
		ConfigFileReader genCFGReader = new ConfigFileReader(MapRead.MapDir + "general.cfg");
		genCFG = genCFGReader.get();
	}
	
	public static void init(String MapDir) throws IOException{
		MapRead.MapDir = MapDir;
		MapRead.init();
	}
	
	public static String[] getFields() throws IOException{
		ConfigFileReader FieldsCFGReader = new ConfigFileReader(MapRead.MapDir + "map.cfg");
		HashMap<String, String>FieldsCFG = FieldsCFGReader.get();
		
		String MapFieldsDir = MapDir + genCFG.get("TERRAIN_DIR");
		
		String[] map = new String[FieldsCFG.size()];
		for(int i = 0; i < FieldsCFG.size(); i++){
			map[i] = MapFieldsDir + FieldsCFG.get(i + "");
		}
		
		return map;
	}
	public static String getTerrainDir()
	{
		return genCFG.get("TERRAIN_DIR");
	}
	public static int[][] getLayer0() throws IOException{
		
		ConfigFileReader cfg = new ConfigFileReader(MapRead.MapDir + genCFG.get("MAP_LAYER_CFG"));
		HashMap<String, String> hm = cfg.get();
		int layer[][] = new int[Integer.parseInt(hm.get("COL"))][Integer.parseInt(hm.get("ROW"))];
		Splitter splitter = new Splitter((MapRead.MapDir + genCFG.get("MAP_LAYER_CFG") + ".tbl"), "\\|");
		String str[][] = splitter.splitIt();
		for(int l = 0; l < layer.length; l++)
		{
			for(int m = 0; m < layer[l].length; m++)
			{
				layer[l][m] = Integer.parseInt(str[l][m]);
			}
		}
		return layer;
		/*TableFileReader layer0tbl = new TableFileReader(MapRead.MapDir + genCFG.get("MAP_LAYER_CFG"));
		HashMap<String, String>[] layer0 = layer0tbl.get();
		
		int[][]layer = new int[layer0.length][layer0[0].size()];
		for(int i = 0; i < layer0.length; i++){
			for(int j = 0; j < layer0[i].size(); j++){
				String tmp = layer0[i].get("" + j);
				System.out.println(tmp);
				layer[i][j] = Integer.parseInt(layer0[i].get("" + j));
			}
		}
		
		return layer;*/
	}
	
	public static int[][] getLayer1() throws IOException{
		ConfigFileReader cfg = new ConfigFileReader(MapRead.MapDir + genCFG.get("ACTION_LAYER_CFG"));
		HashMap<String, String> hm = cfg.get();
		int layer[][] = new int[Integer.parseInt(hm.get("COL"))][Integer.parseInt(hm.get("ROW"))];
		Splitter splitter = new Splitter((MapRead.MapDir + genCFG.get("ACTION_LAYER_CFG") + ".tbl"), "\\|");
		String str[][] = splitter.splitIt();
		for(int l = 0; l < layer.length; l++)
		{
			for(int m = 0; m < layer[l].length; m++)
			{
				layer[l][m] = Integer.parseInt(str[l][m]);
			}
		}
		return layer;
		/*TableFileReader layer1tbl = new TableFileReader(MapRead.MapDir + genCFG.get("ACTION_LAYER_CFG"));
		HashMap<String, String>[] layer1 = layer1tbl.get();
		
		int[][]layer = new int[layer1.length][layer1[0].size()];
		for(int i = 0; i < layer1.length; i++){
			for(int j = 0; j < layer1[i].size(); j++){
				layer[i][j] = Integer.parseInt(layer1[i].get("" + j));
			}
		}
		
		return layer;*/
	}
	
	public static int[][] getlayer2() throws IOException{
		ConfigFileReader cfg = new ConfigFileReader(MapRead.MapDir + genCFG.get("FOREGROUND_LAYER_CFG"));
		HashMap<String, String> hm = cfg.get();
		int layer[][] = new int[Integer.parseInt(hm.get("COL"))][Integer.parseInt(hm.get("ROW"))];
		Splitter splitter = new Splitter((MapRead.MapDir + genCFG.get("FOREGROUND_LAYER_CFG") + ".tbl"), "\\|");
		String str[][] = splitter.splitIt();
		for(int l = 0; l < layer.length; l++)
		{
			for(int m = 0; m < layer[l].length; m++)
			{
				layer[l][m] = Integer.parseInt(str[l][m]);
			}
		}
		return layer;
		/*TableFileReader layer2tbl = new TableFileReader(MapRead.MapDir + genCFG.get("FOREGROUND_LAYER_CFG"));
		HashMap<String, String>[] layer2 = layer2tbl.get();
		
		int[][]layer = new int[layer2.length][layer2[0].size()];
		for(int i = 0; i < layer2.length; i++){
			for(int j = 0; j < layer2[i].size(); j++){
				layer[i][j] = Integer.parseInt(layer2[i].get("" + j));
			}
		}
		
		return layer;*/
	}
	
	public static int[] getPlayerStartPos(){
		int[] xy = new int[2];
		      xy[0] = Integer.parseInt(genCFG.get("PLAYER_X"));
		      xy[1] = Integer.parseInt(genCFG.get("PLAYER_Y"));
		
		return xy;
	}
	
	public static String getBackgroundMusic()
	{
		return genCFG.get("MAP_MUSIC");
	}
	
	public static String[][] getNextMaps(){
		String nMaps = genCFG.get("NEXT_MAP_POS");
		String[] maps = nMaps.split("\\|");
		String[][] retMaps = new String[maps.length][5];
		
		for(int i = 0; i < maps.length; i++){
			retMaps[i] = maps[i].split(",");
		}		
		return retMaps;
	}
	public static int[] getPlayerImages()
	{
		String str = genCFG.get("PLAYER_IMAGES");
		String tmp[] = str.split("\\|");
		int i[] = new int[4];
		if(tmp.length < 4)
			return null;
		i[0] = Integer.parseInt(tmp[0]);
		i[1] = Integer.parseInt(tmp[1]);
		i[2] = Integer.parseInt(tmp[2]);
		i[3] = Integer.parseInt(tmp[3]);
		return i;		
	}
	public static String getPersonDir()
	{
		return genCFG.get("PERSON_DIR");
	}
	public static String initPersons() throws IOException //Returns PATH/DIR of the person scripts
	{
		ConfigFileReader cfg = new ConfigFileReader(MapDir + genCFG.get("PERSON_DIR") + "general.cfg");
		personCFG = cfg.get();
		if(personCFG != null)
			person_init = true;
		return personCFG.get("PERSON_SCRIPT_PATH");
	}
	public static int getPersonCount()
	{
		return Integer.parseInt(personCFG.get("PERSON_COUNT"));
	}
	public static String[] getPersonInfo(int index)
	{
		if(index > getPersonCount())
			return null;
		String str[] = new String[3];
		str[0] = personCFG.get(index + "_PERSON_X");
		str[1] = personCFG.get(index + "_PERSON_Y");
		str[2] = personCFG.get(index + "_PERSON_SCRIPT");
		return str;
	}
}
