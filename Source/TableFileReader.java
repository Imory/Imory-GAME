import java.io.IOException;
import java.util.HashMap;


public class TableFileReader {
	private HashMap<String, String>[] h;
	
	public TableFileReader(String tableName) throws IOException{
		ConfigFileReader cfgFile = new ConfigFileReader(tableName + ".cfg");
		HashMap<String, String> cfg = cfgFile.get();

		Splitter file = new Splitter(tableName + ".tbl", "\\|");
		String[][] entries = file.splitIt(cfg.size());
		
		h = new HashMap[entries.length];
		
		for(int i = 0; i < entries.length; i++){
			h[i] = new HashMap<String, String>();
			
			for(int j = 0; j < entries[i].length; j++){
				h[i].put(cfg.get("col" + j), entries[i][j]);
			}
		}
	}
	
	public HashMap<String, String>[] get(){
		return h;
	}
	
	public static void main(String args[]) throws IOException{
		TableFileReader t = new TableFileReader("personsTable");
		HashMap<String, String>[] s = t.get();
		
		for(int i = 0; i < s.length; i++){
			System.out.println("[" + i + "]: " + s[i].toString());
		}
	}
}
