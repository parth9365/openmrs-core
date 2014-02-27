/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.validator;

import org.junit.Assert;
import org.junit.Test;
import org.openmrs.Concept;
import org.openmrs.OrderFrequency;
import org.openmrs.api.context.Context;
import org.openmrs.test.BaseContextSensitiveTest;
import org.openmrs.test.Verifies;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;

/**
 * Tests methods on the {@link OrderFrequencyValidator} class.
 */
public class OrderFrequencyValidatorTest extends BaseContextSensitiveTest {
	
	/**
	 * @see {@link OrderFrequencyValidator#validate(Object,Errors)}
	 */
	@Test
	@Verifies(value = "should fail if orderFrequency is null", method = "validate(Object,Errors)")
	public void validate_shouldFailIfOrderFrequencyIsNull() throws Exception {
		Errors errors = new BindException(new OrderFrequency(), "orderFrequency");
		new OrderFrequencyValidator().validate(null, errors);
		
		Assert.assertTrue(errors.hasErrors());
	}
	
	/**
	 * @see {@link OrderFrequencyValidator#validate(Object,Errors)}
	 */
	@Test
	@Verifies(value = "should fail if concept is null", method = "validate(Object,Errors)")
	public void validate_shouldFailIfConceptIsNull() throws Exception {
		OrderFrequency orderFrequency = new OrderFrequency();
		
		Errors errors = new BindException(orderFrequency, "orderFrequency");
		new OrderFrequencyValidator().validate(orderFrequency, errors);
		
		Assert.assertTrue(errors.hasFieldErrors("concept"));
	}
	
	/**
	 * @see {@link OrderFrequencyValidator#validate(Object,Errors)}
	 */
	@Test
	@Verifies(value = "should fail if the concept is not of class frequency", method = "validate(Object,Errors)")
	public void validate_shouldFailIfConceptIsNotOfClassFrequency() throws Exception {
		OrderFrequency orderFrequency = new OrderFrequency();
		orderFrequency.setConcept(Context.getConceptService().getConcept(88));
		Errors errors = new BindException(orderFrequency, "orderFrequency");
		new OrderFrequencyValidator().validate(orderFrequency, errors);
		
		Assert.assertTrue(errors.hasFieldErrors("concept"));
	}
	
	/**
	 * @see {@link OrderFrequencyValidator#validate(Object,Errors)}
	 */
	@Test
	@Verifies(value = "should fail if concept is used by another frequency", method = "validate(Object,Errors)")
	public void validate_shouldFailIfConceptIsUsedByAnotherFrequency() throws Exception {
		OrderFrequency orderFrequency = new OrderFrequency();
		orderFrequency.setConcept(Context.getConceptService().getConcept(113));
		Errors errors = new BindException(orderFrequency, "orderFrequency");
		new OrderFrequencyValidator().validate(orderFrequency, errors);
		
		Assert.assertTrue(errors.hasFieldErrors("concept"));
	}
	
	/**
	 * @see {@link OrderFrequencyValidator#validate(Object,Errors)}
	 */
	@Test
	@Verifies(value = "should pass if all fields are correct", method = "validate(Object,Errors)")
	public void validate_shouldPassIfAllFieldsAreCorrect() throws Exception {
		Concept concept = new Concept(1);
		concept.setConceptClass(Context.getConceptService().getConceptClass(19));
		
		OrderFrequency orderFrequency = new OrderFrequency();
		orderFrequency.setConcept(concept);
		Errors errors = new BindException(orderFrequency, "orderFrequency");
		new OrderFrequencyValidator().validate(orderFrequency, errors);
		
		Assert.assertFalse(errors.hasErrors());
	}
}
