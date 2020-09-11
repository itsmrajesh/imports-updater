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

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.iexceed.importupdater.model.ImportDetails;
import com.iexceed.importupdater.model.ImportsData;
import com.iexceed.importupdater.seeddata.SeedData;
import com.iexceed.importupdater.shutdown.AppShutDownUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UpdateALLImports {

	static String[] pathsToUpdate = { "Sources/Containers/AndroidStudio/app/src/main/java/com",
			"Sources/Plugins/AndroidStudio/app/src/main/java/com" };

	public static void initCMD(String basePath) {
		Assert.notNull(basePath, "Base path cant be null");

		SeedData seedData = new SeedData();
		ImportsData importsData = seedData.loadImportsData();

		if (!basePath.endsWith("/")) {
			basePath += "/";
		}

		List<ImportDetails> imports = importsData.getImportsData();
		Map<String, String> importsMap = new HashMap<>();

		for (ImportDetails imprt : imports) {
			importsMap.put(imprt.searchFor, imprt.replace);
		}

		start(basePath, pathsToUpdate, importsMap);

	}

	public static void initREST(String basePath, String[] pathsArr, Map<String, String> importsMap) {

		if (!basePath.endsWith("/")) {
			basePath += "/";
		}

		if (pathsArr != null) {
			pathsToUpdate = pathsArr;
		}

		start(basePath, pathsToUpdate, importsMap);

	}

	private static void start(String basePath, String[] pathsArr, Map<String, String> importsMap) {
		String fullPath = "";
		log.info("Init done..");
		for (String path : pathsArr) {

			if (path.startsWith("/")) {
				fullPath = basePath + (path.length() > 1 ? path.substring(1) : path); // ex: path = "/"
			} else {
				fullPath = basePath + path;
			}

			updateImports(fullPath, importsMap);
		}
		AppShutDownUtil.shutDown(0); // Stop
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
					if (importsMap.containsKey(line)) {
						line = line.replaceAll(line, importsMap.get(line));
						lines.set(i, line);
						isFileChanged = true;
					}
				}

			}

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
			log.info("Found {} files at base path {} ", files.size(), basePath);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return files;
	}

}
