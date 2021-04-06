package vn.vmg.api.enumcfg;

public enum ApiErr {
	SUCCESS(0), 
	
	SOME_ERROR(-1),
	
	NO_DATA_FOUND(4),
	
	SYSTEM_BUSY(32);

	private final int value;

	ApiErr(final int newValue) {
		value = newValue;
	}

	public int getValue() {
		return value;
	}

	public static ApiErr fromInt(int value) {
		for (ApiErr type : values()) {
			if (type.getValue() == value) {
				return type;
			}
		}
		return null;
	}
}
