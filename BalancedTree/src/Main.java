import org.w3c.dom.Node;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int n=0; //количество сбалансированных
        for (int i = 1; i <=12 ; i++) {
            //создаем 12 деревьев
            Bst<Integer,Integer> tree = new Bst<>();
            while (tree.size() < 6) {
                //создем дерево глубиной 6
                tree.put((int) (Math.random() * 200 - 100), 1);
            }
            //проверим его сбалансированность
            System.out.println("дерево № "+i+"  размер = " + tree.size()+"  высота = " + tree.hight());
           //System.out.println(tree.isBalanced());
            if (tree.isBalanced()) n++;
        }
        System.out.println("процент сбалансированных " + n/12*100);
   }

}
