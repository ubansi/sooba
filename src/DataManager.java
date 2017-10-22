import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.table.AbstractTableModel;

public class DataManager extends AbstractTableModel {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private static ArrayList<ItemDataMap> iDMList = new ArrayList<ItemDataMap>(sooba_const.MAXTAB);
	private static ArrayList<ItemData> itemDataList = new ArrayList<ItemData>();
	static String FILENAME = new String(sooba_const.FILENAME);
	static BufferedWriter bufferWriter = null;
	static InfoManager infoman;

	DataManager(){
		infoman = new InfoManager();
		itemDataList.ensureCapacity(500);
	}


	/*
	 * 値を表示する関数
	 */
	public Object getValueAt(int row, int col) {

		if (row < itemDataList.size()) {
			if(window_setup.paneIndex == -1){
				if (col == sooba_const.NAMELINE) {
					return itemDataList.get(row).getName();
				}
				if (col == sooba_const.VALUELINE) {
					int i = itemDataList.get(row).getValue();
					return (i == 0) ? null : i;
				}
				if (col == sooba_const.TIMESTANPLINE) {
					Calendar t = Calendar.getInstance();

					if(itemDataList.get(row).getTimeStampL() != 0){
							t.setTimeInMillis(itemDataList.get(row).getTimeStampL());
						return String.format("%02d", t.get(Calendar.HOUR_OF_DAY))
								+ ":"
								+ String.format("%02d", t.get(Calendar.MINUTE));
					}
				}
				if (col == sooba_const.DELTA) {
					int delta;
					if (itemDataList.get(row).getSize() > 1) {
						delta = itemDataList.get(row).getValue()
								- itemDataList.get(row).getValue(
										itemDataList.get(row).getSize() - 2);
						return String.format("%+d", delta);
					}
					return null;
				}

				if (col == sooba_const.STOCKLINE) {
					return (itemDataList.get(row).getStock() == 0) ? null : itemDataList.get(row).getStock();
				}
			}
			//オーダー品タブ
			else{
				if(row < iDMList.get(window_setup.paneIndex).getSize()){
					if (col == sooba_const.NAMELINE) {
							return itemDataList.get(iDMList.get(window_setup.paneIndex).getCategoryIndex(row)).getName();
					}
					if (col == sooba_const.VALUELINE) {
						int i = itemDataList.get(iDMList.get(window_setup.paneIndex).getCategoryIndex(row)).getValue();
						return (i == 0) ? null : i;
					}
					if (col == sooba_const.TIMESTANPLINE) {
						Calendar t = Calendar.getInstance();
						if(itemDataList.get(iDMList.get(window_setup.paneIndex).getCategoryIndex(row)).getTimeStampL() != 0){
								t.setTimeInMillis(itemDataList.get(iDMList.get(window_setup.paneIndex).getCategoryIndex(row)).getTimeStampL());
							return String.format("%02d", t.get(Calendar.HOUR_OF_DAY))
									+ ":"
									+ String.format("%02d", t.get(Calendar.MINUTE));
						}
					}
					if (col == sooba_const.DELTA) {
						int delta;
						if (itemDataList.get(row).getSize() > 1) {
							delta = itemDataList.get(iDMList.get(window_setup.paneIndex).getCategoryIndex(row)).getValue()
									- itemDataList.get(iDMList.get(window_setup.paneIndex).getCategoryIndex(row)).getValue(
											itemDataList.get(iDMList.get(window_setup.paneIndex).getCategoryIndex(row)).getSize() - 2);
							return String.format("%+d", delta);
						}
						return null;
					}

					if (col == sooba_const.STOCKLINE) {
						return (itemDataList.get(row).getStock() == 0) ? null : itemDataList
								.get(row).getStock();
					}

				}
			}
		}
		return null;
	}



	/*
	 * JTableの値の変更時に呼び出される関数
	 *
	 */
	public void setValueAt(Object value, int row, int col) {
		System.out.println(value.toString());

		int newValue;
		try {
			newValue = new Integer(value.toString());
		} catch (NumberFormatException e) {
			newValue = 0;
		}

		// row行までのitemDataを確保
		while (itemDataList.size() <= row) {
			itemDataList.add(new ItemData());
		}
		if(window_setup.paneIndex == -1){
			if (col == sooba_const.NAMELINE) {
				itemDataList.get(row).setName(value.toString());
			}
			if (col == sooba_const.VALUELINE) {

				itemDataList.get(row).setValue(newValue,Clock.getTimeNumLong());
				stock_sum();

				fireTableCellUpdated(row, col + 1);
				fireTableCellUpdated(row, col + 2);
			}
			if (col == sooba_const.STOCKLINE) {
				itemDataList.get(row).setStock(newValue);
				stock_sum();
			}

			fireTableCellUpdated(row, col);
			sooba_const.DATACHANGE = 1;
		}
		else{
			if (col == sooba_const.NAMELINE) {
				itemDataList.get(iDMList.get(window_setup.paneIndex).getCategoryIndex(row)).setName(value.toString());
			}
			if (col == sooba_const.VALUELINE) {

				itemDataList.get(iDMList.get(window_setup.paneIndex).getCategoryIndex(row)).setValue(newValue,Clock.getTimeNumLong());
				stock_sum();

				fireTableCellUpdated(row, col + 1);
				fireTableCellUpdated(row, col + 2);
			}
			if (col == sooba_const.STOCKLINE) {
				itemDataList.get(iDMList.get(window_setup.paneIndex).getCategoryIndex(row)).setStock(newValue);
				stock_sum();
			}

			fireTableCellUpdated(row, col);
			sooba_const.DATACHANGE = 1;
		}

		itemDataList.get(row).printAllData();
	}

	/**
	 * 名前のセット
	 *
	 * @param newName
	 * @param row
	 */
	public void setNameF(String newName, int row) {
		while (itemDataList.size() <= row) {
			itemDataList.add(new ItemData());
		}
		itemDataList.get(row).setName(newName);
	}

	// 値のクリア用(タイムスタンプなし)
	public void clearValueAt(int row) {
		if (row < itemDataList.size()) {
			itemDataList.get(row).DataClear();
		}
		fireTableDataChanged();
	}

	@Override
	public int getRowCount() {
		return sooba_const.MAXROW;
	}

	@Override
	public int getColumnCount() {
		return sooba_const.MAXCOL;
	}

	// 編集可能かどうか
	public boolean isCellEditable(int rowIndex, int ColumIndex) {
		// タイムラインの列は編集不可
		if (ColumIndex == sooba_const.TIMESTANPLINE) {
			return false;
		}
		if (ColumIndex == sooba_const.DELTA) {
			return false;
		}
		if(ColumIndex == sooba_const.NAMELINE)
			return false;

		return true;
	}

	public String getColumnName(int column) {
		return sooba_const.columnNames[column];

	}

	public void stockClear() {
		int i = 0;
		while (i < itemDataList.size()) {
			itemDataList.get(i).setStock(0);
			fireTableCellUpdated(i, sooba_const.STOCKLINE);
			i++;
		}
		stock_sum();
	}

	public void stock_sum() {
		int sum = 0;
		for (int i = 0; i < itemDataList.size(); i++) {
			sum = itemDataList.get(i).getValue() * itemDataList.get(i).getStock() + sum;
		}
		window_setup.sum_value.setText("" + String.format("%.0f", sum / 1.05));
	}

	public static int getSize() {
		return itemDataList.size();

	}

	public static String getName(int row) {
		if (row < itemDataList.size()){
			if(getIndex(row) != -1){
				//アイテム名はnonameの場合は能力の１番目をコピー
				if(itemDataList.get(getIndex(row)).getName() != null)
				if(itemDataList.get(getIndex(row)).getItemName().equals("noname"))
					return itemDataList.get(getIndex(row)).getEx_abi(0);
				return itemDataList.get(getIndex(row)).getItemName();
			}
		}
		return null;
	}
	public static String getNameSD(int row) {
		if (row < itemDataList.size()){
			if(getIndex(row) != -1){
				return itemDataList.get(getIndex(row)).getName();
			}
		}
		return null;
	}

	public static String getItemNameSD(int row) {
		if (row < itemDataList.size()){
			if(getIndex(row) != -1){
				return itemDataList.get(getIndex(row)).getItemName();
			}
		}
		return null;
	}


	public static String getNameEx(int row) {
		if (row < itemDataList.size()){
				//アイテム名はnonameの場合は能力の１番目をコピー
				if(itemDataList.get(row).getItemName().equals("noname"))
					return itemDataList.get(row).getEx_abi(0);
				return itemDataList.get(row).getItemName();
		}
		return null;
	}


	public static int getValue(int row) {
		if (row < itemDataList.size())
			if(getIndex(row) != -1)
				return itemDataList.get(getIndex(row)).getValue();
		return 0;

	}

	public static int getValue(int row, int i) {
		if (row < itemDataList.size())
			if(getIndex(row) != -1)
				return getValueEx(getIndex(row),i);
		return 0;

	}

	public static int getValueEx(int row,int i){
		if (row < itemDataList.size())
			return itemDataList.get(row).getValue(i);
		return 0;
	}

	public static int getValueEx(int row){
		if (row < itemDataList.size())
			return itemDataList.get(row).getValue();
		return 0;
	}


	public static long getTimestampL(int row) {

		if (row < itemDataList.size() && getIndex(row) != -1)
			if (itemDataList.get(getIndex(row)).getTimeStampL() != 0)
				return itemDataList.get(getIndex(row)).getTimeStampL();
		return 0;
	}

	public static int getTimestamp(int row) {

		if (row < itemDataList.size())
			if (itemDataList.get(getIndex(row)).getTimeStampEx() != 0)
				return itemDataList.get(getIndex(row)).getTimeStampEx();
		return 0;
	}

	public static long getTimestamp(int row, int i) {
		if (row < itemDataList.size())
			if (itemDataList.get(getIndex(row)).getTimeStampL() != 0)
				return itemDataList.get(getIndex(row)).getTimeStampL(i);
		return 0;
	}

	static int getIndex(int row){
		if(window_setup.paneIndex == -1)
			return row;
		else
			return iDMList.get(window_setup.paneIndex).getCategoryIndex(row);
	}

	public static int getTimestampEx(int row) {
		if (row < itemDataList.size())
			if (itemDataList.get(row).getTimeStampEx() != 0)
				return itemDataList.get(row).getTimeStampEx();
		return 0;
	}

	public static int getCategory(int row){
		if (row < itemDataList.size() && getIndex(row) != -1)
				return itemDataList.get(getIndex(row)).getCategory();
		return 8;
	}

	public static int getElement(int row){
		if (row < itemDataList.size() && getIndex(row) != -1)
				return itemDataList.get(getIndex(row)).getElement();
		return 7;
	}

	public static int getSlot(int row){
		if (row < itemDataList.size() && getIndex(row) != -1)
				return itemDataList.get(getIndex(row)).getSlot();
		return 0;
	}

	/**
	 * 最高値の取得
	 *
	 * @param row
	 * @return 最高値 or 0
	 */
	public static int getHighestValue(int row) {
		int highest = 0;
		int i = 0;
		int row2 = getIndex(row);

		if (row2 < itemDataList.size() && row2 >= 0) {
			while (i < itemDataList.get(row2).getSize()) {
				if (itemDataList.get(row2).getValue(i) > highest) {
					highest = itemDataList.get(row2).getValue(i);
				}
				i++;
			}
			return highest;
		}
		return 0;

	}

	/**
	 * 最安値の取得
	 *
	 * @param row
	 * @return 最安値 or 0
	 */
	public static int getRecordLow(int row) {
		int lowest = 0;
		int i = 0;
		int row2 = getIndex(row);

		if (row2 < itemDataList.size() && row2 >= 0) {
			lowest = itemDataList.get(row2).getValue(0);
			while (i < itemDataList.get(row2).getSize()) {
				if (itemDataList.get(row2).getValue(i) < lowest) {
					lowest = itemDataList.get(row2).getValue(i);
				}
				i++;
			}
			return lowest;
		}
		return 0;
	}

	public static int getSize(int row) {
		int row2 = getIndex(row);

		if (row2 < itemDataList.size() && row2 >= 0)
			return itemDataList.get(row2).getSize();
		return 0;
	}

	public static void dataClear() {
		int i = itemDataList.size();
		while (i > 0) {
			i--;
			itemDataList.get(i).DataClear();
		}
		itemDataList.clear();

		idmlClear();


	}

	static void idmlClear(){
		int i=iDMList.size();

		while (i > 0){
			i--;
			iDMList.get(i).dataClear();

		}
		iDMList.clear();
	}

	/**
	 * objに一致する名前があった場合にその行数を返す
	 *
	 * @param obj
	 * @return
	 */
	public static int serchObj(String inName) {
		int row = 0;

		while (row < itemDataList.size()) {
			if (itemDataList.get(row).getItemName().equals(inName))
				return row;
			else
				row++;
		}

		return -1;

	}

	public static int serchObj(ItemData inItem){

		String inName = inItem.getItemName();
		int category = inItem.getCategory();
		int slot = inItem.getSlot();
		int element = inItem.getElement();
		String[] ex_slot = inItem.getEx_abi();

		int row = 0;

		while (row < itemDataList.size()) {
			if(itemDataList.get(row).getItemName() != null)
			if (itemDataList.get(row).getItemName().compareTo(inName) == 0){
				if(itemDataList.get(row).getCategory() == category)
					if(itemDataList.get(row).getElement() == element)
						if(itemDataList.get(row).getSlot() == slot){
							int j=0;
							for(int i=0;i<slot;i++){
								System.out.println("i="+i);
								if(itemDataList.get(row).getEx_abi(i) != null)
								if(itemDataList.get(row).getEx_abi(i).equals(ex_slot[i]) != true)
									j=1;
							}
							if(j==0){
								return row;
							}
						}
			}
				row++;
		}
		return -1;

	}

	/**
	 * インポート用のメソッド
	 *
	 * @param value
	 * @param row
	 */
	public static int setValueInport(int value, int timestamp, int row) {

		// row行までのitemDataを確保
		while (itemDataList.size() <= row) {
			itemDataList.add(new ItemData());
		}

		int i = 0;

		// 時刻を順番に走査
		while (i < itemDataList.get(row).getSize()) {
			// 同じデータがあった場合
			if (itemDataList.get(row).getTimeStampEx(i) != 0) {
				if (itemDataList.get(row).getTimeStampEx(i) == timestamp) {
					System.out.println("同じデータをインポートしようとしたよ");
					return -1;
				}

				// データの中にインポートデータより新しい価格があった場合
				if (itemDataList.get(row).getTimeStampEx(i) > timestamp) {
					// 手前に挿入
					itemDataList.get(row).setValue(value, (long)timestamp*1000, i);
					return 0;
				}
			}
			i++;
		}
		// 最後までみたけど自分が最新の場合最後に追加
		itemDataList.get(row).setValue(value, (long)timestamp*1000);

		return 0;
	}

	public static void removeLast(int i) {
		if (i < itemDataList.size()) {
			itemDataList.get(getIndex(i)).removeLast();

			System.out.println("一番最後のデータを消去しました");
		}
	}

	public void remomveData(int i){
		if(i < itemDataList.size()){
			System.out.println(itemDataList.get(getIndex(i)).getItemName()+"のデータを削除しました");
			
//			itemDataList.get(getIndex(i)).DataClear();
			itemDataList.remove(getIndex(i));
			makeDataMap();
		}
	}


	public static void addLast(ItemData newItem) {

		newItem.setName("newItem");
		itemDataList.add(newItem);


		sooba_const.DATACHANGE =1;

		makeDataMap();
	}

	// ファイルのオープン
	int OpenTable(int maxrow) {
		// テーブルのクリア
		for (int j = 0; j < maxrow; j++) {
			clearValueAt(j);
		}
		DataManager.dataClear();

		try {
			FileReader filereader = new FileReader(FILENAME);
			StreamTokenizer streamtokenizer = new StreamTokenizer(filereader);

			int col = 0, row = -1;
			int line = streamtokenizer.lineno();

			// ファイル読み込み
			streamtokenizer.nextToken();
			//先頭にフォーマットが記載されてる場合
			if(streamtokenizer.sval.equals("SoobaDataFormat07") == true){

			}

			while ((streamtokenizer.nextToken() != StreamTokenizer.TT_EOF ) && (row < maxrow)) {
				// 改行
				if (line != streamtokenizer.lineno()) {
					System.out.println("改行");
					line = streamtokenizer.lineno();
					col=0;
					if(line %2 == 0)
						row++;
				}

				//奇数行の場合 データ本体は２行目から開始
				if(line %2 == 0){


					//文字の場合
					if (streamtokenizer.ttype == StreamTokenizer.TT_WORD) {
						System.out.println("sval"+line +":"+ streamtokenizer.sval);
						if (col == sooba_const.NAMELINE_F) {
							itemDataList.add(new ItemData());
							itemDataList.get(row).setName(streamtokenizer.sval);
						}
						if(col == sooba_const.CopyNAMELINE){
							itemDataList.get(row).setItemName(streamtokenizer.sval);
						}
						if(col >= sooba_const.SLOT_s_F &&col < (sooba_const.SLOT_s_F + itemDataList.get(row).getSlot())){
							itemDataList.get(row).setEx_abi((col-sooba_const.SLOT_s_F),streamtokenizer.sval);
						}

						col++;
					}
					//数字の場合
					if (streamtokenizer.ttype == StreamTokenizer.TT_NUMBER) {
						System.out.println("nval"+line +":"+ streamtokenizer.nval);
						if(col == sooba_const.CATEGORY_F)
							itemDataList.get(row).setCategory((int)streamtokenizer.nval);
						if(col == sooba_const.STOCKLINE_F)
							itemDataList.get(row).setStock((int)streamtokenizer.nval);
						if(col == sooba_const.WATCHLINE_F)
							itemDataList.get(row).setWatch((int)streamtokenizer.nval);
						if(col == sooba_const.ELEMENT_F)
							itemDataList.get(row).setElement((int)streamtokenizer.nval);
						if(col == sooba_const.SLOTNUM_F)
							itemDataList.get(row).setSlot((int)streamtokenizer.nval);

						col++;
					}

				}
				//相場データの行の場合
				if(line %2 == 1){
					if (streamtokenizer.ttype == StreamTokenizer.TT_NUMBER) {
						System.out.println(col);

						if((int)streamtokenizer.nval != 0){
						if(col % 2 == 0){
							System.out.println("int nval"+line +":"+ (int)streamtokenizer.nval);
							itemDataList.get(row).setValueF((int)streamtokenizer.nval);
						}
						if(col % 2 == 1){
							System.out.println("long nval"+line +":"+ (long)streamtokenizer.nval);
							if(col == 1){
								itemDataList.get(row).setBaseTimeStamp((int)streamtokenizer.nval);
								itemDataList.get(row).setTimestampF(0);
							}
							else{
								if(itemDataList.get(row).getBaseTimeStamp() == 0){
								}
								else
									itemDataList.get(row).setTimestampF((int)streamtokenizer.nval);
							}
						}
						}
						col++;
					}
				}


			}

			filereader.close();
			sooba_const.DATACHANGE = 0;
//			itemDataList.get(0).printAllData();
//			itemDataList.get(1).printAllData();
//			itemDataList.get(2).printAllData();
//			itemDataList.get(3).printAllData();
			System.out.println(itemDataList.size());

		} catch (IOException e) {
			status_info.set_status_label("新しいファイルを作成します。");
			File fl = new File(FILENAME);
			sooba_const.openData = 1;
			try {
				fl.createNewFile();
			} catch (IOException e1) {
				status_info.set_status_label("ファイルの作成に失敗しました。");
				sooba_const.openData = 1;
			}
		}

		//データマップの作成
//		itemDataMap = new ArrayList<Integer[]>(itemDataList.size());
//		int[] datamap = new int[sooba_const.MAXTAB];
		makeDataMap();


		return 0;
	}
	/**
	 * ファイル保存用
	 *
	 * @param row
	 *            アイテムの行
	 * @return "name,stock,value1,timestamp1,..."
	 */
	public static String getValueAtF(int row) {
		int i = 0;
		StringBuffer s = new StringBuffer();
		if (row < itemDataList.size()) {
			// 名前がnullじゃないときのみ記録
			if (itemDataList.get(row).getName() != null) {

				// 1つ目のデータ記述
				s.append(itemDataList.get(row).getValue(i));
				s.append(',');

				s.append(itemDataList.get(row).getBaseTimeStamp());
				s.append(',');
				i++;

				// i=1;
				// 値が変化した場合のみ記録
				while (i < itemDataList.get(row).getSize()) {
					if (itemDataList.get(row).getValue(i - 1) != itemDataList.get(row)
							.getValue(i)) {
						s.append(itemDataList.get(row).getValue(i));
						s.append(',');
						s.append(itemDataList.get(row).getTimeStamp(i));
						s.append(',');
					} else if (i == (itemDataList.get(row).getSize() - 1)) {
						// 一番一番最後のデータは変化していなくても記録
						s.append(itemDataList.get(row).getValue());
						s.append(',');
						s.append(itemDataList.get(row).getTimeStamp());
						s.append(',');
					}
					i++;
				}
			}
		}
		return s.toString();
	}

	static String getItemDataF(int row){
		StringBuffer s = new StringBuffer();

		// 名前がnullじゃないときのみ記録
		if (itemDataList.get(row).getName() != null) {
			s.append(itemDataList.get(row).getName());
			s.append(',');
			s.append(itemDataList.get(row).getItemName());
			s.append(',');
			s.append(itemDataList.get(row).getCategory());
			s.append(',');
			s.append(itemDataList.get(row).getStock());
			s.append(',');
			s.append(itemDataList.get(row).getWatch());
			s.append(',');
			if(itemDataList.get(row).getCategory() < 3){
				s.append(itemDataList.get(row).getElement());
				s.append(',');
				s.append(itemDataList.get(row).getSlot());
				s.append(',');

				for(int i=0;i<itemDataList.get(row).getSlot();i++){
					s.append(itemDataList.get(row).getEx_abi(i));
					s.append(',');
				}
			}
		}

		return s.toString();

	}

	static String getItemDataEx(int row){
		StringBuffer s = new StringBuffer();

		// 名前がnullじゃないときのみ記録
		if (itemDataList.get(row).getName() != null) {
			s.append(itemDataList.get(row).getItemName());
			s.append(',');
			s.append(itemDataList.get(row).getCategory());
			s.append(',');

			if(itemDataList.get(row).getCategory() < 2){
				s.append(itemDataList.get(row).getElement());
				s.append(',');
				s.append(itemDataList.get(row).getSlot());
				s.append(',');

				for(int i=0;i<itemDataList.get(row).getSlot();i++){
					s.append(itemDataList.get(row).getEx_abi(i));
					s.append(',');
				}
			}
		}

		return s.toString();

	}

	// テーブルの保存
	public static  int SaveTable() {
		try {
			bufferWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FILENAME)));

			int i = 0;

				bufferWriter.write("SoobaDataFormat07");
				bufferWriter.newLine();
			while (i < DataManager.getSize()) {
				bufferWriter.write(getItemDataF(i));
				bufferWriter.newLine();

				bufferWriter.write(getValueAtF(i));
				bufferWriter.newLine();
				i++;
			}

			sooba_const.DATACHANGE = 0;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferWriter != null) {
					bufferWriter.close();
				}
			} catch (Exception e) {
			}
		}
		return 0;
	}

	void setfilename(String NEWFILENAME) {
		FILENAME = NEWFILENAME;
	}

	static void makeDataMap(){
		iDMList.clear();
		for(int j=0;j<sooba_const.MAXTAB;j++){
			iDMList.add(new ItemDataMap());

			for(int i=0;i<itemDataList.size();i++){
					if(itemDataList.get(i).getCategory() == j){
						iDMList.get(j).setCategoryIndex(i);
					}
			}
		}

		for(int i=0;i<itemDataList.size();i++){
			if(itemDataList.get(i).getWatch() == 1){
				iDMList.get(8).setCategoryIndex(i);
			}
	}


		for(int i=0;i<10;i++)
			iDMList.get(i).printAllData();


	}

	static void setInfoData(int row){
		InfoManager.setInfo("");
		if(row < itemDataList.size() && getIndex(row) != -1)
			if(itemDataList.get(getIndex(row)).getName() != null)
				infoman.setInfo(itemDataList.get(getIndex(row)));

	}

	static String[] getExabi(int row){
		if (row < itemDataList.size() && getIndex(row) != -1)
				return itemDataList.get(getIndex(row)).getEx_abi();
		return null;


	}

	public static void setName(String newName,int row){
		while (itemDataList.size() <= row) {
			itemDataList.add(new ItemData());
		}

		if (row < itemDataList.size() && getIndex(row) != -1)
			itemDataList.get(getIndex(row)).setName(newName);
		else
			System.out.println("set name error");
	}


	public static void setItemName(String newName, int row) {
		// TODO 自動生成されたメソッド・スタブ
		while (itemDataList.size() <= row) {
			itemDataList.add(new ItemData());
		}

		if (row < itemDataList.size() && getIndex(row) != -1)
			itemDataList.get(getIndex(row)).setItemName(newName);
	}
	public static void setCategory(int newCategory,int row){
		while (itemDataList.size() <= row) {
			itemDataList.add(new ItemData());
		}

		if (row < itemDataList.size() && getIndex(row) != -1){
			itemDataList.get(getIndex(row)).setCategory(newCategory);
		}
		if(getIndex(row) == -1){
			ItemData newItem = new ItemData();
			newItem.setCategory(newCategory);
			itemDataList.add(newItem);
		}
		makeDataMap();
	}

	public static void printData(int row){
		while (itemDataList.size() <= row) {
			itemDataList.add(new ItemData());
		}

		if (row < itemDataList.size() && getIndex(row) != -1)
			itemDataList.get(getIndex(row)).printAllData();
	}


	public static void setElement(int elementIndex, int row) {
		// TODO 自動生成されたメソッド・スタブ
		while (itemDataList.size() <= row) {
			itemDataList.add(new ItemData());
		}

		if (row < itemDataList.size() && getIndex(row) != -1)
			itemDataList.get(getIndex(row)).setElement(elementIndex);

	}


	public static void setSlot(int newSlot, int row) {
		// TODO 自動生成されたメソッド・スタブ
		while (itemDataList.size() <= row) {
			itemDataList.add(new ItemData());
		}

		if (row < itemDataList.size() && getIndex(row) != -1)
			itemDataList.get(getIndex(row)).setSlot(newSlot);

	}


	public static void setExAbi(String ex_abi, int i, int row) {
		// TODO 自動生成されたメソッド・スタブ
		while (itemDataList.size() <= row) {
			itemDataList.add(new ItemData());
		}

		if (row < itemDataList.size() && getIndex(row) != -1)
			itemDataList.get(getIndex(row)).setEx_abi(i, ex_abi);
	}


	public void setNewItem(ItemData newItem, int row) {
		// TODO 自動生成されたメソッド・スタブ
		while(itemDataList.size() <= row) {
			itemDataList.add(new ItemData());
		}


//		itemDataList.add(newItem);
		if(serchObj(newItem) == -1)	{
			if (row < itemDataList.size() && getIndex(row) != -1){

				itemDataList.get(getIndex(row)).setItemName(newItem.getItemName());
				itemDataList.get(getIndex(row)).setCategory(newItem.getCategory());
				itemDataList.get(getIndex(row)).setElement(newItem.getElement());
				itemDataList.get(getIndex(row)).setSlot(newItem.getSlot());


				for(int i=0;i < newItem.getSlot();i++)
					itemDataList.get(getIndex(row)).setEx_abi(i,newItem.getEx_abi(i));


//			itemDataList.set(getIndex(row),newItem);
			}
			if(getIndex(row) == -1){
				itemDataList.add(newItem);
			}

			sooba_const.DATACHANGE =1;
		}
		if (row < itemDataList.size() && getIndex(row) != -1){
			itemDataList.get(getIndex(row)).setWatch(newItem.getWatch());
			itemDataList.get(getIndex(row)).setName(newItem.getName());
			sooba_const.DATACHANGE =1;
		}
		makeDataMap();
		fireTableDataChanged();

	}


	public static boolean getWatch(int row) {
		// TODO 自動生成されたメソッド・スタブ
		if (row < itemDataList.size()){
			if(getIndex(row) != -1){
				return (itemDataList.get(getIndex(row)).getWatch() == 1);
			}
		}

		return false;
	}

}
