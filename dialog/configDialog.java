import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JDialog;

public class configDialog extends JDialog implements ActionListener {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	Button btn_y = new Button("適用");
	Button btn_n = new Button("キャンセル");
	Checkbox c1 = new Checkbox("インポート時に足りないものを最後に追加する");

	public configDialog(window_setup frame) {

		super(frame);
		setModal(true);
		setLocationRelativeTo(frame);
		setTitle("設定");

		Panel p1 = new Panel();
		Panel bPanel = new Panel();
		p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS));
		bPanel.setLayout(new BoxLayout(bPanel, BoxLayout.X_AXIS));

		p1.add(c1);

		bPanel.add(btn_y);
		bPanel.add(btn_n);
		p1.add(bPanel);

		getContentPane().add(p1);

		if(sooba_const.INPORTNEW == 1)
			c1.setState(rootPaneCheckingEnabled);
		
		btn_y.addActionListener(this);
		btn_n.addActionListener(this);

		pack();
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		Object obj = e.getSource();
		if (obj == btn_y) {
			if (c1.getState()) {
				// 追加する
				sooba_const.INPORTNEW = 1;
			} else {
				sooba_const.INPORTNEW = 0;
			}

		} else if (obj == btn_n) {
			// dispose();
		}

		dispose();

	}

}
