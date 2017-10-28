import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Calendar;

public class GraphManager extends Canvas {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private static int highValue = 0;
	private static int lowValue = 0;
	private static int nowValue = 0;
	private static long nowTime = 0;

	private static double valueScale = 0;
	private static double minValueScale = 0;
	// static double t_scale = 1000*60*60*24*DAYS;
	private static double timeScaleHour = 3;// 3h
	private static double timeScale = 1000 * 60 * 60 * timeScaleHour * 1.25;
	private static ArrayList<Integer> data_value = new ArrayList<Integer>();
	private static ArrayList<Long> data_time = new ArrayList<Long>();
	private static int averageValue = 0;
	private static Canvas c;
	private static int timeOffset = 0;

	GraphManager(){
		c = this;
	}

	public void paint(Graphics g) {
//		c = this;
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		setBackground(Color.black);

		// 最高値と最安値の表示
		if (highValue * lowValue != 0 && (highValue != lowValue)) {
			g2.setColor(Color.RED);
			g2.drawLine(0, (int) (getHeight() * 0.15), getWidth(),
					(int) (getHeight() * 0.15));
			g2.drawLine(0, (int) (getHeight() * 0.85), getWidth(),
					(int) (getHeight() * 0.85));

			g2.drawString("最高値=" + highValue, 0, 13);
			g2.drawString("最安値=" + lowValue, 0, (int) (getHeight() * 0.90 + 13));

			g2.setColor(Color.decode("#00bfff"));
			g2.drawLine(0, get_Y(averageValue), getWidth(), get_Y(averageValue));
			g2.drawString("平均=" + averageValue, 0, get_Y(averageValue) - 13);

		}

		g2.setColor(Color.GREEN);

		if (data_value.size() > 1) {
			// 時刻ラインの表示
			g2.setColor(Color.GREEN.darker().darker());
			Calendar nowtimeline = Calendar.getInstance();
			Calendar timeline0 = Calendar.getInstance();

			nowtimeline.setTimeInMillis(nowTime);

			int i = 0;
			while (get_X(timeline0.getTimeInMillis()) > 0) {
				timeline0.set(nowtimeline.get(Calendar.YEAR),
						nowtimeline.get(Calendar.MONTH),
						nowtimeline.get(Calendar.DATE)+2, -12 * i, 0, 0);

				if (i % 2 == 0) {
					if (timeScaleHour < 24 * 7){
						g2.drawString("0:00",get_X(timeline0.getTimeInMillis()) - 10,(int)(getHeight()*0.15)-3);
						g2.setColor(Color.decode("#00ff7f"));
						g2.drawString((timeline0.get(Calendar.MONTH) + 1) + "/"+ timeline0.get(Calendar.DAY_OF_MONTH),
								get_X(timeline0.getTimeInMillis()) - 10, (int)(getHeight()*0.10)-3);
						g2.setColor(Color.GREEN.darker().darker());

					}
					else if(timeScaleHour < 24 * 7 * 4){
						g2.setColor(Color.decode("#00ff7f"));
						g2.drawString((timeline0.get(Calendar.MONTH) + 1) + "/"+ timeline0.get(Calendar.DAY_OF_MONTH),
								get_X(timeline0.getTimeInMillis()) - 10, (int)(getHeight()*0.15)-3);
						g2.setColor(Color.GREEN.darker().darker());
					}

					if(timeScaleHour < 24*7*4){
						g2.drawLine(get_X(timeline0.getTimeInMillis()),	(int) (getHeight() * 0.15) - 1,
							get_X(timeline0.getTimeInMillis()),	(int) (getHeight() * 0.85) + 1);
					}
					else{
					}
				} else if (timeScaleHour < 24 * 7) {
					g2.drawString("12:00",get_X(timeline0.getTimeInMillis()) - 13, (int)(getHeight()*0.15)-3);
					g2.drawLine(get_X(timeline0.getTimeInMillis()),(int) (getHeight() * 0.15) - 1,get_X(timeline0.getTimeInMillis()),(int) (getHeight() * 0.85) + 1);
				}

				i++;

			}

			if(timeScaleHour == 24 * 7 * 4){

				Calendar timeline_m0 = Calendar.getInstance();
				Calendar timeline_m1 = Calendar.getInstance();
				Calendar timeline_m2 = Calendar.getInstance();


				timeline_m0.set(nowtimeline.get(Calendar.YEAR),nowtimeline.get(Calendar.MONTH)  ,1,0, 0, 0);
				timeline_m1.set(nowtimeline.get(Calendar.YEAR),nowtimeline.get(Calendar.MONTH)-1,14,0, 0, 0);
				timeline_m2.set(nowtimeline.get(Calendar.YEAR),nowtimeline.get(Calendar.MONTH)-1,1,0, 0, 0);

				System.out.println(timeline_m0.get(Calendar.MONTH));


				g2.setColor(Color.decode("#00ff7f"));
				g2.drawString((timeline_m0.get(Calendar.MONTH) + 1) + "/"+ timeline_m0.get(Calendar.DAY_OF_MONTH),
						get_X(timeline_m0.getTimeInMillis()) - 10, (int)(getHeight()*0.10)-3);

				g2.setColor(Color.GREEN.darker().darker());
				g2.drawLine(get_X(timeline_m0.getTimeInMillis()),	(int) (getHeight() * 0.15) - 1,
						get_X(timeline_m0.getTimeInMillis()),	(int) (getHeight() * 0.85) + 1);

				g2.setColor(Color.decode("#00ff7f"));
				g2.drawString((timeline_m1.get(Calendar.MONTH) + 1) + "/"+ timeline_m1.get(Calendar.DAY_OF_MONTH),
						get_X(timeline_m1.getTimeInMillis()) - 10, (int)(getHeight()*0.10)-3);

				g2.setColor(Color.GREEN.darker().darker());
				g2.drawLine(get_X(timeline_m1.getTimeInMillis()),	(int) (getHeight() * 0.15) - 1,	get_X(timeline_m1.getTimeInMillis()),	(int) (getHeight() * 0.85) + 1);

			}



			i = 0;

			g2.setColor(Color.GREEN);
			// 価格が変動しているもの
			if (lowValue != highValue) {
				i = 0;
				while (i < data_value.size() - 1) {
					// 旧表示
					// g2.drawLine(get_X(data_time.get(i)),get_Y(data_value.get(i)),get_X(data_time.get(i+1)),get_Y(data_value.get(i+1)));
					// 水平線
					g2.drawLine(get_X(data_time.get(i)),
							get_Y(data_value.get(i)),
							get_X(data_time.get(i + 1)),
							get_Y(data_value.get(i)));
					// 垂直線
					g2.drawLine(get_X(data_time.get(i + 1)),
							get_Y(data_value.get(i)),
							get_X(data_time.get(i + 1)),
							get_Y(data_value.get(i + 1)));
					i++;
				}
				// System.out.println("i="+i+":"+(nowTime-data_time.get(i))+":"+get_X(data_time.get(i)));
			}
			// 価格が変動していないものは中心に線を引く
			else if (lowValue == highValue && lowValue != 0) {
				g2.drawLine(0, (int) (getHeight() / 2),
						(int) (getWidth() * 0.8), (int) (getHeight() / 2));
			}

		} else {
			g2.drawString("No Data", (int) getWidth() / 2 - 20,
					(int) getHeight() / 2);
		}

	}

	/**
	 * グラフ描写のための値の入力
	 *
	 * @param selectedRow
	 */
	public static void setGraphParam(int selectedRow) {
		System.out.println("selectedRow" + selectedRow);
		graphDataClear();

		if (selectedRow == -1)
			selectedRow = 0;

		highValue = DataManager.getHighestValue(selectedRow);
		lowValue = DataManager.getRecordLow(selectedRow);
		nowValue = DataManager.getValue(selectedRow);
		nowTime = DataManager.getTimestampL(selectedRow) + (long)(timeOffset*timeScale*0.8);

//		valueScale = (highValue - lowValue) / 0.8;
		valueScale = (highValue - lowValue) / 0.7;

		minValueScale = lowValue - valueScale * 1.5 / 10;

		int dataCount = DataManager.getSize(selectedRow);

		if (dataCount != 0) {

			data_value = new ArrayList<Integer>();
			data_time = new ArrayList<Long>();

			int i = 0;
			while (i < dataCount) {
				data_value.add(DataManager.getValue(selectedRow, i));
				data_time.add(DataManager.getTimestamp(selectedRow, i));
				i++;
			}

			//平均の計算
			int j=0;
			long valueDim = 0;
			if(dataCount > 1){
				while(j+1 < dataCount){
					valueDim += data_value.get(j)*(data_time.get(j+1)-data_time.get(j));
					j++;
				}
//				valueDim += data_value.get(j)*(data_time.get(dataCount-1)-data_time.get(j));

				averageValue = (int) (valueDim / (data_time.get(dataCount-1)-data_time.get(0)));
			}
			//データがひとつの時はそれが平均っしょ
			else{
				averageValue = data_value.get(0);
			}


		}
		c.repaint();
	}

	/**
	 * 値段をY座標に変換する関数
	 *
	 * @param value
	 * @return
	 */
	private int get_Y(int value) {
		double i = (value - minValueScale) / valueScale * getHeight();
		return (int) (getHeight() - i);
	}

	/**
	 * 時間をX座標に変換する関数
	 *
	 * @param timeStamp
	 * @return
	 */
	private int get_X(long timeStamp) {
		double minTimeScale = nowTime - (1000 * 60 * 60 * timeScaleHour);

		return (int) ((getWidth() / timeScale) * (timeStamp - minTimeScale));

	}

	public void setScaleByHour(int hour) {
		timeScaleHour = hour;
		timeScale = 1000 * 60 * 60 * timeScaleHour * 1.25;
		repaint();
	}

	public static void graphDataClear() {
		// 値のクリア
		data_value.clear();
		data_time.clear();
	}

	public void setTimeOffset(int i,int selectedRow){

		if(i != 0 && timeOffset + i <= 0){
			timeOffset = timeOffset + i;
			setGraphParam(selectedRow);
		}else
			timeOffset =0;


	}

}
