package br.compnatural;

import java.util.Arrays;

public class State {
	private double[] coordinates ;
	private double value ;
	
	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	protected State (double ... coordinates){
		this.coordinates = new double[coordinates.length];
		int i = 0;
		for (double d : coordinates) {
			coordinates[i++] = d;
		}
	}
	
	public double[] getCoordinates(){
		return coordinates;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(coordinates);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		State other = (State) obj;
		
		if(other.coordinates.length != coordinates.length){
			return false;
		}
		
		for(int i = 0; i < other.coordinates.length; ++i){
			if((int)(other.coordinates[i]*100000) != (int)(coordinates[i]*100000)){
				return false;
			}
		}
			
		return true;
	}
}
