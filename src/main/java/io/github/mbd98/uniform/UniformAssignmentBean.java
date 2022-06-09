package io.github.mbd98.uniform;

import io.github.mbd98.uniform.selectors.GeneralPartSelector;
import io.github.mbd98.uniform.selectors.RankedPartSelector;
import io.github.mbd98.uniform.selectors.SexedPartSelector;
import io.github.mbd98.uniform.selectors.UniversalPartSelector;
import com.opencsv.bean.CsvBindAndSplitByName;
import com.opencsv.bean.CsvBindByName;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

public final class UniformAssignmentBean implements Serializable {
	private static final Map<String, Function<CadetBean, String>> selectors = new HashMap<>(19);

	@CsvBindByName
	private String email;
	@CsvBindByName
	private int level;
	@CsvBindByName
	private String rank;
	@CsvBindByName
	private String surname;
	@CsvBindByName
	private String givenName;
	@CsvBindByName
	private CadetBean.Sex sex;
	@CsvBindAndSplitByName(elementType = String.class, collectionType = ArrayList.class, writeDelimiter = ", ")
	private List<String> partsOut;
	@CsvBindByName
	private String notes;

	public UniformAssignmentBean() {
	}

	UniformAssignmentBean(CadetBean cb) {
		email = cb.getEmail();
		level = cb.getLevel();
		rank = cb.getRank();
		surname = cb.getSurname();
		givenName = cb.getGivenName();
		sex = cb.getSex();
		notes = cb.getNotes();
		partsOut = cb.getPartsNeeded().stream()
				.map(s -> s.replaceAll("\\s+", ""))
				.flatMap(s -> s.equalsIgnoreCase("everything")
						? Stream.of("Wedge","Beret","Tilley","Necktie","Shirt","Tee","Tunic","Belt","Pants","Socks","Boots","Tuque","Parka","FieldJacket","FieldPants","FieldBoots","Epaulettes","RankSewOns","SquadronBadge")
						: Stream.of(s))
				.distinct()
				.map(s -> selectors.computeIfAbsent(s.toLowerCase(), k -> switch (k) {
					case "pants", "shirt" -> new SexedPartSelector(s);
					case "epaulettes", "ranksewons" -> new RankedPartSelector(s);
					case "tuque", "squadronbadge" -> new UniversalPartSelector(s);
					default -> new GeneralPartSelector(s);
				}).apply(cb))
				.toList();
	}

	public String getEmail() {
		return email;
	}

	public int getLevel() {
		return level;
	}

	public String getRank() {
		return rank;
	}

	public String getSurname() {
		return surname;
	}

	public String getGivenName() {
		return givenName;
	}

	public CadetBean.Sex getSex() {
		return sex;
	}

	public List<String> getPartsOut() {
		return Collections.unmodifiableList(partsOut);
	}

	public String getNotes() {
		return notes;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof UniformAssignmentBean that)) return false;
		return level == that.level && email.equals(that.email) && rank.equals(that.rank) && surname.equals(that.surname) && givenName.equals(that.givenName) && sex == that.sex && partsOut.equals(that.partsOut) && notes.equals(that.notes);
	}

	@Override
	public int hashCode() {
		return Objects.hash(email, level, rank, surname, givenName, sex, partsOut, notes);
	}
}
