package br.compnatural.function;

import java.math.BigDecimal;

import br.compnatural.State;
import br.compnatural.Specification.Coordinate;

public class FunctionSumPow implements MathFunction {

	private State g;
	final Boolean max;
	
	public FunctionSumPow (Boolean max){
		this.max = max;
		g = State.getState();
		g.setValue(0);
	}
	
	private BigDecimal sum(State state){
		
		BigDecimal retorno = null;
		int i =1;
		for (Coordinate coordinate : state.getCoordinate()) {
			if(retorno == null){
				retorno = sumUnit(coordinate, i);
			}else{
				retorno = retorno.add(sumUnit(coordinate, i));
			}
			++i;
			
		}
		return retorno;
	}

	private BigDecimal sumUnit(Coordinate coordinate, int i) {
		return new BigDecimal(Math.pow(Math.abs(coordinate.getValue()), i + 1));
	}
	
	@Override
	public Double eval(State state) {
		BigDecimal retorno = sum(state);
		
		if(max){
			retorno = retorno.negate();
		}
		
		return retorno.doubleValue();
	}

	@Override
	public State getMax() {
		return g;
	}

	@Override
	public String getName() {
		return "Função Sum Pow";
	}
	
	@Override
	public Boolean hasMaximum() {
		return Boolean.FALSE;
	}

}
