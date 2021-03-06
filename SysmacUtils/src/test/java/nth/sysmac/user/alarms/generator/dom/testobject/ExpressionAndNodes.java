package nth.sysmac.user.alarms.generator.dom.testobject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nth.reflect.util.parser.node.Node;
import nth.reflect.util.parser.node.TokenNode;
import nth.reflect.util.parser.token.parser.TokenRule;
import nth.reflect.util.random.Random;

public class ExpressionAndNodes {

	private final String expression;
	private final List<TokenNode> tokenNodes;
	private final List<Node> parsedNodes;

	public ExpressionAndNodes() {
		this.expression = "";
		this.tokenNodes= new ArrayList<>();
		this.parsedNodes = new ArrayList<>();
	}

	
	public ExpressionAndNodes(String expression, List<TokenNode> tokenNodes, List<Node> parsedNodes) {
		this.expression = expression;
		this.tokenNodes= tokenNodes;
		this.parsedNodes = parsedNodes;
	}

	public ExpressionAndNodes(String expression, TokenRule tokenRule) {
		this.expression=expression;
		TokenNode tokenNode = new TokenNode( tokenRule, expression);
		this.tokenNodes=Arrays.asList(tokenNode);
		this.parsedNodes=new ArrayList<>();
		parsedNodes.add(tokenNode);
	}


	/**
	 * creates a copy
	 * @param expressionAndNodes
	 */
	public ExpressionAndNodes(ExpressionAndNodes expressionAndNodes) {
		this.expression=expressionAndNodes.expression;
		this.tokenNodes=new ArrayList<>(expressionAndNodes.tokenNodes);
		this.parsedNodes=new ArrayList<>(expressionAndNodes.parsedNodes);
	}


	public String expression() {
		return expression;
	}

	public List<Node> parcedNodes() {
		return parsedNodes;
	}
	public List<TokenNode> tokenNodes() {
		return tokenNodes;
	}
	
	
	public ExpressionAndNodes append(ExpressionAndNodes appendix) {
		String newExpression = expression+appendix.expression();
		List<TokenNode> newTokenNodes = new ArrayList<>();
		newTokenNodes.addAll(tokenNodes);
		newTokenNodes.addAll(appendix.tokenNodes);
		List<Node> newParcedNodes = new ArrayList<>();
		newParcedNodes.addAll(parsedNodes);
		newParcedNodes.addAll(appendix.parsedNodes);

		return new ExpressionAndNodes(newExpression, newTokenNodes, newParcedNodes);
	}

	public ExpressionAndNodes repeatRandomly(int min, int max) {
		ExpressionAndNodes result=new ExpressionAndNodes();
		Integer repetition = Random.integer().forRange(min, max).generate();
		for (int i=0;i<repetition;i++) {
			result=result.append(this);
		}
		return result;
	}

	
	

}
