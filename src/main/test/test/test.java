package test;

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

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Indexes;

import beans.ChatBean;

public class test {
	
	//가. 클러이언트
		final private MongoClient mc=new MongoClient("localhost",27017);
		//나. 데이터베이스
		MongoDatabase mdb;
		
	public static void main(String[] args) throws NoSuchFieldException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
		test md=new test();	
		ChatBean cn=new ChatBean();
		cn.set_id(md.getSequence("chat_num"));
		cn.setChatContent("감사해요\n 잘있어요 >_<");
		cn.setFromId("test");
		cn.setToId("test1");
		cn.setChatTime(md.getTime());
		
		md.mongoInsert("chat", "chat", cn);
		List<Map<String, Object>> list=md.mongoSelect("chat", "chat");
		for(Map<String,Object> map: list) {
			map.replace("chatContent", md.changeContent((String) map.get("chatContent")));
			map.replace("chatTime", md.getTime((String)map.get("chatTime")));
		}
		System.out.println(list);
		
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
	//db 현재 날짜 반환 
		public String getTime() {
			SimpleDateFormat format1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date time=new Date();
			String time1=format1.format(time);
			
			return time1;
		}
	//userchat db에서 seq 콜렉션에 저장되어있는 시퀀스 불러오기
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
	//mongo insert
		public void mongoInsert(String db,String collection,Object bean) throws NoSuchFieldException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
			mdb=mc.getDatabase(db);
			MongoCollection<Document> coll=mdb.getCollection(collection);
			
			Map<String,Object> map=beanToMap(bean);
					
			Document doc=new Document(map);
			coll.insertOne(doc);
		}
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
	public String changeContent(String content) {
		String result=content.replaceAll(" ", "&nbsp")
				.replaceAll("<", "&lt").replaceAll(">", "&gt")
	            .replaceAll("\n", "<br>");
	            
	    return result;
	}
	public List<Map<String, Object>> mongoSelect(String db,String collection) {
		return mongoSelect(db,collection,new BasicDBObject());
	}
	//mongo select
		public List<Map<String, Object>> mongoSelect(String db,String collection,Bson x) {
			mdb=mc.getDatabase(db);
			MongoCollection<Document> coll=mdb.getCollection(collection);
			coll.createIndex(Indexes.ascending("_id"));
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			FindIterable<Document> docs=coll.find(x);
			Iterator<Document> it=docs.iterator();
			Document doc;
			
			while(it.hasNext()) {
				doc=it.next();
				String key=doc.keySet().toString();
				String keys[]=arrKeys(key);
				Map<String,Object> test=new HashMap<>();

				for(String a:keys) {
					test.put(a, doc.get(a));
				}
				
				list.add(test);
			}
			
			return list;
		}
		public String[] arrKeys(String key) {
			key=key.replace("[", "").replace("]", "").replace(" ", "");
			String keys[]=key.split(",");
			return keys;
		}
		
}
