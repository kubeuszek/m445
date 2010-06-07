package br.compnatural.rna.funtion;

public class ThresholdBipolar implements RnaFunction {

	@Override
	public double eval(double v) {
		if (v >= 0) {
			return 1;
		} else  {
			return -1;
		}
	}

	@Override
	public double eval_derivative(double v) {
		return eval(v);
	}

}
