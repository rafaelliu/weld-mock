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

import java.lang.reflect.ParameterizedType;

import org.mockito.Mockito;

/**
 * This class holds the expectations and the corresponding Java Class.
 * It extends Mockito for pratical reasons (having access to Mockito.* 
 * static methods)
 */
public abstract class Expectations<T> extends Mockito {
	
	public abstract void defineExpectations(T mock);
	
	public Class<?> getType() {
		ParameterizedType superclass = (ParameterizedType) getClass().getGenericSuperclass();
		return (Class<?>) ((ParameterizedType) superclass).getActualTypeArguments()[0];
	}

}
