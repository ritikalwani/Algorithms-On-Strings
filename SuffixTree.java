package code;

import java.util.*;
import java.util.Map.Entry;
import java.io.*;
import java.util.zip.CheckedInputStream;

public class SuffixTree {
    class FastScanner {
        StringTokenizer tok = new StringTokenizer("");
        BufferedReader in;

        FastScanner() {
            in = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() throws IOException {
            while (!tok.hasMoreElements())
                tok = new StringTokenizer(in.readLine());
            return tok.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
    class Node{
    	int start;
    	int length;
    	
    	int suffixStart;
    	HashMap<Character,Integer> children;
    	public Node(int s,int l,int i) {
    		start=s;
    		length=l;
    		
    		children=new HashMap<>();
    		suffixStart=i;
    		
    	}
    	public Node() {
    		start=-1;
    		length=-1;
    		
    		
    		children=new HashMap<>();
    		suffixStart=-1;
    	}
    	
        
    }
    int letterToIndex (char letter)
	{
		switch (letter)
		{
			case 'A': return 0;
			case 'C': return 1;
			case 'G': return 2;
			case 'T': return 3;
			case '$': return 4;
			default: assert (false); return -1;
		}
	}

    // Build a suffix tree of the string text and return a list
    // with all of the labels of its edges (the corresponding 
    // substrings of the text) in any order.
    public List<String> computeSuffixTreeEdges(String text) {
        List<String> result=new ArrayList<>();
        // Implement this function yourself
        List<Node> suffixTree=new ArrayList<>();
        Node root=new Node();
        suffixTree.add(root);
        for(int i=0;i<text.length();i++) {
        	Node curr=suffixTree.get(0);
        	int index=i;
        	
        	
        	while(index<text.length() && curr.children.containsKey(text.charAt(index))) {
        		char symbol=text.charAt(index);
        		Node next=suffixTree.get(curr.children.get(symbol));
        		int k=next.start;
        		
        		while(true) {
        			if(k<next.start+next.length && text.charAt(index)==text.charAt(k)) {
        				k++;
        				index++;
        			}
        			else {
        				break;
        			}
        		}
        		if(k==next.start+next.length) curr=next;
        		else {
        			Node p=new Node(next.start,k-next.start,i);
        			Node q=new Node(k,next.length-k+next.start,i);
        			HashMap<Character,Integer> child=next.children;
        			q.children=child;
        			suffixTree.set(curr.children.get(symbol), p);
        			p.children.put(text.charAt(k), suffixTree.size());
        			suffixTree.add(q);
        			curr=p;
        		}
        		
        			
        		
        	}
        	
    			Node n=new Node(index,text.length()-index,i);
    			curr.children.put(text.charAt(index), suffixTree.size());
    			suffixTree.add(n);
    			
    		
        }
        result=Traversal(suffixTree,text);
        
        
        
        return result;
        
    }
	
	  public static List<String> Traversal(List<Node> suffixTree,String text){
	      List<String>  result=new ArrayList<>();
		  for(int i=1;i<suffixTree.size();i++) {
			  result.add(text.substring(suffixTree.get(i).start,suffixTree.get(i).start+suffixTree.get(i).length));
		  }
		  return result;
	  }
	 

    static public void main(String[] args) throws IOException {
        new SuffixTree().run();
    }

    public void print(List<String> x) {
        for (String a : x) {
            System.out.println(a);
        }
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        String text = scanner.next();
        List<String> edges = computeSuffixTreeEdges(text);
        print(edges);
    }
}
