package com.iexceed.importupdater.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.iexceed.importupdater.fileutil.UpdateALLImports;
import com.iexceed.importupdater.model.ImportDetails;
import com.iexceed.importupdater.model.ImportsData;
import com.iexceed.importupdater.model.ImportsDataDTO;
import com.iexceed.importupdater.model.NewImportsDataDTO;
import com.iexceed.importupdater.seeddata.SeedData;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ImportService {

	@Autowired
	private SeedData seedData;

	public boolean addImports(ImportsDataDTO importsDataDTO) {
		Assert.notNull(importsDataDTO, "imports data cant be null");
		Assert.notNull(importsDataDTO.getBasePath(), "BasePath cant be null");
		Assert.notNull(importsDataDTO.getImportsData(), "Imports data cant be null");

		List<ImportDetails> newImports = importsDataDTO.getImportsData();

		Map<String, String> importsMap = new HashMap<>();
		ImportsData data = seedData.loadImportsData();

		if (importsDataDTO.isAddToImports()) {
			data.getImportsData().addAll(newImports);
			newImports = data.getImportsData();
			importsMap = getImportsMap(newImports);
		} else {
			newImports = data.getImportsData();
			importsMap = getImportsMap(newImports);
		}

		UpdateALLImports.initREST(importsDataDTO.getBasePath(), null, importsMap);

		return true;
	}

	public boolean newImports(NewImportsDataDTO importsDataDTO) {
		Assert.notNull(importsDataDTO, "imports data cant be null");
		Assert.notNull(importsDataDTO.getBasePath(), "BasePath cant be null");
		Assert.notNull(importsDataDTO.getImportsData(), "Imports data cant be null");
		Assert.notNull(importsDataDTO.getPaths(), "paths array cant be null");

		List<ImportDetails> newImports = importsDataDTO.getImportsData();

		Map<String, String> importsMap = getImportsMap(newImports);

		UpdateALLImports.initREST(importsDataDTO.getBasePath(), importsDataDTO.getPaths(), importsMap);

		return true;
	}

	private Map<String, String> getImportsMap(List<ImportDetails> importsList) {
		Map<String, String> importsMap = new HashMap<>();

		log.info("Total imports are {} ", importsList.size());

		for (ImportDetails imprt : importsList) {
			importsMap.put(imprt.searchFor, imprt.replace);
		}

		return importsMap;
	}

}
