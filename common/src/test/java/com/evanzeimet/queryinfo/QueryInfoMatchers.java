package com.evanzeimet.queryinfo;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import com.evanzeimet.queryinfo.condition.Condition;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectWriter;

public class QueryInfoMatchers {

	public static TypeSafeMatcher<Condition> equalsCondition(final Condition expected) {
		return new TypeSafeMatcher<Condition>() {

			private boolean leftHandSideMatches;
			private boolean operatorMatches;
			private boolean rightHandSideMatches;

			@Override
			protected boolean matchesSafely(Condition actual) {
				String expectedLhs = expected.getLeftHandSide();
				String actualLhs = actual.getLeftHandSide();

				leftHandSideMatches = matchString(expectedLhs, actualLhs);

				String expectedOperator = expected.getOperator();
				String actualOperator = actual.getOperator();
				operatorMatches = matchString(expectedOperator, actualOperator);


				String expectedRhs = expected.getRightHandSide();
				String actualRhs = actual.getRightHandSide();
				rightHandSideMatches = matchString(expectedRhs, actualRhs);

				return (leftHandSideMatches && operatorMatches && rightHandSideMatches);
			}

			@Override
			protected void describeMismatchSafely(Condition actual, Description description) {
				boolean hasMismatch = false;

				if (!leftHandSideMatches) {
					description.appendText("left hand side was ")
						.appendValue(actual.getLeftHandSide());
					hasMismatch = true;
				}

				if (!operatorMatches) {
					if (hasMismatch) {
						description.appendText(", ");
					}

					description.appendText("operator was ")
						.appendValue(actual.getOperator());

					hasMismatch = true;
				}

				if (!rightHandSideMatches) {
					if (hasMismatch) {
						description.appendText(", ");
					}

					description.appendText("right hand side was ")
						.appendValue(actual.getRightHandSide());

					hasMismatch = true;
				}
			}

			@Override
			public void describeTo(Description description) {
				boolean hasMismatch = false;

				if (!leftHandSideMatches) {
					description.appendText("left hand side to equal ")
						.appendValue(expected.getLeftHandSide());
					hasMismatch = true;
				}

				if (!operatorMatches) {
					if (hasMismatch) {
						description.appendText(", ");
					}

					description.appendText("operator to equal ")
						.appendValue(expected.getOperator());

					hasMismatch = true;
				}

				if (!rightHandSideMatches) {
					if (hasMismatch) {
						description.appendText(", ");
					}

					description.appendText("right hand side to equal ")
						.appendValue(expected.getRightHandSide());
					hasMismatch = true;
				}
			}
		};
	}

	public static TypeSafeMatcher<Object> equalsJson(final Object expected) {
		return new TypeSafeMatcher<Object>() {

			private String actualJson;
			private String expectedJson;

			@Override
			protected void describeMismatchSafely(Object item, Description description) {
				description.appendText("was ").appendValue(actualJson);
			}

			@Override
			public void describeTo(Description description) {
				description.appendValue(expectedJson);
			}

			@Override
			protected boolean matchesSafely(Object actual) {
				boolean result = false;
				ObjectWriter objectWriter = QueryInfoTestUtils.createObjectMapperWriter();

				try {
					actualJson = objectWriter.writeValueAsString(actual);
					expectedJson = objectWriter.writeValueAsString(expected);

					result = expectedJson.equals(actualJson);
				} catch (JsonProcessingException e) {
					e.printStackTrace();
					result = false;
				}

				return result;
			}
		};
	}

	protected static boolean matchString(String expected, String actual) {
		boolean result;

		if (expected == null) {
			result = (actual == null);
		} else {
			result = expected.equals(actual);
		}

		return result;
	}
}
