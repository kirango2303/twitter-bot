import java.io.BufferedReader;
import java.io.FileReader;
//import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.io.IOException;

/**
 * FileLineIterator provides a useful wrapper around Java's provided
 * BufferedReader and provides practice with implementing an Iterator. Your
 * solution should not read the entire file into memory at once, instead reading
 * a line whenever the next() method is called. See Java's documentation for
 * BufferedReader to learn how to construct one given a path to a file. Then,
 * think about how you can use BufferedReader's methods within this class to
 * implement our desired functionality.
 * <p>
 * Note: Any IOExceptions thrown by readers should be caught and handled
 * properly.
 */
public class FileLineIterator implements Iterator<String> {
    private BufferedReader br;
    private String cachedLine;
    // private static final String dir = System.getProperty("user.dir") + "/test/";

    /**
     * Creates a FileLineIterator for the file located at filePath. Fill out the
     * constructor so that a user can instantiate a FileLineIterator. Feel free to
     * create and instantiate any variables that your implementation requires here.
     * See recitation and lecture notes for guidance.
     * <p>
     * If an IOException is thrown by the BufferedReader or FileReader, then hasNext
     * should return false.
     *
     * @param filePath - the path to the CSV file to be turned to an Iterator
     * @throws IllegalArgumentException if filePath is null or if the file doesn't
     *                                  exist
     */

    public FileLineIterator(String filePath) {
        if (filePath == null) {
            throw new IllegalArgumentException("filePath must not be null");
        }
        FileReader freader = null;
        try {
            freader = new FileReader(filePath);
            br = new BufferedReader(freader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("filePath does not exist");
        }

        try {
            cachedLine = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns true if there are lines left to read in the file, and false
     * otherwise.
     * <p>
     * If there are no more lines left, this method should close the BuffereReader.
     *
     * @return a boolean indicating whether the FileLineIterator can produce another
     *         line from the file
     */
    @Override
    public boolean hasNext() {
        if (cachedLine != null) {
            return true;
        } else {
            try {
                br.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return false;
        }
    }

    /**
     * Returns the next line from the file, or throws a NoSuchElementException if
     * there are no more strings left to return (i.e. hasNext() is false).
     * <p>
     * This method also advances the iterator in preparation for another invocation.
     * If an IOException is thrown during a next() call, the next time next() is
     * called, it should throw a NoSuchElementException.
     *
     * @return the next line in the file
     * @throws java.util.NoSuchElementException if there is no more data in the file
     */
    @Override
    public String next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more lines");
        }
        String currentLine = cachedLine;
        try {
            cachedLine = br.readLine();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return currentLine;
    }
}
