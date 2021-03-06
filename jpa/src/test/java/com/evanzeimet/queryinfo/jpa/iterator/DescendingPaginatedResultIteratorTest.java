package com.evanzeimet.queryinfo.jpa.iterator;

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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import com.evanzeimet.queryinfo.DefaultQueryInfo;
import com.evanzeimet.queryinfo.QueryInfo;
import com.evanzeimet.queryinfo.QueryInfoException;
import com.evanzeimet.queryinfo.jpa.bean.AbstractQueryInfoBean;
import com.evanzeimet.queryinfo.jpa.bean.QueryInfoBean;
import com.evanzeimet.queryinfo.pagination.DefaultPaginationInfo;
import com.evanzeimet.queryinfo.pagination.PaginationInfo;

public class DescendingPaginatedResultIteratorTest {

	private PaginationInfo givenPaginationInfo;
	private DefaultQueryInfo givenQueryInfo;
	private QueryInfoBean<?, ?, Result> givenQueryInfoBean;
	private DescendingPaginatedResultIterator<Result> iterator;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		givenQueryInfoBean = mock(QueryInfoBean.class);

		givenPaginationInfo = new DefaultPaginationInfo();

		givenQueryInfo = new DefaultQueryInfo();
		givenQueryInfo.setPaginationInfo(givenPaginationInfo);

		iterator = new DescendingPaginatedResultIterator<Result>(givenQueryInfoBean, givenQueryInfo);
		iterator = spy(iterator);
	}

	@Test
	public void getStartPageIndex_hasResults_noRemainder_nonZeroPaginationInfoPageIndex() {
		Integer givenPageIndex = 2;
		Integer givenPageSize = 20;
		Long givenTotalCount = 100L;

		iterator.totalCount = givenTotalCount;

		givenPaginationInfo.setPageIndex(givenPageIndex);
		givenPaginationInfo.setPageSize(givenPageSize);

		Integer actual = iterator.getStartPageIndex();
		Integer expected = 2;

		assertEquals(expected, actual);
	}

	@Test
	public void getStartPageIndex_hasResults_noRemainder_zeroPaginationInfoPageIndex() {
		Integer givenPageIndex = 0;
		Integer givenPageSize = 20;
		Long givenTotalCount = 100L;

		iterator.totalCount = givenTotalCount;

		givenPaginationInfo.setPageIndex(givenPageIndex);
		givenPaginationInfo.setPageSize(givenPageSize);

		Integer actual = iterator.getStartPageIndex();
		Integer expected = 4;

		assertEquals(expected, actual);
	}

	@Test
	public void getStartPageIndex_hasResults_remainder_nonZeroPaginationInfoPageIndex() {
		Integer givenPageIndex = 2;
		Integer givenPageSize = 20;
		Long givenTotalCount = 102L;

		iterator.totalCount = givenTotalCount;

		givenPaginationInfo.setPageIndex(givenPageIndex);
		givenPaginationInfo.setPageSize(givenPageSize);

		Integer actual = iterator.getStartPageIndex();
		Integer expected = 3;

		assertEquals(expected, actual);
	}

	@Test
	public void getStartPageIndex_hasResults_remainder_zeroPaginationInfoPageIndex() {
		Integer givenPageIndex = 0;
		Integer givenPageSize = 20;
		Long givenTotalCount = 102L;

		iterator.totalCount = givenTotalCount;

		givenPaginationInfo.setPageIndex(givenPageIndex);
		givenPaginationInfo.setPageSize(givenPageSize);

		Integer actual = iterator.getStartPageIndex();
		Integer expected = 5;

		assertEquals(expected, actual);
	}

	@Test
	public void getStartPageIndex_noResults() {
		Long givenTotalCount = 0L;

		iterator.totalCount = givenTotalCount;

		Integer actual = iterator.getStartPageIndex();
		Integer expected = -1;

		assertEquals(expected, actual);
	}

	@Test
	public void hasNext_false_noMorePages() {
		Integer givenPageIndex = -1;
		Integer givenPageSize = 1;
		Long givenTotalCount = 1L;

		iterator.nextPageIndex = givenPageIndex;
		iterator.totalCount = givenTotalCount;

		givenPaginationInfo.setPageIndex(givenPageIndex);
		givenPaginationInfo.setPageSize(givenPageSize);

		boolean actual = iterator.hasNext();

		assertFalse(actual);
	}

	@Test
	public void hasNext_false_noTotalCount_noResults() throws QueryInfoException {
		Integer givenPageIndex = null;
		Integer givenPageSize = 1;
		Long givenTotalCount = null;

		iterator.nextPageIndex = givenPageIndex;
		iterator.totalCount = givenTotalCount;

		givenPaginationInfo.setPageIndex(givenPageIndex);
		givenPaginationInfo.setPageSize(givenPageSize);

		doReturn(0L).when(givenQueryInfoBean)
				.count(givenQueryInfo);

		boolean actual = iterator.hasNext();

		assertFalse(actual);
	}

	@Test
	public void hasNext_true_noTotalCount() throws QueryInfoException {
		Integer givenPageIndex = 0;
		Integer givenPageSize = 1;
		Long givenTotalCount = null;

		iterator.nextPageIndex = givenPageIndex;
		iterator.totalCount = givenTotalCount;

		givenPaginationInfo.setPageIndex(givenPageIndex);
		givenPaginationInfo.setPageSize(givenPageSize);

		doReturn(100L).when(givenQueryInfoBean)
				.count(givenQueryInfo);

		boolean actual = iterator.hasNext();

		assertTrue(actual);
	}

	@Test
	public void hasNext_true_hasTotalCount() throws QueryInfoException {
		Integer givenPageIndex = 20;
		Integer givenPageSize = 1;
		Long givenTotalCount = 100L;

		iterator.nextPageIndex = givenPageIndex;
		iterator.totalCount = givenTotalCount;

		givenPaginationInfo.setPageIndex(givenPageIndex);
		givenPaginationInfo.setPageSize(givenPageSize);

		boolean actual = iterator.hasNext();

		assertTrue(actual);

		verify(givenQueryInfoBean, never()).count(any(QueryInfo.class));
	}

	@Test
	public void hasNext_true_hasTotalCount_pageSizeOverTotalCount() {
		Integer givenPageIndex = 0;
		Integer givenPageSize = 100;
		Long givenTotalCount = 20L;

		iterator.nextPageIndex = givenPageIndex;
		iterator.totalCount = givenTotalCount;

		givenPaginationInfo.setPageIndex(givenPageIndex);
		givenPaginationInfo.setPageSize(givenPageSize);

		boolean actual = iterator.hasNext();

		assertTrue(actual);
	}

	@Test
	public void hasNext_true_noTotalCount_pageSizeOverTotalCount() throws QueryInfoException {
		Integer givenPageIndex = 0;
		Integer givenPageSize = 100;
		Long givenTotalCount = null;

		iterator.nextPageIndex = givenPageIndex;
		iterator.totalCount = givenTotalCount;

		givenPaginationInfo.setPageIndex(givenPageIndex);
		givenPaginationInfo.setPageSize(givenPageSize);

		doReturn(20L).when(givenQueryInfoBean)
				.count(givenQueryInfo);

		boolean actual = iterator.hasNext();

		assertTrue(actual);
	}

	@Test
	public void next_setsNextPageIndex() {
		iterator.nextPageIndex = 1;

		doReturn(true).when(iterator)
				.hasNext();

		iterator.next();

		Integer actual = iterator.nextPageIndex;
		Integer expected = 0;

		assertEquals(expected,
				actual);
	}

	@Test
	public void queryForNextPage_hasTotalCount_dontCount() throws QueryInfoException {
		Integer givenPageIndex = 0;
		Integer givenPageSize = 100;
		Long givenTotalCount = 42L;

		givenPaginationInfo.setPageIndex(givenPageIndex);
		givenPaginationInfo.setPageSize(givenPageSize);

		givenQueryInfoBean = new TestQueryInfoBean();
		givenQueryInfoBean = spy(givenQueryInfoBean);

		doReturn(42L).when(givenQueryInfoBean).count(any(QueryInfo.class));
		doReturn(Collections.emptyList()).when(givenQueryInfoBean).query(any(QueryInfo.class));

		iterator = new DescendingPaginatedResultIterator<Result>(givenQueryInfoBean, givenQueryInfo);
		iterator.totalCount = givenTotalCount;

		iterator.queryForNextPage();

		verify(givenQueryInfoBean, never()).count(any(QueryInfo.class));
	}

	private static class Result {

	}

	private static class TestQueryInfoBean extends AbstractQueryInfoBean<Result, Result, Result> {

	}
}
