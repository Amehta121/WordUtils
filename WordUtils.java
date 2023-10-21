/**
 * Provides utilities for word games:
 * 1. finds all words in the dictionary that match a list of letters
 * 2. prints an array of words to the screen in tabular format
 * 3. finds the word from an array of words with the highest score
 * 4. calculates the score of a word according to a table
 *
 * Uses the FileUtils and Prompt classes.
 *
 * @author Aarav Mehta
 * @since October 19, 2023
 */

public class WordUtils {
    private String[] words; // the dictionary of words
    private final int MAX_WORDS = 100000; // Maximum number of words in the dictionary
    private final int MAX_WORD_LENGTH = 30; // Maximum word length

    // File containing dictionary of almost 100,000 words.
    private final String WORD_FILE = "wordList.txt";

    public WordUtils() {
        loadWords();
    }

    /**
     * Load all of the dictionary from a file into words array.
     */
    private void loadWords() {
        try (java.util.Scanner scanner = FileUtils.openToRead(WORD_FILE)) {
            words = new String[MAX_WORDS];
            int count = 0;
            while (scanner.hasNextLine() && count < MAX_WORDS) {
                String line = scanner.nextLine();
                if (line.length() <= MAX_WORD_LENGTH) {
                    words[count] = line;
                    count++;
                }
            }
        } catch (Exception e) {
            System.err.println("ERROR: Could not load the dictionary file.");
            System.exit(1);
        }
    }

    /**
     * Find all words that can be formed by a list of letters.
     *
     * @param letters string containing a list of letters
     * @return array of strings with all words found.
     */
    public String[] findAllWords(String letters) {
        letters = letters.toLowerCase(); // Convert to lowercase
        int foundWordCount = 0;
        String[] foundWords = new String[MAX_WORDS];

        for (int i = 0; i < words.length; i++) {
            if (words[i] == null) {
                break;
            }
            if (canFormWord(letters, words[i].toLowerCase())) {
                foundWords[foundWordCount] = words[i];
                foundWordCount++;
            }
        }

        return trimArray(foundWords, foundWordCount);
    }

    private String[] trimArray(String[] array, int length) {
        String[] trimmed = new String[length];
        System.arraycopy(array, 0, trimmed, 0, length);
        return trimmed;
    }

    /**
     * Check if a given word can be formed from a list of letters.
     *
     * @param letters List of available letters
     * @param word    Word to check
     * @return True if the word can be formed, false otherwise.
     */
    private boolean canFormWord(String letters, String word) {
        if (word.length() > letters.length()) {
            return false;
        }

        int[] letterCount = new int[26];
        for (char letter : letters.toCharArray()) {
            letterCount[letter - 'a']++;
        }

        for (char letter : word.toCharArray()) {
            if (letterCount[letter - 'a'] > 0) {
                letterCount[letter - 'a']--;
            } else {
                return false;
            }
        }

        return true;
    }

    /**
     * Print the words found to the screen.
     *
     * @param wordList array containing the words to be printed
     */
    public void printWords(String[] wordList) {
        int wordsPerRow = 10;
        int count = 0;
        for (int i = 0; i < wordList.length; i++) {
            if (wordList[i] == null) {
                break;
            }
            System.out.printf("%-15s", wordList[i]);
            if (++count % wordsPerRow == 0) {
                System.out.println();
            }
        }
        if (count % wordsPerRow != 0) {
            System.out.println();
        }
    }

    /**
     * Finds the highest scoring word according to a score table.
     *
     * @param wordList   An array of words to check
     * @param scoreTable An array of 26 integer scores in letter order
     * @return The word with the highest score
     */
    public String bestWord(String[] wordList, int[] scoreTable) {
        String bestWord = "";
        int maxScore = 0;

        for (int i = 0; i < wordList.length; i++) {
            if (wordList[i] == null) {
                break;
            }
            int score = getScore(wordList[i], scoreTable);
            if (score > maxScore) {
                maxScore = score;
                bestWord = wordList[i];
            }
        }

        return bestWord;
    }

    /**
     * Calculates the score of one word according to a score table.
     *
     * @param word       The word to score
     * @param scoreTable An array of 26 integer scores in letter order
     * @return The integer score of the word
     */
    public int getScore(String word, int[] scoreTable) {
        int score = 0;
        for (char letter : word.toCharArray()) {
            score += scoreTable[letter - 'a'];
        }
        return score;
    }

    /************************** Testing ****************************/

    public static void main(String[] args) {
        WordUtils wu = new WordUtils();
        wu.run();
    }

    public void run() {
        String letters = Prompt.getString("Please enter a list of letters, from 3 to 12 letters long, without spaces");
        if (letters.length() < 3 || letters.length() > 12) {
            System.out.println("Invalid input. The list of letters should be between 3 and 12 letters long.");
            return;
        }

        String[] wordList = findAllWords(letters);
        System.out.println();
        printWords(wordList);

        // Score table in alphabetic order according to Scrabble
        int[] scoreTable = {1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10};
        String best = bestWord(wordList, scoreTable);

        if (!best.isEmpty()) {
            System.out.println("\nHighest scoring word: " + best);
            System.out.println("Score = " + getScore(best, scoreTable) + "\n");
        } else {
            System.out.println("No valid words found with the given letters.");
        }
    }
}
