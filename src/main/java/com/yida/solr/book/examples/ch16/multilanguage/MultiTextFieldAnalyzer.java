package com.yida.solr.book.examples.ch16.multilanguage;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.miscellaneous.RemoveDuplicatesTokenFilter;
import org.apache.solr.schema.IndexSchema;


public class MultiTextFieldAnalyzer extends Analyzer {

protected IndexSchema indexSchema;
	
public final MultiTextFieldSettings Settings;

	public MultiTextFieldAnalyzer(IndexSchema indexSchema, MultiTextFieldSettings settings) {
		super(Analyzer.PER_FIELD_REUSE_STRATEGY);
		this.Settings = settings;
		this.indexSchema = indexSchema;
	}

	protected TokenStreamComponents createComponents(String fieldName) {
		MultiTextFieldTokenizer multiTokenizer = new MultiTextFieldTokenizer(
				indexSchema, fieldName, Settings);

		Tokenizer source = multiTokenizer;
		TokenStream result = multiTokenizer;
		if (Settings.removeDuplicates){
			result = new RemoveDuplicatesTokenFilter(multiTokenizer);
		}
		return new TokenStreamComponents(source, result);
	}
}
