

import java.io.*;
import java.math.*;
import java.util.*;



public class NonSharedSubstring implements Runnable {
	String solve (String p, String q) {
		String text=p+"#"+q+"$";
		List<String> result=computeSuffixTreeEdges(text,p,q);
		
		/* for(String s:result) { System.out.println(s); } */
		Collections.sort(result,(a,b)->(a.length()-b.length()));
		return result.get(0);
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
	public List<String> computeSuffixTreeEdges(String text,String p1,String q1) {
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
        			Node p=new Node(next.start,k-next.start,next.suffixStart);
        			Node q=new Node(k,next.length-k+next.start,next.suffixStart);
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
        result=shortestNonShared(suffixTree,text,p1,q1);
        
        
        
        return result;
        
    }
	public static List<String> Traversal(List<Node> suffixTree,String text){
	      List<String>  result=new ArrayList<>();
		  for(int i=1;i<suffixTree.size();i++) {
			  result.add(text.substring(suffixTree.get(i).start,suffixTree.get(i).start+suffixTree.get(i).length));
			  Node n=suffixTree.get(i);
			  String sub=text.substring(n.start,n.start+n.length);
			  
		  }
		  return result;
	  }
	 public static List<String> shortestNonShared(List<Node> suffixTree, String text,String text1,String text2) {
		 Node root=suffixTree.get(0);
		 Queue<Integer> queue=new LinkedList<>();
		 queue.add(0);
		 List<String> ans=new ArrayList<>();
		 while(!queue.isEmpty()) {
			 Node curr=suffixTree.get(queue.poll());
			 if(curr.equals(root)) {
				 for(int i:curr.children.values()) {
					 queue.add(i);
				 }
			 }
			 else {
				 for(int i:curr.children.values()) {
					 queue.add(i);
				 }
				 String sub=text.substring(curr.start,curr.start+curr.length);
				 
				  if(sub.indexOf('#')>=0 && sub.indexOf('$')>0){
					  //System.out.println(sub);
					  String path="";
					  boolean found=false;
					  //System.out.println(curr.suffixStart);
					  //System.out.println(curr.start);
					  //System.out.println(curr.length);
					  if(curr.start>curr.suffixStart) {
						  path=text.substring(curr.suffixStart,curr.start);
						  //System.out.println(curr.suffixStart);
						  if(text2.indexOf(path)<0) {
							  ans.add(path);
							  found=true;
						  }
					  }
					  if(found==false && curr.children.isEmpty()) {
						  if(sub.charAt(0)=='#' && !path.equals("") && text2.indexOf(path)<0) {
							  ans.add(path);
						  }
						  else if(sub.charAt(0)!='#'){
							  path=path+sub.charAt(0);
							  //System.out.println(path);
							  ans.add(path);
						  }
						  
					  }
					  
					  
					 
				  }
				 
			 }
		 }
		 
		 
		 
		 return ans;
		 
	 }

	public void run () {
		try {
			BufferedReader in = new BufferedReader (new InputStreamReader (System.in));
			String p = in.readLine ();
			String q = in.readLine ();

			String ans = solve (p, q);

			System.out.println (ans);
		}
		catch (Throwable e) {
			e.printStackTrace ();
			System.exit (1);
		}
	}

	public static void main (String [] args) {
		new Thread (new NonSharedSubstring ()).start ();
	}
}
