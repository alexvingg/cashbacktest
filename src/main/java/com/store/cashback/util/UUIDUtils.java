package com.store.cashback.util;

import java.util.UUID;

public final class UUIDUtils {
	
	private UUIDUtils() {
		
	}

	public static String cleanUuid() {
		return String.valueOf(UUID.randomUUID()).replace("-", "");
	}

}
