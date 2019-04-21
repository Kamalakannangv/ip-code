package ip.designpattern.creational.builder;

public interface QuoteBuilder {
	
	public QuoteBuilder addInsured(Insured insured);
	
	public QuoteBuilder addAdditionalInsured(Insured insured);
	
	public QuoteBuilder addQuestionnaire(Questionnaires questionnaires);
	
	public Quote getQuote();
	
}
