package br.compnatural.rna;

import java.util.List;


public class Layer {
	
	private List<Neuron> neurons;
	
	public List<Neuron> getNeurons() {
		return neurons;
	}

	public void setNeurons(List<Neuron> neurons) {
		this.neurons = neurons;
	}

	public Layer(List<Neuron> neurons){
		this.neurons = neurons;
	}
	
	public double[] run(double[] x){
		double retorno[] = new double[neurons.size()];
		
		int i = 0;
		for (Neuron neuron : neurons) {
			
			retorno[i++] = neuron.eval(x);
				
		}
		
		return retorno;
	}
}
