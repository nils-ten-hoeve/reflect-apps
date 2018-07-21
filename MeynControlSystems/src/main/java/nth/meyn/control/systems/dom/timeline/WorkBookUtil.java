package nth.meyn.control.systems.dom.timeline;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import nth.introspect.layer1userinterface.controller.DownloadStream;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WorkBookUtil {

	public static DownloadStream createDownloadStream(String fileName, XSSFWorkbook workbook) throws IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		workbook.write(buffer);
		InputStream inputStream = new ByteArrayInputStream(
				buffer.toByteArray());
		File file = new File(fileName);
		DownloadStream downloadStream = new DownloadStream(file,
				inputStream);
		return downloadStream;	
		}

}