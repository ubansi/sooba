import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Choice;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JDialog;


public class setDataDialog extends JDialog implements ActionListener, ItemListener {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	Component field[] = new Component[10];
	String[] labelName = {"表示名","アイテム名","分類","属性","スロット数","特殊能力"};
	Choice category,element,slot;
	TextField textfield[] = new TextField[8];
	Button b_yes = new Button("確定");
	Button b_cancel = new Button("キャンセル");
	Button b_delete = new Button("削除");
	dataManager dm;
	Checkbox watch_check;

	int row=0;
	int maxpanel = 6;
	int maxabi = 8;

	setDataDialog(window_setup frame,int row,dataManager dm){

	super(frame);
	setModal(true);
	setLocationRelativeTo(frame);

	setTitle("アイテムの追加");

	this.row = row;
	this.dm = dm;



	Panel[] p = new Panel[maxpanel];
	Panel[] ex_abi = new Panel[maxabi/2];

	Panel buttonPanel = new Panel();
	Panel buttonPanel2 = new Panel();

	for(int i=0;i<maxpanel;i++){
		p[i] = new Panel();
	}
	for(int i=0;i<maxabi/2;i++)
		ex_abi[i] = new Panel();


	field[0] = new TextField(dataManager.getNameSD(row),15);
	field[1] = new TextField(dataManager.getItemNameSD(row),15);

	//分類
	category = new Choice();
	for(int i=0;i<8;i++)
		category.add(sooba_const.Category[i]);

	category.add("---");
	category.select(dataManager.getCategory(row));
	category.addItemListener(this);

	field[2] = category;


	//属性
	element = new Choice();

	for(int i=0;i < 7;i++)
		element.add(sooba_const.Element[i]);
	element.add("---");

	element.select(dataManager.getElement(row));
	element.addItemListener(this);

	if(category.getSelectedIndex() != 0)
		element.setEnabled(false);


	field[3] = element;

	//スロット
	slot = new Choice();
	for(int i=0;i < 9;i++)
		slot.add(""+i);
	slot.select(dataManager.getSlot(row));
	slot.addItemListener(this);

	if(category.getSelectedIndex() > 1)
		slot.setEnabled(false);

	field[4] = slot;

	field[5] = new Panel();



//	setLayout(new GridLayout(12,2));
	setLayout(new GridLayout(13,2));

	Panel watch = new Panel();
	watch_check = new Checkbox("注目リストに追加する");

	watch_check.setState(dataManager.getWatch(row));

	watch_check.addItemListener(this);
	watch.add(watch_check);

	add(watch);


	for(int i=0;i<maxpanel;i++)
		add(p[i]);


	for(int i=0;i<maxabi/2;i++)
		add(ex_abi[i]);

	add(buttonPanel);
	buttonPanel.setLayout(new GridLayout(1,2));
	buttonPanel.add(b_yes);
	buttonPanel.add(b_cancel);
	
	add(buttonPanel2);
	buttonPanel2.setLayout(new GridLayout(1,1));
	buttonPanel2.add(b_delete);

	b_yes.addActionListener(this);
	b_cancel.addActionListener(this);
	b_delete.addActionListener(this);



	for(int i=0;i<maxpanel;i++){
		p[i].setLayout(new GridLayout(1,2));
		p[i].add(new Label(labelName[i]));
		p[i].add(field[i]);
	}

	int k = 0;
	for(int j=0;j<4;j++){
		ex_abi[j].setLayout(new GridLayout(1,2));

		for(int i=0;i<2;i++){
			textfield[k] = new TextField();
			ex_abi[j].add(textfield[k]);
			k++;
		}
	}

	for(int i=0; i< maxabi;i++)
		if(dataManager.getExabi(row) != null)
			if(dataManager.getExabi(row)[i] != null)
				textfield[i].setText(dataManager.getExabi(row)[i]);

	for(int i=dataManager.getSlot(row);i<maxabi;i++)
		textfield[i].setEditable(false);


	pack();
	setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		Object obj = e.getSource();

		if(obj == b_yes){
			if(((TextField) field[0]).getText().equals("") == false)
			if(category.getSelectedIndex() != 8)
			{
				ItemData newItem = new ItemData();

				newItem.setName(((TextField) field[0]).getText());

				if(((TextField) field[1]).getText().equals(""))
					newItem.setItemName("noname");
				else
					newItem.setItemName(((TextField) field[1]).getText());

				newItem.setCategory(category.getSelectedIndex());
				newItem.setElement(element.getSelectedIndex());
				newItem.setSlot(slot.getSelectedIndex());

				if(watch_check.getState())
					newItem.setWatch(1);


				for(int i=0;i < slot.getSelectedIndex();i++)
					newItem.setEx_abi(i,textfield[i].getText());


//				newItem.printAllData();

				dm.setNewItem(newItem,row);

				dispose();
			}

		}

		if(obj == b_cancel){
			dispose();
		}
		if(category.getSelectedIndex() != 8)
			if(obj == b_delete){
				dm.remomveData(row);
				dm.fireTableRowsDeleted(row,row);
				
				dispose();
			}

	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		Object cho = (Object) e.getItemSelectable();

		if(cho == category){
			System.out.println("Category Change:"+((Choice) cho).getSelectedItem());
			if(category.getSelectedIndex() != 0){
				element.select(7);
				element.setEnabled(false);
			}
			if(category.getSelectedIndex() == 0)
				element.setEnabled(true);
			if(category.getSelectedIndex() >1){
				slot.setEnabled(false);
			}
			else{
				slot.setEnabled(true);
			}
		}

		if(cho == slot){
			for(int i=0;i<slot.getSelectedIndex();i++)
				textfield[i].setEditable(true);

			for(int i=slot.getSelectedIndex();i<maxabi;i++)
				textfield[i].setEditable(false);

			System.out.println("slot Change:"+((Choice) cho).getSelectedItem());
		}

		if(cho == watch_check){
			System.out.println("Category Change:"+watch_check.getState());

		}

	}

}
