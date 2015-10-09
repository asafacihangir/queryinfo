package com.evanzeimet.queryinfo.jpa.bean;

/*
 * #%L
 * queryinfo-jpa
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


import com.evanzeimet.queryinfo.jpa.bean.context.QueryInfoBeanContext;
import com.evanzeimet.queryinfo.jpa.bean.context.QueryInfoBeanContextRegistry;
import com.evanzeimet.queryinfo.jpa.jpacontext.DefaultQueryInfoJPAContextFactory;
import com.evanzeimet.queryinfo.jpa.jpacontext.QueryInfoJPAContextFactory;
import com.evanzeimet.queryinfo.jpa.order.DefaultQueryInfoOrderFactory;
import com.evanzeimet.queryinfo.jpa.order.QueryInfoOrderFactory;
import com.evanzeimet.queryinfo.jpa.path.DefaultQueryInfoPathFactory;
import com.evanzeimet.queryinfo.jpa.path.QueryInfoPathFactory;
import com.evanzeimet.queryinfo.jpa.predicate.DefaultQueryInfoPredicateFactory;
import com.evanzeimet.queryinfo.jpa.predicate.QueryInfoPredicateFactory;


public abstract class AbstractQueryInfoBeanContext<RootEntity, CriteriaQueryResult, QueryInfoResult>
		implements QueryInfoBeanContext<RootEntity, CriteriaQueryResult, QueryInfoResult> {

	private QueryInfoBeanContextRegistry beanContextRegistry;
	private QueryInfoJPAContextFactory<RootEntity> jpaContextFactory;
	private QueryInfoOrderFactory<RootEntity> orderFactory;
	private QueryInfoPathFactory<RootEntity> pathFactory;
	private QueryInfoPredicateFactory<RootEntity> predicateFactory;

	public AbstractQueryInfoBeanContext(QueryInfoBeanContextRegistry beanContextRegistry) {
		this.beanContextRegistry = beanContextRegistry;
		this.jpaContextFactory = new DefaultQueryInfoJPAContextFactory<>();
		this.orderFactory = new DefaultQueryInfoOrderFactory<>(beanContextRegistry);
		this.predicateFactory = new DefaultQueryInfoPredicateFactory<>();
	}

	protected QueryInfoBeanContextRegistry getBeanContextRegistry() {
		return beanContextRegistry;
	}

	@Override
	public QueryInfoJPAContextFactory<RootEntity> getJpaContextFactory() {
		return jpaContextFactory;
	}

	@Override
	public QueryInfoOrderFactory<RootEntity> getOrderFactory() {
		return orderFactory;
	}

	@Override
	public Boolean getUseDistinctSelections() {
		return Boolean.FALSE;
	}

	@Override
	public QueryInfoPathFactory<RootEntity> getPathFactory() {
		if (pathFactory == null) {
			Class<RootEntity> rootEntityClass = getRootEntityClass();
			pathFactory = new DefaultQueryInfoPathFactory<>(beanContextRegistry, rootEntityClass);
		}

		return pathFactory;
	}

	@Override
	public QueryInfoPredicateFactory<RootEntity> getPredicateFactory() {
		return predicateFactory;
	}
}
