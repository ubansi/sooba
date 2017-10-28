package com.sooba.view;
import java.awt.Component;
import java.awt.Label;

public class StatusInfo {
	private static Label statusLabel;

	public StatusInfo() {
		statusLabel = new Label("ステータス");
	}

	public StatusInfo(String str) {
		statusLabel = new Label(str);
	}

	public Component getStatusLabel() {
		return statusLabel;
	}

	public static void setStatusLabel(String str) {
		statusLabel.setText(str);
	}
}
