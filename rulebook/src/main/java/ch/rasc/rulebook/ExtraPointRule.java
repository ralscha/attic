package ch.rasc.rulebook;

import java.util.List;

import com.deliveredtechnologies.rulebook.annotation.Given;
import com.deliveredtechnologies.rulebook.annotation.Result;
import com.deliveredtechnologies.rulebook.annotation.Rule;
import com.deliveredtechnologies.rulebook.annotation.Then;
import com.deliveredtechnologies.rulebook.annotation.When;

@Rule(order = 3)
public class ExtraPointRule {
  @Given
  List<ApplicantBean> applicants;

  @Result
  private double rate;

  @When
  public boolean when() {
    return
      this.applicants.stream().anyMatch(applicant -> applicant.getCreditScore() < 700 && applicant.getCreditScore() >= 600);
  }

  @Then
  public void then() {
    this.rate += 1;
  }
}