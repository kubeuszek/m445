package br.compnatural.rna.funtion;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SinalBipolar implements RnaFunction {
	Logger log = Logger.getLogger(SinalBipolar.class.getName());
	
	Random random = new Random(System.currentTimeMillis());

	@Override
	public double eval(double v) {
		if (v < 0) {
			return -1;
		} else if (v > 0) {
			return 1;
		} else {
			log.log(Level.SEVERE, "Confirmar se isso vai ser possível");
			if(random.nextBoolean()){
				return -1;
			}else{
				return 1;
			}
			
		}
	}

	@Override
	public double eval_derivative(double v) {
		return eval(v);
	}

}
