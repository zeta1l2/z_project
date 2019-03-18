package com.dao;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
@Component
public class MongoDb {
	//가. 클러이언트
	final private MongoClient mc=new MongoClient("localhost",27017);
	//나. 데이터베이스
	MongoDatabase mdb;
	
	//mongo insert
	public void mongoInsert(String db,String collection,Object bean) throws NoSuchFieldException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		mdb=mc.getDatabase(db);
		MongoCollection<Document> coll=mdb.getCollection(collection);
		
		Map<String,Object> map=beanToMap(bean);
				
		Document doc=new Document(map);
		coll.insertOne(doc);
	}
	
	//mongo select
	public List<Map<String, Object>> mongoSelect(String db,String collection,Bson where) {
		mdb=mc.getDatabase(db);
		MongoCollection<Document> coll=mdb.getCollection(collection);
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		FindIterable<Document> docs=coll.find(where);
		Iterator<Document> it=docs.iterator();
		Document doc;
		
		while(it.hasNext()) {
			doc=it.next();
			String key=doc.keySet().toString();
			Map<String,Object> map=new HashMap<>();

			for(String a:arrKeys(key)) {
				map.put(a, doc.get(a));
			}
			
			list.add(map);
		}
		
		return list;
	}
	public List<Map<String, Object>> mongoSelect(String db,String collection) {
		return mongoSelect(db,collection,new BasicDBObject());
	}
	
	//mongo db key 값 배열화
	public String[] arrKeys(String key) {
		key=key.replace("[", "").replace("]", "").replace(" ", "");
		String keys[]=key.split(",");
		return keys;
	}
	
	//db에 저장되어있는 내용을 수정
	public String changeContent(String content) {
		String result=content.replaceAll(" ", "&nbsp")
				.replaceAll("<", "&lt").replaceAll(">", "&gt")
	            .replaceAll("\n", "<br>");
	    return result;
	}
	
	//db 현재 날짜 반환 
	public String getTime() {
		SimpleDateFormat format1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date time=new Date();
		String time1=format1.format(time);
		
		return time1;
	}
	
	//시스템 date 가져옴
	public String getTime(String date) {
		int chatTime=Integer.parseInt(date.substring(11, 13));
		String timeType="오전";
		if(chatTime >12) {
			timeType="오후";
			chatTime-=12;
		}
		String time=String.format("%s %s : %s", date.substring(0, 10),timeType,date.substring(11, 19));
		
		return time;
	}
	
	//bean을 map으로 변환하는 메서드
	 public Map<String, Object> beanToMap(Object o) throws NoSuchFieldException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
	        Map<String, Object> map = new HashMap<>();

	        Field[] fields = o.getClass().getDeclaredFields();

	        for(Field field : fields){
	            String filedName = field.getName();
	            String methodName = filedName.replace(filedName.substring(0, 1), filedName.substring(0, 1).toUpperCase());
	            map.put(filedName, o.getClass().getDeclaredMethod("get" + methodName).invoke(o));
	        }

	        return map;
	    }
	//chat db에서 seq 콜렉션에 저장되어있는 시퀀스 불러오기
	 public double getSequence(String seqName){
		  mdb=mc.getDatabase("chat");
		  MongoCollection<Document> coll = mdb.getCollection("seq");
		  
		  BasicDBObject queryObj = new BasicDBObject();
		  queryObj.append("_id", seqName);
		  
		  BasicDBObject updateObj = new BasicDBObject();
		  BasicDBObject incObj = new BasicDBObject();
		  incObj.put("seq", 1);
		  updateObj.put("$inc", incObj);
		  
		  Document result = coll.findOneAndUpdate(queryObj, updateObj);
		  
		  double seq = result.getDouble("seq");
		  return seq;
		 }
	//max 값 반환
		public double getMax(List<Map<String, Object>> list) {
			double max=(double) list.get(0).get("_id");
			for(Map<String, Object> map : list) {
				double value=(double)map.get("_id");
				if(max<value) max=value;
			}
			
			return max;
		}
}