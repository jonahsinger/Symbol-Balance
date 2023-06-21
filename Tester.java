public class Tester {
    public static void main (String [] args){
        SymbolBalance a = new SymbolBalance();
        
        a.setFile("TestFiles/Test1.java");
        System.out.println(a.checkFile());
        a.setFile("TestFiles/Test2.java");
        System.out.println(a.checkFile());
        a.setFile("TestFiles/Test3.java");
        System.out.println(a.checkFile());
        a.setFile("TestFiles/Test4.java");
        System.out.println(a.checkFile());
        a.setFile("TestFiles/Test5.java");
        System.out.println(a.checkFile());
        a.setFile("TestFiles/Test6.java");
        System.out.println(a.checkFile());


    }
}
