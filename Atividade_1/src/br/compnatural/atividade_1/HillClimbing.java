package br.compnatural.atividade_1;

import br.compnatural.MathFunction;
import br.compnatural.Specification;
import br.compnatural.State;

public class HillClimbing {
	
	
	public static State hillClimbingStandard(int max_it, State g, MathFunction function, Specification specification){
		
		State x = initialize(specification);
		x.setValue( function.eval(x)) ;
		
		int t = 0;
		
		Integer it_first_best = null;
		
		while(t < max_it && equals_witherror( x, g)){
			State x_linha = perturb(x, specification);
			x_linha.setValue( function.eval(x_linha));
			
			if(x_linha.getValue() > x.getValue()){
				x = x_linha;
				
				if(null == it_first_best){
					it_first_best = t;
				}
			}
			
			t++;
		}
		return x;
	}

	private static boolean  equals_witherror(State x, State g) {		
		double diff = x.getValue() - g.getValue();
		
		diff *= (double) 1000000;
		
		if(diff != 0){
			return false;
		}

		return true;
	}
	
	
	public static State initialize(Specification specification){
		return specification.initialize();
	}
	
	public static State perturb(State x, Specification specification){
		return specification.perturb(x);
	}
	
}
