package ch.rasc.rulebook;

import java.util.List;

import com.deliveredtechnologies.rulebook.RuleState;
import com.deliveredtechnologies.rulebook.annotation.Given;
import com.deliveredtechnologies.rulebook.annotation.Result;
import com.deliveredtechnologies.rulebook.annotation.Rule;
import com.deliveredtechnologies.rulebook.annotation.Then;
import com.deliveredtechnologies.rulebook.annotation.When;

@Rule(order = 2)
public class LowCreditScoreRule {
  @Given
  private List<ApplicantBean> applicants;

  @Result
  private double rate;

  @When
  public boolean when() {
    return this.applicants.stream()
      .allMatch(applicant -> applicant.getCreditScore() < 600);
  }

  @Then
  public RuleState then() {
    this.rate *= 4;
    return RuleState.BREAK;
  }
}