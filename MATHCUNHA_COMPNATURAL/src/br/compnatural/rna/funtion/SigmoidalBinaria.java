package br.compnatural.rna.funtion;

import java.math.BigDecimal;

public class SigmoidalBinaria implements RnaFunction {

	@Override
	public double eval(double v) {
		double aux = (new BigDecimal(v)).negate().doubleValue();
		return (1 )/ (1 + Math.exp(aux));
	}

	@Override
	public double eval_derivative(double v) {
		double aux = eval(v);
		return aux * (1-aux);
	}

}
