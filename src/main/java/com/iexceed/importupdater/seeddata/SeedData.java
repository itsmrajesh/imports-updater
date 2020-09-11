package com.iexceed.importupdater.seeddata;

import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iexceed.importupdater.model.ImportsData;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SeedData {

	public ImportsData loadImportsData() {
		ImportsData data = null;
		ObjectMapper objectMapper = new ObjectMapper();
		InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("importsdata.json");
		try {
			data = objectMapper.readValue(resourceAsStream, ImportsData.class);
			log.info("Total imports found : {} ", data.getImportsData().size());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return data;
	}

}
