/**
 * Copyright 2011 rafael liu <rafaelliu@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.rafaelliu.weld.mock.context;

import java.util.HashMap;
import java.util.Map;

public class MockContext {

	private static ThreadLocal<MockContext> mockContext = new ThreadLocal<MockContext>() {
		@Override
		protected MockContext initialValue() {
			return new MockContext();
		}
	};
	private Map<Class<?>, Expectations<?>> expectations = new HashMap<Class<?>, Expectations<?>>();
	private Map<Class<?>, DoubleType> types = new HashMap<Class<?>, DoubleType>();

	public static MockContext getInstance() {
		return mockContext.get();
	}

	public void clear() {
		expectations.clear();
		types.clear();
	}

	public <T> void addExpectations(Expectations<T> exp) {
		addExpectations(DoubleType.MOCK, exp);
	}

	/**
	 * Expectations are not cumulative. That is, adding an expectation
	 * overrides previous expectations instead of "appending"
	 * 
	 * @param exp The (probably) anonymous Expcetation class
	 */
	public <T> void addExpectations(DoubleType type, Expectations<T> exp) {
		Class<?> clazz = exp.getType();

		DoubleType previousType = types.get(clazz);
		if (previousType != null && previousType != type) {
			throw new RuntimeException("Bean " + clazz + " cannot be registered as "
							+ type + ". It's already registered as " + previousType);
		}

		expectations.put(clazz, exp);
		types.put(clazz, type);
	}

	@SuppressWarnings("unchecked")
	public <T> Expectations<T> getExpectations(Class<T> clazz) {
		return (Expectations<T>) expectations.get(clazz);
	}

	public DoubleType getType(Class<?> clazz) {
		return types.get(clazz);
	}

}
