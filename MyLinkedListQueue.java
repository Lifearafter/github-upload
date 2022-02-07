package MyImplementations;

//Muhammad Zaid; MXZ190024
public class MyLinkedListQueue<E> {
  private MyLinkedList<E> list 
    = new MyLinkedList<E>();

  public void enqueue(E e) {
    // TODO : Implement this method
    list.addLast(e);

  }

  public E dequeue() {
    // TODO : Implement this method
    return list.removeFirst();
  }

  public int getSize() {
    return list.size();
  }

  @Override
  public String toString() {
    return "Queue: " + list.toString();
  }
}
