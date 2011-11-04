package br.unifor.mia.formais.teorema.display;
/**
 * DisplayBinaryTree
 * @author Matheus Cunha
 * 
 *
 */


import javax.swing.*;
import javax.swing.tree.*; 

import br.unifor.mia.formais.teorema.Tableaux;
import br.unifor.mia.formais.teorema.Tableaux.TableauxInfo;
import br.unifor.mia.formais.util.tree.BinaryTree;

public class DisplayBinaryTree implements TreeModel {
	private BinaryTree<TableauxInfo> root;
	public DisplayBinaryTree(BinaryTree<TableauxInfo> root){
		this.root = root;
	}

    public Object getRoot()  {
        return root;
    }

    @SuppressWarnings("unchecked")
	public int getChildCount(Object parent)  { 
    	BinaryTree<TableauxInfo> lParent = (BinaryTree<TableauxInfo>)parent;
    	int count = 2;
    	
    	if (Tableaux.DEAD_NODE == lParent.getRightNode() || lParent.getRightNode() == null)
    		count--;
    	
    	if (Tableaux.DEAD_NODE == lParent.getLeftNode() || lParent.getLeftNode() == null)
    		count--;
    	
    	return  count;
    	
    }

    public Object getChild(Object parent, int index) {
    	BinaryTree<TableauxInfo> lParent = (BinaryTree<TableauxInfo>)parent;
        if(0 == index){
        	return lParent.getLeftNode();
        }else{
        	return lParent.getRightNode(); 
        }
    }

    public int getIndexOfChild(Object parent, Object child) {    	
    	
        return parent == child ? 0 : 1;
    }

    public boolean isLeaf(Object node) {
    	
    	BinaryTree<TableauxInfo> lParent = (BinaryTree<TableauxInfo>)node;
    	
        return (lParent.getLeftNode() == null && (lParent.getRightNode() == null || lParent.getRightNode() == Tableaux.DEAD_NODE)); 
    }

    // stubbed out methods not needed for display though for any real
    // application at least the listener methods should be implemented.
    public void addTreeModelListener(javax.swing.event.TreeModelListener l) {}
    public void removeTreeModelListener(javax.swing.event.TreeModelListener l) {}
    public void valueForPathChanged(TreePath path, Object newValue) {}

    /**
     * Creates a JTree from an InfiniteBinaryTree model and displays it.
     */
    public static void display(BinaryTree root)  {
        JTree binTree = new JTree(new DisplayBinaryTree(root));
        binTree.setShowsRootHandles(true);
        JFrame frame = new JFrame("Binary Tree - Tableaux");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new JScrollPane(binTree));
        frame.setSize(new java.awt.Dimension(400, 400));
        frame.setVisible(true);
    }
}