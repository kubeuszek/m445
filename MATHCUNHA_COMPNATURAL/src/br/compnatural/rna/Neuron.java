package br.compnatural.rna;

import br.compnatural.rna.funtion.RnaFunction;

public class Neuron {
	private double[] weights;
	private double bias;
	private RnaFunction function;
	
	public Neuron(double[] weights, double bias, RnaFunction function){
		this.function = function;
		this.weights = weights;
		this.bias = bias;
	}

	public double[] getWeights() {
		return weights;
	}

	public void setWeights(double[] weights) {
		this.weights = weights;
	}

	public double getBias() {
		return bias;
	}

	public void setBias(double bias) {
		this.bias = bias;
	}

	public RnaFunction getFunction() {
		return function;
	}

	public void setFunction(RnaFunction function) {
		this.function = function;
	}
	
	public double junction(double[] x){
		
		double sum = bias;
		for (int i = 0; i < weights.length; i++) {
			sum += (weights[i] * x[i]);
		}
		
		return sum;
	}
	
	public double eval(double[] x){
		return  getFunction().eval(junction(x)); 
	}
}
