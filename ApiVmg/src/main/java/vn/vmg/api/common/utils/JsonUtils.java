package vn.vmg.api.common.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.vertx.core.json.JsonObject;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Format Json to object, to string and more...
 */
public class JsonUtils {

	/**
	 * auto map json file, json string, jsonObject to java class this method fill
	 * value from Json to Java Object
	 */
	private static <T> T parseJsonObjToClass(Object content, Class<T> mapValue, boolean skipUnknown)
			throws IOException {
		ObjectMapper mapper = new ObjectMapper();

		// config skip node json if this not exist in Object class
		if (skipUnknown) {
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		}

		if (content instanceof File) {
			return mapper.readValue((File) content, mapValue);
		} else if (content instanceof String) {
			return mapper.readValue(content.toString(), mapValue);
		} else if (content instanceof JsonObject) {
			return mapper.readValue(content.toString(), mapValue);
		}

		return mapper.readValue(mapper.writeValueAsString(content), mapValue);
	}

	public static <T> T mapStrJsToClass(String jsonString, Class<T> mapValue, boolean skipUnknown) throws IOException {
		return parseJsonObjToClass(jsonString, mapValue, skipUnknown);
	}

	public static <T> T mapFileJsToClass(File jsonFile, Class<T> mapValue, boolean skipUnknown) throws IOException {
		return parseJsonObjToClass(jsonFile, mapValue, skipUnknown);
	}

	public static <T> T mapFileToClass(String jsonFile, Class<T> mapValue, boolean skipUnknown) throws IOException {
		File f = new File(jsonFile);
		if (null == f || !f.exists())
			throw new IOException("Json config not exist!!! file= " + jsonFile);
		return mapFileJsToClass(f, mapValue, skipUnknown);
	}

	public static <T> T mapJsonObjectToClass(Object jsonObject, Class<T> mapValue, boolean skipUnknown)
			throws IOException {
		return parseJsonObjToClass(jsonObject, mapValue, skipUnknown);
	}

	public static void writeJsonFile(String pathFile, Object data, boolean overWrite) throws IOException {
		File file = new File(pathFile);

		if (file != null && file.exists() && !overWrite) {
			return;
		}
		ObjectMapper mapper = new ObjectMapper();
		ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
		writer.writeValue(new File(pathFile), data);
	}

	/**
	 * Convert class object to String Json
	 */
	public static String parseObjToJsStr(Object objectInput, boolean setPrettyFm) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		if (setPrettyFm) {
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(objectInput);
		} else {
			return mapper.writeValueAsString(objectInput);
		}
	}

	public static JsonNode toJsonNode(Object objInput) throws Exception {
		try {
			ObjectMapper mapper = new ObjectMapper();
			if (objInput instanceof String) {
				return mapper.readTree((String) objInput);
			} else if (objInput instanceof JsonNode) {
				return (JsonNode) objInput;
			}
			return mapper.convertValue(objInput, JsonNode.class);
		} catch (Exception e) {
			throw e;
		}
	}

	public static JsonNode toJsNodeSkipErr(Object objInput) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			if (objInput instanceof String) {
				return mapper.readTree((String) objInput);
			} else if (objInput instanceof JsonNode) {
				return (JsonNode) objInput;
			}
			return mapper.convertValue(objInput, JsonNode.class);
		} catch (Exception e) {
			return null;
		}
	}

	public static String toJsonStr(Object input) {
		try {
			return parseObjToJsStr(input, false);
		} catch (Exception ex) {
			System.out.println(ex);
			return "unknow format!";
		}
	}

	public static String toJsonStrPretty(Object input) {
		try {
			return parseObjToJsStr(input, true);
		} catch (Exception ex) {
			System.out.println(ex);
			return "unknow format!";
		}
	}

	public static void addEmptyObj(JsonNode jsonData, String nodeName) {
		ObjectNode jNode = (ObjectNode) jsonData;
		jNode.putObject(nodeName);
	}

	public static void addEmptyArr(JsonNode jsonData, String nodeName) {
		ObjectNode jNode = (ObjectNode) jsonData;
		jNode.putArray(nodeName);
	}

	public static void appendNode(JsonNode jsonNodeData, Object nodeData) throws Exception {
		ObjectNode jNodeData;
		if (nodeData instanceof String) {
			jNodeData = (ObjectNode) toJsonNode(nodeData.toString());
		} else if (nodeData instanceof JsonNode) {
			jNodeData = ((ObjectNode) nodeData);
		} else {
			throw new IOException("Format nodeData isn't String or JsonNode: " + nodeData);
		}

		if (jsonNodeData.isArray()) {
			((ArrayNode) jsonNodeData).add(jNodeData);
		} else {
			((ObjectNode) jsonNodeData).setAll(jNodeData);
		}
	}

	public static void addNodeValue(JsonNode jsonData, String nodeName, Object nodeData) {
		ObjectNode jNode = (ObjectNode) jsonData;
		if (nodeData instanceof String) {
			jNode.put(nodeName, nodeData.toString());
			return;
		}

		if (nodeData instanceof JsonNode) {
			JsonNode nodeTmp = (JsonNode) nodeData;
			jNode.set(nodeName, nodeTmp);
			return;
		}

		jNode.putPOJO(nodeName, nodeData);
	}

	public static void addDeepNodeValue(JsonNode jsonData, String pathNodeName, Object nodeData) {
		if (pathNodeName.lastIndexOf("/") < 1) {
			addNodeValue(jsonData, pathNodeName, nodeData);
			return;
		}
		String[] spl = pathNodeName.split("\\/");
		JsonNode tmp = jsonData;
		for (int i = 0, maxSize = spl.length; i < maxSize; i++) {
			if (spl[i].isEmpty())
				continue;
			if (i >= (maxSize - 1)) {
				addNodeValue(tmp, spl[i], nodeData);
			} else {
				if (!isExist(tmp, spl[i])) {
					addNodeValue(tmp, spl[i], newEmptyObj());
				}
				tmp = jsonData.at("/".concat(spl[i]));
			}
		}
	}

	public static boolean removeNode(JsonNode jsonData, String nodeName) {
		if (isNullOrEmpty(jsonData) || jsonData.isArray())
			return false;
		((ObjectNode) jsonData).remove(nodeName);
		return true;
	}

	public static boolean removeIndex(JsonNode jsonData, int idxRemove) {
		if (isNullOrEmpty(jsonData) || !jsonData.isArray() || jsonData.size() <= idxRemove)
			return false;
		((ArrayNode) jsonData).remove(idxRemove);
		return true;
	}

	public static JsonNode newEmptyObj() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readTree("{}");
		} catch (IOException e) {
			return null;
		}
	}

	public static JsonNode newEmptyArr() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readTree("[]");
		} catch (Exception e) {
			return null;
		}
	}

	public static boolean isNullOrEmpty(JsonNode nodeData) {
		if (nodeData == null) {
			return true;
		}

		if (!nodeData.isContainerNode()
				&& (nodeData.isNull() || "null".equals(nodeData.asText()) || nodeData.asText().isEmpty())) {
			return true;
		}

		if (nodeData.isContainerNode() && nodeData.toString().matches("^(\\[\\])|(\\{\\})$")) {
			return true;
		}

		return false;
	}

	public static boolean isNull(JsonNode nodeData) {
		if (nodeData == null) {
			return true;
		}

		if (!nodeData.isContainerNode() && (nodeData.isNull() || "null".equals(nodeData.asText()))) {
			return true;
		}

		return false;
	}

	public static boolean isExist(JsonNode nodeData, String prefixNodeName) {
		try {
			int lastIdx = prefixNodeName.lastIndexOf("/");
			if (lastIdx > 1) {
				if (!prefixNodeName.startsWith("/")) {
					prefixNodeName = "/" + prefixNodeName;
					lastIdx++;
				}
				String endNode = prefixNodeName.substring(lastIdx + 1);
				return nodeData.at(prefixNodeName.substring(0, lastIdx)).has(endNode);
			} else {
				if (prefixNodeName.startsWith("/"))
					prefixNodeName = prefixNodeName.substring(1);
				return nodeData.has(prefixNodeName);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return false;
	}

	public static boolean nodeIsNullOrEmpty(JsonNode nodeData, String nodeName) {
		return isNullOrEmpty(getValue(nodeData, nodeName));
	}

	public static String jsToString(JsonNode jsonDataNode) {
		if (isNull(jsonDataNode)) {
			return null;
		}

		if (jsonDataNode.isContainerNode()) {
			String strNode = jsonDataNode.toString();
			if (strNode.matches("^(\\[\\])|(\\{\\})$"))
				strNode = null;
			return strNode;
		}

		if (jsonDataNode.isNumber() && jsonDataNode.asText().contains("E")) {
			return new BigDecimal(jsonDataNode.asText()).toPlainString();
		}
		return jsonDataNode.asText();
	}

	public static String objGetString(Object objectData) {
		if (objectData != null && objectData instanceof JsonNode) {
			return jsToString((JsonNode) objectData);
		}
		return ApiUtils.toStr(objectData);
	}

	public static JsonNode coalesceNode(JsonNode sourceA, JsonNode sourceB, String nodeName) {
		JsonNode tmp;
		tmp = getValue(sourceA, nodeName);
		if (tmp != null)
			return tmp;

		return getValue(sourceB, nodeName);
	}

	public static JsonNode getValue(JsonNode jsonNode, String nodeName) {
		try {
			if (!nodeName.startsWith("/"))
				nodeName = "/".concat(nodeName);
			if (isExist((jsonNode.isArray() ? jsonNode.get(0) : jsonNode), nodeName))
				return (jsonNode.isArray() ? jsonNode.get(0).at(nodeName) : jsonNode.at(nodeName));
		} catch (Exception e) {
		}
		return null;
	}

	public static JsonNode getValueWithPattern(JsonNode jsonNode, String nodeNamePattern) {
		try {
			JsonNode resJs = jsonNode;
			// xu ly voi mau patter co dinh dang /abc[0]/asd hoac [123]/asd/abc
			if (nodeNamePattern.contains("]")) {
				String[] splPattern = nodeNamePattern.split("\\[");
				int idx = 0;
				String path;
				for (String pt : splPattern) {
					if (pt.isEmpty())
						continue;

					if (pt.contains("]")) {
						idx = ApiUtils.toInt(pt.substring(0, pt.indexOf("]")), -1);
						if (idx < 0)
							return null;
						path = pt.substring(pt.indexOf("]") + 1);
					} else {
						idx = -1;
						path = pt;
					}

					if (idx >= 0) {
						if (!resJs.isArray() || resJs.size() <= idx)
							return null;
						resJs = resJs.get(idx);
					}

					if (!path.startsWith("/"))
						path = "/".concat(path);
					if (!isExist(resJs, path))
						return null;
					resJs = resJs.at(path);
					continue;
				}
			} else {
				if (!nodeNamePattern.startsWith("/"))
					nodeNamePattern = "/".concat(nodeNamePattern);
				if (!isExist(jsonNode, nodeNamePattern))
					return null;
				resJs = jsonNode.at(nodeNamePattern);
			}
			return resJs;
		} catch (Exception e) {
		}
		return null;
	}

	public static String getString(String data, String nodeName) {
		try {
			return getString(toJsonNode(data), nodeName);
		} catch (Exception e) {
		}
		return null;
	}

	public static String getString(JsonNode jsonNode, String nodeName) {
		try {
			return jsToString(getValue(jsonNode, nodeName));
		} catch (Exception e) {
		}
		return null;
	}
	
	public static String getString(JsonNode jsonNode, String nodeName, String defaultValue) {
		JsonNode schemaNode = JsonUtils.getValue(jsonNode, nodeName);
		if (JsonUtils.isNullOrEmpty(schemaNode))
			return defaultValue;
		else
			return jsToString(schemaNode);
	}

	public static int getInt(JsonNode jsonNode, String nodeName, int defaultValue) {
		return ApiUtils.toInt(getString(jsonNode, nodeName), defaultValue);
	}

	public static int getInt(JsonNode jsonNode, String nodeName) throws IOException {
		return ApiUtils.toInt(getString(jsonNode, nodeName), -1);
	}

	public static int getInt(String jsonStringData, String nodeName) throws IOException {
		return ApiUtils.toInt(getString(jsonStringData, nodeName), -1);
	}

	public static long getLong(JsonNode jsonNode, String nodeName, long defaultValue) {
		return ApiUtils.toLong(getString(jsonNode, nodeName), defaultValue);
	}

	public static long getLong(JsonNode jsonNode, String nodeName) throws IOException {
		return ApiUtils.toLong(getString(jsonNode, nodeName), -1);
	}

	public static long getLong(String jsonStringData, String nodeName) throws IOException {
		return ApiUtils.toLong(getString(jsonStringData, nodeName), -1);
	}

	public static Long getLongObj(JsonNode jsonNode, String nodeName, Long defaultValue) {
		return ApiUtils.toLongObj(getString(jsonNode, nodeName), defaultValue);
	}

	public static Long getLongObj(JsonNode jsonNode, String nodeName) throws IOException {
		return ApiUtils.toLongObj(getString(jsonNode, nodeName), null);
	}

	public static Long getLongObj(String jsonStringData, String nodeName) throws IOException {
		return ApiUtils.toLongObj(getString(jsonStringData, nodeName), null);
	}

	public static Map<String, Object> toMap(Object jsonNode) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			if (jsonNode instanceof JsonNode) {
				return mapper.convertValue(jsonNode, new TypeReference<Map<String, Object>>() {
				});
			}
			return mapper.convertValue(toJsonNode(jsonNode), new TypeReference<Map<String, Object>>() {
			});
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	public static List<Map<String, Object>> arrToMap(Object jsonNode) {
		List<Map<String, Object>> result = null;
		try {
			if (jsonNode != null) {
				ObjectMapper mapper = new ObjectMapper();
				if (jsonNode instanceof JsonNode) {
					result = mapper.convertValue(jsonNode, new TypeReference<List<Map<String, Object>>>() {
					});
				} else {
					result = mapper.convertValue(toJsonNode(jsonNode), new TypeReference<List<Map<String, Object>>>() {
					});
				}
			}
		} catch (Exception e) {
			System.out.println(ApiUtils.writeOutException(e));
		}
		if (result == null)
			result = new ArrayList<Map<String, Object>>();
		if (result.isEmpty())
			result.add(new HashMap<String, Object>());
		return result;
	}

	public static Object getFirstNodeExport(JsonNode jsData, String lstCfgNode, boolean isArr) {
		JsonNode nodeData = null;
		try {
			if (jsData != null && lstCfgNode != null) {
				String[] splCfg = lstCfgNode.split(",");
				for (String cfg : splCfg) {
					nodeData = getValueWithPattern(jsData, cfg);
					if (nodeData != null)
						break;
				}
			}
		} catch (Exception e) {
			System.out.println(ApiUtils.writeOutException(e));
		}

		return (isArr ? arrToMap(nodeData) : jsToString(nodeData));
	}

	public static JsonNode firstKeyToLowerObj(String jsonInput) throws IOException {
		JsonNode objNodeNew = newEmptyObj();
		try {
			JsonNode node = toJsonNode(jsonInput);
			if (!node.isArray() && !isKeyLowerObj(node)) {
				Iterator<String> inter = node.fieldNames();
				String keyNode;
				String newKey;
				while (inter.hasNext()) {
					keyNode = inter.next();
					newKey = keyNode.substring(0, 1).toLowerCase() + keyNode.substring(1);
					addNodeValue(objNodeNew, newKey, getValue(node, keyNode));
				}
				return objNodeNew;
			} else {
				return node;
			}
		} catch (Exception e) {
			throw new IOException("First Key To Lower Obj Err! ", e);
		}
	}

	public static boolean isKeyLowerObj(JsonNode jsonInput) throws IOException {
		try {
			if (!jsonInput.isArray()) {
				Iterator<String> inter = jsonInput.fieldNames();
				if (inter.hasNext()) {
					String firstChar = Character.toString(inter.next().charAt(0));
					return firstChar.equals(firstChar.toLowerCase());
				}
			}
		} catch (Exception e) {
			throw new IOException("check key lower error! ", e);
		}
		return true;
	}


}
