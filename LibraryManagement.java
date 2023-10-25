import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


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

       
         delButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              deleteBook(); 
            }
        });
        
       
       
      
        frame.setSize(600, 400);
        frame.setVisible(true);



    }
    private void deleteFromFile(String name){
  try {
        File inputFile = new File("data.txt");
        File tempFile = new File("temp.txt");

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String currentLine;
        while ((currentLine = reader.readLine()) != null) {
            if (currentLine.contains(name)) {
                continue;
            }
            writer.write(currentLine + "\n");
        }
        writer.close();
        reader.close();

      
        tempFile.renameTo(inputFile);
         } catch (Exception e) {
        e.printStackTrace();
    }
    }
    private void deleteFromTable(String name){
       
        int count = tableModel.getRowCount();
        for (int i=0; i<count; i++){
           String value = tableModel.getValueAt(i, 0).toString();;
            if (value.equalsIgnoreCase(name)){
                tableModel.removeRow(i);
                break;
                }
                }
                
    }
    private void deleteBook(){
        JDialog delBook = new JDialog(frame, "Delete Book");
        delBook.setLayout(new GridLayout(2, 1));
        JLabel label1 = new JLabel("Enter Name of the book to Delete: ");
        JTextField textField = new JTextField();
     
    
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");
     
       
       cancelButton.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
           delBook.dispose();
        }
        
       });
        okButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent arg0){
                String name=textField.getText();
                deleteFromFile(name);
                deleteFromTable(name);
             
                 delBook.dispose();
   
          }});
        delBook.add(label1);
        delBook.add(textField);
        delBook.add(okButton, BorderLayout.SOUTH);
        delBook.add(cancelButton, BorderLayout.SOUTH);
         delBook.setSize(300, 150);
        delBook.setVisible(true);
                                
    
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
    private void editBooks(){

    }

    public static void main(String[] args) {     
                LibraryManagement app = new LibraryManagement();
                app.loadBooks("data.txt"); 
   
    }
}
