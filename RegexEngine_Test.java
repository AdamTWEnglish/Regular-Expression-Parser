import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class RegexEngine_Test {

  @Test
  public void patternSynax() { //Tests the most basic pattern
    assertEquals(RegexEngine.testPatternSyntax("abc"), true);
    assertEquals(RegexEngine.testPatternSyntax(" abc "), true);
    assertEquals(RegexEngine.testPatternSyntax("(abc)"), true);
    assertEquals(RegexEngine.testPatternSyntax("(abc)|efg"), true);
    assertEquals(RegexEngine.testPatternSyntax("(abc)|(efg)"), true);
    assertEquals(RegexEngine.testPatternSyntax("(abc)*cf+|efg"), true);
    assertEquals(RegexEngine.testPatternSyntax("(ab|c)|g(e|f)g"), true);
    assertEquals(RegexEngine.testPatternSyntax("a*b*c*|e+f+g+"), true);
    assertEquals(RegexEngine.testPatternSyntax("a*(bc+|de*f*)gh*i+|jk(l)+m*n"), true);
  }

  @Test
  public void generateAlphabet() { //Tests the most basic pattern
    ArrayList<String> alphabet1 = new ArrayList<String>(Arrays.asList("a","b","c"));
    assertEquals(RegexEngine.defineAlphabet("abc"), alphabet1);
    assertEquals(RegexEngine.defineAlphabet("(abc)"), alphabet1);
    ArrayList<String> alphabet2 = new ArrayList<String>(Arrays.asList("a","b","c","d","e"));
    assertEquals(RegexEngine.defineAlphabet("(abc|de)"), alphabet2);
    ArrayList<String> alphabet3 = new ArrayList<String>(Arrays.asList("a","b","c","d","e","f","g","h","i","j","k"));
    assertEquals(RegexEngine.defineAlphabet("(abc|de)*f|gh(i+j)k"), alphabet3);
    ArrayList<String> alphabet4 = new ArrayList<String>(Arrays.asList("a","b","c","d","e","f","g","h","i","j","k","l","m","n"));
     assertEquals(RegexEngine.defineAlphabet("a*(bc+|de*f*)gh*i+|jk(l)+m*n"), alphabet4);
    ArrayList<String> alphabet5 = new ArrayList<String>(Arrays.asList("1","2","3"));
    assertEquals(RegexEngine.defineAlphabet("123"), alphabet5);
    ArrayList<String> alphabet6 = new ArrayList<String>(Arrays.asList("6","v", "g", "8"));
    assertEquals(RegexEngine.defineAlphabet("66*v+(g*|8)"), alphabet6);
   
  }

  @Test
  public void basic() { //Tests the most basic pattern
    assertEquals(RegexEngine.match("abc","abc"), true);
    assertEquals(RegexEngine.match("abc"," abc"), false);
    assertEquals(RegexEngine.match("abc","abc "), false);
    assertEquals(RegexEngine.match("abc","ab"), false);
  }

  @Test
  public void basicBracket() { //Tests basic bracket usage
    assertEquals(RegexEngine.match("(abc)","abc"), true);
    assertEquals(RegexEngine.match("(abc)","(abc)"), false);
    assertEquals(RegexEngine.match("(abc)"," abc"), false);
    assertEquals(RegexEngine.match("(abc)","abc "), false);
    assertEquals(RegexEngine.match("(abc)","ab"), false);
  }

  @Test
  public void basicAlternative() { //Tests basic alternative paths
    assertEquals(RegexEngine.match("a|ab|abc","a"), true);
    assertEquals(RegexEngine.match("a|ab|abc","ab"), true);
    assertEquals(RegexEngine.match("a|ab|abc","abc"), true);
    assertEquals(RegexEngine.match("a|ab|abc","aa"), false);
    assertEquals(RegexEngine.match("a|ab|abc","bc"), false);
    assertEquals(RegexEngine.match("a|ab|abc","abcd"), false);
    assertEquals(RegexEngine.match("a|ab|abc"," abc"), false);
    assertEquals(RegexEngine.match("a|ab|abc","abc "), false);
  }
  
  @Test
  public void bracketAlternative() { //Tests basic bracket alternative paths
    assertEquals(RegexEngine.match("(a)|(b)|(c)","a"), true);
    assertEquals(RegexEngine.match("(a)|(b)|(c)","b"), true);
    assertEquals(RegexEngine.match("(a)|(b)|(c)","c"), true);
    assertEquals(RegexEngine.match("(a)|(b)|(c)","(a)"), false);
    assertEquals(RegexEngine.match("(a)|(b)|(c)","(b)"), false);
    assertEquals(RegexEngine.match("(a)|(b)|(c)","(c)"), false);
    assertEquals(RegexEngine.match("(a)|(b)|(c)","d"), false);
    assertEquals(RegexEngine.match("(a)|(b)|(c)","ab"), false);
    assertEquals(RegexEngine.match("(a)|(b)|(c)","ac"), false);
    assertEquals(RegexEngine.match("(a)|(b)|(c)","bc"), false);
    assertEquals(RegexEngine.match("(a)|(b)|(c)","ca"), false);
    assertEquals(RegexEngine.match("(a)|(b)|(c)"," a"), false);
    assertEquals(RegexEngine.match("(a)|(b)|(c)","a "), false);
    assertEquals(RegexEngine.match("(a)|(b)|(c)"," c"), false);
    assertEquals(RegexEngine.match("(a)|(b)|(c)","c "), false);

    assertEquals(RegexEngine.match("(abc)|(abcd)|(abcde)","abc"), true);
    assertEquals(RegexEngine.match("(abc)|(abcd)|(abcde)","abcd"), true);
    assertEquals(RegexEngine.match("(abc)|(abcd)|(abcde)","abcde"), true);
    assertEquals(RegexEngine.match("(abc)|(abcd)|(abcde)",""), false);
    assertEquals(RegexEngine.match("(abc)|(abcd)|(abcde)"," abcde"), false);
    assertEquals(RegexEngine.match("(abc)|(abcd)|(abcde)","abcde "), false);
    assertEquals(RegexEngine.match("(abc)|(abcd)|(abcde)","abcdef"), false);
    assertEquals(RegexEngine.match("(abc)|(abcd)|(abcde)"," "), false);
  }

  @Test
  public void complexBracketAlternative() { //Tests complex alternative paths
    assertEquals(RegexEngine.match("a(b|c)d|e(f|gh)i","abd"), true);
    assertEquals(RegexEngine.match("a(b|c)d|e(f|gh)i","acd"), true);
    assertEquals(RegexEngine.match("a(b|c)d|e(f|gh)i","efi"), true);
    assertEquals(RegexEngine.match("a(b|c)d|e(f|gh)i","eghi"), true);
    assertEquals(RegexEngine.match("a(b|c)d|e(f|gh)i","ad"), false);
    assertEquals(RegexEngine.match("a(b|c)d|e(f|gh)i","ei"), false);
    assertEquals(RegexEngine.match("a(b|c)d|e(f|gh)i"," "), false);
    assertEquals(RegexEngine.match("a(b|c)d|e(f|gh)i","a(b|c)d|e(f|gh)i"), false);
    assertEquals(RegexEngine.match("a(b|c)d|e(f|gh)i"," "), false);
    assertEquals(RegexEngine.match("a(b|c)d|e(f|gh)i"," acd"), false);
    assertEquals(RegexEngine.match("a(b|c)d|e(f|gh)i","acd "), false);
    assertEquals(RegexEngine.match("a(b|c)d|e(f|gh)i"," eghi"), false);
    assertEquals(RegexEngine.match("a(b|c)d|e(f|gh)i","eghi "), false);
  }

  @Test
  public void basicPlus() { //Tests basic plus
    assertEquals(RegexEngine.match("a+","a"), true);
    assertEquals(RegexEngine.match("a+","aa"), true);
    assertEquals(RegexEngine.match("a+","aaaaaaaaaa"), true);
    assertEquals(RegexEngine.match("a+"," a"), false);
    assertEquals(RegexEngine.match("a+","a "), false);
    assertEquals(RegexEngine.match("a+"," "), false);
    assertEquals(RegexEngine.match("a+","aab"), false);
    assertEquals(RegexEngine.match("a+","b"), false);
    assertEquals(RegexEngine.match("a+","ccc"), false);
  }

  @Test
  public void plusBracket() { //Tests plus bracket
    assertEquals(RegexEngine.match("(a)+","a"), true);
    assertEquals(RegexEngine.match("(a)+","aa"), true);
    assertEquals(RegexEngine.match("(a)+","aaaaaaaaaa"), true);
    assertEquals(RegexEngine.match("(a)+"," a"), false);
    assertEquals(RegexEngine.match("(a)+","a "), false);
    assertEquals(RegexEngine.match("(a)+"," "), false);
    assertEquals(RegexEngine.match("(a)+","abb"), false);
    assertEquals(RegexEngine.match("(a)+","b"), false);
    assertEquals(RegexEngine.match("(a)+","ccc"), false);

    assertEquals(RegexEngine.match("(a+)","a"), true);
    assertEquals(RegexEngine.match("(a+)"," a"), false);
    assertEquals(RegexEngine.match("(a+)","a "), false);
    assertEquals(RegexEngine.match("(a+)","aab"), false);
    assertEquals(RegexEngine.match("(a+)","b"), false);

    assertEquals(RegexEngine.match("(abc)+","abc"), true);
    assertEquals(RegexEngine.match("(abc)+","abcabc"), true);
    assertEquals(RegexEngine.match("(abc)+","abcabcabcabcabcabc"), true);
    assertEquals(RegexEngine.match("(abc)+"," abc"), false);
    assertEquals(RegexEngine.match("(abc)+","abc "), false);
    assertEquals(RegexEngine.match("(abc)+"," "), false);
    assertEquals(RegexEngine.match("(abc)+","(abc)"), false);
    assertEquals(RegexEngine.match("(abc)+","abca"), false);
    assertEquals(RegexEngine.match("(abc)+","abcab"), false);
    assertEquals(RegexEngine.match("(abc)+","ab"), false);
    assertEquals(RegexEngine.match("(abc)+","def"), false);
    


  }

  @Test
  public void plusAlternative() { //Tests plus alternative paths
    assertEquals(RegexEngine.match("a+|b+|c+","a"), true);
    assertEquals(RegexEngine.match("a+|b+|c+","b"), true);
    assertEquals(RegexEngine.match("a+|b+|c+","c"), true);
    assertEquals(RegexEngine.match("a+|b+|c+","aa"), true);
    assertEquals(RegexEngine.match("a+|b+|c+","bb"), true);
    assertEquals(RegexEngine.match("a+|b+|c+","cc"), true);
    assertEquals(RegexEngine.match("a+|b+|c+","aaaaaaaa"), true);
    assertEquals(RegexEngine.match("a+|b+|c+","bbbbbbbb"), true);
    assertEquals(RegexEngine.match("a+|b+|c+","cccccccc"), true);
    assertEquals(RegexEngine.match("a+|b+|c+"," "), false);
    assertEquals(RegexEngine.match("a+|b+|c+","abc"), false);
    assertEquals(RegexEngine.match("a+|b+|c+","aaab"), false);
    assertEquals(RegexEngine.match("a+|b+|c+","bbbbc"), false);
  }

  @Test
  public void plusBracketAlternative() { //Test plus bracket alternative
    assertEquals(RegexEngine.match("(a+|b+)|c+","a"), true);
    assertEquals(RegexEngine.match("(a+|b+)|c+","aa"), true);
    assertEquals(RegexEngine.match("(a+|b+)|c+","aaaaaaaa"), true);
    assertEquals(RegexEngine.match("(a+|b+)|c+","b"), true);
    assertEquals(RegexEngine.match("(a+|b+)|c+","bb"), true);
    assertEquals(RegexEngine.match("(a+|b+)|c+","bbbbbbbb"), true);
    assertEquals(RegexEngine.match("(a+|b+)|c+","c"), true);
    assertEquals(RegexEngine.match("(a+|b+)|c+","cc"), true);
    assertEquals(RegexEngine.match("(a+|b+)|c+"," "), false);
    assertEquals(RegexEngine.match("(a+|b+)|c+"," a"), false);
    assertEquals(RegexEngine.match("(a+|b+)|c+","a "), false);
    assertEquals(RegexEngine.match("(a+|b+)|c+"," b"), false);
    assertEquals(RegexEngine.match("(a+|b+)|c+","b "), false);
    assertEquals(RegexEngine.match("(a+|b+)|c+"," c"), false);
    assertEquals(RegexEngine.match("(a+|b+)|c+","c "), false);
    assertEquals(RegexEngine.match("(a+|b+)|c+","aaaaaaab"), false);
    assertEquals(RegexEngine.match("(a+|b+)|c+","bbbbbbba"), false);
    assertEquals(RegexEngine.match("(a+|b+)|c+","aaaacccc"), false);
  }

  @Test
  public void complexPlusBracketAlternative() { //Test complex plus bracket alternative
    assertEquals(RegexEngine.match("a(b+|c)+d+|e+(f|g+h)+i","abd"), true);
    assertEquals(RegexEngine.match("a(b+|c)+d+|e+(f|g+h)+i","acd"), true);
    assertEquals(RegexEngine.match("a(b+|c)+d+|e+(f|g+h)+i","efi"), true);
    assertEquals(RegexEngine.match("a(b+|c)+d+|e+(f|g+h)+i","eghi"), true);
    assertEquals(RegexEngine.match("a(b+|c)+d+|e+(f|g+h)+i","abbbccccdd"), true);
    assertEquals(RegexEngine.match("a(b+|c)+d+|e+(f|g+h)+i","acbcbd"), true);
    assertEquals(RegexEngine.match("a(b+|c)+d+|e+(f|g+h)+i","eeefi"), true);
    assertEquals(RegexEngine.match("a(b+|c)+d+|e+(f|g+h)+i","efghffi"), true);
    assertEquals(RegexEngine.match("a(b+|c)+d+|e+(f|g+h)+i","eggghgghghi"), true);
    assertEquals(RegexEngine.match("a(b+|c)+d+|e+(f|g+h)+i","eghfggghgghffi"), true);
    assertEquals(RegexEngine.match("a(b+|c)+d+|e+(f|g+h)+i","abbb"), false);
    assertEquals(RegexEngine.match("a(b+|c)+d+|e+(f|g+h)+i","ei"), false);
    assertEquals(RegexEngine.match("a(b+|c)+d+|e+(f|g+h)+i","abcad"), false);
  }

  @Test
  public void basicAsterisk() { //Test basic asterisk
    assertEquals(RegexEngine.match("a*","a"), true);
    assertEquals(RegexEngine.match("a*","aa"), true);
    assertEquals(RegexEngine.match("a*","aaaaaaaa"), true);
    assertEquals(RegexEngine.match("a*",""), true);
    assertEquals(RegexEngine.match("a*"," a"), false);
    assertEquals(RegexEngine.match("a*","a "), false);
    assertEquals(RegexEngine.match("a*"," "), false);
    assertEquals(RegexEngine.match("a*","a*"), false);
    assertEquals(RegexEngine.match("a*","ab"), false);
    assertEquals(RegexEngine.match("a*","b"), false);

    assertEquals(RegexEngine.match("a*a*a*","a"), true);
    assertEquals(RegexEngine.match("a*a*a*","aa"), true);
    assertEquals(RegexEngine.match("a*a*a*","aaa"), true);
    assertEquals(RegexEngine.match("a*a*a*","aaaaaaaa"), true);
    assertEquals(RegexEngine.match("a*a*a*",""), true);
    assertEquals(RegexEngine.match("a*a*a*"," a"), false);
    assertEquals(RegexEngine.match("a*a*a*","a "), false);
    assertEquals(RegexEngine.match("a*a*a*","aaab"), false);
    assertEquals(RegexEngine.match("a*a*a*","aaaaaaab"), false);
    assertEquals(RegexEngine.match("a*a*a*","a*a*a*"), false);
  }

  @Test
  public void asteriskBracket() { //Test asterisk bracket
    assertEquals(RegexEngine.match("(a)*","a"), true);
    assertEquals(RegexEngine.match("(a)*","aa"), true);
    assertEquals(RegexEngine.match("(a)*","aaaaaa"), true);
    assertEquals(RegexEngine.match("(a)*",""), true);
    assertEquals(RegexEngine.match("(a)*"," a"), false);
    assertEquals(RegexEngine.match("(a)*","a "), false);
    assertEquals(RegexEngine.match("(a)*"," "), false);
    assertEquals(RegexEngine.match("(a)*","b"), false);
    assertEquals(RegexEngine.match("(a)*","aab"), false);
    assertEquals(RegexEngine.match("(a)*","ba"), false);

    assertEquals(RegexEngine.match("(abc)*","abc"), true);
    assertEquals(RegexEngine.match("(abc)*","abcabc"), true);
    assertEquals(RegexEngine.match("(abc)*","abcabcabcabcabcabcabcabc"), true);
    assertEquals(RegexEngine.match("(abc)*",""), true);
    assertEquals(RegexEngine.match("(abc)*"," abc"), false);
    assertEquals(RegexEngine.match("(abc)*","abc "), false);
    assertEquals(RegexEngine.match("(abc)*"," "), false);
    assertEquals(RegexEngine.match("(abc)*","abcd"), false);
    assertEquals(RegexEngine.match("(abc)*","ab"), false);
    assertEquals(RegexEngine.match("(abc)*","(abc)*"), false);
    
    assertEquals(RegexEngine.match("(abc*)","abc"), true);
    assertEquals(RegexEngine.match("(abc*)","abcc"), true);
    assertEquals(RegexEngine.match("(abc*)","abccccccc"), true);
    assertEquals(RegexEngine.match("(abc*)","ab"), true);
    assertEquals(RegexEngine.match("(abc*)",""), false);
    assertEquals(RegexEngine.match("(abc*)","abcabc"), false);
    assertEquals(RegexEngine.match("(abc*)"," abc"), false);
    assertEquals(RegexEngine.match("(abc*)","abc "), false);
    assertEquals(RegexEngine.match("(abc*)","abcd"), false);
    assertEquals(RegexEngine.match("(abc*)","(abc*)"), false);
  }

  @Test
  public void asteriskAlternative() { //Test asterisk alternative
    assertEquals(RegexEngine.match("a*|b*|c*","a"), true);
    assertEquals(RegexEngine.match("a*|b*|c*","aa"), true);
    assertEquals(RegexEngine.match("a*|b*|c*","aaaaaaaa"), true);
    assertEquals(RegexEngine.match("a*|b*|c*","b"), true);
    assertEquals(RegexEngine.match("a*|b*|c*","bb"), true);
    assertEquals(RegexEngine.match("a*|b*|c*","bbbbbbb"), true);
    assertEquals(RegexEngine.match("a*|b*|c*","c"), true);
    assertEquals(RegexEngine.match("a*|b*|c*","cc"), true);
    assertEquals(RegexEngine.match("a*|b*|c*","ccccccc"), true);
    assertEquals(RegexEngine.match("a*|b*|c*",""), true);
    assertEquals(RegexEngine.match("a*|b*|c*"," "), false);
    assertEquals(RegexEngine.match("a*|b*|c*","abc"), false);
    assertEquals(RegexEngine.match("a*|b*|c*","a*|b*|c*"), false);
    assertEquals(RegexEngine.match("a*|b*|c*","d"), false);
    assertEquals(RegexEngine.match("a*|b*|c*"," a"), false);
    assertEquals(RegexEngine.match("a*|b*|c*","a "), false);
  }

  @Test
  public void asteriskBracketAlternative() { //Test asterisk bracket alternative
    assertEquals(RegexEngine.match("(a)*|(b)*|(c)*","a"), true);
    assertEquals(RegexEngine.match("(a)*|(b)*|(c)*","aa"), true);
    assertEquals(RegexEngine.match("(a)*|(b)*|(c)*","aaaaaaaa"), true);
    assertEquals(RegexEngine.match("(a)*|(b)*|(c)*","b"), true);
    assertEquals(RegexEngine.match("(a)*|(b)*|(c)*","bb"), true);
    assertEquals(RegexEngine.match("(a)*|(b)*|(c)*","bbbbbbb"), true);
    assertEquals(RegexEngine.match("(a)*|(b)*|(c)*","c"), true);
    assertEquals(RegexEngine.match("(a)*|(b)*|(c)*","cc"), true);
    assertEquals(RegexEngine.match("(a)*|(b)*|(c)*","ccccccc"), true);
    assertEquals(RegexEngine.match("(a)*|(b)*|(c)*",""), true);
    assertEquals(RegexEngine.match("(a)*|(b)*|(c)*","aaab"), false);
    assertEquals(RegexEngine.match("(a)*|(b)*|(c)*","bbbc"), false);
    assertEquals(RegexEngine.match("(a)*|(b)*|(c)*","cccd"), false);
    assertEquals(RegexEngine.match("(a)*|(b)*|(c)*","abcd"), false);
    assertEquals(RegexEngine.match("(a)*|(b)*|(c)*","(a)*|(b)*|(c)*"), false);

    assertEquals(RegexEngine.match("(abc)*|(abcd)*|(abcde)*","abc"), true);
    assertEquals(RegexEngine.match("(abc)*|(abcd)*|(abcde)*","abcd"), true);
    assertEquals(RegexEngine.match("(abc)*|(abcd)*|(abcde)*","abcde"), true);
    assertEquals(RegexEngine.match("(abc)*|(abcd)*|(abcde)*","abcabcabc"), true);
    assertEquals(RegexEngine.match("(abc)*|(abcd)*|(abcde)*","abcdabcdabcd"), true);
    assertEquals(RegexEngine.match("(abc)*|(abcd)*|(abcde)*","abcdeabcdeabcde"), true);
    assertEquals(RegexEngine.match("(abc)*|(abcd)*|(abcde)*",""), true);
    assertEquals(RegexEngine.match("(abc)*|(abcd)*|(abcde)*"," abc"), false);
    assertEquals(RegexEngine.match("(abc)*|(abcd)*|(abcde)*","abc "), false);
    assertEquals(RegexEngine.match("(abc)*|(abcd)*|(abcde)*","abcdef"), false);
    assertEquals(RegexEngine.match("(abc)*|(abcd)*|(abcde)*","abcabcabcdef"), false);
  }

  @Test
  public void gradescopeTestCases() { //Some tests that were used on the Gradescope marking
    assertEquals(RegexEngine.match("a+b*|c","ab"), true);
    assertEquals(RegexEngine.match("a+b*|c","c"), true);
    assertEquals(RegexEngine.match("a+b*|c","abbbbbbbb"), true);
    assertEquals(RegexEngine.match("a+b*|c","a"), true);
    assertEquals(RegexEngine.match("a+b*|c","aaaaaaaa"), true);
    assertEquals(RegexEngine.match("a+b*|c","aaaaabbbbb"), true);
    assertEquals(RegexEngine.match("a+b*|c","abc"), false);
    assertEquals(RegexEngine.match("a+b*|c","cc"), false);
    assertEquals(RegexEngine.match("a+b*|c",""), false);
    assertEquals(RegexEngine.match("a+b*|c"," a"), false);
    assertEquals(RegexEngine.match("a+b*|c","a "), false);

    assertEquals(RegexEngine.match("(a|b)*c","ac"), true);
    assertEquals(RegexEngine.match("(a|b)*c","bc"), true);
    assertEquals(RegexEngine.match("(a|b)*c","c"), true);
    assertEquals(RegexEngine.match("(a|b)*c","aaaac"), true);
    assertEquals(RegexEngine.match("(a|b)*c","bbbbc"), true);
    assertEquals(RegexEngine.match("(a|b)*c","abc"), true);
    assertEquals(RegexEngine.match("(a|b)*c","bac"), true);
    assertEquals(RegexEngine.match("(a|b)*c","ababbabaabc"), true);
    assertEquals(RegexEngine.match("(a|b)*c",""), false);
    assertEquals(RegexEngine.match("(a|b)*c"," ac"), false);
    assertEquals(RegexEngine.match("(a|b)*c","bc "), false);
    assertEquals(RegexEngine.match("(a|b)*c"," "), false);
    assertEquals(RegexEngine.match("(a|b)*c","(a|b)*c"), false);
    assertEquals(RegexEngine.match("(a|b)*c","a"), false);
    assertEquals(RegexEngine.match("(a|b)*c","b"), false);
    assertEquals(RegexEngine.match("(a|b)*c","abbaaaba"), false);

    assertEquals(RegexEngine.match("(x|y+z)*|z","x"), true);
    assertEquals(RegexEngine.match("(x|y+z)*|z","yz"), true);
    assertEquals(RegexEngine.match("(x|y+z)*|z","z"), true);
    assertEquals(RegexEngine.match("(x|y+z)*|z",""), true);
    assertEquals(RegexEngine.match("(x|y+z)*|z","xyzxyz"), true);
    assertEquals(RegexEngine.match("(x|y+z)*|z","xxxxxx"), true);
    assertEquals(RegexEngine.match("(x|y+z)*|z","yzyzyzyz"), true);
    assertEquals(RegexEngine.match("(x|y+z)*|z","yyyyz"), true);
    assertEquals(RegexEngine.match("(x|y+z)*|z","yyyyyzyyz"), true);
    assertEquals(RegexEngine.match("(x|y+z)*|z","xyzxyzxyz"), true);
    assertEquals(RegexEngine.match("(x|y+z)*|z","yzxxxyzyyzx"), true);
    assertEquals(RegexEngine.match("(x|y+z)*|z","y"), false);
    assertEquals(RegexEngine.match("(x|y+z)*|z","yyyyy"), false);
    assertEquals(RegexEngine.match("(x|y+z)*|z","yzyyzyzy"), false);
    assertEquals(RegexEngine.match("(x|y+z)*|z","zzz"), false);
    assertEquals(RegexEngine.match("(x|y+z)*|z","xz"), false);
    assertEquals(RegexEngine.match("(x|y+z)*|z"," x"), false);
    assertEquals(RegexEngine.match("(x|y+z)*|z","x "), false);
    assertEquals(RegexEngine.match("(x|y+z)*|z","xxxyyyzyzxy"), false);
  }

}
