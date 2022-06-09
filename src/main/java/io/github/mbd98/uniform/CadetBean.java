package io.github.mbd98.uniform;

import com.opencsv.bean.CsvBindAndSplitByName;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

import java.io.Serializable;
import java.util.*;

public final class CadetBean implements Serializable {
	@CsvBindByName
	@CsvDate(value = "MM/dd/YYYY HH:mm:ss")
	private Date timestamp;
	@CsvBindByName(column = "Email Address")
	private String email;
	@CsvBindByName
	private int level;
	@CsvBindByName
	private String rank;
	@CsvBindByName(column = "Last name", required = true)
	private String surname;
	@CsvBindByName(column = "First name", required = true)
	private String givenName;
	@CsvBindByName(required = true)
	private Sex sex;
	@CsvBindAndSplitByName(column = "Required Items", elementType = String.class, collectionType = ArrayList.class, required = true, splitOn = ",\\s+")
	private List<String> partsNeeded;
	@CsvBindByName(required = true)
	private double head;
	@CsvBindByName(required = true)
	private double neck;
	@CsvBindByName(required = true)
	private double chest;
	@CsvBindByName(required = true)
	private double waist;
	@CsvBindByName(required = true)
	private double hips;
	@CsvBindByName(required = true)
	private double height;
	@CsvBindByName(column = "Longest Foot Length", required = true)
	private double footLen;
	@CsvBindByName(column = "Widest Foot Width", required = true)
	private double footWid;
	@CsvBindByName
	private String notes;

	public CadetBean() {
	}

	public CadetBean(Date timestamp, String email, int level, String rank, String surname, String givenName, Sex sex, List<String> partsNeeded, double head, double neck, double chest, double waist, double hips, double height, double footLen, double footWid, String notes) {
		this.timestamp = timestamp;
		this.email = email;
		this.level = level;
		this.rank = rank;
		this.surname = surname;
		this.givenName = givenName;
		this.sex = sex;
		this.partsNeeded = partsNeeded;
		this.head = head;
		this.neck = neck;
		this.chest = chest;
		this.waist = waist;
		this.hips = hips;
		this.height = height;
		this.footLen = footLen;
		this.footWid = footWid;
		this.notes = notes;
	}

	public Date getTimestamp() {
		return timestamp;
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

	public Sex getSex() {
		return sex;
	}

	public List<String> getPartsNeeded() {
		return Collections.unmodifiableList(partsNeeded);
	}

	public double getHead() {
		return head;
	}

	public double getNeck() {
		return neck;
	}

	public double getChest() {
		return chest;
	}

	public double getWaist() {
		return waist;
	}

	public double getHips() {
		return hips;
	}

	public double getHeight() {
		return height;
	}

	public double getFootLen() {
		return footLen;
	}

	public double getFootWid() {
		return footWid;
	}

	public String getNotes() {
		return notes;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof CadetBean cadetBean)) return false;
		return level == cadetBean.level && Double.compare(cadetBean.head, head) == 0 && Double.compare(cadetBean.neck, neck) == 0 && Double.compare(cadetBean.chest, chest) == 0 && Double.compare(cadetBean.waist, waist) == 0 && Double.compare(cadetBean.hips, hips) == 0 && Double.compare(cadetBean.height, height) == 0 && Double.compare(cadetBean.footLen, footLen) == 0 && Double.compare(cadetBean.footWid, footWid) == 0 && timestamp.equals(cadetBean.timestamp) && email.equals(cadetBean.email) && rank.equals(cadetBean.rank) && surname.equals(cadetBean.surname) && givenName.equals(cadetBean.givenName) && sex == cadetBean.sex && partsNeeded.equals(cadetBean.partsNeeded) && Objects.equals(notes, cadetBean.notes);
	}

	@Override
	public int hashCode() {
		return Objects.hash(timestamp, email, level, rank, surname, givenName, sex, partsNeeded, head, neck, chest, waist, hips, height, footLen, footWid, notes);
	}

	public enum Sex {
		MALE, FEMALE;
	}
}
