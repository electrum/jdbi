/*
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
package org.jdbi.v3;

import static org.jdbi.v3.Types.findGenericParameter;

import java.lang.reflect.Type;

import org.jdbi.v3.tweak.ResultSetMapper;

class InferredMapperFactory<X> implements ResultSetMapperFactory
{
    private final Type maps;
    private final ResultSetMapper<X> mapper;

    InferredMapperFactory(ResultSetMapper<X> mapper)
    {
        this.maps = findGenericParameter(mapper.getClass(), ResultSetMapper.class)
                .orElseThrow(() -> new UnsupportedOperationException("Must use a concretely typed ResultColumnMapper here"));
        this.mapper = mapper;
    }

    @Override
    public boolean accepts(Type type, StatementContext ctx)
    {
        return maps.equals(type);
    }

    @Override
    public ResultSetMapper<?> mapperFor(Type type, StatementContext ctx)
    {
        if (!type.equals(maps)) {
            throw new IllegalArgumentException("Expected to map " + type + " but I only map " + maps);
        }
        return mapper;
    }
}
