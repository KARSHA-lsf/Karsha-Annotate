package org.karsha.tokenize;

/**
 * Copyright (C) 2012, Lanka Software Foundation.
 *
 * Date Author Changes 
 * 2010 Shanchan Wu Created (Lucene 2.9 compatible)
 * June 1 2012 Kasun Perera Modified the program as compatible to Lucene 3.5
 *
 */


import java.io.Reader;
import java.io.StringReader;
import java.util.Iterator;

import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.standard.StandardFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.apache.lucene.util.Version;

/**
 * DefaultTokenizer filters punctuation and stop words (TERRIER_STOP_WORDS).
 */
public class DefaultTokenizer implements Tokenizer {

    private static boolean replaceInvalidAcronym = false;
    private static int maxTokenLength = 255;
    @SuppressWarnings("unused")
    /*
     * Lucene Default Stop words
     */
    private static final String[] LUCENE_STOP_WORDS = {"a", "an", "and", "are", "as", "at", "be",
        "but", "by", "for", "if", "in", "into", "is", "it", "no", "not", "of", "on", "or",
        "such", "that", "the", "their", "then", "there", "these", "they", "this", "to", "was",
        "will", "with"};
    /*
     * Custom Stop words for english language
     */
    private static final String[] TERRIER_STOP_WORDS = {"x", "y", "your", "yours", "yourself",
        "yourselves", "you", "yond", "yonder", "yon", "ye", "yet", "z", "zillion", "j", "u",
        "umpteen", "usually", "us", "username", "uponed", "upons", "uponing", "upon", "ups",
        "upping", "upped", "up", "unto", "until", "unless", "unlike", "unliker", "unlikest",
        "under", "underneath", "use", "used", "usedest", "r", "rath", "rather", "rathest",
        "rathe", "re", "relate", "related", "relatively", "regarding", "really", "res",
        "respecting", "respectively", "q", "quite", "que", "qua", "n", "neither", "neaths",
        "neath", "nethe", "nethermost", "necessary", "necessariest", "necessarier", "never",
        "nevertheless", "nigh", "nighest", "nigher", "nine", "noone", "nobody", "nobodies",
        "nowhere", "nowheres", "no", "noes", "nor", "nos", "no-one", "none", "not",
        "notwithstanding", "nothings", "nothing", "nathless", "natheless", "t", "ten", "tills",
        "till", "tilled", "tilling", "to", "towards", "toward", "towardest", "towarder",
        "together", "too", "thy", "thyself", "thus", "than", "that", "those", "thou", "though",
        "thous", "thouses", "thoroughest", "thorougher", "thorough", "thoroughly", "thru",
        "thruer", "thruest", "thro", "through", "throughout", "throughest", "througher",
        "thine", "this", "thises", "they", "thee", "the", "then", "thence", "thenest",
        "thener", "them", "themselves", "these", "therer", "there", "thereby", "therest",
        "thereafter", "therein", "thereupon", "therefore", "their", "theirs", "thing",
        "things", "three", "two", "o", "oh", "owt", "owning", "owned", "own", "owns", "others",
        "other", "otherwise", "otherwisest", "otherwiser", "of", "often", "oftener",
        "oftenest", "off", "offs", "offest", "one", "ought", "oughts", "our", "ours",
        "ourselves", "ourself", "out", "outest", "outed", "outwith", "outs", "outside", "over",
        "overallest", "overaller", "overalls", "overall", "overs", "or", "orer", "orest", "on",
        "oneself", "onest", "ons", "onto", "a", "atween", "at", "athwart", "atop", "afore",
        "afterward", "afterwards", "after", "afterest", "afterer", "ain", "an", "any",
        "anything", "anybody", "anyone", "anyhow", "anywhere", "anent", "anear", "and",
        "andor", "another", "around", "ares", "are", "aest", "aer", "against", "again",
        "accordingly", "abaft", "abafter", "abaftest", "abovest", "above", "abover", "abouter",
        "aboutest", "about", "aid", "amidst", "amid", "among", "amongst", "apartest",
        "aparter", "apart", "appeared", "appears", "appear", "appearing", "appropriating",
        "appropriate", "appropriatest", "appropriates", "appropriater", "appropriated",
        "already", "always", "also", "along", "alongside", "although", "almost", "all",
        "allest", "aller", "allyou", "alls", "albeit", "awfully", "as", "aside", "asides",
        "aslant", "ases", "astrider", "astride", "astridest", "astraddlest", "astraddler",
        "astraddle", "availablest", "availabler", "available", "aughts", "aught", "vs", "v",
        "variousest", "variouser", "various", "via", "vis-a-vis", "vis-a-viser",
        "vis-a-visest", "viz", "very", "veriest", "verier", "versus", "k", "g", "go", "gone",
        "good", "got", "gotta", "gotten", "get", "gets", "getting", "b", "by", "byandby",
        "by-and-by", "bist", "both", "but", "buts", "be", "beyond", "because", "became",
        "becomes", "become", "becoming", "becomings", "becominger", "becomingest", "behind",
        "behinds", "before", "beforehand", "beforehandest", "beforehander", "bettered",
        "betters", "better", "bettering", "betwixt", "between", "beneath", "been", "below",
        "besides", "beside", "m", "my", "myself", "mucher", "muchest", "much", "must", "musts",
        "musths", "musth", "main", "make", "mayest", "many", "mauger", "maugre", "me",
        "meanwhiles", "meanwhile", "mostly", "most", "moreover", "more", "might", "mights",
        "midst", "midsts", "h", "huh", "humph", "he", "hers", "herself", "her", "hereby",
        "herein", "hereafters", "hereafter", "hereupon", "hence", "hadst", "had", "having",
        "haves", "have", "has", "hast", "hardly", "hae", "hath", "him", "himself", "hither",
        "hitherest", "hitherer", "his", "how-do-you-do", "however", "how", "howbeit",
        "howdoyoudo", "hoos", "hoo", "w", "woulded", "woulding", "would", "woulds", "was",
        "wast", "we", "wert", "were", "with", "withal", "without", "within", "why", "what",
        "whatever", "whateverer", "whateverest", "whatsoeverer", "whatsoeverest", "whatsoever",
        "whence", "whencesoever", "whenever", "whensoever", "when", "whenas", "whether",
        "wheen", "whereto", "whereupon", "wherever", "whereon", "whereof", "where", "whereby",
        "wherewithal", "wherewith", "whereinto", "wherein", "whereafter", "whereas",
        "wheresoever", "wherefrom", "which", "whichever", "whichsoever", "whilst", "while",
        "whiles", "whithersoever", "whither", "whoever", "whosoever", "whoso", "whose",
        "whomever", "s", "syne", "syn", "shalling", "shall", "shalled", "shalls", "shoulding",
        "should", "shoulded", "shoulds", "she", "sayyid", "sayid", "said", "saider", "saidest",
        "same", "samest", "sames", "samer", "saved", "sans", "sanses", "sanserifs", "sanserif",
        "so", "soer", "soest", "sobeit", "someone", "somebody", "somehow", "some", "somewhere",
        "somewhat", "something", "sometimest", "sometimes", "sometimer", "sometime", "several",
        "severaler", "severalest", "serious", "seriousest", "seriouser", "senza", "send",
        "sent", "seem", "seems", "seemed", "seemingest", "seeminger", "seemings", "seven",
        "summat", "sups", "sup", "supping", "supped", "such", "since", "sine", "sines", "sith",
        "six", "stop", "stopped", "p", "plaintiff", "plenty", "plenties", "please", "pleased",
        "pleases", "per", "perhaps", "particulars", "particularly", "particular",
        "particularest", "particularer", "pro", "providing", "provides", "provided", "provide",
        "probably", "l", "layabout", "layabouts", "latter", "latterest", "latterer",
        "latterly", "latters", "lots", "lotting", "lotted", "lot", "lest", "less", "ie", "ifs",
        "if", "i", "info", "information", "itself", "its", "it", "is", "idem", "idemer",
        "idemest", "immediate", "immediately", "immediatest", "immediater", "in", "inwards",
        "inwardest", "inwarder", "inward", "inasmuch", "into", "instead", "insofar",
        "indicates", "indicated", "indicate", "indicating", "indeed", "inc", "f", "fact",
        "facts", "fs", "figupon", "figupons", "figuponing", "figuponed", "few", "fewer",
        "fewest", "frae", "from", "failing", "failings", "five", "furthers", "furtherer",
        "furthered", "furtherest", "further", "furthering", "furthermore", "fourscore",
        "followthrough", "for", "forwhy", "fornenst", "formerly", "former", "formerer",
        "formerest", "formers", "forbye", "forby", "fore", "forever", "forer", "fores", "four",
        "d", "ddays", "dday", "do", "doing", "doings", "doe", "does", "doth", "downwarder",
        "downwardest", "downward", "downwards", "downs", "done", "doner", "dones", "donest",
        "dos", "dost", "did", "differentest", "differenter", "different", "describing",
        "describe", "describes", "described", "despiting", "despites", "despited", "despite",
        "during", "c", "cum", "circa", "chez", "cer", "certain", "certainest", "certainer",
        "cest", "canst", "cannot", "cant", "cants", "canting", "cantest", "canted", "co",
        "could", "couldst", "comeon", "comeons", "come-ons", "come-on", "concerning",
        "concerninger", "concerningest", "consequently", "considering", "e", "eg", "eight",
        "either", "even", "evens", "evenser", "evensest", "evened", "evenest", "ever",
        "everyone", "everything", "everybody", "everywhere", "every", "ere", "each", "et",
        "etc", "elsewhere", "else", "ex", "excepted", "excepts", "except", "excepting", "exes",
        "enough"};
    private final CharArraySet lu_stop_words;
    private final CharArraySet te_stop_words;

    public DefaultTokenizer() {

        this.lu_stop_words = new CharArraySet(10, false);
 this.te_stop_words= new CharArraySet(10, false);
        for (int i = 0; i < LUCENE_STOP_WORDS.length; i++) {
            lu_stop_words.add(LUCENE_STOP_WORDS[i]);
        }
for (int i = 0; i < TERRIER_STOP_WORDS.length; i++) {
            te_stop_words.add(TERRIER_STOP_WORDS[i]);
        }

    }

    public TokenStream tokenStream(String text) {
        return tokenStream(new StringReader(text));
    }

    public TokenStream tokenStream(Reader reader) {

        //StandardTokenizer tokenStream = new StandardTokenizer(reader, replaceInvalidAcronym);
        StandardTokenizer tokenStream = new StandardTokenizer(Version.LUCENE_35, reader);
        tokenStream.setMaxTokenLength(maxTokenLength);
        TokenStream result = new StandardFilter(tokenStream);

        result = new LowerCaseFilter(result);
        result = new StopFilter(Version.LUCENE_35, result, lu_stop_words);
        result = new StopFilter(Version.LUCENE_35,result, te_stop_words);
        //result = new PorterStemFilter(result);

        return result;
    }

    public String processText(String text) {
        StringBuffer str = new StringBuffer();
        TokenStream stream = tokenStream(new StringReader(text));
        Token token = new Token();

        try {
            
            while(stream.incrementToken()) {
                str.append(stream.getAttribute(TermAttribute.class).term());
                str.append(" ");

            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        return str.toString();
    }

    public static void main(String[] args) {
        String a = "[menu plan monday]I haven&#8217;t done a Menu Plan"
                + " Monday over here yet (I was doing them at my personal "
                + "blog for a while) so I figured I would start doing them "
                + "here since it goes hand in hand with my grocery shopping "
                + "and whatnot.Anyway, this week I Tried to k ";
        String b = new DefaultTokenizer().processText(a);
        System.out.println(b);
    }
}
