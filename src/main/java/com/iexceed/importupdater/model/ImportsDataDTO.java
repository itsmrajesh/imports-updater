package com.iexceed.importupdater.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ImportsDataDTO {

	private boolean addToImports;

	private String basePath;

	private List<ImportDetails> importsData;

}
