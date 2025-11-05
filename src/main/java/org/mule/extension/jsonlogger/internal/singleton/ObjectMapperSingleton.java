package org.mule.extension.jsonlogger.internal.singleton;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

public class ObjectMapperSingleton {

    // JSON Object Mapper
    private final ObjectMapper om = JsonMapper.builder()
            .addModule(new Jdk8Module())
            .serializationInclusion(Include.NON_NULL)
            .build();

    public ObjectMapper getObjectMapper() {
        return this.om;
    }
}
