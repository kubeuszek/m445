package br.unifor.mia.formais.teorema;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.unifor.mia.formais.teorema.expression.AndExpression;
import br.unifor.mia.formais.teorema.expression.Expression;
import br.unifor.mia.formais.teorema.expression.IFFExpression;
import br.unifor.mia.formais.teorema.expression.ImpExpression;
import br.unifor.mia.formais.teorema.expression.NotExpression;
import br.unifor.mia.formais.teorema.expression.OrExpression;
import br.unifor.mia.formais.teorema.expression.Variable;
import br.unifor.mia.formais.util.tree.BinaryTree;

public class Tableaux {

	private static Logger log = Logger.getLogger(Tableaux.class.getName());

	private Expression conclusion;
	private Expression hypothesis[];

	public Tableaux(Expression conclusion, Expression... hypothesis) {
		this.conclusion = conclusion;
		this.hypothesis = hypothesis;
	}

	public Expression mountHypothesis(int indice) {
		try {
			if ((indice + 1) < hypothesis.length) {
				AndExpression retorno = new AndExpression(hypothesis[indice],
						mountHypothesis(++indice));
				retorno.setId(indice * (-1));
				return retorno;
			} else {
				return hypothesis[indice];
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			log.log(Level.SEVERE, "Arquivo não possui nenhuma hipótese", e);
			throw e;
		}
	}

	private BinaryTree<TableauxInfo> ifValue(ImpExpression expression,
			Boolean value) {
		BinaryTree<TableauxInfo> retorno = null;

		if (Boolean.TRUE.equals(value)) {
			Expression[] expressions = expression.split();
			retorno = new BinaryTree<TableauxInfo>(null);
			retorno.setLeftNode(new BinaryTree<TableauxInfo>(
					new TableauxInfo(expressions[0], Boolean.FALSE)));
			retorno.setRightNode(new BinaryTree<TableauxInfo>(
					new TableauxInfo(expressions[1], Boolean.TRUE)));
			
			retorno.getLeftNode().setRoot(retorno);
			retorno.getRightNode().setRoot(retorno);
			
		} else if (Boolean.FALSE.equals(value)) {
			Expression[] expressions = expression.split();
			retorno = new BinaryTree<TableauxInfo>(new TableauxInfo(
					expressions[0], Boolean.TRUE));
			retorno.setLeftNode(new BinaryTree<TableauxInfo>(
					new TableauxInfo(expressions[1], Boolean.FALSE)));
			
			retorno.getLeftNode().setRoot(retorno);
			retorno.setRightNode(DEAD_NODE);
		}

		return retorno;
	}
	
	
	private BinaryTree<TableauxInfo> ifValue(NotExpression expression,
			Boolean value) {

		return new BinaryTree<TableauxInfo>(new TableauxInfo(
				expression.getUnique(), !value));
	}

	private BinaryTree<TableauxInfo> ifValue(AndExpression expression,
			Boolean value) {
		BinaryTree<TableauxInfo> retorno = null;

		if (Boolean.TRUE.equals(value)) {
			Expression[] expressions = expression.split();
			retorno = new BinaryTree<TableauxInfo>(new TableauxInfo(
					expressions[0], Boolean.TRUE));
			
			retorno.setLeftNode(new BinaryTree<TableauxInfo>(
					new TableauxInfo(expressions[1], Boolean.TRUE)));
			
			retorno.getLeftNode().setRoot(retorno);
			
			retorno.setRightNode(DEAD_NODE);
			
			
		} else if (Boolean.FALSE.equals(value)) {
			Expression[] expressions = expression.split();
			retorno = new BinaryTree<TableauxInfo>(null);
			retorno.setLeftNode(new BinaryTree<TableauxInfo>(
					new TableauxInfo(expressions[0], Boolean.FALSE)));
			
			retorno.getLeftNode().setRoot(retorno);
			
			retorno.setRightNode(new BinaryTree<TableauxInfo>(
					new TableauxInfo(expressions[1], Boolean.FALSE)));
			
			retorno.getRightNode().setRoot(retorno);
			
		}

		return retorno;
	}
	
	private BinaryTree<TableauxInfo> ifValue(OrExpression expression,
			Boolean value) {
		BinaryTree<TableauxInfo> retorno = null;

		if (Boolean.FALSE.equals(value)) {
			Expression[] expressions = expression.split();
			retorno = new BinaryTree<TableauxInfo>(new TableauxInfo(
					expressions[0], Boolean.FALSE));
			
			retorno.setLeftNode(new BinaryTree<TableauxInfo>(
					new TableauxInfo(expressions[1], Boolean.FALSE)));
			
			retorno.getLeftNode().setRoot(retorno);
			
			retorno.setRightNode(DEAD_NODE);
			
			
		} else if (Boolean.TRUE.equals(value)) {
			Expression[] expressions = expression.split();
			retorno = new BinaryTree<TableauxInfo>(null);
			retorno.setLeftNode(new BinaryTree<TableauxInfo>(
					new TableauxInfo(expressions[0], Boolean.TRUE)));
			
			retorno.getLeftNode().setRoot(retorno);
			
			retorno.setRightNode(new BinaryTree<TableauxInfo>(
					new TableauxInfo(expressions[1], Boolean.TRUE)));
			
			retorno.getRightNode().setRoot(retorno);
			
		}

		return retorno;
	}
	
	private BinaryTree<TableauxInfo> ifValue(IFFExpression expression,
			Boolean value) {
		BinaryTree<TableauxInfo> retorno = null;

		if (Boolean.FALSE.equals(value)) {
			
			retorno = new BinaryTree<TableauxInfo>(null);
			
			Expression[] expressions = expression.split();
			retorno.setLeftNode( new BinaryTree<TableauxInfo>(new TableauxInfo(
					expressions[0], Boolean.FALSE)));
			
			retorno.getLeftNode().setLeftNode(new BinaryTree<TableauxInfo>(
					new TableauxInfo(expressions[1], Boolean.TRUE)));
			
			retorno.getLeftNode().setRoot(retorno);
			retorno.getLeftNode().getLeftNode().setRoot(retorno.getLeftNode());
			
			retorno.getLeftNode().setRightNode(DEAD_NODE);
			
			retorno.setRightNode(new BinaryTree<TableauxInfo>(new TableauxInfo(
					expressions[0], Boolean.TRUE)));
			
			retorno.getRightNode().setLeftNode(new BinaryTree<TableauxInfo>(
					new TableauxInfo(expressions[1], Boolean.FALSE)));
			
			retorno.getRightNode().setRoot(retorno);
			retorno.getRightNode().getLeftNode().setRoot(retorno.getRightNode());
			
			retorno.getRightNode().setRightNode(DEAD_NODE);
			
			
			
		} else if (Boolean.TRUE.equals(value)) {
			retorno = new BinaryTree<TableauxInfo>(null);
			
			Expression[] expressions = expression.split();
			retorno.setLeftNode( new BinaryTree<TableauxInfo>(new TableauxInfo(
					expressions[0], Boolean.FALSE)));
			
			retorno.getLeftNode().setLeftNode(new BinaryTree<TableauxInfo>(
					new TableauxInfo(expressions[1], Boolean.FALSE)));
			
			retorno.getLeftNode().setRoot(retorno);
			retorno.getLeftNode().getLeftNode().setRoot(retorno.getLeftNode());
			
			retorno.getLeftNode().setRightNode(DEAD_NODE);
			
			retorno.setRightNode(new BinaryTree<TableauxInfo>(new TableauxInfo(
					expressions[0], Boolean.TRUE)));
			
			retorno.getRightNode().setLeftNode(new BinaryTree<TableauxInfo>(
					new TableauxInfo(expressions[1], Boolean.TRUE)));
			
			retorno.getRightNode().setRoot(retorno);
			retorno.getRightNode().getLeftNode().setRoot(retorno.getRightNode());
			
			retorno.getRightNode().setRightNode(DEAD_NODE);
			
		}

		return retorno;
	}

	private BinaryTree<TableauxInfo> ifValue(Expression expression,
			Boolean value) {
		BinaryTree<TableauxInfo> retorno = null;

		if (expression instanceof ImpExpression) {
			return ifValue((ImpExpression) expression, value);
		} else if (expression instanceof AndExpression) {
			return ifValue((AndExpression) expression, value);
		} else if (expression instanceof OrExpression) {
			return ifValue((OrExpression) expression, value);
		} else if (expression instanceof IFFExpression) {
			return ifValue((IFFExpression) expression, value);
		} else if (expression instanceof NotExpression) {
			return ifValue((NotExpression)expression, value);
		}

		return retorno;
	}

	public BinaryTree<TableauxInfo> tree() {
		Expression lExpression = mountHypothesis(0);		
		lExpression = new ImpExpression(lExpression, conclusion);
		lExpression.setId((hypothesis.length + 2) *(-1));
		
		BinaryTree<TableauxInfo> root = new BinaryTree<TableauxInfo>(new TableauxInfo(lExpression, Boolean.FALSE));
		root.setRightNode(DEAD_NODE);
		BinaryTree<TableauxInfo> node = root;
		BinaryTree<TableauxInfo> newNode = null;
		BinaryTree<TableauxInfo> leafNode = null;
		
		
		while(node != null){
			//printTree(root);
			
			List<BinaryTree<TableauxInfo>> list = new ArrayList<BinaryTree<TableauxInfo>>();
			int index = 0;
						
			leafNodes(node, list);
			
			if(index < list.size()){
				leafNode = list.get(index++);
			}
			
			if(list.size() != 0){
				
				while(leafNode != null){
					newNode =  ifValue(node.getOperator().expression, node.getOperator().value);
					
					node.getOperator().evaluated = Boolean.TRUE;
					
					if(testAlfa(newNode)){
						newNode.setRoot(leafNode);
						Boolean sit = situation(newNode);
						newNode.getOperator().contradiction = sit;
						
						evaluateVariable(newNode);
						
						if(newNode.getLeftNode() != null){
							sit = situation(newNode.getLeftNode());
							newNode.getLeftNode().getOperator().contradiction = sit;
							evaluateVariable(newNode.getLeftNode());
							
							if(!Boolean.TRUE.equals(newNode.getLeftNode().getOperator().contradiction) && Boolean.TRUE.equals(newNode.getOperator().contradiction)){
								TableauxInfo aux = newNode.getLeftNode().getOperator();
								newNode.getLeftNode().setOperator(newNode.getOperator());
								newNode.setOperator(aux);
							}
						}
						
						if(leafNode.getRightNode() == null){
							leafNode.setRightNode(DEAD_NODE);
						}
						
						leafNode.setLeftNode(newNode);
					}else{
						
						leafNode.setLeftNode(newNode.getLeftNode());
						newNode.getLeftNode().setRoot(leafNode);
						
						Boolean sit = situation(newNode.getLeftNode());
						newNode.getLeftNode().getOperator().contradiction = sit;
						
						evaluateVariable(newNode.getLeftNode());
						
						leafNode.setRightNode(newNode.getRightNode());
						newNode.getRightNode().setRoot(leafNode);
						
						sit = situation(newNode.getRightNode());
						newNode.getRightNode().getOperator().contradiction = sit;
						
						evaluateVariable(newNode.getRightNode());
						
						if(newNode.getLeftNode().getLeftNode() != null){
							sit = situation(newNode.getLeftNode().getLeftNode());
							newNode.getLeftNode().getLeftNode().getOperator().contradiction = sit;
							evaluateVariable(newNode.getLeftNode().getLeftNode());
							
							if(!Boolean.TRUE.equals(newNode.getLeftNode().getLeftNode().getOperator().contradiction) && Boolean.TRUE.equals(newNode.getLeftNode().getOperator().contradiction)){
								TableauxInfo aux = newNode.getLeftNode().getLeftNode().getOperator();
								newNode.getLeftNode().getLeftNode().setOperator(newNode.getLeftNode().getOperator());
								newNode.getLeftNode().setOperator(aux);
							}
						}
						
						if(newNode.getRightNode().getLeftNode() != null){
							sit = situation(newNode.getRightNode().getLeftNode());
							newNode.getRightNode().getLeftNode().getOperator().contradiction = sit;
							evaluateVariable(newNode.getRightNode().getLeftNode());
							
							if(!Boolean.TRUE.equals(newNode.getRightNode().getLeftNode().getOperator().contradiction) && Boolean.TRUE.equals(newNode.getRightNode().getOperator().contradiction)){
								TableauxInfo aux = newNode.getRightNode().getLeftNode().getOperator();
								newNode.getRightNode().getLeftNode().setOperator(newNode.getRightNode().getOperator());
								newNode.getRightNode().setOperator(aux);
							}
						}
						
					}
					leafNode = null;
					if(index < list.size()){
						leafNode = list.get(index++);
					}
				}
				node = expandTree(root);
			}else{
				node = null;
			}
			
		}
		
		printTree(root);
		
		node = expandTree(root);
		if(node == null)
			log.info("\n Não há mais expressões para expandir e ");
		else
			log.info("\n É possível expandir, mas ");
		
		leafNode = nodeMoreLeft(root);
		if(leafNode == null){
			log.info("TODAS as folhas estão em CONTRADIÇÃO com algum nó \n");
		}else{
			log.info("tem pelo menos um caminho sem contradição "+leafNode.getOperator()+" \n");
		}
		
		return root;
	}

	public static void printTree(BinaryTree<TableauxInfo> root) {
		LinkedList<String> list = new LinkedList<String>();
		treeToList(root, 0, list);
		int i = 0;
		for (String string : list) {
			log.info("Nível "+(i++)+" - "+string+"\n");
		}
	}

	private void evaluateVariable(BinaryTree<TableauxInfo> newNode) {
		if(newNode.getOperator().expression instanceof Variable){
			newNode.getOperator().evaluated = Boolean.TRUE;
		}
	}
	
	public static void treeToList (BinaryTree<TableauxInfo> root, int height, List<String> list){
		if(root != null){
			String strValor = null;
			
			if(height < list.size()){
				strValor = list.get(height);
			}else{
				list.add("");
			}
			
			if(strValor == null){
				strValor = "";
			}
			strValor = strValor +" "+root.getOperator();
			list.set(height, strValor);
			treeToList(root.getLeftNode(), height+1, list);
			treeToList(root.getRightNode(), height+1, list);
		}
	}
	
	public String toString (BinaryTree<TableauxInfo> root){
		String retorno = "";
		if(root != null){
			String strRoot = (root.getRoot() != null ? root.getRoot().getOperator().expression.getId().toString() : "");
			retorno = "root["+strRoot+"]" + (root.getOperator() != null? root.getOperator().toString():"DEAD LEAF")+"\n";
			retorno += "ESQ "+toString (root.getLeftNode());
			retorno += "DIR "+toString (root.getRightNode());
		}
		
		return retorno;
	}
	
	public Boolean situation (BinaryTree<TableauxInfo> node ){
		Boolean retorno = null;
		TableauxInfo info = node.getOperator();
		
		while(node.getRoot() != null){
			if (node.getRoot().getOperator().expression.getId().equals(info.expression.getId())){
				if(retorno != null)
					retorno = retorno && !node.getRoot().getOperator().value.equals(info.value);
				else
					retorno = !node.getRoot().getOperator().value.equals(info.value);
			}
			node = node.getRoot();
		}		
		
		return retorno;
	}
	
	private void leafNodes(BinaryTree<TableauxInfo> root, List<BinaryTree<TableauxInfo>> list){
		BinaryTree<TableauxInfo> retorno = null;
		if(root.getLeftNode() == null){
			if(!Boolean.TRUE.equals(root.getOperator().contradiction)){
				retorno = root;
				list.add(root);
			}
		}else{
			if(root.getLeftNode() != DEAD_NODE){
				leafNodes(root.getLeftNode(), list);
			}
		}
		
		if(root.getRightNode() == null){
			if(!Boolean.TRUE.equals(root.getOperator().contradiction)){
				if(retorno == null){
					retorno = root;
					list.add(root);
				}
				
			}
		}else{
			if(root.getRightNode() != DEAD_NODE){
				leafNodes(root.getRightNode(), list);
			}
		}
		
		
	}
	
	private BinaryTree<TableauxInfo> nodeMoreLeft(BinaryTree<TableauxInfo> root){
		BinaryTree<TableauxInfo> retorno = null;
		if(root.getLeftNode() == null){
			if(!Boolean.TRUE.equals(root.getOperator().contradiction)){
				retorno = root;
			}
		}else{
			if(root.getLeftNode() != DEAD_NODE){
				retorno = nodeMoreLeft(root.getLeftNode());
			}
		}
		if(retorno == null){
			if(root.getRightNode() == null){
				if(!Boolean.TRUE.equals(root.getOperator().contradiction)){
					retorno = root;
				}
			}else{
				if(root.getRightNode() != DEAD_NODE){
					retorno = nodeMoreLeft(root.getRightNode());
				}
			}
		}
		return retorno;
	}

	private boolean deadNodeOrContradiction(BinaryTree<TableauxInfo> root) {
		Boolean retorno = Boolean.TRUE.equals(root.getOperator().contradiction);
		retorno = retorno || (root.equals(DEAD_NODE));
		return retorno ; 
	}
	
	private BinaryTree<TableauxInfo> expandTree(BinaryTree<TableauxInfo> root){
		if(root != null && DEAD_NODE != root){
			BinaryTree<TableauxInfo> node = null;
			if (!Boolean.TRUE.equals(root.getOperator().evaluated)){
				node =  ifValue(root.getOperator().expression, root.getOperator().value);
				
				if(testAlfa(node)){//Alfa
					return root;
				}
			}
			BinaryTree<TableauxInfo> leftNode 	= expandTree(root.getLeftNode());			
			
			if(leftNode != null && testAlfa(ifValue(leftNode.getOperator().expression, leftNode.getOperator().value) )){
				return leftNode;
			}
			
			BinaryTree<TableauxInfo> rightNode 	= expandTree(root.getRightNode());
			
			if(rightNode != null && testAlfa(ifValue(rightNode.getOperator().expression, rightNode.getOperator().value) )){
				return rightNode;
			}
			
			if(node != null){
				return root;
			}else if(leftNode != null){
				return leftNode;
			}else{
				return rightNode;
			}
		}
		return null;
	}

	private boolean testAlfa(BinaryTree<TableauxInfo> leftNode) {
		return leftNode.getOperator() != null;
	}

	public void resolve(Expression... expressions) {
		Expression lExpression = mountHypothesis(0);
		lExpression = new ImpExpression(lExpression, conclusion);
		Boolean terminou = false;
		log.info(lExpression.toString());

		Expression components[][] = new Expression[expressions.length][2];
		int i = 0;
		for (Expression expression : expressions) {
			components[i][0] = expression;
			components[i][1] = null;
			++i;
		}

		for (i = 1; i >= 0; i--) {
			log.log(Level.FINEST, "" + i);
			terminou = true;
			for (Expression[] lExpr : components) {
				Boolean valor = lExpr[0].evaluate();
				if (valor != null && (lExpr[1] == null)) {
					if (i == 0)
						i++;

					lExpr[1] = new Variable(valor, "");
				}

				if (terminou && lExpr[1] == null) {
					terminou = Boolean.FALSE;
				}
			}

			if (!terminou)
				lExpression.setValue(Boolean.FALSE);
		}
		if (terminou) {
			log.info("Prova realizada com sucesso");
		} else {
			log.info("Não foi possível provar o teorema");
		}
		log.info(lExpression.evaluate() + " - " + lExpression.toString());
	}
	
	
	public static final BinaryTree<TableauxInfo> DEAD_NODE = new BinaryTree<TableauxInfo> (new TableauxInfo(null, null)); 

	public static class TableauxInfo {
		public Expression expression;
		public Boolean evaluated;
		public Boolean value;
		public Boolean contradiction;

		public TableauxInfo(Expression expression, Boolean value) {
			this.expression = expression;
			this.value = value;
		}
		
		@Override
		public String toString(){
			
			String retorno =  value+"["+(expression != null ? expression.toString() : "DEAD_LEAF")+"]";			
			
			if(Boolean.TRUE.equals(contradiction)){
				retorno += "-CONTRADICAO";
			}
			if(Boolean.TRUE.equals(evaluated)){
				retorno += "*";
			}
			
			if(!Level.FINEST.equals(log.getLevel())){
				if(expression == null){
					retorno = "";
				}
			}
			
			return retorno;
		}
	}
}