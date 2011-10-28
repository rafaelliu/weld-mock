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
package net.rafaelliu.weld.mock.junit;

import net.rafaelliu.weld.mock.context.MockContext;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.junit.After;
import org.junit.Before;

public class WeldMockTest {
	
	protected MockContext mockContext;
	protected WeldContainer weldContainer; 

	@Before
	public void init() {
		weldContainer = new Weld().initialize();
		mockContext = MockContext.getInstance();
	}
	
	@After
	public void destroy() {
		mockContext.clear();
	}
	
	public <T> T getBean(Class<T> clazz) {
		return weldContainer.instance().select(clazz).get();
	}
	
}
