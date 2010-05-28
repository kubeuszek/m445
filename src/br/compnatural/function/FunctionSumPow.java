package br.compnatural.function;

import java.math.BigDecimal;

import br.compnatural.State;
import br.compnatural.coordinate.Coordinate;
import br.compnatural.coordinate.RealCoordinate;

public class FunctionSumPow implements MathFunction {

	private State g;
	final Boolean max;
	
	public FunctionSumPow (Boolean max){
		this.max = max;
		g = State.getState();
		g.setValue(0d);
	}
	
	private BigDecimal sum(State state){
		
		BigDecimal retorno = null;
		int i =1;
		for (Coordinate coordinate : state.getCoordinate()) {
			if(retorno == null){
				retorno = sumUnit((RealCoordinate)coordinate, i);
			}else{
				retorno = retorno.add(sumUnit((RealCoordinate)coordinate, i));
			}
			++i;
			
		}
		return retorno;
	}

	private BigDecimal sumUnit(RealCoordinate coordinate, int i) {
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
		return "Function Sum Pow";
	}
	
	@Override
	public Boolean hasMaximum() {
		return Boolean.FALSE;
	}

}
