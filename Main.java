public class Main{
    public static void main(String[] args) {
        System.out.println("=====LCS=====");
        Algorithms.LCS lcs = new Algorithms.LCS();
        lcs.LCS("BEAVER","BAR");
        lcs.LCS("","");
        lcs.LCS("maple","map");
        lcs.LCS("rabbit","bbbababab");

        System.out.println("\n"+"=====MERGING BLOCKS=====");
        Algorithms.Merge merge = new Algorithms.Merge();
        merge.Merge(new int[]{2,3,4});
        merge.Merge(new int[]{0,0,0});
        merge.Merge(new int[]{1,0,1});

        System.out.println("\n"+"=====TREE=====");
        Algorithms.Tree tree = new Algorithms.Tree();

        System.out.println("\n"+"=====NAIVE=====");
        Algorithms.Naive naive = new Algorithms.Naive();
        naive.naive("aaaaaa","aaa");

        System.out.println("\n"+"=====KMP=====");
        Algorithms.KMP kmp = new Algorithms.KMP();
        kmp.KMPSearch("aaaaaabcaababc","abc");
        kmp.KMPSearch("","abc");
        kmp.KMPSearch("a","abc");
        kmp.KMPSearch("abcabcabca","abca");

        System.out.println("\n"+"=====RK=====");
        Algorithms.RabinKarp rk  = new Algorithms.RabinKarp();
        rk.RK("aaababcababc", "abc");
        rk.RK("abdj", "ab");
        rk.RK("abdj", "dj");

        System.out.println("\n"+"=====KASAI=====");
        Algorithms.Kasai Kasai = new Algorithms.Kasai();
        Kasai.performKasai("banana");
        Kasai.performKasai("abcd");
        Kasai.performKasai("a");
        Kasai.performKasai("aaaaa");


        System.out.println("\n"+"=====AHO=====");
        Algorithms.Aho aho = new Algorithms.Aho();
        aho.performAho(new String[]{"bana","a","na","a",""},new String[]{"banana","ananas","","banana"});
    }
}