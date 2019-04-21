package ip.designpattern.creational.builder;

import java.util.List;

public class Quote {
	
	private Prospect prospect;
	private Insured insured;
	private List<Insured> additionalInsureds;
	private List<Questionnaires> questionnaires;
	
	public Quote(Prospect prospect, Insured insured, List<Insured> additionalInsureds,
			List<Questionnaires> questionnaires) {
		super();
		this.prospect = prospect;
		this.insured = insured;
		this.additionalInsureds = additionalInsureds;
		this.questionnaires = questionnaires;
	}

	public Prospect getProspect() {
		return prospect;
	}

	public Insured getInsured() {
		return insured;
	}

	public List<Insured> getAdditionalInsureds() {
		return additionalInsureds;
	}

	public List<Questionnaires> getQuestionnaires() {
		return questionnaires;
	}

	@Override
	public String toString() {
		return "Quote [prospect=" + prospect + ", insured=" + insured + ", additionalInsureds=" + additionalInsureds
				+ ", questionnaires=" + questionnaires + "]";
	}

}

