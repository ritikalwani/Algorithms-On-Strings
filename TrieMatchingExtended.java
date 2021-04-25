import java.io.*;
import java.util.*;

class Node
{
	public static final int Letters =  4;
	public static final int NA      = -1;
	public int next [];
	public boolean patternEnd;

	Node ()
	{
		next = new int [Letters];
		Arrays.fill (next, NA);
		patternEnd = false;
	}
	
	public boolean isPatternEnd(){
	    return patternEnd;
	}
}

public class TrieMatchingExtended implements Runnable {
	int letterToIndex (char letter)
	{
		switch (letter)
		{
			case 'A': return 0;
			case 'C': return 1;
			case 'G': return 2;
			case 'T': return 3;
			default: assert (false); return Node.NA;
		}
	}
	
	List<Node> patternToTrie(List<String> patterns){
	    List<Node> trie=new ArrayList<>();
	    trie.add(new Node());
	    for(String pattern:patterns){
	        Node curr=trie.get(0);
	        for(int i=0;i<pattern.length();i++){
	            char symbol=pattern.charAt(i);
	            int index=curr.next[letterToIndex(symbol)];
	            if(index!=Node.NA){
	                curr=trie.get(index);
	            }
	            else{
	                Node temp=new Node();
	                trie.add(temp);
	                curr.next[letterToIndex(symbol)]=trie.size()-1;
	                curr=temp;
	            }
	            if(i==pattern.length()-1){
	                curr.patternEnd=true;
	            }
	        }
	    }
	    return trie;
	}
	public int prefixTrieMatching(int rem, String text,List<Node> trie){
	    Character currentSymbol = text.charAt(0);
		Node currentNode = trie.get(0);
		int indexCurrentChar = 0;
		while (true) {
			if (currentNode.isPatternEnd()) {
				return rem;
			} 
			else if (currentNode.next[letterToIndex(currentSymbol)] != Node.NA) {
				currentNode = trie.get(currentNode.next[letterToIndex(currentSymbol)]);
				if (indexCurrentChar + 1 < text.length()) {
					currentSymbol = text.charAt(++indexCurrentChar);
				}
				else {
					if (currentNode.isPatternEnd()) {
						return rem;
					}
					break;
				}
			}
			else {
				break;
			}
		}
		return -1;
	}
	public List<Integer> solve(String text,int n,List<String> patterns){
	    List<Integer> result=new ArrayList<Integer>();
	    List<Node> trie=patternToTrie(patterns);
	    int count=0;
	    while(!text.isEmpty()){
	        int match=prefixTrieMatching(count,text,trie);
	        count++;
	        if(match!=-1){
	            result.add(match);
	        }
	        text=text.substring(1);
	    }
	    return result;
	}
	




	public void run () {
		try {
			BufferedReader in = new BufferedReader (new InputStreamReader (System.in));
			String text = in.readLine ();
		 	int n = Integer.parseInt (in.readLine ());
		 	List <String> patterns = new ArrayList <String> ();
			for (int i = 0; i < n; i++) {
				patterns.add (in.readLine ());
			}

			List <Integer> ans = solve (text, n, patterns);
            Collections.sort(ans);
			for (int j = 0; j < ans.size (); j++) {
				System.out.print ("" + ans.get (j));
				System.out.print (j + 1 < ans.size () ? " " : "\n");
			}
		}
		catch (Throwable e) {
			e.printStackTrace ();
			System.exit (1);
		}
	}

	public static void main (String [] args) {
		new Thread (new TrieMatchingExtended ()).start ();
	}
}
