package br.compnatural.function;

import br.compnatural.State;

public class FunctionHamming implements MathFunction {
	
	private State g;
	final Boolean max;
	
	public FunctionHamming (Boolean max){
		this.max = max;
		g = State.getState();
		g.setValue(0);
	}

	@Override
	public Double eval(State state) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public State getMax() {		
		return g;
	}

	@Override
	public String getName() {		
		return "binario";
	}

	@Override
	public Boolean hasMaximum() {		
		return Boolean.TRUE;
	}

}
