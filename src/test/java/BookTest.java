import org.junit.*;
import static org.junit.Assert.*;
import org.junit.Rule;
import java.util.List;

public class BookTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_returnsAllInstancesOfBook_true() {
    Book firstBook = new Book("Cat's Cradle");
    Book secondBook = new Book("Cat's Cradle");
    firstBook.save();
    secondBook.save();
    assertTrue(Book.all().contains(firstBook));
    assertTrue(Book.all().contains(secondBook));
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Book newBook = new Book("Neko Catsume");
    newBook.save();
    assertTrue(Book.all().get(0).equals(newBook));
  }

  @Test
  public void find_findsBookById(){
    Book newBook = new Book("Beep Boop");
    newBook.save();
    Book saved = Book.find(newBook.getId());
    assertTrue(newBook.equals(saved));
  }

  @Test
  public void addAuthor_addsAuthorForBook() {
    Author newAuthor = new Author("Author Man");
    newAuthor.save();
    Book newBook = new Book("Shifty FiveFingers");
    newBook.save();
    newBook.addAuthor(newAuthor);
    Author savedAuthor = newBook.getAuthors().get(0);
    assertTrue(newAuthor.equals(savedAuthor));
  }

  @Test
  public void getAuthors_returnsAllAuthors_List() {
    Author newAuthor = new Author("Author Man");
    newAuthor.save();
    Book newBook = new Book("Shifty FiveFingers");
    newBook.save();
    newBook.addAuthor(newAuthor);
    List<Author> savedAuthor = newBook.getAuthors();
    assertEquals(savedAuthor.size(), 1);
  }
}
