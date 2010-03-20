package br.compnatural;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Specification {
	
	Random random = new Random(System.currentTimeMillis());
	Random random2 = new Random(System.currentTimeMillis());
	Random random3 = new Random(System.currentTimeMillis());
	
	List<Coordinate> coordinates = new ArrayList<Coordinate>();
	
	public void addCoordinate(String name, int min, int max){
		coordinates.add(new Coordinate(name, min, max));
	}
	
	public State initialize(){
		double values [] = new double[coordinates.size()];
		
		double value;
		for (int i = 0; i < coordinates.size() ; ++i) {
			
			Coordinate coordinate  = coordinates.get(i);
			
			boolean positivo = true;
			
			if(coordinate.min < 0){
				positivo = random3.nextBoolean();
			}
			
			if(coordinate.max == 1 && coordinate.min == 0){
				values[i]  = random.nextDouble();
				continue;
			}else{
				values[i] = random2.nextInt(coordinate.max) + random.nextDouble(); 
			}
			
			
			
			i++;
		}
		
		return new State(values);
	}
	
	public State perturb(State state){
		return null;
	}
	
	static class Coordinate{
		String name;
		int min;
		int max;
		
		public Coordinate(String name, int min, int max){
			this.name = name;
			this.min = min;
			this.max = max;
		}
		
	}
}
