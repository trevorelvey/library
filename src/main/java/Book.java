import java.util.List;
import java.util.ArrayList;
import org.sql2o.*;

public class Book {
  private int id;
  private String title;

  // CONSTRUCTOR //
  public Book(String title) {
    this.title = title;
  }

  // GETTERS //
  public String getBookName() {
    return title;
  }

  public int getId() {
    return id;
  }

  @Override
  public boolean equals(Object newBook){
    if(!(newBook instanceof Book)){
      return false;
    } else {
      Book book = (Book) newBook;
      return this.getBookName().equals(book.getBookName());
    }
  }

  // CREATE //
  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO books (title) VALUES (:title)";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("title", this.title)
      .executeUpdate()
      .getKey();
    }
  }

  // READ //
  public static List<Book> all() {
    String sql = "SELECT * FROM books";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
      .executeAndFetch(Book.class);
    }
  }

  public static Book find(int id){
    String sql = "SELECT * FROM books WHERE id=:id";
    try(Connection con = DB.sql2o.open()){
      return con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Book.class);
    }
  }

    public List<Author> getAuthors() {
    String sql = "SELECT authors.* FROM books JOIN author_books ON (books.id = author_books.book_id) JOIN authors ON (author_books.author_id = authors.id) WHERE books.id = :book_id";
    try(Connection con = DB.sql2o.open()) {
        return con.createQuery(sql)
          .addParameter("book_id", id)
          .executeAndFetch(Author.class);
    }
  }

  public void addAuthor(Author author) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO author_books (author_id, book_id) VALUES (:author_id, :book_id)";
      con.createQuery(sql)
        .addParameter("author_id", author.getId())
        .addParameter("book_id", this.getId())
        .executeUpdate();
    }
  }

  // UPDATE //


  // DESTROY //

}
