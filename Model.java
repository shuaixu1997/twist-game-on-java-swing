package code;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;

public class Model {

	private HashSet<String> _words;
	private ArrayList<String> _longWords;
	public Model(HashSet<String> words, ArrayList<String> longWords) {
		_words = words;
		_longWords = longWords;
	}
	public Model(String file1){
		_words = new HashSet<String>();
		_longWords = new ArrayList<String>();
		FileReader file3 = new FileReader(file1);

		do
		{
			String x = file3.next();
			if (x.length() < 7) _words.add(x);
			else if (x.length() == 7){
				_longWords.add(x);
			}
			
		}
		while (file3.hasNext());
	}
	public ArrayList<Character> string2charList(String file4){
		ArrayList<Character> temp1 = new ArrayList<Character>();
		for (int i=0; i<file4.length();i++){
			temp1.add(file4.charAt(i));	
		}
		return temp1;
	}
	public boolean anagramOfLetterSubset(String file5, ArrayList<Character> temp2){
		int count=file5.length();
		for (int i=0; i<count;i++){
			Character realchar = file5.charAt(i);
			if (temp2.contains(realchar) == false) 
			return false;
			else
			{
				temp2.remove(realchar);
			}
		}
		
		temp2.clear();
		return true;
	}
public HashSet<String> possibleWords(String file6){
		
		HashSet<String> tempset = new HashSet<String>();
		ArrayList<Character> letters = new ArrayList<Character>();
		letters = string2charList(file6);
		String currentword;
		ArrayList<Character> temp1 = new ArrayList<Character>();
		temp1 = letters;
		//System.out.println(letters.size());
		//System.out.println(_words.size());
		//System.out.println(_longWords);
		Iterator<String> theIterator = _words.iterator();
		//System.out.println(theIterator);
		
		//System.out.println(letters);
		
		while (theIterator.hasNext())
		{
			currentword = theIterator.next();
			//letters = string2charList(file6);
			letters = string2charList(file6);
			temp1 = letters;
			//System.out.println(currentword);
			if (anagramOfLetterSubset(currentword,temp1))
			{	
				//System.out.println("111");
				tempset.add(currentword);
				//temp1 = letters;
			}
			
			//System.out.println(temp1.size());
			//System.out.println(letters.size());
			//System.out.println(currentword.length());
		}
		
		return tempset;
		
	}
	public ArrayList<Character> getList(String s1){
		ArrayList<Character> list = new ArrayList<Character>();
		Collections.shuffle(_longWords);
		String s =s1;
		for (int i=0;i<s.length();i++){
			list.add(s.charAt(i));
		}
		return list;
	}
	
	public String gettheword(){
		Collections.shuffle(_longWords);
		return _longWords.get(0);
	}
	

}
