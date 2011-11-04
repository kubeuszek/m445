package br.unifor.mia.formais.teorema;

import java.io.File;
import java.util.logging.Logger;

import br.unifor.mia.formais.teorema.expression.Expression;
import br.unifor.mia.formais.teorema.expression.ImpExpression;
import br.unifor.mia.formais.teorema.expression.NotExpression;
import br.unifor.mia.formais.teorema.expression.Variable;

public class Resolver {
	
	public static Logger log = Logger.getLogger(Resolver.class.getName());
	
	public static void main(String args[]){
		for (String string : args) {
			Loader loader = new Loader (new File(string));
			loader.run1();
		}		
	}
	
	public void exemploCaderno(){
		Variable A = new Variable(null,"A");		
		Variable B = new Variable(null,"B");
		
		Expression exp1 = new ImpExpression(A,B);
		Expression exp2 = new NotExpression(B);
		Expression exp3 = new ImpExpression(A,exp2);
		//Expression exp4 = new NotExpression(A);
		
		Tableaux t = new Tableaux(A, exp1, exp3);
		
		t.resolve(A,B,exp1,exp2,exp3,A);
	}
	
	
}
