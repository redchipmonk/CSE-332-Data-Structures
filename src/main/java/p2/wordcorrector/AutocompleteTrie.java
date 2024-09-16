package p2.wordcorrector;

import cse332.datastructures.containers.Item;
import cse332.types.AlphabeticString;
import datastructures.dictionaries.HashTrieMap;

import java.util.AbstractMap;
import java.util.Map;

public class AutocompleteTrie extends HashTrieMap<Character, AlphabeticString, Integer> {

    public AutocompleteTrie() {
        super(AlphabeticString.class);
    }

    public String autocomplete(String key) {
        @SuppressWarnings("unchecked")
        HashTrieNode current = (HashTrieNode) this.root;
        for (Character item : key.toCharArray()) {
            if (current.pointers.find(item) == null) {
                return null;
            }
            else {
                current = current.pointers.find(item);
            }
        }

        StringBuilder result = new StringBuilder(key);

        while (current.pointers.size() == 1) {
            if (current.value != null) {
                return null;
            }
            Item<Character, HashTrieMap<Character, AlphabeticString, Integer>.HashTrieNode> temp =
                    current.pointers.iterator().next();
            result.append(temp.key);
            current = temp.value;
        }

        if (current.pointers.size() != 0) {
            return result.toString();
        }
        return result.toString();
    }
}