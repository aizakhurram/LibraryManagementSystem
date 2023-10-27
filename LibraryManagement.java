import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;


public class LibraryManagement {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton readButton, addButton, ediButton, delButton, popButton;
   
    public LibraryManagement() {
       
        frame = new JFrame("Book Reader");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
         JPanel buttonPanel = new JPanel();
       addButton = new JButton("Add Item");
       delButton = new JButton("Delete Item");
       ediButton = new JButton("Edit Item");
      readButton = new JButton("Read");
       popButton = new JButton("View Popularity");
       buttonPanel.add(addButton);
       buttonPanel.add(delButton);
       buttonPanel.add(ediButton);
       buttonPanel.add(readButton);
       buttonPanel.add(popButton);
       frame.add(buttonPanel, BorderLayout.SOUTH);
        
      
        tableModel = new DefaultTableModel(new Object[]{"Title", "Author", "Year", "Popularity"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);
          
        table.setSelectionBackground(Color.BLUE);
        table.setSelectionForeground(Color.WHITE);
       table.addMouseListener(new MouseListener() {

        @Override
        public void mouseEntered(MouseEvent e) {
            int row = table.rowAtPoint(e.getPoint());
            table.getSelectionModel().setSelectionInterval(row, row);
        }

        @Override
        public void mouseExited(MouseEvent e) {
          table.clearSelection();
        }

        @Override
        public void mouseClicked(MouseEvent e) {}

        @Override
        public void mousePressed(MouseEvent e) {}
        
        @Override
        public void mouseReleased(MouseEvent e) {}
       
       });

           
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBook();
            }
        });


       ListSelectionModel selectionModel = table.getSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                   
                     
            readButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                   String title = (String) tableModel.getValueAt(selectedRow, 0);
                     String c=(String) tableModel.getValueAt(selectedRow, 3);
                    c=c.trim();
                     int count= Integer.parseInt(c);
                      count++;
                      tableModel.setValueAt(Integer.toString(count), selectedRow, 3);
                      String newLine= title+", "+(String) tableModel.getValueAt(selectedRow, 1)+", "+(String) tableModel.getValueAt(selectedRow, 2)+", "+Integer.toString(count);
                      editBookinFile(title, newLine);
                      readBox();
                
               
                }
                
                });
                }
                }
            }
        });

        frame.add(scrollPane, BorderLayout.CENTER);
       
              
        
           
       
         ediButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              editBooks(); 
            }
        });
         delButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              deleteBook(); 
            }
        });
        popButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
              createAndShowGraph();
            }
            
        });
        
       
       
      
        frame.setSize(600, 400);
        frame.setVisible(true);

        

    }
    private void createAndShowGraph() {
        JFrame frame = new JFrame("Pie Chart");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
      
        PieDataset dataset = createDataset("data.txt");
        JFreeChart chart = createChart(dataset);
    
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(400, 300));
    
       
        frame.add(chartPanel);
    
        frame.pack();
        frame.setVisible(true);
    }
    
    private static PieDataset createDataset(String filename) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length == 4) {
                    String bookTitle = parts[0];
                    int bookCount = Integer.parseInt(parts[3]);
                    dataset.setValue(bookTitle, bookCount);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataset;
    }
    
    private static JFreeChart createChart(PieDataset dataset) {
        return ChartFactory.createPieChart("Book Popularity Pie Chart", dataset, true, true, false);
    }
    private void readBox(){
                 frame = new JFrame("Book Reader");
                frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

                frame.setLayout(new BorderLayout());
                String Text="Software engineering is a field that focuses on the development and maintenance of software systems.\n It combines principles of computer science, mathematics, and engineering to create efficient and reliable software solutions.      In software engineering, professionals follow a systematic approach to software development.\n This involves analyzing requirements, designing the system architecture, coding the software, and testing it to ensure its functionality and quality. Software engineers also work on enhancing existing software systems by fixing bugs and adding new features.   One of the key aspects of software engineering is the use of different software development methodologies, such as Agile and Waterfall.\n These methodologies help in managing the software development process and ensuring timely delivery of the product.Software engineers use programming languages like Java, C++, and Python to write code for software applications. They also work with different tools and technologies to build and deploy software systems. Overall, software engineering plays a crucial role in the advancement of technology and the development of innovative software solutions for various industries. It requires a strong understanding of computer science concepts, problem-solving skills, and the ability to work in a team to deliver high-quality software products.\n";
                JTextArea text=new JTextArea(Text, 40, 20);
                frame.add(text);
                JScrollPane scrollPane = new JScrollPane(text);
                scrollPane.setPreferredSize(new Dimension(500,180));
                frame.getContentPane().add(scrollPane);
                frame.pack();
                frame.setVisible(true);
                frame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        int choice = JOptionPane.showConfirmDialog(frame,
                                "Do you want to exit reading the book?",
                                "Confirmation",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE);
        
                        if (choice == JOptionPane.YES_OPTION) {
                            frame.dispose(); 
                        }
                       
                    }
                });
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
               
       
                tableModel.addRow(new Object[]{title, author, year, 0});
                addBook.dispose();
                saveBooks(title, year, author, 0);
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
                if (bookInfo.length == 4) {
                    String title = bookInfo[0];
                    String author = bookInfo[1];
                    String year = bookInfo[2];
                    String popcount= bookInfo[3];
                    tableModel.addRow(new Object[]{title, author, year, popcount});
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void saveBooks(String title, String year, String author, int count) {
        try (FileWriter writer = new FileWriter("data.txt", true)) {
            writer.write("\n"+ title + "," + author + "," + year + "," + count);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void editBookinFile(String name, String newLine){
         try {
        File inputFile = new File("data.txt");
        File tempFile = new File("temp.txt");

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String currentLine;
        while ((currentLine = reader.readLine()) != null) {
            if (currentLine.contains(name)) {
                 writer.write(newLine + "\n");
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
         private void editBookinTable(String name, String[] newLine){
        
        int count = tableModel.getRowCount();
        for (int i=0; i<count; i++){
           String value = tableModel.getValueAt(i, 0).toString();;
            if (value.equalsIgnoreCase(name)){
                tableModel.removeRow(i);
               tableModel.insertRow(i,new Object[]{newLine[0], newLine[1], newLine[2], newLine[3]});
                break;
                }
                }
                
    }    

    
    private void editBooks(){
        JDialog editBook = new JDialog(frame, "Edit Book");
        editBook.setLayout(new GridLayout(5, 2));
       JLabel oldtitle = new JLabel("Title of the book you want to edit:");
        JTextField otitle = new JTextField();

        JLabel title = new JLabel("New Title:");
        JTextField titleF = new JTextField();

        JLabel year = new JLabel("New Year:");
        JTextField yearF = new JTextField();

        JLabel author = new JLabel("New Author:");
        JTextField authorF = new JTextField();
          JLabel pop = new JLabel("New popularity Count:");
        JTextField popC = new JTextField();

        JButton doneButton = new JButton("Done");
        doneButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               String name=otitle.getText();
               String newTitle= titleF.getText();
                String newYear= yearF.getText();
                 String newAuthor= authorF.getText();
                 String newCount=popC.getText();
               String newLine= newTitle+", "+newAuthor+", "+newYear+", "+newCount;
               editBookinFile(name, newLine);
               String[] arr={newTitle,newAuthor,newYear,newCount};
               editBookinTable(name, arr);
               editBook.dispose();
               
            }
            
        });
        editBook.add(oldtitle);
        editBook.add(otitle);
        editBook.add(title);
        editBook.add(titleF);
        editBook.add(year);
        editBook.add(yearF);
        editBook.add(author);
        editBook.add(authorF);
        editBook.add(pop);
        editBook.add(popC);
        editBook.add(doneButton);

        editBook.setSize(300, 150);
        editBook.setVisible(true);
    }
    

    public static void main(String[] args) {     
                LibraryManagement app = new LibraryManagement();
                app.loadBooks("data.txt"); 
   
    }
}
