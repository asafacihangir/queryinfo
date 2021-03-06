package com.evanzeimet.queryinfo.it;

/*
 * #%L
 * queryinfo-integration-tests
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

import javax.persistence.EntityManager;

import com.evanzeimet.queryinfo.it.organizations.OrganizationEntity;
import com.evanzeimet.queryinfo.jpa.bean.entity.DefaultEntityQueryInfoBean;
import com.evanzeimet.queryinfo.jpa.bean.entity.DefaultEntityQueryInfoBeanContext;

public class TestQueryInfoBeanFactory {

	public static DefaultEntityQueryInfoBean<OrganizationEntity> createOrganizationEntityQueryInfoBean(EntityManager entityManager) {
		TestQueryInfoEntityContextRegistry entityContextRegistry = TestQueryInfoEntityContextRegistry.getInstance();

		DefaultEntityQueryInfoBeanContext<OrganizationEntity> beanContext = new DefaultEntityQueryInfoBeanContext<>();

		beanContext.setEntityContextRegistry(entityContextRegistry);
		beanContext.setEntityManager(entityManager);
		beanContext.setRootEntityClass(OrganizationEntity.class);

		DefaultEntityQueryInfoBean<OrganizationEntity> result = new DefaultEntityQueryInfoBean<>();
		result.setBeanContext(beanContext);

		return result;
	}
}
