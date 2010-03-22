package br.compnatural.atividade_1;

import br.compnatural.Experimento;
import br.compnatural.Specification;
import br.compnatural.State;

public class Experimento_1 extends Experimento {
	
	public static void main(String[] args){
		
		State g = State.getState();
		g.setValue(1);
		
		Specification specification = null;
		for (int i = 0; i < 10; i++) {
			specification = new Specification();
			
			specification.addCoordinate("x", 0, 1);
			
			HillClimbing.hillClimbingStandard(10, g, new FunctionUnid(), specification);
			
			specification = new Specification();
			
			specification.addCoordinate("x", 0, 1);
			
			HillClimbing.hillClimbingStandard(100, g, new FunctionUnid(), specification);
			
			
			specification = new Specification();
			
			specification.addCoordinate("x", 0, 1);
			
			HillClimbing.hillClimbingStandard(1000, g, new FunctionUnid(), specification);
		}
		
	}
	
}
