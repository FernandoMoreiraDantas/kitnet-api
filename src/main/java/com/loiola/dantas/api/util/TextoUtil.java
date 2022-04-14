package com.loiola.dantas.api.util;

public class TextoUtil {
	
	public static boolean isNullOrEmpty(String texto) {
		if (texto == null || texto.trim().equals("")) {
			return true;
		}
		return false;
	}
	
	public static boolean isCelularValido(String fone) {
		return fone.matches("^[1-9]{2}[9][1-9]{8}$");
	}
	
	public static String removerMascara(String valor) {
		if(valor != null && !valor.trim().equals("")) {
			return valor.replaceAll("[^0-9]","");
		}
		
		return null;
	}
}