package ch.ess.cal.web;

import org.apache.struts.action.ActionForm;

/**
 * Empty (or "null") form for use with "formless" forms.
 * The Struts JSP tags require a form bean to create elements like
 * buttons, even if one is not actually needed.
 * The NullForm placates the tag by providing an empty form.^
 * 
 * @struts.form name="nullForm" 
 */
public final class NullForm extends ActionForm {

  // cal form for use with command buttons

}