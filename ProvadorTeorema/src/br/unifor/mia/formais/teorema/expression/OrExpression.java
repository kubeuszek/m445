package br.unifor.mia.formais.teorema.expression;

import java.util.logging.Level;
import java.util.logging.Logger;

public class OrExpression extends BiExpression {
	
	private static Logger log = Logger.getLogger(AndExpression.class.getName());
	
	public OrExpression (Expression first, Expression second){
		super(first, second);
	}

	@Override
	public Boolean evaluate() {
		Boolean firstValue = first.evaluate();
		Boolean secondValue = second.evaluate();
		
		if(Boolean.TRUE.equals(firstValue) || Boolean.TRUE.equals(secondValue)){
			return Boolean.TRUE;
		}
		try{
			return (firstValue || secondValue);
		}catch(NullPointerException e){
			log.log(Level.FINEST, e.getMessage());
			return null;
		}
		
	}

	@Override
	public void setValue(Boolean value) {
		Boolean firstValue = first.evaluate();
		Boolean secondValue = second.evaluate();
		
		if (Boolean.FALSE.equals(value)){
			if ( !Boolean.TRUE.equals(firstValue) && !Boolean.TRUE.equals(secondValue)){
				first.setValue(value);
				second.setValue(value);
			}
		}else if(Boolean.TRUE.equals(value)){
			if (firstValue != null && !firstValue)
				second.setValue(Boolean.TRUE);
			if(secondValue != null && !secondValue)
				first.setValue(Boolean.TRUE);
		}
	}

	@Override
	public String getConnective() {		
		return " OR ";
	}
}