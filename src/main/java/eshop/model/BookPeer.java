package eshop.model;

import eshop.beans.Book;

import java.util.ArrayList;
import java.util.List;

public class BookPeer {
    public static ArrayList<Book> searchBooks(DataManager dataManager, String keyword) {
        String sql = "SELECT book_id, title, author, price FROM books WHERE title LIKE ? OR author LIKE ?";
        String value = "%" + keyword.trim() + "%";
        List<Book> books = GeneralPeer.generalQuery(dataManager, Book.class, sql, value, value);
        return (ArrayList<Book>) books;
    }

    public static ArrayList<Book> getBooksByCategory(DataManager dataManager, String categoryId) {
        String sql = "SELECT book_id, title, author, price FROM books WHERE category_id = ?";
        List<Book> books = GeneralPeer.generalQuery(dataManager, Book.class, sql, categoryId);
        return (ArrayList<Book>) books;
    }

    public static Book getBookById(DataManager dataManager, String bookID) {
        String sql = "SELECT book_id, title, author, price FROM books WHERE book_id = ?";
        List<Book> books = GeneralPeer.generalQuery(dataManager, Book.class, sql, bookID);
        return books.isEmpty() ? null : books.get(0);
    }
}

