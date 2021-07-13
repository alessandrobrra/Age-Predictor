import java.io.*;
import java.util.Objects;
import java.util.Properties;
import java.util.Scanner;


public class WhatsMyAge {
    private List<State> listOfStates;
    private File file;
    private Properties properties;
    private FileReader fileReader;
    private BufferedReader bufferedReader;
    private InputStream inputStream;


    public WhatsMyAge() {
        listOfStates = null;
        file = null;
        properties = new Properties();
        inputStream = null;
    }
    /**
     * function to set the program values
     * reads properties file and sets them depending if valid
     * if not returns an exception
     */
    private void programValues() throws Exception {
        inputStream = new FileInputStream("/Users/alessandrobarrera/Documents/CS 245/projects/project2/src/config.properties");
        properties.load(inputStream);
        if (properties.getProperty("ListType").equalsIgnoreCase("ArrayList") || properties.getProperty("ListType").equalsIgnoreCase("LinkedList")) {
            if (properties.getProperty("ListType").equalsIgnoreCase("ArrayList")) {
                listOfStates = new ArrayList<State>();
            } else if (properties.getProperty("ListType").equalsIgnoreCase("LinkedList")) {
                listOfStates = new LinkedList<State>();
            }
        } else {
            throw new Exception("Not a valid List Type");
        }
        file = new File(properties.getProperty("Directory"));
        if (!file.exists()) {
            throw new Exception("File does not exist");
        }
        readAndStoreFiles();
    }
    /**
     * function to read and store the values from the files
     *
     */
    private void readAndStoreFiles() throws Exception {
        System.out.println("Reading files");
        File[] listOfFiles = file.listFiles();
        for (int i = 0; i < Objects.requireNonNull(listOfFiles).length; i++) {
            if (listOfFiles[i].isFile() && (listOfFiles[i].getName().endsWith(".TXT") || listOfFiles[i].getName().endsWith(".txt"))) {
                String state = listOfFiles[i].toString();
                state = state.substring((listOfFiles[i].toString().length() - 6), (listOfFiles[i].toString().length() - 4));
                State state1 = new State(state);
                listOfStates.add(state1);
                ArrayList<List> gender = state1.getGenders();
                ArrayList<Year> male = new ArrayList<Year>();
                ArrayList<Year> female = new ArrayList<Year>();
                gender.add(male);
                gender.add(female);
                fileReader = new FileReader(listOfFiles[i]);
                bufferedReader = new BufferedReader(fileReader);
                String line = bufferedReader.readLine();
                while (line != null) {
                    String[] lines = line.split(",");
                    if (lines[1].equals("M")) {
                        boolean safe = isSafe(male, lines[3], Integer.parseInt(lines[4]), Integer.parseInt(lines[2]));
                        if (safe) {
                            Year year = new Year();
                            year.setName(lines[3]);
                            year.addYear(Integer.parseInt(lines[2]));
                            year.setOccurrences(Integer.parseInt(lines[4]));
                            male.add(year);
                        }
                    } else if (lines[1].equals("F")) {
                        boolean safe = isSafe(female, lines[3], Integer.parseInt(lines[4]), Integer.parseInt(lines[2]));
                        if (safe) {
                            Year year = new Year();
                            year.setName(lines[3]);
                            year.addYear(Integer.parseInt(lines[2]));
                            year.setOccurrences(Integer.parseInt(lines[4]));
                            female.add(year);
                        }
                    }
                    line = bufferedReader.readLine();
                }
                //[DE, M, 2020, Zander, 5]
            }
        }
    }

    /**
     * helper function to determine if it's save to create a new Year object
     * A Year object contains the year with the most occurrences, the name and how many occurrences
     * @param gender ArrayList containing all the valid people so far
     * @param name Name of person to check
     * @param occurrences integer number of occurrences
     * @param year integer number of the year
     * @return true or false if it's safe to create
     */
    private boolean isSafe(ArrayList<Year> gender, String name, int occurrences, int year) throws Exception {
        for (int i = 0; i < gender.size(); i++) {
            if (gender.get(i).getName().equalsIgnoreCase(name)) {
                if (gender.get(i).getOccurrences() < occurrences) {
                    Year updatedPerson = new Year();
                    updatedPerson.setOccurrences(occurrences);
                    updatedPerson.addYear(year);
                    updatedPerson.setName(name);
                    gender.remove(i);
                    gender.add(updatedPerson);
                    return false;
                } else if (gender.get(i).getOccurrences() > occurrences) {
                    return false;
                } else if (gender.get(i).getOccurrences() == occurrences) {
                    gender.get(i).addYear(year);
                    return false;
                }
            }
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    /**
     * function to look for what the user wants
     * @param userName String of the name the user wants
     * @param userGender String of the gender  the user wants
     * @param userState String of the US State the user wants
     * @return user search results
     * @throws Exception getter/size method
     */
    public String userSearch(String userName, String userGender, String userState) throws Exception {
        ArrayList<Integer> years;
        String age = "";
        if (userGender.equalsIgnoreCase("m")|| userGender.equalsIgnoreCase("f")) {
            outerloop:
            for (int i = 0; i <= listOfStates.size(); i++) {
                if (i == listOfStates.size()) {
                    return "Not a valid state";
                }
                if (listOfStates.get(i).getName().equalsIgnoreCase(userState)) {
                    ArrayList<List> gender = listOfStates.get(i).getGenders();
                    if (userGender.equalsIgnoreCase("m")) {
                        ArrayList<Year> male = (ArrayList<Year>) gender.get(0);
                        for (int j = 0; j <= male.size(); j++) {
                            if (j == male.size()) {
                                return "No age hypothesis";
                            }
                            if (male.get(j).getName().equalsIgnoreCase(userName)) {
                                years = male.get(j).getYear();
                                age = findHowOld(years);
                                break outerloop;
                            }
                        }
                    } else if (userGender.equalsIgnoreCase("f")) {
                        ArrayList<Year> female = (ArrayList<Year>) gender.get(1);
                        for (int m = 0; m <= female.size(); m++) {
                            if (m == female.size()) {
                                return "No age hypothesis";
                            }
                            if (female.get(m).getName().equalsIgnoreCase(userName)) {
                                years = female.get(m).getYear();
                                age = findHowOld(years);
                                break outerloop;
                            }
                        }
                    }
                }
            }
        } else{
            return "Not a valid gender";
        }
        String cap = userName.substring(0,1).toUpperCase() + userName.substring(1);
        return cap + ", born in " + userState.toUpperCase() + " is most likely around " + age + " years old";
    }

    /**
     *
     * @param years ArrayList containing the years  of that person
     * @return String depending the most likely  age
     * @throws Exception getter method
     */
    private String findHowOld(ArrayList<Integer> years) throws Exception {
        int currentYear = 2021;
        int currentYear2 = 2021;
        if (years.size == 1) {
            currentYear -= years.get(0);
        } else {
            currentYear -= years.get(years.size - 1);
            currentYear2 -= years.get(0);
        }
        if (currentYear2 == 2021) {
            return currentYear + "";
        } else {
            return currentYear + "-" + currentYear2;
        }
    }

    public static void main(String[] args) throws Exception {
        WhatsMyAge age = new WhatsMyAge();
        Scanner input = new Scanner(System.in);
        String userInput = "";
        try {
            age.programValues();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
        while (!userInput.equalsIgnoreCase("exit")) {
            System.out.println("Name  of  the person (or EXIT to quit)");
            userInput = input.nextLine();
            if (userInput.equalsIgnoreCase("exit")) {
                break;
            }
            System.out.println("Gender (M/F)");
            String userGender = input.nextLine();
            System.out.println("State of birth (two-letter state code)");
            String userState = input.nextLine();
            System.out.println(age.userSearch(userInput, userGender, userState));
        }

    }
}

