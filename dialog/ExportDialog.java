import java.awt.Button;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ExportDialog extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Button btn_y = new Button("コピー");
	Button btn_n = new Button("キャンセル");
	StringBuffer exportString = new StringBuffer(",SooBaExportData07,");
	JTextArea ta = new JTextArea();

	public ExportDialog(WindowSetup frame) {

		// TODO 自動生成されたコンストラクター・スタブ
		super(frame);
		setModal(true);
		setLocationRelativeTo(frame);

		Panel p1 = new Panel();
		Panel bPanel = new Panel();
		p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS));
		bPanel.setLayout(new BoxLayout(bPanel, BoxLayout.X_AXIS));

		Label lab = new Label("エクスポート用データ");

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

		int i = 0;
		while (i < DataManager.getSize()) {
			if (DataManager.getNameEx(i) != null) {
				System.out.println("dataTime"+DataManager.getTimestampL(i));
				System.out.println("nowTime"+Calendar.getInstance().getTimeInMillis());
				System.out.println("delay"+( Calendar.getInstance().getTimeInMillis() - (long)DataManager.getTimestampEx(i)*1000));
				System.out.println();
				
				if( Calendar.getInstance().getTimeInMillis() - (long)DataManager.getTimestampEx(i)*1000 < 120*60*1000){
					exportString.append(DataManager.getItemDataEx(i));
					exportString.append(DataManager.getValueEx(i) + ",");
					exportString.append(DataManager.getTimestampEx(i) + ",");
				}
			}
			i++;
		}
		exportString.append("ExportEnd,");

		ta.setText(exportString.toString());
		ta.setEditable(false);
		ta.selectAll();

		pack();
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自動生成されたメソッド・スタブ

		Object obj = e.getSource();
		if (obj == btn_y) {
			ta.copy();

		} else if (obj == btn_n) {
			dispose();
		}

	}

}
