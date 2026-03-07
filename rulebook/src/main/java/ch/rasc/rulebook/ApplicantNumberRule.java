package ch.rasc.rulebook;

import java.util.List;

import com.deliveredtechnologies.rulebook.RuleState;
import com.deliveredtechnologies.rulebook.annotation.Given;
import com.deliveredtechnologies.rulebook.annotation.Rule;
import com.deliveredtechnologies.rulebook.annotation.Then;
import com.deliveredtechnologies.rulebook.annotation.When;

@Rule(order = 1) //order specifies the order the rule should execute in; if not specified, any order may be used
public class ApplicantNumberRule {
  @Given
  private List<ApplicantBean> applicants; //Annotated Lists get injected with all Facts of the declared generic type

  @When
  public boolean when() {
    return this.applicants.size() > 3;
  }

  @Then
  public RuleState then() {
    return RuleState.BREAK;
  }
}