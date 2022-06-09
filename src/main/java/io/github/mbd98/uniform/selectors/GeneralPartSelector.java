package io.github.mbd98.uniform.selectors;

import io.github.mbd98.uniform.CadetBean;
import io.github.mbd98.uniform.Utils;
import com.google.common.collect.Table;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Function;

public final class GeneralPartSelector implements Function<CadetBean, String> {
	private final String part;
	private Table<String, String, Double> table;

	public GeneralPartSelector(String part) {
		this.part = part;
		try {
			this.table = Utils.createSizingTable(Utils.getCsvForPartType(part));
		} catch (IOException | CsvValidationException ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}

	@Override
	public String apply(CadetBean cb) {
		final StringBuilder builder = new StringBuilder(part).append('-');
		final Map<String, Double> mv = new HashMap<>(table.columnKeySet().size());
		for (String m : table.columnKeySet()) {
			mv.put(m, Utils.measurementFetch(cb, m));
		}
		builder.append(table.rowMap().entrySet().stream().filter(e -> {
			for (String m : table.columnKeySet()) {
				if (e.getValue().get(m) < mv.get(m)) {
					return false;
				}
			}
			return true;
		}).min((o1, o2) -> {
			final Iterator<String> iter = mv.keySet().iterator();
			String i;
			int cmp = 0;
			while (cmp == 0 && iter.hasNext()) {
				i = iter.next();
				cmp = Double.compare(o1.getValue().get(i), o2.getValue().get(i));
			}
			return cmp;
		}).map(Map.Entry::getKey).orElse("NONE"));
		return builder.toString();
	}
}
