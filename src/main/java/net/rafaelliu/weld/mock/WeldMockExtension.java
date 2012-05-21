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

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.InjectionTarget;
import javax.enterprise.inject.spi.ProcessInjectionTarget;
import javax.enterprise.inject.spi.ProcessProducer;
import javax.enterprise.inject.spi.Producer;

import net.rafaelliu.weld.mock.context.DoubleType;
import net.rafaelliu.weld.mock.context.Expectations;
import net.rafaelliu.weld.mock.context.MockContext;
import net.rafaelliu.weld.mock.wrapper.WrappedInjectionTarget;
import net.rafaelliu.weld.mock.wrapper.WrappedProducer;

import org.mockito.Mockito;

public class WeldMockExtension implements Extension {

	private MockContext mockContext = MockContext.getInstance();

	public <T> void processInjectionTarget(
			@Observes final ProcessInjectionTarget<T> pit) {
		final InjectionTarget<T> it = pit.getInjectionTarget();

		InjectionTarget<T> wrapped = new WrappedInjectionTarget<T>(it) {
			@Override
			public T produce(CreationalContext<T> ctx) {
				Class<T> baseType = pit.getAnnotatedType().getJavaClass();
				T testDouble = getTestDouble(baseType, it, ctx);
				return testDouble != null ? testDouble : it.produce(ctx);
			}
		};
		pit.setInjectionTarget(wrapped);
	}

	public <T, X> void processProcessProducer(
			@Observes final ProcessProducer<T, X> pp) {
		final Producer<X> producer = pp.getProducer();

		Producer<X> wrapped = new WrappedProducer<X>(producer) {
			@Override
			@SuppressWarnings("unchecked")
			public X produce(CreationalContext<X> ctx) {
				Type baseType = pp.getAnnotatedMember().getBaseType();
				Class<X> baseClazz = null;
				if (baseType instanceof ParameterizedType) {
					baseClazz = (Class<X>) ((ParameterizedType) baseType).getRawType();
				} else {
					baseClazz = (Class<X>) baseType;
				}

				X testDouble = getTestDouble(baseClazz, producer, ctx);

				return testDouble != null ? testDouble : producer.produce(ctx);
			}
		};
		pp.setProducer(wrapped);
	}

	private <T> T getTestDouble(Class<T> clazz, Producer<T> producer,
			CreationalContext<T> ctx) {
		T mock = null;
		if (mockContext.getType(clazz) == DoubleType.MOCK) {
			mock = Mockito.mock(clazz);
			System.out.println("Mocking " + clazz);
		} else if (mockContext.getType(clazz) == DoubleType.SPY) {
			T obj = producer.produce(ctx);
			mock = Mockito.spy(obj);
			System.out.println("Spying " + clazz);
		}

		Expectations<T> expectations = mockContext.getExpectations(clazz);
		if (expectations != null) {
			expectations.defineExpectations(mock);
		}
		return mock;
	}

}
