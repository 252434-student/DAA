public class Main{
    public static void main(String[] args) {
        Algorithms.LCS lcs = new Algorithms.LCS();
        //lcs.LCS("BEAVER","BAR");
        Algorithms.Naive naive = new Algorithms.Naive();
        //naive.naive("aaaaaa","aaa");
        Algorithms.KMP kmp = new Algorithms.KMP();
        //kmp.KMPSearch("aaaaaabcaababc","abc");
        Algorithms.RabinKarp rk  = new Algorithms.RabinKarp();
        //rk.RK("aaababcababc", "abc");
    }
}