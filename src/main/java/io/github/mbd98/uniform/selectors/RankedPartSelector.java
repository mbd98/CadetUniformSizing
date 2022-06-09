package io.github.mbd98.uniform.selectors;

import io.github.mbd98.uniform.CadetBean;

import java.util.function.Function;

public final class RankedPartSelector implements Function<CadetBean, String> {
	private final String part;

	public RankedPartSelector(String part) {
		this.part = part;
	}

	@Override
	public String apply(CadetBean cb) {
		return part + "-" + cb.getRank();
	}
}
