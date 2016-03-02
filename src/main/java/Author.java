import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import org.sql2o.*;

public class Author {
  private int id;
  private String author;

  // CONSTRUCTOR //
  public Author(String author) {
    this.author = author;
  }

  // GETTERS //
  public String getAuthorName() {
    return author;
  }

  public int getId() {
    return id;
  }

  @Override
  public boolean equals(Object newAuthor){
    if(!(newAuthor instanceof Author)){
      return false;
    } else {
      Author book = (Author) newAuthor;
      return this.getAuthorName().equals(book.getAuthorName());
    }
  }

  // CREATE //
  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO authors (author) VALUES (:author)";
      this.id = (int) con.createQuery(sql, true)
      .addParameter("author", this.author)
      .executeUpdate()
      .getKey();
    }
  }

  // READ //
  public static List<Author> all() {
    String sql = "SELECT * FROM authors";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql)
      .executeAndFetch(Author.class);
    }
  }

  public static Author find(int id){
    String sql = "SELECT * FROM authors WHERE id=:id";
    try(Connection con = DB.sql2o.open()){
      return con.createQuery(sql)
      .addParameter("id", id)
      .executeAndFetchFirst(Author.class);
    }
  }

  public List<Book> getBooks(){
    String sql = "SELECT books.* FROM authors JOIN author_books ON (authors.id = author_books.author_id) JOIN books ON (author_books.book_id = books.id) WHERE authors.id = :author_id";
    try(Connection con = DB.sql2o.open()){
      return con.createQuery(sql)
      .addParameter("author_id", id)
      .executeAndFetch(Book.class);
    }
  }

  public void addBook(Book book) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO author_books (author_id, book_id) VALUES (:author_id, :book_id)";
      con.createQuery(sql)
        .addParameter("book_id", book.getId())
        .addParameter("author_id", this.getId())
        .executeUpdate();
    }
  }

  // DESTROY //
  public void delete() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM authors WHERE id = :id;";
      con.createQuery(sql)
        .addParameter("id", id)
        .executeUpdate();

      String author_booksQuery = "DELETE FROM author_books WHERE author_id = :authorId";
      con.createQuery(author_booksQuery)
        .addParameter("authorId", this.getId())
        .executeUpdate();
      }
    }
}
