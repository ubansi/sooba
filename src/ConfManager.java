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
		String configFile = "Sooba.conf";
		Properties prop = new Properties();
		FileInputStream fi;

		try {
			fi = new FileInputStream(configFile);
			prop.load(fi);

			if (prop.getProperty("MaxRow") != null)
				sooba_const.MAXROW = Integer.parseInt(prop
						.getProperty("MaxRow"));
			System.out.println("MaxRow=" + sooba_const.MAXROW);
			if (prop.getProperty("graphSpan") != null)
				sooba_const.GRAPHSPAN = Integer.parseInt(prop
						.getProperty("graphSpan"));
			System.out.println("graphSpan=" + sooba_const.GRAPHSPAN);
			if (prop.getProperty("inportNew") != null)
				sooba_const.INPORTNEW = Integer.parseInt(prop
						.getProperty("inportNew"));

			fi.close();

		} catch (FileNotFoundException e) {
			File fl = new File(configFile);
			try {
				fl.createNewFile();
			} catch (IOException e1) {
				status_info.set_status_label("ファイルの作成に失敗しました。");
				sooba_const.openData = 1;
			}
			// System.out.println("ひらけませんですた");
			sooba_const.openData = 1;
		} catch (IOException e) {
//			e.printStackTrace();
			sooba_const.openData =1;

		}
	}

	public static void saveConf() {
		String configFile = "Sooba.conf";
		BufferedWriter bufferWriter = null;

		try {
			bufferWriter = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(configFile)));

			bufferWriter.write("# SooBa config file");
			bufferWriter.newLine();
			bufferWriter.write("MaxRow=" + sooba_const.MAXROW);
			bufferWriter.newLine();
			bufferWriter.write("graphSpan=" + sooba_const.GRAPHSPAN);
			bufferWriter.newLine();
			bufferWriter.write("inportNew=" + sooba_const.INPORTNEW);
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
