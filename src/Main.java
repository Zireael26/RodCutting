public class Main {

    public static void main(String[] args) {
        // given array contains the selling prices of rods of size = index
        int[] sellPriceArray = {0, 3, 5, 6, 15, 10, 25, 12, 24};
        rodCutTab(sellPriceArray);
        rodCutMem(sellPriceArray);
    }

    // maximize the selling price of a given rods, with an SP Array
    private static void rodCutTab(int[] arr) {
        int[] maximCost = new int[arr.length];
        String[] path = new String[arr.length];

        // set values for 0th index
        maximCost[0] = 0;
        path[0] = "";

        // set values for first index
        maximCost[1] = arr[1];
        path[1] = 1 + " ";

        for (int i = 2; i < arr.length; i++) {
            maximCost[i] = arr[i]; // initialize with given selling price, then compete later
            path[i] = i + " ";

            int left = 1;
            int right = i - 1;
            while (left <= right) {
                // if the smaller rds fetch more money, break the rod
                if (maximCost[left] + maximCost[right] > maximCost[i]) {
                    maximCost[i] = maximCost[left] + maximCost[right];
                    path[i] = path[left] + path[right];
                }

                left++;
                right--;
            }
        }

        System.out.println("The maximum selling price for the rod is: " + maximCost[arr.length - 1]);
        System.out.println("The pieces it must be broken down into are: " + path[arr.length - 1]);
    }

    private static void rodCutMem(int[] arr) {
        int[] memArray = new int[arr.length];
        System.out.println("Maximum selling price for the rod is: " + rodCutMemHelper(arr, arr.length - 1, memArray));
    }

    private static int rodCutMemHelper(int[] arr, int rodLength, int[] memArr) {
        // if there's no rod, then price is 0
        if (rodLength == 0) {
            return 0;
        }

        // if we have already calculated the max selling price of a rod of length = redLength, return its value from memoization array
        if (memArr[rodLength] != 0) {
            return memArr[rodLength];
        }

        // else calculate it (will only happen once per rod length)
        // set initial price as the given price (price for selling the rod as a whole)
        int maxSellPrice = arr[rodLength];
        // the max selling prices will be the winner of the comparison of selling the rod as a whole and having the
        // rod broken in pieces of lengths 'left' and 'right'
        int left = 1; // because rod size cant be split to 0 + rodLength, minimum split is 1 + (rodLength - 1)
        int right = rodLength - 1;

        // while all combinations of smaller rods are not checked
        while (left <= right) {
            // max selling price of rods of size 'left' and 'right' respectively
            int mspLeft = rodCutMemHelper(arr, left, memArr);
            int mspRight = rodCutMemHelper(arr, right, memArr);

            // if the total selling price of smaller rods is greater than selling it whole, we break and sell
            if (mspLeft + mspRight > maxSellPrice) {
                maxSellPrice = mspLeft + mspRight;
            }

            // increase size of left rod and decrease size of right rod to try all combinations
            left++;
            right--;
        }

        memArr[rodLength] = maxSellPrice;
        return maxSellPrice;
    }
}
