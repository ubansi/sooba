import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;

public class MyEdtorListener implements CellEditorListener {

	@Override
	public void editingStopped(ChangeEvent e) {
		//TODO
		// 編集完了時のリスナ(1列目の値段エリアのみ)
		// System.out.println("editing Stop"+tableManager.getCol()+":"+tableManager.getRow());
		// tableManager.SetCell(tableManager.getRow(),tableManager.getCol()+1,clock.getTimeNum());

//		System.out.println("edit end" + tableManager.getRow());
		GraphManager.setGraphParam(WindowSetup.Table[WindowSetup.pane.getSelectedIndex()].getSelectedRow());
		
		// 編集後次の行のアイテム名をコピー
		WindowSetup.copyClipboad(WindowSetup.Table[WindowSetup.pane.getSelectedIndex()].getSelectedRow()+1);

	}

	@Override
	public void editingCanceled(ChangeEvent e) {
		System.out.println("editing cancell");
	}

}
