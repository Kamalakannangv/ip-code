package ip.designpattern.creational.builder;

import java.util.ArrayList;
import java.util.List;

public class PolisyQuoteBuilder implements QuoteBuilder {
	
	private Prospect prospect;
	private InsuredObject insuredObject;
	private List<Insured> additionalInsureds = new ArrayList<>();
	private List<Questionnaires> questionnaires = new ArrayList<>();
	private long maintenanceCharges;

	public PolisyQuoteBuilder(Prospect prospect){
		this.prospect = prospect;
	}
	
	@Override
	public PolisyQuoteBuilder addInsured(Insured insured) {
		if(insured instanceof InsuredObject){
			insuredObject = (InsuredObject)insured;
		}
		return this;
	}

	@Override
	public PolisyQuoteBuilder addAdditionalInsured(Insured insured) {
		if(insured instanceof InsuredObject){
			additionalInsureds.add((InsuredObject)insured);
		}
		return this;
	}

	@Override
	public PolisyQuoteBuilder addQuestionnaire(Questionnaires questionnaires) {
		if(questionnaires instanceof InsuredObjectQuestionnaire){
			this.questionnaires.add((InsuredObjectQuestionnaire)questionnaires);
		}
		return this;
	}
	
	public PolisyQuoteBuilder addMaintenanceCharges(long maintenanceCharges) {
		this.maintenanceCharges = maintenanceCharges;
		return this;
	}

	@Override
	public Quote getQuote() {
		return new PolisyQuote(prospect, insuredObject, additionalInsureds, questionnaires, maintenanceCharges);
	}

}
