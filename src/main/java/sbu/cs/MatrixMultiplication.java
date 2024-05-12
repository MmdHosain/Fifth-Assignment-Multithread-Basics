package sbu.cs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MatrixMultiplication {
    public static List<List<Integer>> finalMatrix = new ArrayList<List<Integer>>();

    // You are allowed to change all code in the BlockMultiplier class
    public static class BlockMultiplier implements Runnable
    {
        List<List<Integer>> matrix_A = new ArrayList<>();
        List<List<Integer>> matrix_B = new ArrayList<>();
        int a;
        int b;
        int c;
        int d;
        int q;
        public BlockMultiplier(List<List<Integer>> matrix_A, List<List<Integer>> matrix_B,int a,int b,int c,int d) {
            this.matrix_A = matrix_A;
            this.matrix_B = matrix_B;
            this.a=a;
            this.b=b;
            this.c=c;
            this.d=d;
            q = matrix_B.size();
        }

        @Override
        public void run() {
            for (int i = a; i <= b; i++) {
                for (int j = c; j <= d; j++) {
                    Integer sum = 0;
                    for (int k = 0; k < q; k++) {
                        sum += matrix_A.get(i).get(k)*matrix_B.get(k).get(j);
                    }
                    finalMatrix.get(i).set(j ,sum);
                }
            }
        }
    }

    /*
    Matrix A is of the form p x q
    Matrix B is of the form q x r
    both p and r are even numbers
    */
    public static List<List<Integer>> ParallelizeMatMul(List<List<Integer>> matrix_A, List<List<Integer>> matrix_B) throws InterruptedException {
        int p = matrix_A.size();
        int q = matrix_B.size();
        int r = matrix_B.getFirst().size();

        for (int i = 0; i < p; i++) {
            finalMatrix.add(new ArrayList<>());
            for (int j = 0; j < r; j++) {
                finalMatrix.get(i).add(0);
            }
        }

        BlockMultiplier block1 = new BlockMultiplier(matrix_A,matrix_B,0,(p/2)-1,0,(r/2)-1);
        BlockMultiplier block2 = new BlockMultiplier(matrix_A,matrix_B,(p/2), p-1,0,(r/2)-1);
        BlockMultiplier block3 = new BlockMultiplier(matrix_A,matrix_B,0,(p/2)-1,(r/2),r-1);
        BlockMultiplier block4 = new BlockMultiplier(matrix_A,matrix_B,(p/2), p-1,(r/2),r-1);

        Thread thread1 = new Thread(block1);
        Thread thread2 = new Thread(block2);
        Thread thread3 = new Thread(block3);
        Thread thread4 = new Thread(block4);


        thread1.start();
        thread2.start();
        thread3.start();

        thread4.start();
        thread1.join();
        thread2.join();
        thread3.join();
        thread4.join();

        for (int i = 0; i < matrix_A.size(); i++) {
            for (int j = 0; j < matrix_B.get(1).size(); j++) {
                System.out.print(finalMatrix.get(i).get(j)+" ");
            }
            System.out.println();
        }
        return finalMatrix;

    }

    public static void main(String[] args) throws InterruptedException {
        List<List<Integer>> matrix1 = new ArrayList<List<Integer>>();
        List<List<Integer>> matrix2 = new ArrayList<List<Integer>>();
        matrix1.add(Arrays.asList(1, 0, 0));
        matrix1.add(Arrays.asList(0, 1, 0));
        matrix1.add(Arrays.asList(0, 0, 1));

        matrix2.add(Arrays.asList(1, 2, 3));
        matrix2.add(Arrays.asList(4, 1, 6));
        matrix2.add(Arrays.asList(7, 8, 1));
        for (int i = 0; i < matrix1.size(); i++) {
            finalMatrix.add(new ArrayList<>());
            for (int j = 0; j < matrix2.get(1).size(); j++) {
                finalMatrix.get(i).add(0);
            }
        }
        ParallelizeMatMul(matrix1,matrix2);
        for (int i = 0; i < matrix1.size(); i++) {
            for (int j = 0; j < matrix2.get(1).size(); j++) {
                System.out.print(finalMatrix.get(i).get(j)+" ");
            }
            System.out.println();
        }
        System.out.print("");
    }
}
