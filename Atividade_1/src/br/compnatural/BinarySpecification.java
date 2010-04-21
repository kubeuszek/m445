package br.compnatural;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

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

	public static class BinaryCoordinate{
		public String name;
		public int length;
		private boolean value[];

		public boolean[] getValue() {
			return value;
		}

		public void setValue(boolean[] value) {
			this.value = value;
		}

		public BinaryCoordinate(String name, int length){
			this.name = name;
			this.length = length;
			value = new boolean[length];
		}
		
		public String toString(){
			String string = "name=("+name +") value=(";
			for (int i = 0; i < value.length; i++) {
				string += value[i] ? "1":"0";
				
			}
			return string +")";  
		}
	}
}
