package io.github.mbd98.uniform.selectors;

import io.github.mbd98.uniform.CadetBean;

import java.util.function.Function;

public final class UniversalPartSelector implements Function<CadetBean, String> {
	private final String part;

	public UniversalPartSelector(String part) {
		this.part = part;
	}

	@Override
	public String apply(CadetBean cadetBean) {
		return part + "-UNI";
	}
}
