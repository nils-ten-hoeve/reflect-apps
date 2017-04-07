package nth.meyn.cx.sysmac.converter.util;

import javax.xml.bind.DatatypeConverter;

public class StringToArrayCodeUtil {

	public static String print(String txt) {
		StringBuilder builder = new StringBuilder();
		builder.append("byte[] a= DatatypeConverter.parseHexBinary(\"");
		for (byte b : txt.getBytes()) {
			builder.append(getTwoDigitHex(b));
		}
		builder.append("\");");
		return builder.toString();
	}

	private static String getTwoDigitHex(byte b) {
		String hex = String.format("%02X ", b).substring(0, 2);
			return hex;
	}

}
