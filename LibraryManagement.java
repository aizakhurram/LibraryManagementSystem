import javax.swing.*;
import javax.swing.GroupLayout.Group;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
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
        readButton = new JButton("Read");
      
        tableModel = new DefaultTableModel(new Object[]{"Title", "Year", "Author", readButton}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

       
        readButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
            }
        });
     

       
       JPanel buttonPanel = new JPanel();
       addButton = new JButton("Add Item");
       delButton = new JButton("Delete Item");
       ediButton = new JButton("Edit Item");
       buttonPanel.add(addButton);
       buttonPanel.add(delButton);
       buttonPanel.add(ediButton);
       frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setSize(600, 400);
        frame.setVisible(true);



    }

    private void loadBooks(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] bookInfo = line.split(",");
                if (bookInfo.length == 3) {
                    String title = bookInfo[0];
                    String year = bookInfo[1];
                    String author = bookInfo[2];
                    tableModel.addRow(new Object[]{title, year, author, "Read"});
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
      
           
                LibraryManagement app = new LibraryManagement();
                app.loadBooks("data.txt"); 
            
       
    }
}
