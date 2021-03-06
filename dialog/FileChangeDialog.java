import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;

import com.sooba.constant.SoobaConst;

public class FileChangeDialog extends JDialog implements ActionListener {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	Button btn_y = new Button("保存する");
	Button btn_n = new Button("保存しない");

	public FileChangeDialog(WindowSetup frame) {

		// TODO 自動生成されたコンストラクター・スタブ
		super(frame);
		// setSize(200,120);
		setModal(true);
		setLocationRelativeTo(frame);
		getContentPane().setLayout(new FlowLayout());
		Label lab = new Label("データを保存しますか？");

		getContentPane().add(lab);
		getContentPane().add(btn_y);
		getContentPane().add(btn_n);
		btn_y.addActionListener(this);
		btn_n.addActionListener(this);

		pack();
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		Object obj = e.getSource();
		if (obj == btn_y) {
			DataManager.SaveTable();
			SoobaConst.DATACHANGE = 0;
			dispose();
			System.exit(0);
		} else if (obj == btn_n) {
			dispose();
			System.exit(0);
		}

	}

}
