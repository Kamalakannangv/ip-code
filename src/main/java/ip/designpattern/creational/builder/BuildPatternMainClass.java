package ip.designpattern.creational.builder;

public class BuildPatternMainClass {

	public static void main(String[] args) {
		
		Prospect prospect = new Prospect("Kamal", "11");
		
		QuoteBuilder lifeQuoteBuilder = new LifeQuoteBuilder(prospect);
		InsuredPersonQuestionnaires lifeQuestionnaires = new InsuredPersonQuestionnaires();
		lifeQuestionnaires.isInsuranceRejected(false);
		lifeQuestionnaires.setNoOfClaimsInLastThreeYear(1);
		lifeQuestionnaires.setInsuredHeight(177);
		lifeQuestionnaires.setInsuredWeight(74);
		lifeQuoteBuilder = lifeQuoteBuilder.setQuestionnaire(lifeQuestionnaires);
		lifeQuoteBuilder = ((LifeQuoteBuilder)lifeQuoteBuilder).addFna("FNA String");
		Quote lifeQuote = lifeQuoteBuilder.getQuote();
		System.out.println("Life Quote:\n" + lifeQuote.toString());
		
		
		Car car = new Car("Baleno", 2017, "ABCDE123", "addsr3qewre434");
		InsuredObject insuredObject = new InsuredObject();
		insuredObject.setSumInsured(1000000);
		insuredObject.setCar(car);
		QuoteBuilder pncQuoteBuilder = new PolisyQuoteBuilder(prospect, insuredObject);
		InsuredObjectQuestionnaire pncQuestionnaires = new InsuredObjectQuestionnaire();
		pncQuestionnaires.isInsuranceRejected(true);
		pncQuestionnaires.setNoOfClaimsInLastThreeYear(1);
		pncQuestionnaires.setKiloMeters(10000);
		pncQuoteBuilder = pncQuoteBuilder.setQuestionnaire(pncQuestionnaires);
		pncQuoteBuilder = pncQuoteBuilder.addInsured(insuredObject);
		pncQuoteBuilder = ((PolisyQuoteBuilder)pncQuoteBuilder).addMaintenanceCharges(1000);
		Quote pncQuote = pncQuoteBuilder.getQuote();
		System.out.println("\n\nPolisy Quote:\n" + pncQuote.toString());
	}
}
