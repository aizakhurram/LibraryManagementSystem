import javax.swing.*;
import javax.swing.GroupLayout.Group;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class LibraryManagement {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton readButton, addButton, ediButton, delButton;
    

    public LibraryManagement() {
        frame = new JFrame("Book Reader");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
         JPanel buttonPanel = new JPanel();
       addButton = new JButton("Add Item");
       delButton = new JButton("Delete Item");
       ediButton = new JButton("Edit Item");
       buttonPanel.add(addButton);
       buttonPanel.add(delButton);
       buttonPanel.add(ediButton);
       frame.add(buttonPanel, BorderLayout.SOUTH);
        readButton = new JButton("Read");
      
        tableModel = new DefaultTableModel(new Object[]{"Title", "Author", "Year", "Read"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBook();
            }
        });

       
        // readButton.addActionListener(new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent e) {
               
        //     }
        // });

       
        //  delButton.addActionListener(new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent e) {
               
        //     }
        // });
        
       
       
      
        frame.setSize(600, 400);
        frame.setVisible(true);



    }
    private void addBook() {
        JDialog addBook = new JDialog(frame, "Add Book");
        addBook.setLayout(new GridLayout(4, 2));

        JLabel title = new JLabel("Title:");
        JTextField titleF = new JTextField();

        JLabel year = new JLabel("Year:");
        JTextField yearF = new JTextField();

        JLabel author = new JLabel("Author:");
        JTextField authorF = new JTextField();

        JButton doneButton = new JButton("Done");
        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title = titleF.getText();
                String year = yearF.getText();
                String author = authorF.getText();
                tableModel.addRow(new Object[]{title, author, year, "Read"});
                addBook.dispose();
                saveBooks(title, year, author);
            }
        });

        addBook.add(title);
        addBook.add(titleF);
        addBook.add(year);
        addBook.add(yearF);
        addBook.add(author);
        addBook.add(authorF);
        addBook.add(doneButton);

        addBook.setSize(300, 150);
        addBook.setVisible(true);
    }
    private void loadBooks(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] bookInfo = line.split(",");
                if (bookInfo.length == 3) {
                    String title = bookInfo[0];
                    String author = bookInfo[1];
                    String year = bookInfo[2];
                    tableModel.addRow(new Object[]{title, author, year, "Read"});
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void saveBooks(String title, String year, String author) {
        try (FileWriter writer = new FileWriter("data.txt", true)) {
            writer.write("\n"+ title + "," + author + "," + year);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {     
                LibraryManagement app = new LibraryManagement();
                app.loadBooks("data.txt"); 
   
    }
}
