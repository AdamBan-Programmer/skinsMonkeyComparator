package com.example.skinsMonkeyComparator;

import com.example.skinsMonkeyComparator.Items.Item;
import com.example.skinsMonkeyComparator.Utils.FormatConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SkinsMonkeyComparatorApplicationTests {

	/*@Test
	void contextLoads() {
	}


	@Test
	public void sortList() //sorting DESC
	{
		int[] items = {2,4,6,1,0,5,7,8,3,9};
		boolean shouldRepeat = true;
		while(shouldRepeat)
		{
			shouldRepeat = false;
			int size = items.length;
			for(int i =0;i<size;i++)
			{
				if(i < size-1) {
					int current = items[i];
					int next = items[i+1];
					if (current < next) {
						items[i] = next;
						items[i+1] = current;
						shouldRepeat = true;
						//break;
					}
				}
			}
		}
		String s = "";
		for(int item : items)
		{
			s += " " + item;
		}
		System.out.println(s);
	}
	@Test
	public void roundValue()
	{
		FormatConverter f = new FormatConverter();
		double value = ((36 / 100) * 4.16);
		double roundedValue= (f.roundValue(value));
	}

	@Test
	public void parseSteamPrice()
	{
		try {
			String json = "{\"success\":true,\"price\":\"1,99zł\",\"lowest_price\":\"2,19zł\",\"volume\":\"80,755\",\"median_price\":\"2,16zł\"}";
			json = json.replace("zł", "").replaceAll("(?<=\\d),(?=\\d)", ".");
			ObjectMapper objectMapper = new ObjectMapper();
			Item item = objectMapper.readValue(json,Item.class);
			double aa = item.getPriceSteam();
		}
		catch (Exception e)
		{
			int a =1;
		}
	}*/
}
