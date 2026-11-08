import java.util.*;

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
            System.out.println("Naive Positions match at: "+ Arrays.toString(positions));
        }
    }
    static class KMP{
        void KMPSearch(String txt, String pat)
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
                    System.out.println("KMP Found pattern "
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
    static class RabinKarp{
        static final int P = 31;
        static final int MOD = 101;
        void RK(String text, String pat){
            int n = text.length(), m = pat.length();
            int p = 0, t=0, h=1;
            ArrayList<Integer> ans = new ArrayList<>();

            // Precompute h = pow(d, m-1) % q
            for (int i = 0; i < m - 1; i++)
                h = (h * P) % MOD;

            // Compute initial hash values for pattern and first
            // window of text
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
            System.out.println("RK Match found at index: "+ans.toString());
        }
    }
    static class Kasai{
        // Structure to store information of a suffix
        static class Suffix {
            int index;  // To store original index
            int[] rank = new int[2]; // To store ranks and next rank pair
        }

        public class SuffixArray {

            // A comparison function used by Arrays.sort() to compare two suffixes
            // Compares two pairs, returns 1 if the first pair is smaller
            static int cmp(Suffix a, Suffix b) {
                return (a.rank[0] == b.rank[0]) ? Integer.compare(a.rank[1], b.rank[1]) : Integer.compare(a.rank[0], b.rank[0]);
            }

            // This is the main function that takes a string 'txt' of size n as an
            // argument, builds and returns the suffix array for the given string
            static Vector<Integer> buildSuffixArray(String txt, int n) {
                // A structure to store suffixes and their indexes
                Suffix[] suffixes = new Suffix[n];

                // Store suffixes and their indexes in an array of structures.
                // The structure is needed to sort the suffixes alphabetically
                // and maintain their old indexes while sorting
                for (int i = 0; i < n; i++) {
                    suffixes[i] = new Suffix();
                    suffixes[i].index = i;
                    suffixes[i].rank[0] = txt.charAt(i) - 'a';
                    suffixes[i].rank[1] = (i + 1) < n ? (txt.charAt(i + 1) - 'a') : -1;
                }

                // Sort the suffixes using the comparison function
                // defined above.
                Arrays.sort(suffixes, SuffixArray::cmp);

                // At this point, all suffixes are sorted according to the first
                // 2 characters. Let us sort suffixes according to first 4
                // characters, then first 8 and so on
                int[] ind = new int[n];  // This array is needed to get the index in suffixes[]
                // from the original index. This mapping is needed to get
                // the next suffix.
                for (int k = 4; k < 2 * n; k = k * 2) {
                    // Assigning rank and index values to the first suffix
                    int rank = 0;
                    int prev_rank = suffixes[0].rank[0];
                    suffixes[0].rank[0] = rank;
                    ind[suffixes[0].index] = 0;

                    // Assigning rank to suffixes
                    for (int i = 1; i < n; i++) {
                        // If the first rank and next ranks are the same as that of the previous
                        // suffix in the array, assign the same new rank to this suffix
                        if (suffixes[i].rank[0] == prev_rank &&
                                suffixes[i].rank[1] == suffixes[i - 1].rank[1]) {
                            prev_rank = suffixes[i].rank[0];
                            suffixes[i].rank[0] = rank;
                        } else { // Otherwise increment rank and assign
                            prev_rank = suffixes[i].rank[0];
                            suffixes[i].rank[0] = ++rank;
                        }
                        ind[suffixes[i].index] = i;
                    }

                    // Assign next rank to every suffix
                    for (int i = 0; i < n; i++) {
                        int nextindex = suffixes[i].index + k / 2;
                        suffixes[i].rank[1] = (nextindex < n) ?
                                suffixes[ind[nextindex]].rank[0] : -1;
                    }

                    // Sort the suffixes according to the first k characters
                    Arrays.sort(suffixes, SuffixArray::cmp);
                }

                // Store indexes of all sorted suffixes in the suffix array
                Vector<Integer> suffixArr = new Vector<>();
                for (int i = 0; i < n; i++)
                    suffixArr.add(suffixes[i].index);

                // Return the suffix array
                return suffixArr;
            }

            /* To construct and return LCP */
            static Vector<Integer> kasai(String txt, Vector<Integer> suffixArr) {
                int n = suffixArr.size();

                // To store LCP array
                int temp[]=new int[n];
                Vector<Integer> lcp = new Vector<>(n);

                // An auxiliary array to store the inverse of the suffix array
                // elements. For example, if suffixArr[0] is 5, the
                // invSuff[5] would store 0. This is used to get the next
                // suffix string from the suffix array.
                int[] invSuff = new int[n];

                // Fill values in invSuff[]
                for (int i = 0; i < n; i++)
                    invSuff[suffixArr.get(i)] = i;

                // Initialize the length of the previous LCP
                int k = 0;

                // Process all suffixes one by one starting from
                // the first suffix in txt[]
                for (int i = 0; i < n; i++) {
            /* If the current suffix is at n-1, then we don’t
               have the next substring to consider. So lcp is not
               defined for this substring, we put zero. */
                    if (invSuff[i] == n - 1) {
                        k = 0;
                        continue;
                    }

            /* j contains the index of the next substring to
               be considered to compare with the present
               substring, i.e., the next string in the suffix array */
                    int j = suffixArr.get(invSuff[i] + 1);

                    // Directly start matching from k'th index as
                    // at least k-1 characters will match
                    while (i + k < n && j + k < n && txt.charAt(i + k) == txt.charAt(j + k))
                        k++;

                    temp[invSuff[i]]=k;// lcp for the present suffix.

                    // Deleting the starting character from the string.
                    if (k > 0)
                        k--;
                }

                for(int i=0;i<n;i++) {
                    lcp.add(temp[i]);
                }
                // return the constructed lcp array
                return lcp;
            }

            // Utility function to print an array
            static void printArr(Vector<Integer> arr, int n) {
                for (Integer value : arr)
                    System.out.print(value + " ");
                System.out.println();
            }

        }
    } //TODO
    static class Aho{
            // Max number of states in the matching
// machine. Should be equal to the sum
// of the length of all keywords.
            static int MAXS = 500;

            // Maximum number of characters
// in input alphabet
            static int MAXC = 26;

            // OUTPUT FUNCTION IS IMPLEMENTED USING out[]
// Bit i in this mask is one if the word with
// index i appears when the machine enters
// this state.
            static int []out = new int[MAXS];

            // FAILURE FUNCTION IS IMPLEMENTED USING f[]
            static int []f = new int[MAXS];

            // GOTO FUNCTION (OR TRIE) IS
// IMPLEMENTED USING g[][]
            static int [][]g = new int[MAXS][MAXC];

            // Builds the String matching machine.
// arr -   array of words. The index of each keyword is important:
//         "out[state] & (1 << i)" is > 0 if we just found word[i]
//         in the text.
// Returns the number of states that the built machine has.
// States are numbered 0 up to the return value - 1, inclusive.
            static int buildMatchingMachine(String arr[], int k)
            {

                // Initialize all values in output function as 0.
                Arrays.fill(out, 0);

                // Initialize all values in goto function as -1.
                for(int i = 0; i < MAXS; i++)
                    Arrays.fill(g[i], -1);

                // Initially, we just have the 0 state
                int states = 1;

                // Convalues for goto function, i.e., fill g[][]
                // This is same as building a Trie for arr[]
                for(int i = 0; i < k; ++i)
                {
                    String word = arr[i];
                    int currentState = 0;

                    // Insert all characters of current
                    // word in arr[]
                    for(int j = 0; j < word.length(); ++j)
                    {
                        int ch = word.charAt(j) - 'a';

                        // Allocate a new node (create a new state)
                        // if a node for ch doesn't exist.
                        if (g[currentState][ch] == -1)
                            g[currentState][ch] = states++;

                        currentState = g[currentState][ch];
                    }

                    // Add current word in output function
                    out[currentState] |= (1 << i);
                }

                // For all characters which don't have
                // an edge from root (or state 0) in Trie,
                // add a goto edge to state 0 itself
                for(int ch = 0; ch < MAXC; ++ch)
                    if (g[0][ch] == -1)
                        g[0][ch] = 0;

                // Now, let's build the failure function
                // Initialize values in fail function
                Arrays.fill(f, -1);

                // Failure function is computed in
                // breadth first order
                // using a queue
                Queue<Integer> q = new LinkedList<>();

                // Iterate over every possible input
                for(int ch = 0; ch < MAXC; ++ch)
                {

                    // All nodes of depth 1 have failure
                    // function value as 0. For example,
                    // in above diagram we move to 0
                    // from states 1 and 3.
                    if (g[0][ch] != 0)
                    {
                        f[g[0][ch]] = 0;
                        q.add(g[0][ch]);
                    }
                }

                // Now queue has states 1 and 3
                while (!q.isEmpty())
                {

                    // Remove the front state from queue
                    int state = q.peek();
                    q.remove();

                    // For the removed state, find failure
                    // function for all those characters
                    // for which goto function is
                    // not defined.
                    for(int ch = 0; ch < MAXC; ++ch)
                    {

                        // If goto function is defined for
                        // character 'ch' and 'state'
                        if (g[state][ch] != -1)
                        {

                            // Find failure state of removed state
                            int failure = f[state];

                            // Find the deepest node labeled by proper
                            // suffix of String from root to current
                            // state.
                            while (g[failure][ch] == -1)
                                failure = f[failure];

                            failure = g[failure][ch];
                            f[g[state][ch]] = failure;

                            // Merge output values
                            out[g[state][ch]] |= out[failure];

                            // Insert the next level node
                            // (of Trie) in Queue
                            q.add(g[state][ch]);
                        }
                    }
                }
                return states;
            }

            // Returns the next state the machine will transition to using goto
// and failure functions.
// currentState - The current state of the machine. Must be between
//                0 and the number of states - 1, inclusive.
// nextInput - The next character that enters into the machine.
            static int findNextState(int currentState, char nextInput)
            {
                int answer = currentState;
                int ch = nextInput - 'a';

                // If goto is not defined, use
                // failure function
                while (g[answer][ch] == -1)
                    answer = f[answer];

                return g[answer][ch];
            }

            // This function finds all occurrences of
// all array words in text.
            static void searchWords(String arr[], int k,
                                    String text)
            {

                // Preprocess patterns.
                // Build machine with goto, failure
                // and output functions
                buildMatchingMachine(arr, k);

                // Initialize current state
                int currentState = 0;

                // Traverse the text through the
                // built machine to find all
                // occurrences of words in arr[]
                for(int i = 0; i < text.length(); ++i)
                {
                    currentState = findNextState(currentState,
                            text.charAt(i));

                    // If match not found, move to next state
                    if (out[currentState] == 0)
                        continue;

                    // Match found, print all matching
                    // words of arr[]
                    // using output function.
                    for(int j = 0; j < k; ++j)
                    {
                        if ((out[currentState] & (1 << j)) > 0)
                        {
                            System.out.print("Word " +  arr[j] +
                                    " appears from " +
                                    (i - arr[j].length() + 1) +
                                    " to " +  i + "\n");
                        }
                    }
                }
            }
    } //TODO
}