package com.actitime.tests;

import java.util.Scanner;

import org.testng.annotations.Test;

public class test {
//	@Test(groups= {"check"})
	public static void testing() {
		String s="Selenium";
		System.out.println("Enter the character: ");
		Scanner scn = new Scanner(System.in);
		String s1 = scn.nextLine();
		scn.close();
		
//		
		char c = s1.charAt(0);
		
		int counter=0;
		
		for(int x=0;x<s.length();x++) {
			if(s.charAt(x)==c)
				counter++;
		}
		
		System.out.println(counter);
	}
//	@Test
	public static void stringSplit() {
		Scanner scn = new Scanner(System.in);
		
		String input = scn.nextLine();
		
		String[] arr = input.split(" ");
		
		for(String s: arr) {
			System.out.println(s);
		}
	}
	
//	@Test
	public static void conversion() {
		int num = 5000;
		String s = String.valueOf(num);
//		System.out.println(1/0);
		System.out.println("value is "+s+" and is "+s+" instance of String: "+s instanceof String);
	}
//	@Test
	public static void sb() {
		StringBuilder sb = new StringBuilder("Selenium"); //insert, delete, update, reverse
		System.out.println(sb);
		sb.insert(sb.length(),"check");
		System.out.println(sb);
		sb.setCharAt(4, 't');
		System.out.println(sb);
		sb.delete(4, 5);
		System.out.println(sb);
		sb.insert(4, 'n');
		System.out.println(sb);
//		System.out.println("Replaced string is "+replaceString(sb,"replace",4));
		System.out.println(sb);
		sb.reverse();
		System.out.println(sb);
		
		
	}
}
