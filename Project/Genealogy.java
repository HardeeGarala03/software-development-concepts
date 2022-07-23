import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Genealogy {
	Connection connection;
	Statement statement;
	ResultSet resultset;
	PersonIdentity person;

	Map<Integer, ArrayList<Integer>> family = new HashMap<Integer, ArrayList<Integer>>();

	// connecting to the database
	public void databaseConnection() throws Exception /* ClassNotFound Exception */ {
		Class.forName("com.mysql.cj.jdbc.Driver");
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306?serverTimezone=UTC&useSSL=false", "root",
				"RAbh*1913");
		statement = connection.createStatement();
		statement.execute("use project");
	}

	// adds the name of the person to the person table
	public PersonIdentity addPerson(String name) throws Exception {
		databaseConnection();
		// since id is set to auto increment the person is associated with a unique id
		statement.execute("insert into person (name) values ('" + name + "');");
		// fetching the id to create a new PersonIdentity object
		resultset = statement.executeQuery("select id from person where name = '" + name + "';");
		int id = 0;
		// if there are multiple similar names, the most recently added name's id is
		// fetched since the while loop checks till last, the last name match id is
		// stored in id to add in the PersonIdentity object
		while (resultset.next()) {
			id = resultset.getInt("id");
		}

		person = new PersonIdentity(id, name);
		statement.close();
		connection.close();

		return person;
	}

	// this method records attribute of the person passed in parameter
	public Boolean recordAttributes(PersonIdentity person, Map<String, String> personAttr) throws Exception {
		boolean attAdded = true;
		// connected to database
		databaseConnection();
		// a list of available attributes is created to check the attribute asked by the
		// user to add in the database
		List<String> allKeyAttributes = new ArrayList<String>();
		allKeyAttributes.add("birthdate");
		allKeyAttributes.add("birthlocation");
		allKeyAttributes.add("deathdate");
		allKeyAttributes.add("deathlocation");
		allKeyAttributes.add("gender");
		allKeyAttributes.add("occupation");

		// this loop iterates over the key value provided by user to check and add key
		// and value of the attribute to database

		for (Map.Entry<String, String> mapElements : personAttr.entrySet()) {
			String keyAttr = mapElements.getKey();
			String valueAttr = mapElements.getValue();
			// for birthdate, birthlocation, deathdate, deathlocation, gender , the values
			// are updated in person table. However, there can be multiple occupations
			// associated with a person hence a new row is created in occupation table
			if (keyAttr.equalsIgnoreCase("birthdate") || keyAttr.equalsIgnoreCase("deathdate")
					|| keyAttr.equalsIgnoreCase("birthlocation") || keyAttr.equalsIgnoreCase("deathlocation")) {
				statement.execute(
						"update person set " + keyAttr + " = '" + valueAttr + "' where id = " + person.getId() + ";");

			} else if (keyAttr.equalsIgnoreCase("occupation")) {
				statement.execute("insert into occupation values (" + person.getId() + ",'" + valueAttr + "');");
			} else if (keyAttr.equalsIgnoreCase("gender")
					&& (valueAttr.equalsIgnoreCase("M") || valueAttr.equalsIgnoreCase("F"))) {
				statement.execute("update person set gender = '" + valueAttr + "' where id = " + person.getId() + ";");
			} else {
				attAdded = false;
				System.out.println("the attribute " + keyAttr
						+ " is invalid please add attribute from birthdate, birthlocation, deathdate, deathlocation, gender or occupation.");
			}
		}
		return attAdded;
	}

	// this method is used to record attributes of media
	Boolean recordMediaAttributes(FileIdentifier fileIdentifier, Map<String, String> attributes) throws Exception {
		boolean attAdded = true;
		databaseConnection();
		// a list of available attributes is created to check the attribute asked by the
		// user to add in the database
		List<String> allKeyAttributes = new ArrayList<String>();
		allKeyAttributes.add("year");
		allKeyAttributes.add("day");
		allKeyAttributes.add("month");
		allKeyAttributes.add("location");
		allKeyAttributes.add("province");
		allKeyAttributes.add("city");
		allKeyAttributes.add("country");
		allKeyAttributes.add("name");
		allKeyAttributes.add("date");
		// this loop iterates over the key value provided by user to check and add key
		// and value of the attribute to database
		for (Map.Entry<String, String> mapElements : attributes.entrySet()) {
			String keyAttr = mapElements.getKey();
			String valueAttr = mapElements.getValue();
			// if the attribute exists in the pre-defined list then the value is updated in
			// the filedetails table other wise the user is presented with a message stating
			// which attribute is incorrect
			if (allKeyAttributes.contains(keyAttr)) {
				statement.execute("update filedetails set " + keyAttr + " = '" + valueAttr + "' where id = "
						+ fileIdentifier.getId() + ";");
			} else {
				attAdded = false;
				System.out.println("the attribute " + keyAttr
						+ " is invalid please add attribute from year, day, month, location, province, city, country, name or date");
			}
		}
		return attAdded;
	}

	// took reference find entry time from
	// https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html
	// it returns the current time and date
	String[] findEntryTime() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		String d[] = dtf.format(now).split(" ");
		return d;
	}

	// to record the reference in the table reference_detail
	public boolean recordReference(PersonIdentity p1, String reference) throws Exception {
		// stored the current time and date as and when reference is added
		String d[] = findEntryTime();
		String referenceRecordedDate = d[0];
		String referenceRecordedTime = d[1];
		databaseConnection();
		int identity = p1.getId();
		// inserting all details of reference for the specified person into
		// reference_detail table
		statement.execute("insert into reference_detail (refer_id , reference, entryDate, entryTime) values ("
				+ identity + ",'" + reference + "','" + referenceRecordedDate + "','" + referenceRecordedTime + "' );");
		statement.close();
		connection.close();
		return true;
	}

	// to record the notes in table in table note_detail
	public boolean recordNote(PersonIdentity p1, String note) throws Exception {
		// stored the current time and date as and when note is added
		String d[] = findEntryTime();
		String referenceRecordedDate = d[0];
		String referenceRecordedTime = d[1];
		databaseConnection();
		int identity = p1.getId();
		// inserting all details of note for the specified person into note_detail table
		statement.execute("insert into note_detail (person_note_id , person_notes, entryDate, entryTime) values ("
				+ identity + ",'" + note + "','" + referenceRecordedDate + "','" + referenceRecordedTime + "');");
		statement.close();
		connection.close();
		return true;
	}

	// this method records relation of a child and parent and stores id of both in
	// parentchild table
	public Boolean recordChild(PersonIdentity parent, PersonIdentity child)
			throws Exception, SQLIntegrityConstraintViolationException {

		databaseConnection();
		int relationExist = 0;

		resultset = statement
				.executeQuery("select child_id from parentchild where parent_id = " + parent.getId() + ";");
		// checking whether same relation exist in database then returns false.
		while (resultset.next()) {
			if (resultset.getInt("child_id") == child.getId()) {
				relationExist = 1;
			}
		}
		// if same relation does not exist in the table it is stored
		if (relationExist == 0) {
			statement.executeUpdate("insert into parentchild values (" + parent.getId() + "," + child.getId() + ");");

		}
		return true;
	}

	// this method stores two person's id who are partner in partner table
	Boolean recordPartnering(PersonIdentity partner1, PersonIdentity partner2)
			throws Exception, SQLIntegrityConstraintViolationException {

		databaseConnection();
		int relationExist = 0;
		resultset = statement
				.executeQuery("select partner1id from partner where partner2id = " + partner2.getId() + ";");
		while (resultset.next()) {
			if (resultset.getInt("partner1id") == partner1.getId()) {
				relationExist = 1;
			}
		}
		resultset = statement
				.executeQuery("select partner1id from partner where partner2id = " + partner1.getId() + ";");
		while (resultset.next()) {
			if (resultset.getInt("partner1id") == partner2.getId()) {
				relationExist = 1;
			}
		}
		// if same relationship already exist in table then relationExist value is
		// changed to 1. If relationExist value is 0 then only the id's are added to the
		// table
		if (relationExist == 0) {
			statement.executeUpdate("insert into partner values (" + partner1.getId() + "," + partner2.getId() + ");");
		}
		return true;

	}

	// this method records the partners that are not a couple in the dissolution
	// table
	Boolean recordDissolution(PersonIdentity partner1, PersonIdentity partner2)
			throws Exception, SQLIntegrityConstraintViolationException {
		databaseConnection();

		int relationExist = 0;
		resultset = statement
				.executeQuery("select dissolutionPartner1Id from dissolution where dissolutionPartner2Id = "
						+ partner2.getId() + ";");
		while (resultset.next()) {
			if (resultset.getInt("dissolutionPartner1Id") == partner1.getId()) {
				relationExist = 1;
			}
		}
		resultset = statement
				.executeQuery("select dissolutionPartner1Id from dissolution where dissolutionPartner2Id = "
						+ partner1.getId() + ";");
		while (resultset.next()) {
			if (resultset.getInt("dissolutionPartner1Id") == partner2.getId()) {
				relationExist = 1;
			}
		}

		// if the relation already exist then the data is not added in the table
		// otherwise it is added.
		if (relationExist == 0) {
			statement.executeUpdate(
					"insert into dissolution values (" + partner1.getId() + "," + partner2.getId() + ");");
		}
		return true;
	}

	// this method stores details of file in filedetails
	FileIdentifier addMediaFile(String fileLocation) throws Exception {
		// if fileLocation is empty or null then null is returned.
		if (fileLocation.isEmpty() || fileLocation.equalsIgnoreCase(null)) {
			return null;
		}
		databaseConnection();
		// getting file name from location
		String[] fileNames = fileLocation.split("\\\\\\\\");
		// fileLocation.replace("\\", "\\\\");
		String fileName = fileNames[fileNames.length - 1];
		// iserting filelocation and file name to table filedetails
		statement.execute(
				"insert into filedetails (filelocation, filename) values ('" + fileLocation + "','" + fileName + "');");
		resultset = statement
				.executeQuery("select id, filelocation from filedetails where filelocation = '" + fileLocation + "';");
		int id = 0;
		String fileLoc = "";
		// since filelocation is unique we will get only one result
		if (resultset.next()) {
			id = resultset.getInt("id");
			fileLoc = resultset.getString("filelocation");
		}
		// creating the object by obtaining data from the database;
		FileIdentifier file = new FileIdentifier(id, fileLoc, fileName);
		statement.close();
		connection.close();
		return file;
	}

	// adding id of the file with the people associated with that file
	Boolean peopleInMedia(FileIdentifier fileIdentifier, List<PersonIdentity> people) throws Exception {
		databaseConnection();
		// this loop is repeated for all people present in the list
		for (int i = 0; i < people.size(); i++) {
			int relationExist = 0;
			// first checked whether the same data exist in the table
			resultset = statement
					.executeQuery("select person_id from peopleinfile where file_id = " + fileIdentifier.getId() + ";");
			while (resultset.next()) {
				if (resultset.getInt("person_id") == people.get(i).getId()) {
					relationExist = 1;
					System.out.println("Person with id " + people.get(i).getId() + " is already attached to file");
				}
			}
			// if same does not exist in table then the data is inserted and returned true
			if (relationExist == 0) {
				statement.executeUpdate("insert into peopleinfile values (" + people.get(i).getId() + ","
						+ fileIdentifier.getId() + ");");
			}
		}

		return true;
	}

	// this method records tag for an individual
	Boolean tagMedia(FileIdentifier fileIdentifier, String tag) throws Exception {
		databaseConnection();
		// if the same tag is added for the person relation exist
		int relationExist = 0;
		resultset = statement.executeQuery("select tag from mediatags where file_id = " + fileIdentifier.getId() + ";");
		while (resultset.next()) {
			if (resultset.getString("tag") == tag) {
				relationExist = 1;
			}
		}
		// if no such tag present in table then it is added.
		if (relationExist == 0) {
			statement.executeUpdate("insert into mediatags values (" + fileIdentifier.getId() + ",'" + tag + "');");

		}

		return true;
	}

	// this method returns object of type PersonIdentity that has the name passed as
	// parameter
	PersonIdentity findPerson(String name) throws Exception {
		// if name is null or empty
		if (name.isEmpty() || name.equalsIgnoreCase(null)) {
			return null;
		}
		databaseConnection();
		int id = 0;
		resultset = statement.executeQuery("select id from person where name = '" + name + "';");
		while (resultset.next()) {
			id = resultset.getInt("id");
		}
		person = new PersonIdentity(id, name);
		statement.close();
		connection.close();
		// if person not found returns null
		if (id == 0) {
			return null;
		}
		return person;
	}

	// this method takes the name of file and return the object of type
	// FileIdentifier
	FileIdentifier findMediaFile(String name) throws Exception {
		// if name of file is empty or null then returns null
		if (name.isEmpty() || name.equalsIgnoreCase(null)) {
			return null;
		}
		databaseConnection();
		int id = 0;
		FileIdentifier file1;
		String fileName = "";
		String fileLoc = "";
		resultset = statement
				.executeQuery("select id, filename, filelocation from filedetails where filename = '" + name + "';");
		while (resultset.next()) {
			id = resultset.getInt("id");
			fileName = resultset.getString("filename");
			fileLoc = resultset.getString("filelocation");
		}
		file1 = new FileIdentifier(id, fileLoc, fileName);
		statement.close();
		connection.close();
		// if no such file found returns null
		if (id == 0) {
			return null;
		}
		return file1;
	}

	// by this method the name of the person is returned by the object that is
	// passed.
	String findName(PersonIdentity person) throws Exception {
		int id = person.getId();
		String name = null;
		databaseConnection();
		resultset = statement.executeQuery("select name from person where id = " + id + ";");
		while (resultset.next()) {
			name = resultset.getString("name");
		}
		statement.close();
		connection.close();
		return name;
	}

	// filelocation is returned for this method for the object of FileIdentifier
	String findMediaFile(FileIdentifier fileId) throws Exception {
		int id = fileId.getId();
		String location = null;
		databaseConnection();
		resultset = statement.executeQuery("select filelocation from filedetails where id = " + id + ";");
		while (resultset.next()) {
			location = resultset.getString("filelocation");
		}
		statement.close();
		connection.close();
		return location;
	}

	// this method is used to create the graph using adjacency list by taking data
	// from the parentchild table.
	public boolean createFamilyTree() throws Exception {
		databaseConnection();
		createNodes();
		resultset = statement.executeQuery("select * from parentchild;");
		while (resultset.next()) {
			int childExist = 0;
			// for each person their child is stored in arraylist
			List<Integer> childrenList = new ArrayList<>();
			childrenList = family.get(resultset.getInt("parent_id"));
			// below loop checks if same child exist in the arraylist
			for (int i = 0; i < childrenList.size(); i++) {
				if (childrenList.get(i) == resultset.getInt("child_id")) {
					childExist = 1;
				}
			}
			// if the child already exist the child id is not added to the arraylist of
			// value.
			if (childExist == 0) {
				childrenList.add(resultset.getInt("child_id"));
			}
		}
		return true;
	}

	// nodes for graph are created for all the people present in person table
	public void createNodes() throws SQLException {
		resultset = statement.executeQuery("select id from person;");
		while (resultset.next()) {
			if (family.isEmpty()) {
				family.put(resultset.getInt("id"), new ArrayList<>());
			} else if (family.size() == 0 || !family.containsKey(resultset.getInt("id"))) {
				family.put(resultset.getInt("id"), new ArrayList<>());
			}
		}
	}

	// this method finds the descendants of a person till given count of generations
	public Set<PersonIdentity> descendents(PersonIdentity person, Integer generations) throws Exception {
		Set<PersonIdentity> personDescendents = new HashSet<>();
		createFamilyTree();
		int count = 0;
		ArrayList<Integer> firstDescendents = new ArrayList<>();
		// the person is searched in key
		if (family.containsKey(person.getId())) {
			// person found so its value arraylist are all descendants of the person for
			// first generation
			firstDescendents = family.get(person.getId());
			// to obtain other descendants below method is called recursively
			personDescendents = findDescendents(personDescendents, firstDescendents, count, generations);
		}
		// all person found are returned in form of set
		return personDescendents;
	}

	Set<PersonIdentity> findDescendents(Set<PersonIdentity> personDescendents, ArrayList<Integer> firstDescendents,
			int count, int generations) throws Exception {
		ArrayList<Integer> firstDescendentsnew = null;
		// the recursive call stops when either all the children visited or the count is
		// equal to generation
		if (count == generations) {
			return personDescendents;
		} else {
			// count is incremented for each level of descendants
			count++;
			for (int i = 0; i < firstDescendents.size(); i++) {
				// the descendants found are stored in the set of type PersonIdentity
				personDescendents.add(findObject(firstDescendents.get(i)));
				firstDescendentsnew = family.get(firstDescendents.get(i));
				// if a node does not have any other children nodes then it will be null so
				// below condition checks whether the person node is a leaf node
				if (firstDescendentsnew != null && !firstDescendentsnew.isEmpty()) {
					findDescendents(personDescendents, firstDescendentsnew, count, generations);
				}
			}
		}

		return personDescendents;
	}

	// this method finds the ancestors of a person till given count of generations
	public Set<PersonIdentity> ancestores(PersonIdentity person, Integer generations) throws Exception {
		Set<PersonIdentity> personAncestors = new HashSet<>();
		createFamilyTree();
		ArrayList<Integer> firstAncestors = null;
		int parent;
		int count = 1;
		// the person is searched in value
		for (Map.Entry<Integer, ArrayList<Integer>> mapElements : family.entrySet()) {
			firstAncestors = mapElements.getValue();
			// all the values are checked as child can have more than one parent so can be
			// present in value of other key too
			for (int i = 0; i < firstAncestors.size(); i++) {
				if (firstAncestors.get(i) == person.getId()) {
					// when a person is found its key is taken the parent of the person.
					parent = mapElements.getKey();
					// the found parent is added to the set
					personAncestors.add(findObject(parent));
					// recursive call is made to findAncestors to find all the parents
					personAncestors = findAncestors(personAncestors, parent, count, generations);
				}
			}
		}
		return personAncestors;
	}

	Set<PersonIdentity> findAncestors(Set<PersonIdentity> personAncestors, int child, int count, int generations)
			throws Exception {
		ArrayList<Integer> firstAncestorsnew = null;
		int parentnew;
		// the recursive call stops when the count is equal to the generations till
		// which we need to find ancestors for
		if (count == generations) {
			return personAncestors;
		} else {
			// count is incremented for each level
			count++;
			for (Map.Entry<Integer, ArrayList<Integer>> mapElements : family.entrySet()) {
				firstAncestorsnew = mapElements.getValue();
				for (int i = 0; i < firstAncestorsnew.size(); i++) {
					if (firstAncestorsnew.get(i) == child) {
						parentnew = mapElements.getKey();
						// recursive call is made to findAncestors the new parent found
						personAncestors.add(findObject(parentnew));
						findAncestors(personAncestors, parentnew, count, generations);
					}
				}
			}
		}
		return personAncestors;
	}

	// using this method in main.java to pass object in parameters of different
	// methods
	PersonIdentity findObject(int id) throws Exception {
		databaseConnection();
		String name = "";
		resultset = statement.executeQuery("select name from person where id = " + id + ";");
		while (resultset.next()) {
			name = resultset.getString("name");
		}
		person = new PersonIdentity(id, name);
		statement.close();
		connection.close();
		return person;
	}

	// this method finds degree of cousinship and degree of removal between 2 person
	public BiologicalRelation findRelation(PersonIdentity person1, PersonIdentity person2) throws Exception {

		// person1arr stores all of the ancestors of person1 including itself in
		// addition to count of level from person1 to its ancestors
		PersonIdentity[] person1arr = relationAncestor(person1, Integer.MAX_VALUE).toArray(new PersonIdentity[0]);
		// person2arr stores all of the ancestors of person2 including itself in
		// addition to count of level from person2 to its ancestors
		PersonIdentity[] person2arr = relationAncestor(person2, Integer.MAX_VALUE).toArray(new PersonIdentity[0]);
		// initializing minimum value
		int min = Integer.MAX_VALUE;
		PersonIdentity commonAnc = null;
		PersonIdentity temp = null;
		for (int i = 0; i < person1arr.length; i++) {
			for (int j = 0; j < person2arr.length; j++) {
				// for both the array check the common ancestors
				if (person1arr[i].getId() == person2arr[j].getId()) {
					// for these common ancestors we check for the lowest stored count. This count
					// gives the level of a nearest common ancestors from a person
					if (person1arr[i].getCount() < person2arr[j].getCount()) {
						temp = person1arr[i];
					}
					if (person1arr[i].getCount() >= person2arr[j].getCount()) {
						temp = person2arr[j];
					}
					System.out.println("temp " + temp.getId() + " " + temp.getCount());
					// we store the object or person that is minimum in commonAnc
					if (temp.getCount() < min) {
						min = temp.getCount();
						commonAnc = temp;
					}
				}
			}
		}
		BiologicalRelation br;
		// to find the degree of removal below is used. if there is no common ancestor
		// then there cannot be a degree of removal too hence we return null
		if (commonAnc == null) {
			br = null;
		} else {
			// if common ancestor exist then we check for the level from the person to its
			// highest common node and subtract both the values this is degree of removal.
			int maxRemoval1 = Integer.MIN_VALUE;
			int maxRemoval2 = Integer.MIN_VALUE;
			for (int i = 0; i < person1arr.length; i++) {
				if (maxRemoval1 < person1arr[i].getCount()) {
					maxRemoval1 = person1arr[i].getCount();
				}
			}
			for (int i = 0; i < person2arr.length; i++) {
				if (maxRemoval2 < person2arr[i].getCount()) {
					maxRemoval2 = person2arr[i].getCount();
				}
			}
			// the count of commonAnc obtained minus 1 is degree of cousinship of a person
			br = new BiologicalRelation(commonAnc.getCount() - 1, Math.abs(maxRemoval1 - maxRemoval2));
		}
		// the object of type BiologicalRalation is returned
		return br;
	}

	// the method relationAncestor and relationfindAncestor is similar to ancestor
	// and findAncestor respectively, only difference is count is stored in
	// objects and the person itself is included as part of ancestor. this method is
	// used to obtain the array of ancestors when we find the relation between 2
	// people
	public Set<PersonIdentity> relationAncestor(PersonIdentity person, Integer generations) throws Exception {
		Set<PersonIdentity> personAncestors = new HashSet<>();
		createFamilyTree();
		ArrayList<Integer> firstAncestors = null;
		int parent;
		int count = 0;
		int selfId = person.getId();
		PersonIdentity confirmSelf = findObject(selfId);
		confirmSelf.setCount(count);
		// adding the person themselves to the ancestor list
		personAncestors.add(confirmSelf);
		count++;
		for (Map.Entry<Integer, ArrayList<Integer>> mapElements : family.entrySet()) {
			firstAncestors = mapElements.getValue();
			for (int i = 0; i < firstAncestors.size(); i++) {
				if (firstAncestors.get(i) == person.getId()) {
					parent = mapElements.getKey();
					PersonIdentity confirmAncestor = findObject(parent);
					// setting count of level for the person object
					confirmAncestor.setCount(count);
					personAncestors.add(confirmAncestor);

					personAncestors = relationfindAncestor(personAncestors, parent, count, generations);
				}
			}
		}
		return personAncestors;
	}

	Set<PersonIdentity> relationfindAncestor(Set<PersonIdentity> personAncestors, int child, int count, int generations)
			throws Exception {
		ArrayList<Integer> firstAncestorsnew = null;
		int parentnew;
		if (count == generations) {
			return personAncestors;
		} else {
			count++;
			for (Map.Entry<Integer, ArrayList<Integer>> mapElements : family.entrySet()) {
				firstAncestorsnew = mapElements.getValue();
				for (int i = 0; i < firstAncestorsnew.size(); i++) {
					if (firstAncestorsnew.get(i) == child) {
						parentnew = mapElements.getKey();
						PersonIdentity confirmAncestor = findObject(parentnew);
						confirmAncestor.setCount(count);
						personAncestors.add(confirmAncestor);
						// calling method recursively to find all the ancestors and to set the count of
						// the level.
						relationfindAncestor(personAncestors, parentnew, count, generations);
					}
				}
			}
		}

		return personAncestors;
	}

	// this method is used in main.java for passing FileIdentifier object as
	// parameters to the methods for testing
	FileIdentifier findMediaObject(int id) throws Exception {
		databaseConnection();
		FileIdentifier file1;
		String fileName = "";
		String fileLoc = "";
		resultset = statement.executeQuery("select filename, filelocation from filedetails where id = " + id + ";");
		while (resultset.next()) {
			fileName = resultset.getString("filename");
			fileLoc = resultset.getString("filelocation");
		}
		file1 = new FileIdentifier(id, fileLoc, fileName);
		statement.close();
		connection.close();
		return file1;
	}

	// this method return all the notes and references associated to the person in
	// the order they were added to the database table.
	List<String> notesAndReferences(PersonIdentity person) throws Exception {
		List<String> notesandref = new ArrayList<>();
		databaseConnection();
		resultset = statement.executeQuery(
				"select person_note_id, person_notes, entryDate, entryTime from note_detail where person_note_id = "
						+ person.getId()
						+ " UNION select refer_id, reference, entryDate, entryTime from reference_detail where refer_id = "
						+ person.getId() + " order by entryDate, entryTime;");
		while (resultset.next()) {
			notesandref.add(resultset.getString("person_notes"));
		}
		statement.close();
		connection.close();
		return notesandref;
	}

	// this method is used to find the media that has given tag associated with it
	// and the media is of the period determined by the start and end date provided.
	Set<FileIdentifier> findMediaByTag(String tag, String startDate, String endDate) throws Exception {
		Set<FileIdentifier> taggedfiles = new HashSet<>();
		databaseConnection();
		// if both start and end date are null then all media associated with given tag
		// is added.
		if ((startDate == null) && (endDate == null)) {
			resultset = statement.executeQuery(
					"select file_id, filelocation, filename, mediaDate from mediatags join filedetails where tag = '"
							+ tag + "' and filedetails.id = mediatags.file_id;");
			while (resultset.next()) {
				FileIdentifier fileNew = new FileIdentifier(resultset.getInt("file_id"),
						resultset.getString("filelocation"), resultset.getString("filename"));
				taggedfiles.add(fileNew);
			}
			statement.close();
		}
		// if startDate is null then all media having date before the endDate associated
		// with given tag is added.
		else if (startDate == null) {
			resultset = statement.executeQuery(
					"select file_id, filelocation, filename, mediaDate from mediatags join filedetails where tag = '"
							+ tag + "' and filedetails.id = mediatags.file_id and mediaDate < '" + endDate + "';");
			while (resultset.next()) {
				FileIdentifier fileNew = new FileIdentifier(resultset.getInt("file_id"),
						resultset.getString("filelocation"), resultset.getString("filename"));
				taggedfiles.add(fileNew);
			}
			statement.close();
		}
		// if endDate is null then all media having date after the startDate associated
		// with given tag is added.
		else if (endDate == null) {
			resultset = statement.executeQuery(
					"select file_id, filelocation, filename, mediaDate from mediatags join filedetails where tag = '"
							+ tag + "' and filedetails.id = mediatags.file_id and mediaDate > '" + startDate + "';");
			while (resultset.next()) {
				FileIdentifier fileNew = new FileIdentifier(resultset.getInt("file_id"),
						resultset.getString("filelocation"), resultset.getString("filename"));
				taggedfiles.add(fileNew);
			}
			statement.close();
		}
		// if both dates are given then all media having date between the startDate and
		// endDate associated with given tag is added.
		else {
			resultset = statement.executeQuery(
					"select file_id, filelocation, filename, mediaDate from mediatags join filedetails where tag = '"
							+ tag + "' and filedetails.id = mediatags.file_id and mediaDate between '" + startDate
							+ "' and '" + endDate + "';");

			while (resultset.next()) {
				FileIdentifier fileNew = new FileIdentifier(resultset.getInt("file_id"),
						resultset.getString("filelocation"), resultset.getString("filename"));
				taggedfiles.add(fileNew);
			}
			statement.close();
		}

		return taggedfiles;
	}

	// this method is used to find the media by location associated with it and the
	// media is of the period determined by the start and end date provided.
	Set<FileIdentifier> findMediaByLocation(String location, String startDate, String endDate) throws Exception {
		Set<FileIdentifier> filesByLocation = new HashSet<>();
		databaseConnection();
		// if both start and end date are null then all media associated with given
		// location is added.
		if ((startDate == null) && (endDate == null)) {
			resultset = statement.executeQuery(
					"select id, filelocation, filename, mediaDate from filedetails where location = " + location + ";");
			while (resultset.next()) {
				FileIdentifier fileNew = new FileIdentifier(resultset.getInt("file_id"),
						resultset.getString("filelocation"), resultset.getString("filename"));
				filesByLocation.add(fileNew);
			}
			statement.close();
		}
		// if startDate is null then all media having date before the endDate associated
		// with given location is added.
		else if (startDate == null) {
			resultset = statement
					.executeQuery("select id, filelocation, filename, mediaDate from filedetails where location = "
							+ location + " and mediaDate < '" + endDate + "';");
			while (resultset.next()) {
				FileIdentifier fileNew = new FileIdentifier(resultset.getInt("id"), resultset.getString("filelocation"),
						resultset.getString("filename"));
				filesByLocation.add(fileNew);
			}
			statement.close();
		}
		// if endDate is null then all media having date after the startDate associated
		// with given location is added.
		else if (endDate == null) {
			resultset = statement
					.executeQuery("select id, filelocation, filename, mediaDate from filedetails where location = "
							+ location + " and mediaDate > '" + startDate + "';");
			while (resultset.next()) {
				FileIdentifier fileNew = new FileIdentifier(resultset.getInt("id"), resultset.getString("filelocation"),
						resultset.getString("filename"));
				filesByLocation.add(fileNew);
			}
			statement.close();
		}
		// if both dates are given then all media having date between the startDate and
		// endDate associated with given location is added.
		else {
			resultset = statement
					.executeQuery("select id, filelocation, filename, mediaDate from filedetails where location = "
							+ location + " and mediaDate between '" + startDate + "' and '" + endDate + "';");

			while (resultset.next()) {
				FileIdentifier fileNew = new FileIdentifier(resultset.getInt("id"), resultset.getString("filelocation"),
						resultset.getString("filename"));
				filesByLocation.add(fileNew);
			}
			statement.close();
		}

		return filesByLocation;
	}

	// this method returns list of FileIdentifier for a set of people such that the
	// media files are dated between start and endDate.
	List<FileIdentifier> findIndividualsMedia(Set<PersonIdentity> people, String startDate, String endDate)
			throws Exception {
		List<FileIdentifier> fileList = new ArrayList<>();
		int count = 0;
		// person_id for all people present in set are added to the query string to get
		// all files that has any of the people associated with it then we filter it on
		// the basis of start date and enddate similar to findMediaByLocation method.
		String query = "select person_id, file_id, id, filename, filelocation, mediaDate from peopleinfile join filedetails where peopleinfile.file_id = filedetails.id and (";
		for (PersonIdentity p : people) {
			int id = p.getId();
			if (count == 0) {
				query = query + " person_id = '" + id + "'";
				count++;
			} else {
				query = query + " or person_id = '" + id + "'";
			}

		}

		if ((startDate == null) && (endDate == null)) {
			query = query + ") group by file_id order by mediaDate desc, filename;";
		} else if (startDate == null) {
			query = query + ") and (mediaDate < '" + endDate
					+ "' or mediaDate is null) group by file_id order by mediaDate desc, filename;";
		} else if (endDate == null) {
			query = query + ") and (mediaDate > '" + startDate
					+ "' or mediaDate is null) group by file_id order by mediaDate desc, filename;";
		} else {
			query = query + ") and (mediaDate between '" + startDate + "' and '" + endDate
					+ "' or mediaDate is null) group by file_id order by mediaDate desc, filename;";
		}

		databaseConnection();
		resultset = statement.executeQuery(query);
		while (resultset.next()) {
			// the fileObjects are added in the list in chronological order of the mediaDate
			// and null values are added at the end of the list
			FileIdentifier fileNew = new FileIdentifier(resultset.getInt("id"), resultset.getString("filelocation"),
					resultset.getString("filename"));
			if (resultset.getString("mediaDate") == null) {
				fileList.add(fileNew);
			} else {
				fileList.add(0, fileNew);
			}

		}
		statement.close();

		return fileList;
	}

	// this method returns the file associated with immediate descendant of a person
	List<FileIdentifier> findBiologicalFamilyMedia(PersonIdentity person) throws Exception {

		// below method find all the immediate descendants
		Set<PersonIdentity> bioFamily = descendents(person, 1);
		// these descendants are then passed in findIndividualsMedia method. They will
		// return the list of fileIdentifier which can be returned
		List<FileIdentifier> bioFamilyMedia = findIndividualsMedia(bioFamily, null, null);

		return bioFamilyMedia;
	}
}
