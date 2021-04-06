package vn.vmg.api.enumcfg;

public enum ErrorEnum {
	SUCCESS							(0),

	BAD_REQUEST						(200),
	SYSTEM_BUSY						(201),
	NOT_AUTHORIZED 					(202),
	SYSTEM_NOT_START				(203),
	SYSTEM_STARTED 					(204),
	DB_ERROR	 					(205),
	BUILD_RAND_KEY_ERR				(206),
	SEND_RESPONSE_PRIVATE_KEY_ERR	(207),

	IP_NOT_IN_WHITELIST												(100),
	SESSION_NOT_EXIST_OR_EXPIRED									(101),
	FORMAT_KEY_START_INVALID										(103),
	KEY_START_NOT_TRUE												(104),
	FORMAT_NEW_KEY_INVALID											(105),
	APP_NAME_EMPTY													(106),
	APP_NAME_ALREADY_EXIST  										(107),
	APP_ID_EMPTY 													(108),
	APP_ID_NOT_EXIST 												(109),
	APP_ID_CONFIG_ERR 												(110),
	RESPONSE_URL_EMPTY												(111),
	KEY_SIZE_ONLY_16_or_32											(112),
	SAME_KEY_IN_KEYSTART_AND_NEWKEY 								(113),
	PASSWORD_NULL_OR_INCORRECT										(114),
	NEW_PASS_MUST_INCLUDE_UPPER_LOWER_NUMBER_AND_SYMBOLS			(115),
	
	
	WRONG_LOGIN_OR_PASSWORD							(300),
	PLEASE_RELOGIN_AFTER_A_FEW_MINUTES 				(301),
	
	
	SERVER_ERROR				(500);
	
	
	
	

	
	
	
	
	
	
	
	private final int value;

	ErrorEnum(final int newValue) {
		value = newValue;
	}

	public int getValue() {
		return value;
	}

	public static ErrorEnum fromInt(int value) {
		for (ErrorEnum type : values()) {
			if (type.getValue() == value) {
				return type;
			}
		}
		return null;
	}
}
