package br.compnatural;

import java.util.ArrayList;
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
	
	protected State (State state){
		setCoordinate(new ArrayList<Coordinate>(state.getCoordinate().size()));
		for (Coordinate coordinate : state.getCoordinate()) {
			Coordinate lCoordinate = new Coordinate(coordinate.name, coordinate.min, coordinate.max);
			lCoordinate.setValue(coordinate.getValue());
			getCoordinate().add(lCoordinate);
		}
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
	
	public static State getState(){
		return new State(new ArrayList<Coordinate>());
	}
	
	@Override
	public String toString(){
		String retorno = "";
		for (Coordinate coordinate : getCoordinate()) {
			retorno += coordinate.name + "="+ coordinate.getValue()+", ";
		}
		
		return retorno;
	}
}
