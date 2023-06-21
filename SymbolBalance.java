/*
* Jonah Singer
* 03/02/2026
* SymbolBalance Checker
 */

import java.io.*;
import java.util.*;

public class SymbolBalance implements SymbolBalanceInterface {

    private File fileName;

    // Set file takes in a string representing the path to the file and sets fileName
    public void setFile(String filename){
        this.fileName = new File(filename);
    }


    /*
    Reads in the file character by character and
    check to make sure that symbols are balanced
     */
    public BalanceError checkFile(){
        try {
            // Creates a BufferedReader to read in file
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            // Creates a stack to push and pop symbols
            Stack<Character> stack = new Stack<>();

            // line stores the line as a String and lineNum stores the line number
            String line;
            int lineNum = 0;

            // ignore keeps track of if characters should be ignored
            boolean ignore = false;

            // prevSymbol keeps track of the previous Symbol for comment blocks
            char prevSymbol = 0;

            /*
            While loop runs until there are no more lines. Reads a line from br and
            assigns it to line, then checks if it is null. if not, continues loop
            */
            while ((line = br.readLine()) != null) {
                lineNum++;

                // for loop iterates through each line char by char
                for (char currentSymbol : line.toCharArray()) {

                    // switch case deals with all special chars
                    switch (currentSymbol) {
                        // '(', '[', and '{' are treated the same
                        case '(':
                        case '[':
                        case '{':
                            // if ignore is false push them to stack
                            if (!ignore) {
                                stack.push(currentSymbol);
                            }
                            break;
                        case ')': {
                            // ignores if ignore is true
                            if (ignore) {
                                break;
                            }
                            // if stack is empty return empty stack error
                            if (stack.empty()) {
                                return new EmptyStackError(lineNum);
                            }
                            // pop and store top of the stack
                            char topChar = stack.pop();
                            // return mismatch error if ( is not top of stack
                            if (topChar != '(') {
                                return new MismatchError(lineNum, currentSymbol, topChar);
                            }
                            break;
                        }
                        case ']': {
                            // ignores if ignore is true
                            if (ignore) {
                                break;
                            }
                            // if stack is empty return empty stack error
                            if (stack.empty()) {
                                return new EmptyStackError(lineNum);
                            }
                            // pop and store top of the stack
                            char topChar = stack.pop();
                            // return mismatch error if [ is not top of stack
                            if (topChar != '[') {
                                return new MismatchError(lineNum, currentSymbol, topChar);
                            }
                            break;
                        }
                        case '}': {
                            // ignores if ignore is true
                            if (ignore) {
                                break;
                            }
                            // if stack is empty return empty stack error
                            if (stack.empty()) {
                                return new EmptyStackError(lineNum);
                            }
                            // pop and store top of the stack
                            char topChar = stack.pop();
                            // return mismatch error if { is not top of stack
                            if (topChar != '{') {
                                return new MismatchError(lineNum, currentSymbol, topChar);
                            }
                            break;
                        }
                        case '"':
                            // if in comment ignore
                            if (ignore && stack.peek() == '*'){
                                break;
                            }
                            // if in string
                            if (ignore){
                                // pop and change ignore to false
                                stack.pop();
                                ignore = false;
                            }
                            // if not in string
                            else {
                                // push and set ignore to true
                                stack.push('"');
                                ignore = true;
                            }
                            break;
                        case '/':
                            // if in string ignore
                            if (ignore && stack.peek() == '"'){
                                break;
                            }
                            // if closing comment
                            if (prevSymbol == '*'){
                                // change ignore to false
                                ignore = false;
                                // if stack is empty return empty stack error
                                if (stack.empty()) {
                                    return new EmptyStackError(lineNum);
                                }
                                // if there is not a comment open return mismatch
                                if (stack.peek() != '*'){
                                    return new MismatchError(lineNum, '*', stack.peek());
                                }
                                // if there is a comment open than pop
                                else {
                                    stack.pop();
                                }
                            }
                            break;
                        case '*':
                            // if ignore is true then ignore
                            if (ignore){
                                break;
                            }
                            // if this is a comment open
                            if (prevSymbol == '/'){
                                // push * on to stack and set ignore to true
                                stack.push('*');
                                ignore = true;
                            }
                            break;
                    }
                    // saves the current symbol for next iteration
                    prevSymbol = currentSymbol;
                }
            }
            // if there are still symbols on the stack return a NonEmptyStackError
            if (!stack.empty()){
                return new NonEmptyStackError(stack.peek(), stack.size());
            }
            br.close();
        } 
        // if the file is not found
        catch(IOException e){
            System.out.print("IOException");
        }
        
        // if there is no error null is returned
        return null;
    }

}
