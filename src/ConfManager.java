import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Properties;

public class ConfManager {

	public static void openConf() {
		String configFile = "sooba.conf";
		Properties prop = new Properties();
		FileInputStream fi;

		try {
			fi = new FileInputStream(configFile);
			prop.load(fi);

			if (prop.getProperty("MaxRow") != null)
				SoobaConst.MAXROW = Integer.parseInt(prop
						.getProperty("MaxRow"));
			System.out.println("MaxRow=" + SoobaConst.MAXROW);
			if (prop.getProperty("graphSpan") != null)
				SoobaConst.GRAPHSPAN = Integer.parseInt(prop
						.getProperty("graphSpan"));
			System.out.println("graphSpan=" + SoobaConst.GRAPHSPAN);
			if (prop.getProperty("inportNew") != null)
				SoobaConst.INPORTNEW = Integer.parseInt(prop
						.getProperty("inportNew"));

			fi.close();

		} catch (FileNotFoundException e) {
			File fl = new File(configFile);
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
		String configFile = "sooba.conf";
		BufferedWriter bufferWriter = null;

		try {
			bufferWriter = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(configFile)));

			bufferWriter.write("# SooBa config file");
			bufferWriter.newLine();
			bufferWriter.write("MaxRow=" + SoobaConst.MAXROW);
			bufferWriter.newLine();
			bufferWriter.write("graphSpan=" + SoobaConst.GRAPHSPAN);
			bufferWriter.newLine();
			bufferWriter.write("inportNew=" + SoobaConst.INPORTNEW);
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
