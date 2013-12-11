package com.largeandopen.twelve;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Parser {
	public static void main(String[] args) throws Exception {

		//URL 天氣
        URL rain = new URL("http://www.cwb.gov.tw/V7/observe/rainfall/Rain_Hr/13.htm");
     
        /*
           	向網頁伺服發出請求，並將回應分析成document。
         	第一個參數是：請求位置與QueryString。
         	第二個參數是：連線時間(毫秒)，在指定時間內若無回應則會丟出IOException
         */       
        Document doc = Jsoup.parse(rain, 3000);
        //取回所center下所有的table
        Elements tables = doc.select("center>table");
       // Elements tables = doc.select("table");
        Iterator iterator;
        //print all table 
        
      /*  for(Element table : tables)
        {
            //get td Iterator
         iterator = table.select("td").iterator();           
           
            while(iterator.hasNext())
            {               
                System.out.println("data" + ": " + ((Element) iterator.next()).text().trim());
            }          
        }*/ 
                   
        Element table;
        Element ty_table;
        table = tables.get(1).select("tbody").get(0);        
        iterator = table.select("tr").iterator();   
        int i = 0;
        while(iterator.hasNext()) {   
        	i++;
            System.out.println("data" + ": " + ((Element) iterator.next()).text().trim()+" "+i);
        } 
        
        //抓最新 y_table = ty_tables.get(0).select("tr").get(2);     
        URL typhoon = new URL("http://rdc28.cwb.gov.tw/tylist_warning.php");
        
        Document ty_doc = Jsoup.parse(typhoon, 3000);
        //取回所center下所有的table
        Elements ty_tables = ty_doc.select("body>table");
        ty_table = ty_tables.get(0).select("tr").get(3);        
        iterator = ty_table.select("FONT").iterator(); 
        String test = null;
        while(iterator.hasNext()){   
        	test += ((Element) iterator.next()).text().trim().toString() + "-";
           // System.out.println("data" + ": " + ((Element) iterator.next()).text().trim());
        } 
        System.out.println(test);
       
        String[] names = test.split("-");
        int rr=0;
        for(String name:names){
          System.out.println(name+rr);
          rr++;}
      
        System.out.print(record(1));
        
    
       
	}
	
	public static String record(int scale) throws IOException {
		//挖颱風網頁資料
		String same = "";
		URL typhoon = new URL("http://rdc28.cwb.gov.tw/tylist_warning.php");
        Document ty_doc = Jsoup.parse(typhoon, 3000);
		Elements ty_tables = ty_doc.select("body>table");
		for(int i = 3;i<20;i++) {
			String name = null;
			Element ty_table = ty_tables.get(0).select("tr").get(i);        
			Iterator iterator = ty_table.select("FONT").iterator(); 
        
			while(iterator.hasNext()) {   
				name += ((Element) iterator.next()).text().trim().toString() + "@";
				// System.out.println("data" + ": " + ((Element) iterator.next()).text().trim());
			} 
			String[] result = name.split("@");
			System.out.println("路徑"+i+":"+result[6]+"  scale"+":"+scale);
			if(result[6].toString().equals(String.valueOf(scale))) {	
				same += result[2] + "@";	
			}
			
		}
		return same;
		
	}
}
