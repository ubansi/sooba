import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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

	private static ArrayList<Integer> dataPrice = new ArrayList<Integer>();
	private static ArrayList<Long> dataTime = new ArrayList<Long>();
	private static int averageValue = 0;
	private static Canvas c;
	private static int timeOffset = 0;

	private static final Color BACK_GROUND = Color.BLACK;
	private static final Color PRICE_LIMIT = Color.RED;
	private static final Color TIME_SCALE = Color.GREEN.darker().darker();
	private static final Color DATE = Color.decode("#00ff7f");
	private static final Color AVERAGE = Color.decode("#00bfff");
	private static final Color PRICE = Color.GREEN;

	public GraphManager() {
		c = this;
	}

	@Override
	public void paint(Graphics g) {
		//		c = this;
		Graphics2D graph = (Graphics2D) g;
		graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		setBackground(BACK_GROUND);

		// 最高値と最安値の表示
		if (highValue * lowValue != 0 && (highValue != lowValue)) {
			graph.setColor(PRICE_LIMIT);
			graph.drawLine(0, (int) (getHeight() * 0.15), getWidth(),
					(int) (getHeight() * 0.15));
			graph.drawLine(0, (int) (getHeight() * 0.85), getWidth(),
					(int) (getHeight() * 0.85));

			graph.drawString("最高値=" + highValue, 0, 13);
			graph.drawString("最安値=" + lowValue, 0, (int) (getHeight() * 0.90 + 13));

			graph.setColor(AVERAGE);
			graph.drawLine(0, priceToY(averageValue), getWidth(), priceToY(averageValue));
			graph.drawString("平均=" + averageValue, 0, priceToY(averageValue) - 13);

		}

		graph.setColor(PRICE);

		if (dataPrice.size() > 1) {
			// 時刻ラインの表示
			graph.setColor(TIME_SCALE);
			Calendar nowtimeline = Calendar.getInstance();
			Calendar timeline0 = Calendar.getInstance();

			nowtimeline.setTimeInMillis(nowTime);

			int i = 0;
			while (timestampToX(timeline0.getTimeInMillis()) > 0) {
				timeline0.set(nowtimeline.get(Calendar.YEAR),
						nowtimeline.get(Calendar.MONTH),
						nowtimeline.get(Calendar.DATE) + 2, -12 * i, 0, 0);
				int timeline0X = timestampToX(timeline0.getTimeInMillis());

				if (i % 2 == 0) {
					if (timeScaleHour < 24 * 7) {
						graph.drawString("0:00", timeline0X - 10, (int) (getHeight() * 0.15) - 3);
						graph.setColor(DATE);
						graph.drawString(getDateString(timeline0),
								timeline0X - 10, (int) (getHeight() * 0.10) - 3);
						graph.setColor(TIME_SCALE);

					} else if (timeScaleHour < 24 * 7 * 4) {
						graph.setColor(DATE);
						graph.drawString(getDateString(timeline0),
								timeline0X - 10, (int) (getHeight() * 0.15) - 3);
						graph.setColor(TIME_SCALE);
					}

					if (timeScaleHour < 24 * 7 * 4) {
						graph.drawLine(timeline0X, (int) (getHeight() * 0.15) - 1,
								timeline0X, (int) (getHeight() * 0.85) + 1);
					} else {
					}
				} else if (timeScaleHour < 24 * 7) {
					graph.drawString("12:00", timeline0X - 13, (int) (getHeight() * 0.15) - 3);
					graph.drawLine(timeline0X, (int) (getHeight() * 0.15) - 1, timeline0X, (int) (getHeight() * 0.85) + 1);
				}

				i++;

			}

			if (timeScaleHour == 24 * 7 * 4) {

				Calendar timeline_m0 = Calendar.getInstance();
				Calendar timeline_m1 = Calendar.getInstance();
				Calendar timeline_m2 = Calendar.getInstance();

				timeline_m0.set(nowtimeline.get(Calendar.YEAR), nowtimeline.get(Calendar.MONTH), 1, 0, 0, 0);
				timeline_m1.set(nowtimeline.get(Calendar.YEAR), nowtimeline.get(Calendar.MONTH) - 1, 14, 0, 0, 0);
				timeline_m2.set(nowtimeline.get(Calendar.YEAR), nowtimeline.get(Calendar.MONTH) - 1, 1, 0, 0, 0);

				System.out.println(timeline_m0.get(Calendar.MONTH));

				graph.setColor(DATE);
				graph.drawString(getDateString(timeline_m0),
						timestampToX(timeline_m0.getTimeInMillis()) - 10, (int) (getHeight() * 0.10) - 3);

				graph.setColor(TIME_SCALE);
				graph.drawLine(timestampToX(timeline_m0.getTimeInMillis()), (int) (getHeight() * 0.15) - 1,
						timestampToX(timeline_m0.getTimeInMillis()), (int) (getHeight() * 0.85) + 1);

				graph.setColor(DATE);
				graph.drawString(getDateString(timeline_m1),
						timestampToX(timeline_m1.getTimeInMillis()) - 10, (int) (getHeight() * 0.10) - 3);

				graph.setColor(TIME_SCALE);
				graph.drawLine(timestampToX(timeline_m1.getTimeInMillis()), (int) (getHeight() * 0.15) - 1,
						timestampToX(timeline_m1.getTimeInMillis()), (int) (getHeight() * 0.85) + 1);

			}

			i = 0;

			graph.setColor(PRICE);
			// 価格が変動しているもの
			if (lowValue != highValue) {
				drawPrice(dataPrice, dataTime, graph);
			}
			// 価格が変動していないものは中心に線を引く
			else if (lowValue == highValue && lowValue != 0) {
				int halfHeight = (int) (getHeight()/2);

				graph.drawLine(0, halfHeight,(int) (getWidth() * 0.8), halfHeight);
			}

		} else {
			graph.drawString("No Data", (int) getWidth() / 2 - 20,
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
		nowTime = DataManager.getTimestampL(selectedRow) + (long) (timeOffset * timeScale * 0.8);

		//		valueScale = (highValue - lowValue) / 0.8;
		valueScale = (highValue - lowValue) / 0.7;

		minValueScale = lowValue - valueScale * 1.5 / 10;

		int dataCount = DataManager.getSize(selectedRow);

		if (dataCount != 0) {

			dataPrice = new ArrayList<Integer>();
			dataTime = new ArrayList<Long>();

			int i = 0;
			while (i < dataCount) {
				dataPrice.add(DataManager.getValue(selectedRow, i));
				dataTime.add(DataManager.getTimestamp(selectedRow, i));
				i++;
			}

			//平均の計算
			int j = 0;
			long valueDim = 0;
			if (dataCount > 1) {
				while (j + 1 < dataCount) {
					valueDim += dataPrice.get(j) * (dataTime.get(j + 1) - dataTime.get(j));
					j++;
				}
				//				valueDim += data_value.get(j)*(data_time.get(dataCount-1)-data_time.get(j));

				averageValue = (int) (valueDim / (dataTime.get(dataCount - 1) - dataTime.get(0)));
			}
			//データがひとつの時はそれが平均っしょ
			else {
				averageValue = dataPrice.get(0);
			}

		}
		c.repaint();
	}

	/**
	 * 値段をY座標に変換する関数
	 *
	 * @param price
	 * @return
	 */
	private int priceToY(int price) {
		double i = (price - minValueScale) / valueScale * getHeight();
		return (int) (getHeight() - i);
	}

	/**
	 * 時間をX座標に変換する関数
	 *
	 * @param timeStamp
	 * @return
	 */
	private int timestampToX(long timeStamp) {
		double minTimeScale = nowTime - (1000 * 60 * 60 * timeScaleHour);

		return (int) ((getWidth() / timeScale) * (timeStamp - minTimeScale));

	}

	/**
	 * グラフに表示する期間の変更
	 *
	 * @param hour
	 */
	public void setScaleByHour(int hour) {
		timeScaleHour = hour;
		timeScale = 1000 * 60 * 60 * timeScaleHour * 1.25;
		repaint();
	}

	public static void graphDataClear() {
		// 値のクリア
		dataPrice.clear();
		dataTime.clear();
	}

	public void setTimeOffset(int i, int selectedRow) {

		if (i != 0 && timeOffset + i <= 0) {
			timeOffset = timeOffset + i;
			setGraphParam(selectedRow);
		} else
			timeOffset = 0;

	}

	/**
	 * カレンダーの文字列表現 1/1 の形式で出力する
	 * @param calendar
	 * @return
	 */
	private static String getDateString(Calendar calendar) {
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);

		return month + "/" + day;
	}

	/**
	 * 価格情報の描画関数
	 * @param prices 値段配列
	 * @param times 時間配列
	 * @param graph 描写対象のグラフ
	 */
	private void drawPrice(final List<Integer> prices, final List<Long> times, Graphics2D graph) {

		int i = 0;
		while (i < prices.size() - 1) {

			int priceY = priceToY(prices.get(i));
			int timeX = timestampToX(times.get(i));
			int nextPriceY = priceToY(prices.get(i + 1));
			int nextTimeX = timestampToX(times.get(i + 1));

			// 水平線
			graph.drawLine(timeX, priceY, nextTimeX, priceY);

			// 垂直線
			graph.drawLine(nextTimeX, priceY, nextTimeX, nextPriceY);

			i++;
		}
	}
}
