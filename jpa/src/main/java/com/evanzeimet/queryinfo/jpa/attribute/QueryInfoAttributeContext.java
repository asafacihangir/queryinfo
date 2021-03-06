package com.evanzeimet.queryinfo.jpa.attribute;

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

import java.util.Map;

import com.evanzeimet.queryinfo.jpa.field.QueryInfoFieldInfo;
import com.evanzeimet.queryinfo.jpa.join.QueryInfoJoinInfo;

public interface QueryInfoAttributeContext {

	QueryInfoFieldInfo getField(String queryInfoFieldAttributeName);

	Map<String, QueryInfoFieldInfo> getFields();

	void setFields(Map<String, QueryInfoFieldInfo> fields);

	QueryInfoJoinInfo getJoin(String queryInfoJoinAttributeName);

	Map<String, QueryInfoJoinInfo> getJoins();

	void setJoins(Map<String, QueryInfoJoinInfo> joins);

}
