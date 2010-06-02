package br.compnatural.rna;

import Jama.Matrix;

public class Pattern {
	public int erro;
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
		X = new Matrix[x.length];

		for (int i = 0; i < x.length; i++) {
			value = new double[x[0].length];
			for (int j = 0; j < x[0].length; j++) {
				value[j] = x[i][j];
			}
			X[i] = new Matrix(value, value.length);
		}
		
		
		D = new Matrix[d.length];

		for (int i = 0; i < d.length; i++) {
			value = new double[d[0].length];
			for (int j = 0; j < d[0].length; j++) {
				value[j] = d[i][j];
			}
			D[i] = new Matrix(value, value.length);
		}
	}
}
