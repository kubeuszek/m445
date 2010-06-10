package br.compnatural.rna;

import java.util.List;

import Jama.Matrix;


public class Layer {
	
	private double minWeight;
	private double maxWeight;
	
	public double getMinWeight() {
		return minWeight;
	}

	public void setMinWeight(double minWeight) {
		this.minWeight = minWeight;
	}

	public double getMaxWeight() {
		return maxWeight;
	}

	public void setMaxWeight(double maxWeight) {
		this.maxWeight = maxWeight;
	}
	
	private List<Neuron> neurons;
	
	public List<Neuron> getNeurons() {
		return neurons;
	}

	public void setNeurons(List<Neuron> neurons) {
		this.neurons = neurons;
	}

	public Layer(List<Neuron> neurons, double minWeight, double maxWeight){
		this.maxWeight = maxWeight;
		this.minWeight = minWeight;
		this.neurons = neurons;
	}
	
	public double[][] run(double[][] x, int index){
		double retorno[][] = new double[neurons.size()][1];
		double vector[] = new double[x[1].length];
		
		for (int i = 0; i < vector.length; i++) {
			vector[i] = x[index][i];
		}
		
		retorno = run(vector);
		
		return retorno;
	}

	public double[][] run(double[] vector) {
		int i = 0;
		double retorno[][] = new double[neurons.size()][1];
		
		for (Neuron neuron : neurons) {
			retorno[i++][0] = neuron.eval(vector);
		}
		
		return retorno;
	}
	
	public void addDeltaB(Matrix deltaB){
		int i = 0;
		for (Neuron neuron : neurons) {
			neuron.setBias(deltaB.get(i++, 0));
		}
	}
	
	public void addDeltaW(Matrix deltaW){
		int j = 0 ;
		for (Neuron neuron : neurons) {
			for (int i = 0; i < neuron.getWeights().length; i++) {
				neuron.getWeights()[i] = neuron.getWeights()[i] + deltaW.get(j, i);
				
				/*if(neuron.getWeights()[i] > maxWeight){
					neuron.getWeights()[i] = maxWeight;
				}else if(neuron.getWeights()[i] < minWeight){
					neuron.getWeights()[i] = minWeight;
				}*/
			}
			j++;
		}
	}
	
	public Matrix getW(){
		Matrix retorno = null;
		double[][] value = null;
		int i =0;
		for (Neuron neuron : neurons) {
			if(value == null){
				value = new double[neurons.size()][neuron.getWeights().length];
			}
			value[i] = neuron.getWeights();
		}
		retorno = new Matrix(value);
		return retorno;
	}
}
