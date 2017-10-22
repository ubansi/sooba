import java.awt.Color;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

public class MyTable extends JTable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public MyTable(TableModel tableModel) {
		super(tableModel);

		// 1番目のカラムを取得する
		for (int i = 0; i < this.getColumnCount(); i++) {
			TableColumn colum = this.getColumnModel().getColumn(i);
			// StripeTableRenderer r =new StripeTableRenderer();
			this.setDefaultRenderer(getModel().getColumnClass(i),
					new StripeTableRenderer());

			// 値段の列の個別設定
			if (i == sooba_const.VALUELINE) {
				JTextField t = new JTextField();
				t.setForeground(Color.blue);
				colum.setCellEditor(new MyCellEditor(t));
				colum.setMaxWidth(9 * sooba_const.MAXVALUEDIGIT.length());
			} else if (i == sooba_const.NAMELINE) {
				colum.setMinWidth(100);
			}
			if (i == sooba_const.DELTA) {
				// 列の幅を文字数x10に設定
				colum.setMaxWidth(9 * sooba_const.MAXVALUEDIGIT.length());
			}
			if (i == sooba_const.TIMESTANPLINE) {
				// 列の幅を文字数x10に設定
				colum.setMaxWidth(9 * sooba_const.TIMESTANPLINESTRING.length());
			}

		}

	}

}
