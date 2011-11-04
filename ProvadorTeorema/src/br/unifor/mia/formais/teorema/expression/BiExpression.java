package br.unifor.mia.formais.teorema.expression;

public abstract class BiExpression implements Expression {
	
	protected Expression first;
	protected Expression second;
	protected Integer id;
	
	public BiExpression (Expression first, Expression second){
		this.first = first;
		this.second = second;
	}

	@Override
	public abstract Boolean evaluate() ;

	@Override
	public abstract void setValue(Boolean value) ;
	
	public Expression[] split() {		
		return new Expression []{first, second};
	}
	
	public abstract String getConnective();
	
	public String toString(){
		return "("+first.toString() +getConnective()+second.toString()+")";
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
