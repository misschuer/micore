package cc.mi.core.manager;

import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import cc.mi.core.utils.FileUtils;

public enum GameDataManager {
	INSTANCE;
	private final Map<String, JsonObject> jsonHash = new HashMap<>();
	
	public void loads() {
		URL url = MapTemplateManager.class.getResource("/datas");
		List<File> fileList = FileUtils.INSTANCE.listFilesInCurrentDirectory(url.getPath());
		try {
			for (File file : fileList) {
				JsonParser parser = new JsonParser();
				JsonObject json = (JsonObject)parser.parse(new JsonReader(new FileReader(file)));
				jsonHash.put(file.getName().split("\\.")[ 0 ], json);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	public void testWithJson() {
		String filename = "D:\\python_dict\\tb_area_demo.json";
		
		try {
			JsonParser parser = new JsonParser();
			File file = new File(filename);
			JsonObject json = (JsonObject)parser.parse(new JsonReader(new FileReader(file)));
			System.out.println(file.getName().split("\\.")[ 0 ]);
			for (Entry<String, JsonElement> info : json.entrySet()) {
				System.out.println(info.getKey());
				JsonElement element = info.getValue();
				JsonObject json2 = element.getAsJsonObject();
				for (Entry<String, JsonElement> info2 : json2.entrySet()) {
					System.out.println("	" + info2.getKey() + " => " + info2.getValue());
					JsonElement ele2 = info2.getValue();
					if (ele2.isJsonArray()) {
						JsonArray arr = ele2.getAsJsonArray();
						for (int i = 0; i < arr.size(); ++ i) {
							System.out.println("		" + arr.get(i));
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
