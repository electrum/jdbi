/*
 * Copyright (C) 2004 - 2014 Brian McCallister
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.skife.jdbi.v2;

import org.skife.jdbi.v2.tweak.ContainerFactory;

class UnwrappedSingleValueFactory implements ContainerFactory<Object>
{
    @Override
    public boolean accepts(Class<?> type)
    {
        return UnwrappedSingleValue.class.equals(type);
    }

    @Override
    public ContainerBuilder newContainerBuilderFor(Class<?> type)
    {
        return new ContainerBuilder<Object>()
        {
            private Object it;

            @Override
            public ContainerBuilder add(Object it)
            {
                this.it = it;
                return this;
            }

            @Override
            public Object build()
            {
                return it;
            }
        };
    }

}
