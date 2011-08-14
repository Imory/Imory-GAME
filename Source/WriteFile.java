import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class WriteFile {
	private File file;
	private FileWriter filewriter;
	private BufferedWriter bufferedwriter;
	private boolean init;
	
	public WriteFile(String file)
	{
		try
		{
			this.file = new File(file);
			System.out.println("Creating new File: " + file);
			this.file.createNewFile();
			this.filewriter = new FileWriter(this.file);
			this.bufferedwriter = new BufferedWriter(this.filewriter);
			this.init = true;
		}
		catch(IOException e)
		{
			e.printStackTrace();
			this.init = false;
		}
	}
	public boolean IsInit()
	{
		return this.init;
	}
	public void Write(String str)
	{
		if(this.init == false)
			return;
		try {
			this.bufferedwriter.write(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public void Close()
	{
		if(this.init == false)
			return;
		try {
			this.bufferedwriter.close();
			this.filewriter.close();
			this.init = false;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
