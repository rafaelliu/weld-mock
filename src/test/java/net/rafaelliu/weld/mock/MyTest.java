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
package net.rafaelliu.weld.mock;

import net.rafaelliu.weld.mock.context.DoubleType;
import net.rafaelliu.weld.mock.context.Expectations;
import net.rafaelliu.weld.mock.junit.WeldMockTest;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

public class MyTest extends WeldMockTest {
	
	@Test
	public void testReal() {
		MyBean bean = getBean(MyBean.class);

		String calculate = bean.calculate(2, 3);
		Assert.assertThat(calculate, CoreMatchers.is("multiplication is 6"));
	}
	
	@Test
	public void testMock() {
		mockContext.addExpectations(new Expectations<MyDependency>() {
			@Override
			public void defineExpectations(MyDependency mock) {
				when(mock.multiply(2, 3)).thenReturn(5);
			}
		});
		
		MyBean bean = getBean(MyBean.class);

		String calculate = bean.calculate(3, 3);
		Assert.assertThat(calculate, CoreMatchers.is("multiplication is 0"));

		calculate = bean.calculate(2, 3);
		Assert.assertThat(calculate, CoreMatchers.is("multiplication is 5"));

		calculate = bean.calculate(2, 4);
		Assert.assertThat(calculate, CoreMatchers.is("multiplication is 0"));
	}
	
	@Test
	public void testSpy() {
		mockContext.addExpectations(DoubleType.SPY, new Expectations<MyDependency>() {
			@Override
			public void defineExpectations(MyDependency mock) {
				when(mock.multiply(2, 3)).thenReturn(5);
			}
		});
		
		MyBean bean = getBean(MyBean.class);

		String calculate = bean.calculate(3, 3);
		Assert.assertThat(calculate, CoreMatchers.is("multiplication is 9"));

		calculate = bean.calculate(2, 3);
		Assert.assertThat(calculate, CoreMatchers.is("multiplication is 5"));

		calculate = bean.calculate(2, 4);
		Assert.assertThat(calculate, CoreMatchers.is("multiplication is 8"));
	}

}
