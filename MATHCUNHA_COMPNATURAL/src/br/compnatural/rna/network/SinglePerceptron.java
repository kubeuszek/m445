package br.compnatural.rna.network;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import Jama.Matrix;
import br.compnatural.rna.Layer;
import br.compnatural.rna.Neuron;
import br.compnatural.rna.Pattern;
import br.compnatural.rna.funtion.SinalBipolar;

public class SinglePerceptron {
	
	private double minWeight;
	private double maxWeight;
	private Layer layer;
	

	private int lenNeuron;
	
	
	public SinglePerceptron(int neuron, double minWeight, double maxWeight){
		this.maxWeight = maxWeight;
		this.minWeight = minWeight;
		lenNeuron = neuron;
	}
	
	public void perceptron(int max_it, double alfa, Pattern pattern){
		SinglePerceptron singlePerceptron = getSinglePerceptronSinalBipolar(pattern.getD().length, minWeight, maxWeight);
		Random random = new Random(System.currentTimeMillis());
		for (int i = 0; i < max_it; i++) {
			double[] y;
			
			int j = 0;
			Set<Integer> indexes = new LinkedHashSet<Integer>(pattern.getD().length);
			
			
			while ( indexes.size() < pattern.getD().length ){
				
				int ant = indexes.size();
				j = random.nextInt(pattern.getD().length);
				indexes.add(j);
				if(ant == indexes.size()){
					continue;
				}
				
				Matrix yMtr = new Matrix(singlePerceptron.layer.run(pattern.getX(), j));
				
				 
				Matrix dMtr = pattern.getDMatrix()[j];
				
				Matrix eMtr = dMtr.minus(yMtr);
				
				eMtr = eMtr.times(alfa);
				
				Matrix deltaW = eMtr.times((pattern.getXMatrix()[j].transpose()));
				Matrix deltab = eMtr; 				
				
				layer.addDeltaB(deltab);
				layer.addDeltaW(deltaW);
			}
		}
		
		this.layer = singlePerceptron.layer;
	}
	
	public static Matrix getX(double[] x){
		double[][] X = new double[x.length][];
		for (int i = 0; i < x.length; i++) {
			X[i][0] = x[i];
		}
		
		return new Matrix(X);
	}
	
	public static SinglePerceptron getSinglePerceptronSinalBipolar (int numNeurons, double minWeight, double maxWeight){
		List<Neuron> neurons = new ArrayList<Neuron>(numNeurons);
		Layer layer = new Layer(neurons, minWeight, maxWeight);
		Random lRandom = new Random(System.currentTimeMillis());
		
		for (int i = 0; i < numNeurons; i++) {
			double[] weigth = new double[numNeurons];
			
			for (int j = 0; j < numNeurons; j++) {
				weigth[j] = randomValueInRange(lRandom, minWeight, maxWeight); 
			}
			neurons.add(new Neuron(weigth, randomValueInRange(lRandom, minWeight, maxWeight), new SinalBipolar()));
		}
		
		SinglePerceptron retorno = new SinglePerceptron(numNeurons, minWeight, maxWeight);
		retorno.setLayer(layer);
		return retorno;
	}
	
	public static double randomValueInRange(Random random, double min, double max){
		double value;
		double range = Math.abs(max - min);

		value = (random.nextDouble() * range) + min;

		return value;
	}
	
	public void setLayer(Layer layer) {
		this.layer = layer;
	}
}
