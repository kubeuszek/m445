package br.compnatural.rna.network;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.logging.Logger;

import Jama.Matrix;
import br.compnatural.rna.Layer;
import br.compnatural.rna.Neuron;
import br.compnatural.rna.Pattern;
import br.compnatural.rna.Util;
import br.compnatural.rna.funtion.ThresholdBipolar;

public class SinglePerceptron {
	
	private double minWeight;
	private double maxWeight;
	private Layer layer;
	protected Logger log = Logger.getLogger(SinglePerceptron.class.getName());
	
	
	public SinglePerceptron(int neuron, double minWeight, double maxWeight){
		this.maxWeight = maxWeight;
		this.minWeight = minWeight;
	}
	
	public void perceptron(int max_it, double alfa, Pattern pattern){
		SinglePerceptron singlePerceptron = getSinglePerceptronSinalBipolar(pattern.getD().length, pattern.getX()[0].length, minWeight, maxWeight);
		Random random = new Random(System.currentTimeMillis());
		for (int i = 0; i < max_it; i++) {
			int index = 0;
			Set<Integer> indexes = new LinkedHashSet<Integer>(pattern.getD().length);
			
			
			while ( indexes.size() < pattern.getD().length ){
				
				int aux = indexes.size();
				index = random.nextInt(pattern.getD().length);
				indexes.add(index);
				if(aux == indexes.size()){
					continue;
				}
				
				Matrix yMtr = new Matrix(run(pattern, singlePerceptron, index));
				
				Matrix dMtr = pattern.getDMatrix()[index];
				
				Matrix eMtr = dMtr.minus(yMtr);
				
				eMtr = eMtr.times(alfa);
				
				Matrix deltaW = eMtr.times((pattern.getXMatrix()[index].transpose()));
				Matrix deltab = eMtr; 				
				
				singlePerceptron.layer.addDeltaB(deltab);
				singlePerceptron.layer.addDeltaW(deltaW);
			}
			
			log.fine(indexes.toString());
		}
		
		this.layer = singlePerceptron.layer;
	}
	
	public double[][] run(Pattern pattern, int index){
		return this.run(pattern, this, index);
	}

	private double[][] run(Pattern pattern, SinglePerceptron singlePerceptron,
			int index) {
		return singlePerceptron.layer.run(pattern.getX(), index);
	}
	
	public static Matrix getX(double[] x){
		double[][] X = new double[x.length][];
		for (int i = 0; i < x.length; i++) {
			X[i][0] = x[i];
		}
		
		return new Matrix(X);
	}
	
	public static SinglePerceptron getSinglePerceptronSinalBipolar (int numNeurons, int weights, double minWeight, double maxWeight){
		List<Neuron> neurons = new ArrayList<Neuron>(numNeurons);
		Layer layer = new Layer(neurons, minWeight, maxWeight);
		Random lRandom = new Random(System.currentTimeMillis());
		
		for (int i = 0; i < numNeurons; i++) {
			double[] weigth = new double[weights];
			
			for (int j = 0; j < weights; j++) {
				weigth[j] = randomValueInRange(lRandom, minWeight, maxWeight); 
			}
			
			Neuron neuron = new Neuron(weigth, randomValueInRange(lRandom, minWeight, maxWeight), new ThresholdBipolar());
			
			neurons.add(neuron);
		}
		
		SinglePerceptron retorno = new SinglePerceptron(numNeurons, minWeight, maxWeight);
		retorno.setLayer(layer);
		return retorno;
	}
	
	public static double randomValueInRange(Random random, double min, double max){
		return Util.randomValueInRange(random, min, max);
	}
	
	public void setLayer(Layer layer) {
		this.layer = layer;
	}
}
