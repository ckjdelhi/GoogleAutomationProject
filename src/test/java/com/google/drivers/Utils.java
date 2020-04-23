package com.google.drivers;

public class Utils {
	public static String extractText(String text) {
		String searchText = text;
		if(!searchText.isEmpty() && searchText.contains("=") && searchText.contains("&")) {
			searchText = searchText.substring(searchText.indexOf('=')+1, searchText.indexOf('&'));
		}
		return searchText;
	}
	public static String removeSpecialChars(String text) {
		if(!text.isEmpty() && text.contains("+")) {
			text = text.replace("+", " ");
		}
		return text;
	}
}
