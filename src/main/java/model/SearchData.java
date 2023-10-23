package model;



public class SearchData {
	private int type;
	private String text;
	private long time_stamp;

	public long getTime_stamp() {
		return time_stamp;
	}

	public int getType() {
		return type;
	}

	public String getText() {
		return text;
	}

	public static SearchEnum getTypeEnum(int type) {
		for (SearchEnum data : SearchEnum.values()) {
			if (data.getType() == type)
				return data;
		}
		return SearchEnum.BOOK;
	}

	public enum SearchEnum {
		BOOK(1), AUTHOR(2), USER(3);

		private int type;

		SearchEnum(int i) {
			this.type = i;
		}

		public int getType() {
			return type;
		}
	}
}
