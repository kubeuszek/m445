package br.compnatural.rna.network;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Jama.Matrix;
import br.compnatural.rna.Layer;
import br.compnatural.rna.Neuron;
import br.compnatural.rna.Pattern;
import br.compnatural.rna.funtion.SinalBipolar;

public class SinglePerceptron {
	
	private double minWeight;
	private double maxWeight;
	private Layer layer;
	
	
	public SinglePerceptron(Layer layer, double minWeight, double maxWeight){
		this.maxWeight = maxWeight;
		this.minWeight = minWeight;
		this.layer = layer;
	}
	
	public void perceptron(int max_it, double alfa, List<Pattern> patterns){
		SinglePerceptron singlePerceptron = getSinglePerceptronSinalBipolar(this.layer.getNeurons().size(), minWeight, maxWeight);
		
		for (int i = 0; i < max_it; i++) {
			double[] y;
			for (Pattern pattern : patterns) {
				y = singlePerceptron.layer.run(pattern.getX());
				
				Matrix yMtr = new Matrix(y, y.length);
				Matrix dMtr = new Matrix(pattern.getD(), pattern.getD().length);
				
				Matrix eMtr = dMtr.minus(yMtr);
				
				eMtr = eMtr.times(alfa);
				
				Matrix deltaW = eMtr.times(getX(pattern.getX()));
				
				//TODO - bias e W
			}
		}
		
		this.layer = singlePerceptron.layer;
	}
	
	public static Matrix getX(double[] x){
		double[][] X = new double[x.length][x.length];
		for (int i = 0; i < x.length; i++) {
			X[i] = x;
		}
		
		return new Matrix(X);
	}
	
	public static SinglePerceptron getSinglePerceptronSinalBipolar (int numNeurons, double minWeight, double maxWeight){
		List<Neuron> neurons = new ArrayList<Neuron>(numNeurons);
		Layer layer = new Layer(neurons);
		Random lRandom = new Random(System.currentTimeMillis());
		
		for (int i = 0; i < numNeurons; i++) {
			double[] weigth = new double[numNeurons];
			
			for (int j = 0; j < numNeurons; j++) {
				weigth[j] = randomValueInRange(lRandom, minWeight, maxWeight); 
			}
			neurons.add(new Neuron(weigth, randomValueInRange(lRandom, minWeight, maxWeight), new SinalBipolar()));
		}
		
		return new SinglePerceptron(layer, minWeight, maxWeight);		
	}
	
	public static double randomValueInRange(Random random, double min, double max){
		double value;
		double range = Math.abs(max - min);

		value = (random.nextDouble() * range) + min;

		return value;
	}
}
