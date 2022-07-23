public class BiologicalRelation {
	// to store the cousinship between 2 person
	private int cousinShipDegree;
	// to store degree of removal between 2 person
	private int removalDegree;

	public int getCousinShipDegree() {
		return cousinShipDegree;
	}

	public void setCousinShipDegree(int cousinShipDegree) {
		this.cousinShipDegree = cousinShipDegree;
	}

	public int getRemovalDegree() {
		return removalDegree;
	}

	public void setRemovalDegree(int removalDegree) {
		this.removalDegree = removalDegree;
	}

	// constructor to initialize the object of type BiologicalRelation
	public BiologicalRelation(int cousinShipDegree, int removalDegree) {
		this.cousinShipDegree = cousinShipDegree;
		this.removalDegree = removalDegree;
	}

}
