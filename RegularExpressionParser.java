/*
Author: Adam English
Date: 2023/9/20
*/
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

//This program takes a regular expression provided by the user and takes strings inputted by the user to test against
//the regular expression, outputing either true or false if the string matches the pattern.

public class RegularExpressionParser {

    //Tests whether the regular expression provided by the user is syntactically correct
    //Takes in a regular expression string and returns either true or false if the pattern is correct or not
    static boolean testPatternSyntax(String pattern) {
        boolean validity = true;
        int patternIndex = 0;
        ArrayList<String> alphabet;

        //"defineAlphabet" is a separate method which creates the alphabet of the regular expression
        //This is useful, as it will be used later to determine whether a character is a unique regular expression
        //character ( brackets, *, +, |) or not
        alphabet = defineAlphabet(pattern);
        
        //Concatinated at the end of the pattern to identify when the pattern ends
        pattern = pattern.concat("~");
        
        //While-loop that goes through each character in the pattern
        while(pattern.charAt(patternIndex) == '~') {

            if(pattern.charAt(patternIndex) == pattern.charAt(patternIndex+1) && !alphabet.contains(pattern.charAt(patternIndex))) { //Check to see if there are any special characters in a row which violate synatx
                if(pattern.charAt(patternIndex) != '*') { //The special character '*' can be written in a row, so a check must be made here to not fail the patterns syntax
                    validity = false;
                    break;
                }
            }

            if(alphabet.contains(pattern.charAt(patternIndex))) { //If the character is a part of the alphabet, continue the pattern
                patternIndex++;
            } else if(pattern.charAt(patternIndex) == '(') { //Checking if brackets are set up correctly
                for(int i = patternIndex; pattern.charAt(i) != '~'; i++) {
                    if(pattern.charAt(i) == '(') { //Task doesn't allow for nested brackets
                        validity = false;
                        break;
                    } else if(pattern.charAt(i) == ')') { //The exit condition of the for-loop
                        i = pattern.length();
                    }
                }
                patternIndex++;
            } else if(pattern.charAt(patternIndex) == ')') {
                patternIndex++;
            } else if(pattern.charAt(patternIndex) == '|') {
                if(pattern.charAt(patternIndex+1) == '~') { //Checking if '|' was used at the end of the pattern
                    validity = false;
                    break;
                } else if(pattern.charAt(patternIndex+1) == '|') { //Checking if '|' was used twice in a row
                    validity = false;
                    break;
                } else {
                    patternIndex++;
                }
            } else {
                patternIndex++;
            }
        }

        return validity;
    }

    //Called by the "testPatternSyntax" method to create the alphabet of a regular expression
    //Takes a regular expression string and returns the alphabet of the regular expression
    static ArrayList<String> defineAlphabet(String pattern) {
        ArrayList<String> alphabet = new ArrayList<String>();
        List<String> specialChar = Arrays.asList("(", ")", " ", "|", "*", "+"); //All acknowledged special regular expression characters 
        int index = 0;

        //While-loop that goes through the regular expression and records all non-special characters
        while(pattern.length() > index) {
            if(!alphabet.contains(Character.toString(pattern.charAt(index))) && !specialChar.contains(Character.toString(pattern.charAt(index)))) {
                alphabet.add(Character.toString(pattern.charAt(index)));
            }
            index++;
        }

        return alphabet;
    }

    //Checks whether the string matches the regular expression logic
    //Takes in a regular expression string and string and return true or fail if the string does or
    //doesn't match the regular expression
    static boolean match(String pattern, String string) {
        boolean result = false;
        ArrayList<Integer> paths = new ArrayList<Integer>();
        int stringIndex = 0; //keeps the current index of the string
        int patternIndex = 0; //keeps the current index of the regular expression
        int pathCounter = 0; //Keeps count of which '|' the program is currently on
        int index = 0; //Temporary variable that is used to define the index of every '|' not inside a bracket
        int matchesInBracket = 0; //Keeps track of how many matches were made inside a bracket. Useful for '*' at the end of closed-brackets test-cases.
                                  //"matchesInBracket" is used at multiple specific stages in the method that confirm that the logic of a bracket has
                                  //completed and or can now be skipped, reducing the value of the variable to 0 
        int openBracket = 0; //Keeps track of the index for the current open-bracket
        int closedBracket = 0; //Keeps track of the index for the current closed-bracket
        int bracketAlt = 0; //Keeps track of the index for the '|' inside brackets (just in case a a '|' does exist) 

        //Special Character added to the end of each string to signify the end of the string. This was easier
        //than handling out-of-bound errors
        string = string.concat("~");
        pattern = pattern.concat("~");

        //Sets up logic for going to alternative paths in the regular expression
        while(pattern.length() > index) { //Identify all '|' so if a path is incorrect, go to the next instance that '|' is used
            if(pattern.charAt(index) == '(') {
                openBracket = index;
                for(int i = openBracket+1; i < pattern.length(); i++) { //Get the index of the ')'
                    if(pattern.charAt(i) == ')') {
                        closedBracket = i;
                        i = pattern.length(); //Ends the for-loop
                    }
                }
            } else if(pattern.charAt(index) == '|') {
                if(!(index > openBracket && index < closedBracket)) { //Check if '|' is inside brackets
                    paths.add(index);
                } else {
                }
            }

            index++;
        }

        openBracket = 0;
        closedBracket = 0;

        //While-loop goes through the regular expression character-by-character to determine if the string matches the logic
        while(true) {
            
            //Debug test statements, helps with understanding the flow of the program
            // System.out.println("");
            // System.out.println("stringIndex: " + stringIndex);
            // System.out.println("patternIndex: " + patternIndex);
            // System.out.println("string at " + stringIndex + ": " + string.charAt(stringIndex));
            // System.out.println("patternIndex at " + patternIndex + ": " + pattern.charAt(patternIndex));
            // System.out.println("matchesInBracket: " + matchesInBracket);

            //Checks used at the beginning to see if the string correctly matches the regular expression logic 
            if(pattern.charAt(patternIndex) == '~') { //If the pattern is exhausted
                if(string.charAt(stringIndex) == '~') { //If the string is exhausted
                    result = true;
                    break;
                }
            } else if(pattern.charAt(patternIndex) == '|' && !(patternIndex > openBracket && patternIndex< closedBracket)) { //If the string has reached the end of one of the regular expression's alternative path
                if(string.charAt(stringIndex) == '~') { //If the string is exhausted
                    result = true;
                    break;
                }
            }
            
            if(pattern.charAt(patternIndex) == '(') { //If special character '(' is detected
                openBracket = patternIndex;
                for(int i = openBracket+1; i < pattern.length(); i++) { //Get the index of the ')' and any '|' inside the brackets
                    if(pattern.charAt(i) == ')') {
                        closedBracket = i;
                        i = pattern.length(); //Ends the for-loop
                    } else if(pattern.charAt(i) == '|') { //Gets the index of any '|' inside the brackets
                        bracketAlt = i;
                    }
                }
                patternIndex++;
            } else if(pattern.charAt(patternIndex) == ')') { //If special character '(' is detected
                patternIndex++;
                matchesInBracket = 0;
            } else if(pattern.charAt(patternIndex) == '|') {//If special character '|' is detected
                if(patternIndex > openBracket && patternIndex < closedBracket) { //Checks if the path inside the brackets is done, so the pattern can now exit the brackets
                    patternIndex = closedBracket;
                    matchesInBracket = 0;
                } else if(pathCounter < paths.size()) { //Checks if an alternative path can be taken, as all other possible test-cases have been checked
                    patternIndex = paths.get(pathCounter) + 1;
                    pathCounter++;
                    stringIndex = 0;
                } else { //If no other alternative paths exist, the string does not match pattern
                    result = false;
                    break;
                }
            } else if(pattern.charAt(patternIndex) == '+') { //If special character '+' is detected
                if(pattern.charAt(patternIndex-1) != ')') { //Checks that no brackets are involved in the operation
                    if(string.charAt(stringIndex) == '~' && pattern.charAt(patternIndex+1) == '~') { //If string and pattern are exhausted, incrementing patternIndex will end the program on the next loop
                        patternIndex++;
                    } else if(string.charAt(stringIndex) == pattern.charAt(patternIndex-1)) { //If the previous pattern character is the same as the current string character, repeat the character
                        patternIndex--;
                    } else if(pattern.charAt(patternIndex+1) == '(') { //Identifies it's the start of a nested bracket, so just continue the pattern
                        patternIndex++;  
                    } else if(pattern.charAt(patternIndex+1) == ')') { //Checks it's the next character is a ')'
                        patternIndex++;              
                    } else if(string.charAt(stringIndex) == pattern.charAt(patternIndex+1)) { //Check if the pattern continues, so don't repeat previous character
                        patternIndex++;
                    } else if(pattern.charAt(patternIndex+1) == '|') { //If next character is '|', continue the pattern
                        patternIndex++;
                    } else { //Default logic is to skip the '+' and continue the pattern
                        patternIndex++;
                    }
                } else if(pattern.charAt(patternIndex-1) == ')') { //Checking logic for bracket
                    if(string.charAt(stringIndex) == pattern.charAt(patternIndex+1)) { //Checks if the pattern continues after the brackets, so don't repeat them
                        patternIndex++;
                    } else { //Default logic is to repeat the brackets
                        patternIndex = openBracket + 1;
                    }
                }
            } else if(pattern.charAt(patternIndex) == '*') { //If special character '*' is detected
                if(string.charAt(stringIndex) == pattern.charAt(patternIndex-1)) {//Repeat the '*' with the previous character
                    patternIndex--;
                } else if(pattern.charAt(patternIndex-1) == ')') { //Check '*' is attatched to brackets
                    if(string.charAt(stringIndex) == pattern.charAt(openBracket+1)) { //Repeat the bracket if first character matches
                        patternIndex = openBracket + 1;
                        matchesInBracket = 0;
                    } else if(string.charAt(stringIndex) == pattern.charAt(bracketAlt+1)) { //Check if there is an alternative path in the bracket and if the pattern continues there
                        patternIndex = bracketAlt + 1;
                    } else { //Can skip repeating the brackets and continue the pattern
                        patternIndex++;
                        stringIndex = stringIndex - matchesInBracket; //This line is used for the test-cases when the string fails halfway through brackets, but the brackets are attatched to a '*', so the string has to go back to its index before the brackets and the pattern skips the brackets
                    }
                } else { //Default logic is to skip the '*'
                    patternIndex++;
                }
            } else if(string.charAt(stringIndex) == pattern.charAt(patternIndex)) { //If the pattern character at patternIndex matches the string character at stringIndex
                if(patternIndex > openBracket && patternIndex < closedBracket) { //Checks if inside brackets
                    if(pattern.charAt(patternIndex+1) == closedBracket) { //Checks if the pattern is at the end of brackets, so the "matchesInBracket" varaible can be reset
                        matchesInBracket = 0;
                    } else { //If still inside brackets
                        matchesInBracket++;
                    }
                } else { //Default logic to reset the variable "matchesInBracket"
                    matchesInBracket = 0;
                }
                
                //Increments both the pattern and string indexes
                stringIndex++;
                patternIndex++;
            
            //If the pattern character and string character are still not matching, check if inside brackets
            } else if(string.charAt(stringIndex) != pattern.charAt(patternIndex) && (patternIndex > openBracket && patternIndex < closedBracket) && (bracketAlt > openBracket && bracketAlt < closedBracket)) {
                if(patternIndex <= bracketAlt) { //Check if the alternative path inside the brackets has been tried yet
                    patternIndex = bracketAlt+1;
                } else if(pattern.charAt(closedBracket+1) == '*') { //Check if the brackets are attached to a '*' and just skip the brackets and continue the pattern
                    patternIndex = closedBracket+1;
                } else if(pathCounter < paths.size()) { //Checks if an alternative path can be taken, as all other possible test-cases have been checked
                    patternIndex = paths.get(pathCounter) + 1;
                    pathCounter++;
                    stringIndex = 0;
                } else { //If no other alternative paths exist, the string does not match pattern
                    result = false;
                    break;
                }

            //If the pattern character and string character are still not matching and the pattern is not exhausted, check other possibilities
            } else if(string.charAt(stringIndex) != pattern.charAt(patternIndex) && pattern.charAt(patternIndex) != '~') {
                if(pattern.charAt(patternIndex+1) == '*') { //Checks if the character can be skipped
                    patternIndex++;
                } else if(patternIndex > openBracket && patternIndex < closedBracket) { //Check if inside brackets
                    if(pattern.charAt(closedBracket+1) == '*') { //Check if you can skip the brackets
                        patternIndex = closedBracket + 2;
                        stringIndex = 0;
                    } else if(pathCounter < paths.size()) { //Checks if an alternative path can be taken, as all other possible test-cases have been checked
                        patternIndex = paths.get(pathCounter) + 1;
                        pathCounter++;
                        stringIndex = 0;
                    } else { //If no other alternative paths exist, the string does not match pattern
                        result = false;
                        break;
                    }
                } else if(pathCounter < paths.size()) { //Checks if an alternative path can be taken, as all other possible test-cases have been checked
                    patternIndex = paths.get(pathCounter) + 1;
                    pathCounter++;
                    stringIndex = 0;  
                } else { //If no other alternative paths exist, the string does not match pattern
                    result = false;
                    break;
                }
            } else { //Default logic is to check if an alternative path exists within the regular expression, as all possible test-cases have been accounted for
                if(pathCounter < paths.size()) { //Checks if an alternative path can be taken, as all other possible test-cases have been checked
                    patternIndex = paths.get(pathCounter) + 1;
                    pathCounter++;
                    stringIndex = 0;
                } else { //If no other alternative paths exist, the string does not match pattern
                    result = false;
                    break;
                }
            }
        }

        return result;
    }

    //Main method that runs the program. It takes the user's input to obtain the regular expression. It then checks the regular expression's
    //syntax and then takes user input to obtain a string. The string is then tested against the logic of the regular expression. Prints
    //true or false if the string does or doesn't match the pattern
    public static void main(String[] args) throws IOException {
        Scanner reader = new Scanner(System.in);
        boolean result;
        String pattern;
        String currentString;
        
        System.out.println("Enter a regular expression");

        pattern = reader.nextLine();

        //Checks if the syntax of the regular expression is correct
        if(!testPatternSyntax(pattern)) {
            System.out.println("Pattern has incorrect synatx");
            System.exit(1); //Ends the program with an exit code 1
        }

        System.out.println("ready to test strings"); //Prompts the user to input a string to test against the regualr expression's logic

        while(true) {
            if(reader.hasNextLine()) {
                currentString = reader.nextLine(); //Reads the user's input
                    
                result = match(pattern, currentString);
                System.out.println(result);
            }
        }
    }
}

    