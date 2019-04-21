package ip.designpattern.creational.builder;

import java.util.ArrayList;
import java.util.List;

public class LifeQuoteBuilder implements QuoteBuilder {
	
	private Prospect prospect;
	private InsuredPerson insuredPerson;
	private List<Insured> additionalInsureds = new ArrayList<>();
	private List<Questionnaires> questionnaires = new ArrayList<>();
	private String fna;

	public LifeQuoteBuilder(Prospect prospect){
		this.prospect = prospect;
		InsuredPerson defaultInsured = new InsuredPerson(prospect);
		defaultInsured.setSumInsured(100000);
		defaultInsured.setTerm(10);
		insuredPerson = defaultInsured;
	}

	@Override
	public LifeQuoteBuilder addInsured(Insured insured) {
		if(insured instanceof InsuredPerson){
			insuredPerson = (InsuredPerson)insured;
		}
		return this;
	}

	@Override
	public LifeQuoteBuilder addQuestionnaire(Questionnaires questionnaires) {
		if(questionnaires instanceof InsuredPersonQuestionnaires){
			this.questionnaires.add((InsuredPersonQuestionnaires)questionnaires);
		}
		return this;
	}

	@Override
	public LifeQuoteBuilder addAdditionalInsured(Insured insured) {
		if(insured instanceof InsuredPerson){
			additionalInsureds.add((InsuredPerson)insured);
		}
		return this;
	}
	
	public LifeQuoteBuilder addFna(String fna) {
		this.fna = fna;
		return this;
	} 
	
	
	@Override
	public Quote getQuote() {
		return new LifeQuote(prospect, insuredPerson, additionalInsureds, questionnaires, fna);
	}

}
