package de.uni.koeln.se.bookstore.Controller;

import de.uni.koeln.se.bookstore.datamodel.Book;
import de.uni.koeln.se.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/bookStore")
@RestController
public class BookController {

    @Autowired
    BookService bookSer;
    @GetMapping
    public ResponseEntity<List<Book>> getAllbooks() {
        List<Book> books = new ArrayList<Book>();
        books = bookSer.findBooks();
        return new ResponseEntity<>(books,HttpStatus.OK);
    }
    @GetMapping("/oldest")
    public ResponseEntity<Book> getOldestBook() {
        List<Book> books = new ArrayList<Book>();
        books = bookSer.findBooks();
        Book B=new Book();
        int releaseYear=999999999;
        for(int i=0; i<books.size(); i++){
            if(books.get(i).getDateYear()<releaseYear){
                releaseYear=books.get(i).getDateYear();
                B=books.get(i);
            }
        }
        return new ResponseEntity<>(B,HttpStatus.OK);
    }
    @GetMapping("/latest")
    public ResponseEntity<Book> getLatestBook() {
        List<Book> books = new ArrayList<Book>();
        books = bookSer.findBooks();
        Book B=new Book();
        int releaseYear=-9999999;
        for(int i=0; i<books.size(); i++){
            if(books.get(i).getDateYear()>releaseYear){
                releaseYear=books.get(i).getDateYear();
                B=books.get(i);
            }
        }
        return new ResponseEntity<>(B,HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable int id){

        return new ResponseEntity<>(bookSer.fetchBook(id).get(), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book){
        bookSer.addBook(book);
        return new ResponseEntity<>(book,HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> removeBookById(@PathVariable int id){
        Book book= bookSer.fetchBook(id).get();

        if(bookSer.deleteBook(id)){
            return new ResponseEntity<>(book,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(book,HttpStatus.BAD_REQUEST);

        }
    }
}
