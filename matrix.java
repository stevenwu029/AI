import java.util.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit ;
class matrix {

    //For enqueue and dequeue the unreached list for dij
    public static Queue enqueue(Queue queue, int row, int column){
        Queue ptr = queue;
        if (queue == null){
            queue = new Queue(row,column);
            return queue;
        }

        while(ptr.next != null){
            ptr = ptr.next;
        }
        Queue newNode = new Queue(row,column);
        ptr.next = newNode;
        return queue;
    }
    public static Queue dequeue(Queue queue){
        Queue ptr = queue;
        if (queue == null){
            return null;
        }
        queue = queue.next;
        return queue;
    }
    //End of enqueue and dequeue for dij

    // From here is method for A*
    public static AsQueue AsEnqueue(AsQueue queue, int row, int column, boolean returnVal){
        if (queue == null){
            queue = new AsQueue(row,column,returnVal);
            //System.out.println("very beginning "+ "["+ row + ","+column+"]");
            //print(queue);
            return queue;
        }

        AsQueue ptr = queue;
        AsQueue prev = null;
        while (ptr != null){
           
            if (ptr.samePlace == false){
                break;
            }
            prev = ptr;
            ptr = ptr.next;
        }

        if (ptr != null && prev == null){
            AsQueue newQueue = new AsQueue(row,column,returnVal);
            newQueue.next = ptr;
            //System.out.println("bigger than the first " + "["+ row + ","+column+"]");
            //print(newQueue);
            return newQueue;
        } else if (ptr != null && prev != null){
            AsQueue newQueue = new AsQueue(row,column,returnVal);
            prev.next = newQueue;
            newQueue.next = ptr;
            //System.out.println("in the middle "+ "["+ row + ","+column+"]");
            //print(queue);
            return queue;
        }else{
            AsQueue newQueue = new AsQueue(row,column,returnVal);
            prev.next = newQueue;
            //System.out.println("very end "+ "["+ row + ","+column+"]");
            //print(queue);
            return queue;
        }  
    }
    public static void print (AsQueue queue){
        AsQueue ptr = queue;
        while (ptr != null){
            System.out.println("[" + ptr.row + "," + ptr.column + "]");
            ptr = ptr.next;
        }
    }
    public static AsQueue AsDequeue(AsQueue queue){
        AsQueue ptr = queue;
        if (queue == null){
            return null;
        }
        queue = queue.next;
        return queue;
    }
    // end of A* methods
    

    //For creating a matrix
    public static int[][] matrixReturn (int[][] matrix, int level, int num){
        if (level == num - 1){
            matrix[level][level] = (int) (Math.random()*(level + 1));
            while (matrix[level][level] == 0){
                 matrix[level][level] = (int) (Math.random()*(level + 1));
            }
            return matrix;
        }
        //up
        for (int i = level; i < num; i ++){
            matrix[level][i] = (int) (Math.random()*(num ));
             while (matrix[level][i] == 0){
                 matrix[level][i] = (int) (Math.random()*(num));
            }
        }
        //left
        for (int i = level; i < num; i ++){
            matrix[i][level] = (int) (Math.random()*(num));
             while (matrix[i][level] == 0){
                 matrix[i][level] = (int) (Math.random()*(num));
            }
        }
        //right
        for (int i = level; i < num; i ++){
            matrix[i][num-1] = (int) (Math.random()*(num));
             while (matrix[i][num-1] == 0){
                 matrix[i][num-1] = (int) (Math.random()*(num));
            }
        }
        //bottom
        for (int i = level; i < num; i ++){
            matrix[num-1][i] = (int) (Math.random()*(num));
             while (matrix[num-1][i] == 0){
                 matrix[num-1][i] = (int) (Math.random()*(num));
            }
        }
        matrixReturn(matrix,level+1,num-1);
        return matrix;
    }
    //End of creating matrix



    public static void main (String  [] args) {
        Scanner myObj = new Scanner(System.in);
        System.out.println("Enter an odd number");
        int n = myObj.nextInt();
        while (n % 2 == 0 || n <= 1){
            System.out.println("You have to Enter an ODD NUMBER that is GREATER THAN ONE");
            n = myObj.nextInt();
        }
        
      
        int [][]matrix = new int [n][n];
        matrix = matrixReturn(matrix,0,n);
        
        //Print out the matrix
        System.out.println("Task2: The original matrix is:");
        for (int i = 0;  i < matrix.length; i++) {
            for ( int j = 0; j < matrix.length; j++) {
                if (i == n - 1 && j == n - 1){
                    System.out.print("G" + "\t");
                } else {
                    System.out.print(matrix[i][j] + "\t");

                }  
            }
            System.out.println("\n");
        }
        System.out.println("\n");


        //For Dijkstra search
        Queue head = null;
        int[][] ansDij = new int[n][n];
        int countNull = 0;
        //Long cancelTime = System.nanoTime();
        //Long startTimeFirst = System.nanoTime();
        ansDij = Dijkstra(matrix,n,head);
        //Long endTimeFirst = System.nanoTime();
        //long timePassesFirst = endTimeFirst - startTimeFirst;
        
        System.out.println("Task 3: Function evaluation");
        for (int i = 0; i < n; i ++){
            for (int j = 0; j < n; j ++){
                if (ansDij[i][j] == Integer.MAX_VALUE){
                    System.out.print("X" + "\t");
                    countNull --;
                } else {
                    System.out.print(ansDij[i][j] + "\t");
                }
                
            }
            System.out.println("\n");
        }
        if (ansDij[n-1][n-1] != Integer.MAX_VALUE){
            System.out.println("The value function is "+ ansDij[n-1][n-1]);
        } else {
            System.out.println("The value function is "+ countNull);
        }
        //System.out.println("Time passed in nanoSecond using Dijkstra for original matrix is: " + timePassesFirst);
        System.out.println("\n");


        // Hill Climbing
        System.out.println("Task 4: New Matrix After Hill");
        Scanner myObj2 = new Scanner(System.in);
        System.out.println("Please input the number of iterations");
        int n2 = myObj2.nextInt();
        head = null;
        int[][] harder = new int[n][n];
        harder = Clone(matrix,harder,n,n);
        Long hillStartTime = System.nanoTime();
        harder = bestpuzzle(harder, n2,n);
        Long hillEndTime = System.nanoTime();
        long hillTimePasses = hillEndTime - hillStartTime;
        for (int i = 0;  i < n; i++) {
            for ( int j = 0; j < n; j++) {
                if (i == n - 1 && j == n - 1){
                    System.out.print("G" + "\t");
                } else {
                    System.out.print(harder[i][j] + "\t");

                }  
            }
            System.out.println("\n");
        }
        System.out.println("Time passed in nanoSecond for hill Climbing is: " + hillTimePasses);
        System.out.println("\n");


        //long whatever1 = System.nanoTime();
        //long whatever2 = System.nanoTime();
        //long whatever = whatever2 - whatever1;
        //Slove hill Climbing using Dijkstra
        System.out.println("Task 5: Solve new Matrix using Dijkstra");
        int[][] ansHar = new int[n][n];
        Long startTime = System.nanoTime();
        ansHar = Dijkstra(harder,n,head);
        Long endTime = System.nanoTime();
        long timePasses = endTime - startTime;
        for (int i = 0; i < n; i ++){
            for (int j = 0; j < n; j ++){
                if (ansHar[i][j] == Integer.MAX_VALUE){
                    System.out.print("X" + "\t");
                } else {
                    System.out.print(ansHar[i][j] + "\t");
                }
                
            }
            System.out.println("\n");
        }
        System.out.println("Time passed in nanoSecond using Dijkstra after hill Climbing is: " + timePasses);
        System.out.println("\n");


        //For A* search After hill
        AsQueue asHead = null;
        ansDij = new int[n][n];
        long newStartTime = System.nanoTime();
        int[][] ansAS = new int[n][n];
        ansAS = AStar(harder,n,asHead);
        long newLongEndTime = System.nanoTime();
        long newTimePasses = newLongEndTime - newStartTime;
        System.out.println("Task 6: Solve new Matrix using A*");
        for (int i = 0; i < n; i ++){
            for (int j = 0; j < n; j ++){
                if (ansAS[i][j] == Integer.MAX_VALUE){
                    System.out.print("X" + "\t");
                } else {
                    System.out.print(ansAS[i][j] + "\t");
                }
                
            }
            System.out.println("\n");
        }
        System.out.println("Time passed in nanoSecond using A* after hill Climbing is: " + newTimePasses);
        System.out.println("\n");



        //Genetic
        System.out.println("Task 7: Matrix After genetic");
        head = null;
        int[][] geneticAlgo = new int[n][n];
        Scanner myObj3 = new Scanner(System.in);
        System.out.println("Please input the number of iterations");
        int n3 = myObj3.nextInt();
        Long genStartTime = System.nanoTime();
        geneticAlgo = GeneticAlgo(matrix,n,head,n3);
        Long genEndTime = System.nanoTime();
        long genTimePasses = genEndTime - genStartTime;
        for (int i = 0;  i < n; i++) {
            for ( int j = 0; j < n; j++) {
                if (i == n - 1 && j == n - 1){
                    System.out.print("G" + "\t");
                } else {
                    System.out.print(geneticAlgo[i][j] + "\t");

                }  
            }
            System.out.println("\n");
        }
        System.out.println("Time passed in nanoSecond For GA is: " + genTimePasses);
        System.out.println("\n");


        //Solve Genetic using Dijkstra
        System.out.println("Solve Genetic");
        head = null;
        int[][] genAns = new int[n][n];
        genAns = Dijkstra(geneticAlgo,n,head);
        for (int i = 0; i < n; i ++){
            for (int j = 0; j < n; j ++){
                if (genAns[i][j] == Integer.MAX_VALUE){
                    System.out.print("X" + "\t");
                } else {
                    System.out.print(genAns[i][j] + "\t");
                }
                
            }
            System.out.println("\n");
        }
    }
    public static int[][] GeneticAlgo(int [][] matrix, int size, Queue head, int loopTime){

        int[][] runTime = new int[size][size];
        int[][] runTimeAns = new int[size][size];
        runTimeAns = Dijkstra(matrix,size,head);
        while (runTimeAns[size - 1][size - 1] == Integer.MAX_VALUE){
            runTime = matrixReturn(runTime, 0, size);
            runTimeAns = Dijkstra(runTime,size,head);
        }
        head = null;
        int[][] ans = new int[size][size];
        ans = Clone(runTime,ans,size,size);
       // System.out.println("Reached");
        int count = runTimeAns[size - 1][size - 1];//record the first time's value
        // System.out.println();
        int[][] generateA = new int[size][size];
        generateA =  matrixReturn(generateA, 0, size);
        int[][] generateB = new int[size][size];
        generateB =  matrixReturn(generateB, 0, size);
        int[][] generateC = new int[size][size];
        generateC =  matrixReturn(generateC, 0, size);
        int[][] generateD = new int[size][size];
        generateD =  matrixReturn(generateD, 0, size);
        int[][] generateE = new int[size][size];
        generateE =  matrixReturn(generateE, 0, size);
        int[][] generateF = new int[size][size];
        generateF =  matrixReturn(generateF, 0, size);
        int[][] generateG = new int[size][size];
        generateG =  matrixReturn(generateG, 0, size);
        int[][] generateH = new int[size][size];
        generateH =  matrixReturn(generateH, 0, size);
        int[][] generateI = new int[size][size];
        generateI =  matrixReturn(generateI, 0, size);
        int[][] generateJ = new int[size][size];
        generateJ =  matrixReturn(generateJ, 0, size);
        

        while (loopTime > 0){
            int[][] copyA = new int[size][size];
            int[][] copyB = new int[size][size];
            int[][] copyC = new int[size][size];
            int[][] copyD = new int[size][size];
            int[][] copyE = new int[size][size];
            int[][] copyF = new int[size][size];
            int[][] copyG = new int[size][size];
            int[][] copyH = new int[size][size];
            int[][] copyI = new int[size][size];
            int[][] copyJ = new int[size][size];

            copyA = Clone(generateA,copyA,size,size);
            copyB = Clone(generateB,copyB,size,size);
            copyC = Clone(generateC,copyC,size,size);
            copyD = Clone(generateD,copyD,size,size);
            copyE = Clone(generateE,copyE,size,size);
            copyF = Clone(generateF,copyF,size,size);
            copyG = Clone(generateG,copyG,size,size);
            copyH = Clone(generateH,copyH,size,size);
            copyI = Clone(generateI,copyI,size,size);
            copyJ = Clone(generateJ,copyJ,size,size);
            //int takeTurn = (int) (Math.random() * (2));
            //if 1, go row, if 0, go column

            int rowFrom = (int) (Math.random() * (size));
            int rowTo = (int) (Math.random() * (size));
            while ((rowFrom >= rowTo) || (rowFrom == 0 && rowTo == 0)){
                rowFrom = (int) (Math.random() * (size));
                rowTo = (int) (Math.random() * (size));
            }
            int columnFrom = (int) (Math.random() * (size));
            int columnTo = (int) (Math.random() * (size));
            while ((columnFrom >= columnTo) || (columnFrom == 0 && columnTo == 0)){
                columnFrom = (int) (Math.random() * (size));
                columnTo = (int) (Math.random() * (size));
            }

            int finalRow = rowTo - rowFrom + 1;
            int finalColumn = columnTo - columnFrom + 1;
            int[][] smallerA = new int[finalRow][finalColumn];
            int[][] smallerB = new int[finalRow][finalColumn];
            int[][] smallerC = new int[finalRow][finalColumn];
            int[][] smallerD = new int[finalRow][finalColumn];
            int[][] smallerE = new int[finalRow][finalColumn];
            int[][] smallerF = new int[finalRow][finalColumn];
            int[][] smallerG = new int[finalRow][finalColumn];
            int[][] smallerH = new int[finalRow][finalColumn];
            int[][] smallerI = new int[finalRow][finalColumn];
            int[][] smallerJ = new int[finalRow][finalColumn];
            // System.out.println("finalRow: " + finalRow +" finalColumn: " + finalColumn);
            // System.out.println("rowFrom: " + rowFrom + " rowTo: " + rowTo + " columnFrom: " + columnFrom + " columnTo: " + columnTo);

            smallerA = cutMatrix(copyA,smallerA,rowFrom,rowTo,columnFrom,columnTo);
            smallerB = cutMatrix(copyB,smallerB,rowFrom,rowTo,columnFrom,columnTo);
            smallerC = cutMatrix(copyC,smallerC,rowFrom,rowTo,columnFrom,columnTo);
            smallerD = cutMatrix(copyD,smallerD,rowFrom,rowTo,columnFrom,columnTo);
            smallerE = cutMatrix(copyE,smallerE,rowFrom,rowTo,columnFrom,columnTo);
            smallerF = cutMatrix(copyF,smallerF,rowFrom,rowTo,columnFrom,columnTo);
            smallerG = cutMatrix(copyG,smallerG,rowFrom,rowTo,columnFrom,columnTo);
            smallerH = cutMatrix(copyH,smallerH,rowFrom,rowTo,columnFrom,columnTo);
            smallerI = cutMatrix(copyI,smallerI,rowFrom,rowTo,columnFrom,columnTo);
            smallerJ = cutMatrix(copyJ,smallerJ,rowFrom,rowTo,columnFrom,columnTo);
            
            int method = (int) (Math.random() * (10));

            if (method == 0 || method == 1){
                copyA = mutMatrix(copyA,smallerB,rowFrom,rowTo,columnFrom,columnTo);
                boolean mutABigger = biggerThanCount(copyA,size,head,count);
                head = null;
                if (mutABigger == true){
                    int[][] ansIn = new int[size][size];
                    ansIn = Dijkstra(copyA,size,head);
                    head = null;
                    count = ansIn[size - 1][size - 1];
                    ans = Clone(copyA,ans,size,size);
                    generateA = Clone(copyA,ans,size,size);
                }
                copyB = mutMatrix(copyB,smallerA,rowFrom,rowTo,columnFrom,columnTo);
                boolean mutBBigger = biggerThanCount(copyB,size,head,count);
                head = null;
                if (mutBBigger == true){
                    int[][] ansIn = new int[size][size];
                    ansIn = Dijkstra(copyB,size,head);
                    head = null;
                    count = ansIn[size - 1][size - 1];
                    ans = Clone(copyB,ans,size,size);
                    generateB = Clone(copyB,ans,size,size);
                }
                copyC = mutMatrix(copyC,smallerD,rowFrom,rowTo,columnFrom,columnTo);
                boolean mutCBigger = biggerThanCount(copyC,size,head,count);
                head = null;
                if (mutCBigger == true){
                    int[][] ansIn = new int[size][size];
                    ansIn = Dijkstra(copyC,size,head);
                    head = null;
                    count = ansIn[size - 1][size - 1];
                    ans = Clone(copyC,ans,size,size);
                    generateC = Clone(copyC,ans,size,size);
                }
                copyD = mutMatrix(copyD,smallerC,rowFrom,rowTo,columnFrom,columnTo);
                boolean mutDBigger = biggerThanCount(copyD,size,head,count);
                head = null;
                if (mutDBigger == true){
                    int[][] ansIn = new int[size][size];
                    ansIn = Dijkstra(copyD,size,head);
                    head = null;
                    count = ansIn[size - 1][size - 1];
                    ans = Clone(copyD,ans,size,size);
                    generateD = Clone(copyD,ans,size,size);
                }
                copyE = mutMatrix(copyE,smallerF,rowFrom,rowTo,columnFrom,columnTo);
                boolean mutEBigger = biggerThanCount(copyE,size,head,count);
                head = null;
                if (mutEBigger == true){
                    int[][] ansIn = new int[size][size];
                    ansIn = Dijkstra(copyE,size,head);
                    head = null;
                    count = ansIn[size - 1][size - 1];
                    ans = Clone(copyE,ans,size,size);
                    generateE = Clone(copyE,ans,size,size);
                }
                copyF = mutMatrix(copyF,smallerE,rowFrom,rowTo,columnFrom,columnTo);
                boolean mutFBigger = biggerThanCount(copyF,size,head,count);
                head = null;
                if (mutFBigger == true){
                    int[][] ansIn = new int[size][size];
                    ansIn = Dijkstra(copyF,size,head);
                    head = null;
                    count = ansIn[size - 1][size - 1];
                    ans = Clone(copyF,ans,size,size);
                    generateF = Clone(copyF,ans,size,size);
                }
                copyG = mutMatrix(copyG,smallerH,rowFrom,rowTo,columnFrom,columnTo);
                boolean mutGBigger = biggerThanCount(copyG,size,head,count);
                head = null;
                if (mutGBigger == true){
                    int[][] ansIn = new int[size][size];
                    ansIn = Dijkstra(copyG,size,head);
                    head = null;
                    count = ansIn[size - 1][size - 1];
                    ans = Clone(copyG,ans,size,size);
                    generateG = Clone(copyG,ans,size,size);
                }
                copyH = mutMatrix(copyH,smallerG,rowFrom,rowTo,columnFrom,columnTo);
                boolean mutHBigger = biggerThanCount(copyH,size,head,count);
                head = null;
                if (mutHBigger == true){
                    int[][] ansIn = new int[size][size];
                    ansIn = Dijkstra(copyH,size,head);
                    head = null;
                    count = ansIn[size - 1][size - 1];
                    ans = Clone(copyH,ans,size,size);
                    generateH = Clone(copyH,ans,size,size);
                }
                copyI = mutMatrix(copyI,smallerJ,rowFrom,rowTo,columnFrom,columnTo);
                boolean mutIBigger = biggerThanCount(copyI,size,head,count);
                head = null;
                if (mutIBigger == true){
                    int[][] ansIn = new int[size][size];
                    ansIn = Dijkstra(copyI,size,head);
                    head = null;
                    count = ansIn[size - 1][size - 1];
                    ans = Clone(copyI,ans,size,size);
                    generateI = Clone(copyI,ans,size,size);
                }
                copyJ = mutMatrix(copyJ,smallerI,rowFrom,rowTo,columnFrom,columnTo);
                boolean mutJBigger = biggerThanCount(copyJ,size,head,count);
                head = null;
                if (mutJBigger == true){
                    int[][] ansIn = new int[size][size];
                    ansIn = Dijkstra(copyJ,size,head);
                    head = null;
                    count = ansIn[size - 1][size - 1];
                    ans = Clone(copyJ,ans,size,size);
                    generateJ = Clone(copyJ,ans,size,size);
                }
            }
            if (method == 2 || method == 3){
                copyA = mutMatrix(copyA,smallerC,rowFrom,rowTo,columnFrom,columnTo);
                boolean mutABigger = biggerThanCount(copyA,size,head,count);
                head = null;
                if (mutABigger == true){
                    int[][] ansIn = new int[size][size];
                    ansIn = Dijkstra(copyA,size,head);
                    head = null;
                    count = ansIn[size - 1][size - 1];
                    ans = Clone(copyA,ans,size,size);
                    generateA = Clone(copyA,ans,size,size);
                }
                copyC = mutMatrix(copyC,smallerA,rowFrom,rowTo,columnFrom,columnTo);
                boolean mutCBigger = biggerThanCount(copyC,size,head,count);
                head = null;
                if (mutCBigger == true){
                    int[][] ansIn = new int[size][size];
                    ansIn = Dijkstra(copyC,size,head);
                    head = null;
                    count = ansIn[size - 1][size - 1];
                    ans = Clone(copyC,ans,size,size);
                    generateC = Clone(copyC,ans,size,size);
                }
                copyD = mutMatrix(copyD,smallerF,rowFrom,rowTo,columnFrom,columnTo);
                boolean mutDBigger = biggerThanCount(copyD,size,head,count);
                head = null;
                if (mutDBigger == true){
                    int[][] ansIn = new int[size][size];
                    ansIn = Dijkstra(copyD,size,head);
                    head = null;
                    count = ansIn[size - 1][size - 1];
                    ans = Clone(copyD,ans,size,size);
                    generateD = Clone(copyD,ans,size,size);
                }
                copyF = mutMatrix(copyF,smallerD,rowFrom,rowTo,columnFrom,columnTo);
                boolean mutFBigger = biggerThanCount(copyF,size,head,count);
                head = null;
                if (mutFBigger == true){
                    int[][] ansIn = new int[size][size];
                    ansIn = Dijkstra(copyF,size,head);
                    head = null;
                    count = ansIn[size - 1][size - 1];
                    ans = Clone(copyF,ans,size,size);
                    generateF = Clone(copyF,ans,size,size);
                }
                copyG = mutMatrix(copyG,smallerI,rowFrom,rowTo,columnFrom,columnTo);
                boolean mutGBigger = biggerThanCount(copyG,size,head,count);
                head = null;
                if (mutGBigger == true){
                    int[][] ansIn = new int[size][size];
                    ansIn = Dijkstra(copyG,size,head);
                    head = null;
                    count = ansIn[size - 1][size - 1];
                    ans = Clone(copyG,ans,size,size);
                    generateG = Clone(copyG,ans,size,size);
                }
                copyI = mutMatrix(copyI,smallerG,rowFrom,rowTo,columnFrom,columnTo);
                boolean mutIBigger = biggerThanCount(copyI,size,head,count);
                head = null;
                if (mutIBigger == true){
                    int[][] ansIn = new int[size][size];
                    ansIn = Dijkstra(copyI,size,head);
                    head = null;
                    count = ansIn[size - 1][size - 1];
                    ans = Clone(copyI,ans,size,size);
                    generateI = Clone(copyI,ans,size,size);
                }
                copyH = mutMatrix(copyH,smallerJ,rowFrom,rowTo,columnFrom,columnTo);
                boolean mutHBigger = biggerThanCount(copyH,size,head,count);
                head = null;
                if (mutHBigger == true){
                    int[][] ansIn = new int[size][size];
                    ansIn = Dijkstra(copyH,size,head);
                    head = null;
                    count = ansIn[size - 1][size - 1];
                    ans = Clone(copyH,ans,size,size);
                    generateH = Clone(copyH,ans,size,size);
                }
                copyJ = mutMatrix(copyJ,smallerH,rowFrom,rowTo,columnFrom,columnTo);
                boolean mutJBigger = biggerThanCount(copyJ,size,head,count);
                head = null;
                if (mutJBigger == true){
                    int[][] ansIn = new int[size][size];
                    ansIn = Dijkstra(copyJ,size,head);
                    head = null;
                    count = ansIn[size - 1][size - 1];
                    ans = Clone(copyJ,ans,size,size);
                    generateJ = Clone(copyJ,ans,size,size);
                }
                copyB = mutMatrix(copyB,smallerE,rowFrom,rowTo,columnFrom,columnTo);
                boolean mutBBigger = biggerThanCount(copyB,size,head,count);
                head = null;
                if (mutBBigger == true){
                    int[][] ansIn = new int[size][size];
                    ansIn = Dijkstra(copyB,size,head);
                    head = null;
                    count = ansIn[size - 1][size - 1];
                    ans = Clone(copyB,ans,size,size);
                    generateB = Clone(copyB,ans,size,size);
                }
                copyE = mutMatrix(copyE,smallerB,rowFrom,rowTo,columnFrom,columnTo);
                boolean mutEBigger = biggerThanCount(copyE,size,head,count);
                head = null;
                if (mutEBigger == true){
                    int[][] ansIn = new int[size][size];
                    ansIn = Dijkstra(copyE,size,head);
                    head = null;
                    count = ansIn[size - 1][size - 1];
                    ans = Clone(copyE,ans,size,size);
                    generateE = Clone(copyE,ans,size,size);
                }
            }
            if (method == 4 || method == 5){
                copyA = mutMatrix(copyA,smallerD,rowFrom,rowTo,columnFrom,columnTo);
                boolean mutABigger = biggerThanCount(copyA,size,head,count);
                head = null;
                if (mutABigger == true){
                    int[][] ansIn = new int[size][size];
                    ansIn = Dijkstra(copyA,size,head);
                    head = null;
                    count = ansIn[size - 1][size - 1];
                    ans = Clone(copyA,ans,size,size);
                    generateA = Clone(copyA,ans,size,size);
                }
                copyD = mutMatrix(copyD,smallerA,rowFrom,rowTo,columnFrom,columnTo);
                boolean mutDBigger = biggerThanCount(copyD,size,head,count);
                head = null;
                if (mutDBigger == true){
                    int[][] ansIn = new int[size][size];
                    ansIn = Dijkstra(copyD,size,head);
                    head = null;
                    count = ansIn[size - 1][size - 1];
                    ans = Clone(copyD,ans,size,size);
                    generateD = Clone(copyD,ans,size,size);
                }
                copyE = mutMatrix(copyE,smallerH,rowFrom,rowTo,columnFrom,columnTo);
                boolean mutEBigger = biggerThanCount(copyE,size,head,count);
                head = null;
                if (mutEBigger == true){
                    int[][] ansIn = new int[size][size];
                    ansIn = Dijkstra(copyE,size,head);
                    head = null;
                    count = ansIn[size - 1][size - 1];
                    ans = Clone(copyE,ans,size,size);
                    generateE = Clone(copyE,ans,size,size);
                }
                copyH = mutMatrix(copyH,smallerE,rowFrom,rowTo,columnFrom,columnTo);
                boolean mutHBigger = biggerThanCount(copyH,size,head,count);
                head = null;
                if (mutHBigger == true){
                    int[][] ansIn = new int[size][size];
                    ansIn = Dijkstra(copyH,size,head);
                    head = null;
                    count = ansIn[size - 1][size - 1];
                    ans = Clone(copyH,ans,size,size);
                    generateH = Clone(copyH,ans,size,size);
                }
                copyB = mutMatrix(copyB,smallerI,rowFrom,rowTo,columnFrom,columnTo);
                boolean mutBBigger = biggerThanCount(copyB,size,head,count);
                head = null;
                if (mutBBigger == true){
                    int[][] ansIn = new int[size][size];
                    ansIn = Dijkstra(copyB,size,head);
                    head = null;
                    count = ansIn[size - 1][size - 1];
                    ans = Clone(copyB,ans,size,size);
                    generateB = Clone(copyB,ans,size,size);
                }
                copyI = mutMatrix(copyI,smallerB,rowFrom,rowTo,columnFrom,columnTo);
                boolean mutIBigger = biggerThanCount(copyI,size,head,count);
                head = null;
                if (mutIBigger == true){
                    int[][] ansIn = new int[size][size];
                    ansIn = Dijkstra(copyI,size,head);
                    head = null;
                    count = ansIn[size - 1][size - 1];
                    ans = Clone(copyI,ans,size,size);
                    generateI = Clone(copyI,ans,size,size);
                }
                copyG = mutMatrix(copyG,smallerJ,rowFrom,rowTo,columnFrom,columnTo);
                boolean mutGBigger = biggerThanCount(copyG,size,head,count);
                head = null;
                if (mutGBigger == true){
                    int[][] ansIn = new int[size][size];
                    ansIn = Dijkstra(copyG,size,head);
                    head = null;
                    count = ansIn[size - 1][size - 1];
                    ans = Clone(copyG,ans,size,size);
                    generateG = Clone(copyG,ans,size,size);
                }
                copyJ = mutMatrix(copyJ,smallerG,rowFrom,rowTo,columnFrom,columnTo);
                boolean mutJBigger = biggerThanCount(copyJ,size,head,count);
                head = null;
                if (mutJBigger == true){
                    int[][] ansIn = new int[size][size];
                    ansIn = Dijkstra(copyJ,size,head);
                    head = null;
                    count = ansIn[size - 1][size - 1];
                    ans = Clone(copyJ,ans,size,size);
                    generateJ = Clone(copyJ,ans,size,size);
                }
                copyC = mutMatrix(copyC,smallerF,rowFrom,rowTo,columnFrom,columnTo);
                boolean mutCBigger = biggerThanCount(copyC,size,head,count);
                head = null;
                if (mutCBigger == true){
                    int[][] ansIn = new int[size][size];
                    ansIn = Dijkstra(copyC,size,head);
                    head = null;
                    count = ansIn[size - 1][size - 1];
                    ans = Clone(copyC,ans,size,size);
                    generateC = Clone(copyC,ans,size,size);
                }
                copyF = mutMatrix(copyF,smallerC,rowFrom,rowTo,columnFrom,columnTo);
                boolean mutFBigger = biggerThanCount(copyF,size,head,count);
                head = null;
                if (mutFBigger == true){
                    int[][] ansIn = new int[size][size];
                    ansIn = Dijkstra(copyF,size,head);
                    head = null;
                    count = ansIn[size - 1][size - 1];
                    ans = Clone(copyF,ans,size,size);
                    generateF = Clone(copyF,ans,size,size);
                }
            }
            if (method == 6 || method == 7){
                copyA = mutMatrix(copyA,smallerE,rowFrom,rowTo,columnFrom,columnTo);
                boolean mutABigger = biggerThanCount(copyA,size,head,count);
                head = null;
                if (mutABigger == true){
                    int[][] ansIn = new int[size][size];
                    ansIn = Dijkstra(copyA,size,head);
                    head = null;
                    count = ansIn[size - 1][size - 1];
                    ans = Clone(copyA,ans,size,size);
                    generateA = Clone(copyA,ans,size,size);
                }
                copyE = mutMatrix(copyE,smallerA,rowFrom,rowTo,columnFrom,columnTo);
                boolean mutEBigger = biggerThanCount(copyE,size,head,count);
                head = null;
                if (mutEBigger == true){
                    int[][] ansIn = new int[size][size];
                    ansIn = Dijkstra(copyE,size,head);
                    head = null;
                    count = ansIn[size - 1][size - 1];
                    ans = Clone(copyE,ans,size,size);
                    generateE = Clone(copyE,ans,size,size);
                }
                copyF = mutMatrix(copyF,smallerJ,rowFrom,rowTo,columnFrom,columnTo);
                boolean mutFBigger = biggerThanCount(copyF,size,head,count);
                head = null;
                if (mutFBigger == true){
                    int[][] ansIn = new int[size][size];
                    ansIn = Dijkstra(copyF,size,head);
                    head = null;
                    count = ansIn[size - 1][size - 1];
                    ans = Clone(copyF,ans,size,size);
                    generateF = Clone(copyF,ans,size,size);
                }
                copyJ = mutMatrix(copyJ,smallerF,rowFrom,rowTo,columnFrom,columnTo);
                boolean mutJBigger = biggerThanCount(copyJ,size,head,count);
                head = null;
                if (mutJBigger == true){
                    int[][] ansIn = new int[size][size];
                    ansIn = Dijkstra(copyJ,size,head);
                    head = null;
                    count = ansIn[size - 1][size - 1];
                    ans = Clone(copyJ,ans,size,size);
                    generateJ = Clone(copyJ,ans,size,size);
                }
                copyB = mutMatrix(copyB,smallerG,rowFrom,rowTo,columnFrom,columnTo);
                boolean mutBBigger = biggerThanCount(copyB,size,head,count);
                head = null;
                if (mutBBigger == true){
                    int[][] ansIn = new int[size][size];
                    ansIn = Dijkstra(copyB,size,head);
                    head = null;
                    count = ansIn[size - 1][size - 1];
                    ans = Clone(copyB,ans,size,size);
                    generateB = Clone(copyB,ans,size,size);
                }
                copyG = mutMatrix(copyG,smallerB,rowFrom,rowTo,columnFrom,columnTo);
                boolean mutGBigger = biggerThanCount(copyG,size,head,count);
                head = null;
                if (mutGBigger == true){
                    int[][] ansIn = new int[size][size];
                    ansIn = Dijkstra(copyG,size,head);
                    head = null;
                    count = ansIn[size - 1][size - 1];
                    ans = Clone(copyG,ans,size,size);
                    generateG = Clone(copyG,ans,size,size);
                }
                copyC = mutMatrix(copyC,smallerH,rowFrom,rowTo,columnFrom,columnTo);
                boolean mutCBigger = biggerThanCount(copyC,size,head,count);
                head = null;
                if (mutCBigger == true){
                    int[][] ansIn = new int[size][size];
                    ansIn = Dijkstra(copyC,size,head);
                    head = null;
                    count = ansIn[size - 1][size - 1];
                    ans = Clone(copyC,ans,size,size);
                    generateC = Clone(copyC,ans,size,size);
                }
                copyH = mutMatrix(copyH,smallerC,rowFrom,rowTo,columnFrom,columnTo);
                boolean mutHBigger = biggerThanCount(copyH,size,head,count);
                head = null;
                if (mutHBigger == true){
                    int[][] ansIn = new int[size][size];
                    ansIn = Dijkstra(copyH,size,head);
                    head = null;
                    count = ansIn[size - 1][size - 1];
                    ans = Clone(copyH,ans,size,size);
                    generateH = Clone(copyH,ans,size,size);
                }
                copyD = mutMatrix(copyD,smallerI,rowFrom,rowTo,columnFrom,columnTo);
                boolean mutDBigger = biggerThanCount(copyD,size,head,count);
                head = null;
                if (mutDBigger == true){
                    int[][] ansIn = new int[size][size];
                    ansIn = Dijkstra(copyD,size,head);
                    head = null;
                    count = ansIn[size - 1][size - 1];
                    ans = Clone(copyD,ans,size,size);
                    generateD = Clone(copyD,ans,size,size);
                }
                copyI = mutMatrix(copyI,smallerD,rowFrom,rowTo,columnFrom,columnTo);
                boolean mutIBigger = biggerThanCount(copyI,size,head,count);
                head = null;
                if (mutIBigger == true){
                    int[][] ansIn = new int[size][size];
                    ansIn = Dijkstra(copyI,size,head);
                    head = null;
                    count = ansIn[size - 1][size - 1];
                    ans = Clone(copyI,ans,size,size);
                    generateI = Clone(copyI,ans,size,size);
                }
            }
            if (method == 8 || method == 9){
                copyA = mutMatrix(copyA,smallerJ,rowFrom,rowTo,columnFrom,columnTo);
                boolean mutABigger = biggerThanCount(copyA,size,head,count);
                head = null;
                if (mutABigger == true){
                    int[][] ansIn = new int[size][size];
                    ansIn = Dijkstra(copyA,size,head);
                    head = null;
                    count = ansIn[size - 1][size - 1];
                    ans = Clone(copyA,ans,size,size);
                    generateA = Clone(copyA,ans,size,size);
                }
                copyJ = mutMatrix(copyJ,smallerA,rowFrom,rowTo,columnFrom,columnTo);
                boolean mutJBigger = biggerThanCount(copyJ,size,head,count);
                head = null;
                if (mutJBigger == true){
                    int[][] ansIn = new int[size][size];
                    ansIn = Dijkstra(copyJ,size,head);
                    head = null;
                    count = ansIn[size - 1][size - 1];
                    ans = Clone(copyJ,ans,size,size);
                    generateJ = Clone(copyJ,ans,size,size);
                }
                copyB = mutMatrix(copyB,smallerI,rowFrom,rowTo,columnFrom,columnTo);
                boolean mutBBigger = biggerThanCount(copyB,size,head,count);
                head = null;
                if (mutBBigger == true){
                    int[][] ansIn = new int[size][size];
                    ansIn = Dijkstra(copyB,size,head);
                    head = null;
                    count = ansIn[size - 1][size - 1];
                    ans = Clone(copyB,ans,size,size);
                    generateB = Clone(copyB,ans,size,size);
                }
                copyI = mutMatrix(copyI,smallerB,rowFrom,rowTo,columnFrom,columnTo);
                boolean mutIBigger = biggerThanCount(copyI,size,head,count);
                head = null;
                if (mutIBigger == true){
                    int[][] ansIn = new int[size][size];
                    ansIn = Dijkstra(copyI,size,head);
                    head = null;
                    count = ansIn[size - 1][size - 1];
                    ans = Clone(copyI,ans,size,size);
                    generateI = Clone(copyI,ans,size,size);
                }
                copyC = mutMatrix(copyC,smallerH,rowFrom,rowTo,columnFrom,columnTo);
                boolean mutCBigger = biggerThanCount(copyC,size,head,count);
                head = null;
                if (mutCBigger == true){
                    int[][] ansIn = new int[size][size];
                    ansIn = Dijkstra(copyC,size,head);
                    head = null;
                    count = ansIn[size - 1][size - 1];
                    ans = Clone(copyC,ans,size,size);
                    generateC = Clone(copyC,ans,size,size);
                }
                copyH = mutMatrix(copyH,smallerC,rowFrom,rowTo,columnFrom,columnTo);
                boolean mutHBigger = biggerThanCount(copyH,size,head,count);
                head = null;
                if (mutHBigger == true){
                    int[][] ansIn = new int[size][size];
                    ansIn = Dijkstra(copyH,size,head);
                    head = null;
                    count = ansIn[size - 1][size - 1];
                    ans = Clone(copyH,ans,size,size);
                    generateH = Clone(copyH,ans,size,size);
                }
                copyD = mutMatrix(copyD,smallerG,rowFrom,rowTo,columnFrom,columnTo);
                boolean mutDBigger = biggerThanCount(copyD,size,head,count);
                head = null;
                if (mutDBigger == true){
                    int[][] ansIn = new int[size][size];
                    ansIn = Dijkstra(copyD,size,head);
                    head = null;
                    count = ansIn[size - 1][size - 1];
                    ans = Clone(copyD,ans,size,size);
                    generateD = Clone(copyD,ans,size,size);
                }
                copyG = mutMatrix(copyG,smallerD,rowFrom,rowTo,columnFrom,columnTo);
                boolean mutGBigger = biggerThanCount(copyG,size,head,count);
                head = null;
                if (mutGBigger == true){
                    int[][] ansIn = new int[size][size];
                    ansIn = Dijkstra(copyG,size,head);
                    head = null;
                    count = ansIn[size - 1][size - 1];
                    ans = Clone(copyG,ans,size,size);
                    generateG = Clone(copyG,ans,size,size);
                }
                copyE = mutMatrix(copyE,smallerF,rowFrom,rowTo,columnFrom,columnTo);
                boolean mutEBigger = biggerThanCount(copyE,size,head,count);
                head = null;
                if (mutEBigger == true){
                    int[][] ansIn = new int[size][size];
                    ansIn = Dijkstra(copyE,size,head);
                    head = null;
                    count = ansIn[size - 1][size - 1];
                    ans = Clone(copyE,ans,size,size);
                    generateE = Clone(copyE,ans,size,size);
                }
                copyF = mutMatrix(copyF,smallerE,rowFrom,rowTo,columnFrom,columnTo);
                boolean mutFBigger = biggerThanCount(copyF,size,head,count);
                head = null;
                if (mutFBigger == true){
                    int[][] ansIn = new int[size][size];
                    ansIn = Dijkstra(copyF,size,head);
                    head = null;
                    count = ansIn[size - 1][size - 1];
                    ans = Clone(copyF,ans,size,size);
                    generateF = Clone(copyF,ans,size,size);
                }
            }

            loopTime --;
        }

        return ans;
    }
    public static int[][] Dijkstra(int[][] matrix, int size, Queue head){
        int[][] distance = new int[size][size];
        head = enqueue(head,0,0);

        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++) {
                distance[i][j] = Integer.MAX_VALUE;
            }
        }
        distance[0][0] = 0;

        while (head != null){
            int getRow = head.row;
            int getColumn = head.column;
            head = dequeue(head);
            if (getRow == size - 1 && getColumn == size - 1){
                break;
            }
            while(true){
                int num = matrix[getRow][getColumn];      //previous node's number
                int howFar = distance[getRow][getColumn]; //previous node's distance

                //move down
                if (getRow + num <= size - 1){
                    int curDistance = distance[getRow + num][getColumn];
                    if (curDistance == Integer.MAX_VALUE){
                        head = enqueue(head,getRow + num,getColumn);
                        distance[getRow + num][getColumn] = howFar + 1;
                    } else {
                        distance[getRow + num][getColumn] = Math.min(curDistance,howFar + 1);
                    }
                }

                //move up
                if (getRow - num >= 0){
                    int curDistance = distance[getRow - num][getColumn];
                    if (curDistance == Integer.MAX_VALUE){
                        head = enqueue(head,getRow - num,getColumn);
                        distance[getRow - num][getColumn] = howFar + 1;
                    } else {
                        distance[getRow - num][getColumn] = Math.min(curDistance,howFar + 1);
                    }
                }

                //move right
                if (getColumn + num <= size - 1){
                    int curDistance = distance[getRow][getColumn + num];
                    if (curDistance == Integer.MAX_VALUE){
                        head = enqueue(head,getRow,getColumn + num);
                        distance[getRow][getColumn + num] = howFar + 1;
                    } else {
                        distance[getRow][getColumn + num] = Math.min(curDistance,howFar + 1);
                    }
                }

                //move left
                if (getColumn - num >= 0){
                    int curDistance = distance[getRow][getColumn - num];
                    if (curDistance == Integer.MAX_VALUE){
                        head = enqueue(head,getRow,getColumn - num);
                        distance[getRow][getColumn - num] = howFar + 1;
                    } else {
                        distance[getRow][getColumn - num] = Math.min(curDistance,howFar + 1);
                    }
                }
                break;

            }
        }

        return distance;
    }
    public static int[][] AStar(int[][] matrix, int size, AsQueue head){
        int[][] distance = new int[size][size];
        AsQueue traverse = head;
        traverse = AsEnqueue(traverse,0,0,false);
        for (int i = 0; i < size; i++){
            for (int j = 0; j < size; j++) {
                distance[i][j] = Integer.MAX_VALUE;
            }
        }
        distance[0][0] = 0;
        //f(n) = g(n) + h(n) in this case, we actuall only need to consider h(n),
        //Using Manhattan distance 
        while (traverse != null){
            int getRow = traverse.row;
            int getColumn = traverse.column;
            traverse = AsDequeue(traverse);
           
            if (getRow == size - 1 && getColumn == size - 1){
                break;
            }
            while(true){
                
                int num = matrix[getRow][getColumn];      //previous node's number
                int howFar = distance[getRow][getColumn]; //previous node's distance

                //move down
                if (getRow + num <= size - 1){
                   // System.out.println("Move down");
                    boolean sameRow = false;
                    boolean sameColumn = false;
                    boolean returnVal = false;
                    if (getRow + num == size - 1){
                        sameRow = true;
                    }
                    if (getColumn == size - 1){
                        sameColumn = true;
                    }
                    if (sameColumn == true || sameRow == true){
                        returnVal = true;
                    }
                    int curDistance = distance[getRow + num][getColumn];
                    if (curDistance == Integer.MAX_VALUE){
                        traverse = AsEnqueue(traverse,getRow + num,getColumn,returnVal);
                        distance[getRow + num][getColumn] = howFar + 1;
                    } else {
                        distance[getRow + num][getColumn] = Math.min(curDistance,howFar + 1);
                    }
                }

                //move up
                if (getRow - num >= 0){
                    //System.out.println("Move up");
                    boolean sameRow = false;
                    boolean sameColumn = false;
                    boolean returnVal = false;
                    if (getRow - num == size - 1){
                        sameRow = true;
                    }
                    if (getColumn == size - 1){
                        sameColumn = true;
                    }
                    if (sameColumn == true || sameRow == true){
                        returnVal = true;
                    }
                    int curDistance = distance[getRow - num][getColumn];
                    if (curDistance == Integer.MAX_VALUE){
                        traverse = AsEnqueue(traverse,getRow - num,getColumn,returnVal);
                        distance[getRow - num][getColumn] = howFar + 1;
                    } else {
                        distance[getRow - num][getColumn] = Math.min(curDistance,howFar + 1);
                    }
                }

                //move right
                if (getColumn + num <= size - 1){
                    //System.out.println("Move right");
                    boolean sameRow = false;
                    boolean sameColumn = false;
                    boolean returnVal = false;
                    if (getRow  == size - 1){
                        sameRow = true;
                    }
                    if (getColumn + num == size - 1){
                        sameColumn = true;
                    }
                    if (sameColumn == true || sameRow == true){
                        returnVal = true;
                    }
                    int curDistance = distance[getRow][getColumn + num];
                    if (curDistance == Integer.MAX_VALUE){
                        traverse = AsEnqueue(traverse,getRow ,getColumn + num,returnVal);
                        distance[getRow][getColumn + num] = howFar + 1;
                    } else {
                        distance[getRow][getColumn + num] = Math.min(curDistance,howFar + 1);
                    }
                }

                //move left
                if (getColumn - num >= 0){
                    //System.out.println("Move left");
                    boolean sameRow = false;
                    boolean sameColumn = false;
                    boolean returnVal = false;
                    if (getRow  == size - 1){
                        sameRow = true;
                    }
                    if (getColumn - num == size - 1){
                        sameColumn = true;
                    }
                    if (sameColumn == true || sameRow == true){
                        returnVal = true;
                    }
                    int curDistance = distance[getRow][getColumn - num];
                    if (curDistance == Integer.MAX_VALUE){
                        traverse = AsEnqueue(traverse,getRow ,getColumn - num,returnVal);
                        distance[getRow][getColumn - num] = howFar + 1;
                    } else {
                        distance[getRow][getColumn - num] = Math.min(curDistance,howFar + 1);
                    }
                }
                break;

            }
        }

        return distance;
    }
    public static int[][] bestpuzzle(int[][] matrix, int iterations,int n){
        //compute the original function value
        Queue head = null;
        int[][] ans = new int[n][n];
        int value = 0;
        int newvalue = 0;
        ans = Dijkstra(matrix,n,head);

        if (ans[n-1][n-1]==Integer.MAX_VALUE){
            //System.out.println("The value function is "+value);
        }else{
            value = ans[n-1][n-1];
            //System.out.println("The value function is "+value);
        }

        //user input number of iterations, atleast 50
        for (int x = 0; x < iterations; x++){//loop
            //seclect a random, non-goal cell
            //value = 0;
            
            newvalue = 0;
            head = null;
           int randomrow = (int) (Math.random()*(n ));
           int randomcol = (int) (Math.random()*(n ));
           while (randomrow == (n-1)&&randomcol == (n-1)){
             randomrow = (int) (Math.random()*(n ));
             randomcol = (int) (Math.random()*(n ));
           }
           int originaldistance = matrix[randomrow][randomcol]; // store random selected cell's original value
        
        int randomdistance = (int) (Math.random()*(n-1))+1;//先这么写，避免illegal move，之后再完善逻辑:(
        boolean left = false, right = false, up = false, down = false;
        while (down != true && up != true && left != true && right != true){
            randomdistance = (int) (Math.random()*(n-1))+1;
            if (randomrow + randomdistance <= n-1){
                down = true;
            } else if (randomrow - randomdistance >= 0){
                up = true;
            } else if (randomcol + randomdistance <= n-1){
                right = true;
            } else if (randomcol - randomdistance >= 0){
                left = true;
            }
            if (randomdistance == matrix[randomrow][randomcol]){
                left = false; right = false; up = false; down = false;
            }
        }
        left = false; right = false; up = false; down = false;
        matrix[randomrow][randomcol] = randomdistance;//set new value to the matrix

        //前面改完数之后，算新的value,然后对比
            ans = Dijkstra(matrix,n,head);

            if (ans[n-1][n-1]==Integer.MAX_VALUE){

                //System.out.println("The newvalue function is "+newvalue);
            }else{
                newvalue = ans[n-1][n-1];
                //System.out.println("The newvalue function is "+newvalue);
            }
           
            if (newvalue>=value && newvalue >= 0){
                value = newvalue;//accept the change
            }else{
                matrix[randomrow][randomcol] = originaldistance;// reject the change and revert the cell to its previous move number
            }

        }//for loop
        //System.out.println("The value function is "+value);
        return matrix;
    }
    public static int[][] cutMatrix(int[][] matrix, int[][] smallerMatrix,int rowFrom, int rowTo, int columnFrom, int columnTo){
        int rowStart = 0;
        int columnStart = 0;
        for (int i = rowFrom; i < rowTo + 1; i ++){
            for (int j = columnFrom; j < columnTo + 1; j ++){
                smallerMatrix[rowStart][columnStart] = matrix[i][j];
                columnStart++;
            }
            columnStart = 0;
            rowStart++;
        }

        return smallerMatrix;
    }
    public static int[][] mutMatrix(int[][] matrix, int[][] smallerMatrix, int rowFrom, int rowTo, int columnFrom, int columnTo ){
        int iSmaller = 0;
        int jSmaller = 0;
        for (int i = rowFrom; i < rowTo; i ++){
            for (int j = columnFrom; j < columnTo; j ++){
                matrix[i][j] = smallerMatrix[iSmaller][jSmaller];
                j++;
            }
            i++;
        }

        return matrix;
    }
    public static boolean biggerThanCount(int[][] matrix,int size, Queue head, int count){

        matrix = Dijkstra(matrix,size,head);
        
        if ((matrix[size - 1][size - 1] > count) && (matrix[size - 1][size - 1] != Integer.MAX_VALUE)){
            //System.out.println("Biggest val: " + matrix[size - 1][size - 1]);
            return true;
        }
        //System.out.println("False");
        return false;
    }
    public static int[][] Clone(int[][] original, int[][] newMatrix, int row, int column){
        for (int i = 0; i < row; i++){
            for (int j = 0; j < column; j++){
                newMatrix[i][j] = original[i][j];
            }
        }
        return newMatrix;
    }
}