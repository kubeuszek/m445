package br.unifor.mia.formais.teorema.expression;

public class IFFExpression extends BiExpression {

	public IFFExpression(Expression first, Expression second) {
		super(first, second);		
	}

	@Override
	public Boolean evaluate() {
		Boolean firstValue = first.evaluate();
		Boolean secondValue = second.evaluate();
		if((Boolean.TRUE.equals(firstValue) && Boolean.TRUE.equals(secondValue)) || 
				(Boolean.FALSE.equals(firstValue) && Boolean.FALSE.equals(secondValue))){
			return Boolean.TRUE;
		}else if((Boolean.TRUE.equals(firstValue) && Boolean.FALSE.equals(secondValue)) || 
				(Boolean.FALSE.equals(firstValue) && Boolean.TRUE.equals(secondValue))){
			return Boolean.FALSE;
		}
		return null;
	}

	@Override
	public String getConnective() {
		return " <-> ";
	}

	@Override
	public void setValue(Boolean value) {
		Boolean firstValue = first.evaluate();
		Boolean secondValue = second.evaluate();
		if(Boolean.TRUE.equals(value)){
			if(Boolean.TRUE.equals(firstValue) && !Boolean.FALSE.equals(secondValue)){
				second.setValue(Boolean.TRUE); 
			}else if(Boolean.FALSE.equals(firstValue) && !Boolean.TRUE.equals(secondValue)){
				second.setValue(Boolean.FALSE); 
			}else if(Boolean.TRUE.equals(secondValue) && !Boolean.FALSE.equals(firstValue)){
				first.setValue(Boolean.TRUE); 
			}else if(Boolean.FALSE.equals(secondValue) && !Boolean.TRUE.equals(firstValue)){
				first.setValue(Boolean.FALSE); 
			}
		}else if(Boolean.FALSE.equals(value)){
			if(Boolean.TRUE.equals(firstValue) && !Boolean.TRUE.equals(secondValue)){
				second.setValue(Boolean.FALSE); 
			}else if(Boolean.FALSE.equals(firstValue) && !Boolean.FALSE.equals(secondValue)){
				second.setValue(Boolean.TRUE); 
			}else if(Boolean.TRUE.equals(secondValue) && !Boolean.TRUE.equals(firstValue)){
				first.setValue(Boolean.FALSE); 
			}else if(Boolean.FALSE.equals(secondValue) && !Boolean.FALSE.equals(firstValue)){
				first.setValue(Boolean.TRUE); 
			}
		}
	}
}
