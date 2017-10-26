/**
 *
 */
package com.sooba.entity;

/**
 * @author ubansi アプリ用設定内容
 */
public class Config {
	/**
	 * 最大行
	 */
	private int maxRow = 99;

	/**
	 * グラフの表示時間
	 */
	private int graphSpan = 0;

	/**
	 * noadd:0 addnew:1
	 */
	private int propNameInportNew = 0;

	public int getMaxRow() {
		return maxRow;
	}

	public void setMaxRow(int maxRow) {
		this.maxRow = maxRow;
	}

	public int getGraphSpan() {
		return graphSpan;
	}

	public void setGraphSpan(int graphSpan) {
		this.graphSpan = graphSpan;
	}

	public int getPropNameInportNew() {
		return propNameInportNew;
	}

	public void setPropNameInportNew(int propNameInportNew) {
		this.propNameInportNew = propNameInportNew;
	}
}
