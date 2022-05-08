package com.loiola.dantas.api.util;

public class Util {
	
	private static final int[] pesoCPF = { 11, 10, 9, 8, 7, 6, 5, 4, 3, 2 };
	private static final int[] pesoCNPJ = { 6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2 };
	
	/**
	 * Funcção para validação de CPF
	 * @param cpf
	 * @return
	 */
	public static boolean isValidCPF(String cpf) {
		if ((cpf == null) || (cpf.length() != 11))
			return false;

		for (int j = 0; j < 10; j++)
			if (padLeft(Integer.toString(j), Character.forDigit(j, 10)).equals(cpf))
				return false;

		Integer digito1 = calcularDigito(cpf.substring(0, 9), pesoCPF);
		Integer digito2 = calcularDigito(cpf.substring(0, 9) + digito1, pesoCPF);
		return cpf.equals(cpf.substring(0, 9) + digito1.toString() + digito2.toString());
	}
	
	/**
	 * Função para validação de CNPJ
	 * @param cnpj
	 * @return
	 */
	public static boolean isValidCNPJ(String cnpj) {
		cnpj = cnpj.trim().replace(".", "").replace("-", "");
		if ((cnpj == null) || (cnpj.length() != 14))
			return false;

		Integer digito1 = calcularDigito(cnpj.substring(0, 12), pesoCNPJ);
		Integer digito2 = calcularDigito(cnpj.substring(0, 12) + digito1, pesoCNPJ);
		return cnpj.equals(cnpj.substring(0, 12) + digito1.toString() + digito2.toString());
	}
	
	/**
	 * Função que verifica se uma string é null ou está vazia
	 * @param texto
	 * @return
	 */
	public static boolean isNullOrEmpty(String texto) {
		if (texto == null || texto.trim().equals("")) {
			return true;
		}
		return false;
	}
	
	/**
	 * Função que verifica se um celular é valido
	 * @param fone
	 * @return
	 */
	public static boolean isCelularValido(String fone) {
		return fone.matches("^[1-9]{2}[9][1-9]{8}$");
	}
	
	/**
	 * Função que remove a mascara e retorna apenas numeros
	 * @param valor
	 * @return
	 */
	public static String removerMascara(String valor) {
		if(valor != null && !valor.trim().equals("")) {
			return valor.replaceAll("[^0-9]","");
		}
		
		return null;
	}
	
	
	private static int calcularDigito(String str, int[] peso) {
		int soma = 0;
		for (int indice = str.length() - 1, digito; indice >= 0; indice--) {
			digito = Integer.parseInt(str.substring(indice, indice + 1));
			soma += digito * peso[peso.length - str.length() + indice];
		}
		soma = 11 - soma % 11;
		return soma > 9 ? 0 : soma;
	}

	private static String padLeft(String text, char character) {
		return String.format("%11s", text).replace(' ', character);
	}

}
