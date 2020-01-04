import java.util.*;

/**
 *
 * This class will provide an O(1) implementation of topMatches --- with a tradeoff of requiring more memory.
 *
 * @author Isabella Wang, Nov. 12 2019
 */

public class HashListAutocomplete implements Autocompletor {
    private static final int MAX_PREFIX = 10;
    private Map<String, List<Term>> myMap;
    private int mySize;

    /**
     * Given arrays of words and weights, initialize myTerms to a corresponding
     * array of Terms sorted lexicographically.
     *
     * This constructor is written for you, but you may make modifications to
     * it.
     *
     * @param terms
     *            - A list of words to form terms from
     * @param weights
     *            - A corresponding list of weights, such that terms[i] has
     *            weight[i].
     * @return a BinarySearchAutocomplete whose myTerms object has myTerms[i] =
     *         a Term with word terms[i] and weight weights[i].
     * @throws NullPointerException if either argument passed in is null
     * @throws IllegalArgumentException if terms and weights are not the same length
     */
    public HashListAutocomplete(String[] terms, double[] weights) {

        if (terms == null || weights == null) {
            throw new NullPointerException("One or more arguments null");
        }

        if (terms.length != weights.length) {
            throw new IllegalArgumentException("terms and weights are not the same length");
        }
        initialize(terms,weights);
    }

    @Override
    /**
     * This method serves as a helper method for the constructor and helps build a HashListAutocomplete.
     * In this case, it helps initialize and fill the map with the appropriate keys and corresponding values,
     * and sort the ArrayList values by weight.
     *
     * @param terms
     *            - An array of Strings that will be used to build myTerms
     * @param weights
     *            - An array of weights that will be used to build myTerms
     */
    public void initialize(String[] terms, double[] weights)
    {
        myMap = new HashMap<String, List<Term>>();
        for (int currentIndex = 0; currentIndex < terms.length; currentIndex++) {
            String currentString = terms[currentIndex];
            int until = Math.min(MAX_PREFIX, currentString.length());
            for (int i = 0; i <= until; i++) {
                if (currentString.length() >= i) {
                    String keyToAdd = currentString.substring(0, i);
                    Term valueToAdd = new Term(currentString, weights[currentIndex]);
                    myMap.putIfAbsent(keyToAdd, new ArrayList<Term>());
                    myMap.get(keyToAdd).add(valueToAdd);
                }
            }
        }
        for (String key : myMap.keySet()) {
            Collections.sort(myMap.get(key),Comparator.comparing(Term::getWeight).reversed());
        }
    }

    /**
     * Returns an array containing the
     * k words in myTerms with the largest weight which match the given prefix,
     * in descending weight order. If less than k words exist matching the given
     * prefix (including if no words exist), then the array instead contains all
     * those words. e.g. If terms is {air:3, bat:2, bell:4, boy:1}, then
     * topKMatches("b", 2) should return {"bell", "bat"}, but topKMatches("a",
     * 2) should return {"air"}
     *
     * @param prefix
     *            - A prefix which all returned words must start with
     * @param k
     *            - The (maximum) number of words to be returned
     * @return A list of the k words with the largest weights among all words
     *         starting with prefix, in descending weight order. If less than k
     *         such words exist, return an array containing all those words If
     *         no such words exist, return an empty list.
     * @throws NullPointerException if prefix is null
     * @throws IllegalArgumentException if the value of k is illegal (negative)
     */
    public List<Term> topMatches(String prefix, int k) {
        if (prefix == null)
            throw new NullPointerException("Null pointer");
        if (!(myMap.containsKey(prefix)))
            return new ArrayList<Term>();
        if (k == 0)
            return new ArrayList<Term>();
        if (k < 0)
            throw new IllegalArgumentException("Illegal value of k:" + k);
        List<Term> all = myMap.get(prefix);
        return all.subList(0, Math.min(k, all.size()));
    }

    @Override
    /**
     * Accounts for the size in Bytes for every Term object and every String/key in the map
     * @return size in Bytes for all objects and Strings/keys in the map
     */
    public int sizeInBytes() {
        for (String key : myMap.keySet())
        {
            mySize = mySize + key.length() * BYTES_PER_CHAR;
            List<Term> currentList = myMap.get(key);
            for (int i = 0; i < currentList.size(); i++)
            {
                Term thisTerm = currentList.get(i);
                mySize = mySize + BYTES_PER_DOUBLE + BYTES_PER_CHAR*thisTerm.getWord().length();
            }
        }
        return mySize;
    }
}
