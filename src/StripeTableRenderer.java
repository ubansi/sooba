import java.awt.Color;
import java.awt.Component;
import java.util.Calendar;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
//オーバーライドをEclipseでやったら動いた。
//http://terai.xrea.jp/Swing/StripeTable.html#notefoot_1

public class StripeTableRenderer extends DefaultTableCellRenderer {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private static final Color evenColor = new Color(240, 240, 255);
	private static final Color D_color = new Color(255,0,0);
	private static Calendar c = Calendar.getInstance();

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		// TODO 自動生成されたメソッド・スタブ
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
				row, column);
		if (isSelected) {
			setForeground(table.getSelectionForeground());
			setBackground(table.getSelectionBackground());
		} else {
			setForeground(table.getForeground());
			setBackground((row % 2 == 0) ? evenColor : table.getBackground());
		}

		setHorizontalAlignment((value instanceof Number) ? RIGHT : LEFT);

		if (column == sooba_const.TIMESTANPLINE) {
			setForeground(Color.GRAY.darker());
			setHorizontalAlignment(CENTER);
			
			if(dataManager.getName(row)!=null){
				c = Calendar.getInstance();
				if(c.getTimeInMillis() - dataManager.getTimestampL(row) > 1000*60*60){
					setForeground(D_color);
			}
			}
		}
		if (column == sooba_const.NAMELINE)
			setHorizontalAlignment(LEFT);
		if (column == sooba_const.VALUELINE) {
			setHorizontalAlignment(RIGHT);
		}
		if (column == sooba_const.DELTA) {
			setHorizontalAlignment(RIGHT);
		}
		
//		System.out.println("table cell renderer");
		


		return this;
	}

}
