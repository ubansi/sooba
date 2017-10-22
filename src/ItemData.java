import java.util.ArrayList;

public class ItemData {

	private String name; //表示名
	private String ItemName; //アイテム名（コピペ用）
	private int category; //分類
//	private String tag[];
	private int element;
	private int slot;
	private String[] ex_abi ={null,null,null,null,null,null,null,null};
	private ArrayList<Integer> value;
	private int BaseTimeStamp=0;
	private ArrayList<Integer> timeStamp;
	private int swingSize = 200;

	private int stock;
	private int watch =0;//ウォッチリストに追加するかどうか


	ItemData() {
		name = null;
		ItemName = null;
		category = 8;
		element = 7;
		slot = 0;
		setBaseTimeStamp(0);
		value = new ArrayList<Integer>();
		timeStamp = new ArrayList<Integer>();
		value.ensureCapacity(swingSize);
		timeStamp.ensureCapacity(swingSize);
		stock = 0;
		setWatch(0);
	}

	/**
	 * アイテムデータのセット
	 *
	 * @param inName
	 *            項目名
	 * @param inVal
	 *            値段
	 * @param inTime
	 *            タイムスタンプ
	 */
	void setItem(String inName, int inVal, int inTime) {
		if (name == null)
			name = inName;

		// 名前が等しい時のみ追加
		if (name.compareTo(inName) == 0) {

			// タイムスタンプがない場合、空の場合は追加
			if (timeStamp.isEmpty()) {
				value.add(inVal);
				timeStamp.add(inTime);

			}
			// 一番最後のtimeStanpと違うときのみ更新
			else if (timeStamp.get(timeStamp.size() - 1) != inTime) {
				value.add(inVal);
				timeStamp.add(inTime);
			}
		} else {
			System.out.println("name is different from ItemData");
		}
	}

	/**
	 * アイテム名の取得
	 *
	 * @return アイテム名
	 */
	String getName() {
		return name;
	}

	/**
	 * アイテム名のセット
	 *
	 * @param newName
	 */
	void setName(String newName) {
		name = newName;
	}

	void setValue(int newValue, long newTimestamp) {
		value.add(newValue);
		if(timeStamp.size() == 0)
			BaseTimeStamp = (int)(newTimestamp/1000);

		timeStamp.add((int)((long)((newTimestamp-(long)BaseTimeStamp*1000)/1000)));

	}

	void setValue(int newValue, long newTimestamp, int index) {
		value.add(index, newValue);

		timeStamp.add(index, (int)((long)(newTimestamp-(long)BaseTimeStamp*1000)/1000));

	}

	/**
	 * ファイルオープン用の時間設定
	 *
	 * @param newTimestamp
	 */
	void setTimestampF(int newTimestamp) {
		timeStamp.add(newTimestamp);
	}

	/**
	 * ファイルオープン用の価格設定
	 *
	 * @param newValue
	 */
	void setValueF(int newValue) {
		value.add(newValue);
	}

	/**
	 * 一番最後の値段を取得
	 *
	 * @return 値段 or 0;
	 */
	int getValue() {
		if (value.isEmpty())
			return 0;
		else
			return value.get(value.size() - 1);
	}

	/**
	 * 一番最後の時間を取得
	 *
	 * @return 時間Calendar or 0;
	 */
	long getTimeStampL() {
		if(timeStamp.size() > 0)
			if (timeStamp.get(timeStamp.size() - 1) != null)
				return getTimeStampL(timeStamp.size() - 1);
			else return 0;
		else
			return 0;
	}

	long getTimeStampL(int i) {
		if (i < timeStamp.size()) {
			if (timeStamp.get(timeStamp.size() - 1) != null)
				return (((long)(timeStamp.get(i)+BaseTimeStamp))*1000);
			else
				;
		}
		return 0;

	}

	int getTimeStamp() {
		if (timeStamp.get(timeStamp.size() - 1) != null)
			return getTimeStamp(timeStamp.size() - 1);
		else
			return 0;
	}

	int getTimeStamp(int i) {
		if (i < timeStamp.size()) {
			if (timeStamp.get(timeStamp.size() - 1) != null)
				return (timeStamp.get(i));
			else
				;
		}
		return 0;

	}

	int getTimeStampEx() {
		if(timeStamp.size() != 0)
		if (timeStamp.get(timeStamp.size() - 1) != null)
			return getTimeStampEx(timeStamp.size() - 1);
		
			return 0;
	}

	int getTimeStampEx(int i) {
		if (i < timeStamp.size()) {
			if (timeStamp.get(timeStamp.size() - 1) != null)
				return (timeStamp.get(i)+BaseTimeStamp);
			else
				;
		}
		return 0;

	}

	/**
	 * i番目の要素を取得
	 *
	 * @param i
	 * @return 値段 or 0;
	 */
	int getValue(int i) {
		if(i >= 0)
			if (i < value.size())
				return value.get(i);
			else
				return 0;
		return 0;
	}


	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	/**
	 * すべてのデータを消去
	 */
	public void DataClear() {
		name = null;
		stock = 0;
		value.clear();
		timeStamp.clear();

	}
	

	public int getSize() {
		return value.size();
	}

	/**
	 * 一番最後のデータを消去
	 */
	public void removeLast() {
		value.remove(value.size() - 1);
		timeStamp.remove(timeStamp.size() - 1);
	}

	/**
	 * データ内容の表示
	 */
	public void printAllData() {
		int i = 0;
		System.out.println("----");
		System.out.println("name=" + name);
		System.out.println("itemName=" + ItemName);
		System.out.println("category=" + category);
		System.out.println("element=" + element);
		System.out.println("slot=" + slot);
		System.out.println("stock=" + stock);
		System.out.println("BaseTimeStamp=" + BaseTimeStamp);
		System.out.println("Watch:"+watch);

		for(int j=0;j<8;j++){
			System.out.print(" ex"+j+ex_abi[j]);
		}
		System.out.println();

		while (i < value.size()) {
			System.out.println("value" + i + "=" + value.get(i));
			i++;
		}
		i = 0;
		while (i < timeStamp.size()) {
			System.out.println("time" + i + "=" + (long)getTimeStampL(i));
			i++;
		}
		System.out.println("----");
	}

	public String getItemName() {
		return ItemName;
	}

	public void setItemName(String itemName) {
		ItemName = itemName;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}
	public int getElement() {
		return element;
	}
	public void setElement(int element) {
		this.element = element;
	}

	public String getEx_abi(int index) {
		return ex_abi[index];
	}

	public void setEx_abi(int index,String ex_abi) {
		this.ex_abi[index] = ex_abi;
	}

	public int getSlot() {
		return slot;
	}

	public void setSlot(int slot) {
		this.slot = slot;
	}

	public int getBaseTimeStamp() {
		return BaseTimeStamp;
	}

	public void setBaseTimeStamp(int baseTimeStamp) {
		BaseTimeStamp = baseTimeStamp;
	}

	public String[] getEx_abi() {
		// TODO 自動生成されたメソッド・スタブ
		return ex_abi;
	}

	public int getWatch() {
		return watch;
	}

	public void setWatch(int watch) {
		this.watch = watch;
	}

}
