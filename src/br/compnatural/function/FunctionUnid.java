package br.compnatural.function;

import java.math.BigDecimal;
import java.math.MathContext;

import br.compnatural.State;
import br.compnatural.coordinate.RealCoordinate;

public class FunctionUnid implements MathFunction {
	
	final Integer dois = new Integer(2);
	final Integer seis = new Integer(6);
	final Integer cinco = new Integer(5);
	final Float zero_um = new Float(0.1);
	final Float zero_nove = new Float(0.9);
	private State g;
	final Boolean max;
	
	
	
	public FunctionUnid(Boolean max){
		this.max = max;
		g = State.getState();
		g.setValue(1d);
	}

	@Override
	public Double eval(State state) {
		
		MathContext context = MathContext.DECIMAL128;
		BigDecimal x = new BigDecimal(((RealCoordinate)state.getCoordinate().get(0)).getValue());
		

		BigDecimal primeiraParte = x.subtract(new BigDecimal(zero_um))
		.divide(new BigDecimal(zero_nove),context)
		.pow(dois)
		.multiply(new BigDecimal(dois * (-1)));

		primeiraParte = new BigDecimal(StrictMath.pow(dois, primeiraParte.doubleValue()));

		BigDecimal segundaParte = new BigDecimal(StrictMath.pow((StrictMath
				.sin(cinco * StrictMath.PI * x.doubleValue())), seis));

		
		primeiraParte = primeiraParte.multiply(segundaParte);
		
		if(!max){
			primeiraParte = primeiraParte.negate();
			g.setValue(-1d);
		}
		
		return primeiraParte.doubleValue();
		
		
	}

	@Override
	public String getName() {
		
		return "Function Unid";
	}

	@Override
	public State getMax() {
		return g;
	}

	@Override
	public Boolean hasMaximum() {
		return Boolean.TRUE;
	}
}
