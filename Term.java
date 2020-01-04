
/*************************************************************************
 * @author Kevin Wayne
 *
 * Description: A term and its weight.
 * 
 * @author Owen Astrachan, revised for Java 8-11, toString added
 * @author Isabella Wang, Nov. 12, 2019
 * 
 *************************************************************************/

import java.util.Comparator;

public class Term implements Comparable<Term> {

	private String myWord;
	private double myWeight;

	/**
	 * The constructor for the Term class. Should set the values of word and
	 * weight to the inputs, and throw the exceptions listed below
	 * 
	 * @param word
	 *            The word this term consists of
	 * @param weight
	 *            The weight of this word in the Autocomplete algorithm
	 * @throws NullPointerException
	 *             if word is null
	 * @throws IllegalArgumentException
	 *             if weight is negative
	 */
	public Term(String word, double weight) {
		if (word == null)
		{
			throw new NullPointerException("null word "+word);
		}
		if (weight<0)
		{
			throw new IllegalArgumentException("negative weight "+weight);
		}
		myWord = word;
		myWeight = weight;
	}
	
	/**
	 * Default compare is by word, no weight involved
	 * @return this.getWord().compareTo(that.getWord())
	 */
	@Override
	public int compareTo(Term that) {
		return myWord.compareTo(that.myWord);
	}

	/**
	 * Getter for Term's word
	 * @return this Term's word
	 */
	public String getWord() {
		return myWord;
	}

	/**
	 * Getter for Term's weight
	 * @return this Term's weight
	 */
	public double getWeight() {
		return myWeight;
	}

	/**
	 * @return (word,weight) for this Term
	 */
	@Override
	public String toString() {
		return String.format("(%2.1f,%s)", myWeight, myWord);
	}
	
	/**
	 * Use default compareTo, so only word for equality, not weight
	 * @return true if this.getWord().equal(o.getWord())
	 */
	@Override
	public boolean equals(Object o) {
		Term other = (Term) o;
		return this.compareTo(other) == 0;
	}

	/**
	 * A Comparator for comparing Terms using a set number of the letters they
	 * start with. 
	 * This Comparator required for assignment.
	 *
	 */
	public static class PrefixOrder implements Comparator<Term> {
		private final int myPrefixSize;

		public PrefixOrder(int r) {
			this.myPrefixSize = r;
		}

		/**
		 * Compares v and w lexicographically using only their first r letters.
		 * If the first r letters are the same, then v and w should be
		 * considered equal. This method should take O(r) to run, and be
		 * independent of the length of v and w's length. You can access the
		 * Strings to compare using v.myWord and w.myWord.
		 * 
		 * @param v/w
		 *            - Two Terms whose words are being compared
		 * @return positive number if v > w, negative number if v < w, 0 if they're equal
		 */
		public int compare(Term v, Term w) {
			if (myPrefixSize <= v.getWord().length() && myPrefixSize <= w.getWord().length())//if v and w are longer than the prefix, runs compareTo on the characters in each term that are in the prefix
			{
				for (int i = 0; i < myPrefixSize; i++) {
					if (v.getWord().charAt(i) - w.getWord().charAt(i) < 0) {
						return -1;
					}
					if (v.getWord().charAt(i) - w.getWord().charAt(i) > 0) {
						return 1;
					}
				}
				return 0;
			}
			if (myPrefixSize > v.getWord().length() && myPrefixSize > w.getWord().length()) //if v and w are shorter than the prefix, compares v and w as a whole
			{
				int min = Math.min(v.getWord().length(), w.getWord().length());
				for (int i = 0; i<min; i++)
				{
					if (v.getWord().charAt(i) - w.getWord().charAt(i) < 0) {
						return -1;
					}
					if (v.getWord().charAt(i) - w.getWord().charAt(i) > 0) {
						return 1;
					}
				}
				return 0;
			}
			if (myPrefixSize <= v.getWord().length() && myPrefixSize > w.getWord().length())
			{
				for (int i = 0; i < w.getWord().length(); i++) {
					if (v.getWord().charAt(i) - w.getWord().charAt(i) < 0) {
						return -1;
					}
					if (v.getWord().charAt(i) - w.getWord().charAt(i) > 0) {
						return 1;
					}
				}
				return 1;
			}
			for (int i = 0; i < v.getWord().length(); i++) {
				if (v.getWord().charAt(i) - w.getWord().charAt(i) < 0) {
					return -1;
				}
				if (v.getWord().charAt(i) - w.getWord().charAt(i) > 0) {
					return 1;
				}
			}
			return -1;
		}
	}
}
