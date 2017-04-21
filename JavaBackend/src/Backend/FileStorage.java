package Backend;
import java.io.File;
import java.util.ArrayList;

//Stores the google doc files for later use
public class FileStorage extends ArrayList<String> {
	private static final long serialVersionUID = 1L;
	
	//The source and bin directories
	public File src;
	public File bin;

	//Creates a new empty file storage object
	public FileStorage() {super();}
	
	//Creates a file storage object at the set directory
	public FileStorage(File directory) {
		this();
		setDirectory(directory);
	}
	
	//Creates source and bin folders from scratch
	public FileStorage(File src, File bin) {
		this(); setSourceAndBin(src, bin);}
	
	//Sets the directory and creates src and bin folders
	public void setDirectory(File directory) {
		src = new File(directory.getAbsolutePath() + File.separator + "src");
		if(!src.exists()) {src.mkdir();}
		bin = new File(directory.getAbsolutePath() + File.separator + "bin");
		if(!bin.exists()) {bin.mkdir();}
	}
	
	//Sets the source and bin folders
	public void setSourceAndBin(File src, File bin) {
		this.src = src; if(!src.exists()) {src.mkdir();}
		this.bin = bin; if(!bin.exists()) {bin.mkdir();}
	}
	
	//Adds the string to the front of the array
	public void addToFront(String string) {this.add(0, string);}
}
