import javax.swing.DefaultCellEditor;
import javax.swing.JTextField;

public class MyCellEditor extends DefaultCellEditor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MyCellEditor(JTextField textField) {
		super(textField);
		// TODO 自動生成されたコンストラクター・スタブ
		setClickCountToStart(1);
		addCellEditorListener(new MyEdtorListener());
	}

}
