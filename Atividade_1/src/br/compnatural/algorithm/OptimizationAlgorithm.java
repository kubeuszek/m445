package br.compnatural.algorithm;

import java.util.logging.Logger;

import br.compnatural.Specification;
import br.compnatural.State;

public abstract class OptimizationAlgorithm {
	
	protected Logger log = Logger.getLogger(OptimizationAlgorithm.class.getName());
	
	protected static boolean  equals_witherror(State x, State g) {		
		double diff = x.getValue() - g.getValue();
		
		diff *= (double) 1000;
		
		if((int)diff != 0){
			return false;
		}

		return true;
	}
	
	
	protected static State initialize(Specification specification){
		return specification.initialize();
	}
	
	protected static State perturb(State x, Specification specification){
		return specification.perturb(x);
	}
	
	public abstract String getName();
}
