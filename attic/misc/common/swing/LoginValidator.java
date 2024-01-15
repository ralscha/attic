
package common.swing;

public interface LoginValidator {
	public boolean validate(String name, String password);
	public void validationCancelled();
}