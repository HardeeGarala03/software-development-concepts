import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Main {

	public static void main(String[] args) {
		try {
			Genealogy genealogy = new Genealogy();
			Map<String, String> personAttr = new HashMap<>();
			Map<String, String> mediaAttr = new HashMap<>();
			List<PersonIdentity> people = new ArrayList<>();
			Scanner sc;
			PersonIdentity p1 = null;
			char option = 'Z';
			// the loop continues till user enters to exit
			while (option != 'E') {
				System.out.println(
						"Please enter 'A' to add person,\n enter 'B' to pass reference,\n enter 'C' to record child,\n enter 'D' to create family tree,\n 'F' to find ancestors,\n 'G' to find descendants,\n 'H' to find relation,\n 'I' to add attributes ,\n 'J' to record partner,\n 'K' to record dissolution,\n 'L' to add media file,\n 'M' to record media attributes, \n 'N' to add people in file, \n 'O' to add tag in file,\n P for findPerson, \n Q for finding media file, \n R for findName, \n S findMediaFile, \n T findMEdiaByTag, \n U find individuals media, \n V find notes and references \n and 'E' to exit");
				sc = new Scanner(System.in);
				option = sc.next().charAt(0);
				// on the basis of entered option from the user the desired case is selected.
				switch (option) {
				// if user want to add dictionary this case is used to get the file path from
				// user and call the getDictionary method
				case 'A':
					System.out.println("Enter name");
					sc = new Scanner(System.in);
					String name = sc.nextLine();
					p1 = genealogy.addPerson(name);
					break;
				case 'B':
					int ref_id = 11;
					genealogy.recordReference(genealogy.findObject(ref_id), "Cambodia");
				case 'C':
					System.out.println("Enter parent id and enter child id");
					int parent_id = sc.nextInt();
					int child_id = sc.nextInt();
					genealogy.recordChild(genealogy.findObject(parent_id), genealogy.findObject(child_id));
					break;
				case 'D':
					genealogy.createFamilyTree();
					break;
				case 'F':
					System.out.println("Please enter id of the person");
					int id = sc.nextInt();
					System.out.println("Please enter number of generation");
					int generationA = sc.nextInt();
					genealogy.ancestores(genealogy.findObject(id), generationA);
					break;
				case 'G':
					System.out.println("Please enter id of the person");
					int idD = sc.nextInt();
					System.out.println("Please enter number of generation");
					int generationD = sc.nextInt();
					genealogy.descendents(genealogy.findObject(idD), generationD);
					break;
				case 'H':
					System.out.println("Please enter id of the person 1");
					int id1 = sc.nextInt();
					System.out.println("Please enter id of the person 2");
					int id2 = sc.nextInt();
					genealogy.findRelation(genealogy.findObject(id1), genealogy.findObject(id2));
					break;
				case 'I':
					int attPerson = 15;
					personAttr.put("birthdate", "1968-10-03");
					personAttr.put("death", "2020-02-01");
					personAttr.put("birthlocation", "rajasthan");
					personAttr.put("deathlocation", "rajasthan");
					personAttr.put("gender", "M");
					personAttr.put("occupation", "technical assistant");
					genealogy.recordAttributes(genealogy.findObject(attPerson), personAttr);
					break;

				case 'J':
					System.out.println("Please enter id of the partner 1");
					int partnerid1 = sc.nextInt();
					System.out.println("Please enter id of the partner 2");
					int partnerid2 = sc.nextInt();
					genealogy.recordPartnering(genealogy.findObject(partnerid1), genealogy.findObject(partnerid2));
					break;
				case 'K':
					System.out.println("Please enter id of the partner 1");
					int disPartnerid1 = sc.nextInt();
					System.out.println("Please enter id of the partner 2");
					int disPartnerid2 = sc.nextInt();
					genealogy.recordDissolution(genealogy.findObject(disPartnerid1),
							genealogy.findObject(disPartnerid2));
					break;
				case 'L':
					String fileLoc = "D:\\\\used\\\\charm.jpg";
					genealogy.addMediaFile(fileLoc);
					break;
				case 'M':
					System.out.println("Please enter id of the file");
					int fileId = sc.nextInt();
					mediaAttr.put("location", "bali");
					mediaAttr.put("year", "2002");
					mediaAttr.put("day", "9");
					mediaAttr.put("month", "7");
					mediaAttr.put("province", "Lesser");
					mediaAttr.put("city", "Sunda");
					mediaAttr.put("country", "Indonesia");
					mediaAttr.put("count", "Indonesia");
					genealogy.recordMediaAttributes(genealogy.findMediaObject(fileId), mediaAttr);
					break;
				case 'N':
					System.out.println("Please enter id of the file");
					int fileIdPeople = sc.nextInt();
					people.add(genealogy.findObject(9));
					genealogy.peopleInMedia(genealogy.findMediaObject(fileIdPeople), people);
					break;
				case 'O':
					System.out.println("Please enter id of the file");
					int fileIdTag = sc.nextInt();
					String tag = "travel";
					genealogy.tagMedia(genealogy.findMediaObject(fileIdTag), tag);
					break;
				case 'P':
					String person_name = "anu";
					genealogy.findPerson(person_name);
					break;
				case 'Q':
					String file_name = "flower.png";
					genealogy.findMediaFile(file_name);
					break;
				case 'R':
					genealogy.findName(null);
					break;
				case 'S':
					genealogy.findMediaFile(genealogy.findMediaObject(29));
					break;
				case 'T':
					genealogy.findMediaByTag("google photos", null, "2020-11-19");
				case 'U':
					Set<PersonIdentity> peopleMedia = new HashSet<>();
					peopleMedia.add(genealogy.findObject(26));
					peopleMedia.add(genealogy.findObject(19));
					peopleMedia.add(genealogy.findObject(8));
					peopleMedia.add(genealogy.findObject(12));
					peopleMedia.add(genealogy.findObject(30));
					genealogy.findIndividualsMedia(peopleMedia, "1990-12-12", null);
					break;
				case 'V':
					genealogy.notesAndReferences(genealogy.findObject(2));
					break;
				case 'W':
					genealogy.recordNote(genealogy.findObject(3), "Colosseum");
					break;
				case 'E':
					break;
				// if invalid input is entered by default below case is called
				default:
					System.out.println("Please select valid option!");
					break;
				}
			}
		} catch (SQLIntegrityConstraintViolationException sqlintegrity) {
			System.out.println("Integrity violation");
		} catch (Exception e) {
			e.getMessage();
			e.printStackTrace();
			System.out.println("Invalid input");
		}
	}

}
