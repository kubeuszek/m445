package br.unifor.mia.formais.teorema.test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import br.unifor.mia.formais.teorema.Loader;
import br.unifor.mia.formais.teorema.expression.Expression;
import br.unifor.mia.formais.teorema.expression.Variable;
import junit.framework.TestCase;

public class LoaderTeste extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}
	
	
	
	public void testRun(){
		Loader loader = new Loader (new File("C:\\Users\\familia\\Desktop\\outra.txt"));
		
		loader.run1();
	}
	/*
	public void testTratamentoErro() {
		Logger log = Logger.getLogger(getClass().getName());
		Loader loader = new Loader (null);
		Expression expression = loader.fromString("(A | B))", new HashMap<String, Variable>(), new ArrayList<Expression>());
		log.info(expression.toString());
		assertEquals("(A{null} | B{null})", expression.toString());
	}

	public void testFromString() {
		Logger log = Logger.getLogger(getClass().getName());
		Loader loader = new Loader (null);
		Expression expression = loader.fromString("(A | B)", new HashMap<String, Variable>(), new ArrayList<Expression>());
		log.info(expression.toString());
		assertEquals("(A{null} | B{null})", expression.toString());
		
		expression = loader.fromString("(A > B)", new HashMap<String, Variable>(), new ArrayList<Expression>());
		log.info(expression.toString());
		assertEquals("(A{null} > B{null})", expression.toString());
		
		
		expression = loader.fromString("(A & B)", new HashMap<String, Variable>(), new ArrayList<Expression>());
		log.info(expression.toString());
		assertEquals("(A{null} & B{null})", expression.toString());
		
		expression = loader.fromString("!(A)", new HashMap<String, Variable>(), new ArrayList<Expression>());
		log.info(expression.toString());
		assertEquals("!(A{null})", expression.toString());
		
		expression = loader.fromString("((A | B) & (B > !(A)))", new HashMap<String, Variable>(), new ArrayList<Expression>());
		log.info(expression.toString());
		assertEquals("((A{null} | B{null}) & (B{null} > !(A{null})))", expression.toString());
		
		expression = loader.fromString("(((A & B)))", new HashMap<String, Variable>(), new ArrayList<Expression>());
		log.info(expression.toString());
		assertEquals("(A{null} & B{null})", expression.toString());
		
		
		expression = loader.fromString("!(((((A)))))", new HashMap<String, Variable>(), new ArrayList<Expression>());
		log.info(expression.toString());
		assertEquals("!(A{null})", expression.toString());
		
		expression = loader.fromString("((((A & B))) > !(((((A))))))", new HashMap<String, Variable>(), new ArrayList<Expression>());
		log.info(expression.toString());
		assertEquals("((A{null} & B{null}) > !(A{null}))", expression.toString());
		
		expression = loader.fromString("(((((A)))))", new HashMap<String, Variable>(), new ArrayList<Expression>());
		log.info(expression.toString());
		assertEquals("A{null}", expression.toString());
		//(w)
		expression = loader.fromString("(w)", new HashMap<String, Variable>(), new ArrayList<Expression>());
		log.info(expression.toString());
		assertEquals("w{null}", expression.toString());
	}
*/
}
