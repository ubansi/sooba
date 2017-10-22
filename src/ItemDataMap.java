import java.util.ArrayList;


public class ItemDataMap {
	private ArrayList<Integer> categoryIndex;
	
	ItemDataMap(){
		categoryIndex = new ArrayList<Integer>();
	}

	public int getCategoryIndex(int i) {
		if(i < categoryIndex.size())
			return categoryIndex.get(i);
		return -1;
	}
	

	public void setCategoryIndex(int index) {
		categoryIndex.add(index);
	}

	public void printAllData() {
		// TODO 自動生成されたメソッド・スタブ
		for(int i=0;i<categoryIndex.size();i++){
			System.out.print(categoryIndex.get(i)+",");
		}
		System.out.println();
	}
	
	int getSize(){
		return categoryIndex.size();
	}
	
	void dataClear(){
		categoryIndex.clear();
	}

}
