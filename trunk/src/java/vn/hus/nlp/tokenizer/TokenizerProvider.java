/**
 * (C) Le Hong Phuong, phuonglh@gmail.com
 * vn.hus.tokenizer
 * 2007
 */
package vn.hus.nlp.tokenizer;

import vn.hus.nlp.tokenizer.segmenter.AbstractResolver;
import vn.hus.nlp.tokenizer.segmenter.Segmenter;
import vn.hus.nlp.tokenizer.segmenter.UnigramResolver;



/**
 * @author LE Hong Phuong
 * <p>
 * 8 janv. 07
 * </p>
 * A provider of tokenizer. It creates a tokenizer for Vietnamese.
 * 
 */
public final class TokenizerProvider {
	
	/**
	 * A lexical segmenter
	 */
	private final Segmenter segmenter;
	/**
	 * An ambiguity resolver
	 */
	private final AbstractResolver resolver;
	/**
	 * The tokenizer
	 */
	private final Tokenizer tokenizer;
	/**
	 * An instance flag
	 */
	private static boolean instanceFlag = false;
	/**
	 * Private constructor
	 *
	 */
	private TokenizerProvider() {
		// create a unigram resolver. 
		//
		resolver = new UnigramResolver(IConstants.UNIGRAM_MODEL);
		// create a lexical segmenter that use the unigram resolver
		//
		System.out.println("Creating lexical segmenter...");
		segmenter = new Segmenter(resolver);
		System.out.println("Lexical segmenter created.");
		// init the tokenizer
		System.out.print("Initializing tokenizer...");
		tokenizer = new Tokenizer(IConstants.LEXER_SPECIFICATION, segmenter);
		// Do not resolve the ambiguity.
//		tokenizer.setAmbiguitiesResolved(false);
		System.out.println("OK");
	}
	/**
	 * The only method to instantiate a data provider object.
	 * @return a provider object
	 */
	public static TokenizerProvider getInstance() {
		if (!instanceFlag) {
			instanceFlag = true;
			return new TokenizerProvider();
		} else {
			System.err.println("The tokenizer provider has already existed.");
			return null;
		}
	}
	/**
	 * Get the lexical segmenter
	 * @return the lexical segmenter
	 */
	public Segmenter getSegmenter() {
		return segmenter;
	}
	/**
	 * Returns the tokenizer
	 * @return
	 */
	public Tokenizer getTokenizer() {
		return tokenizer;
	}
	/**
	 * Dispose the data provider
	 *
	 */
	public void dispose() {
		// dispose the tokenizer
		// this will dispose the lexical tokenizer and the automata
		tokenizer.dispose();
	}
}
