package br.compnatural.rna.funtion;

public class TangenteHiperbolica implements RnaFunction {

	@Override
	public double eval(double v) {
		double aux = -2*v;
		return (1 - Math.exp(aux)) / (1 + Math.exp(aux));
	}

	@Override
	public double eval_derivative(double v) {
		return 1 - Math.pow(eval(v), 2);
	}

}
