import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Ile{
	private FileReader read;
	private BufferedReader buffRead;
	private File file;
	private String content = "";
	
	public Ile(String fileName) throws IOException{
		try{
			file = new File(fileName);
			read = new FileReader(file);
			buffRead = new BufferedReader(read);
			setContent();
		} catch(IOException e){
			e.printStackTrace();
		}
	}
	
	protected void setContent() throws IOException{
		String l;
		while((l = buffRead.readLine()) != null){
			content += l + "\n";
		}
	}
	
	public String getContent(){
		return content;
	}
	
	public void printFile() throws IOException{
		System.out.println(getContent());
	}
	
	protected void finalize(){
		try {
			read.close();
			buffRead.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}