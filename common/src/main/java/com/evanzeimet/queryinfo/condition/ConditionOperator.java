package com.evanzeimet.queryinfo.condition;

/*
 * #%L
 * queryinfo-common
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2015 Evan Zeimet
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

public enum ConditionOperator {

	EQUAL_TO("=",
			"equals"),
	NOT_EQUAL_TO("<>",
			"not equals"),
	GREATER_THAN(">",
			"is greater than"),
	GREATER_THAN_OR_EQUAL_TO(">=",
			"is greater than or equal to"),
	LESS_THAN("<",
			"is less than"),
	LESS_THAN_OR_EQUAL_TO("<=",
			"is less than or equal to"),
	NULL("is null",
			"is null"),
	NOT_NULL("is not null",
			"is not null"),
	LIKE("like",
			"like"),
	NOT_LIKE("not like",
			"not like"),
	IN("is in",
			"is in"),
	NOT_IN("is not in",
			"is not in");

	private final String description;
	private final String text;

	ConditionOperator(String text, String description) {
		this.text = text;
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public String getText() {
		return text;
	}

	public static ConditionOperator fromText(String text) {
		ConditionOperator result = null;

		for (ConditionOperator conditionOperator : ConditionOperator.values()) {
			if (conditionOperator.getText().equals(text)) {
				result = conditionOperator;
				break;
			}
		}

		return result;
	}
}