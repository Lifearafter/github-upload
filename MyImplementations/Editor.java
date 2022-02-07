package MyImplementations;

import java.util.*;
import java.io.*;

import MyImplementations.MyArrayList;
import MyImplementations.MyArrayListStack;

/**Name: Muhammad Zaid
 * Class: CS2336.001
 * UTD ID: 2021535544
 * Date: 11/14/2021
*/
public class Editor {
  /** cursor row position */
  int row;
  /** cursor column position */
  int col;
  /** the text of the editor */
  MyArrayList<String> text = new MyArrayList<>();;
  /** the undo stack */
  MyArrayListStack<EditorAction> undoStack = new MyArrayListStack<EditorAction>();
  /** the redo stack */
  MyArrayListStack<EditorAction> redoStack = new MyArrayListStack<EditorAction>();
  // To hold the current file open
  String fileName;

  class EditorAction {
    /** the row of the action */
    int row;
    /** the column of the action */
    int col;
    /** the text of the action */
    String text;
    /** the action type */
    ActionType type;
  }

  enum ActionType {
    /** insert action */
    INSERT,
    /** delete action */
    DELETE,
    /** replace action */
    REPLACE
  }

  /**
   * Constructor
   */
  Editor(String fileName) {
    open(fileName);
  }

  /**
   * Open the file with the given name and read the contents into the editor.
   */
  void open(String fileName) {
    // read the file into the text
    // set the cursor to the beginning of the file

    // Get the absolute file path to the given file
    String filePath = new File(fileName).getAbsolutePath();

    // Try block to catch no file exception
    try {
      // Read into ArrayList the contents of the file
      Scanner s = new Scanner(new File(filePath));
      while (s.hasNext()) {
        text.add(s.nextLine());
      }
      s.close();
    }

    // Catch block to handle if exceptions occurs
    catch (FileNotFoundException e) {
      // Print the exception
      System.out.println("File not found.");
      e.printStackTrace(System.out);
    }

    // Set row and col to initial positions
    row = 0;
    col = 0;

    // Store fileName parameter in object variable
    this.fileName = fileName;

  }

  /**
   * Save the current text to currently open file.
   */
  void save() {
    // Try block to catch any IOExceptions
    try {

      // Use already saved fileName to find the absolute path of the file
      String filePath = new File(this.fileName).getAbsolutePath();

      // Write to the file
      BufferedWriter writerStream = new BufferedWriter(new FileWriter(filePath));
      for (String s : text) {
        writerStream.write(s + "\n");
      }
      writerStream.close();
    }

    // Catch block to handle if exceptions occurs
    catch (IOException e) {
      // Print the exception
      e.printStackTrace(System.out);
    }
  }

  /**
   * Save the current text to the given file.
   */
  void saveAs(String fileName) {
    // Try block to catch any IO Exceptions
    try {
      // Find absolute path of the file
      String filePath = new File(fileName).getAbsolutePath();

      // Write to the file
      BufferedWriter writerStream = new BufferedWriter(new FileWriter(filePath));
      for (String s : text) {
        writerStream.write(s + "\n");
      }
      writerStream.close();
    }

    // Catch block to handle if exceptions occurs
    catch (IOException e) {
      // Print the exception
      e.printStackTrace(System.out);
    }

    this.fileName = fileName;

  }

  /**
   * Undo the last action.
   */
  void undo() {
    // Pop the last element in the undoStack and store that element in undoAction
    EditorAction undoAction = undoStack.pop();
    // Move cursor to last element stored cursor position
    moveCursor(undoAction.row, undoAction.col);

    // Check which type of Action the last element took
    if (undoAction.type == ActionType.INSERT) {
      delete(undoAction.text.length(), true);
    } else if (undoAction.type == ActionType.DELETE) {
      insert(undoAction.text, true);
    } else if (undoAction.type == ActionType.REPLACE) {
      undo();
      undo();
    }

    // Push undoAction to the redoStack
    redoStack.push(undoAction);
  }

  /**
   * Redo the last undone action.
   */
  void redo() {
    // Pop the last element in the redoStack and store that element in redoAction
    EditorAction redoAction = redoStack.pop();
    // Move cursor to last element stored cursor position
    moveCursor(redoAction.row, redoAction.col);

    // Check which type of Action the last element took
    if (redoAction.type == ActionType.INSERT) {
      insert(redoAction.text, false);
    } else if (redoAction.type == ActionType.DELETE) {
      delete(redoAction.text.length(), false);
    } else if (redoAction.type == ActionType.REPLACE) {
      redo();
      redo();
    }

    // Push redoAction to the undoStack
    undoStack.push(redoAction);

  }

  /**
   * Insert the given string at the current cursor position. The cursor position
   * is updated to point to the end of the inserted string.
   */
  void insert(String s) {
    // Intiliaze string variable to hold the line
    String line = text.get(row);
    // Add a string to row and col position of the cursor
    text.set(row, line.substring(0, col) + s + line.substring(col));

    // Declare and initialize EditorAction object
    EditorAction insertAction = new EditorAction();
    insertAction.col = col;
    insertAction.row = row;
    insertAction.text = s;
    insertAction.type = ActionType.INSERT;

    // Push EditorAction object to undoStack;
    undoStack.push(insertAction);
    // Move cursor to new position
    moveCursor(row, col + s.length());

  }
  
  /**
   * Overloaded method insert only to be used by undo and redo methods
   */
  private void insert(String s, boolean undoFunc) {
    
    String line = text.get(row);
    text.set(row, line.substring(0, col) + s + line.substring(col));

    moveCursor(row, col + s.length());

  }

  /**
   * Delete n characters at the current cursor position. The cursor position is
   * updated to point to the end of the deleted string.
   */
  void delete(int n) {
    //Initialize string variable to hold a line of text
    String line = text.get(row);
    //Initialize string variable to hold the to be deleted string
    String deleted = line.substring(col, col + n);
    //Delete someSubstring from the ArrayList
    text.set(row, line.substring(0, col) + line.substring(col + n));

    //Declare and Initialize EditorAction object to push it unto the undoStack
    EditorAction deleteAction = new EditorAction();
    deleteAction.col = col;
    deleteAction.row = row;
    deleteAction.text = deleted;
    deleteAction.type = ActionType.DELETE;
    undoStack.push(deleteAction);
  }

  /**
   * Overloaded method delete only to be used by undo and redo methods
   */
  private void delete(int n, boolean undoFunc) {
    String line = text.get(row);
    text.set(row, line.substring(0, col) + line.substring(col + n));
  }

  /**
   * Replace the n characters at the current cursor position with the given
   * string. The cursor position is updated to point to the end of the replaced
   * string.
   */
  void replace(int n, String s) {
    //Call delete to delete string, and call insert to insert new string
    delete(n);
    insert(s);

    //Declare and Initialize EditorAction object to push it unto the undoStack
    EditorAction replaceAction = new EditorAction();
    replaceAction.col = col;
    replaceAction.row = row;
    replaceAction.type = ActionType.REPLACE;
    undoStack.push(replaceAction);
  }

  /**
   * Find the first instance of given string in the editor and set the cursor to
   * that position.
   */
  int[] find(String s) {
    //Simple for to traverse the ArrayList to find a string
    for (int i = 0; i < text.size(); i++) {
      if (text.get(i).indexOf(s) >= 0) {
        row = i;
        col = text.get(i).indexOf(s);
        return new int[] { i, text.get(i).indexOf(s) };
      }
    }
    return null;
  }

  /**
   * Move the cursor to the given position.
   */
  void moveCursor(int row, int col) {
    // Set object variable row and col to fed in method parameters row and col
    this.row = row;
    this.col = col;
  }

  /**
   * Return the current cursor position.
   */
  int[] getCursor() {
    return new int[] { row, col };
  }

  String getText(int row) {
    return text.get(row);
  }

  String getText(int row, int col) {
    return text.get(row).substring(col);
  }

  String getText(int row, int col, int n) {
    return text.get(row).substring(col, col + n);
  }

}