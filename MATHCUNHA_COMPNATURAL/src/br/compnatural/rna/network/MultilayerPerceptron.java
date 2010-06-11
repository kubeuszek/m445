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
import br.compnatural.rna.funtion.RnaFunction;
import br.compnatural.rna.funtion.TangenteHiperbolica;

public class MultilayerPerceptron {

	private double minWeight;
	private double maxWeight;
	private Layer outLayer;
	private Layer hiddenLayer;
	protected Logger log = Logger.getLogger(MultilayerPerceptron.class.getName());

	public MultilayerPerceptron(double minWeight, double maxWeight) {
		this.maxWeight = maxWeight;
		this.minWeight = minWeight;
	}

	@SuppressWarnings("unchecked")
	private static void mountLayer(int lenNeuron, List<Neuron> neurons, int weights,
			Random random, double minWeight, double maxWeight,
			Class functionClass) {
		for (int i = 0; i < lenNeuron; i++) {
			double[] weigth = new double[weights];

			for (int j = 0; j < weights; j++) {
				weigth[j] = randomValueInRange(random, minWeight, maxWeight);
			}
			Neuron neuron = new Neuron(weigth, randomValueInRange(random,
					minWeight, maxWeight), (RnaFunction) Util
					.createObject(functionClass.getName()));
			neuron.setBias(randomValueInRange(random, minWeight, maxWeight));
			neurons.add(neuron);
		}
	}

	public static MultilayerPerceptron getMultilayerPerceptronSinalBipolar(
			int numNeuronsHidden, int numNeuronsOut, int weights,
			double minWeight, double maxWeight) {
		List<Neuron> neurons = new ArrayList<Neuron>(numNeuronsHidden);
		Layer layer = new Layer(neurons, minWeight, maxWeight);
		Random lRandom = new Random(System.currentTimeMillis());
		MultilayerPerceptron retorno = new MultilayerPerceptron(minWeight, maxWeight);

		mountLayer(numNeuronsHidden, neurons, weights, lRandom, minWeight, maxWeight,
				TangenteHiperbolica.class);
		retorno.setHiddenLayer(layer);

		neurons = new ArrayList<Neuron>(numNeuronsOut);
		layer = new Layer(neurons, minWeight, maxWeight);
		mountLayer(numNeuronsOut, neurons, numNeuronsHidden, lRandom, minWeight, maxWeight,
				TangenteHiperbolica.class);
		retorno.setOutLayer(layer);

		return retorno;
	}

	public void backprop(int max_it, double min_error, double alfa,
			Pattern pattern, int numNeuronsHidden) {
		MultilayerPerceptron lMultilayerPerceptron = getMultilayerPerceptronSinalBipolar(
				numNeuronsHidden, pattern.getD().length,
				pattern.getX()[0].length, minWeight, maxWeight);
		Random random = new Random(System.currentTimeMillis());

		for (int i = 0; i < max_it; i++) {

			int index = 0;
			Set<Integer> indexes = new LinkedHashSet<Integer>(
					pattern.getD().length);

			while (indexes.size() < pattern.getD().length) {

				int aux = indexes.size();
				index = random.nextInt(pattern.getD().length);
				indexes.add(index);
				if (aux == indexes.size()) {
					continue;
				}
				
				Matrix yMtrHidden = new Matrix(lMultilayerPerceptron.getHiddenLayer().run(pattern.getX()[index]));
				Matrix yMtrOut = new Matrix(lMultilayerPerceptron.getOutLayer().run(yMtrHidden.getColumnPackedCopy()));
				
				Matrix dMtr = pattern.getDMatrix()[index];
				
				Matrix sensitivityOut = getF(lMultilayerPerceptron.getOutLayer()).times(-2);				
				sensitivityOut = sensitivityOut.times((dMtr.minus(yMtrOut)));
				
				Matrix sensitivityHidden = getF(lMultilayerPerceptron.getHiddenLayer()).times(lMultilayerPerceptron.getOutLayer().getW().transpose());
				sensitivityHidden = sensitivityHidden.times(sensitivityOut);
				
				//Update weights and bias
				sensitivityHidden 	= sensitivityHidden.times(alfa);
				sensitivityOut 		= sensitivityOut.times(alfa);
				
				lMultilayerPerceptron.getHiddenLayer().minusDeltaW(sensitivityHidden.times(pattern.getXMatrix()[index].transpose()));
				lMultilayerPerceptron.getOutLayer().minusDeltaW(sensitivityOut.times(yMtrHidden.transpose()));
				
				lMultilayerPerceptron.getHiddenLayer().minusDeltaB(sensitivityHidden);
				lMultilayerPerceptron.getOutLayer().minusDeltaB(sensitivityOut);
			}
			
			log.fine(indexes.toString());
		}

		this.setHiddenLayer(lMultilayerPerceptron.getHiddenLayer());
		this.setOutLayer(lMultilayerPerceptron.getOutLayer());
	}
	
	public double[][] run(Pattern pattern, int index){
		Matrix yMtrHidden = new Matrix(this.getHiddenLayer().run(pattern.getX()[index]));
		return this.getOutLayer().run(yMtrHidden.getColumnPackedCopy());
	}

	public Matrix getF(Layer layer) {
		double[][] matrix = new double[layer.getNeurons().size()][layer
				.getNeurons().size()];
		for (int i = 0; i < matrix.length; i++) {
			Neuron neuron = layer.getNeurons().get(i);
			matrix[i][i] = neuron.getFunction().eval_derivative(neuron.getU());
		}
		return new Matrix(matrix);
	}

	public static double randomValueInRange(Random random, double min,
			double max) {
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
