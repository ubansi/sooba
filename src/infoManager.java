import java.awt.TextArea;
import java.util.Calendar;


public class infoManager {
	
	static TextArea ta =new TextArea("アイテム情報",5,30);

	
	static TextArea getTextArea(){
		return ta;
	}
	
	static void setInfo(String str){
		ta.setText(str);
	}
	
	void setInfo(ItemData id){
		StringBuffer info = new StringBuffer("アイテム名："+id.getItemName()+"\n");
		info.append("分類："+sooba_const.Category[id.getCategory()]+"  ");
		
		//武器の場合
		if(id.getCategory() == 0){
			info.append("属性："+sooba_const.Element[id.getElement()]+"  ");
		}
		if(id.getCategory() < 2){
			info.append("スロット数："+id.getSlot()+"\n");
			
			int i = 0;
			while(i < id.getSlot()){
				info.append(id.getEx_abi(i)+",");
				i++;
			}
		}
		Calendar t =Calendar.getInstance();
		t.setTimeInMillis((long)id.getTimeStampEx()*1000);
		info.append("\n"+"最新価格:"+id.getValue()
				+" ("
				+String.format("%04d", t.get(Calendar.YEAR))+"/"
				+String.format("%02d", t.get(Calendar.MONTH)+1)+"/"
				+String.format("%02d", t.get(Calendar.DAY_OF_MONTH))
				+" "
				+String.format("%02d", t.get(Calendar.HOUR_OF_DAY))
				+ ":"
				+ String.format("%02d", t.get(Calendar.MINUTE))
				+")");
			
		ta.setText(info.toString());
	}
}
