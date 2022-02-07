package MyImplementations;

import java.util.EmptyStackException;

//Muhammad Zaid; MXZ190024
public class MyArrayListStack<T> implements MyStack<T> {
  private MyArrayList<T> list = new MyArrayList<T>();

  public T pop() {
    // TODO : Implement this method
    T t = null;
    if(list.isEmpty() == false){
      t = list.get(list.size() - 1);
      list.remove(list.size() - 1);
      return t;
    }
    else{
      throw new IndexOutOfBoundsException("The Stack is empty");
    }

 
  }

  public void push(T element) {
    // TODO : Implement this method
    list.add(element);
  }

  public int size() {
    // TODO : Implement this method
    return list.size();
  }
  public T peek() {
    // DONE : Implement this method
    if (list.size() == 0) {
      throw new EmptyStackException();
    } else {
      return list.get(list.size() - 1);
    }
  }
}