/**
 *
 */
package com.sooba.entity;

/**
 * @author ubansi アプリ用設定内容
 */
public class SoobaConfig {
	/**
	 * 最大行
	 */
	private static int maxRow = 99;

	/**
	 * グラフの表示時間
	 */
	private static int graphSpan = 0;

	/**
	 * noadd:0 addnew:1
	 */
	private static int addImport = 0;



	public static int getMaxRow() {
		return maxRow;
	}

	public static void setMaxRow(int maxRow) {
		SoobaConfig.maxRow = maxRow;
	}

	public static int getGraphSpan() {
		return graphSpan;
	}

	public static void setGraphSpan(int graphSpan) {
		SoobaConfig.graphSpan = graphSpan;
	}

	public static int getAddImport() {
		return addImport;
	}

	public static void setAddImport(int addImport) {
		SoobaConfig.addImport = addImport;
	}


}
