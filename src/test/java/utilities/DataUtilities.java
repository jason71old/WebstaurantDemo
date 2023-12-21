package utilities;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.annotations.DataProvider;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

public class DataUtilities 
{
	@DataProvider
	public Object[] dataProvider() 
	{
		JSONParser parser = new JSONParser();
		JSONObject jsonObject = null;
		try 
		{
			Object obj = parser.parse(new FileReader("src/test/resources/AddItemClearItemFromCartData.json"));
			jsonObject = (JSONObject) obj;
		} 
		catch (IOException | ParseException exception) 
		{
			exception.printStackTrace();
		}
		Object[] data = new Object[1];
		HashMap<String, String> hashMap = new LinkedHashMap<>(); 
		if (jsonObject != null) 
		{
			Set<String> jsonObjKeys = jsonObject.keySet();
			for (String jsonObjKey : jsonObjKeys) 
			{
				hashMap.put(jsonObjKey, (String) jsonObject.get(jsonObjKey));
			}
		}
		else 
		{
			//log.error();
			throw new RuntimeException();
		}
		data[0] = hashMap;
		return data;
	}
}
