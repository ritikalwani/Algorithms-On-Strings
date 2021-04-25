import java.io.*;
import java.util.*;

class Node
{
	public static final int Letters =  4;
	public static final int NA      = -1;
	public int next [];

	Node ()
	{
		next = new int [Letters];
		Arrays.fill (next, NA);
	}
}

public class TrieMatching implements Runnable {
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

	List <Integer> solve (String text, int n, List <String> patterns) {
		List <Integer> result = new ArrayList <Integer> ();

		// write your code here
		List<Map<Character, Integer>> Trie=buildTrie(patterns);
		for(int i=0;i<text.length();i++){
		    char c=text.charAt(i);
		    int index=i;
		    int curr=0;
		    while(true){
		        
		        
		        
		        if(Trie.get(curr).containsKey(c)){
		            curr=Trie.get(curr).get(c);
		            if(Trie.get(curr).isEmpty()){
		               result.add(i);
		               break;
		            }
		            index++;
		            if(index>=text.length()){
		                break;
		            }
		            c=text.charAt(index);
		        }
		        else{
		            break;
		        }
		    }
		}
		

		return result;
	}
	List<Map<Character, Integer>> buildTrie(List<String> patterns) {
        


        // write your code here
        List<Map<Character,Integer>> Trie=new ArrayList<>();
        Map<Character,Integer> root=new HashMap<>();
        Trie.add(root);
        for(String pattern:patterns){
            int curr=0;
            for(int i=0;i<pattern.length();i++){
                char c=pattern.charAt(i);
                
                if(!Trie.get(curr).isEmpty() && Trie.get(curr).containsKey(c)){
                    curr=Trie.get(curr).get(c);
                }
                else{
                    int t=Trie.size();
                    Trie.get(curr).put(c,t);
                    Map<Character,Integer> temp=new HashMap<>();
                    Trie.add(temp);
                    curr=t;
                }
                
                
            }
        }

        return Trie;
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
		new Thread (new TrieMatching ()).start ();
	}
}
