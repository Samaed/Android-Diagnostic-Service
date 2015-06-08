package com.dhia.tp;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.List;

import java.util.Map;

public class Table {

public static Map<String, List<Integer>> map = new HashMap<String, List<Integer>>();
public static Map<String, List<String>> mapm = new HashMap<String, List<String>>();
public static void create(){
	// create map to store


	List<Integer> liswai = new ArrayList<Integer>();

	liswai.add(1);

	liswai.add(2);



	List<Integer> lisbodyt = new ArrayList<Integer>();

	lisbodyt.add(97);

	lisbodyt.add(98);



	List<Integer> lisoxy = new ArrayList<Integer>();

	lisoxy.add(95);

	lisoxy.add(100);


	List<Integer> blodp = new ArrayList<Integer>();

	blodp.add(60);

	blodp.add(89);


	List<Integer> blodg = new ArrayList<Integer>();

	blodg.add(5);

	blodg.add(8);


	List<Integer> pulserate = new ArrayList<Integer>();

	pulserate.add(66);

	pulserate.add(99);




	// put values into map

	
	map.put("waist hip ratio",liswai);
	map.put("body temperature ",lisbodyt);
	map.put("oxygenation of blood",lisoxy);
	map.put("blood pressure",blodp);
	map.put("blood glucose",blodg);
	map.put("pulse rate",pulserate);

	




	
}

public static void createm(){
	
	List<String> lisdia = new ArrayList<String>();
	lisdia.add("blood glucose");
	
	List<String> lisfla = new ArrayList<String>();
	lisfla.add("body temperature ");
	
	
	
	List<String> lisinfix = new ArrayList<String>();
	lisinfix.add("body temperature ");
	
	List<String> lisiHyperthyroïdie = new ArrayList<String>();
	lisiHyperthyroïdie.add("body temperature ");
	lisiHyperthyroïdie.add("oxygenation of blood");
	lisiHyperthyroïdie.add("pulse rate");
	
	
	 mapm.put("Diabete",lisdia );
	 mapm.put("inflammation",lisfla );
	 mapm.put("infection",lisinfix );
	 mapm.put("Hyperthyroïdie",lisiHyperthyroïdie);
	 
	

	
}


public static void main(String[] args) {
 

// create map to store
Map<String, List<String>> map = new HashMap<String, List<String>>();

 



List<Integer> liswai = new ArrayList<Integer>();

liswai.add(1);

liswai.add(2);



List<Integer> lisbodyt = new ArrayList<Integer>();

lisbodyt.add(97);

lisbodyt.add(98);



List<Integer> lisoxy = new ArrayList<Integer>();

lisoxy.add(95);

lisoxy.add(100);


List<Integer> blodp = new ArrayList<Integer>();

blodp.add(60);

blodp.add(89);


List<Integer> blodg = new ArrayList<Integer>();

blodg.add(5);

blodg.add(8);


List<Integer> pulserate = new ArrayList<Integer>();

pulserate.add(66);

pulserate.add(99);




// put values into map



// iterate and display values

System.out.println("Fetching Keys and corresponding [Multiple] Values n");

for (Map.Entry<String, List<String>> entry : map.entrySet()) {

String key = entry.getKey();

List<String> values = entry.getValue();

System.out.println("Key = " + key);

System.out.println("Values = " + values + "n");

}

}

}
