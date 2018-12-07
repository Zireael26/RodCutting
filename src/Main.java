public class Main {

    public static void main(String[] args) {
        // given array contains the selling prices of rods of size = index
        int[] sellPriceArray = {0, 3, 5, 6, 15, 10, 25, 12, 24};
        rodCutTab(sellPriceArray);
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
}
