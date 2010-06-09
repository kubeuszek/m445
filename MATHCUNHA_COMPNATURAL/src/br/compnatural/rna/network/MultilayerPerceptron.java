package br.compnatural.rna.network;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.compnatural.rna.Layer;
import br.compnatural.rna.Neuron;
import br.compnatural.rna.Util;
import br.compnatural.rna.funtion.RnaFunction;
import br.compnatural.rna.funtion.TangenteHiperbolica;

public class MultilayerPerceptron {
	
	private double minWeight;
	private double maxWeight;
	
	private Layer outLayer;
	private Layer hiddenLayer;

	private int lenInNeuron;
	
	public MultilayerPerceptron(int lenInNeuron, double minWeight, double maxWeight){
		this.maxWeight = maxWeight;
		this.minWeight = minWeight;
		this.lenInNeuron = lenInNeuron;
	}
	
	private static void mountLayer(List<Neuron> neurons, int weights, Random random, double minWeight, double maxWeight, Class functionClass){
		for (int i = 0; i < neurons.size(); i++) {
			double[] weigth = new double[weights];
			
			for (int j = 0; j < weights; j++) {
				weigth[j] = randomValueInRange(random, minWeight, maxWeight); 
			}
			neurons.add(new Neuron(weigth, randomValueInRange(random, minWeight, maxWeight), (RnaFunction) Util.createObject(functionClass.getName())));
		}
	}
	
	public static MultilayerPerceptron getMultilayerPerceptronSinalBipolar (int numNeuronsHidden, int numNeuronsOut, int weights, double minWeight, double maxWeight){
		List<Neuron> neurons = new ArrayList<Neuron>(numNeuronsHidden);
		Layer layer = new Layer(neurons, minWeight, maxWeight);
		Random lRandom = new Random(System.currentTimeMillis());
		MultilayerPerceptron retorno = new MultilayerPerceptron(weights, minWeight, maxWeight);
		
		mountLayer(neurons, weights, lRandom, minWeight, maxWeight, TangenteHiperbolica.class);
		retorno.setHiddenLayer(layer);
		
		neurons = new ArrayList<Neuron>(numNeuronsOut);
		layer = new Layer(neurons, minWeight, maxWeight);
		mountLayer(neurons, numNeuronsHidden, lRandom, minWeight, maxWeight, TangenteHiperbolica.class);
		retorno.setOutLayer(layer);
		
		return retorno;
	}
	
	public static double randomValueInRange(Random random, double min, double max){
		return Util.randomValueInRange(random, min, max);
	}
	
	public Layer getHiddenLayer() {
		return hiddenLayer;
	}

	public void setHiddenLayer(Layer hiddenLayer) {
		this.hiddenLayer = hiddenLayer;
	}
	public Layer getOutLayer() {
		return outLayer;
	}

	public void setOutLayer(Layer outLayer) {
		this.outLayer = outLayer;
	}
}
