import java.util.Calendar;

public class clock {

	static String y, mo, d, h, m, s;
	static Calendar now = Calendar.getInstance();

	/**
	 * 時間の取得関数
	 * 
	 * @param mode
	 *            0:秒表示なし 1:秒表示有り
	 * @return 時刻 00:00:00 形式のString値
	 */
	static String getTime(int mode) {
		h = String.format("%02d",
				Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
		m = String.format("%02d", Calendar.getInstance().get(Calendar.MINUTE));
		s = String.format("%02d", Calendar.getInstance().get(Calendar.SECOND));
		if (mode == 1)
			return (h + ":" + m + ":" + s);
		else if (mode == 0)
			return (h + ":" + m);
		else
			return "mode error";
	}

	static long getTimeNumLong() {

		now = Calendar.getInstance();
		return now.getTimeInMillis();
	}
}
