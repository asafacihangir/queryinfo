package com.evanzeimet.queryinfo.jpa.condition;

/*
 * #%L
 * queryinfo-jpa
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2015 - 2016 Evan Zeimet
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import javax.persistence.metamodel.SingularAttribute;

import com.evanzeimet.queryinfo.condition.Condition;
import com.evanzeimet.queryinfo.condition.ConditionOperator;
import com.fasterxml.jackson.databind.JsonNode;

public class ConditionBuilder extends com.evanzeimet.queryinfo.condition.ConditionBuilder {

	public ConditionBuilder() {

	}

	@Override
	public ConditionBuilder builderReferenceInstance(Condition builderReferenceInstance) {
		super.builderReferenceInstance(builderReferenceInstance);
		return this;
	}

	public static ConditionBuilder create() {
		return new ConditionBuilder();
	}

	public ConditionBuilder leftHandSide(SingularAttribute<?, ?> leftHandSide) {
		String name = leftHandSide.getName();
		return leftHandSide(name);
	}

	@Override
	public ConditionBuilder leftHandSide(String leftHandSide) {
		super.leftHandSide(leftHandSide);
		return this;
	}

	@Override
	public ConditionBuilder operator(ConditionOperator operator) {
		super.operator(operator);
		return this;
	}

	@Override
	public ConditionBuilder operator(String operator) {
		super.operator(operator);
		return this;
	}

	@Override
	public ConditionBuilder rightHandSide(Object rightHandSide) {
		super.rightHandSide(rightHandSide);
		return this;
	}

	@Override
	public ConditionBuilder rightHandSide(JsonNode rightHandSide) {
		super.rightHandSide(rightHandSide);
		return this;
	}
}