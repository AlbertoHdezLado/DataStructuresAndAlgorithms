public class Drawer {
	static int level = 0;

	private static void drawLine(int n, char ch) {
		for (int i = 0; i < n; i++)
			System.out.print(ch);
	}

	public static void drawPyramid(int n) {
		for (int i = 1; i <= n; i++) {
			for (int j = n - i + level; j > 0; j--)
				System.out.print(' ');
			drawLine(2 * i - 1, 'X');
			System.out.print('\n');
		}

	}

	public static void drawChristmassTree(int n) {
		for (int i = 1; i <= n; i++) {
			level = n - i;
			drawPyramid(i);
		}
		level = 0;
	}

}
