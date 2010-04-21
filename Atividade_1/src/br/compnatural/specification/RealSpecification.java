package br.compnatural.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.compnatural.State;
import br.compnatural.coordinate.Coordinate;
import br.compnatural.coordinate.RealCoordinate;

public class RealSpecification implements Specification{
	
	Logger log = Logger.getLogger(RealSpecification.class.getName());
	
	Random random = new Random(System.currentTimeMillis());	
	
	Random random2 = new Random(System.currentTimeMillis());
	
	public static final long error = 1000000;
	
	List<RealCoordinate> coordinates = new ArrayList<RealCoordinate>();
	
	public void addCoordinate(String name, double min, double max){
		coordinates.add(new RealCoordinate(name, min, max));
	}
	
	public State initialize(){
		
		RealCoordinate coordinate;
		for (int i = 0; i < coordinates.size() ; ++i) {
			
			coordinate = coordinates.get(i);
			
			coordinate.setValue(initialize(coordinate));
			
			if(!isInRange(coordinate, coordinate.getValue())){
				i--;
				log.log(Level.SEVERE, coordinate+" - valor fora do intervalo "+coordinate.getValue());
			}
		}
		
		List<Coordinate> lCoordinates = new ArrayList<Coordinate>(coordinates.size());
		for (RealCoordinate lCoordinate : coordinates) {
			lCoordinates.add(lCoordinate);
		}
		
		return new State(lCoordinates, Boolean.FALSE);
	}
	
	public double initialize(RealCoordinate coordinate){
		double value;
		double range = Math.abs((coordinate.max - coordinate.min)); 
		
		value = (random.nextDouble() * range) + coordinate.min; 
		
		return value;
	}
	
	public boolean isInRange(RealCoordinate coordinate, double value){
		
		double diff = value - coordinate.min;
		double diff2 = coordinate.max - value;
		
		log.info(coordinate+" -- "+diff +", "+diff2 + ", "+value);
		
		diff *= error;
		diff2 *= error;
		
		//log.info(diff +", "+diff2 + ", "+value);
		
		if((int) diff < 0){
			return false;
		}
		
		if((int) diff2 < 0){
			return false;
		}
		
		return true;
	}
	
	public State perturb(State pState){
		
		int index = random2.nextInt(pState.getCoordinate().size()); 
		State retorno = new State(pState, Boolean.FALSE);
		RealCoordinate coordinate = (RealCoordinate) retorno.getCoordinate().get(index);
		
		double min = coordinate.getValue() - coordinate.delta;
		double max = coordinate.getValue() + coordinate.delta;
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
		
		RealCoordinate vizinho = new RealCoordinate("v_"+coordinate.name, min, max);
		vizinho.setValue(coordinate.value);
		
		log.info(vizinho.toString() + " delta "+coordinate.delta);

		double value = initialize(vizinho);
		
		while(!isInRange(vizinho, value)){
			value = initialize(vizinho);
			log.log(Level.SEVERE, vizinho+" - valor fora do intervalo "+vizinho.getValue());
		}
		
		coordinate.setValue(value);
		
		return retorno;
	}
	
	
}
