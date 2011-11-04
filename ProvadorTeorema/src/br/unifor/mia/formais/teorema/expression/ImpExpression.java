package br.unifor.mia.formais.teorema.expression;


public class ImpExpression extends BiExpression {
	
	public ImpExpression (Expression first, Expression second){
		super(first, second);
	}

	@Override
	public Boolean evaluate() {
		Boolean firstValue = first.evaluate();
		if(Boolean.FALSE.equals(firstValue)){
			return Boolean.TRUE;
		}else if(Boolean.TRUE.equals(firstValue)){
			Boolean secondValue = second.evaluate();
			return secondValue;
		}else{
			return null;
		}
		
	}	

	@Override
	public void setValue(Boolean value) {
		
		Boolean secondValue = second.evaluate();
		Boolean firstValue = first.evaluate();
		
		if (Boolean.FALSE.equals(value)){
			if (!Boolean.TRUE.equals(secondValue) && !Boolean.FALSE.equals(firstValue)){
				second.setValue(Boolean.FALSE);
				first.setValue(Boolean.TRUE);
			}
		}else if(Boolean.TRUE.equals(value)){			
			if(Boolean.FALSE.equals(secondValue) && !Boolean.TRUE.equals(firstValue)){
				first.setValue(Boolean.FALSE);
			}
			if(Boolean.TRUE.equals(firstValue) && !Boolean.FALSE.equals(secondValue)){
				second.setValue(Boolean.TRUE);
			}
		}
	}

	@Override
	public String getConnective() {		
		return " -> ";
	}

}
