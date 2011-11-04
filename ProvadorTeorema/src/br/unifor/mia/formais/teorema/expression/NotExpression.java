package br.unifor.mia.formais.teorema.expression;

import java.util.logging.Level;
import java.util.logging.Logger;

public class NotExpression implements Expression {

	private static Logger log = Logger.getLogger(AndExpression.class.getName());
	
	protected Expression unique;
	public Expression getUnique() {
		return unique;
	}

	protected Integer id;
	
	public NotExpression (Expression unique){
		this.unique = unique;
	}
	
	@Override
	public Boolean evaluate() {
		try{
			return !unique.evaluate();
		}catch(NullPointerException e){
			log.log(Level.FINEST, e.getMessage());
			return null;
		}
	}

	@Override
	public void setValue(Boolean value) {
		unique.setValue(!value);
	}
	
	@Override
	public String toString(){
		return getConnective()+"("+unique.toString()+")";
	}
	
	public String getConnective(){
		return "!";
	}
	
	@Override
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Override
	public Integer getId() {
		return id;
	}
	
	
}
