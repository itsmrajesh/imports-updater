package com.iexceed.importupdater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import com.iexceed.importupdater.fileutil.UpdateALLImports;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class ImportupdaterApplication implements CommandLineRunner {

//	--basepath=/home/user/documents/ --server.port=8085 command line args

	@Value("${server.port}")
	private String port;

	@Autowired
	private Environment env;

	public static void main(String[] args) {
		SpringApplication.run(ImportupdaterApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		System.out.println("-REST API : http://localhost:" + port + "/swagger-ui.html ");

		if (args.length > 0) {

			String basePath = env.getProperty("basepath");

			if ((basePath != null && !basePath.equalsIgnoreCase("emptypath"))) {
				System.out.println("Base Path : " + basePath);
				UpdateALLImports.initCMD(basePath);
			}

		}
	}

}
