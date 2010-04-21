package br.compnatural;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import br.compnatural.coordinate.BinaryCoordinate;

public class BinarySpecification {
	
	Logger log = Logger.getLogger(BinarySpecification.class.getName());
	Random random = new Random(System.currentTimeMillis());
	List<BinaryCoordinate> population = new ArrayList<BinaryCoordinate>();
	
	public State initialize(){
		
		BinaryCoordinate coordinate;
		for (int i = 0; i < population.size() ; ++i) {
			
			coordinate = population.get(i);
			
			initialize(coordinate);			
		}
		
		return null;//new State(coordinates);
	}
	
	public void initialize(BinaryCoordinate coordinate){
		for (int i = 0; i < coordinate.getValue().length; i++) {
			coordinate.getValue()[i] = random.nextBoolean();			
		}
	}
	
	public void addCoordinate(String name, int length){
		population.add(new BinaryCoordinate(name, length));
	}
}
