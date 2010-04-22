package br.compnatural.specification;

import br.compnatural.State;

public interface Specification {
	public State initialize();
	
	public State perturb(State pState);
	
	public State[] recombination(State male, State female, Boolean crossOver);
}
