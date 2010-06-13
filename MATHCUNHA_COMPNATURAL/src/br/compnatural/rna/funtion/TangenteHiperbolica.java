package br.compnatural.rna.funtion;

public class TangenteHiperbolica implements RnaFunction {

	@Override
	public double eval(double v) {
		double aux = -2d*v;
		return (1d - Math.exp(aux)) / (1d + Math.exp(aux));
	}

	@Override
	public double eval_derivative(double v) {
		return 1d - Math.pow(eval(v), 2d);
	}
	
	public static void main(String args[]){
		TangenteHiperbolica tan =  new TangenteHiperbolica();
		System.out.println(tan.eval(-4));
	}

}
