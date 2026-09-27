import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class GeometryAndGrids {
    private static final int[] DR4 = {-1, 0, 1, 0};
    private static final int[] DC4 = {0, 1, 0, -1};
    private static final int[] DR8 = {-1, -1, -1, 0, 0, 1, 1, 1};
    private static final int[] DC8 = {-1, 0, 1, -1, 1, -1, 0, 1};

    public static void main(String[] args) {
        System.out.println("Geometry and grids demos");
        System.out.println("4-neighbours of (1,1): " + neighbours(1, 1, 3, 3, false));
        System.out.println("8-neighbours of (0,0): " + neighbours(0, 0, 3, 3, true));

        char[][] islands = {
                {'1', '1', '0', '0'},
                {'1', '0', '0', '1'},
                {'0', '0', '1', '1'}
        };
        System.out.println("Number of islands: " + countIslands(copy(islands)));

        int[][] image = {
                {1, 1, 1},
                {1, 1, 0},
                {1, 0, 1}
        };
        System.out.println("Flood fill from (1,1) to 2: "
                + Arrays.deepToString(floodFill(image, 1, 1, 2)));

        int[][] oranges = {
                {2, 1, 1},
                {1, 1, 0},
                {0, 1, 1}
        };
        System.out.println("Rotting oranges minutes: " + orangesRotting(oranges));

        int rows = 3;
        int cols = 4;
        int id = toId(2, 3, cols);
        System.out.println("(2,3) in 3x4 grid -> id " + id
                + " -> " + Arrays.toString(fromId(id, cols)));

        int[][] sortedMatrix = {
                {1, 3, 5, 7},
                {10, 11, 16, 20},
                {23, 30, 34, 60}
        };
        System.out.println("Search 16 in flattened matrix: "
                + searchMatrix(sortedMatrix, 16));

        System.out.println("Sudoku box of (7,8): " + sudokuBox(7, 8));
        System.out.println("Main diagonal key r-c for (4,1): " + diagonalKey(4, 1));
        System.out.println("Anti-diagonal key r+c for (4,1): " + antiDiagonalKey(4, 1));

        int[][] square = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        rotateClockwise(square);
        System.out.println("Rotate 3x3 clockwise: " + Arrays.deepToString(square));
        System.out.println("Spiral order: " + spiralOrder(square));
        System.out.println("Generate spiral 3: "
                + Arrays.deepToString(generateSpiral(3)));

        int[][] zeroes = {
                {1, 1, 1},
                {1, 0, 1},
                {1, 1, 1}
        };
        setZeroes(zeroes);
        System.out.println("Set matrix zeroes: " + Arrays.deepToString(zeroes));

        System.out.println("Manhattan (1,2)-(4,6): " + manhattan(1, 2, 4, 6));
        System.out.println("Euclidean squared (1,2)-(4,6): "
                + squaredDistance(1, 2, 4, 6));
        System.out.println("Chebyshev (1,2)-(4,6): " + chebyshev(1, 2, 4, 6));
        System.out.println("Slope key (0,0)->(6,4): " + slopeKey(0, 0, 6, 4));

        int[][] line = {{0, 0}, {2, 2}, {4, 4}, {6, 6}};
        System.out.println("All points on one line: " + checkStraightLine(line));
        System.out.println("Orientation of (0,0),(4,0),(4,3): "
                + orientation(0, 0, 4, 0, 4, 3));
        System.out.println("Triangle area doubled: "
                + twiceTriangleArea(0, 0, 4, 0, 4, 3));

        int[][] polygon = {{0, 0}, {4, 0}, {4, 3}, {0, 3}};
        System.out.println("Rectangle polygon area: " + shoelaceArea(polygon));

        System.out.println("Rectangles overlap: " + rectanglesOverlap(0, 0, 3, 3,
                2, 1, 5, 4));
        System.out.println("Overlap area: " + overlapArea(0, 0, 3, 3, 2, 1, 5, 4));
        System.out.println("Point (3,4) inside radius 5 circle: "
                + insideCircle(3, 4, 5));
    }

    private static List<String> neighbours(int r, int c, int rows, int cols,
                                           boolean eightDirections) {
        int[] dr = eightDirections ? DR8 : DR4;
        int[] dc = eightDirections ? DC8 : DC4;
        List<String> cells = new ArrayList<String>();
        for (int i = 0; i < dr.length; i++) {
            int nr = r + dr[i];
            int nc = c + dc[i];
            if (inBounds(nr, nc, rows, cols)) {
                cells.add("(" + nr + "," + nc + ")");
            }
        }
        return cells;
    }

    private static boolean inBounds(int r, int c, int rows, int cols) {
        return 0 <= r && r < rows && 0 <= c && c < cols;
    }

    private static int countIslands(char[][] grid) {
        int islands = 0;
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                if (grid[r][c] == '1') {
                    islands++;
                    floodLand(grid, r, c);
                }
            }
        }
        return islands;
    }

    private static void floodLand(char[][] grid, int startR, int startC) {
        Queue<int[]> queue = new ArrayDeque<int[]>();
        queue.add(new int[] {startR, startC});
        grid[startR][startC] = '0';
        while (!queue.isEmpty()) {
            int[] cell = queue.remove();
            for (int i = 0; i < 4; i++) {
                int nr = cell[0] + DR4[i];
                int nc = cell[1] + DC4[i];
                if (inBounds(nr, nc, grid.length, grid[0].length)
                        && grid[nr][nc] == '1') {
                    grid[nr][nc] = '0';
                    queue.add(new int[] {nr, nc});
                }
            }
        }
    }

    private static int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
        int oldColor = image[sr][sc];
        if (oldColor == newColor) {
            return image;
        }
        Queue<int[]> queue = new ArrayDeque<int[]>();
        queue.add(new int[] {sr, sc});
        image[sr][sc] = newColor;
        while (!queue.isEmpty()) {
            int[] cell = queue.remove();
            for (int i = 0; i < 4; i++) {
                int nr = cell[0] + DR4[i];
                int nc = cell[1] + DC4[i];
                if (inBounds(nr, nc, image.length, image[0].length)
                        && image[nr][nc] == oldColor) {
                    image[nr][nc] = newColor;
                    queue.add(new int[] {nr, nc});
                }
            }
        }
        return image;
    }

    private static int orangesRotting(int[][] grid) {
        Queue<int[]> queue = new ArrayDeque<int[]>();
        int fresh = 0;
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                if (grid[r][c] == 2) {
                    queue.add(new int[] {r, c});
                } else if (grid[r][c] == 1) {
                    fresh++;
                }
            }
        }
        int minutes = 0;
        while (!queue.isEmpty() && fresh > 0) {
            int levelSize = queue.size();
            for (int step = 0; step < levelSize; step++) {
                int[] cell = queue.remove();
                for (int i = 0; i < 4; i++) {
                    int nr = cell[0] + DR4[i];
                    int nc = cell[1] + DC4[i];
                    if (inBounds(nr, nc, grid.length, grid[0].length)
                            && grid[nr][nc] == 1) {
                        grid[nr][nc] = 2;
                        fresh--;
                        queue.add(new int[] {nr, nc});
                    }
                }
            }
            minutes++;
        }
        return fresh == 0 ? minutes : -1;
    }

    private static int toId(int r, int c, int cols) {
        return r * cols + c;
    }

    private static int[] fromId(int id, int cols) {
        return new int[] {id / cols, id % cols};
    }

    private static boolean searchMatrix(int[][] matrix, int target) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int lo = 0;
        int hi = rows * cols - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int value = matrix[mid / cols][mid % cols];
            if (value == target) {
                return true;
            } else if (value < target) {
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        return false;
    }

    private static int diagonalKey(int r, int c) {
        return r - c;
    }

    private static int antiDiagonalKey(int r, int c) {
        return r + c;
    }

    private static int sudokuBox(int r, int c) {
        return (r / 3) * 3 + c / 3;
    }

    private static void transpose(int[][] matrix) {
        for (int r = 0; r < matrix.length; r++) {
            for (int c = r + 1; c < matrix.length; c++) {
                int temp = matrix[r][c];
                matrix[r][c] = matrix[c][r];
                matrix[c][r] = temp;
            }
        }
    }

    private static void rotateClockwise(int[][] matrix) {
        transpose(matrix);
        for (int[] row : matrix) {
            reverse(row);
        }
    }

    private static void reverse(int[] row) {
        int left = 0;
        int right = row.length - 1;
        while (left < right) {
            int temp = row[left];
            row[left] = row[right];
            row[right] = temp;
            left++;
            right--;
        }
    }

    private static List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> result = new ArrayList<Integer>();
        int top = 0;
        int bottom = matrix.length - 1;
        int left = 0;
        int right = matrix[0].length - 1;
        while (top <= bottom && left <= right) {
            for (int c = left; c <= right; c++) result.add(matrix[top][c]);
            top++;
            for (int r = top; r <= bottom; r++) result.add(matrix[r][right]);
            right--;
            if (top <= bottom) {
                for (int c = right; c >= left; c--) result.add(matrix[bottom][c]);
                bottom--;
            }
            if (left <= right) {
                for (int r = bottom; r >= top; r--) result.add(matrix[r][left]);
                left++;
            }
        }
        return result;
    }

    private static int[][] generateSpiral(int n) {
        int[][] matrix = new int[n][n];
        int top = 0;
        int bottom = n - 1;
        int left = 0;
        int right = n - 1;
        int value = 1;
        while (top <= bottom && left <= right) {
            for (int c = left; c <= right; c++) matrix[top][c] = value++;
            top++;
            for (int r = top; r <= bottom; r++) matrix[r][right] = value++;
            right--;
            for (int c = right; c >= left; c--) matrix[bottom][c] = value++;
            bottom--;
            for (int r = bottom; r >= top; r--) matrix[r][left] = value++;
            left++;
        }
        return matrix;
    }

    private static void setZeroes(int[][] matrix) {
        boolean firstRowZero = false;
        boolean firstColZero = false;
        for (int c = 0; c < matrix[0].length; c++) {
            firstRowZero = firstRowZero || matrix[0][c] == 0;
        }
        for (int r = 0; r < matrix.length; r++) {
            firstColZero = firstColZero || matrix[r][0] == 0;
        }
        for (int r = 1; r < matrix.length; r++) {
            for (int c = 1; c < matrix[0].length; c++) {
                if (matrix[r][c] == 0) {
                    matrix[r][0] = 0;
                    matrix[0][c] = 0;
                }
            }
        }
        for (int r = 1; r < matrix.length; r++) {
            for (int c = 1; c < matrix[0].length; c++) {
                if (matrix[r][0] == 0 || matrix[0][c] == 0) {
                    matrix[r][c] = 0;
                }
            }
        }
        if (firstRowZero) {
            Arrays.fill(matrix[0], 0);
        }
        if (firstColZero) {
            for (int r = 0; r < matrix.length; r++) {
                matrix[r][0] = 0;
            }
        }
    }

    private static int manhattan(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    private static long squaredDistance(long x1, long y1, long x2, long y2) {
        long dx = x1 - x2;
        long dy = y1 - y2;
        return dx * dx + dy * dy;
    }

    private static int chebyshev(int x1, int y1, int x2, int y2) {
        return Math.max(Math.abs(x1 - x2), Math.abs(y1 - y2));
    }

    private static String slopeKey(int x1, int y1, int x2, int y2) {
        int dy = y2 - y1;
        int dx = x2 - x1;
        if (dx == 0) return "1/0";
        if (dy == 0) return "0/1";
        int g = gcd(Math.abs(dy), Math.abs(dx));
        dy /= g;
        dx /= g;
        if (dx < 0) {
            dy = -dy;
            dx = -dx;
        }
        return dy + "/" + dx;
    }

    private static int gcd(int a, int b) {
        while (b != 0) {
            int temp = a % b;
            a = b;
            b = temp;
        }
        return a;
    }

    private static boolean checkStraightLine(int[][] points) {
        int x0 = points[0][0];
        int y0 = points[0][1];
        int x1 = points[1][0];
        int y1 = points[1][1];
        for (int i = 2; i < points.length; i++) {
            if (cross(x1 - x0, y1 - y0, points[i][0] - x0,
                    points[i][1] - y0) != 0) {
                return false;
            }
        }
        return true;
    }

    private static long cross(long ax, long ay, long bx, long by) {
        return ax * by - ay * bx;
    }

    private static String orientation(int ax, int ay, int bx, int by, int cx, int cy) {
        long value = cross(bx - ax, by - ay, cx - ax, cy - ay);
        if (value > 0) return "left turn";
        if (value < 0) return "right turn";
        return "straight";
    }

    private static long twiceTriangleArea(int ax, int ay, int bx, int by,
                                          int cx, int cy) {
        return Math.abs(cross(bx - ax, by - ay, cx - ax, cy - ay));
    }

    private static double shoelaceArea(int[][] points) {
        long twiceArea = 0;
        for (int i = 0; i < points.length; i++) {
            int[] a = points[i];
            int[] b = points[(i + 1) % points.length];
            twiceArea += (long) a[0] * b[1] - (long) a[1] * b[0];
        }
        return Math.abs(twiceArea) / 2.0;
    }

    private static boolean rectanglesOverlap(int ax1, int ay1, int ax2, int ay2,
                                             int bx1, int by1, int bx2, int by2) {
        return Math.max(ax1, bx1) < Math.min(ax2, bx2)
                && Math.max(ay1, by1) < Math.min(ay2, by2);
    }

    private static int overlapArea(int ax1, int ay1, int ax2, int ay2,
                                   int bx1, int by1, int bx2, int by2) {
        int width = Math.max(0, Math.min(ax2, bx2) - Math.max(ax1, bx1));
        int height = Math.max(0, Math.min(ay2, by2) - Math.max(ay1, by1));
        return width * height;
    }

    private static boolean insideCircle(int x, int y, int r) {
        return (long) x * x + (long) y * y <= (long) r * r;
    }

    private static char[][] copy(char[][] grid) {
        char[][] result = new char[grid.length][grid[0].length];
        for (int r = 0; r < grid.length; r++) {
            result[r] = Arrays.copyOf(grid[r], grid[r].length);
        }
        return result;
    }
}
