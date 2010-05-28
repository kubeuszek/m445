package br.compnatural.rna.funtion;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SinalBipolar implements RnaFunction {
	Logger log = Logger.getLogger(SinalBipolar.class.getName());

	@Override
	public double eval(double v) {
		if (v < 0) {
			return -1;
		} else if (v > 0) {
			return 1;
		} else {
			log.log(Level.SEVERE, "Confirmar se isso vai ser possívell");
			return 0;
		}
	}

	@Override
	public double eval_derivative(double v) {
		return eval(v);
	}

}
