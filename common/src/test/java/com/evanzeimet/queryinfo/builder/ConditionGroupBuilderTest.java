package com.evanzeimet.queryinfo.builder;

import static com.evanzeimet.queryinfo.QueryInfoMatchers.equalsCondition;
import static com.evanzeimet.queryinfo.QueryInfoMatchers.equalsJson;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.evanzeimet.queryinfo.condition.Condition;
import com.evanzeimet.queryinfo.condition.ConditionBuilder;
import com.evanzeimet.queryinfo.condition.ConditionGroup;
import com.evanzeimet.queryinfo.condition.ConditionGroupBuilder;
import com.evanzeimet.queryinfo.condition.ConditionGroupOperator;
import com.evanzeimet.queryinfo.condition.ConditionOperator;
import com.evanzeimet.queryinfo.condition.DefaultCondition;

public class ConditionGroupBuilderTest {

	private ConditionGroupBuilder builder;

	@Before
	public void setUp() {
		builder = new ConditionGroupBuilder();
	}

	@Test
	public void build() {
		List<ConditionGroup> givenConditionGroups = new ArrayList<ConditionGroup>();

		Condition givenCondition1 = ConditionBuilder.create()
				.leftHandSide("lhs1")
				.operator(ConditionOperator.EQUAL_TO)
				.rightHandSide("rhs1")
				.build();
		ConditionGroup givenConditionGroup = ConditionGroupBuilder.createForCondition(
				givenCondition1).build();

		givenConditionGroups.add(givenConditionGroup);

		List<Condition> givenConditions = new ArrayList<Condition>();

		Condition givenCondition2 = ConditionBuilder.create()
				.leftHandSide("lhs2")
				.operator(ConditionOperator.NOT_EQUAL_TO)
				.rightHandSide("rhs2")
				.build();
		givenConditions.add(givenCondition2);

		ConditionGroupOperator givenOperator = ConditionGroupOperator.AND;

		ConditionGroup actualConditionGroup = builder.conditionGroups(givenConditionGroups)
				.conditions(givenConditions)
				.operator(givenOperator)
				.build();

		List<ConditionGroup> actualConditionGroups = actualConditionGroup.getConditionGroups();
		assertThat(givenConditionGroups, equalsJson(actualConditionGroups));

		List<Condition> actualConditions = actualConditionGroup.getConditions();
		assertThat(givenConditions, equalsJson(actualConditions));

		String actualOperator = actualConditionGroup.getOperator();
		String expectedOperator = givenOperator.getText();
		assertEquals(expectedOperator, actualOperator);
	}

	@Test
	public void createForCondition() {
		Condition givenCondition = new DefaultCondition();

		givenCondition.setLeftHandSide("lhs1");
		givenCondition.setOperator("op1");
		givenCondition.setRightHandSide("rhs1");

		ConditionGroup actualConditionGroup = ConditionGroupBuilder.createForCondition(
				givenCondition)
				.build();

		List<ConditionGroup> actualConditionGroups = actualConditionGroup.getConditionGroups();
		assertThat(actualConditionGroups, empty());

		List<Condition> actualConditions = actualConditionGroup.getConditions();
		assertThat(actualConditions, hasSize(1));

		Condition actualCondition = actualConditions.get(0);
		assertThat(actualCondition, equalsCondition(givenCondition));

		String actualConditionGroupOperator = actualConditionGroup.getOperator();
		String expectedConditionGroupOperator = ConditionGroupOperator.AND.getText();
		assertEquals(expectedConditionGroupOperator, actualConditionGroupOperator);
	}

	@Test
	public void createForConditionGroup() {
		List<Condition> givenConditions = new ArrayList<Condition>();

		Condition givenCondition = new DefaultCondition();

		givenCondition.setLeftHandSide("lhs1");
		givenCondition.setOperator("op1");
		givenCondition.setRightHandSide("rhs1");

		givenConditions.add(givenCondition);

		ConditionGroup givenConditionGroup = ConditionGroupBuilder.create()
				.conditions(givenConditions)
				.build();

		ConditionGroup actualConditionGroup = ConditionGroupBuilder.createForConditionGroup(
				givenConditionGroup)
				.build();

		List<ConditionGroup> actualNestedConditionGroups = actualConditionGroup.getConditionGroups();
		assertThat(actualNestedConditionGroups, hasSize(1));

		ConditionGroup actualNestedConditionGroup = actualNestedConditionGroups.get(0);
		assertThat(actualNestedConditionGroup, equalsJson(givenConditionGroup));

		List<Condition> actualNestedConditions = actualConditionGroup.getConditions();
		assertThat(actualNestedConditions, empty());

		String actualConditionGroupOperator = actualConditionGroup.getOperator();
		String expectedConditionGroupOperator = ConditionGroupOperator.AND.getText();
		assertEquals(expectedConditionGroupOperator, actualConditionGroupOperator);
	}
}
