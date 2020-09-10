package nth.sysmac.user.alarms.generator.dom.sysmac.useralarm.expression.node;

import java.util.List;

import nth.sysmac.user.alarms.generator.dom.sysmac.useralarm.expression.node.matcher.MatchResult;
import nth.sysmac.user.alarms.generator.dom.sysmac.useralarm.expression.token.Token;

/**
 * A {@link NodeParser} creates a {@link ParseTree} and then combines
 * {@link Node} children by replacing them with other more detailed
 * {@link Node}s as is defined by {@link NodeRule}s.
 * 
 * @author nilsth
 *
 */
public class NodeParser {

	private final List<NodeRule<? extends Node>> nodeRules;

	public NodeParser(List<NodeRule<? extends Node>> nodeReplacements) {
		this.nodeRules = nodeReplacements;
	}

	public ParseTree parse(List<Token> tokens) {
		ParseTree parseTree = new ParseTree(tokens);

		replaceAll(parseTree);

		return parseTree;
	}

	private void replaceAll(ParseTree parseTree) {
		List<Node> children = parseTree.getChildren();
		boolean doneReplacement = false;
		do {
			for (NodeRule<? extends Node> nodeReplacement : nodeRules) {
				doneReplacement = replaceAllInChildren(children, nodeReplacement);
			}
		} while (doneReplacement);
	}

	private boolean replaceAllInChildren(List<Node> children, NodeRule<? extends Node> nodeReplacement) {
		boolean foundReplacement = false;
		boolean doneReplacement = false;
		do {
			foundReplacement = false;
			MatchResult matchResult = nodeReplacement.find(children);
			if (matchResult.found()) {
				foundReplacement = true;
				Node replacementNode = nodeReplacement.createReplacement(matchResult);
				matchResult.replaceFoundNodesWith(replacementNode);
				doneReplacement = true;
			}
		} while (foundReplacement);

		doneReplacement = doneReplacement || replaceAllInChildrenRecursively(children, nodeReplacement);
		return doneReplacement;
	}

	private boolean replaceAllInChildrenRecursively(List<Node> children, NodeRule<? extends Node> nodeReplacement) {
		boolean doneReplacement = false;
		for (Node child : children) {
			doneReplacement = replaceAllInChildren(child.getChildren(), nodeReplacement);
		}
		return doneReplacement;
	}
}