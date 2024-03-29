package nth.sysmac.user.alarms.generator.dom.sysmac.useralarm.parser.rule.componentcode.skipcolumn.columnrange;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import nth.reflect.util.parser.node.Node;
import nth.reflect.util.parser.node.NodeParser;
import nth.reflect.util.parser.node.ParseTree;
import nth.reflect.util.parser.token.parser.Token;
import nth.reflect.util.parser.token.parser.TokenParser;
import nth.sysmac.user.alarms.generator.dom.sysmac.useralarm.parser.rule.NodeParserRules;
import nth.sysmac.user.alarms.generator.dom.sysmac.useralarm.parser.rule.braces.BracedAttributeName;
import nth.sysmac.user.alarms.generator.dom.sysmac.useralarm.parser.rule.componentcode.ComponentCodeNode;
import nth.sysmac.user.alarms.generator.dom.sysmac.useralarm.token.rule.TokenRules;
import nth.sysmac.user.alarms.generator.dom.testobject.ExpressionAndNodes;
import nth.sysmac.user.alarms.generator.dom.testobject.TestObjectFactory;

class SkipMaxColumnRuleTest {

	private static final int PAGE_NUMBER = 31;

	@RepeatedTest(30)
	void test_givenValidExpression_returnValidParseTree() {
		ExpressionAndNodes attributeValue = new SkipMaxColumnTestAttributeFactory().create();
		ExpressionAndNodes attribute = TestObjectFactory
				.bracedAttributeWithValueSurroundedByRandomValues(BracedAttributeName.SKIP, attributeValue);
		ExpressionAndNodes braced = TestObjectFactory.braceNode(attribute);
		ExpressionAndNodes expressionAndNodes = TestObjectFactory.surroundWithRandomTokens(braced);

		TokenParser tokenParser = new TokenParser(TokenRules.all());
		String expression = expressionAndNodes.expression();
		List<Token> tokens = tokenParser.parse(expression);
		NodeParser nodeParser = new NodeParser(NodeParserRules.all());
		ParseTree parseTree = nodeParser.parse(tokens);
		List<Node> actual = parseTree.getNodes();
		List<Node> parcedNodes = expressionAndNodes.parcedNodes();
		assertThat(actual).as("expression=%s", expression).containsExactlyElementsOf(parcedNodes);
	}

	@Test
	void testAppliesTo_givenSkipColumn3_givenComponentCodeWithColumn2_returnTrue() {
		String expression = createSkipMaxColumnAttributeExpression(3);
		ComponentCodeNode componentCode = new ComponentCodeNode(PAGE_NUMBER, 'U', 2);
		SkipColumnRangeNode skipColumnRangeNode = parseColumnRangeNode(expression);

		assertThat(skipColumnRangeNode.appliesTo(componentCode)).isTrue();
	}

	@Test
	void testAppliesTo_givenSkipColumn3_givenComponentCodeWithColumn3_returnTrue() {
		String expression = createSkipMaxColumnAttributeExpression(3);
		ComponentCodeNode componentCode = new ComponentCodeNode(PAGE_NUMBER, 'U', 3);
		SkipColumnRangeNode skipColumnRangeNode = parseColumnRangeNode(expression);

		assertThat(skipColumnRangeNode.appliesTo(componentCode)).isTrue();
	}

	@Test
	void testAppliesTo_givenSkipColumn3_givenComponentCodeWithColumn4_returnFalse() {
		String expression = createSkipMaxColumnAttributeExpression(3);
		ComponentCodeNode componentCode = new ComponentCodeNode(PAGE_NUMBER, 'U', 4);
		SkipColumnRangeNode skipColumnRangeNode = parseColumnRangeNode(expression);

		assertThat(skipColumnRangeNode.appliesTo(componentCode)).isFalse();
	}

	@Test
	void testGoToNext_givenSkipColumn3_givenComponentCodeWithColumn3_returnColumn4() {
		String expression = createSkipMaxColumnAttributeExpression(3);
		ComponentCodeNode componentCode = new ComponentCodeNode(PAGE_NUMBER, 'U', 3);
		SkipColumnRangeNode skipColumnRangeNode = parseColumnRangeNode(expression);
		skipColumnRangeNode.goToNext(componentCode);

		assertThat(componentCode)//
				.hasFieldOrPropertyWithValue("page", PAGE_NUMBER).hasFieldOrPropertyWithValue("column", 4);
	}

	private SkipColumnRangeNode parseColumnRangeNode(String expression) {
		TokenParser tokenParser = new TokenParser(TokenRules.all());
		List<Token> tokens = tokenParser.parse(expression);
		NodeParser nodeParser = new NodeParser(NodeParserRules.all());
		ParseTree parseTree = nodeParser.parse(tokens);
		Node braced = parseTree.getNodes().get(0);
		Node bracedAttribute = braced.getNodes().get(0);
		Optional<Node> found = bracedAttribute.getNodes().stream().filter(n->n instanceof SkipColumnRangeNode).findFirst();
		SkipColumnRangeNode skipColumnRangeNode = (SkipColumnRangeNode) found.get();
		return skipColumnRangeNode;
	}

	private String createSkipMaxColumnAttributeExpression(int columnNumber) {
		ExpressionAndNodes attributeValue = new SkipMaxColumnTestAttributeFactory().create(columnNumber);
		ExpressionAndNodes attribute = TestObjectFactory.bracedAttribute(BracedAttributeName.SKIP, attributeValue);
		ExpressionAndNodes expressionAndNodes = TestObjectFactory.braceNode(attribute);
		return expressionAndNodes.expression();
	}

}
