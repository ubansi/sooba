import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class exit implements WindowListener {
	window_setup frame;

	exit(window_setup window_setup) {
		this.frame = window_setup;
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		confManager.saveConf();

		if (sooba_const.DATACHANGE != 0) {

			// fileChangeDialog dlg =
			new fileChangeDialog(frame);

		} else {
			// データのクリア
			graphManager.graphDataClear();
			dataManager.dataClear();
			frame.dispose();
			System.exit(0);
		}

	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

}
