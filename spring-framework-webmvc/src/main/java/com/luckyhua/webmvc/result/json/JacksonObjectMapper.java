package com.luckyhua.webmvc.result.json;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.CustomSerializerFactory;

import java.io.IOException;

public class JacksonObjectMapper extends ObjectMapper {

	public JacksonObjectMapper() {
		super();
		this.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		this.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		this.configure(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS, false);
		this.configure(JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS, false);
//		this.configure(Feature.WRITE_NULL_MAP_VALUES, false);
//		this.configure(Feature.WRITE_NULL_PROPERTIES, false);
//		this.configure(Feature.FAIL_ON_EMPTY_BEANS, true);
//		this.configure(JsonParser.Feature.ALLOW_NON_NUMERIC_NUMBERS, true);
//		this.configure(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS, true);
		
		CustomSerializerFactory factory = new CustomSerializerFactory();  
        this.setSerializerFactory(factory);   
		
		this.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
			@Override
			public void serialize(Object value, JsonGenerator jg, SerializerProvider sp) throws IOException, JsonProcessingException {
				jg.writeNull();
			}
		});
	}
}