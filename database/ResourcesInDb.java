package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import courseResources.Book;
import courseResources.SummaryNotes;
import courseResources.Videos;

public class ResourcesInDb {

    public static void addBook(Book book) throws SQLException {
        String sql = "INSERT INTO books (title, description, course_name, book_url) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getDescription());
            stmt.setString(3, book.getCourseName());
            stmt.setString(4, book.getBookUrl());
            
            stmt.executeUpdate();
            System.out.println("Book added successfully to the database.");
        }
    }

    /**
     * Adds a Video to the database.
     * 
     * @param video the Video object to be added
     * @throws SQLException if a database access error occurs
     */
    public static void addVideo(Videos video) throws SQLException {
        String sql = "INSERT INTO videos (title, description, course_name, video_url, video_description) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, video.getTitle());
            stmt.setString(2, video.getDescription());
            stmt.setString(3, video.getCourseName());
            stmt.setString(4, video.getVideoUrl());
            stmt.setString(5, video.getVideoDescription());
            
            stmt.executeUpdate();
            System.out.println("Video added successfully to the database.");
        }
    }

    /**
     * Adds Summary Notes to the database.
     * 
     * @param notes the SummaryNotes object to be added
     * @throws SQLException if a database access error occurs
     */
    public static void addSummaryNotes(SummaryNotes notes) throws SQLException {
        String sql = "INSERT INTO summary_notes (title, description, course_name, content) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, notes.getTitle());
            stmt.setString(2, notes.getDescription());
            stmt.setString(3, notes.getCourseName());
            stmt.setString(4, notes.getContent());
            
            stmt.executeUpdate();
            System.out.println("Summary Notes added successfully to the database.");
        }
    }

    /**
     * Retrieves all Books from the database.
     * 
     * @return a list of Book objects
     * @throws SQLException if a database access error occurs
     */
    public static List<Book> getAllBooks() throws SQLException {
        String sql = "SELECT * FROM books";
        List<Book> books = new ArrayList<>();

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Book book = new Book(
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getString("course_name"),
                    rs.getString("book_url")
                );
                books.add(book);
            }
        }
        return books;
    }

    /**
     * Retrieves all Videos from the database.
     * 
     * @return a list of Videos objects
     * @throws SQLException if a database access error occurs
     */
    public static List<Videos> getAllVideos() throws SQLException {
        String sql = "SELECT * FROM videos";
        List<Videos> videos = new ArrayList<>();

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Videos video = new Videos(
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getString("course_name"),
                    rs.getString("video_url"),
                    rs.getString("video_description")
                );
                videos.add(video);
            }
        }
        return videos;
    }

    /**
     * Retrieves all Summary Notes from the database.
     * 
     * @return a list of SummaryNotes objects
     * @throws SQLException if a database access error occurs
     */
    public static List<SummaryNotes> getAllSummaryNotes() throws SQLException {
        String sql = "SELECT * FROM summary_notes";
        List<SummaryNotes> notesList = new ArrayList<>();

        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                SummaryNotes notes = new SummaryNotes(
                    rs.getString("title"),
                    rs.getString("description"),
                    rs.getString("course_name"),
                    rs.getString("content")
                );
                notesList.add(notes);
            }
        }
        return notesList;
    }
    
}
