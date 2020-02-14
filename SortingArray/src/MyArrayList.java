public class MyArrayList<Item extends Comparable<Item>> {
    private Object[] list = new Object[1];
    private int size = 0;

    public int size() {
        return size;
    }

    //получить значение элемента массива
    public Item get(int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException();
        }
        return (Item) list[index];
    }

    //присвоить значение элементу массива
    public void set(int index, Item item) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException();
        }
        list[index] = item;
    }

    //вставить новый элемент массива и рассмотреть возможность увеличения
    // размерности массива в 2 раза
    public void insert(Item item) {
        if (size == list.length) {
            resize(2 * list.length);
        }
        list[size] = item;
        size++;
    }

    // освобождение массива от "хвоста" через временный массив
    private void resize(int capacity) {
        Object[] temp = new Object[capacity];
        for (int i = 0; i < size; i++) {
            temp[i] = list[i];
        }
        list = temp;
    }

    // проверка есть ли в массиве элемент со значением item
    public boolean find(Item item) {
        for (int i = 0; i < size; i++) {
            if (list[i].equals(item)) {
                return true;
            }
        }
        return false;
    }

    // удаляем заданный элемент массива со значением item
    // и сдвигом элементов следующих после него влево и возвращением истина
    // или возвращаем ложь при отсутствии элемента со значением item
    public boolean delete(Item item) {
      int i = 0;
        while (i < size && !list[i].equals(item)) {
            i++;
        }
        if (i == size) {
            return false;
        }
        for (int j = i; j < size - 1; j++) {
            list[j] = list[j + 1];
        }
        list[size - 1] = null;
        size--;
        if (size == list.length / 4 && size > 0) {
            resize(list.length / 2);
        }
        return true;
    }

    // все в строку
    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < size; i++) {
            s = s + list[i] + " ";
        }
        return s;
    }

    // сравнение двух значений любого типа между собой
    private boolean less(Item item1, Item item2) {
        return item1.compareTo(item2) < 0;
    }

    //поменяет местами элементы
    private void exch(int i, int j) {
        Object temp = list[i];
        list[i] = list[j];
        list[j] = temp;
    }

/***************СОРТИРОВКИ**********************/
// сортировка выбором
    public void selectionSort() {
        for (int i = 0; i < size - 1; i++) {
            int min = i;
            for (int j = i + 1; j < size; j++) {
                if (less((Item) list[j], (Item) list[min])) {
                    min = j;
                }
            }
            exch(i, min);
        }
    }

// сортировка вставкой
    public void insertionSort() {
        for (int i = 0; i < size; i++) {
            for (int j = i; j > 0; j--) {
                if (less((Item) list[j], (Item) list[j - 1])) {
                    exch(j, j - 1);
                } else {
                    break;
                }
            }
        }
    }
// и наконец метод пузырька!
    public void bubbleSort() {
        boolean flag = false; //признак а была ли перестановка
        while(!flag) {        // если перестановок не было то массив считается отсортирован
            flag = true;
            for (int i = 0; i < size-1; i++) {
                if(less((Item) list[i+1], (Item) list[i])){
                    flag = false;
                    exch( i+1,i);
                }
            }
        }
    }
}
