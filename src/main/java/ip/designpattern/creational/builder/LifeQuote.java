package ip.designpattern.creational.builder;

import java.util.List;

public class LifeQuote extends Quote {

	private String fna;
	
	public LifeQuote(Prospect prospect, Insured insured, List<Insured> additionalInsureds,
			List<Questionnaires> questionnaires, String fna) {
		super(prospect, insured, additionalInsureds, questionnaires);
		this.fna = fna;
	}

	public String getFna() {
		return fna;
	}

	@Override
	public String toString() {
		return "LifeQuote [fna=" + fna + ", toString()=" + super.toString() + "]";
	}

}
