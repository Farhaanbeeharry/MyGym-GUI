import java.net.Socket;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.*;
import java.net.*;
import java.util.*;

public class ClientGUI extends Application {

    static TextField inputTrainerID, inputClientID, inputDate, inputDelete, inputBookingID;
    static TextField inputTrainerID3, inputSpecialismID2, inputTimeSlot2, inputDate3;
    static TextField inputTrainerID2, inputClientID2, inputDate2, inputTimeSlot, inputSpecialismID;
    static Stage tableWindow, addWindow, warningWindow, deleteWindow, updateWindow;
    static TableView tableItems;
    static Socket socket = null;

    public static void main(String[] args) {
    
        try {
            socket = new Socket("localhost", 8888);
        } catch (Exception ex) {
        }
        
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
try {
ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());


        GridPane mainMenu = new GridPane();
        mainMenu.setAlignment(Pos.CENTER);
        mainMenu.setVgap(30);

        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);

        Label programTitle = new Label();
        programTitle.setText("My Gym");
        vbox.getChildren().add(programTitle);

        Button btnListAll = new Button("List All Bookings");
        btnListAll.setAlignment(Pos.BASELINE_LEFT);
        btnListAll.setMinWidth(650);
        btnListAll.setMinHeight(50);
        btnListAll.setFocusTraversable(false);
        btnListAll.setOnAction(e -> {
            try {
	String input = "LISTALL";
	String inputString[] = input.split(" ");

objectOutputStream.writeObject(inputString);
                objectOutputStream.reset();



if (inputString.length > 1) {
                        warningBox(objectInputStream.readObject().toString());
                    } else {

                        ArrayList<String> allBooking = (ArrayList<String>) objectInputStream.readObject();
                        ArrayList<Booking> allBookings = new ArrayList();

                        for (int x = 0; x < allBooking.size(); x++) {
                            String lineText = allBooking.get(x);

                            String booking[] = lineText.split("#");
                            Booking listedBooking = new Booking();

                            listedBooking.setBookingID(booking[0]);
                            listedBooking.setCustomerID(booking[1]);
                            listedBooking.setTrainerID(booking[2]);
                            listedBooking.setSpecialismID(booking[3]);
                            listedBooking.setTimeSlot(booking[4]);
                            listedBooking.setDate(booking[5]);
                            listedBooking.setDuration(Calculations.getDuration(listedBooking.getTimeSlot()));

                            allBookings.add(listedBooking);

                        }

                        displayBookings(allBookings);

                    }





                
            } catch (Exception ex) {
            }
        });

        inputTrainerID = new TextField();
        inputTrainerID.setPromptText("TrainerID here");
        inputTrainerID.setMinWidth(300);
        inputTrainerID.setMinHeight(50);
        inputTrainerID.setFocusTraversable(false);

        Button btnListByTrainerID = new Button("List All Bookings by TrainerID");
        btnListByTrainerID.setAlignment(Pos.BASELINE_LEFT);
        btnListByTrainerID.setMinWidth(300);
        btnListByTrainerID.setMinHeight(50);
        btnListByTrainerID.setFocusTraversable(false);
        btnListByTrainerID.setOnAction(e -> {
            try {

String input = "LISTPT " + inputTrainerID.getText();
	String inputString[] = input.split(" ");


objectOutputStream.writeObject(inputString);
                objectOutputStream.reset();

ArrayList<Booking> bookingsTrainerID = new ArrayList();

                    if (inputString.length == 1) {
                        warningBox("Not enough arguments !\n");
                    } else if (inputString.length > 2) {
                        warningBox("Too many arguments !\n");
                    } else if (inputString[1].length() != 5) {
                        warningBox("TrainerID can have 5 characters only !\n");
                    } else if (!inputString[1].matches("[a-zA-Z0-9]+")) {
                        warningBox("TrainerID should be alphanumeric !\n");
                    } else {

                        ArrayList<String> bookingTrainerID = (ArrayList<String>) objectInputStream.readObject();

                        if ((bookingTrainerID.size() > 0) && (bookingTrainerID.get(0).equals("NotFound"))) {
                           warningBox("TrainerID not found !\n");
                        } else {
                            for (int x = 0; x < bookingTrainerID.size(); x++) {
                                String lineText = bookingTrainerID.get(x);

                                String booking[] = lineText.split("#");
                                Booking listedBooking = new Booking();

                                listedBooking.setBookingID(booking[0]);
                                listedBooking.setCustomerID(booking[1]);
                                listedBooking.setTrainerID(booking[2]);
                                listedBooking.setSpecialismID(booking[3]);
                                listedBooking.setTimeSlot(booking[4]);
                                listedBooking.setDate(booking[5]);
                                listedBooking.setDuration(Calculations.getDuration(listedBooking.getTimeSlot()));

                                bookingsTrainerID.add(listedBooking);

                            }

                            if (bookingsTrainerID.size() != 0) {
                                displayBookings(bookingsTrainerID);
                            } else if (bookingsTrainerID.size() == 0) {
                               warningBox("No booking for this TrainerID !\n");
                            }
                        }
                    }



            } catch (Exception ex) {
            }
        });

        inputClientID = new TextField();
        inputClientID.setPromptText("ClientID here");
        inputClientID.setMinWidth(300);
        inputClientID.setMinHeight(50);
        inputClientID.setFocusTraversable(false);

        Button btnListByClientID = new Button("List All Bookings by ClientID");
        btnListByClientID.setAlignment(Pos.BASELINE_LEFT);
        btnListByClientID.setMinWidth(300);
        btnListByClientID.setMinHeight(50);
        btnListByClientID.setFocusTraversable(false);
        btnListByClientID.setOnAction(e -> {
            try {

String input = "LISTCLIENT " + inputClientID.getText();
	String inputString[] = input.split(" ");


objectOutputStream.writeObject(inputString);
                objectOutputStream.reset();

ArrayList<Booking> bookingsClientID = new ArrayList();

 if (inputString.length == 1) {
                        warningBox("Not enough arguments !\n");
                    } else if (inputString.length > 2) {
                         warningBox("Too many arguments !\n");
                    } else if (inputString[1].length() != 5) {
                         warningBox("ClientID can have 5 characters only !\n");
                    } else if (!inputString[1].matches("[a-zA-Z0-9]+")) {
                        warningBox("ClientID should be alphanumeric !\n");
                    } else {

                        ArrayList<String> bookingClientiD = (ArrayList<String>) objectInputStream.readObject();

                        if ((bookingClientiD.size() > 0) && (bookingClientiD.get(0).equals("NotFound"))) {
                            warningBox("ClientID not found !\n");
                        } else {
                            for (int x = 0; x < bookingClientiD.size(); x++) {
                                String lineText = bookingClientiD.get(x);

                                String booking[] = lineText.split("#");
                                Booking listedBooking = new Booking();

                                listedBooking.setBookingID(booking[0]);
                                listedBooking.setCustomerID(booking[1]);
                                listedBooking.setTrainerID(booking[2]);
                                listedBooking.setSpecialismID(booking[3]);
                                listedBooking.setTimeSlot(booking[4]);
                                listedBooking.setDate(booking[5]);
                                listedBooking.setDuration(Calculations.getDuration(listedBooking.getTimeSlot()));

                                bookingsClientID.add(listedBooking);

                            }

                            if (bookingsClientID.size() != 0) {
                                displayBookings(bookingsClientID);
                            } else if (bookingsClientID.size() == 0) {
                                warningBox("No booking for this ClientID !\n");
                            }
                        }
                    }
            



            } catch (Exception ex) {
            }
        });

        inputDate = new TextField();
        inputDate.setPromptText("Date here");
        inputDate.setMinWidth(300);
        inputDate.setMinHeight(50);
        inputDate.setFocusTraversable(false);

        Button btnListByDate = new Button("List All Bookings by Date");
        btnListByDate.setAlignment(Pos.BASELINE_LEFT);
        btnListByDate.setMinWidth(300);
        btnListByDate.setMinHeight(50);
        btnListByDate.setFocusTraversable(false);
        btnListByDate.setOnAction(e -> {
            try {
             //   displayBookings();
            } catch (Exception ex) {
            }
        });

        Button btnAdd = new Button("Add new booking");
        btnAdd.setAlignment(Pos.BASELINE_LEFT);
        btnAdd.setMinWidth(650);
        btnAdd.setMinHeight(50);
        btnAdd.setFocusTraversable(false);
        btnAdd.setOnAction(e -> {
            try {
                addNewBooking();
            } catch (Exception ex) {
            }
        });

        Button btnUpdate = new Button("Update a booking");
        btnUpdate.setAlignment(Pos.BASELINE_LEFT);
        btnUpdate.setMinWidth(650);
        btnUpdate.setMinHeight(50);
        btnUpdate.setFocusTraversable(false);
        btnUpdate.setOnAction(e -> {
            try {
                updateBooking();
            } catch (Exception ex) {
            }
        });

        Button btnDelete = new Button("Delete booking");
        btnDelete.setAlignment(Pos.BASELINE_LEFT);
        btnDelete.setMinWidth(650);
        btnDelete.setMinHeight(50);
        btnDelete.setFocusTraversable(false);
        btnDelete.setOnAction(e -> {
            try {
                deleteBooking();
            } catch (Exception ex) {
            }
        });

        Button exitBtn = new Button("Exit");
        exitBtn.setAlignment(Pos.BASELINE_LEFT);
        exitBtn.setMinWidth(650);
        exitBtn.setMinHeight(50);
        exitBtn.setFocusTraversable(false);
        exitBtn.setOnAction(e -> {
            try {
socket.close();
                System.exit(0);
            } catch (Exception ex) {
            }
        });

        HBox hboxTrainerID = new HBox(50);
        hboxTrainerID.getChildren().addAll(inputTrainerID, btnListByTrainerID);
        hboxTrainerID.setAlignment(Pos.CENTER);

        HBox hboxClientID = new HBox(50);
        hboxClientID.getChildren().addAll(inputClientID, btnListByClientID);
        hboxClientID.setAlignment(Pos.CENTER);

        HBox hboxDate = new HBox(50);
        hboxDate.getChildren().addAll(inputDate, btnListByDate);
        hboxDate.setAlignment(Pos.CENTER);

        mainMenu.getChildren().add(vbox);
        mainMenu.add(btnListAll, 0, 1);
        mainMenu.add(hboxTrainerID, 0, 2);
        mainMenu.add(hboxClientID, 0, 3);
        mainMenu.add(hboxDate, 0, 4);
        mainMenu.add(btnAdd, 0, 5);
        mainMenu.add(btnUpdate, 0, 6);
        mainMenu.add(btnDelete, 0, 7);
        mainMenu.add(exitBtn, 0, 8);

        Scene scene = new Scene(mainMenu);

        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.setTitle("MyGym");
        primaryStage.setOnCloseRequest(e -> {
            try {
                e.consume();
                System.exit(0);

            } catch (Exception ex) {
            }
        });
        primaryStage.setResizable(false);
        primaryStage.show();

} catch (Exception e) {
            System.out.println("Exception caught when trying to listen on port");
            e.printStackTrace();
        }

    }

    public static void displayBookings(ArrayList<Booking> allBookings) {

        tableWindow = new Stage();
        tableWindow.setResizable(false);
        tableWindow.setTitle("Table");

        double columnWidth = 200;

        try {
            TableColumn<String, String> bookingID = new TableColumn<>("Booking ID");
            bookingID.setCellValueFactory(new PropertyValueFactory<>("BookingID"));
            bookingID.setMinWidth(columnWidth);
            bookingID.setResizable(false);
            bookingID.setSortable(false);

            TableColumn<String, String> customerID = new TableColumn<>("Customer ID");
            customerID.setCellValueFactory(new PropertyValueFactory<>("CustomerID"));
            customerID.setMinWidth(columnWidth);
            customerID.setResizable(false);
            customerID.setSortable(false);

            TableColumn<String, String> trainerID = new TableColumn<>("Trainer ID");
            trainerID.setCellValueFactory(new PropertyValueFactory<>("TrainerID"));
            trainerID.setMinWidth(columnWidth);
            trainerID.setResizable(false);
            trainerID.setSortable(false);

            TableColumn<String, String> specialismID = new TableColumn<>("Specialism ID");
            specialismID.setCellValueFactory(new PropertyValueFactory<>("SpecialismID"));
            specialismID.setMinWidth(columnWidth);
            specialismID.setResizable(false);
            specialismID.setSortable(false);

            TableColumn<String, String> timeSlot = new TableColumn<>("Time Slot");
            timeSlot.setCellValueFactory(new PropertyValueFactory<>("TimeSlot"));
            timeSlot.setMinWidth(columnWidth);
            timeSlot.setResizable(false);
            timeSlot.setSortable(false);

            TableColumn<String, String> date = new TableColumn<>("Date Booked");
            date.setCellValueFactory(new PropertyValueFactory<>("Date"));
            date.setMinWidth(columnWidth);
            date.setResizable(false);
            date.setSortable(false);

            TableColumn<String, Long> duration = new TableColumn<>("Duration");
            duration.setCellValueFactory(new PropertyValueFactory<>("Duration"));
            duration.setMinWidth(columnWidth);
            duration.setResizable(false);
            duration.setSortable(false);

            tableItems = new TableView<>();
            tableItems.setFocusTraversable(false);
            tableItems.setEditable(false);
            tableItems.prefHeightProperty().bind(tableWindow.heightProperty());
            tableItems.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            for (int i = 0; i < allBookings.size(); i++) {
            tableItems.getItems().add(new Booking(allBookings.get(i).getBookingID(),
allBookings.get(i).getCustomerID(),
allBookings.get(i).getTrainerID(),
allBookings.get(i).getSpecialismID(),
allBookings.get(i).getTimeSlot(),
allBookings.get(i).getDate(),
allBookings.get(i).getDuration()));
        }

            tableItems.getColumns().addAll(bookingID, customerID, trainerID, specialismID, timeSlot, date, duration);
        } catch (Exception e) {
        }

        Button btnBack = new Button("Back");
        btnBack.setPadding(new Insets(10, 10, 10, 10));
        btnBack.setMinWidth(250);
        btnBack.setFocusTraversable(false);
        btnBack.setOnAction(e -> tableWindow.close());

        VBox vbox = new VBox();
        vbox.getChildren().addAll(tableItems, btnBack);
        vbox.setAlignment(Pos.BOTTOM_CENTER);

        BorderPane bp = new BorderPane();
        bp.setCenter(vbox);

        Scene scene = new Scene(bp);

        tableWindow.setScene(scene);
        tableWindow.setMaximized(true);
        tableWindow.show();

    }

    public static void addNewBooking() {

        addWindow = new Stage();
        addWindow.setResizable(false);
        addWindow.setTitle("Add new booking");

        GridPane addNewGrid = new GridPane();
        addNewGrid.setAlignment(Pos.CENTER);
        addNewGrid.setVgap(30);
        
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);

        Label addTitle = new Label();
        addTitle.setText("Add new booking");
        vbox.getChildren().add(addTitle);
        
        inputClientID2 = new TextField();
        inputClientID2.setMinWidth(300);
        inputClientID2.setMinHeight(50);
        inputClientID2.setPromptText("clientID here");
        inputClientID2.setFocusTraversable(false);
        
       
        inputTrainerID2 = new TextField();
        inputTrainerID2.setMinWidth(300);
        inputTrainerID2.setMinHeight(50);
        inputTrainerID2.setPromptText("trainerID here");
        inputTrainerID2.setFocusTraversable(false);
        
        
        inputSpecialismID = new TextField();
        inputSpecialismID.setMinWidth(300);
        inputSpecialismID.setMinHeight(50);
        inputSpecialismID.setPromptText("specialismID here");
        inputSpecialismID.setFocusTraversable(false);
        
        inputTimeSlot = new TextField();
        inputTimeSlot.setMinWidth(300);
        inputTimeSlot.setMinHeight(50);
        inputTimeSlot.setPromptText("timeSlot here");
        inputTimeSlot.setFocusTraversable(false);
        
        inputDate2 = new TextField();
        inputDate2.setMinWidth(300);
        inputDate2.setMinHeight(50);
        inputDate2.setPromptText("date here");
        inputDate2.setFocusTraversable(false);
        
        Button btnAdd = new Button("Add");
        btnAdd.setAlignment(Pos.BASELINE_LEFT);
        btnAdd.setMinWidth(650);
        btnAdd.setMinHeight(50);
        btnAdd.setFocusTraversable(false);
        btnAdd.setOnAction(e -> {
            try {

            } catch (Exception ex) {
            }
        });


        Button btnCancel = new Button("Cancel");
        btnCancel.setAlignment(Pos.BASELINE_LEFT);
        btnCancel.setMinWidth(650);
        btnCancel.setMinHeight(50);
        btnCancel.setFocusTraversable(false);
        btnCancel.setOnAction(e -> addWindow.close());
        
        addNewGrid.getChildren().add(vbox);
        addNewGrid.add(addTitle, 0, 1);
        addNewGrid.add(inputClientID2, 0, 2);
        addNewGrid.add(inputTrainerID2, 0, 3);
        addNewGrid.add(inputSpecialismID, 0, 4);
        addNewGrid.add(inputTimeSlot, 0, 5);
        addNewGrid.add(inputDate2, 0, 6);
        addNewGrid.add(btnAdd, 0, 7);
        addNewGrid.add(btnCancel, 0, 8);
        
        
        Scene scene = new Scene(addNewGrid);

        addWindow.setScene(scene);
        addWindow.setMaximized(true);
        addWindow.show();

    }

    public static void deleteBooking() {

        deleteWindow = new Stage();
        deleteWindow.setResizable(false);
        deleteWindow.setTitle("Delete booking");

        GridPane deleteGrid = new GridPane();
        deleteGrid.setAlignment(Pos.CENTER);
        deleteGrid.setVgap(30);
        
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);

        Label deleteTitle = new Label();
        deleteTitle.setText("Delete booking");
        vbox.getChildren().add(deleteTitle);
        
        inputDelete = new TextField();
        inputDelete.setMinWidth(300);
        inputDelete.setMinHeight(50);
        inputDelete.setPromptText("BookingID here");
        inputDelete.setFocusTraversable(false);
        
        CheckBox deleteAgree = new CheckBox("Agree to delete");
        deleteAgree.setFocusTraversable(false);
       
        Button btnDelete = new Button("Delete");
        btnDelete.setAlignment(Pos.BASELINE_LEFT);
        btnDelete.setMinWidth(650);
        btnDelete.setMinHeight(50);
        btnDelete.setFocusTraversable(false);
        btnDelete.setOnAction(e -> {
            try {

            } catch (Exception ex) {
            }
        });


        Button btnCancel = new Button("Cancel");
        btnCancel.setAlignment(Pos.BASELINE_LEFT);
        btnCancel.setMinWidth(650);
        btnCancel.setMinHeight(50);
        btnCancel.setFocusTraversable(false);
        btnCancel.setOnAction(e -> deleteWindow.close());
        
        deleteGrid.getChildren().add(vbox);
        deleteGrid.add(deleteTitle, 0, 1);
        deleteGrid.add(inputDelete, 0, 2);
        deleteGrid.add(deleteAgree, 0, 3);
        deleteGrid.add(btnDelete, 0, 4);
        deleteGrid.add(btnCancel, 0, 5);
        deleteGrid.setAlignment(Pos.CENTER);
        
        
        Scene scene = new Scene(deleteGrid);

        deleteWindow.setScene(scene);
        deleteWindow.setMaximized(true);
        deleteWindow.show();
        
        
    }
    
    public static void updateBooking() {
          updateWindow = new Stage();
        updateWindow.setResizable(false);
        updateWindow.setTitle("Update booking");

        GridPane updateNewGrid = new GridPane();
        updateNewGrid.setAlignment(Pos.CENTER);
        updateNewGrid.setVgap(30);
        
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);

        Label updateTitle = new Label();
        updateTitle.setText("Update booking");
        vbox.getChildren().add(updateTitle);
        
        inputBookingID = new TextField();
        inputBookingID.setMinWidth(300);
        inputBookingID.setMinHeight(50);
        inputBookingID.setPromptText("existing bookingID here");
        inputBookingID.setFocusTraversable(false);
        
       
        inputTrainerID3 = new TextField();
        inputTrainerID3.setMinWidth(300);
        inputTrainerID3.setMinHeight(50);
        inputTrainerID3.setPromptText("trainerID here");
        inputTrainerID3.setFocusTraversable(false);
        
        
        inputSpecialismID2 = new TextField();
        inputSpecialismID2.setMinWidth(300);
        inputSpecialismID2.setMinHeight(50);
        inputSpecialismID2.setPromptText("specialismID here");
        inputSpecialismID2.setFocusTraversable(false);
        
        inputTimeSlot2 = new TextField();
        inputTimeSlot2.setMinWidth(300);
        inputTimeSlot2.setMinHeight(50);
        inputTimeSlot2.setPromptText("timeSlot here");
        inputTimeSlot2.setFocusTraversable(false);
        
        inputDate3 = new TextField();
        inputDate3.setMinWidth(300);
        inputDate3.setMinHeight(50);
        inputDate3.setPromptText("date here");
        inputDate3.setFocusTraversable(false);
        
        Button btnUpdate = new Button("Update");
        btnUpdate.setAlignment(Pos.BASELINE_LEFT);
        btnUpdate.setMinWidth(650);
        btnUpdate.setMinHeight(50);
        btnUpdate.setFocusTraversable(false);
        btnUpdate.setOnAction(e -> {
            try {

            } catch (Exception ex) {
            }
        });


        Button btnCancel = new Button("Cancel");
        btnCancel.setAlignment(Pos.BASELINE_LEFT);
        btnCancel.setMinWidth(650);
        btnCancel.setMinHeight(50);
        btnCancel.setFocusTraversable(false);
        btnCancel.setOnAction(e -> updateWindow.close());
        
        updateNewGrid.getChildren().add(vbox);
        updateNewGrid.add(updateTitle, 0, 1);
        updateNewGrid.add(inputBookingID, 0, 2);
        updateNewGrid.add(inputTrainerID3, 0, 3);
        updateNewGrid.add(inputSpecialismID2, 0, 4);
        updateNewGrid.add(inputTimeSlot2, 0, 5);
        updateNewGrid.add(inputDate3, 0, 6);
        updateNewGrid.add(btnUpdate, 0, 7);
        updateNewGrid.add(btnCancel, 0, 8);
        
        
        Scene scene = new Scene(updateNewGrid);

        updateWindow.setScene(scene);
        updateWindow.setMaximized(true);
        updateWindow.show();

    }
    
    public static void warningBox(String message) {
        
        warningWindow = new Stage();
        warningWindow.setTitle("Warning");
        warningWindow.setResizable(false);
        warningWindow.initModality(Modality.APPLICATION_MODAL);

        GridPane exitPane = new GridPane();
        exitPane.setAlignment(Pos.CENTER);
        exitPane.setVgap(30);

        Label exitMessage = new Label();
        exitMessage.setText(message);
        exitMessage.setAlignment(Pos.CENTER);

        Button btnOK = new Button("Ok");
        btnOK.setMinWidth(250);
        btnOK.setFocusTraversable(false);
        btnOK.setOnAction(e -> warningWindow.close());

       
        exitPane.add(exitMessage, 0, 0);
        exitPane.add(btnOK, 0, 1);

        Scene scene = new Scene(exitPane, 600, 150);

        warningWindow.setScene(scene);
        warningWindow.showAndWait();
    }
}

