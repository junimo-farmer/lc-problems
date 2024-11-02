// Online Java Compiler
// Use this editor to write, compile and run your Java code online

import java.util.*;
class Prefix {
    public static void main(String[] args) {
        Dictionary<String, Integer> database =  new Hashtable();
        database.put("AAPL",160);
        database.put("APPL",200);
        database.put("ANBN",506);
        database.put("DEL",123);
        database.put("ABNB",250);
        database.put("AB", 900);
        database.put("TRYN",400);
        database.put("DELHI",123);
        
        TrieNode root = new TrieNode();
        root.insert("AAPL",160);
        root.insert("ANBN",200);
        root.insert("ABNB",506);
        root.insert("DEL",123);
        
        String prefix = "";
        Solution s = new Solution();
    
        List<Entry> result2 = s.solve(prefix, root);
        List<Entry> result= s.solve(prefix, database);
        if(result.size() ==0) {
            System.out.println("No matching entries for DB!");
        } else { 
            System.out.println("From the DB!");
            for(Entry e: result){
                System.out.println(e);
            }
        }
        if(result2.size() == 0) {
            System.out.println("No matching entries for Trie");
            return;
        }
        System.out.println("From the Trie:");
        for(Entry e: result2) {
            e.key = prefix + e.key;
            System.out.println(e);
        }
    }
}

class Solution {
    public Solution() {
        //does nothing
    } 
    public static List<Entry> solve(String prefix, Dictionary<String, Integer> database) {
        List<Entry> result = new ArrayList<Entry>();
        Enumeration e = database.keys();
        while(e.hasMoreElements()) {
            String element = (String)e.nextElement();
            if(element.indexOf(prefix) == 0) {
                result.add(new Entry(element, database.get(element)));
            }
        }
        return result;
    }
    public static List<Entry> solve(String prefix, TrieNode database) {
        List<Entry> result = retrievePrefixes(prefix, database);
        return result;
    }
    private static List<Entry> retrievePrefixes(String prefix, TrieNode root) {
        TrieNode currentNode = root;
        String cpyPrefix = prefix;
        while(prefix.length() > 0) {
            //recurse down trienode
            int ind = prefix.charAt(0) - 'A';
            if(currentNode.children[ind]!=null) {
                prefix = prefix.substring(1);
                currentNode = currentNode.children[ind];
            } else {
                return new ArrayList<Entry>();
            }
        }
        return currentNode.retrieveAll();
    }
}

class Entry {
    public String key;
    public Integer value;
    public Entry(String key, Integer value) {
        this.key = key;
        this.value = value;
    }
    
    public String toString() {
        return ("[" + key + " " + value + "]");
    }
}

class TrieNode {
    public boolean wordEnd;
    public TrieNode[] children;
    public int value;
    public TrieNode() {
        this.wordEnd = false;
        this.children = new TrieNode[26];
        this.value = -1;
    }
    public TrieNode(int value) {
        this.wordEnd = false;
        this.children = new TrieNode[26];
        this.value = value;
    }
    public void insert(String entry, int value) {
        if(entry.length() > 0) {
            int ind = entry.charAt(0) - 'A';
            if(this.children[ind] == null) {
                this.children[ind] = new TrieNode();
            }
            this.children[ind].insert(entry.substring(1), value);
        }else {
            this.wordEnd = true;
            this.value = value;
        }
    }
    public boolean search(String str) {
        boolean result = false;
        if(str.length()>0) {
            int ind = str.charAt(0)-'A';
            if(children[ind] == null) {return false;}
            else result = children[ind].search(str.substring(1));
        } else {
            result = wordEnd;
        }
        
        return result;
    }
    
    public List<Entry> retrieveAll() {
        List<Entry> entries = new ArrayList<Entry>();
        retrieveEntry("", entries);
        return entries;
        
    }
    private void retrieveEntry(String previous, List<Entry> entries) {
        if(this.wordEnd) {
            entries.add(new Entry(previous, this.value));
        }
        for(int i =0; i<26; i ++) {
            if(this.children[i] != null) {
                this.children[i].retrieveEntry(previous + (char)(i+'A'), entries);
            }
        }
    }
}
