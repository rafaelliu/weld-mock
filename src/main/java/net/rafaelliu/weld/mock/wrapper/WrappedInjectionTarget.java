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
package net.rafaelliu.weld.mock.wrapper;

import java.util.Set;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.InjectionTarget;

public class WrappedInjectionTarget<T> implements InjectionTarget<T> {
	
	private InjectionTarget<T> injectionTarget;
	
	public WrappedInjectionTarget(InjectionTarget<T> injectionTarget) {
		this.injectionTarget = injectionTarget;
	}

	public T produce(CreationalContext<T> ctx) {
		return injectionTarget.produce(ctx);
	}

	public void dispose(T instance) {
		injectionTarget.dispose(instance);
	}

	public Set<InjectionPoint> getInjectionPoints() {
		return injectionTarget.getInjectionPoints();
	}

	public void inject(T instance, CreationalContext<T> ctx) {
		injectionTarget.inject(instance, ctx);
	}

	public void postConstruct(T instance) {
		injectionTarget.postConstruct(instance);
	}

	public void preDestroy(T instance) {
		injectionTarget.preDestroy(instance);
	}

}
