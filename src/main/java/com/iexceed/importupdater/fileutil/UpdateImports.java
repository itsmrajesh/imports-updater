package com.iexceed.importupdater.fileutil;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.iexceed.importupdater.model.FileDetails;
import com.iexceed.importupdater.model.FilePaths;
import com.iexceed.importupdater.model.ImportDetails;
import com.iexceed.importupdater.model.ImportsData;
import com.iexceed.importupdater.seeddata.SeedData;

public class UpdateImports {

/*	public static void init() {
		SeedData seedData = new SeedData();
		ImportsData importsData = seedData.loadImportsData();
		FilePaths pathsData = seedData.loadPathsData();
		start(pathsData, importsData);
	}

	public static void start(FilePaths rawFiles, ImportsData importsData) {

		List<FileDetails> files = rawFiles.getFilesPaths();
		List<ImportDetails> imports = importsData.getImportsData();

		Map<String, List<String>> filesMap = new HashMap<>();
		Map<String, String> importsMap = new HashMap<>();

		List<String> filesList = null;

		for (FileDetails file : files) {
			if (filesMap.containsKey(file.Filepath)) {
				filesMap.get(file.Filepath).add(file.FileName);
			} else {
				filesList = new ArrayList<>();
				filesList.add(file.FileName);
				filesMap.put(file.Filepath, filesList);
			}
		}

		for (ImportDetails imprt : imports) {
			importsMap.put(imprt.searchFor, imprt.replace);
		}

		updateImports(filesMap, importsMap);

	}

	private static void updateImports(Map<String, List<String>> filesMap, Map<String, String> importsMap) {

		Set<Entry<String, List<String>>> entrySet = filesMap.entrySet();

		for (Entry<String, List<String>> keys : entrySet) {
			String filePath = keys.getKey();
			List<String> filesList = keys.getValue();
			findFile(filePath.substring(1), filesList, importsMap);
		}

	}

	private static void findFile(String filePath, List<String> filesList, Map<String, String> importsMap) {

		for (String fileName : filesList) {
			boolean isJavaFile = fileName.endsWith(".java");
			Path rootPath = Paths.get(filePath);
			String fileToFind = File.separator + fileName;

			try {
				Files.walkFileTree(rootPath, new SimpleFileVisitor<Path>() {

					@Override
					public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
						String fileString = file.toAbsolutePath().toString();
						System.out.println("pathString = " + fileString);

						if (fileString.endsWith(fileToFind)) {
							System.out.println("file found at path: " + file.toAbsolutePath());
							updateFile(file, importsMap, isJavaFile);
							return FileVisitResult.TERMINATE;
						}
						return FileVisitResult.CONTINUE;
					}

				});
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	protected static void updateFile(Path filePath, Map<String, String> importsMap, boolean isJavaFile) {
		try {

			List<String> lines = Files.readAllLines(filePath);

			String line = null;

			for (int i = 0; i < lines.size(); i++) {
				line = lines.get(i).trim();

				if (isJavaFile && line.startsWith("import")) {
					if (importsMap.containsKey(line)) {
						line = line.replaceAll(line, importsMap.get(line));
						lines.set(i, line);
					}
				} else {
					if (importsMap.containsKey(line)) {
						line = line.replaceAll(line, importsMap.get(line));
						lines.set(i, line);
					}
				}

			}

			Files.write(filePath, lines);
			System.out.println("File updated");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}*/

}
