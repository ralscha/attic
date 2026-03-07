package ch.rasc.rulebook;

import com.deliveredtechnologies.rulebook.FactMap;
import com.deliveredtechnologies.rulebook.NameValueReferableMap;
import com.deliveredtechnologies.rulebook.lang.RuleBookBuilder;
import com.deliveredtechnologies.rulebook.model.RuleBook;

public class Main2 {

	public static void main(String[] args) {
		RuleBook<Double> loanRateRuleBook = RuleBookBuilder.create()
				.withResultType(Double.class).withDefaultResult(4.5)
				.addRule(rule -> rule.withFactType(ApplicantBean.class)
						.when(facts -> facts.getOne().getCreditScore() < 600)
						.then((facts, result) -> result.setValue(result.getValue() * 4))
						.stop())
				.addRule(rule -> rule.withFactType(ApplicantBean.class)
						.when(facts -> facts.getOne().getCreditScore() < 700)
						.then((facts, result) -> result.setValue(result.getValue() + 1)))
				.addRule(rule -> rule.withFactType(ApplicantBean.class)
						.when(facts -> facts.getOne().getCreditScore() >= 700
								&& facts.getOne().getCashOnHand() >= 25000)
						.then((facts, result) -> result
								.setValue(result.getValue() - 0.25)))
				.addRule(rule -> rule.withFactType(ApplicantBean.class)
						.when(facts -> facts.getOne().isFirstTimeHomeBuyer()).then((facts,
								result) -> result.setValue(result.getValue() * 0.80)))
				.build();

		 NameValueReferableMap facts = new FactMap();
		    facts.setValue("applicant", new ApplicantBean(650, 20000.0, true));
		    loanRateRuleBook.run(facts);

		    loanRateRuleBook.getResult().ifPresent(result -> System.out.println("Applicant qualified for the following rate: " + result));
		 
	}

}
