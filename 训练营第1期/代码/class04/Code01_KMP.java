package class04;

public class Code01_KMP {

	public static int getIndexOf(String s, String m) {
		if (s == null || m == null || m.length() < 1 || s.length() < m.length()) {
			return -1;
		}
		char[] str1 = s.toCharArray();
		char[] str2 = m.toCharArray();
		int x = 0;
		int y = 0;
		int[] next = getNextArray(str2);
		while (x < str1.length && y < str2.length) {
			if (str1[x] == str2[y]) {//当前位置相等，下标同时后移
				x++;
				y++;
			} else if (next[y] == -1) {//字符串s的当前字符x无法匹配出m，尝试当前字符的下一个位置
				x++;
			} else {//模式字符串m当前y位置无法与当前字符x位置匹配，尝试m的next位置匹配
				y = next[y];
			}
		}
		return y == str2.length ? x - y : -1;
	}

	public static int[] getNextArray(char[] ms) {
		if (ms.length == 1) {
			return new int[] { -1 };
		}
		int[] next = new int[ms.length];
		next[0] = -1;
		next[1] = 0;
		int i = 2;//从求第2个位置开始求next值
		// cn代表，cn位置的字符，是当前和i-1位置比较的字符;也表示0~i-1范围的相等最长前缀和后缀对应前缀下标cn
		int cn = 0;
		while (i < next.length) {
			if (ms[i - 1] == ms[cn]) {//i-1位置的字符与上一个前缀的后一个字符相等，继续扩大
				next[i++] = ++cn;
			} else if (cn > 0) {//不能再扩大cn向前跳，用0~next[cn]范围的最长前缀继续尝试i位置的值
				cn = next[cn];
			} else {//cn已近跳到开头,每次next只能从开头试
				next[i++] = 0;
			}
		}
		return next;
	}

	// for test
	public static String getRandomString(int possibilities, int size) {
		char[] ans = new char[(int) (Math.random() * size) + 1];
		for (int i = 0; i < ans.length; i++) {
			ans[i] = (char) ((int) (Math.random() * possibilities) + 'a');
		}
		return String.valueOf(ans);
	}

	public static void main(String[] args) {
		int possibilities = 5;
		int strSize = 20;
		int matchSize = 5;
		int testTimes = 5000000;
		System.out.println("test begin");
		for (int i = 0; i < testTimes; i++) {
			String str = getRandomString(possibilities, strSize);
			String match = getRandomString(possibilities, matchSize);
			if (getIndexOf(str, match) != str.indexOf(match)) {
				System.out.println("Oops!");
			}
		}
		System.out.println("test finish");
	}

}
