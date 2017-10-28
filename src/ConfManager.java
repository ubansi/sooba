import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.sooba.entity.SoobaConfig;

public class ConfManager {
	private final static String CONF_FILE_NAME = "sooba.conf";

	private final static String PROP_NAME_MAX_ROW = "MaxRow";

	private final static String PROP_NAME_GRAPH_SPAN = "GraphSpan";

	private final static String PROP_NAME_INPORT_NEW = "ImportNew";

	public static void openConf() {
		Properties prop = new Properties();

		try (InputStream is = new FileInputStream(CONF_FILE_NAME)) {
			prop.load(is);

			int maxRow = Integer.parseInt(prop.getProperty(PROP_NAME_MAX_ROW));
			int graphSpan = Integer.parseInt(prop.getProperty(PROP_NAME_GRAPH_SPAN));
			int addImport = Integer.parseInt(prop.getProperty(PROP_NAME_INPORT_NEW));

			if (prop.getProperty(PROP_NAME_MAX_ROW) != null)
				SoobaConfig.setMaxRow(maxRow);

			if (prop.getProperty(PROP_NAME_GRAPH_SPAN) != null)
				SoobaConfig.setGraphSpan(graphSpan);

			if (prop.getProperty(PROP_NAME_INPORT_NEW) != null)
				SoobaConfig.setAddImport(addImport);

		} catch (FileNotFoundException e) {

			// System.out.println("ひらけませんですた");
			SoobaConst.openData = 1;
		} catch (IOException e) {
			// e.printStackTrace();
			SoobaConst.openData = 1;

		}
	}

	public static void saveConf() {

		Properties prop = new Properties();
		prop.setProperty(PROP_NAME_MAX_ROW, "" + SoobaConfig.getMaxRow());
		prop.setProperty(PROP_NAME_GRAPH_SPAN, "" + SoobaConfig.getGraphSpan());
		prop.setProperty(PROP_NAME_INPORT_NEW, "" + SoobaConfig.getAddImport());

		try {
			prop.store(new FileOutputStream(CONF_FILE_NAME), "SooBa Config File");

		} catch (FileNotFoundException e) {
			// TODO 自動生成された catch ブロック

			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

	}

}
