import java.util.Arrays;
import java.util.List;

public class Algorithms {
    static class LCS{
        void LCS(String a, String b) {
            int n = a.length(), m = b.length();
            int[][] dp = new int[n + 1][m + 1];
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= m; j++) {
                    if (a.charAt(i - 1) == b.charAt(j - 1)) {
                        dp[i][j] = dp[i - 1][j - 1] + 1;
                    } else {
                        dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                    }
                }
            }
            int index = dp[n][m];
            int temp = index, i = n, j = m;
            char[] lcs = new char[index + 1];

            while (i > 0 && j > 0) {
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    lcs[index - 1] = a.charAt(i - 1);
                    i--;
                    j--;
                    index--;
                } else if (dp[i - 1][j] > dp[i][j - 1]) {
                    i--;
                } else {
                    j--;
                }
            }
            System.out.print("LCS length: " + dp[n][m] + ", sequence: ");
            for (int k = 0; k <= temp; k++) {
                System.out.print(lcs[k]);
            }
        }
    }
    static class Merge{
        void Merge(int[] sizes) {
            int n = sizes.length;
            int[][] dp = new int[n + 1][n + 1];
            int[] prefixSum = new int[n + 1];

            for (int i = 1; i <= n; i++) {
                prefixSum[i] = prefixSum[i - 1] + sizes[i - 1];
            }

            for (int len = 2; len <= n; len++) {
                for (int i = 1; i <= n - len + 1; i++) {
                    int j = i + len - 1;
                    int sum = prefixSum[j] - prefixSum[i - 1];
                    dp[i][j] = Integer.MAX_VALUE;

                    for (int k = i; k < j; k++) {
                        int cost = dp[i][k] + dp[k + 1][j] + sum;
                        dp[i][j] = Math.min(dp[i][j], cost);
                    }
                }
            }
            System.out.print("Merge cost: " + dp[1][n]);
        }
    }
    static class Tree{
        List<Integer>[] tree;
        int[] weights;
        int[][] dp;
        boolean[] visited;

        void dfs(int u){
            visited[u] = true;
            dp[u][0] = 0;
            dp[u][1] = weights[u];

            for(int v:tree[u]){
                if(!visited[v]){
                    dfs(v);
                    dp[u][0] =  Math.min(dp[u][0],dp[v][1]);
                    dp[u][1] += dp[v][0];
                }
            }
        }
        void maxIndependentSet(int n){
            dp = new int[n + 1][2];
            visited = new boolean[n + 1];
            dfs(0);
            System.out.println("MIS: "+Math.max(dp[1][0],dp[1][1]));
        }
    } //TODO
    static class Naive{
        void naive(String text, String pattern){
            int[] positions = new int[text.length()-pattern.length()+1];
            int n = text.length(), m = pattern.length();
            int i=0, j=0;
            for(; i<n-m+1; i++){
                for(; j<m; j++){
                    if(text.charAt(i+j)!=pattern.charAt(j)){break;}
                }
                if(j==m){
                    positions[i] = i;
                }
            }
            System.out.println("Positions match at: "+ Arrays.toString(positions));
        }
    }
    static class KMP{
        void KMPSearch(String pat, String txt)
        {
            int M = pat.length();
            int N = txt.length();
            int lps[] = new int[M];
            int j = 0;
            computeLPSArray(pat, M, lps);

            int i = 0;
            while (i < N) {
                if (pat.charAt(j) == txt.charAt(i)) {
                    j++;
                    i++;
                }
                if (j == M) {
                    System.out.println("Found pattern "
                            + "at index " + (i - j));
                    j = lps[j - 1];
                }
                else if (i < N && pat.charAt(j) != txt.charAt(i)) {
                    if (j != 0)
                        j = lps[j - 1];
                    else
                        i = i + 1;
                }
            }
        }

        void computeLPSArray(String pat, int M, int lps[]) {
            int len = 0;
            int i = 1;
            lps[0] = 0;

            while (i < M) {
                if (pat.charAt(i) == pat.charAt(len)) {
                    len++;
                    lps[i] = len;
                    i++;
                }
                else {
                    if (len != 0) {
                        len = lps[len - 1];
                    }
                    else {
                        lps[i] = len;
                        i++;
                    }
                }
            }
        }
    }
}