import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class RegularExpressionParser_Test {

  @Test
  public void patternSynax() { //Tests the most basic pattern
    assertEquals(RegularExpressionParser.testPatternSyntax("abc"), true);
    assertEquals(RegularExpressionParser.testPatternSyntax(" abc "), true);
    assertEquals(RegularExpressionParser.testPatternSyntax("(abc)"), true);
    assertEquals(RegularExpressionParser.testPatternSyntax("(abc)|efg"), true);
    assertEquals(RegularExpressionParser.testPatternSyntax("(abc)|(efg)"), true);
    assertEquals(RegularExpressionParser.testPatternSyntax("(abc)*cf+|efg"), true);
    assertEquals(RegularExpressionParser.testPatternSyntax("(ab|c)|g(e|f)g"), true);
    assertEquals(RegularExpressionParser.testPatternSyntax("a*b*c*|e+f+g+"), true);
    assertEquals(RegularExpressionParser.testPatternSyntax("a*(bc+|de*f*)gh*i+|jk(l)+m*n"), true);
  }

  @Test
  public void generateAlphabet() { //Tests the most basic pattern
    ArrayList<String> alphabet1 = new ArrayList<String>(Arrays.asList("a","b","c"));
    assertEquals(RegularExpressionParser.defineAlphabet("abc"), alphabet1);
    assertEquals(RegularExpressionParser.defineAlphabet("(abc)"), alphabet1);
    ArrayList<String> alphabet2 = new ArrayList<String>(Arrays.asList("a","b","c","d","e"));
    assertEquals(RegularExpressionParser.defineAlphabet("(abc|de)"), alphabet2);
    ArrayList<String> alphabet3 = new ArrayList<String>(Arrays.asList("a","b","c","d","e","f","g","h","i","j","k"));
    assertEquals(RegularExpressionParser.defineAlphabet("(abc|de)*f|gh(i+j)k"), alphabet3);
    ArrayList<String> alphabet4 = new ArrayList<String>(Arrays.asList("a","b","c","d","e","f","g","h","i","j","k","l","m","n"));
     assertEquals(RegularExpressionParser.defineAlphabet("a*(bc+|de*f*)gh*i+|jk(l)+m*n"), alphabet4);
    ArrayList<String> alphabet5 = new ArrayList<String>(Arrays.asList("1","2","3"));
    assertEquals(RegularExpressionParser.defineAlphabet("123"), alphabet5);
    ArrayList<String> alphabet6 = new ArrayList<String>(Arrays.asList("6","v", "g", "8"));
    assertEquals(RegularExpressionParser.defineAlphabet("66*v+(g*|8)"), alphabet6);
   
  }

  @Test
  public void basic() { //Tests the most basic pattern
    assertEquals(RegularExpressionParser.match("abc","abc"), true);
    assertEquals(RegularExpressionParser.match("abc"," abc"), false);
    assertEquals(RegularExpressionParser.match("abc","abc "), false);
    assertEquals(RegularExpressionParser.match("abc","ab"), false);
  }

  @Test
  public void basicBracket() { //Tests basic bracket usage
    assertEquals(RegularExpressionParser.match("(abc)","abc"), true);
    assertEquals(RegularExpressionParser.match("(abc)","(abc)"), false);
    assertEquals(RegularExpressionParser.match("(abc)"," abc"), false);
    assertEquals(RegularExpressionParser.match("(abc)","abc "), false);
    assertEquals(RegularExpressionParser.match("(abc)","ab"), false);
  }

  @Test
  public void basicAlternative() { //Tests basic alternative paths
    assertEquals(RegularExpressionParser.match("a|ab|abc","a"), true);
    assertEquals(RegularExpressionParser.match("a|ab|abc","ab"), true);
    assertEquals(RegularExpressionParser.match("a|ab|abc","abc"), true);
    assertEquals(RegularExpressionParser.match("a|ab|abc","aa"), false);
    assertEquals(RegularExpressionParser.match("a|ab|abc","bc"), false);
    assertEquals(RegularExpressionParser.match("a|ab|abc","abcd"), false);
    assertEquals(RegularExpressionParser.match("a|ab|abc"," abc"), false);
    assertEquals(RegularExpressionParser.match("a|ab|abc","abc "), false);
  }
  
  @Test
  public void bracketAlternative() { //Tests basic bracket alternative paths
    assertEquals(RegularExpressionParser.match("(a)|(b)|(c)","a"), true);
    assertEquals(RegularExpressionParser.match("(a)|(b)|(c)","b"), true);
    assertEquals(RegularExpressionParser.match("(a)|(b)|(c)","c"), true);
    assertEquals(RegularExpressionParser.match("(a)|(b)|(c)","(a)"), false);
    assertEquals(RegularExpressionParser.match("(a)|(b)|(c)","(b)"), false);
    assertEquals(RegularExpressionParser.match("(a)|(b)|(c)","(c)"), false);
    assertEquals(RegularExpressionParser.match("(a)|(b)|(c)","d"), false);
    assertEquals(RegularExpressionParser.match("(a)|(b)|(c)","ab"), false);
    assertEquals(RegularExpressionParser.match("(a)|(b)|(c)","ac"), false);
    assertEquals(RegularExpressionParser.match("(a)|(b)|(c)","bc"), false);
    assertEquals(RegularExpressionParser.match("(a)|(b)|(c)","ca"), false);
    assertEquals(RegularExpressionParser.match("(a)|(b)|(c)"," a"), false);
    assertEquals(RegularExpressionParser.match("(a)|(b)|(c)","a "), false);
    assertEquals(RegularExpressionParser.match("(a)|(b)|(c)"," c"), false);
    assertEquals(RegularExpressionParser.match("(a)|(b)|(c)","c "), false);

    assertEquals(RegularExpressionParser.match("(abc)|(abcd)|(abcde)","abc"), true);
    assertEquals(RegularExpressionParser.match("(abc)|(abcd)|(abcde)","abcd"), true);
    assertEquals(RegularExpressionParser.match("(abc)|(abcd)|(abcde)","abcde"), true);
    assertEquals(RegularExpressionParser.match("(abc)|(abcd)|(abcde)",""), false);
    assertEquals(RegularExpressionParser.match("(abc)|(abcd)|(abcde)"," abcde"), false);
    assertEquals(RegularExpressionParser.match("(abc)|(abcd)|(abcde)","abcde "), false);
    assertEquals(RegularExpressionParser.match("(abc)|(abcd)|(abcde)","abcdef"), false);
    assertEquals(RegularExpressionParser.match("(abc)|(abcd)|(abcde)"," "), false);
  }

  @Test
  public void complexBracketAlternative() { //Tests complex alternative paths
    assertEquals(RegularExpressionParser.match("a(b|c)d|e(f|gh)i","abd"), true);
    assertEquals(RegularExpressionParser.match("a(b|c)d|e(f|gh)i","acd"), true);
    assertEquals(RegularExpressionParser.match("a(b|c)d|e(f|gh)i","efi"), true);
    assertEquals(RegularExpressionParser.match("a(b|c)d|e(f|gh)i","eghi"), true);
    assertEquals(RegularExpressionParser.match("a(b|c)d|e(f|gh)i","ad"), false);
    assertEquals(RegularExpressionParser.match("a(b|c)d|e(f|gh)i","ei"), false);
    assertEquals(RegularExpressionParser.match("a(b|c)d|e(f|gh)i"," "), false);
    assertEquals(RegularExpressionParser.match("a(b|c)d|e(f|gh)i","a(b|c)d|e(f|gh)i"), false);
    assertEquals(RegularExpressionParser.match("a(b|c)d|e(f|gh)i"," "), false);
    assertEquals(RegularExpressionParser.match("a(b|c)d|e(f|gh)i"," acd"), false);
    assertEquals(RegularExpressionParser.match("a(b|c)d|e(f|gh)i","acd "), false);
    assertEquals(RegularExpressionParser.match("a(b|c)d|e(f|gh)i"," eghi"), false);
    assertEquals(RegularExpressionParser.match("a(b|c)d|e(f|gh)i","eghi "), false);
  }

  @Test
  public void basicPlus() { //Tests basic plus
    assertEquals(RegularExpressionParser.match("a+","a"), true);
    assertEquals(RegularExpressionParser.match("a+","aa"), true);
    assertEquals(RegularExpressionParser.match("a+","aaaaaaaaaa"), true);
    assertEquals(RegularExpressionParser.match("a+"," a"), false);
    assertEquals(RegularExpressionParser.match("a+","a "), false);
    assertEquals(RegularExpressionParser.match("a+"," "), false);
    assertEquals(RegularExpressionParser.match("a+","aab"), false);
    assertEquals(RegularExpressionParser.match("a+","b"), false);
    assertEquals(RegularExpressionParser.match("a+","ccc"), false);
  }

  @Test
  public void plusBracket() { //Tests plus bracket
    assertEquals(RegularExpressionParser.match("(a)+","a"), true);
    assertEquals(RegularExpressionParser.match("(a)+","aa"), true);
    assertEquals(RegularExpressionParser.match("(a)+","aaaaaaaaaa"), true);
    assertEquals(RegularExpressionParser.match("(a)+"," a"), false);
    assertEquals(RegularExpressionParser.match("(a)+","a "), false);
    assertEquals(RegularExpressionParser.match("(a)+"," "), false);
    assertEquals(RegularExpressionParser.match("(a)+","abb"), false);
    assertEquals(RegularExpressionParser.match("(a)+","b"), false);
    assertEquals(RegularExpressionParser.match("(a)+","ccc"), false);

    assertEquals(RegularExpressionParser.match("(a+)","a"), true);
    assertEquals(RegularExpressionParser.match("(a+)"," a"), false);
    assertEquals(RegularExpressionParser.match("(a+)","a "), false);
    assertEquals(RegularExpressionParser.match("(a+)","aab"), false);
    assertEquals(RegularExpressionParser.match("(a+)","b"), false);

    assertEquals(RegularExpressionParser.match("(abc)+","abc"), true);
    assertEquals(RegularExpressionParser.match("(abc)+","abcabc"), true);
    assertEquals(RegularExpressionParser.match("(abc)+","abcabcabcabcabcabc"), true);
    assertEquals(RegularExpressionParser.match("(abc)+"," abc"), false);
    assertEquals(RegularExpressionParser.match("(abc)+","abc "), false);
    assertEquals(RegularExpressionParser.match("(abc)+"," "), false);
    assertEquals(RegularExpressionParser.match("(abc)+","(abc)"), false);
    assertEquals(RegularExpressionParser.match("(abc)+","abca"), false);
    assertEquals(RegularExpressionParser.match("(abc)+","abcab"), false);
    assertEquals(RegularExpressionParser.match("(abc)+","ab"), false);
    assertEquals(RegularExpressionParser.match("(abc)+","def"), false);
    


  }

  @Test
  public void plusAlternative() { //Tests plus alternative paths
    assertEquals(RegularExpressionParser.match("a+|b+|c+","a"), true);
    assertEquals(RegularExpressionParser.match("a+|b+|c+","b"), true);
    assertEquals(RegularExpressionParser.match("a+|b+|c+","c"), true);
    assertEquals(RegularExpressionParser.match("a+|b+|c+","aa"), true);
    assertEquals(RegularExpressionParser.match("a+|b+|c+","bb"), true);
    assertEquals(RegularExpressionParser.match("a+|b+|c+","cc"), true);
    assertEquals(RegularExpressionParser.match("a+|b+|c+","aaaaaaaa"), true);
    assertEquals(RegularExpressionParser.match("a+|b+|c+","bbbbbbbb"), true);
    assertEquals(RegularExpressionParser.match("a+|b+|c+","cccccccc"), true);
    assertEquals(RegularExpressionParser.match("a+|b+|c+"," "), false);
    assertEquals(RegularExpressionParser.match("a+|b+|c+","abc"), false);
    assertEquals(RegularExpressionParser.match("a+|b+|c+","aaab"), false);
    assertEquals(RegularExpressionParser.match("a+|b+|c+","bbbbc"), false);
  }

  @Test
  public void plusBracketAlternative() { //Test plus bracket alternative
    assertEquals(RegularExpressionParser.match("(a+|b+)|c+","a"), true);
    assertEquals(RegularExpressionParser.match("(a+|b+)|c+","aa"), true);
    assertEquals(RegularExpressionParser.match("(a+|b+)|c+","aaaaaaaa"), true);
    assertEquals(RegularExpressionParser.match("(a+|b+)|c+","b"), true);
    assertEquals(RegularExpressionParser.match("(a+|b+)|c+","bb"), true);
    assertEquals(RegularExpressionParser.match("(a+|b+)|c+","bbbbbbbb"), true);
    assertEquals(RegularExpressionParser.match("(a+|b+)|c+","c"), true);
    assertEquals(RegularExpressionParser.match("(a+|b+)|c+","cc"), true);
    assertEquals(RegularExpressionParser.match("(a+|b+)|c+"," "), false);
    assertEquals(RegularExpressionParser.match("(a+|b+)|c+"," a"), false);
    assertEquals(RegularExpressionParser.match("(a+|b+)|c+","a "), false);
    assertEquals(RegularExpressionParser.match("(a+|b+)|c+"," b"), false);
    assertEquals(RegularExpressionParser.match("(a+|b+)|c+","b "), false);
    assertEquals(RegularExpressionParser.match("(a+|b+)|c+"," c"), false);
    assertEquals(RegularExpressionParser.match("(a+|b+)|c+","c "), false);
    assertEquals(RegularExpressionParser.match("(a+|b+)|c+","aaaaaaab"), false);
    assertEquals(RegularExpressionParser.match("(a+|b+)|c+","bbbbbbba"), false);
    assertEquals(RegularExpressionParser.match("(a+|b+)|c+","aaaacccc"), false);
  }

  @Test
  public void complexPlusBracketAlternative() { //Test complex plus bracket alternative
    assertEquals(RegularExpressionParser.match("a(b+|c)+d+|e+(f|g+h)+i","abd"), true);
    assertEquals(RegularExpressionParser.match("a(b+|c)+d+|e+(f|g+h)+i","acd"), true);
    assertEquals(RegularExpressionParser.match("a(b+|c)+d+|e+(f|g+h)+i","efi"), true);
    assertEquals(RegularExpressionParser.match("a(b+|c)+d+|e+(f|g+h)+i","eghi"), true);
    assertEquals(RegularExpressionParser.match("a(b+|c)+d+|e+(f|g+h)+i","abbbccccdd"), true);
    assertEquals(RegularExpressionParser.match("a(b+|c)+d+|e+(f|g+h)+i","acbcbd"), true);
    assertEquals(RegularExpressionParser.match("a(b+|c)+d+|e+(f|g+h)+i","eeefi"), true);
    assertEquals(RegularExpressionParser.match("a(b+|c)+d+|e+(f|g+h)+i","efghffi"), true);
    assertEquals(RegularExpressionParser.match("a(b+|c)+d+|e+(f|g+h)+i","eggghgghghi"), true);
    assertEquals(RegularExpressionParser.match("a(b+|c)+d+|e+(f|g+h)+i","eghfggghgghffi"), true);
    assertEquals(RegularExpressionParser.match("a(b+|c)+d+|e+(f|g+h)+i","abbb"), false);
    assertEquals(RegularExpressionParser.match("a(b+|c)+d+|e+(f|g+h)+i","ei"), false);
    assertEquals(RegularExpressionParser.match("a(b+|c)+d+|e+(f|g+h)+i","abcad"), false);
  }

  @Test
  public void basicAsterisk() { //Test basic asterisk
    assertEquals(RegularExpressionParser.match("a*","a"), true);
    assertEquals(RegularExpressionParser.match("a*","aa"), true);
    assertEquals(RegularExpressionParser.match("a*","aaaaaaaa"), true);
    assertEquals(RegularExpressionParser.match("a*",""), true);
    assertEquals(RegularExpressionParser.match("a*"," a"), false);
    assertEquals(RegularExpressionParser.match("a*","a "), false);
    assertEquals(RegularExpressionParser.match("a*"," "), false);
    assertEquals(RegularExpressionParser.match("a*","a*"), false);
    assertEquals(RegularExpressionParser.match("a*","ab"), false);
    assertEquals(RegularExpressionParser.match("a*","b"), false);

    assertEquals(RegularExpressionParser.match("a*a*a*","a"), true);
    assertEquals(RegularExpressionParser.match("a*a*a*","aa"), true);
    assertEquals(RegularExpressionParser.match("a*a*a*","aaa"), true);
    assertEquals(RegularExpressionParser.match("a*a*a*","aaaaaaaa"), true);
    assertEquals(RegularExpressionParser.match("a*a*a*",""), true);
    assertEquals(RegularExpressionParser.match("a*a*a*"," a"), false);
    assertEquals(RegularExpressionParser.match("a*a*a*","a "), false);
    assertEquals(RegularExpressionParser.match("a*a*a*","aaab"), false);
    assertEquals(RegularExpressionParser.match("a*a*a*","aaaaaaab"), false);
    assertEquals(RegularExpressionParser.match("a*a*a*","a*a*a*"), false);
  }

  @Test
  public void asteriskBracket() { //Test asterisk bracket
    assertEquals(RegularExpressionParser.match("(a)*","a"), true);
    assertEquals(RegularExpressionParser.match("(a)*","aa"), true);
    assertEquals(RegularExpressionParser.match("(a)*","aaaaaa"), true);
    assertEquals(RegularExpressionParser.match("(a)*",""), true);
    assertEquals(RegularExpressionParser.match("(a)*"," a"), false);
    assertEquals(RegularExpressionParser.match("(a)*","a "), false);
    assertEquals(RegularExpressionParser.match("(a)*"," "), false);
    assertEquals(RegularExpressionParser.match("(a)*","b"), false);
    assertEquals(RegularExpressionParser.match("(a)*","aab"), false);
    assertEquals(RegularExpressionParser.match("(a)*","ba"), false);

    assertEquals(RegularExpressionParser.match("(abc)*","abc"), true);
    assertEquals(RegularExpressionParser.match("(abc)*","abcabc"), true);
    assertEquals(RegularExpressionParser.match("(abc)*","abcabcabcabcabcabcabcabc"), true);
    assertEquals(RegularExpressionParser.match("(abc)*",""), true);
    assertEquals(RegularExpressionParser.match("(abc)*"," abc"), false);
    assertEquals(RegularExpressionParser.match("(abc)*","abc "), false);
    assertEquals(RegularExpressionParser.match("(abc)*"," "), false);
    assertEquals(RegularExpressionParser.match("(abc)*","abcd"), false);
    assertEquals(RegularExpressionParser.match("(abc)*","ab"), false);
    assertEquals(RegularExpressionParser.match("(abc)*","(abc)*"), false);
    
    assertEquals(RegularExpressionParser.match("(abc*)","abc"), true);
    assertEquals(RegularExpressionParser.match("(abc*)","abcc"), true);
    assertEquals(RegularExpressionParser.match("(abc*)","abccccccc"), true);
    assertEquals(RegularExpressionParser.match("(abc*)","ab"), true);
    assertEquals(RegularExpressionParser.match("(abc*)",""), false);
    assertEquals(RegularExpressionParser.match("(abc*)","abcabc"), false);
    assertEquals(RegularExpressionParser.match("(abc*)"," abc"), false);
    assertEquals(RegularExpressionParser.match("(abc*)","abc "), false);
    assertEquals(RegularExpressionParser.match("(abc*)","abcd"), false);
    assertEquals(RegularExpressionParser.match("(abc*)","(abc*)"), false);
  }

  @Test
  public void asteriskAlternative() { //Test asterisk alternative
    assertEquals(RegularExpressionParser.match("a*|b*|c*","a"), true);
    assertEquals(RegularExpressionParser.match("a*|b*|c*","aa"), true);
    assertEquals(RegularExpressionParser.match("a*|b*|c*","aaaaaaaa"), true);
    assertEquals(RegularExpressionParser.match("a*|b*|c*","b"), true);
    assertEquals(RegularExpressionParser.match("a*|b*|c*","bb"), true);
    assertEquals(RegularExpressionParser.match("a*|b*|c*","bbbbbbb"), true);
    assertEquals(RegularExpressionParser.match("a*|b*|c*","c"), true);
    assertEquals(RegularExpressionParser.match("a*|b*|c*","cc"), true);
    assertEquals(RegularExpressionParser.match("a*|b*|c*","ccccccc"), true);
    assertEquals(RegularExpressionParser.match("a*|b*|c*",""), true);
    assertEquals(RegularExpressionParser.match("a*|b*|c*"," "), false);
    assertEquals(RegularExpressionParser.match("a*|b*|c*","abc"), false);
    assertEquals(RegularExpressionParser.match("a*|b*|c*","a*|b*|c*"), false);
    assertEquals(RegularExpressionParser.match("a*|b*|c*","d"), false);
    assertEquals(RegularExpressionParser.match("a*|b*|c*"," a"), false);
    assertEquals(RegularExpressionParser.match("a*|b*|c*","a "), false);
  }

  @Test
  public void asteriskBracketAlternative() { //Test asterisk bracket alternative
    assertEquals(RegularExpressionParser.match("(a)*|(b)*|(c)*","a"), true);
    assertEquals(RegularExpressionParser.match("(a)*|(b)*|(c)*","aa"), true);
    assertEquals(RegularExpressionParser.match("(a)*|(b)*|(c)*","aaaaaaaa"), true);
    assertEquals(RegularExpressionParser.match("(a)*|(b)*|(c)*","b"), true);
    assertEquals(RegularExpressionParser.match("(a)*|(b)*|(c)*","bb"), true);
    assertEquals(RegularExpressionParser.match("(a)*|(b)*|(c)*","bbbbbbb"), true);
    assertEquals(RegularExpressionParser.match("(a)*|(b)*|(c)*","c"), true);
    assertEquals(RegularExpressionParser.match("(a)*|(b)*|(c)*","cc"), true);
    assertEquals(RegularExpressionParser.match("(a)*|(b)*|(c)*","ccccccc"), true);
    assertEquals(RegularExpressionParser.match("(a)*|(b)*|(c)*",""), true);
    assertEquals(RegularExpressionParser.match("(a)*|(b)*|(c)*","aaab"), false);
    assertEquals(RegularExpressionParser.match("(a)*|(b)*|(c)*","bbbc"), false);
    assertEquals(RegularExpressionParser.match("(a)*|(b)*|(c)*","cccd"), false);
    assertEquals(RegularExpressionParser.match("(a)*|(b)*|(c)*","abcd"), false);
    assertEquals(RegularExpressionParser.match("(a)*|(b)*|(c)*","(a)*|(b)*|(c)*"), false);

    assertEquals(RegularExpressionParser.match("(abc)*|(abcd)*|(abcde)*","abc"), true);
    assertEquals(RegularExpressionParser.match("(abc)*|(abcd)*|(abcde)*","abcd"), true);
    assertEquals(RegularExpressionParser.match("(abc)*|(abcd)*|(abcde)*","abcde"), true);
    assertEquals(RegularExpressionParser.match("(abc)*|(abcd)*|(abcde)*","abcabcabc"), true);
    assertEquals(RegularExpressionParser.match("(abc)*|(abcd)*|(abcde)*","abcdabcdabcd"), true);
    assertEquals(RegularExpressionParser.match("(abc)*|(abcd)*|(abcde)*","abcdeabcdeabcde"), true);
    assertEquals(RegularExpressionParser.match("(abc)*|(abcd)*|(abcde)*",""), true);
    assertEquals(RegularExpressionParser.match("(abc)*|(abcd)*|(abcde)*"," abc"), false);
    assertEquals(RegularExpressionParser.match("(abc)*|(abcd)*|(abcde)*","abc "), false);
    assertEquals(RegularExpressionParser.match("(abc)*|(abcd)*|(abcde)*","abcdef"), false);
    assertEquals(RegularExpressionParser.match("(abc)*|(abcd)*|(abcde)*","abcabcabcdef"), false);
  }

  @Test
  public void gradescopeTestCases() { //Some tests that were used on the Gradescope marking
    assertEquals(RegularExpressionParser.match("a+b*|c","ab"), true);
    assertEquals(RegularExpressionParser.match("a+b*|c","c"), true);
    assertEquals(RegularExpressionParser.match("a+b*|c","abbbbbbbb"), true);
    assertEquals(RegularExpressionParser.match("a+b*|c","a"), true);
    assertEquals(RegularExpressionParser.match("a+b*|c","aaaaaaaa"), true);
    assertEquals(RegularExpressionParser.match("a+b*|c","aaaaabbbbb"), true);
    assertEquals(RegularExpressionParser.match("a+b*|c","abc"), false);
    assertEquals(RegularExpressionParser.match("a+b*|c","cc"), false);
    assertEquals(RegularExpressionParser.match("a+b*|c",""), false);
    assertEquals(RegularExpressionParser.match("a+b*|c"," a"), false);
    assertEquals(RegularExpressionParser.match("a+b*|c","a "), false);

    assertEquals(RegularExpressionParser.match("(a|b)*c","ac"), true);
    assertEquals(RegularExpressionParser.match("(a|b)*c","bc"), true);
    assertEquals(RegularExpressionParser.match("(a|b)*c","c"), true);
    assertEquals(RegularExpressionParser.match("(a|b)*c","aaaac"), true);
    assertEquals(RegularExpressionParser.match("(a|b)*c","bbbbc"), true);
    assertEquals(RegularExpressionParser.match("(a|b)*c","abc"), true);
    assertEquals(RegularExpressionParser.match("(a|b)*c","bac"), true);
    assertEquals(RegularExpressionParser.match("(a|b)*c","ababbabaabc"), true);
    assertEquals(RegularExpressionParser.match("(a|b)*c",""), false);
    assertEquals(RegularExpressionParser.match("(a|b)*c"," ac"), false);
    assertEquals(RegularExpressionParser.match("(a|b)*c","bc "), false);
    assertEquals(RegularExpressionParser.match("(a|b)*c"," "), false);
    assertEquals(RegularExpressionParser.match("(a|b)*c","(a|b)*c"), false);
    assertEquals(RegularExpressionParser.match("(a|b)*c","a"), false);
    assertEquals(RegularExpressionParser.match("(a|b)*c","b"), false);
    assertEquals(RegularExpressionParser.match("(a|b)*c","abbaaaba"), false);

    assertEquals(RegularExpressionParser.match("(x|y+z)*|z","x"), true);
    assertEquals(RegularExpressionParser.match("(x|y+z)*|z","yz"), true);
    assertEquals(RegularExpressionParser.match("(x|y+z)*|z","z"), true);
    assertEquals(RegularExpressionParser.match("(x|y+z)*|z",""), true);
    assertEquals(RegularExpressionParser.match("(x|y+z)*|z","xyzxyz"), true);
    assertEquals(RegularExpressionParser.match("(x|y+z)*|z","xxxxxx"), true);
    assertEquals(RegularExpressionParser.match("(x|y+z)*|z","yzyzyzyz"), true);
    assertEquals(RegularExpressionParser.match("(x|y+z)*|z","yyyyz"), true);
    assertEquals(RegularExpressionParser.match("(x|y+z)*|z","yyyyyzyyz"), true);
    assertEquals(RegularExpressionParser.match("(x|y+z)*|z","xyzxyzxyz"), true);
    assertEquals(RegularExpressionParser.match("(x|y+z)*|z","yzxxxyzyyzx"), true);
    assertEquals(RegularExpressionParser.match("(x|y+z)*|z","y"), false);
    assertEquals(RegularExpressionParser.match("(x|y+z)*|z","yyyyy"), false);
    assertEquals(RegularExpressionParser.match("(x|y+z)*|z","yzyyzyzy"), false);
    assertEquals(RegularExpressionParser.match("(x|y+z)*|z","zzz"), false);
    assertEquals(RegularExpressionParser.match("(x|y+z)*|z","xz"), false);
    assertEquals(RegularExpressionParser.match("(x|y+z)*|z"," x"), false);
    assertEquals(RegularExpressionParser.match("(x|y+z)*|z","x "), false);
    assertEquals(RegularExpressionParser.match("(x|y+z)*|z","xxxyyyzyzxy"), false);
  }

}
