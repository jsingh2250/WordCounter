import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/** This program is a command-line tool that counts the number of each word in a user-specified file.
 * 
 * @author Jaskaran Singh
 */
public class WordCounter {
    // Store the name of this program as a string.
    private static final String PROGRAM_NAME = "Word Counter";

    // Stores the text input cursor that will be used in this program.
    private static final String TEXT_INPUT_CURSOR = ">";

    /**
     * Prints the text with a new line before and after the text.
     * @param text The text to print.
     */
    private static void printLnTextLn(String text) {
        System.out.println();
        System.out.println(text);
    }

    /**
     * Prints a formatted version of the text input cursor to show the user where to type their input.
     * @param textInputCursor The text input cursor to print.
     */
    private static void printTextInputCursor(String textInputCursor) {
        System.out.println();
        System.out.print(textInputCursor + " ");
    }

    /**
    * Prints information about the specified exception and exits out of the program.
    * 
    * @param exception The exception to print information about.
    */
    public static void printExceptionInformationAndExit(Exception exception) {
        // Print information about the exception.
        System.out.println();
        if (!(exception.getMessage().isEmpty())) {
            System.out.println(exception.getMessage());
        }
        exception.printStackTrace();
        System.out.println();

        // Exit out of the program.
        System.exit(0);
    }

    /**
     * Prints a message to the user to let them know that their input was invalid.
     * 
     */
    private static void printInvalidInputMessage() {
        printLnTextLn("Your input was invalid. Please try again.");
    }

    /**
     * Main method of this class.
     * 
     * @param args User-specified filepath and filename.
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // Declare a variable to store the user's first inputted argument.
        String filename = null;

        // Assume that the user specified the input as the first argument. Ignore any additional arguments.
        if (args.length > 0) {
            filename = args[0];
        } else {
            printLnTextLn("No filename was specified.\n" +
                    "Please rerun this program with your filename as the first argument.");
            System.exit(0);
        }

        // Make sure that the user specified a file with a .txt file extension.
        if (!filename.endsWith(".txt")) {
            printLnTextLn("The filename must end with the file extension \".txt\".");
            System.exit(0);
        }

        // Create a scanner object to read the words in the file.
        File file = new File(filename);
        Scanner fileScanner = null;
        try {
            fileScanner = new Scanner(file);
        } catch (FileNotFoundException exception) {
            printLnTextLn(exception.getMessage());
            System.exit(0);
        }

        // Create a tree map to store words and word counts.
        TreeMap<String, Integer> wordCounts = new TreeMap<>();

        // Determine and store the word counts for each word in the file.
        while (fileScanner.hasNext()) {
            // Read and store the next word from the file.
            String word = fileScanner.next();

            // Determine the new word count for the word.
            int wordCount = wordCounts.getOrDefault(word, 0) + 1;

            // Update the stored words and word counts.
            wordCounts.put(word, wordCount);
        }

        // Tell the user that the word counts will be stored in a file.
        printLnTextLn("The word counts will be stored in a file.");

        // Create a scanner object to read user input.
        Scanner userInputScanner = new Scanner(System.in);

        // Create a default filename for the file that will store the word counts.
        String outputFilename = filename.replace(".txt", " - WordCounts.txt");

        do {
            // Prompt the user with the option to specify a filename for the word counts file.
            printLnTextLn(
                    "Please enter a filename with a \".txt\" file extension for the words counts file or press ENTER to use the filename \""
                            +
                            outputFilename + "\".");
            printTextInputCursor(TEXT_INPUT_CURSOR);

            // Store the user's input.
            String userInput = userInputScanner.nextLine();

            // Don't change the output filename if the user didn't specify a new filename.
            if (userInput.isBlank()) {
                break;
            }
            // If the user specified a filename with a .txt file extension, store that filename.
            else if (userInput.endsWith(".txt")) {
                outputFilename = userInput;
                break;
            }
            // The user gave an invalid input.
            else {
                printInvalidInputMessage();
            }

        } while (true);

        // Create a file writer object to write the words and word counts to a file.
        File outputFile = new File(outputFilename);
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(outputFile);
        } catch (IOException exception) {
            printLnTextLn(exception.getMessage());
            System.exit(0);
        }

        // Write the words and word counts to a file.
        while (!wordCounts.isEmpty()) {
            Map.Entry<String, Integer> wordCountsEntry = wordCounts.pollFirstEntry();
            fileWriter.write(wordCountsEntry.getKey() + ": " + wordCountsEntry.getValue() + "\n");
        }

        // Tell the user that the words and word counts were written to the file.
        printLnTextLn(
                "The words from \"" + filename + "\" were counted and written to \"" + outputFilename + "\" with their associated word counts.");

        // Close the scanners.
        fileScanner.close();
        userInputScanner.close();

        // Print a farewell message.
        printLnTextLn("Thank you for using the " + PROGRAM_NAME + " program!\n" + "Goodbye.");
    }
}