package br.compnatural;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import br.compnatural.Specification.Coordinate;

public class State {
	
	private double value ;
	private List<Coordinate> coordinate;
	
	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	protected State (List<Coordinate> coordinate){		
		setCoordinate(coordinate);
	}

	private void setCoordinate(List<Coordinate> coordinate) {
		this.coordinate = coordinate;
	}

	public List<Coordinate> getCoordinate() {
		return coordinate;
	}
}
