package br.compnatural.function;

import java.math.BigDecimal;
import br.compnatural.State;
import br.compnatural.coordinate.RealCoordinate;

public class FunctionRosenbrock implements MathFunction {

	final Boolean max;
	private State g;
	
	public FunctionRosenbrock (Boolean max){
		this.max = max;
		g = State.getState();
		g.setValue(0d);
	}

	@Override
	public String getName() {
		return "Function Rosenbrock";
	}

	@Override
	public State getMax() {
		return g;
	}

	@Override
	public Boolean hasMaximum() {
		return Boolean.FALSE;
	}
	
	@Override
	public Double eval(State state) {
		BigDecimal retorno = sum(state);
		
		
		
		if(max){
			retorno = retorno.negate();
		}
		
		return retorno.doubleValue();
	}
	
	private BigDecimal sum(State state){
		
		BigDecimal retorno = null;
		for (int i = 0; i < state.getCoordinate().size() -1 ;++i) {
			RealCoordinate coordinate = (RealCoordinate) state.getCoordinate().get(i);
			RealCoordinate coordinate_i_PLUS = (RealCoordinate) state.getCoordinate().get(i+1);
			
			if(retorno == null){
				retorno = sumUnit(coordinate, coordinate_i_PLUS);
			}else{
				retorno = retorno.add(sumUnit(coordinate, coordinate_i_PLUS));
			}
			
		}
		return retorno;
	}

	private BigDecimal sumUnit(RealCoordinate xi, RealCoordinate xi_1) {
		BigDecimal primeiraParte =  new BigDecimal(100 * Math.pow( Math.pow(xi.getValue(),2) - xi_1.getValue(), 2));
		
		BigDecimal segundaParte =  new BigDecimal(Math.pow( 1 - xi.getValue(), 2));
		
		return primeiraParte.add(segundaParte);
	}
}
