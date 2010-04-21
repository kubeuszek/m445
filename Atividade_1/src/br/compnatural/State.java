package br.compnatural;

import java.util.ArrayList;
import java.util.List;

import br.compnatural.coordinate.BinaryCoordinate;
import br.compnatural.coordinate.Coordinate;
import br.compnatural.coordinate.RealCoordinate;

public class State {
	
	private double value ;
	private List<Coordinate> coordinate;
	private Boolean binary;
	
	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}
	
	public State (State state, Boolean binary){
		this.binary = binary;
		setCoordinate(new ArrayList<Coordinate>(state.getCoordinate().size()));
		Coordinate lCoordinate; 
		for (Coordinate coordinate : state.getCoordinate()) {
			if(!binary){
				lCoordinate = new RealCoordinate(coordinate.name, ((RealCoordinate)coordinate).min, ((RealCoordinate)coordinate).max);
				((RealCoordinate)lCoordinate).setValue(((RealCoordinate)coordinate).getValue());
			}else{
				lCoordinate = new BinaryCoordinate(coordinate.name, ((BinaryCoordinate) coordinate).length);
				((BinaryCoordinate)lCoordinate).setValue(((BinaryCoordinate)coordinate).getValue());
			}
			getCoordinate().add(lCoordinate);
		}
	}

	public State (List<Coordinate> coordinate, Boolean binary){
		this.binary = binary;
		setCoordinate(coordinate);
	}

	protected void setCoordinate(List<Coordinate> coordinate) {
		this.coordinate = coordinate;
	}

	public List<Coordinate> getCoordinate() {
		return coordinate;
	}
	
	public static State getState(){
		return new State(new ArrayList<Coordinate>(),Boolean.FALSE);
	}

	@Override
	public String toString(){
		String retorno = "";
		for (Coordinate coordinate : getCoordinate()) {
			Object lValue = binary ? ((BinaryCoordinate)coordinate).getValue() : ((RealCoordinate)coordinate).getValue();
			retorno += coordinate.name + "="+ lValue+", ";
		}
		
		return retorno;
	}
}
