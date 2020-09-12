package com.iexceed.importupdater.fileutil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.util.Assert;

import com.iexceed.importupdater.model.ImportDetails;
import com.iexceed.importupdater.model.ImportsData;
import com.iexceed.importupdater.seeddata.SeedData;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UpdateALLImports {

	public static void init(String basePath) {
		Assert.notNull(basePath, "Base path cant be null");

		SeedData seedData = new SeedData();
		ImportsData importsData = seedData.loadImportsData();

		String[] paths = { "app/Appzillon/Sources/Containers/AndroidStudio/app/src/main/java/com",
				"app/Appzillon/Sources/Plugins/AndroidStudio/app/src/main/java/com",
				"app/Appzillon/Sources/Containers/AndroidStudio/app/src/main/res/layout" };

		if (!basePath.endsWith("/")) {
			basePath += "/";
		}

		List<ImportDetails> imports = importsData.getImportsData();
		Map<String, String> importsMap = new HashMap<>();

		for (ImportDetails imprt : imports) {
			importsMap.put(imprt.searchFor, imprt.replace);
		}

		log.info("-----INIT DONE-----");

		for (String path : paths) {
			updateImports(basePath + path, importsMap);
		}

	}

	private static void updateImports(String path, Map<String, String> importsMap) {
		List<String> files = getAllFiles(path);
		writeToFile(files, importsMap);

	}

	private static void writeToFile(List<String> files, Map<String, String> importsMap) {
		Path path = null;
		for (String filePath : files) {
			path = Paths.get(filePath);
			updateFile(path, importsMap, filePath.endsWith(".java"));
		}
	}

	private static void updateFile(Path filePath, Map<String, String> importsMap, boolean isJavaFile) {
		try {

			List<String> lines = Files.readAllLines(filePath);

			String line = null;
			
			String str = "";

			boolean isFileChanged = false;

			for (int i = 0; i < lines.size(); i++) {
				line = lines.get(i).trim();

				if (isJavaFile && line.startsWith("import")) {
					if (importsMap.containsKey(line)) {
						line = line.replaceAll(line, importsMap.get(line));
						lines.set(i, line);
						isFileChanged = true;
					}
				} else {

					if (line.startsWith("</") && line.endsWith(">")) {
						int endIndex = line.lastIndexOf(">");
						line = line.substring(2, endIndex).trim();
						if (importsMap.containsKey(line)) {
							line = line.replaceAll(line, importsMap.get(line));
							line = "</" + line + ">";
							lines.set(i, line);
							isFileChanged = true;
						}

					}

					else if (line.startsWith("<") && !line.endsWith("?>")) {
						line = line.substring(1);
						int index = line.indexOf("xmlns:");
						if (index != -1) {
							str = line.substring(index);
							line = line.substring(0, index - 1).trim();
						}
						if (importsMap.containsKey(line)) {
							line = line.replaceAll(line, importsMap.get(line));
							line = "<" + line +" "+ str;
							lines.set(i, line);
							isFileChanged = true;
						}
					}
				}

				
				
			} // for loop end

			if (isFileChanged) {
				Path status = Files.write(filePath, lines);
				if (status != null) {
					log.info("File updated successfully at location {} ", filePath.toAbsolutePath().toString());
				} else {
					log.info("File failed to update at location {} ", filePath.toAbsolutePath().toString());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static List<String> getAllFiles(String basePath) {

		List<String> files = new ArrayList<>();

		Path path = Paths.get(basePath);
		try (Stream<Path> walk = Files.walk(path)) {

			files = walk.filter(Files::isRegularFile).map(x -> x.toString()).collect(Collectors.toList());
			log.info("Found {} files at location {} ", files.size(), basePath);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return files;
	}

}
