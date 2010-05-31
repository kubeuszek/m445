package br.compnatural.rna;

import Jama.Matrix;

public class Pattern {
	private double[][] x;
	private double[][] d;
	private Matrix[] X;
	private Matrix[] D;

	public double[][] getX() {
		return x;
	}

	public void setX(double[][] x) {
		this.x = x;
	}
	
	public Matrix[] getXMatrix(){
		return X; 
	}
	
	public Matrix[] getDMatrix(){
		return D; 
	}

	public double[][] getD() {
		return d;
	}

	public void setD(double[][] d) {
		this.d = d;
	}

	public void buildMatrix() {
		double[] value;
		X = new Matrix[x[0].length];

		for (int i = 0; i < x[0].length; i++) {
			value = new double[x.length];
			for (int j = 0; j < x.length; j++) {
				value[j] = x[j][i];
			}
			X[i] = new Matrix(value, x.length);
		}
		
		
		D = new Matrix[d[0].length];

		for (int i = 0; i < d[0].length; i++) {
			value = new double[d.length];
			for (int j = 0; j < d.length; j++) {
				value[j] = d[j][i];
			}
			D[i] = new Matrix(value, d.length);
		}
	}
}
