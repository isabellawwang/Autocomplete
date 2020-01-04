import java.util.*;

/**
 * The code in BruteAutoComplete uses a priority queue in topMatches.
 * This class is based on the logic of BruteAutoComplete, except it sorts all the elements before finding the top M matches from N.
 *
 * In general, the runtimes of SlowBruteAutocomplete are longer than those of BruteAutocomplete. In some cases, they are not even in the same order
 * of magnitude. When I used the "data/fourletterwords.txt" file in BenchMarkForAutocomplete, the runtime for SlowBruteAutocomplete was almost
 * 70 times longer when searching for matches with empty strings. The difference in runtime between these two Autocomplete classes is most prominent
 * for text files with a greater number of strings. This makes sense, because SlowBruteAutocomplete has to sort every single string, which relies very
 * heavily on the total number of strings. Since BruteAutocomplete uses a PriorityQueue, it's faster to sort by weight and the method does less unnecessary work.
 *
 * @author Isabella Wang
 */

public class SlowBruteAutocomplete extends BruteAutocomplete {
    public SlowBruteAutocomplete(String[] terms, double[] weights)
    {
        super(terms,weights);
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
    @Override
    public List<Term> topMatches(String prefix, int k) {
        if (prefix == null)
            throw new NullPointerException("Null pointer");
        if (k == 0)
            return new ArrayList<Term>();
        if (k < 0)
            throw new IllegalArgumentException("Illegal value of k:" + k);
        List<Term> list = new ArrayList<>();
        List<Term> list = new ArrayList<>();
        for (Term t : myTerms)
        {
            if (t.getWord().startsWith(prefix))
            {
                list.add(t);
            }
        }
        Collections.sort(list, Comparator.comparing(Term::getWeight).reversed());
        return list.subList(0, Math.min(k, list.size()));
    }
}
