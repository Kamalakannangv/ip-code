package ip.designpattern.creational.builder;

import java.util.ArrayList;
import java.util.List;

public class PolisyQuoteBuilder implements QuoteBuilder {
	
	private Prospect prospect;
	private InsuredObject insuredObject;
	private List<Insured> additionalInsureds = new ArrayList<>();
	private Questionnaires questionnaires;
	private long maintenanceCharges;

	public PolisyQuoteBuilder(Prospect prospect, InsuredObject insuredObject){
		this.prospect = prospect;
		this.insuredObject = insuredObject;
		
		// Setting default values
		InsuredObjectQuestionnaire pncQuestionnaires = new InsuredObjectQuestionnaire();
		pncQuestionnaires.isInsuranceRejected(true);
		pncQuestionnaires.setNoOfClaimsInLastThreeYear(5);
		pncQuestionnaires.setKiloMeters(100000);
		this.questionnaires = pncQuestionnaires;
		this.maintenanceCharges = 10000;
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
	public PolisyQuoteBuilder setQuestionnaire(Questionnaires questionnaires) {
		if(questionnaires instanceof InsuredObjectQuestionnaire){
			this.questionnaires = ((InsuredObjectQuestionnaire)questionnaires);
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
