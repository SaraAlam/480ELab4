import java.text.NumberFormat;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import java.lang.Math;
import javafx.scene.control.ListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;

/**
* 
* GUI for Off-campus Housing application
*      @author
*      @version
* 
* List of resources you used:
* 
*/
public class StudentHousing extends Application {
    
    private int noOfRooms;
    private HousemateList list;   // important variable to the model, keep track of all
                                // the info entered, modified and to display
    
    // WIDTH and HEIGHT of GUI stored as constants 
    private final int WIDTH = 400;  // arbitrary number: change and update comments
    private final int HEIGHT = 200;
    
    // visual components to COMPLETE, starting code example
    // to have partial code handler working
    
    private Label title = new Label("Housemates");
    
    private Button saveAndQuitButton  = new Button("Save and Quit");
    private Button listButton  = new Button("A List"); 
    
    private TextArea displayArea1  = new TextArea();  // bad name, but use in handler code
    private TextArea displayArea2;
    
    
    
    
    @Override
    /** Initialises the screen 
    *  @param stage:   The scene's stage 
    */
    public void start(Stage stage) {
        
        noOfRooms = getNumberOfRooms(); // call private method below for window
        // that takes in number of rooms in house 
        
        // Gridpane
        GridPane main_screen = new GridPane();

        int squareRoot = (int)Math.pow(noOfRooms, .5);
        int remainder = noOfRooms % squareRoot;
        int roomNum = 1;
        for(int i=0; i<squareRoot;i++){
            for(int j = 0; j < squareRoot;j++){
            //String room_num = Integer.toString(i+1);
            Button room_i = new Button("" + roomNum);
            roomNum++;
            room_i.setBackground(new Background(new BackgroundFill(Color.GREENYELLOW, new CornerRadii(10), Insets.EMPTY)));
            main_screen.add(room_i, j,i);
            }
        }
        for(int i=0; i < remainder; i++){
            Button room_i = new Button("" + roomNum);
            roomNum++;
            room_i.setBackground(new Background(new BackgroundFill(Color.GREENYELLOW, new CornerRadii(10), Insets.EMPTY)));
            main_screen.add(room_i, i, squareRoot);
        }

        
        // create some HBoxes
        VBox display_box = new VBox(10);
        String[] labels = new String[]{"Room:", "Name:", "Payments:"};
        Button[] add_buttons = new Button[(labels.length)];
        int buttonNum = 0;
        for(String s: labels)
        {
            Label l = new Label(s);
            TextField t = new TextField("");
            add_buttons[buttonNum] = new Button("+");
            
            HBox h = new HBox();
            h.getChildren().addAll(l, t, add_buttons[buttonNum]);
            buttonNum++;
            display_box.getChildren().add(h);
        }

        ArrayList<Payment> p = new ArrayList<Payment>();
        ObservableList<Payment> observable_p = FXCollections.observableArrayList(p);
        ListView<Payment> payment_view = new ListView<Payment>();
        payment_view.setItems(observable_p);
        display_box.getChildren().add(payment_view);
        // create HBox
        VBox grid = new VBox();
        grid.getChildren().addAll(title, main_screen);
        HBox root = new HBox(10);
                
       // set font of heading
        Font font = new Font("Calibri", 20);
        title.setFont(font);
        
        // add all components to VBox
        root.getChildren().addAll(grid, display_box);
        
        
        // create the scene
        Scene scene = new Scene(root, WIDTH, HEIGHT);
      
        // call private methods for button event handlers
        // you will need one for each button added: call and complete all the ones provided
        
        stage.setScene(scene);
        stage.setTitle("Off-campus Houseing Application");
        stage.show(); 
        
    }
    
    /**
    * Method to request number of house rooms from the user
    * @return number of rooms
    */
    private int getNumberOfRooms() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("How many rooms are in the house?");
        dialog.setTitle("House Attributes");
        
        String response = dialog.showAndWait().get(); 
        return Integer.parseInt(response);  
    }
    
    /**
    * Method to initialize housemate list from file
    * Need to be called at the beginning of application to implement
    * save to file feature
    */
    private void initFromFile() {
        list  = new HousemateList(noOfRooms);   
        HousemateFileHandler.readRecords(list);
    }
    
    /**
    * Method to display housemate list in text area
    * Need to be called and completed 
    */
    public void displayHandler() {
        // A message for empty house should be displayed when appropriate
        // so user get some feedback 
        if (!list.isEmpty()) {
            // header
            displayArea1.setText("Room" +  "\t" +  "Name" +  "\n");
            // rest of info missing...
            displayArea1.appendText("To be completed \n");
        }
    }
    
    
    /**
    * Method to display payment list in text are for a particular room:
    *          !!! right now hard-coded for room 1 !!!!
    * Need be called, modified and completed to handle errors
    */
    private void listPaymentHandler() {  
        // List payments for hard-coded room 1
        // Instead of 1 should be replaced by a variable connected to a widget
        Housemate t =  list.search(1);   
        
        PaymentList p  = t.getPayments();
        if(t.getPayments().getTotal() == 0) {
            displayArea2.setText("No payments made for this housemate");
        } 
        else {  
            //The NumberFormat class is similar to the DecimalFormat class that we used previously.
            //The getCurrencyInstance method of this class reads the system values to find out 
            //which country we are in, then uses the correct currency symbol 
            NumberFormat nf =  NumberFormat.getCurrencyInstance();
            String s;
            displayArea2.setText("Month" +  "\t\t" +  "Amount" +  "\n");
            for (int i =  1; i <=  p.getTotal(); i++  ) {
                s =  nf.format(p.getPayment(i).getAmount());
                displayArea2.appendText("" + p.getPayment(i).getMonth() +  "\t\t\t" + s + "\n");
            } 
            displayArea2.appendText("\n" + "Total paid so far :   " + 				
            nf.format(p.calculateTotalPaid()));
        }
    }
    
    private void saveAndQuitHandler() {
        HousemateFileHandler.saveRecords(noOfRooms, list);
        Platform.exit();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}

