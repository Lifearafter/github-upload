package MyImplementations;

public interface MyQueue<E> {
  public void enqueue(E e);
  public E dequeue();
  public int getSize();
}
