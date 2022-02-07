package MyImplementations;

//Muhammad Zaid; MXZ190024
public class MyLinkedList<E> implements MyList<E> {
  private Node<E> head, tail;
  private int size = 0; // Number of elements in the list

  /** Create an empty list */
  public MyLinkedList() {
  }

  /** Create a list from an array of objects */
  public MyLinkedList(E[] objects) {
    // TODO : Implement this method
    for (int i = 0; i < objects.length; i++) {
      add(objects[i]);
    }

  }

  /** Return the head element in the list */
  public E getFirst() {
    // TODO : Implement this method
    if (size != 0) {
      return head.element;
    }

    return null;
  }

  /** Return the last element in the list */
  public E getLast() {
    // TODO : Implement this method
    if (size != 0) {
      return tail.element;
    }
    return null;
  }

  /** Add an element to the beginning of the list */
  public void addFirst(E e) {
    // TODO : Implement this method
    Node<E> nextNode = new Node<>(e);
    nextNode.next = head;
    head = nextNode;
    size++;
  }

  /** Add an element to the end of the list */
  public void addLast(E e) {
    // TODO : Implement this method
    Node<E> newNode = new Node<>(e); // Create a new for element e

    if (tail == null) {
      head = tail = newNode; // The new node is the only node in list
    } else {
      tail.next = newNode; // Link the new with the last node
      tail = newNode; // tail now points to the last node
    }

    size++; // Increase size
  }

  @Override /**
             * Add a new element at the specified index in this list. The index of the head
             * element is 0
             */
  public void add(int index, E e) {
    // TODO : Implement this method
    if (index >= size) {
      addLast(e);
    } else if (index == 0) {
      addFirst(e);
    } else {
      Node<E> currentNode = head;

      for (int i = 1; i < index; i++) {
        currentNode = currentNode.next;
      }
      Node<E> tempNode = currentNode.next;
      Node<E> addNode = new Node<>(e);
      currentNode.next = addNode;
      addNode.next = tempNode;
      size++;

    }
  }

  /**
   * Remove the head node and return the object that is contained in the removed
   * node.
   */
  public E removeFirst() {
    // TODO : Implement this method
    if (size != 0) {
      E tempVar = head.element;
      head = head.next;
      if (head == null) {
        tail = null;
      }
      size--;
      return tempVar;
    } else {
      return null;
    }
  }

  /**
   * Remove the last node and return the object that is contained in the removed
   * node.
   */
  public E removeLast() {
    // TODO : Implement this method
    if (size == 0) {
      return null;
    } else if (size == 1) {
      E tempVar = head.element;
      head = null;
      tail = null;
      size--;

      return tempVar;
    } else {
      Node<E> currentNode = head;
      E tempVar = tail.element;

      for (int i = 0; i < size - 2; i++) {
        currentNode = currentNode.next;
      }
      currentNode.next = null;
      tail = currentNode;
      size--;
      return tempVar;
    }
  }

  @Override /**
             * Remove the element at the specified position in this list. Return the element
             * that was removed from the list.
             */
  public E remove(int index) {
    // TODO : Implement this method
    if(index < 0 || index > size - 1) {
      return null;
    }
    else if (index == 0) {
      return removeFirst();
    } else if (index == size - 1) {
      return removeLast();
    } 
    else {
      Node<E> currentNode = head;
      E tempVar;

      for (int i = 1; i < index; i++) {
        currentNode = currentNode.next;
      }
      tempVar = currentNode.element;
      currentNode.next = (currentNode.next).next;

      return tempVar;
    }

  }

  @Override /** Override toString() to return elements in the list */
  public String toString() {
    StringBuilder result = new StringBuilder("[");

    Node<E> current = head;
    for (int i = 0; i < size; i++) {
      result.append(current.element);
      current = current.next;
      if (current != null) {
        result.append(", "); // Separate two elements with a comma
      } else {
        result.append("]"); // Insert the closing ] in the string
      }
    }

    return result.toString();
  }

  @Override /** Clear the list */
  public void clear() {
    size = 0;
    head = tail = null;
  }

  @Override /** Return true if this list contains the element e */
  public boolean contains(Object e) {
    // Left as an exercise
    // TODO : Implement this method
    if (head != null && tail != null) {
      Node<E> currentNode = head;
      for (int i = 0; i < size - 1; i++) {
        if (e.equals(currentNode.element)) {
          return true;
        }
        currentNode = currentNode.next;
      }
    }

    return false;
  }

  @Override /** Return the element at the specified index */
  public E get(int index) {
    // Left as an exercise
    // TODO : Implement this method

    if (index > 0 && index < size - 1) {
      Node<E> currentNode = head;
      for (int i = 1; i < index; i++) {
        currentNode = currentNode.next;
      }
      return (currentNode.next).element;
    } else if (index == 0) {
      return head.element;

    } else if (index == size - 1) {
      return tail.element;
    } else {
      return null;
    }
  }

  @Override /**
             * Return the index of the head matching element in this list. Return -1 if no
             * match.
             */
  public int indexOf(Object e) {
    // Left as an exercise
    // TODO : Implement this method

    if (head != null && tail != null) {
      Node<E> currentNode = head;
      for (int i = 0; i < size - 1; i++) {
        if (e.equals(currentNode.element)) {
          return i;
        }
        currentNode = currentNode.next;
      }
    }

    return -1;
  }

  @Override /**
             * Return the index of the last matching element in this list. Return -1 if no
             * match.
             */
  public int lastIndexOf(E e) {
    // Left as an exercise
    // TODO : Implement this method
    int tempIndex = -1;
    if (head != null && tail != null) {
      Node<E> currentNode = head;
      for (int i = 0; i < size - 1; i++) {
        if (e.equals(currentNode.element)) {
          if (tempIndex < i) {
            tempIndex = i;
          }
        }
        currentNode = currentNode.next;
      }
    }

    return tempIndex;
  }

  @Override /**
             * Replace the element at the specified position in this list with the specified
             * element.
             */
  public E set(int index, E e) {
    // Left as an exercise
    // TODO : Implement this method
    if (index == 0) {
      head.element = e;
    } else if (index == size - 1) {
      tail.element = e;
    } else {
      Node<E> currentNode = head;
      for (int i = 1; i < index; i++) {
        currentNode = currentNode.next;
      }
      (currentNode.next).element = e;
    }
    return null;
  }

  @Override /** Override iterator() defined in Iterable */
  public java.util.Iterator<E> iterator() {
    return new LinkedListIterator();
  }

  private class LinkedListIterator implements java.util.Iterator<E> {
    private Node<E> current = head; // Current index

    @Override
    public boolean hasNext() {
      return (current != null);
    }

    @Override
    public E next() {
      E e = current.element;
      current = current.next;
      return e;
    }

    @Override
    public void remove() {
      // Left as an exercise
      // TODO : Implement this method

      Node<E> currentNode = head;
      if (head == null) {
        throw new IllegalAccessError();
      } else if (currentNode == null) {
        head = tail = null;
      } else {
        for (int i = 0; i < size - 3; i++) {
          currentNode = currentNode.next;
        }
        currentNode.next = (currentNode.next).next;
        size--;
      }

    }
  }

  private static class Node<E> {
    E element;
    Node<E> next;

    public Node(E element) {
      this.element = element;
    }
  }

  @Override /** Return the number of elements in this list */
  public int size() {
    return size;
  }
}
