import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Trie {
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

    List<Map<Character, Integer>> buildTrie(String[] patterns) {
        


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

    static public void main(String[] args) throws IOException {
        new Trie().run();
    }

    public void print(List<Map<Character, Integer>> trie) {
        for (int i = 0; i < trie.size(); ++i) {
            Map<Character, Integer> node = trie.get(i);
            for (Map.Entry<Character, Integer> entry : node.entrySet()) {
                System.out.println(i + "->" + entry.getValue() + ":" + entry.getKey());
            }
        }
    }

    public void run() throws IOException {
        FastScanner scanner = new FastScanner();
        int patternsCount = scanner.nextInt();
        String[] patterns = new String[patternsCount];
        for (int i = 0; i < patternsCount; ++i) {
            patterns[i] = scanner.next();
        }
        List<Map<Character, Integer>> trie = buildTrie(patterns);
        print(trie);
    }
}
