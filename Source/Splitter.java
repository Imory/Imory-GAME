import java.io.IOException;


public class Splitter{
	private String splitter = "\\|";
	private String content;
	
	public Splitter(String file) throws IOException{
		Ile i = new Ile(file);
		content = i.getContent();
	}
	
	public Splitter(String file, String split) throws IOException{
		splitter = split;
		Ile i = new Ile(file);
		content = i.getContent();
	}
	
	public String[][] splitIt(){
		String[] lines = content.split("\\n");
		String[][] ret = new String[lines.length][lines[0].split(splitter).length];
		
		for(int i = 0; i < lines.length; i++){
			String[] line = lines[i].split(splitter);
			
			if(line.length == 1) continue;
			
			for(int j = 0; j < line.length; j++){
				ret[i][j] = line[j];
			}
		}
		
		return ret;
	}
	
	public String[][] splitIt(int col_count){
		String[] lines = content.split("\\n");
		String[][] ret = new String[lines.length][col_count];
		
		int refactor = 0;
		
		for(int i = 0; i < lines.length; i++){
			String[] line = lines[i].split(splitter);
			
			if(line.length == 1){
				refactor--;
				continue;
			}
			
			for(int j = 0; j < line.length; j++){
				ret[i + refactor][j] = line[j];
			}
		}
		
		return ret;
	}
}
