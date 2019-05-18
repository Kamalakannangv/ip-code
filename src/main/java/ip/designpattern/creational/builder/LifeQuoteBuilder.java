package ip.designpattern.creational.builder;

import java.util.ArrayList;
import java.util.List;

public class LifeQuoteBuilder implements QuoteBuilder {
	
	private Prospect prospect;
	private InsuredPerson insuredPerson;
	private List<Insured> additionalInsureds = new ArrayList<>();
	private Questionnaires questionnaires; 
	private String fna;

	public LifeQuoteBuilder(Prospect prospect){
		this.prospect = prospect;
		
		// setting default values
		InsuredPerson defaultInsured = new InsuredPerson(prospect);
		defaultInsured.setSumInsured(100000);
		defaultInsured.setTerm(10);
		this.insuredPerson = defaultInsured;
		
		InsuredPersonQuestionnaires lifeQuestionnaires = new InsuredPersonQuestionnaires();
		lifeQuestionnaires.isInsuranceRejected(true);
		lifeQuestionnaires.setNoOfClaimsInLastThreeYear(5);
		lifeQuestionnaires.setInsuredHeight(10);
		lifeQuestionnaires.setInsuredWeight(200);
		this.questionnaires = lifeQuestionnaires;
	}

	@Override
	public LifeQuoteBuilder addInsured(Insured insured) {
		if(insured instanceof InsuredPerson){
			insuredPerson = (InsuredPerson)insured;
		}
		return this;
	}

	@Override
	public LifeQuoteBuilder setQuestionnaire(Questionnaires questionnaires) {
		if(questionnaires instanceof InsuredPersonQuestionnaires){
			this.questionnaires = ((InsuredPersonQuestionnaires)questionnaires);
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
