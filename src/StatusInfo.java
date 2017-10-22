import java.awt.Component;
import java.awt.Label;

public class StatusInfo {
	static Label status_label;

	StatusInfo() {
		status_label = new Label("ステータス");

	}

	StatusInfo(String str) {
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
