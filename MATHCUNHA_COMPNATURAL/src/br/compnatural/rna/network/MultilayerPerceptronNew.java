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

public class MultilayerPerceptronNew {
	private double minWeight;
	private double maxWeight;
	private List<Layer> layers;

	protected Logger log = Logger.getLogger(MultilayerPerceptronNew.class
			.getName());

	public MultilayerPerceptronNew(double minWeight, double maxWeight) {
		this.maxWeight = maxWeight;
		this.minWeight = minWeight;
	}
	
	public static void initialise(List<Layer> currentLayers, List<Layer> initialLayers){
		int i = 0;
		for (Layer layerInitial : initialLayers) {
			if( i == 0){
				i++;
				continue;
			}
			Layer layer = currentLayers.get(i);
			layer.setW(layerInitial.getW());
			layer.setB(layerInitial.getB());
			i++;
		}
	}

	public void backprop(int max_it, double min_error, double alfa,
			Pattern pattern, int numNeuronsHidden, MultilayerPerceptronNew initial) {
		MultilayerPerceptronNew lMultilayerPerceptron = getMultilayerPerceptronTangenteOneHidden(
				numNeuronsHidden, pattern.getD().length,
				pattern.getX()[0].length, minWeight, maxWeight);
		
		if(initial != null){
			initialise(lMultilayerPerceptron.getLayers(), initial.getLayers());
		}
		
		Random random = new Random(System.currentTimeMillis());
		log.fine("inicio treinamento");
		double erro = 121221;
		
		for (int i = 0; i < max_it && min_error< erro ; i++) {

			int index = 0;
			Set<Integer> indexes = new LinkedHashSet<Integer>(
					pattern.getD().length);
			
			erro = 0;

			while (indexes.size() < pattern.getD().length) {

				int aux = indexes.size();
				index = random.nextInt(pattern.getD().length);
				indexes.add(index);
				if (aux == indexes.size()) {
					continue;
				}
				
				Matrix[] Y = new Matrix[lMultilayerPerceptron.getLayers().size()];
				Matrix[] sensitivity = new Matrix[lMultilayerPerceptron.getLayers().size()];
				
				Y[0] = pattern.getXMatrix()[index];
				
				for (int j = 1; j < Y.length; j++) {
					Layer layer = lMultilayerPerceptron.getLayers().get(j);					
					Y[j] = new Matrix(layer.run(Y[j-1].getColumnPackedCopy())); 
				}
				
				int m = Y.length -1;
				Matrix dMtr = pattern.getDMatrix()[index];
				Matrix eMtr = dMtr.minus(Y[m]);
				sensitivity[m] = getF(lMultilayerPerceptron.getLayers().get(m)).times(eMtr).times(-2d);
				//sensitivity[m] = sensitivity[m].times(eMtr);
				
				for (int j = m-1; j > 0; j--) {
					sensitivity[j] = getF(lMultilayerPerceptron.getLayers().get(j)).times(lMultilayerPerceptron.getLayers().get(j+1).getW().transpose()).times(sensitivity[j+1]);
				}
				
				for (int j = 1; j < sensitivity.length; j++) {
					Layer layer = lMultilayerPerceptron.getLayers().get(j);
					layer.addDeltaW(sensitivity[j].times(Y[j-1].transpose()).times(-1d*alfa));
					layer.addDeltaB(sensitivity[j].times(-1d*alfa));
				}
				
				erro += eMtr.transpose().times(eMtr).getArray()[0][0];
				
			}
			erro = erro / indexes.size() * pattern.getD().length;
			log.fine(" erro = "+erro);
			log.fine(indexes.toString());
		}
		log.fine("fim treinamento");
		this.setLayers(lMultilayerPerceptron.getLayers());
	}
	
	public double[][] run(Pattern pattern, int index){
		Matrix[] Y = new Matrix[getLayers().size()];
		Y[0] = pattern.getXMatrix()[index];
		for (int j = 1; j < Y.length; j++) {
			Layer layer = getLayers().get(j);
			Y[j] = new Matrix(layer.run(Y[j-1].getColumnPackedCopy())); 
		}
		
		double retorno[][] = Y[Y.length-1].getArray();
		for (int j2 = 0; j2 < retorno.length; j2++) {
			if (retorno[j2][0] <= 0.0) {
				retorno[j2][0] = -1.0;
			} else if (retorno[j2][0] > 0.0) {
				retorno[j2][0] = 1.0;
			}
		}
		
		return retorno;
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

	public static MultilayerPerceptronNew getMultilayerPerceptronTangenteOneHidden(
			int numNeuronsHidden, int numNeuronsOut, int weights,
			double minWeight, double maxWeight) {
		Random lRandom = new Random(System.currentTimeMillis());
		MultilayerPerceptronNew retorno = new MultilayerPerceptronNew(
				minWeight, maxWeight);
		retorno.setLayers(new ArrayList<Layer>(3));
		retorno.getLayers().add(
				new Layer(new ArrayList<Neuron>(numNeuronsHidden), minWeight,
						maxWeight));
		retorno.getLayers().add(
				new Layer(new ArrayList<Neuron>(numNeuronsHidden), minWeight,
						maxWeight));
		retorno.getLayers().add(
				new Layer(new ArrayList<Neuron>(numNeuronsOut), minWeight,
						maxWeight));

		mountLayer(numNeuronsHidden, retorno.getLayers().get(1).getNeurons(),
				weights, lRandom, minWeight, maxWeight,
				TangenteHiperbolica.class);

		mountLayer(numNeuronsOut, retorno.getLayers().get(2).getNeurons(),
				numNeuronsHidden, lRandom, minWeight, maxWeight,
				TangenteHiperbolica.class);

		return retorno;
	}

	@SuppressWarnings("unchecked")
	private static void mountLayer(int lenNeuron, List<Neuron> neurons,
			int weights, Random random, double minWeight, double maxWeight,
			Class functionClass) {
		for (int i = 0; i < lenNeuron; i++) {
			double[] weigth = new double[weights];

			for (int j = 0; j < weights; j++) {
				weigth[j] = randomValueInRange(random, minWeight, maxWeight);
			}
			Neuron neuron = new Neuron(weigth, randomValueInRange(random,
					minWeight, maxWeight), (RnaFunction) Util
					.createObject(functionClass.getName()));
			
			neurons.add(neuron);
		}
	}

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

	public List<Layer> getLayers() {
		return layers;
	}

	public void setLayers(List<Layer> layers) {
		this.layers = layers;
	}

	public static double randomValueInRange(Random random, double min,
			double max) {
		return Util.randomValueInRange(random, min, max);
	}
}
