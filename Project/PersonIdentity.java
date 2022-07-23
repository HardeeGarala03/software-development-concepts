
public class PersonIdentity {
	// variable id of a person of type integer
	private int id;
	// private variable name of a person
	private String name;
	// private variable count of a person in relation to other person
	private int count;

	// getter and setter to access all these private variables
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	// constructor to initialize object of type PersonIdentity
	public PersonIdentity(int id, String data) {
		this.id = id;
		this.name = data;
	}

}
