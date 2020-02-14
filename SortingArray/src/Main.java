public class Main {
    public static void main(String[] args) {
      long start, stop; //время исполнения начало, конец
      int size = 100000; // размерность массива, на котором будем тренироваться
      MyArrayList arr;

// отсортируем методом выбора
      arr= getArray(size); //для чистоты эксперимента каждый раз создаем новый несорт. массив
      System.out.println("\n Массив сортируется методом выбора...");
      start = System.nanoTime();
      arr.selectionSort();
      stop = System.nanoTime();
      for (int i = 0; i < size ; i++) {
        System.out.print(arr.get(i)+ " ");
      }
      System.out.println("\n Массив отсортирован за "+ (stop-start)/1000000000 + " сек");

// отсортируем методом вставки
      arr = getArray(size);
      System.out.println("\n Массив сортируется методом вставки...");
      start = System.nanoTime();
      arr.insertionSort();
      stop = System.nanoTime();
      for (int i = 0; i < size ; i++) {
        System.out.print(arr.get(i)+ " ");
      }
      System.out.println("\n Массив отсортирован за "+ (stop-start)/1000000000 + " сек");

//отсортируем методом пузырька
      arr = getArray(size);
      System.out.println("\n Массив сортируется методом пузырька...");
      start = System.nanoTime();
      arr.bubbleSort();
      stop = System.nanoTime();
      for (int i = 0; i < size ; i++) {
        System.out.print(arr.get(i)+ " ");
      }
      System.out.println("\n Массив отсортирован за "+ (stop-start)/1000000000 + " сек");

   }
// здесь будем создавать и выводить на экран новый несортированный массив
  private static MyArrayList getArray(int size) {
    MyArrayList arr1 = new MyArrayList();

    for (int i = 0; i < size ; i++) {
      arr1.insert((int) Math.round((Math.random() * 20) - 10));
    }
    System.out.println("Исходный массив");
    for (int i = 0; i < size ; i++) {
        System.out.print(arr1.get(i)+ " ");
      }
    return arr1;
  }
}
