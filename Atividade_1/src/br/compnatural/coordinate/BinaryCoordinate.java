package br.compnatural.coordinate;

public class BinaryCoordinate extends Coordinate {

	public int length;
	private boolean value[];

	public boolean[] getValue() {
		return value;
	}

	public void setValue(boolean[] value) {
		this.value = value;
	}

	public BinaryCoordinate(String name, int length) {
		super.name = name;
		this.length = length;
		value = new boolean[length];
	}

	public String toString() {
		String string = "name=(" + super.name + ") value=(";
		for (int i = 0; i < value.length; i++) {
			string += value[i] ? "1" : "0";

		}
		return string + ")";
	}

}
