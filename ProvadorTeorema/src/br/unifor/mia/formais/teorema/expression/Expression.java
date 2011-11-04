package br.unifor.mia.formais.teorema.expression;

public interface Expression {
	
	Boolean evaluate();
	
	void setValue(Boolean value);
	
	Integer getId();
	
	void setId(Integer id);
}
