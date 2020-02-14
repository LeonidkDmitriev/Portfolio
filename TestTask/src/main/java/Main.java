import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        WordIndex trie = new WordIndex();
        Scanner in = new Scanner(System.in);

        System.out.println("Введите путь и имя файла...");
        String filename = in.nextLine();
        trie.loadFile(filename);

        System.out.println("введите искомое слово, имеющиеся в файле слова представлены ниже");
        System.out.println("hello hell word world to be fine fine or fine hell");
        String searchWord = in.nextLine();

        System.out.println(trie.find(searchWord));
    }
}