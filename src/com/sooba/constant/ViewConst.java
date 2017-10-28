package com.sooba.constant;

import java.util.LinkedHashMap;
import java.util.Map;

public class ViewConst {

	/**
	 * グラフのスケール指定
	 */
	public final static Map<String, Integer> GRAPH_SCALE = new LinkedHashMap<String, Integer>() {
		{
			put("３時間", 3);
			put("１２時間", 12);
			put("２４時間", 24);
			put("４８時間", 48);
			put("１週間", 24 * 7);
			put("１ヶ月", 24 * 7 * 4);
		}
	};
}
