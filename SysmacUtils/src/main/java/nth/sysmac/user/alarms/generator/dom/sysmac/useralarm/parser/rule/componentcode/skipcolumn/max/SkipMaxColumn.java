package nth.sysmac.user.alarms.generator.dom.sysmac.useralarm.parser.rule.componentcode.skiprule;

import nth.sysmac.user.alarms.generator.dom.sysmac.useralarm.parser.rule.componentcode.ComponentCodeNode;

public class MaxColumnRule extends ComponentCodeSkipRule {

	@Override
	public boolean appliesTo(ComponentCodeNode componentCodeNode) {
		return componentCodeNode.getColumn()>ComponentCodeNode.COLUMN_MAX;
	}

	@Override
	public void goToNext(ComponentCodeNode componentCodeNode) {
		int nextColumn = ComponentCodeNode.COLUMN_MIN;
		componentCodeNode.setColumn(nextColumn);
		int nextPage = componentCodeNode.getPage()+1;
		componentCodeNode.setPage(nextPage);
	}

	@Override
	protected Object[] getFieldValues() {
		return new Object[0];
	}

}