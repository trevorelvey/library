import org.junit.*;
import java.time.LocalDate;
import static org.junit.Assert.*;
import org.junit.Rule;
import java.util.ArrayList;
import java.util.List;

public class AuthorTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void all_returnsAllInstancesOfAuthor_true() {
    Author firstAuthor = new Author("Author Guy");
    Author secondAuthor = new Author("Author Guy");
    firstAuthor.save();
    secondAuthor.save();
    assertTrue(Author.all().contains(firstAuthor));
    assertTrue(Author.all().contains(secondAuthor));
  }

  @Test
  public void save_savesIntoDatabase_true() {
    Author newAuthor = new Author("Mr Author");
    newAuthor.save();
    assertTrue(Author.all().get(0).equals(newAuthor));
  }

  @Test
  public void find_findsAuthorById(){
    Author newAuthor = new Author("Bill Clinton");
    newAuthor.save();
    Author saved = Author.find(newAuthor.getId());
    assertTrue(newAuthor.equals(saved));
  }

  @Test
  public void addBook_addsBookForAuthor() {
    Author newAuthor = new Author("Author Man");
    newAuthor.save();
    Book newBook = new Book("Shifty FiveFingers");
    newBook.save();
    newAuthor.addBook(newBook);
    Book savedBook = newAuthor.getBooks().get(0);
    assertTrue(newBook.equals(savedBook));
  }

  @Test
  public void getBooks_returnsAllBooks_List() {
    Author newAuthor = new Author("Author Man");
    newAuthor.save();
    Book newBook = new Book("Shifty FiveFingers");
    newBook.save();
    newAuthor.addBook(newBook);
    List<Book> savedBook = newAuthor.getBooks();
    assertEquals(savedBook.size(), 1);
  }

  @Test
   public void delete_deleteDeletesAuthor() {
     Author myAuthor = new Author("Shifty FiveFingers");
     myAuthor.save();
     myAuthor.delete();
     assertEquals(Author.all().size(), 0);
   }

   

}
