import java.io.IOException;
import java.util.HashMap;




public class ConfigFileReader {
	private HashMap<String, String> l;
	private String values[];
	private String keys[];
	
	public ConfigFileReader(String cFile) throws IOException{
		// To add a '.cfg' if missing
		cFile += (cFile.indexOf(".cfg") == -1)? ".cfg" : "";
		
		// Splits the file in lines and then each line into key and value
		Splitter s = new Splitter(cFile, "=");
		String[][] m = s.splitIt(2);
		
		// The returned HashMap
		l = new HashMap<String, String>();
		
		values = new String[m.length];	
		keys = new String[m.length];
		for(int i = 0; i < m.length; i++){
			if(m[i].length == 1 || m[i] == null) continue;
			
			l.put(m[i][0], m[i][1]);
			values[i] = m[i][1];
			keys[i] = m[i][0];
		}
		
		l.remove(null);
	}
	
	public HashMap<String, String> get(){
		return l;
	}
	public String[] getValues()
	{
		return values;
	}
	public String[] getKeys()
	{
		return keys;
	}	
	public static void main(String[] args) throws IOException{
		ConfigFileReader c = new ConfigFileReader("personsTable");
		HashMap<String, String> m = c.get();
		
		System.out.print(m.toString());
	}
}
