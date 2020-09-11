package com.iexceed.importupdater.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iexceed.importupdater.model.ImportsDataDTO;
import com.iexceed.importupdater.model.NewImportsDataDTO;
import com.iexceed.importupdater.service.ImportService;

@RestController
@RequestMapping("/api/v2/app/")
@CrossOrigin
public class ImportsController {

	@Autowired
	private ImportService service;

	@PostMapping("/add")
	public void addImports(@RequestBody ImportsDataDTO imports) {
		if (service.addImports(imports)) {
			System.out.println("----------------------------------");
			System.out.println("imports updated successfully");
		}

		System.out.println("Failed to update imports, try again");

	}

	@PostMapping("/new")
	public void newImports(@RequestBody NewImportsDataDTO imports) {
		if (service.newImports(imports)) {
			System.out.println("----------------------------------");
			System.out.println("imports updated successfully");
		}

		System.out.println("Failed to update imports, try again");
	}

}
