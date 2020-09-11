package com.iexceed.importupdater.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewImportsDataDTO {

	private String basePath;

	private String[] paths;

	private List<ImportDetails> importsData;

}