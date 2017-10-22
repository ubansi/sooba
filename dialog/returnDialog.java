import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;

public class returnDialog extends JDialog implements ActionListener {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	Button btn_y = new Button("はい");
	Button btn_n = new Button("いいえ");
	static Label lab;
	static MyTable table;

	public returnDialog(window_setup frame) {
		super(frame);

		setModal(true);
		setLocationRelativeTo(frame);
		getContentPane().setLayout(new FlowLayout());
		lab = new Label("取り消しますか？");

		getContentPane().add(lab);
		getContentPane().add(btn_y);
		getContentPane().add(btn_n);
		btn_y.addActionListener(this);
		btn_n.addActionListener(this);

		pack();
		setVisible(true);
	}
	
	static public void setTable(MyTable NowTable){
		table = NowTable;	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj == btn_y) {
			DataManager.removeLast(table.getSelectedRow());
			GraphManager.setGraphParam(table.getSelectedRow());
			sooba_const.DATACHANGE = 1;
			dispose();
		} else if (obj == btn_n) {
			dispose();
		}

	}

}
