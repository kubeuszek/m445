package br.compnatural.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import br.compnatural.State;
import br.compnatural.coordinate.BinaryCoordinate;
import br.compnatural.coordinate.Coordinate;

public class BinarySpecification implements Specification {
	
	Logger log = Logger.getLogger(BinarySpecification.class.getName());
	Random random = new Random(System.currentTimeMillis());
	List<BinaryCoordinate> population = new ArrayList<BinaryCoordinate>();
	
	public State initialize(){
		
		BinaryCoordinate coordinate;
		for (int i = 0; i < population.size() ; ++i) {
			
			coordinate = population.get(i);
			
			initialize(coordinate);			
		}
		
		List<Coordinate> lCoordinates = new ArrayList<Coordinate>(population.size());
		for (BinaryCoordinate lCoordinate : population) {
			lCoordinates.add(lCoordinate);
		}
		
		return new State(lCoordinates, Boolean.TRUE);
	}
	
	public void initialize(BinaryCoordinate coordinate){
		for (int i = 0; i < coordinate.getValue().length; i++) {
			coordinate.getValue()[i] = random.nextBoolean();			
		}
	}
	
	public void addCoordinate(String name, int length){
		population.add(new BinaryCoordinate(name, length));
	}

	@Override
	public State perturb(State pState) {
		// TODO Auto-generated method stub
		return null;
	}
}
