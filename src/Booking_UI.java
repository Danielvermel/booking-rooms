import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;

public class Booking_UI {

    //Create the 9 Instances of Rooms
    private static Room nsaRoom1 = new Room("Taff",2, "meeting room",8,1);
    private static Room nsaRoom2 = new Room("Llangorse",2,"meeting room",24,2);
    private static Room nsaRoom3 = new Room("Pen y Fan",2,"teaching space",70,3);
    private static Room nsaRoom4 = new Room("Usk",3,"meeting room",8,4);
    private static Room nsaRoom5 = new Room("Bala",3,"meeting room",24,5);
    private static Room nsaRoom6 = new Room("Cadair Idris",3,"teaching space",70,6);
    private static Room nsaRoom7 = new Room("Wye",4,"meeting room",8,7);
    private static Room nsaRoom8 = new Room("Gower",4,"open meeting/break-out space",24,8);
    private static Room nsaRoom9 = new Room("Snowdon",4,"teaching space",70,9);

    private static Room[] nsaRoomArray = {nsaRoom1, nsaRoom2, nsaRoom3, nsaRoom4, nsaRoom5, nsaRoom6, nsaRoom7, nsaRoom8, nsaRoom9 } ;
    private static Scanner scan = new Scanner(System.in);

    //Possible Reservations in a day (24*9 = 216)
    private static Booking[] bookingRoom = new Booking[216];

    public static void main (String[] args){
        //Variables
        int option;
        boolean flag = true;

        System.out.println("----------------------------------------------");
        System.out.println("----- Welcome to the NSA Booking System ------");
        System.out.println("----------------------------------------------");

        // Display the current date
        System.out.println("Date: "+LocalDate.now());

        //initialize Booking instances
        for(int i = 0; i < bookingRoom.length; i++){
            bookingRoom[i] = new Booking("","",0,"");
        }


        // Loop - For Selecting which option User wants, if the value is not valid it repeats
        do {
            //UI interface
            System.out.println("");
            System.out.println("What do you want to do in our booking system ?");
            System.out.println("1 - View all Rooms ");
            System.out.println("2 - Book a Room");
            System.out.println("3 - View Customers Reservations");
            System.out.println("4 - Exit ");

            //User Input
            /*characterValidator() - Returns the value in integer and validates the input
            * minValue and maxValue help with the validation limits*/
            option = characterValidator(scan.next(),1,4);

            //User option
            switch (option) {
                case 1:
                    viewAll();
                    break;
                case 2:
                    makeReservation();
                    break;
                case 3:
                    viewCustomersReservations();
                    break;
                case 4:
                    System.out.println("You're out of the program!");
                    flag = false;
                    break;
            }
        }while(flag);
    }
    /* -------------------------------------------------------------
     *                   Option 1 - View all Rooms
     *
     * Overview: This function prints the list of all  Rooms in the
     *           System
     *
     * -------------------------------------------------------------
     * */
    public static void viewAll()  {
        System.out.println("----------------------------------------------");
        System.out.println("---------------- List of Rooms ---------------");
        System.out.println("----------------------------------------------");
        System.out.println("");

        //loop to make the Room's table
        for(int i = 0; i < nsaRoomArray.length; i++){
            System.out.println(nsaRoomArray[i]);
            System.out.println("----------------------------------------------------------------------------------");
        }
    }

    /* ------------------------------------------------------------
     *                Option 2 - Make a Reservation (Part 1/2)
     *
     * Overview: This function is used to make reservations. It is
     *           divided in 8 Parts:
     *
     *           ---------  in chooseConstrains() function ----------
     *           1th Part - Choosing Constrains
     *           2nd Part - Constrain Questions
     *           3rd Part   Constrains Loop (for multiple constrains)
     *          ------------------------------------------------------
     *
     *          ----------- in makeReservation() function -----------
     *          4th Part - Choose which Room to Book
     *          5th Part - Costumer Name and Surname
     *          6th Part - Choose booking date dd/mm/yyyy
     *          7th Part - Choose booking Hours
     *          8th Part - Save the Booking properties
     *
     * --------------------------------------------------------------
     * */


    public static void makeReservation() {

        //Variables
        int chooseHour, chooseRoomNumber, ii;
        boolean flag, flagHour, booking_is_possible = true;
        String name, surname, date;
        Room nsaRoomToBook = null; // to save the value of the room to book
        int[] nsaRoomId = new int[nsaRoomArray.length]; // to save in IDs form the value of Rooms that meet the constrains criteria

        nsaRoomId = ChooseConstrains(nsaRoomId); // Returns the IDs of Rooms with specific constrains

        // NOTE: the (1,2,3) parts are inside of ChooseConstrains()

        // ---- 4th Part - Choose which Room to Book  ----

        // Loop - This is for selecting the Room, in case the value is not valid it repeats
        do {

            System.out.println("------------------------------------------------------------ ");
            System.out.println("--------------- These are the Rooms Available -------------- ");
            System.out.println("------------------------------------------------------------ ");

            ii = 0; //its used to get max valid value, used latter

            for (int i = 0; i < nsaRoomArray.length; i++) {
                //Print the Rooms which have the IDs with specific constrains (tha values came from chooseConstrains() function)
                if (nsaRoomId[ii] == nsaRoomArray[i].getRoomId()) {
                    ii++;
                    System.out.println(ii + " - " + "Name: " + nsaRoomArray[i].getRoomName() + " ||| Floor:" + nsaRoomArray[i].getFloor() + " ||| Type of Room: " + nsaRoomArray[i].getTypeRoom() + " ||| Capacity: " + nsaRoomArray[i].getCapacity());
                }

                //In case there aren't any rooms that satisfy user constrains
                if (ii == 0 && i == nsaRoomArray.length - 1) {
                    System.out.println("");
                    System.out.println("*******************************************");
                    System.out.println("       There're no Available Rooms         ");
                    System.out.println("*******************************************");
                    System.out.println("NOTE: Insert again all your constrains!");
                    System.out.println("");
                    //To ask again the constrains it goes to makeReservation()
                    makeReservation();
                    return; // to exit the recursive function
                }
            }


            System.out.println("");
            System.out.println("Which Room do you want to Book?");
            //User Input
            /*characterValidator() - Returns the value in integer and validates the input
             * minValue and maxValue (ii) help with the validation limits*/
            chooseRoomNumber = characterValidator(scan.next(),1,ii);

        }while(chooseRoomNumber == 0);

        //Because the list of Rooms begin with the option 1 and not zero and the first value
        //of the Array begins with 0, there is a need to decrement.
        chooseRoomNumber--;


        // Loop - This insert the chosen Room in the nsaRoomToBook to later save in booking
        for (int i = 0; i < nsaRoomArray.length; i++) {
            if (nsaRoomId[chooseRoomNumber] == nsaRoomArray[i].getRoomId()) {
                ii++;
                nsaRoomToBook = nsaRoomArray[i];
            }
        }

        // ---- 5th Part - Costumer name and surname ----

        // Loop - to select the FIRST NAME
        do {
            System.out.println("");
            System.out.println("What's your first Name?");
            //User Input
            /*characterValidator() - Returns the value into String and makes the first letter upper case
             * and the others lower case to help latter with search for costumer
             * NOTE: If the value is not valid it returns the word again to repeat the loop */
            name = characterValidator(scan.next(),"validate");
        }while(name.equals("again"));

        // Loop - to select the LAST NAME
        do{
            System.out.println("");
            System.out.println("What's your Surname?");
            //User Input
            /*characterValidator() - Returns the value into String and makes the first letter upper case
             * and the others lower case to help latter with search for costumer
             * NOTE: If the value is not valid it returns the word again to repeat the loop*/
            surname = characterValidator(scan.next(),"validate");
        }while(surname.equals("again"));

        // ---- 6th - Choose booking date dd/mm/yyyy ----

        // Loop - to select the date
        do{
            //Date   Year / Month / Day / Hour
            System.out.println("");
            System.out.println("Which date [dd/mm/yyyy] do you want to make your reservation?");
            //User Input
            /*characterValidator() - Returns the value into Boolean, in case it passes
             * the validation it returns false.
             * NOTE: If the value is not valid it returns true to repeat the loop*/
            date = scan.next();
        }while(!(characterValidator(date)));


        // ---- 7th - Choose booking Hours ----

        //Loop - This is for selecting the Hour and set the values in Booking
        do{
            System.out.println("When do you want to book your Room?");
            System.out.println(" ");

            // Loop - This is to print the Hours Table
            for (int i = 1; i <= 24; i++) {
                flag = true;

                //Loop - This is to check if the Hour for a specific Room and date is available
                for(int s = 0; s < bookingRoom.length; s++ ){
                    // Checks if the date is already used by one of the Bookings
                    if(bookingRoom[s].isValidBookingDate(date)){
                        //Checks if there is another booking with the same room
                        if (nsaRoomToBook.getRoomName() == bookingRoom[s].getRoomName()) {
                            //Checks if  that Room has any hour booked and which one
                            if (bookingRoom[s].getBookingHours(i)) {
                                System.out.println(i + " - Between " + --i + ":00 to " + ++i + ":00 -> NOT AVAILABLE");
                                flag = false;
                            }
                        }
                    }
                }
                //Prints when the Hour is available
                if(flag){
                    System.out.println(i + " - Between " + --i + ":00 to " + ++i + ":00");
                }
            }

            //User Input
            //characterValidator() - Returns an int and verifies if its valid
            //NOTE: Returns a 0 if it's not valid
            chooseHour = characterValidator(scan.next(),1,24);

            flag = true; // used to avoid printing the same warning more tha once

            // Loop - checks if the chooseHour is valid or not
            if(chooseHour != 0){

                for(int i= 0; i < bookingRoom.length; i++){
                    // Checks if the Booking i empty
                    if(bookingRoom[i].getRoomName() == "" ){

                        if(flag){ // This is to avoid repeating teh following warning message about Room booked for this hour
                            flagHour = true; // enables the Booking properties to be saved in Booking

                            //Hours available
                            for(int a = 0; a <  bookingRoom.length ; a++) {
                                if (nsaRoomToBook.getRoomName() == bookingRoom[a].getRoomName()) {
                                    // Checks if the date is already used by one of the Bookings
                                    if(bookingRoom[a].isValidBookingDate(date)) {
                                        //Checks if  that Room has any hour booked
                                        if (bookingRoom[a].getBookingHours(chooseHour)) {
                                            flagHour = false; // disables setting the Booking
                                            flag = false; // disables more warning
                                            System.out.println("");
                                            System.out.println("**********************************************");
                                            System.out.println("     Room is already booked for this hour     ");
                                            System.out.println("**********************************************");
                                        }
                                    }
                                }
                            }

                            // ---- 8th - Save the Booking properties ----


                            if(flagHour){ // Checks if the Reservation is possible

                                bookingRoom[i].setFirstName(name);
                                bookingRoom[i].setLastName(surname);
                                bookingRoom[i].setRoomId(nsaRoomToBook.getRoomId());
                                bookingRoom[i].setRoomName(nsaRoomToBook.getRoomName());
                                bookingRoom[i].setBookingDate(date);
                                bookingRoom[i].setBookingHours(chooseHour);
                                System.out.println("");
                                System.out.println(bookingRoom[i]);// Prints the Reservation with all properties
                                booking_is_possible = false; // Enables the Program to continue to the next function
                                flag = false;
                            }
                        }
                    }

                }
            }

        }while(booking_is_possible);
    }


    /* ------------------------------------------------------------
     *
     *           Option 2 - Make a Reservation (Part 2/2)
     *
     * Overview: this is the function that's inside makeReservation()
     *           to simplifying the code comprehension. It enables
     *           the user to choose one or more constrains.
     * --------------------------------------------------------------
     * */

    public static int[] ChooseConstrains( int[] nsaRoomId){
        int option;
        boolean flag  = true, flagger;
        ArrayList<Integer> savedConstrains = new ArrayList<Integer>(); // to save which constrain was selected
        ArrayList<String> savedOptions = new ArrayList<String>();  // to save the value chosen in the constrain selected
        //List of all possible constrains options
        String[] constrains = {"1 - No","2 - Floor","3 - Type of Room","4 - Capacity"};

        // ---- 1st Part - Choosing Constrains ----

        //Loop - This for selecting the first constrain
        do {
            System.out.println("");
            System.out.println("Do you have any constrains?");
            for (int i = 0; i < constrains.length; i++) {
                System.out.println(constrains[i]);
            }
            System.out.println("");
            //User Input
            /*characterValidator() - Returns the value in integer and validates the input
             * minValue and maxValue help with the validation limits
             * NOTE: if option is not valid returns a 0*/
            option = characterValidator(scan.next(),1,4); //
        }while((option == 0));

        // ---- 2nd Part - Constrain Questions ----

        do {
            switch (option) {

                case 1:
                    savedConstrains.add(4);
                    savedOptions.add("0");
                    break;

                case 2:
                    String option1 = "0";

                    do {
                        System.out.println("");
                        System.out.println("Which floor do you want to choose?");
                        System.out.println("1 - second floor");
                        System.out.println("2 - third floor");
                        System.out.println("3 - fourth floor");
                        System.out.println("");
                        //User Input
                        String userOption1 = scan.next();

                        if (userOption1.equals("1")) {
                            option1 = "2";
                        } else if (userOption1.equals("2")) {
                            option1 = "3";
                        } else if (userOption1.equals("3")) {
                            option1 = "4";
                        } else {
                            System.out.println("");
                            System.out.println("-------------------------------------------");
                            System.out.println("------ Insert a valid Character !!! -------");
                            System.out.println("-------------------------------------------");
                            System.out.println("");
                        }

                    } while (option1.equals("0"));
                    savedConstrains.add(1);
                    savedOptions.add(option1);
                    break;

                case 3:
                    String option2 = "0";
                    do {
                        System.out.println("");
                        System.out.println("Which type of room do you want to choose?");
                        System.out.println("1 - meeting room");
                        System.out.println("2 - teaching space");
                        System.out.println("3 - open meeting/break-out space");
                        System.out.println("");
                        //User Input
                        String userOption2 = scan.next();

                        if (userOption2.equals("1")) {
                            option2 = "meeting room";
                        } else if (userOption2.equals("2")) {
                            option2 = "teaching space";
                        } else if (userOption2.equals("3")) {
                            option2 = "open meeting/break-out space";
                        } else {
                            System.out.println("");
                            System.out.println("-------------------------------------------");
                            System.out.println("------ Insert a valid Character !!! -------");
                            System.out.println("-------------------------------------------");
                            System.out.println("");
                        }

                    } while (option2.equals("0"));
                    savedConstrains.add(2);
                    savedOptions.add(option2);
                    break;

                case 4:
                    String option3 = "0";
                    do {
                        System.out.println("How many people do you need in your room?");

                        //User Input
                        String userOption3 = scan.next();
                        int compareCapacity = characterValidator(userOption3,0,70);

                        if (0 < compareCapacity && compareCapacity <= 8) {
                            option3 = "8";
                        } else if (8 < compareCapacity && compareCapacity <= 24) {
                            option3 = "24";
                        } else if (24 < compareCapacity && compareCapacity <= 70) {
                            option3 = "70";
                        } else {
                            System.out.println("*******************************************");
                            System.out.println(" There's no Rooms for more than 70 people  ");
                            System.out.println("*******************************************");
                            System.out.println("");
                        }

                    } while (option3.equals("0"));
                    savedConstrains.add(3);
                    savedOptions.add(option3);
                    break;
            }

            // ---- 3rd Part - Repeat the loop for more constrains ----

            if(!(savedOptions.get(0) == "0")){ // Checks if user chose the option 1 that has value 0 and represents no constrains

                // Loop to select the second or third  constrain
                do {
                    flagger = false; // enable the program to continue
                    System.out.println("");
                    System.out.println("Do you have any more constrains?");

                    //Loop - to print all the constrains
                    for (int i = 0; i < constrains.length; i++) {

                        //Loop - to print constrains that are already set
                        for (int iii = 0; iii < savedConstrains.size(); iii++) {
                            if (i == savedConstrains.get(iii)) {
                                System.out.println(constrains[i] + " /// ->  ALREADY SET ///");
                                flag = false;

                            }
                        }
                        //Prints the constrains that are not set
                        if (flag) {
                            System.out.println(constrains[i]);

                        }
                        flag = true;
                    }

                    System.out.println("");
                    //User Input
                    /*characterValidator() - Returns the value in integer and validates the input
                     * minValue and maxValue help with the validation limits
                     * NOTE: if option is not valid returns a 0*/
                    option = characterValidator(scan.next(), 1, 4);

                    //Loop - This is to print the warning
                    for (int i = 0; i < savedConstrains.size(); i++) {
                        if (savedConstrains.get(i) + 1 == option) {
                            System.out.println("*******************************************");
                            System.out.println("        The constrain " + option + " is set    ");
                            System.out.println("*******************************************");
                            System.out.println("");
                            flagger = true; // disables program to continue because it was chosen a already set constrain
                        }
                    }

                } while (flagger);
            }

            //Checks if the user is done with choosing constrains
            if(option == 1){
                flag = false;
                // ViewAllCondition - this returns the Room IDs with the specific constrains
                nsaRoomId = ViewAllCondition(savedOptions,savedConstrains);
            }

        }while(flag);

        return nsaRoomId; // The Room IDs with the specific constrains
    }

    /* -------------------------------------------------------------
     *            Option 3 - View Customers Reservations
     *
     *  Overview: This function is used to print Reservations. It can
     *            either let you choose a specific name + surname or
     *            view all Reservations.
     *
     *           1th Part - Choosing if search by Customer or not
     *           2nd Part - If yes > What's name and surname
     *           3rd Part - Print list of Reservations
     * -------------------------------------------------------------
     * */

    public static void viewCustomersReservations(){

        int iii = 0, customerSearch;
        String name = "", surname = "";

        // ---- 1st Part - Choosing if search by Customer or not ----

        // Loop - This is to choose if the user wants to search or not by a specific customer
        do {
            System.out.println("");
            System.out.println("Do you want to search for a specific Customer ?");
            System.out.println("1 - Yes");
            System.out.println("2 - No");

            //User Input
            /*characterValidator() - Returns the value in integer and validates the input
             * minValue and maxValue help with the validation limits
             * NOTE: if option is not valid returns a 0*/
            customerSearch = characterValidator(scan.next(),1,2);
        }while(customerSearch==0);

        // ----   2nd Part - If yes > What's name and surname ----

        if(customerSearch == 1){
            //First Name
            do {
                System.out.println("");
                System.out.println("What's your first Name?");
                name = characterValidator(scan.next(),"validate");
            }while(name.equals("again"));

            // Last Name
            do{
                System.out.println("");
                System.out.println("What's your Surname?");
                surname = characterValidator(scan.next(),"validate");
            }while(surname.equals("again"));
        }

        // ---- 3rd - Print list of Reservations ----

        System.out.println("");
        System.out.println("--------------------------------------------");
        System.out.println("----------- List of Reservations -----------");
        System.out.println("--------------------------------------------");


        for (int i = 0; i < bookingRoom.length; i++) {

            for (int ii = 0; ii < nsaRoomArray.length; ii++) {
                if (bookingRoom[i].getRoomName() == nsaRoomArray[ii].getRoomName()) {
                    if(customerSearch == 1) {
                        // compares name and last name written by the user with booking ones
                        if(bookingRoom[i].getFirstName().equals(name) && bookingRoom[i].getLastName().equals(surname)){
                            i++;
                            System.out.println(i-- + "º - " + bookingRoom[i]);
                        }
                    }else{
                        i++;
                        System.out.println(i-- + "º - " + bookingRoom[i]);
                    }

                } else { iii++;  } // If there is no booking
            }
            if (iii == bookingRoom.length) { // To prevent repeating the warning
                System.out.println("");
                System.out.println("********************************************");
                System.out.println("           No reservations made yet!        ");
                System.out.println("********************************************");
            }
        }
    }

    /* -------------------------------------------------------------
     *           Functions - View All Conditions function
     *
     * Overview: This function finds which Rooms fulfill  the
     *           constrains and then returns the ids of Rooms that do.

     * -------------------------------------------------------------
     * */


    public static int[] ViewAllCondition(ArrayList<String> savedOption,  ArrayList<Integer> constrain) {
        int[][] nsaRoomIdBuffer = new int[constrain.size()][nsaRoomArray.length];// holds the Room IDs to compare purposes
        int[] nsaRoomId = new int[nsaRoomArray.length]; // Holds the Room ID
        int[] nsaRoomIdConstrain = new int[nsaRoomArray.length];// holds the Room IDs to compare purposes
        int iii = 0, aa = 0;

        // Loop - Selects the Id Room that satisfy the constrains
        for(int i = 0; i < constrain.size(); i++) {

            if (!(constrain.get(i) == 0)) { // checks if there is no constrains left

                if (constrain.get(i) == 1) { // Floor constrain
                    int floor = Integer.parseInt(savedOption.get(i));
                    for (int ii = 0; ii < nsaRoomArray.length; ii++) {
                        //checks which Rooms are in a specific floor
                        if (nsaRoomArray[ii].getFloor() == floor) {
                            nsaRoomIdBuffer[i][iii] = nsaRoomArray[ii].getRoomId();
                            iii++;
                        }
                    }
                }

                if (constrain.get(i) == 2) { // Type of Room constrain
                    for (int ii = 0; ii < nsaRoomArray.length; ii++) {
                        //checks which Rooms have a specific type
                        if (nsaRoomArray[ii].getTypeRoom() == savedOption.get(i)) {
                            nsaRoomIdBuffer[i][iii] = nsaRoomArray[ii].getRoomId();
                            iii++;
                        }
                    }
                }

                if (constrain.get(i) == 3) { // Capacity constrain
                    int capacity = Integer.parseInt(savedOption.get(i));
                    for (int ii = 0; ii < nsaRoomArray.length; ii++) {
                        //checks which Rooms have a specific capacity
                        if (nsaRoomArray[ii].getCapacity() >= capacity) {
                            nsaRoomIdBuffer[i][iii] = nsaRoomArray[ii].getRoomId();
                            iii++;
                        }
                    }
                }

                if (constrain.get(i) == 4) { // no constrain
                    //Gives all the Rooms when there is no constrains
                    for (int ii = 0; ii < nsaRoomArray.length; ii++) {
                        // Selection of Rooms
                        nsaRoomIdBuffer[i][iii] = nsaRoomArray[ii].getRoomId();
                        iii++;
                    }
                }
            }


            if(i > 0){ // Checks if there is more than one constrain
                for (int a = 0; a < nsaRoomIdBuffer[i].length; a++) {
                    for(int ii= 0; ii < nsaRoomId.length; ii++) {
                        //checks if the ID rom the first/second constrain is the same to the second/third constrain
                        if (nsaRoomIdBuffer[i][a] == nsaRoomId[ii] && nsaRoomId[ii] != 0) {
                            nsaRoomIdConstrain[aa] = nsaRoomIdBuffer[i][a];
                            aa++;
                        }
                    }
                }
                //Loop -  Insert Room IDs in the return variable
                for (int ii = 0; ii < nsaRoomId.length; ii++){
                    if(ii < nsaRoomIdConstrain.length ){
                        nsaRoomId[ii]= nsaRoomIdConstrain[ii];
                    }else{nsaRoomId[ii] = 0;} // Inserts dummies IDS when there is no Room that satisfies both constrains
                    nsaRoomIdConstrain[ii] = 0; // Clean for getting the 3rd constrain Room Id values
                }

            }else{
                // Loop - Inserts the Room Ids when there is only one constrain
                for (int ii = 0 ; ii < nsaRoomArray.length; ii++){
                    nsaRoomId[ii] = nsaRoomIdBuffer[0][ii];
                }
            }

            aa = 0;
            iii = 0;
        }
        return nsaRoomId;
    }

    /* -------------------------------------------------------------
     *                Functions - Validation Functions
     *
     *  Overview: This function validates all the users inputs
     *
     * -------------------------------------------------------------
     * */

    private static int characterValidator(String givenValue, int minimumValue, int maxValue) {

        int givenValueInt;

        try { // Try to convert String to Int otherwise returns 0
            givenValueInt = Integer.parseInt(givenValue);
        } catch (Exception e) {
            givenValueInt = 0;
        }

        //Checks if the value fulfills the requirements
        if(givenValueInt >= minimumValue && givenValueInt <= maxValue){
            return givenValueInt;
        }else{
            System.out.println("");
            System.out.println("-------------------------------------------");
            System.out.println("------ Insert a valid Character !!! -------");
            System.out.println("-------------------------------------------");
            System.out.println("");
            return 0;
        }
    }

    private static String characterValidator (String givenValue, String state) {
        boolean flag = true;

        if(state == "validate"){
            if (givenValue == null) {
                flag = false;
            }

            int len = givenValue.length();

            // Loop - checks whether the character is a letter or not
            for (int i = 0; i < len; i++) {

                // if it is not a letter it will return false
                if ((Character.isLetter(givenValue.charAt(i)) == false)) {
                    flag = false;
                }
            }
        }

        //First letter Upper case, the rest Lower case
        givenValue = givenValue.substring(0, 1).toUpperCase() + givenValue.substring(1).toLowerCase();

        if(flag){
            return givenValue;
        }else{
            System.out.println("");
            System.out.println("-------------------------------------------");
            System.out.println("------ Insert a valid Character !!! -------");
            System.out.println("-------------------------------------------");
            System.out.println("NOTE: Only use words for your name/surname");
            System.out.println("");
            return "again";
        }
    }


    private static boolean characterValidator(String givenValue) {
        String[] givenParts = givenValue.split("/"); //Holds the chosen date by the user
        LocalDate nowDate = LocalDate.now();
        int nowYear = nowDate.getYear();
        int nowMonth = nowDate.getMonthValue();
        int nowDay = nowDate.getDayOfMonth();

        // To verify if the date is valid
        try {
                //checks if the date fulfills the most common requirements
                if ((nowDay <= Integer.parseInt(givenParts[0].trim()) && nowMonth == Integer.parseInt(givenParts[1].trim()) && nowYear == Integer.parseInt(givenParts[2].trim()) || nowMonth < Integer.parseInt(givenParts[1].trim()) && nowYear == Integer.parseInt(givenParts[2].trim()) || nowYear < Integer.parseInt(givenParts[2].trim())) && (Integer.parseInt(givenParts[0].trim()) <= 31) ) {

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                    try { // checks if the date is in the right format and is a value that makes sense
                        LocalDate ld = LocalDate.parse(givenValue, formatter);
                        String result = ld.format(formatter);
                        if(result.equals(givenValue)){ //checks if the format is correct
                            return result.equals(givenValue);
                        }

                    } catch (DateTimeParseException e) {

                    }
                }
            }catch(Exception e){

            }

        System.out.println("");
        System.out.println("-------------------------------------------");
        System.out.println("------ Insert a valid Character !!! -------");
        System.out.println("-------------------------------------------");
        System.out.println("");
        System.out.println("Today is the: "+LocalDate.now());  // Display the current date
        System.out.println("");

        return false;
    }
}