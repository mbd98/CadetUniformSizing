package io.github.mbd98.uniform;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Comparator;
import java.util.List;

public final class UniformMain {
	public static void main(String[] args) throws Exception {
		if (args.length != 2) {
			System.err.println("Usage: [in file] [out file]");
			System.exit(1);
		}
		final File input = new File(args[0]), output = new File(args[1]);
		final List<CadetBean> cadets;
		final StatefulBeanToCsv<UniformAssignmentBean> uacOut;
		final HeaderColumnNameMappingStrategy<UniformAssignmentBean> uacStrat;

		if (!input.exists()) {
			System.err.println("Missing \"input.csv\"");
			System.exit(1);
		}
		if (!output.exists()) {
			output.createNewFile();
		}
		cadets = new CsvToBeanBuilder<CadetBean>(new FileReader(input)).withType(CadetBean.class).build().parse();
		uacStrat = new HeaderColumnNameMappingStrategy<>();
		uacStrat.setType(UniformAssignmentBean.class);
		uacStrat.setColumnOrderOnWrite(Comparator.comparingInt(s -> switch (s.toLowerCase()) {
			case "email" -> 0;
			case "level" -> 1;
			case "rank" -> 2;
			case "surname" -> 3;
			case "givenname" -> 4;
			case "sex" -> 5;
			case "partsout" -> 6;
			case "notes" -> 7;
			default -> Integer.MAX_VALUE;
		}));
		try (Writer w = new FileWriter(output)) {
			uacOut = new StatefulBeanToCsvBuilder<UniformAssignmentBean>(w).withMappingStrategy(uacStrat).build();
			uacOut.write(cadets.stream().map(UniformAssignmentBean::new).toList());
		}
	}
}
