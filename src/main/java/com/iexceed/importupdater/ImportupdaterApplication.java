package com.iexceed.importupdater;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.iexceed.importupdater.fileutil.UpdateALLImports;

@SpringBootApplication
public class ImportupdaterApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ImportupdaterApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// UpdateImports.init();

		if (args.length > 0) {
			String basePath = args[0];
			System.out.println("Base Path : " + basePath);
			UpdateALLImports.init(basePath);
		} else {
			System.out.println("Provide Bath Path to update imports");
			String path = "/home/users/rajesh.m/app/";
			System.out.println("Example: java -jar importupdater-0.0.1-SNAPSHOT.jar " + "''" + path + "''");
		}
	}

}
