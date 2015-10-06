package com.evanzeimet.queryinfo.jpa.field;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.evanzeimet.queryinfo.QueryInfoException;

public class EntityAnnotationsResolverTest {

	private EntityAnnotationsResolver<TestEntity> resolver;

	@Before
	public void setUp() {
		resolver = new EntityAnnotationsResolver<TestEntity>(TestEntity.class);
	}

	@Test
	public void createFieldInfo() throws NoSuchMethodException,
			QueryInfoException {
		Method annotatedMethod = getStuffAndThingsGetter();

		QueryInfoFieldInfo actualFieldInfo = resolver.createFieldInfo(annotatedMethod);

		assertNotNull(actualFieldInfo);

		String actualEntityAttributeNameName = actualFieldInfo.getEntityAttributeName();
		String expectedEntityAttributeName = "stuffAndThings";

		assertEquals(expectedEntityAttributeName, actualEntityAttributeNameName);

		String actualFieldName = actualFieldInfo.getFieldName();
		String expectedFieldName = "stuffAndThings";

		assertEquals(expectedFieldName, actualFieldName);

		Boolean actualIsQueryable = actualFieldInfo.getIsQueryable();
		assertTrue(actualIsQueryable);

		Boolean actualIsResult = actualFieldInfo.getIsResult();
		assertFalse(actualIsResult);
	}

	@Test
	public void createFieldName_getter() throws NoSuchMethodException,
			SecurityException,
			QueryInfoException {
		Method annotatedMethod = getStuffAndThingsGetter();

		String actual = resolver.createFieldName(annotatedMethod);

		String expected = "stuffAndThings";

		assertEquals(expected, actual);
	}

	@Test
	public void createFieldName_nonAccessor() throws NoSuchMethodException,
			SecurityException,
			QueryInfoException {
		Method annotatedMethod = getIsSomethingElseMethod();

		String actual = resolver.createFieldName(annotatedMethod);

		String expected = "isSomethingElse";

		assertEquals(expected, actual);
	}

	@Test
	public void createFieldName_override() throws NoSuchMethodException,
			SecurityException,
			QueryInfoException {
		Method annotatedMethod = getImOverriddenMethod();

		String actual = resolver.createFieldName(annotatedMethod);

		String expected = "iLikeToOverrideThings";

		assertEquals(expected, actual);
	}

	@Test
	public void createFieldName_setter() throws NoSuchMethodException,
			SecurityException,
			QueryInfoException {
		Method annotatedMethod = getThingAndStuffSetter();

		String actual = resolver.createFieldName(annotatedMethod);

		String expected = "thingsAndStuff";

		assertEquals(expected, actual);
	}

	@Test
	public void validateFieldNameUniqueness_notUnique() throws QueryInfoException {
		List<QueryInfoFieldInfo> fieldInfos = new ArrayList<>();

		QueryInfoFieldInfo fieldInfo;

		{
			fieldInfo = new DefaultQueryInfoFieldInfo();

			fieldInfo.setFieldName("field1");
			fieldInfo.setEntityAttributeName("field1");

			fieldInfos.add(fieldInfo);
		}

		{
			fieldInfo = new DefaultQueryInfoFieldInfo();

			fieldInfo.setFieldName("field1");
			fieldInfo.setEntityAttributeName("fieldOne");

			fieldInfos.add(fieldInfo);
		}

		{
			fieldInfo = new DefaultQueryInfoFieldInfo();

			fieldInfo.setFieldName("field2");
			fieldInfo.setEntityAttributeName("field2");

			fieldInfos.add(fieldInfo);
		}

		{
			fieldInfo = new DefaultQueryInfoFieldInfo();

			fieldInfo.setFieldName("field2");
			fieldInfo.setEntityAttributeName("fieldTwo");

			fieldInfos.add(fieldInfo);
		}

		QueryInfoException actualException = null;

		try {
			resolver.validateFieldNameUniqueness(fieldInfos);
		} catch (QueryInfoException e) {
			actualException = e;
		}

		assertNotNull(actualException);

		String actualExceptionMessage = actualException.getMessage();
		String expectedExceptionMessage = String.format("Found [2] non-unique field names for entity [com.evanzeimet.queryinfo.jpa.field.EntityAnnotationsResolverTest.TestEntity]:%n"
				+ "Found [2] field infos for name [field1]: field1, fieldOne.%n"
				+ "Found [2] field infos for name [field2]: field2, fieldTwo.");

		assertEquals(expectedExceptionMessage, actualExceptionMessage);
	}

	@Test
	public void validateFieldNameUniqueness_unique() {
		List<QueryInfoFieldInfo> fieldInfos = new ArrayList<>();

		QueryInfoFieldInfo fieldInfo;

		{
			fieldInfo = new DefaultQueryInfoFieldInfo();

			fieldInfo.setFieldName("field1");
			fieldInfo.setEntityAttributeName("fieldTwo");

			fieldInfos.add(fieldInfo);
		}

		{
			fieldInfo = new DefaultQueryInfoFieldInfo();

			fieldInfo.setFieldName("field2");
			fieldInfo.setEntityAttributeName("fieldTwo");

			fieldInfos.add(fieldInfo);
		}

		QueryInfoException actualException = null;

		try {
			resolver.validateFieldNameUniqueness(fieldInfos);
		} catch (QueryInfoException e) {
			actualException = e;
		}

		assertNull(actualException);
	}

	private Method getImOverriddenMethod() throws NoSuchMethodException {
		return TestEntity.class.getMethod("imOverridden");
	}

	private Method getIsSomethingElseMethod() throws NoSuchMethodException {
		return TestEntity.class.getMethod("isSomethingElse");
	}

	private Method getStuffAndThingsGetter() throws NoSuchMethodException {
		return TestEntity.class.getMethod("getStuffAndThings");
	}

	private Method getThingAndStuffSetter() throws NoSuchMethodException {
		return TestEntity.class.getMethod("setThingsAndStuff");
	}

	private static class TestEntity {

		private String stuffAndThings;

		@QueryInfoField(isQueryable = true,
				isResult = false)
		public String getStuffAndThings() {
			return stuffAndThings;
		}

		@QueryInfoField
		public void setThingsAndStuff() {

		}

		@QueryInfoField
		public boolean isSomethingElse() {
			return true;
		}

		@QueryInfoField(fieldName = "iLikeToOverrideThings")
		public boolean imOverridden() {
			return true;
		}
	}

}
