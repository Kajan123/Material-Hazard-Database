package utils;

import java.util.Comparator;

import objects.Materials;

public class SortByRecentlyAdded implements Comparator<Materials> {

	public int compare(Materials a, Materials b) {
		
		return -Integer.valueOf(a.getAddOrder()).compareTo(Integer.valueOf(b.getAddOrder()));
		
	}

}