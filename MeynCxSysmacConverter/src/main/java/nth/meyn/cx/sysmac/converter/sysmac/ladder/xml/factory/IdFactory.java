package nth.meyn.cx.sysmac.converter.sysmac.ladder.xml.factory;

import java.util.UUID;

public class IdFactory {

	private int instanceId;
	private int variableId;
	private static final String HEX_PREFIX = "0x";

	public IdFactory() {
		instanceId = 2;
		variableId=13;
	}

	public String createNextElementId() {
		instanceId++;
		return getHex(instanceId);
	}

	public static String getHex(int id) {
		StringBuilder builder = new StringBuilder();
		builder.append(HEX_PREFIX);
		String hexString = Integer.toHexString(id).toUpperCase();
		int leadingZeros = 8 - hexString.length();
		for (int i = 0; i < leadingZeros; i++) {
			builder.append("0");
		}
		builder.append(hexString);
		return builder.toString();
	}

	public String createNextVariableId() {
		variableId++;
		String id = String.format("07c93356-3dd8-4e3b-896f-4788%08d", variableId);
//		String id = UUID.randomUUID().toString();
		return id;
	}

}
