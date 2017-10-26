import java.awt.Button;
import java.awt.Choice;
import java.awt.Component;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableCellEditor;

public class WindowSetup extends Frame implements ActionListener, Runnable,
		ItemListener, MouseListener, ClipboardOwner, ChangeListener {

	MenuItem fi1, fi2;
	MenuItem ed1, ed2;
	MenuItem conf1;
	StatusInfo ws_info;
	static MyTable[] Table = new MyTable[SoobaConst.MAXTAB];//MAXTAB=10

	DataManager dm = new DataManager();
	static JTabbedPane pane;

	Button b1, b2;
	Button back,forward;

	Label time;
	static Label sum_value;
	static Clipboard clipboad;
	TableCellEditor tce;
	GraphManager gm;
	static Frame f;
	/**
	 * 0:all 1:watch 2:order
	 */
	static int paneIndex = -1;

	boolean clock_switch = true;

	Thread th = new Thread(this);
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	GridBagLayout gbl = new GridBagLayout();

	void addLayout(Component comp, int x, int y, int w, int h) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = w;
		gbc.gridheight = h;

		gbl.setConstraints(comp, gbc);
		add(comp);
	}

	// ウインドウの作成
	WindowSetup() {

		f = this;
		// openingDialog od =
		new OpeningDialog(this);
		StatusInfo sf = new StatusInfo();
		ConfManager.openConf();

		setTitle("SooBa Ver" + SoobaConst.VER);

		// メニューバーの設置
		MenuBar mb = new MenuBar();
		Menu file1 = new Menu("ファイル");
		Menu edit1 = new Menu("編集");
		Menu conf = new Menu("設定");


		fi1 = new MenuItem("開く");
		fi2 = new MenuItem("保存");

		ed1 = new MenuItem("インポート");
		ed2 = new MenuItem("エクスポート");

		conf1 = new MenuItem("設定");

		fi1.addActionListener(this);
		fi2.addActionListener(this);

		ed1.addActionListener(this);
		ed2.addActionListener(this);

		conf1.addActionListener(this);

		file1.add(fi1);
		file1.add(fi2);

		edit1.add(ed1);
		edit1.add(ed2);

		conf.add(conf1);

		// メニューへ追加
		mb.add(file1);
		mb.add(edit1);
		mb.add(conf);

		// メニューバーをフレームに追加
		setMenuBar(mb);

		// 終了ボタンの実装
		addWindowListener(new Exit(this));

		// setLayout(gbl);

		Panel p1 = new Panel();
		Panel p2 = new Panel();
		// Panel p3 = new Panel();
		Panel p4 = new Panel();
		Panel p5 = new Panel();



		b1 = new Button("在庫リセット");
		b1.addActionListener(this);
		b2 = new Button("編集取り消し");
		b2.addActionListener(this);

//		TextArea ta1 = new TextArea("値段をいれると\n次のアイテム名を\n勝手にコピーするよ", 5, 30);

		gm = new GraphManager();


		Label span = new Label("表示時間");
		back = new Button("<<");
		forward = new Button(">>");

		back.addActionListener(this);
		forward.addActionListener(this);

		Choice c2 = new Choice();
		c2.add("３時間");
		c2.add("１２時間");
		c2.add("２４時間");
		c2.add("４８時間");
		c2.add("１週間");
		c2.add("４週間");

		c2.addItemListener(this);
		c2.select(SoobaConst.GRAPH_SPAN);

		if (SoobaConst.GRAPH_SPAN == 0)
			gm.setScale(3);
		else if (SoobaConst.GRAPH_SPAN == 1)
			gm.setScale(12);
		else if (SoobaConst.GRAPH_SPAN == 2)
			gm.setScale(24);
		else if (SoobaConst.GRAPH_SPAN == 3)
			gm.setScale(48);
		else if (SoobaConst.GRAPH_SPAN == 4)
			gm.setScale(24 * 7);
		else if (SoobaConst.GRAPH_SPAN == 5)
			gm.setScale(24 * 7 * 4);
		else
			gm.setScale(24);

		Label lname = new Label("現在時刻");
		lname.setAlignment(Label.RIGHT);
		time = new Label("00:00:00");
		Label sum = new Label("合計金額");
		sum_value = new Label("0");

		setLayout(gbl);


//		addLayout(scroll, 0, 0, 1, 7);

		JScrollPane[] scroll = new JScrollPane[10];

		//タブの追加
		pane = new JTabbedPane();


		for(int i = 0;i < SoobaConst.MAXTAB;i++){
			Table[i] = new MyTable(dm);
			Table[i].addMouseListener(this);
			scroll[i] = new JScrollPane(Table[i]);

		}

		pane.addTab("全部",scroll[0]);
		pane.addTab("注目",scroll[1]);

		for(int i =0;i < SoobaConst.MAXTAB -2;i++){
			pane.addTab(SoobaConst.Category[i],scroll[i+2]);
		}

		pane.addChangeListener(this);



		addLayout(pane,0,0,1,7);

		p1.setLayout(new GridLayout(1, 2));
		p1.add(b2);
		p1.add(lname);
		p1.add(time);

		p2.setLayout(new GridLayout(1, 3));
		p2.add(b1);
		p2.add(sum);
		p2.add(sum_value);

		p4.setLayout(new GridLayout(1, 3));
		p4.add(back);
		p4.add(forward);
		p4.add(span);
		p4.add(c2);

		p5.setLayout(new GridLayout(1, 1));
		p5.add(sf.get_status_label());

		addLayout(p1, 1, 0, 3, 1);
		addLayout(p2, 1, 1, 3, 1);
		addLayout(InfoManager.getTextArea(), 1, 2, 3, 1);
		// addLayout(p3, 1, 3, 3, 1);
		addLayout(p4, 1, 4, 3, 1);
		addLayout(gm, 1, 5, 3, 2);
		addLayout(p5, 0, 8, 1, 1);

		StatusInfo.set_status_label("レイアウト完了");

		th.start();
		pack();
		// setVisible(true);

		dm.OpenTable(Table[0].getRowCount());
		StatusInfo.set_status_label("ファイルを開きました   (" + Clock.getTime(Clock.HHMMSS) + ")");

		OpeningDialog.closeOpening();



		setVisible(true);
		GraphManager.setGraphParam(0);

		DataManager.setInfoData(0);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();


		if (obj == fi1) {
			StatusInfo.set_status_label("ファイルを開きました   (" + Clock.getTime(Clock.HHMMSS) + ")");
			dm.OpenTable(Table[0].getRowCount());
			GraphManager
					.setGraphParam(Table[0].getSelectedRow());
		} else if (obj == fi2) {
			DataManager.SaveTable();
			StatusInfo.set_status_label("データを保存しました   (" + Clock.getTime(1)
					+ ")");
		} else if (obj == b1) {
			StatusInfo.set_status_label("在庫がリセットされました");
			dm.stockClear();
		}
		// インポート
		if (obj == ed1) {
			System.out.println("inport");
			new InportDialog(this);
			dm.fireTableDataChanged();
		}
		// エクスポート
		if (obj == ed2) {
			System.out.println("export");
			new ExportDialog(this);
		}
		// 設定
		if (obj == conf1) {
			System.out.println("config");
			new ConfigDialog(this);
		}
		// 編集とりけし
		if (obj == b2) {
			System.out.println("return");
			ReturnDialog.setTable(Table[pane.getSelectedIndex()]);
			new ReturnDialog(this);
			dm.fireTableCellUpdated(Table[pane.getSelectedIndex()].getSelectedRow(),SoobaConst.VALUELINE);
			dm.fireTableCellUpdated(Table[pane.getSelectedIndex()].getSelectedRow(),SoobaConst.TIMESTANPLINE);
		}
		if (obj == back){
			System.out.println("back");
			gm.setTimeOffset(-1,Table[pane.getSelectedIndex()].getSelectedRow());
		}
		if (obj == forward){
			System.out.println("forWard");
			gm.setTimeOffset(1,Table[pane.getSelectedIndex()].getSelectedRow());
		}
	}

	// 時計のスレッド
	@Override
	public void run() {

		int i=0;

		while (true == clock_switch) {
			time.setText(Clock.getTime(Clock.HHMMSS));
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {

			}
			if(i == 60*5){
				dm.fireTableDataChanged();
				i=0;
			}
			i++;

		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO 自動生成されたメソッド・スタブ

		//右クリック時の処理
		if(SwingUtilities.isRightMouseButton(e)){
			int rowPoint =Table[pane.getSelectedIndex()].rowAtPoint(e.getPoint());
			Table[pane.getSelectedIndex()].changeSelection(rowPoint, 0, false,false);

			new SetDataDialog(this,rowPoint,dm);
		}
		else{
			copyClipboad(Table[pane.getSelectedIndex()].getSelectedRow());
			GraphManager.setGraphParam(Table[pane.getSelectedIndex()].getSelectedRow());
			DataManager.setInfoData(Table[pane.getSelectedIndex()].getSelectedRow());
			DataManager.printData(Table[pane.getSelectedIndex()].getSelectedRow());
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	// クリップボードのオーナーではなくなったことをキャッチ
	public void lostOwnership(Clipboard clipboard, Transferable contents) {

	}

	@Override
	public void itemStateChanged(ItemEvent e) {

		Choice cho = (Choice) e.getItemSelectable();
		if (cho.getSelectedIndex() == 0)
			gm.setScale(3);
		if (cho.getSelectedIndex() == 1)
			gm.setScale(12);
		if (cho.getSelectedIndex() == 2)
			gm.setScale(24);
		if (cho.getSelectedIndex() == 3)
			gm.setScale(48);
		if (cho.getSelectedIndex() == 4)
			gm.setScale(24 * 7);
		if (cho.getSelectedIndex() == 5)
			gm.setScale(24*7*4);

		SoobaConst.GRAPH_SPAN = cho.getSelectedIndex();
		gm.setTimeOffset(0,Table[0].getSelectedRow());
	}

	static public void copyClipboad(int row) {
		String s;
		if (null != (s = DataManager.getName(row))) {
			StatusInfo.set_status_label(s + "をクリップボードにコピーしました");

			clipboad = f.getToolkit().getSystemClipboard();
			StringSelection contents = new StringSelection(s);
			clipboad.setContents(contents, (ClipboardOwner) f);

		}
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		System.out.println("tab change ="+pane.getSelectedIndex());
		if(pane.getSelectedIndex() == 0)
			paneIndex = -1;
		if(pane.getSelectedIndex() == 1)
			paneIndex = 8;
		if(pane.getSelectedIndex() > 1)
			paneIndex = pane.getSelectedIndex()-2;

	}
}
