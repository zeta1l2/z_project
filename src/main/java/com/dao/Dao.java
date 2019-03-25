package com.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.output.FileWriterWithEncoding;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

@Component
public class Dao {
		// 첨부 파일 추가
		public Map<String, String> upload_file(MultipartFile f,String path) {
			//정보출력
			System.out.printf("파일이름 : %s\n",f.getOriginalFilename());
			// 초기
			UUID uid=UUID.randomUUID(); //랜덤아이디 생성
			String oname=f.getOriginalFilename();
			String fname=uid.toString() + "_" + oname; //유일한이름
			System.out.printf("fname : %s\n",fname);
			//리턴 맵
			Map<String,String> finfos= new HashMap<String, String>();
				finfos.put("oname",oname);
				finfos.put("fname",fname);
			//파일처리
			File file=new File(path, fname);
			try {
				FileCopyUtils.copy(f.getBytes(), file);
				return finfos;
			} catch (Exception e) {
				System.out.println("첨부파일 추가 예외 :"+e.getMessage());
				return null;
			}
		}
		//첨부 파일 삭제
		public boolean delete_file(String fname,String path) {
			File file=new File(path,fname);
			return file.delete();
		}
		//텍스트 파일 내용 불러오기
		public String get_text(String fname,String path) {
			String str="";
			try {
			File file=new File(path, fname);
			FileReader fr;
			fr = new FileReader(file);
			BufferedReader reader = new BufferedReader(fr);
			
			String line=null;
			
			while((line=reader.readLine())!=null) {
				str+=line+"<br>";
			}
			System.out.println(str);
			reader.close();
			}catch(IOException e) {
				System.out.println("텍스트 파일 불러오기 에러 : "+e.getStackTrace());
			}
			return str;
		}
		//텍스트 파일 작성하기
		public String set_text(String fname,String path,String ct) {
			UUID uid=UUID.randomUUID(); //랜덤아이디 생성
			fname=uid.toString() + "_" + fname; //유일한이름
			
			File file=new File(path, fname);
			System.out.println("dao ct : "+ct);
			try {
			FileWriterWithEncoding fw = new FileWriterWithEncoding(file,"utf-8");
			BufferedWriter out= new BufferedWriter(fw);
			out.write(ct);
			out.newLine();
			out.close();
			return fname;
			}catch(IOException e) {
			System.out.println("파일 작성 에러 : " + e.getStackTrace());	
			}
			return "0";
		}
		//문자열 배열로 변환하는 메서드
		public String[] str_change(String str) {
			String []arr=str.split(",");
			return arr;
		}
}