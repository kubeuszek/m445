package br.compnatural.atividade_1;

import java.math.BigDecimal;

import br.compnatural.MathFunction;
import br.compnatural.State;

public class FunctionUnid implements MathFunction {

	@Override
	public Double eval(State state) {
		BigDecimal x = new BigDecimal(state.getCoordinate().get(0).getValue());
		Integer dois = new Integer(2);
		Integer seis = new Integer(6);
		Integer cinco = new Integer(5);
		Float zero_um = new Float (0.1);
		Float zero_nove = new Float (0.9);
		
		x.subtract(new BigDecimal(zero_um)).divide(new BigDecimal(zero_nove)).pow(dois).multiply(new BigDecimal(dois * (-1)));
		
		BigDecimal primeiraParte = new BigDecimal(StrictMath.pow(dois, x.doubleValue()));
		
		BigDecimal segundaParte = new BigDecimal(StrictMath.pow((StrictMath.sin(cinco * StrictMath.PI * x.doubleValue())), seis));
				
		return primeiraParte.multiply(segundaParte).doubleValue();
	}

}
