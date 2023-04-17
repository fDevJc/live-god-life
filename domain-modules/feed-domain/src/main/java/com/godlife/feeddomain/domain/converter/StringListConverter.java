package com.godlife.feeddomain.domain.converter;

import static java.util.Objects.*;

import java.util.Arrays;
import java.util.List;

import javax.persistence.AttributeConverter;
import javax.persistence.Convert;

import org.springframework.util.StringUtils;

@Convert
public class StringListConverter implements AttributeConverter<List<String>, String> {
	@Override
	public String convertToDatabaseColumn(List<String> attribute) {
		if (isNull(attribute)) {
			return null;
		}
		return StringUtils.collectionToCommaDelimitedString(attribute);
	}

	@Override
	public List<String> convertToEntityAttribute(String dbData) {
		if (isNull(dbData)) {
			return null;
		}
		return Arrays.asList(dbData.split(","));
	}
}
