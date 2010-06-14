package br.compnatural;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import Jama.Matrix;
import br.compnatural.coordinate.BinaryCoordinate;
import br.compnatural.coordinate.Coordinate;
import br.compnatural.coordinate.RealCoordinate;

public class State implements Comparable<State>{
	
	private Double value ;
	private List<Coordinate> coordinate;
	private Boolean binary;
	public Matrix[] x;
	
	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
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

	@Override
	public int compareTo(State o) {
		return getValue().compareTo(o.getValue());
	}
	
	public static class InverseOrderState implements Comparator<State>{

		@Override
		public int compare(State o1, State o2) {
			return o2.compareTo(o1);
		}
		
	}
	
	public void setX(Matrix[] x){
		this.x = x;
	}
	
	public Matrix[] getX(){
		return x;
	}
}
