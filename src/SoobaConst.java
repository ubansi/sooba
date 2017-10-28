public class SoobaConst {
	public static final String VER = "1.005test";// バージョン値
	static String[] columnNames = { "アイテム名", "値段", "時間", "変動", "在庫" };
	public static final int NAMELINE = 0;// アイテム名
	public static final int VALUELINE = 1;// 値段
	public static final int TIMESTANPLINE = 2;// タイムスタンプ
	public static final int DELTA = 3;// 変動
	public static final int STOCKLINE = 4;// 在庫
	public static final int MAXTAB = 10;
	public static final String[] Category = {"武器","ユニット","衣装","消費","ディスク","家具","オーダー","その他",null};
	public static final String[] Element = {"無属性","炎","氷","雷","風","光","闇",null};

	public static final int MAXCOL = columnNames.length;

	// セルの書式
	public static final String TIMESTANPLINESTRING = "00:00";
	public static final String MAXVALUEDIGIT = "1000000000";

	/**
	 * 表示名
	 */
	public static final int NAMELINE_F = 0;
	/**
	 * アイテム名
	 */
	public static final int CopyNAMELINE = 1;
	/**
	 * 分類
	 */
	public static final int CATEGORY_F = 2;
	/**
	 * 在庫
	 */
	public static final int STOCKLINE_F = 3;

	/**
	 * 注目リスト
	 */
	public static final int WATCHLINE_F = 4;
	/**
	 * 属性
	 */
	public static final int ELEMENT_F = 5;
	/**
	 * スロット数
	 */
	public static final int SLOTNUM_F = 6;
	/**
	 * スロット 6~14
	 */
	public static final int SLOT_s_F = 7;
	/**
	 * スロット 6~14
	 */
	public static final int SLOT_e_F = 15;

	/**
	 * 値段
	 */
	public static final int VALUELINE_F = 1;
	/**
	 * タイムスタンプ
	 */
	public static final int TIMESTANPLINE_F = 2;

	/**
	 * changed:1 no-change:0
	 */
	public static int DATACHANGE = 0;
	/**
	 * ファイルが正しく開けたか
	 */
	public static int openData = 0;

	// 設定用
	public static String FILE_NAME = "pso.csv";// ファイル名


}
