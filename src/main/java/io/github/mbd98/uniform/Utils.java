package io.github.mbd98.uniform;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Objects;

public final class Utils {
	public static Reader getResourceReader(String rsc) {
		return new InputStreamReader(Objects.requireNonNull(UniformMain.class.getResourceAsStream(rsc), "Resource doesn't exist."));
	}

	public static Table<String, String, Double> createSizingTable(String csv) throws IOException, CsvValidationException {
		final Table<String, String, Double> table = HashBasedTable.create();
		final String[] header;
		String[] currLine;

		try (CSVReader read = new CSVReader(getResourceReader(csv))) {
			header = read.readNext();
			while ((currLine = read.readNext()) != null) {
				for (int i = 1; i < currLine.length; i++) {
					table.put(currLine[0], header[i], Double.parseDouble(currLine[i]));
				}
			}
		}
		return ImmutableTable.copyOf(table);
	}

	public static String getCsvForPartType(String part) {
		return "/" + switch (part.toLowerCase()) {
			case "belt" -> "Belt";
			case "boots" -> "Boots";
			case "fieldboots" -> "FieldBoots";
			case "fieldjacket" -> "FieldJacket";
			case "fieldpants" -> "FieldPants";
			case "necktie" -> "Necktie";
			case "pantsfemale" -> "PantsFemale";
			case "pantsmale" -> "PantsMale";
			case "parka" -> "Parka";
			case "shirtfemale" -> "ShirtFemale";
			case "shirtmale" -> "ShirtMale";
			case "socks" -> "Socks";
			case "tee" -> "Tee";
			case "tilley" -> "Tilley";
			case "tunic" -> "Tunic";
			case "wedge", "beret" -> "Wedge";
			default -> throw new IllegalArgumentException("Unknown part " + part);
		} + ".csv";
	}

	public static double measurementFetch(CadetBean cb, String m) {
		return switch (m.toLowerCase()) {
			case "head" -> cb.getHead();
			case "neck" -> cb.getNeck();
			case "chest" -> cb.getChest();
			case "waist" -> cb.getWaist();
			case "hips" -> cb.getHips();
			case "height" -> cb.getHeight();
			case "footlen" -> cb.getFootLen();
			case "footwid" -> cb.getFootWid();
			default -> Double.MAX_VALUE;
		};
	}
}
