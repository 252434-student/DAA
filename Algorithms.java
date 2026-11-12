import java.util.*;

public class Algorithms {
    static class LCS {
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
            for (int k = 0; k < temp; k++) {
                System.out.print(lcs[k]);
            }
            System.out.println();
        }
    }

    static class Merge {
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
            System.out.println("Merge cost: " + dp[1][n]);
        }
    }

    static class Tree {
        List<Integer>[] tree;
        int[] weights;
        int[][] dp;
        boolean[] visited;

        void dfs(int u) {
            visited[u] = true;
            dp[u][0] = 0;
            dp[u][1] = weights[u];

            for (int v : tree[u]) {
                if (!visited[v]) {
                    dfs(v);
                    dp[u][0] = Math.min(dp[u][0], dp[v][1]);
                    dp[u][1] += dp[v][0];
                }
            }
        }

        void maxIndependentSet(int n) {
            dp = new int[n + 1][2];
            visited = new boolean[n + 1];
            dfs(0);
            System.out.println("MIS: " + Math.max(dp[1][0], dp[1][1]));
        }
    }

    static class Naive {
        void naive(String text, String pattern) {
            int[] positions = new int[text.length() - pattern.length() + 1];
            int n = text.length(), m = pattern.length();
            int i = 0, j = 0;
            for (; i < n - m + 1; i++) {
                for (; j < m; j++) {
                    if (text.charAt(i + j) != pattern.charAt(j)) {
                        break;
                    }
                }
                if (j == m) {
                    positions[i] = i;
                }
            }
            System.out.println("Naive Positions match at: " + Arrays.toString(positions));
        }
    }

    static class KMP {
        void KMPSearch(String txt, String pat) {
            int M = pat.length();
            int N = txt.length();
            int lps[] = new int[M];
            int j = 0;
            computeLPSArray(pat, M, lps);

            int i = 0;
            ArrayList<Integer> match = new ArrayList<>();
            while (i < N) {
                if (pat.charAt(j) == txt.charAt(i)) {
                    j++;
                    i++;
                }
                if (j == M) {
                    match.add(i - j);
                    j = lps[j - 1];
                } else if (i < N && pat.charAt(j) != txt.charAt(i)) {
                    if (j != 0)
                        j = lps[j - 1];
                    else
                        i = i + 1;
                }
            }
            System.out.println("KMP positions: " + match.toString());
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
                } else {
                    if (len != 0) {
                        len = lps[len - 1];
                    } else {
                        lps[i] = len;
                        i++;
                    }
                }
            }
        }
    }

    static class RabinKarp {
        static final int P = 31;
        static final int MOD = 101;

        void RK(String text, String pat) {
            int n = text.length(), m = pat.length();
            int p = 0, t = 0, h = 1;
            ArrayList<Integer> ans = new ArrayList<>();

            for (int i = 0; i < m - 1; i++)
                h = (h * P) % MOD;

            for (int i = 0; i < m; i++) {
                p = (P * p + pat.charAt(i)) % MOD;
                t = (P * t + text.charAt(i)) % MOD;
            }

            for (int i = 0; i <= n - m; i++) {
                if (p == t) {
                    boolean match = true;
                    for (int j = 0; j < m; j++) {
                        if (text.charAt(i + j)
                                != pat.charAt(j)) {
                            match = false;
                            break;
                        }
                    }
                    if (match)
                        ans.add(i);
                }
                if (i < n - m) {
                    t = (P * (t - text.charAt(i) * h)
                            + text.charAt(i + m))
                            % MOD;
                    if (t < 0)
                        t += MOD;
                }
            }
            System.out.println("RK Match found at index: " + ans.toString());
        }
    }

    static class Kasai {
        static class Suffix {
            int index;
            int[] rank = new int[2];
        }

        static int compare(Suffix a, Suffix b) {
            return (a.rank[0] == b.rank[0]) ? Integer.compare(a.rank[1], b.rank[1]) : Integer.compare(a.rank[0], b.rank[0]);
        }

        static Vector<Integer> buildSuffixArray(String txt, int n) {
            Suffix[] suffixes = new Suffix[n];

            for (int i = 0; i < n; i++) {
                suffixes[i] = new Suffix();
                suffixes[i].index = i;
                suffixes[i].rank[0] = txt.charAt(i) - 'a';
                suffixes[i].rank[1] = (i + 1) < n ? (txt.charAt(i + 1) - 'a') : -1;
            }

            Arrays.sort(suffixes, Kasai::compare);

            int[] ind = new int[n];
            for (int k = 4; k < 2 * n; k = k * 2) {
                int rank = 0;
                int prev_rank = suffixes[0].rank[0];
                suffixes[0].rank[0] = rank;
                ind[suffixes[0].index] = 0;

                for (int i = 1; i < n; i++) {
                    if (suffixes[i].rank[0] == prev_rank &&
                            suffixes[i].rank[1] == suffixes[i - 1].rank[1]) {
                        prev_rank = suffixes[i].rank[0];
                        suffixes[i].rank[0] = rank;
                    } else {
                        prev_rank = suffixes[i].rank[0];
                        suffixes[i].rank[0] = ++rank;
                    }
                    ind[suffixes[i].index] = i;
                }
                for (int i = 0; i < n; i++) {
                    int nextindex = suffixes[i].index + k / 2;
                    suffixes[i].rank[1] = (nextindex < n) ?
                            suffixes[ind[nextindex]].rank[0] : -1;
                }
                Arrays.sort(suffixes, Kasai::compare);
            }

            Vector<Integer> suffixArr = new Vector<>();
            for (int i = 0; i < n; i++)
                suffixArr.add(suffixes[i].index);

            return suffixArr;
        }

        static Vector<Integer> kasai(String txt, Vector<Integer> suffixArr) {
            int n = suffixArr.size();

            int temp[] = new int[n];
            Vector<Integer> lcp = new Vector<>(n);
            int[] invSuff = new int[n];

            for (int i = 0; i < n; i++)
                invSuff[suffixArr.get(i)] = i;

            int k = 0;

            for (int i = 0; i < n; i++) {
                if (invSuff[i] == n - 1) {
                    k = 0;
                    continue;
                }

                int j = suffixArr.get(invSuff[i] + 1);

                while (i + k < n && j + k < n && txt.charAt(i + k) == txt.charAt(j + k))
                    k++;

                temp[invSuff[i]] = k;
                if (k > 0)
                    k--;
            }

            for (int i = 0; i < n; i++) {
                lcp.add(temp[i]);
            }
            return lcp;
        }

        static void printArr(Vector<Integer> arr, int n) {
            for (Integer value : arr)
                System.out.print(value + " ");
            System.out.println();
        }

        public void performKasai(String text) {
            Vector<Integer> sa = buildSuffixArray(text, text.length());
            System.out.println("Suffix array: ");
            printArr(sa, sa.size());

            Vector<Integer> lcp = kasai(text, sa);
            System.out.println("LCP array: ");
            printArr(lcp, lcp.size());
        }
    }

    static class Aho{
            static int MAXS = 500;

            static int MAXC = 26;

            static int []out = new int[MAXS];

            static int []f = new int[MAXS];

            static int [][]g = new int[MAXS][MAXC];

            static int buildMatchingMachine(String arr[], int k)
            {
                Arrays.fill(out, 0);

                for(int i = 0; i < MAXS; i++)
                    Arrays.fill(g[i], -1);

                int states = 1;

                for(int i = 0; i < k; ++i)
                {
                    String word = arr[i];
                    int currentState = 0;

                    for(int j = 0; j < word.length(); ++j)
                    {
                        int ch = word.charAt(j) - 'a';

                        if (g[currentState][ch] == -1)
                            g[currentState][ch] = states++;

                        currentState = g[currentState][ch];
                    }

                    out[currentState] |= (1 << i);
                }

                for(int ch = 0; ch < MAXC; ++ch)
                    if (g[0][ch] == -1)
                        g[0][ch] = 0;

                Arrays.fill(f, -1);

                Queue<Integer> q = new LinkedList<>();

                for(int ch = 0; ch < MAXC; ++ch)
                {

                    if (g[0][ch] != 0)
                    {
                        f[g[0][ch]] = 0;
                        q.add(g[0][ch]);
                    }
                }

                while (!q.isEmpty())
                {
                    int state = q.peek();
                    q.remove();
                    for(int ch = 0; ch < MAXC; ++ch)
                    {
                        if (g[state][ch] != -1)
                        {
                            int failure = f[state];

                            while (g[failure][ch] == -1)
                                failure = f[failure];

                            failure = g[failure][ch];
                            f[g[state][ch]] = failure;

                            out[g[state][ch]] |= out[failure];

                            q.add(g[state][ch]);
                        }
                    }
                }
                return states;
            }

            static int findNextState(int currentState, char nextInput)
            {
                int answer = currentState;
                int ch = nextInput - 'a';

                while (g[answer][ch] == -1)
                    answer = f[answer];

                return g[answer][ch];
            }

            public void performAho(String[] pattern, String[] text) {
                int[][] count = new int[pattern.length][text.length];
                int k = pattern.length, l = text.length;
                buildMatchingMachine(pattern, k);

                int currentState = 0;
                for (int a = 0; a < l; ++a) {
                    for (int i = 0; i < text[a].length(); ++i) {
                        currentState = findNextState(currentState,
                                text[a].charAt(i));

                        if (out[currentState] == 0)
                            continue;
                        for (int j = 0; j < k; ++j) {
                            if ((out[currentState] & (1 << j)) > 0) {
                                count[j][a] += 1;
                            }
                        }
                    }
                }
                System.out.println("Count: "+ Arrays.deepToString(count));
            }
    }
}