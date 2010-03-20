package br.compnatural;

import java.util.Arrays;
import java.util.List;

import br.compnatural.Specification.Coordinate;

public class State {
	private double[] coordinates ;
	private double value ;
	private List<Coordinate> coordinate;
	
	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	protected State (List<Coordinate> coordinate, double ... coordinates){
		this.coordinates = new double[coordinates.length];
		int i = 0;
		for (double d : coordinates) {
			coordinates[i++] = d;
		}
		setCoordinate(coordinate);
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

	private void setCoordinate(List<Coordinate> coordinate) {
		this.coordinate = coordinate;
	}

	public List<Coordinate> getCoordinate() {
		return coordinate;
	}
}
