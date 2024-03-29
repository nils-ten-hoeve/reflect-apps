package nth.sysmac.user.alarms.generator.dom.sysmac.useralarm;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nth.sysmac.user.alarms.generator.dom.sysmac.basetype.OmronBaseType;
import nth.sysmac.user.alarms.generator.dom.sysmac.xml.datatype.DataType;

public class GroupNames extends ArrayList<String> {

	private static final long serialVersionUID = -9132859674221644142L;

	public GroupNames(DataType root) {
		List<DataType> children = root.getChildren();
		if (aRootChildIsAnEvent(children)) {
			add("");
		}
		
		List<String> childNames = getChildNamesContainingReferences(children);
		for (String childName : childNames) {
			groupNameStartsWithChildName(childName).ifPresent(groupName -> {
				remove(groupName);
				add(childName);
			});
			if (!childNameStartsWithGroupName(childName)) {
				add(childName);
			}
		}

	}

	private boolean aRootChildIsAnEvent(List<DataType> children) {
		return children.stream().allMatch(c-> c.isLeaf() && c.getBaseType().getOmronType().equals(Optional.of(OmronBaseType.BOOL)));
	}

	private List<String> getChildNamesContainingReferences(List<DataType> children) {
		List<String> childNames = children.stream().filter(c -> c.getBaseType().getReference().isPresent()).map(c -> c.getName())
				.collect(Collectors.toList());
		return childNames;
	}

	private Optional<String> groupNameStartsWithChildName(String childName) {
		for (String groupName : this) {
			if (groupName.startsWith(childName)) {
				return Optional.of(groupName);
			}
		}
		return Optional.empty();

	}

	private boolean childNameStartsWithGroupName(String childName) {
		for (String groupName : this) {
			if (childName.startsWith(groupName)) {
				return true;
			}
		}
		return false;
	}

}
