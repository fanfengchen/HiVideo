package com.ebeijia.videocore.web.json;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

@SuppressWarnings("serial")
public class NullJsonObjectMaper extends ObjectMapper{

	public NullJsonObjectMaper() {
		super();
		this.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>(){

			@Override
			public void serialize(Object value, JsonGenerator jgen,
					SerializerProvider provider) throws IOException,
					JsonProcessingException {
				jgen.writeString("");
			}
			
		});
	}

}
