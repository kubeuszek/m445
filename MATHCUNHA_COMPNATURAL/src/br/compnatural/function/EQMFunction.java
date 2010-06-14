package br.compnatural.function;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;

import Jama.Matrix;
import br.compnatural.State;
import br.compnatural.coordinate.Coordinate;
import br.compnatural.coordinate.RealCoordinate;
import br.compnatural.rna.Layer;
import br.compnatural.rna.Neuron;
import br.compnatural.rna.network.MultilayerPerceptronNew;

public class EQMFunction implements MathFunction {
	private State g;
	final Boolean max;
	public int hiddenNeurons;

	public EQMFunction(Boolean max, int hidden) {
		this.max = max;
		this.hiddenNeurons = hidden;
		g = State.getState();
		g.setValue(0d);
	}

	@Override
	public Double eval(State state) {
		MultilayerPerceptronNew lMultilayerPerceptronNew  = buildPerceptron(state);
		BigDecimal retorno = new BigDecimal(0d);
		
		for (int i = 0 ; i < state.getX().getD().length; ++i) {
			Matrix y = new Matrix(lMultilayerPerceptronNew.run(state.getX(), i));
			Matrix erro = y.minus(state.getX().getDMatrix()[i]);
			
			retorno = retorno.add(new BigDecimal(erro.times(erro.transpose()).getArray()[0][0]));
		}
		
		retorno = retorno.divide(new BigDecimal(state.getX().getD().length * state.getX().getD().length, MathContext.DECIMAL128));

		if (max) {
			retorno = retorno.negate();
		}

		return retorno.doubleValue();
	}
	
	public static State buildState(MultilayerPerceptronNew multilayerPerceptronNew){
		
		List<Coordinate> coordinates = new ArrayList<Coordinate>();
		int i = 0;
		
		for (Layer layer : multilayerPerceptronNew.getLayers()) {
			if(i == 0){
				i++;
				continue;
			}
			for (Neuron neuron : layer.getNeurons()) {
				RealCoordinate coordinate = new RealCoordinate("nBi", multilayerPerceptronNew.getMinWeight(), multilayerPerceptronNew.getMaxWeight());
				coordinate.setValue(neuron.getBias());
				coordinates.add(coordinate);
				
				
				for (int j = 0; j < neuron.getWeights().length; j++) {
					coordinate = new RealCoordinate("nWe", multilayerPerceptronNew.getMinWeight(), multilayerPerceptronNew.getMaxWeight());
					coordinate.setValue(neuron.getWeights()[j]);
					coordinates.add(coordinate);
				}
			}
		}
		return new State(coordinates, false);
	}
	
	public MultilayerPerceptronNew buildPerceptron(State state){
		MultilayerPerceptronNew lMultilayerPerceptronNew =  MultilayerPerceptronNew.getMultilayerPerceptronTangenteOneHidden(hiddenNeurons, state.getX().getD().length, state.getX().getX()[0].length, -1, 1, true);
		
		int i = 0;
		int index = 0;
		for (Layer layer : lMultilayerPerceptronNew.getLayers()) {
			if(i == 0){
				i++;
				continue;
			}
			for (Neuron neuron : layer.getNeurons()) {
				
				neuron.setBias(((RealCoordinate) state.getCoordinate().get(index++)).value);
				for (int j = 0; j < neuron.getWeights().length; j++) {
					RealCoordinate coordinate = (RealCoordinate) state.getCoordinate().get(index++);
					neuron.getWeights()[j] = coordinate.getValue();					
				}
			}
		}

		return lMultilayerPerceptronNew;
	}

	

	@Override
	public String getName() {
		return "Function EQM";
	}

	@Override
	public State getMax() {
		return g;
	}

	@Override
	public Boolean hasMaximum() {
		return Boolean.FALSE;
	}
}
