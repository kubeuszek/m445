package br.compnatural.atividade_1;

import java.util.Random;

import br.compnatural.MathFunction;
import br.compnatural.State;

public class HillClimbing {
	
	
	public static State hillClimbingStandard(int max_it, State g, MathFunction function, Random random){
		
		State x = initialize(random);
		x.setValue(function.eval(x)) ;
		
		int t = 0;
		
		while(t < max_it && equals(g, x)){
			State x_linha = perturb(x, random);
			x_linha.setValue( function.eval(x_linha));
			
			if(x_linha.getValue() > x.getValue()){
				x = x_linha;
			}
			
			t++;
		}
		return x;
	}



	private static boolean equals(State g, State x) {
		return (int) (x.getValue() * 1000000) == (int) (g.getValue() * 1000000);
	}
	
	
	
	public static State initialize(Random random){
		return null;//new State (random.nextDouble());
	}
	
	public static State perturb(State x, Random random){
		return null;//new State (x.getCoordinates()[0] + random.nextDouble());
	}
	
}
