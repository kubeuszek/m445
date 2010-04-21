package br.compnatural;

import java.util.ArrayList;
import java.util.List;

import br.compnatural.BinarySpecification.BinaryCoordinate;

public class BinaryState extends State {

	protected BinaryState(List<Coordinate> coordinate) {
		super(coordinate);		
	}

	protected BinaryState(State state) {
		this(state.getCoordinate());
		if(state instanceof BinaryState){
			BinaryState bState = (BinaryState) state;
			super.setCoordinate(new ArrayList<Coordinate>(state.getCoordinate().size()));
			for (Coordinate coordinate : state.getCoordinate()) {
				Coordinate lCoordinate = new Coordinate(coordinate.name, coordinate.min, coordinate.max);
				lCoordinate.setValue(coordinate.getValue());
				getCoordinate().add(lCoordinate);
			}
		}
	}

}
