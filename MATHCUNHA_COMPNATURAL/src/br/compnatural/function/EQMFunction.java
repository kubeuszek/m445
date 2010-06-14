package br.compnatural.function;

import java.math.BigDecimal;
import java.math.MathContext;

import Jama.Matrix;
import br.compnatural.State;
import br.compnatural.coordinate.Coordinate;
import br.compnatural.coordinate.RealCoordinate;

public class EQMFunction implements MathFunction {
	private State g;
	final Boolean max;
	
	public int hiddenNeurons;
	public int outNeurons;

	public EQMFunction(Boolean max, int hiddenLen, int outLen) {
		this.max = max;
		this.hiddenNeurons = hiddenLen;
		this.outNeurons = outLen;
		g = State.getState();
		g.setValue(0d);
	}

	@Override
	public Double eval(State state) {
		BigDecimal retorno = sum(state);

		if (max) {
			retorno = retorno.negate();
		}

		return retorno.doubleValue();
	}

	private BigDecimal sum(State state) {

		BigDecimal retorno = new BigDecimal(0);
		Matrix x;
		for (int i = 0; i < state.getX().length; i++) {
			x = state.getX()[i];
			int j = 0;
			double value;
			double[] values = x.getColumnPackedCopy();
			for (j = 0; j < values.length; j++) {
				//retorno = retorno.ad
			}
			for (Coordinate coordinate : state.getCoordinate()) {
				RealCoordinate rCoordinate = (RealCoordinate) coordinate;
				value = values[j++]; 
				
			}
		}
		
		return retorno;
	}

	private BigDecimal sumUnit(RealCoordinate coordinate) {
		return new BigDecimal(Math.pow(coordinate.getValue(), 2));
	}
	
	private BigDecimal multiplyUnit(RealCoordinate coordinate, double value) {
		return new BigDecimal(coordinate.getValue() * value);
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
