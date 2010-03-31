package br.compnatural.function;

import java.math.BigDecimal;
import java.math.MathContext;

import br.compnatural.State;
import br.compnatural.Specification.Coordinate;

public class FunctionGriewank implements MathFunction {

	final BigDecimal um = new BigDecimal(1);
	final BigDecimal quatro_mill = new BigDecimal(4000);
	private State g;
	final Boolean max;
	
	public FunctionGriewank (Boolean max){
		this.max = max;
		g = State.getState();
		g.setValue(0);
	}
	
	@Override
	public Double eval(State state) {
		BigDecimal retorno = sum(state).divide(quatro_mill, MathContext.DECIMAL128);
		
		retorno = um.add(retorno).subtract(multiply(state));
		
		if(max){
			retorno = retorno.negate();
		}
		
		return retorno.doubleValue();
	}
	
	private BigDecimal sum(State state){
		
		BigDecimal retorno = null;
		for (Coordinate coordinate : state.getCoordinate()) {
			if(retorno == null){
				retorno = sumUnit(coordinate);
			}else{
				retorno = retorno.add(sumUnit(coordinate));
			}
			
		}
		return retorno;
	}

	private BigDecimal sumUnit(Coordinate coordinate) {
		return new BigDecimal(Math.pow(coordinate.getValue(), 2));
	}
	
	private BigDecimal multiplyUnit(Coordinate coordinate, int i) {
		return new BigDecimal(Math.cos(coordinate.getValue()/ Math.sqrt(i)));
	}
	
	private BigDecimal multiply(State state){
		BigDecimal retorno = null;
		int i = 1;
		for (Coordinate coordinate : state.getCoordinate()) {
			if(retorno == null){
				retorno = multiplyUnit(coordinate, i);
			}else{
				retorno = retorno.multiply((multiplyUnit(coordinate, i)));
			}
			
			++i;
		}
		return retorno;
	}

	@Override
	public String getName() {
		return "Função Griewank";
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
