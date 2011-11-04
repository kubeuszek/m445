package br.unifor.mia.formais.teorema.expression;

import java.util.logging.Level;
import java.util.logging.Logger;

public class AndExpression extends BiExpression {
	
	private static Logger log = Logger.getLogger(AndExpression.class.getName());
	
	public AndExpression (Expression first, Expression second){
		super(first, second);
	}

	@Override
	public Boolean evaluate() {
		Boolean firstValue = first.evaluate();
		Boolean secondValue = second.evaluate();
		
		if(Boolean.FALSE.equals(firstValue) || Boolean.FALSE.equals(secondValue)){
			return Boolean.FALSE;
		}
		
		try{
			return (first.evaluate() && second.evaluate());
		}catch(NullPointerException e){
			log.log(Level.FINEST, e.getMessage());
			return null;
		}
		
	}

	@Override
	public void setValue(Boolean value) {
		Boolean firstValue = first.evaluate();
		Boolean secondValue = second.evaluate();
		
		if (Boolean.TRUE.equals(value)){
			if ( !Boolean.FALSE.equals(firstValue) && !Boolean.FALSE.equals(secondValue)){
				first.setValue(value);
				second.setValue(value);
			}
		}else if(Boolean.FALSE.equals(value)){
			if (firstValue != null && firstValue)
				second.setValue(Boolean.FALSE);
			if(secondValue != null && secondValue)
				first.setValue(Boolean.FALSE);
		}
	}

	@Override
	public String getConnective() {		
		return " AND ";
	}

}
