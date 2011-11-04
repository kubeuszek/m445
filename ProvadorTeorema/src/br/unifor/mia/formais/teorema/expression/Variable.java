package br.unifor.mia.formais.teorema.expression;

import java.util.logging.Logger;

public class Variable implements Expression {
	private static Logger log = Logger.getLogger(Variable.class.getName());
	protected Boolean variable;
	protected String name;
	protected Integer id;
	
	public String getName() {
		return name;
	}

	public Variable(Boolean value, String name){
		variable = value;
		this.name = name;
	}

	@Override
	public Boolean evaluate() {		
		return variable;
	}

	@Override
	public void setValue(Boolean value) {
		if(variable == null)
			variable = value;
		else if(!value.equals(variable)){
			log.info("Valor de ["+name+"] não pode ser modificado");			
		}
	}
	
	public String toString(){
		return name;//+"{"+variable+"}";
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
