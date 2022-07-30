package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JFileChooser;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import model.InvoiceHeader;
import model.InvoiceLine;
import model.InvoiceLineTableModel;
import view.InvoiceFrame;

/**
 *
 * @author Mahmoud
 */
public class Controller implements ActionListener, ListSelectionListener {
    

    private InvoiceFrame frame;
    
    public Controller(InvoiceFrame frame) {
        this.frame= frame;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Action Handler called");
        switch(e.getActionCommand()){
            case "Create New Invoice":
                System.out.println("Create New Invoice");
                createNewInvoice();
                break;
            case "Delete Inovice":
                System.out.println("Delete Inovice");
                deletInvoice();
                break;
            case "Save":
                System.out.println("Save");
                saveItemChanges();
                break;
            case "Cancel":
                System.out.println("Cancel");
                cancelItemChanges();
                break;
            case "Load File":
                System.out.println("Load File");
                loadFfile();
                break;
            case "Save FIle":
                System.out.println("Save FIle");
                saveFile();
                break;   
        }
    }
    
    @Override
    public void valueChanged(ListSelectionEvent e) {
        System.out.println("Row Selected");
        int selectedRow = frame.getInvHeaderTable().getSelectedRow();
        System.out.println(selectedRow);
        ArrayList<InvoiceLine> lines = frame.getInvoiceHeadersList().get(selectedRow).getLines();
        frame.getInvLineTable().setModel(new InvoiceLineTableModel(lines));
    }

    private void createNewInvoice() {
    }

    private void deletInvoice() {
    }


    private void loadFfile() {
       try{ 
           JFileChooser fc = new JFileChooser();
        int result = fc.showOpenDialog(frame);
        //If user selects a file from the window
        if (result == JFileChooser.APPROVE_OPTION){
            File headerFile = fc.getSelectedFile();
            //To get the absolute path of the selected file
            String headerStrPath = headerFile.getAbsolutePath();
            Path headerPath = Paths.get(headerStrPath);
            // This gets the whole content of the file in a List of strings
            // ["1,22-11-2020,Ali"],["2,13-10-2021,Saleh"] 
            List<String> headerLines = Files.lines(headerPath).collect(Collectors.toList());
            
            ArrayList<InvoiceHeader> invoiceHeadersList = new ArrayList();
            
            for(String headerLine : headerLines){
                String[] parts = headerLine.split(",");
                //parts = ["1","22-11-2020","Ali"]
                //parts = ["2,"13-10-2021","Saleh"] 
                int id = Integer.parseInt(parts[0]);
                InvoiceHeader invHeader = new InvoiceHeader(id, parts[1], parts[2]);
                
                //Adding invoice headers to a list of Invoice headers
                invoiceHeadersList.add(invHeader);
            }
            System.out.println("Check");
            result = fc.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION){
               String lineStrPath = fc.getSelectedFile().getAbsolutePath();
               Path linePath = Paths.get(lineStrPath);
               List<String> lineLines = Files.lines(linePath).collect(Collectors.toList());
               
               for(String lineLine : lineLines){
                   String[] parts = lineLine.split(",");
                   int invId = Integer.parseInt(parts[0]);
                   double price = Double.parseDouble(parts[2]);
                   int count = Integer.parseInt(parts[3]);
                   
                   InvoiceHeader header = getInvoiceHeaderById(invoiceHeadersList, invId);
                   InvoiceLine invLine = new InvoiceLine(header, parts[1], price, count);
                   header.getLines().add(invLine);
                   frame.setInvoiceHeadersList(invoiceHeadersList);
               }
            }
        }
        }catch(IOException ex){ 
            ex.printStackTrace();
        }
    }
    
    private InvoiceHeader getInvoiceHeaderById(ArrayList<InvoiceHeader> invoices, int id){
        for(InvoiceHeader invoice : invoices){
            if(invoice.getInvoiceNum()== id){
                return invoice;
            }
        }
        return null;
    }
    
    private void saveFile() {
    }

    private void saveItemChanges() {
    }

    private void cancelItemChanges() {
    }

    
}
