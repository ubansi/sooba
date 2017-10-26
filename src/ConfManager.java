import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Properties;

public class ConfManager {
	private final static String CONF_FILE_NAME = "sooba.conf";

	private final static String PROP_NAME_MAX_ROW = "MaxRow";

	private final static String PROP_NAME_GRAPH_SPAN = "GraphSpan";

	private final static String PROP_NAME_INPORT_NEW = "InportNew";

	public static void openConf() {
		Properties prop = new Properties();
		FileInputStream fi;

		try {
			fi = new FileInputStream(CONF_FILE_NAME);
			prop.load(fi);

			if (prop.getProperty(PROP_NAME_MAX_ROW) != null)
				SoobaConst.MAX_ROW = Integer.parseInt(prop
						.getProperty(PROP_NAME_MAX_ROW));

			System.out.println(PROP_NAME_MAX_ROW + "=" + SoobaConst.MAX_ROW);

			if (prop.getProperty(PROP_NAME_GRAPH_SPAN) != null)
				SoobaConst.GRAPH_SPAN = Integer.parseInt(prop
						.getProperty(PROP_NAME_GRAPH_SPAN));

			System.out.println(PROP_NAME_GRAPH_SPAN +"=" + SoobaConst.GRAPH_SPAN);

			if (prop.getProperty(PROP_NAME_INPORT_NEW) != null)
				SoobaConst.INPORT_NEW = Integer.parseInt(prop
						.getProperty(PROP_NAME_INPORT_NEW));

			fi.close();

		} catch (FileNotFoundException e) {
			File fl = new File(CONF_FILE_NAME);
			try {
				fl.createNewFile();
			} catch (IOException e1) {
				StatusInfo.set_status_label("ファイルの作成に失敗しました。");
				SoobaConst.openData = 1;
			}
			// System.out.println("ひらけませんですた");
			SoobaConst.openData = 1;
		} catch (IOException e) {
//			e.printStackTrace();
			SoobaConst.openData =1;

		}
	}

	public static void saveConf() {
		BufferedWriter bufferWriter = null;

		try {
			bufferWriter = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(CONF_FILE_NAME)));

			bufferWriter.write("# SooBa config file");
			bufferWriter.newLine();
			bufferWriter.write(PROP_NAME_MAX_ROW+ "=" + SoobaConst.MAX_ROW);
			bufferWriter.newLine();
			bufferWriter.write(PROP_NAME_GRAPH_SPAN +"=" + SoobaConst.GRAPH_SPAN);
			bufferWriter.newLine();
			bufferWriter.write(PROP_NAME_INPORT_NEW + "=" + SoobaConst.INPORT_NEW);
			bufferWriter.newLine();

		} catch (FileNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} finally {
			try {
				if (bufferWriter != null) {
					bufferWriter.close();
				}
			} catch (Exception e) {
			}
		}

	}

}
