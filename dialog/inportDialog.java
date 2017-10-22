import java.awt.Button;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class inportDialog extends JDialog implements ActionListener {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	Button btn_y = new Button("インポートする");
	Button btn_n = new Button("キャンセル");
	JTextArea ta = new JTextArea();

	public inportDialog(window_setup frame) {

		super(frame);
		setModal(true);
		setLocationRelativeTo(frame);

		Panel p1 = new Panel();
		Panel bPanel = new Panel();
		p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS));
		bPanel.setLayout(new BoxLayout(bPanel, BoxLayout.X_AXIS));

		Label lab = new Label("インポート用データ");

		ta.setLineWrap(true);
		ta.setColumns(30);
		ta.setRows(5);

		JScrollPane sc = new JScrollPane(ta);

		p1.add(lab);
		p1.add(sc);

		bPanel.add(btn_y);
		bPanel.add(btn_n);
		p1.add(bPanel);

		getContentPane().add(p1);

		btn_y.addActionListener(this);
		btn_n.addActionListener(this);

		pack();
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		Object obj = e.getSource();
		if (obj == btn_y) {
			String inportString = ta.getText();

			String[] strAry = inportString.split(",");
			ArrayList<ItemData> inItemDataList = new ArrayList<ItemData>();


			int i = 0;
			int j = 0;
			int row = 0;
			while (i < strAry.length) {

				// データの先頭を見つける
				if (strAry[i].equals("SooBaExportData07")) {
					i++;
					j = 0;

					// データの終わりまで挿入
					while (strAry[i].equals("ExportEnd") != true) {
						if (j == 0) {
							inItemDataList.add(new ItemData());
							inItemDataList.get(row).setItemName(strAry[i]);
						}
						if (j == 1) {
							try {
								inItemDataList.get(row).setCategory(Integer.parseInt(strAry[i]));
							} catch (Exception e1) {
								e1.printStackTrace();
								inItemDataList.get(row).setCategory(-1);
							}
						}
						//装備以外の場合
						if(inItemDataList.get(row).getCategory() > 1){
							if (j == 2) {
								try {
									inItemDataList.get(row).setValueF(Integer.parseInt(strAry[i]));
								} catch (Exception e1) {
									e1.printStackTrace();
									inItemDataList.get(row).setValueF(0);
								}
							}
							if (j == 3) {
								System.out.print(" time =" + strAry[i]);

								try {
									inItemDataList.get(row).setBaseTimeStamp(Integer.parseInt(strAry[i]));
								} catch (Exception e1) {
									e1.printStackTrace();
									inItemDataList.get(row).setBaseTimeStamp(0);
								}
							}

						}
						//ユニット、武器のエクスポートの時
						else if(inItemDataList.get(row).getCategory() < 2){
							if(j == 2){
								inItemDataList.get(row).setElement(Integer.parseInt(strAry[i]));
							}
							
							if(j == 3){
								inItemDataList.get(row).setSlot(Integer.parseInt(strAry[i]));
							}
							if(j >= 4 && j < 4+inItemDataList.get(row).getSlot()){
								inItemDataList.get(row).setEx_abi(j-4,strAry[i]);
							}
							if(j == 4+inItemDataList.get(row).getSlot()){
								try {
									inItemDataList.get(row).setValueF(Integer.parseInt(strAry[i]));
								} catch (Exception e1) {
									e1.printStackTrace();
									inItemDataList.get(row).setValueF(0);
								}
							}
							if(j == 5+inItemDataList.get(row).getSlot()){
								try {
									inItemDataList.get(row).setBaseTimeStamp(Integer.parseInt(strAry[i]));
								} catch (Exception e1) {
									e1.printStackTrace();
									inItemDataList.get(row).setBaseTimeStamp(0);
								}
							}

						}
						
						j++;
						i++;
						
						if(inItemDataList.get(row).getCategory() > 1){
							if(j == 4){
								j=0;row++;
							}
						}else{
							if(j == 6+inItemDataList.get(row).getSlot()){
								j=0;row++;
							}
						}
					}


				}

				i++;
			}
			
			int k = 0;
			while (k < inItemDataList.size()) {
				// データを挿入する行
				int dataLine = -1;
				System.out.println("serching = "+inItemDataList.get(k).getItemName());
				if(inItemDataList.get(k).getCategory() > 1){

					dataLine = DataManager.serchObj(inItemDataList.get(k).getItemName());
	
				}else{
					dataLine = DataManager.serchObj(inItemDataList.get(k));
				}

				
				if (dataLine != -1) {
					// インポートデータを挿入
					DataManager.setValueInport(inItemDataList.get(k).getValue(), inItemDataList.get(k).getBaseTimeStamp(),dataLine);
					SoobaConst.DATACHANGE = 1;

				}
				// 名前が見つからなかった場合は最後尾に追加
				else {
					if (SoobaConst.INPORTNEW == 1) {
						DataManager.addLast(inItemDataList.get(k));
						SoobaConst.DATACHANGE = 1;
					}
				}
				
				k++;				
			}

			StatusInfo.set_status_label("インポートが完了しました");
			
			dispose();
		} else if (obj == btn_n) {
			dispose();
		}

	}

}
