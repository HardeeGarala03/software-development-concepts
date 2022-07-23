public class FileIdentifier {
	// private variable id of the file
	private int id;
	// variable to store location of the file
	private String filelocation;
	// variable to store filename of the file
	private String filename;

	// getter and setter to access the private variables declared in this method.
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFilelocation() {
		return filelocation;
	}

	public void setFilelocation(String filelocation) {
		this.filelocation = filelocation;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	// constructor to initialize the object of type FileIdentifier
	public FileIdentifier(int id, String filelocation, String filename) {
		super();
		this.id = id;
		this.filelocation = filelocation;
		this.filename = filename;
	}
}
