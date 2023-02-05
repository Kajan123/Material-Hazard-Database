package utils;

import java.util.Comparator;

import objects.Materials;

public class SortAToZ implements Comparator<Materials> {

	public int compare(Materials a, Materials b) {
		
		return a.getName().compareTo(b.getName());
		
	}

}
