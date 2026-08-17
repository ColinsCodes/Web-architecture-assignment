package mancala.api.models;

public class PitDTO {

	public int index;
	public int nrOfStones;

	public PitDTO(int index, int nrOfStones) {
		this.index = index;
		this.nrOfStones = nrOfStones;
	}

	public int getIndex() {
		return index;
	}

	public int getNrOfStones() {
		return nrOfStones;
	}
}