public class GaussJordan {

    // Function to Print matrix.
    static void PrintMatrix(float[][] ar, int n, int m)
    {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.print(ar[i][j] + "  ");
            }
            System.out.println();
        }
    }

    // Function to Print inverse matrix
    static void PrintInverse(float[][] ar, int n, int m)
    {
        for (int i = 0; i < n; i++) {
            for (int j = n; j < m; j++) {
                System.out.printf("%.3f  ", ar[i][j]);
            }
            System.out.println();
        }
    }

    // Function to perform the inverse operation on the
    // matrix.
    static void InverseOfMatrix(float[][] matrix, int order)
    {
        // Matrix Declaration.

        float temp;

        // PrintMatrix function to print the element
        // of the matrix.
        System.out.println("=== Matrix ===");
        PrintMatrix(matrix, order, order);

        // Create the augmented matrix
        for (int i = 0; i < order; i++) {

            for (int j = 0; j < 2 * order; j++) {

                // Add '1' at the diagonal places of
                // the matrix to create a identity matrix
                if (j == (i + order))
                    matrix[i][j] = 1;
            }
        }

        // Interchange the row of matrix,
        // interchanging of row will start from the last row
        for (int i = order - 1; i > 0; i--) {

            if (matrix[i - 1][0] < matrix[i][0]) {
                float[] tempArr = matrix[i];
                matrix[i] = matrix[i - 1];
                matrix[i - 1] = tempArr;
            }
        }

        // Replace a row by sum of itself and a
        for (int i = 0; i < order; i++) {

            for (int j = 0; j < order; j++) {

                if (j != i) {

                    temp = matrix[j][i] / matrix[i][i];
                    for (int k = 0; k < 2 * order; k++) {

                        matrix[j][k] -= matrix[i][k] * temp;
                    }
                }
            }
        }

        for (int i = 0; i < order; i++) {

            temp = matrix[i][i];
            for (int j = 0; j < 2 * order; j++) {

                matrix[i][j] = matrix[i][j] / temp;
            }
        }

        // print the resultant Inverse matrix.
        System.out.println("\n=== Inverse Matrix ===");
        PrintInverse(matrix, order, 2 * order);
    }

    public static void main(String[] args)
    {
        int order;

        order = 3;

        float[][] matrix = new float[20][20];

        matrix[0][0] = 1;
        matrix[0][1] = 0;
        matrix[0][2] = 0;
        matrix[1][0] = 2;
        matrix[1][1] = 1;
        matrix[1][2] = 0;
        matrix[2][0] = 5;
        matrix[2][1] = 4;
        matrix[2][2] = 1;

        // Get the inverse of matrix
        InverseOfMatrix(matrix, order);
    }
}