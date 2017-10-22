import java.awt.Button;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.net.URL;

import javax.swing.JFrame;

public class openingDialog extends JFrame {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	Button btn_y = new Button("はい");
	Button btn_n = new Button("いいえ");
	static Label lab;
	static JFrame f;
	// Image p = getToolkit().getImage("res/sooba_op.gif");
	URL url = getClass().getClassLoader().getResource("sooba_op.gif");

	Image p = getToolkit().getImage(url);

	public openingDialog(WindowSetup frame) {
		f = this;

		setLocationRelativeTo(frame);

		setSize(180, 50);
		setUndecorated(true);

		setVisible(true);
	}

	@Override
	public void paint(Graphics g) {
		g.drawImage(p, 0, 0, this);

	}

	static public void closeOpening() {
		f.dispose();
	}

}
