package br.compnatural.rna.funtion;

import java.math.BigDecimal;

public class SigmoidalBipolar implements RnaFunction {

	@Override
	public double eval(double v) {
		double aux = (new BigDecimal(v)).negate().doubleValue();
		return (1 - Math.exp(aux)) / (1 + Math.exp(aux));
	}

	@Override
	public double eval_derivative(double v) {
		return (1 - Math.pow(eval(v), 2))/2;
	}

}
