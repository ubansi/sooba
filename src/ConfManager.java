import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfManager {
	private final static String CONF_FILE_NAME = "sooba.conf";

	private final static String PROP_NAME_MAX_ROW = "MaxRow";

	private final static String PROP_NAME_GRAPH_SPAN = "GraphSpan";

	private final static String PROP_NAME_INPORT_NEW = "InportNew";

	public static void openConf() {
		Properties prop = new Properties();

		try(InputStream is = new FileInputStream(CONF_FILE_NAME)) {
			prop.load(is);

			if (prop.getProperty(PROP_NAME_MAX_ROW) != null)
				SoobaConst.MAX_ROW = Integer.parseInt(prop
						.getProperty(PROP_NAME_MAX_ROW));

			System.out.println(PROP_NAME_MAX_ROW + "=" + SoobaConst.MAX_ROW);

			if (prop.getProperty(PROP_NAME_GRAPH_SPAN) != null)
				SoobaConst.GRAPH_SPAN = Integer.parseInt(prop
						.getProperty(PROP_NAME_GRAPH_SPAN));

			System.out.println(PROP_NAME_GRAPH_SPAN + "="
					+ SoobaConst.GRAPH_SPAN);

			if (prop.getProperty(PROP_NAME_INPORT_NEW) != null)
				SoobaConst.INPORT_NEW = Integer.parseInt(prop
						.getProperty(PROP_NAME_INPORT_NEW));


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
		prop.setProperty(PROP_NAME_MAX_ROW, ""+SoobaConst.MAX_ROW);
		prop.setProperty(PROP_NAME_GRAPH_SPAN, ""+SoobaConst.GRAPH_SPAN);
		prop.setProperty(PROP_NAME_INPORT_NEW, ""+SoobaConst.INPORT_NEW);

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
