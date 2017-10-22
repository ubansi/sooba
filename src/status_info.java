import java.awt.Component;
import java.awt.Label;

public class status_info {
	static Label status_label;

	status_info() {
		status_label = new Label("ステータス");

	}

	status_info(String str) {
		status_label = new Label(str);
	}

	Component get_status_label() {
		return status_label;
	}

	static int set_status_label(String str) {
		status_label.setText(str);
		return 0;
	}
}
