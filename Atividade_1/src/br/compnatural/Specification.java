package br.compnatural;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class Specification {
	
	Logger log = Logger.getLogger(Specification.class.getName());
	
	Random random = new Random(System.currentTimeMillis());
	Random random2 = random;//new Random(System.currentTimeMillis());
	Random random3 = random;//new Random(System.currentTimeMillis());
	
	List<Coordinate> coordinates = new ArrayList<Coordinate>();
	
	public void addCoordinate(String name, int min, int max){
		coordinates.add(new Coordinate(name, min, max));
	}
	
	public State initialize(){
		double values [] = new double[coordinates.size()];
		Coordinate coordinate;
		for (int i = 0; i < coordinates.size() ; ++i) {
			
			coordinate = coordinates.get(i);
			
			values[i] = initialize(coordinate);
			
			if(!isInRange(coordinate, values[i])){
				i--;
				log.info(coordinate.name+" - valor fora do intervalo "+values[i]);
			}
			
			i++;
		}
		
		return new State(coordinates, values);
	}
	
	public double initialize(Coordinate coordinate){
		double value;
		
		boolean positivo = true;
		
		if(coordinate.min < 0){
			positivo = random3.nextBoolean();
		}
		
		if(coordinate.max == 1 && coordinate.min == 0){
			value  = random.nextDouble();
			return value;
		}else{
			value = random2.nextInt(new Double(coordinate.max).intValue()) + random.nextDouble(); 
		}
		
		if(!positivo){
			value *= -1;
		}
		
		return value;
		
	}
	
	public boolean isInRange(Coordinate coordinate, double value){
		
		double diff = value - coordinate.min;
		double diff2 = coordinate.max - value;
		log.info(diff +", "+diff2 + ", "+value);
		diff *= 1000000;
		diff2 *= 1000000;
		
		//log.info(diff +", "+diff2 + ", "+value);
		
		if((int) diff < 0){
			return false;
		}
		
		if((int) diff2 < 0){
			return false;
		}
		
		return true;
	}
	
	public State perturb(State state){
		String name = "v";
		
		Coordinate coordinate = state.getCoordinate().get(0);
		
		double min = state.getCoordinates()[0] - coordinate.delta;
		double max = state.getCoordinates()[0] + coordinate.delta;
		double aux ; 
		
		if(min > max){
			aux = max;
			max = min;
			min = aux;
		}
		
		if(min < coordinate.min){
			min = coordinate.min;
		}
		
		if(max > coordinate.max){
			max = coordinate.max;
		}
		
		Coordinate vizinho = new Coordinate("v"+coordinate.name, min, max);

		double value = initialize(vizinho);
		
		while(!isInRange(coordinate, value)){
			value = initialize(vizinho);
			log.info(vizinho.name+" - valor fora do intervalo "+value);
		}
		
		double[] arr = state.getCoordinates();
		arr[0] = value;
		
		return new State(state.getCoordinate(), arr);
	}
	
	static class Coordinate{
		String name;
		double min;
		double max;
		double delta;
		
		public Coordinate(String name, double min, double max){
			this.name = name;
			this.min = min;
			this.max = max;
			delta = (double)(Math.abs(min) + Math.abs(max)) * 2d / 100d;
		}
		
	}
}
