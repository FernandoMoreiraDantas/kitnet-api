package com.loiola.dantas.api.util;

public class TextoUtil {
	
	public static boolean isNullOrEmpty(String texto) {
		if (texto == null || texto.trim().equals("")) {
			return true;
		}
		return false;
	}
}
