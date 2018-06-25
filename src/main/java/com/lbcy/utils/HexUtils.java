package com.lbcy.utils;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class HexUtils {

	/**
	 * Hex解码.
	 */
	public static String hexDecode(String input) {
		try {
			return new String(Hex.decodeHex(input.toCharArray()),"UTF-8");
		} catch (Exception e) {
			throw new IllegalStateException("Hex Decoder exception", e);
		}
	}
	
}