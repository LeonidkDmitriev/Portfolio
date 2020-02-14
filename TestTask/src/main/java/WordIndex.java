import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class WordIndex {
    static class TrieNode {
        Map<Character, TrieNode> children = new TreeMap<Character, TrieNode>();
        boolean leaf;
        Set<Integer> indexWord = new LinkedHashSet<Integer>();
    }

    TrieNode root = new TrieNode();
    /* помещение исходных данных в префиксное дерево*/
    public void loadFile(String filename) {
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(filename), StandardCharsets.UTF_8))) {
            int i = 0;
            String s = new String();
            while ((s = in.readLine()) != null) {
                TrieNode v = root;
                for (char ch : s.toCharArray()) {
                    if (!v.children.containsKey(ch)) {
                        v.children.put(ch, new TrieNode());
                    }
                    v = v.children.get(ch);
                }
                v.leaf = true;             //признак что узел не промежуточный
                v.indexWord.add(i + 1);    //запоминаем индекс слова
                i++;                       // увеличиаемсчетчик индекса
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
    /* поиск заданных данных в префиксном дереве*/
    public Set<Integer> find(String searchWord) {
        TrieNode v = root;
        for (char ch : searchWord.toCharArray()) {
            if (!v.children.containsKey(ch))
                return null;                  // если сразу не встретили нужного ключа
            else
                v = v.children.get(ch);
        }
        if (!v.leaf)                         //проверяем поиск остановился на промежуточной вершине
            return null;                    //пример ищем слово "под", которого нет, но есть слово "подвал"
        return v.indexWord;
    }
}

