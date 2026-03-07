package ch.rasc.rulebook;

import java.util.List;

import com.deliveredtechnologies.rulebook.annotation.Given;
import com.deliveredtechnologies.rulebook.annotation.Result;
import com.deliveredtechnologies.rulebook.annotation.Rule;
import com.deliveredtechnologies.rulebook.annotation.Then;
import com.deliveredtechnologies.rulebook.annotation.When;

@Rule(order = 4)
public class FirstTimeHomeBuyerRule {
  @Given
  List<ApplicantBean> applicants;

  @Result
  private double rate;

  @When
  public boolean when() {
    return
      this.applicants.stream().anyMatch(applicant -> applicant.isFirstTimeHomeBuyer());
  }

  @Then
  public void then() {
    this.rate = this.rate - (this.rate * 0.20);
  }
}