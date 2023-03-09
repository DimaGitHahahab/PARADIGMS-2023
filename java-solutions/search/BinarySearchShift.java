package search;

public class BinarySearchShift {
    //  Pred: any array[i] > array[i + 1] \ {i = k}
    //  Post: R == (k + 1): array[k] < array[k + 1]
    static int iterative(final int[] array) {
        int left = 0;
        //  left = 0 && any array[i] > array[i + 1] \ {i = k}
        int right = array.length - 1;
        //  left = 0 && right = array.length - 1 && any array[i] > array[i + 1] \ {i = k}

        //  I: left' <= k <= right' && array[k] < array[k + 1]
        while (left < right) {
            // left' <= k <= right' && left' < right' array[k] < array[k + 1]
            int middle = left + (right - left) / 2;
            // left' <= k <= right' && left' < right' && middle' = (left' + right') // 2 array[k] < array[k + 1]
            if (array[middle] > array[right]) {
                // array[middle'] > array[right'] && left' <= k <= middle' && left' < middle' && middle' = (left' + right') // 2 &&
                // && array[k] < array[k + 1]
                right = middle;
                // array[middle'] > array[right'] && left' <= k <= right' && left' < right' && middle' = (left' + right') // 2 &&
                // && array[k] < array[k + 1]
            } else {
                // middle' + 1 <= k <= right' && (middle + 1)' <= right' && middle' = (left' + right') // 2 && array[middle'] < array[right'] &&
                // && array[k] < array[k + 1]
                left = middle + 1;
                // left' <= k <= right' && left' < right' && middle' = (left' + right') // 2 && array[middle'] < array[right'] &&
                // && array[k] < array[k + 1]
            }
            // left' <= k <= right' && left' < right' && array[k] < array[k + 1]
        }
        // left' <= k <= right' && left >= right -> left' == right' == k && && array[k] < array[k + 1] &&
        // array[left' - 1] > array[left'] && array[right'] < array[right' + 1] -> R == (k + 1): array[k] < array[k + 1]
        return right;
    }

    // Pred: any array[i] > array[i + 1] \ {i = k} && left = 0 && right = array.length - 1 && left <= k <= right
    // Post: R == (k + 1): array[k] < array[k + 1]
    static int recursive(final int[] array, int left, int right) {
        // I: left' <= k <= right' && array[k] < array[k + 1]
        if (left == right) {
            // left' == right' == k && array[k] < array[k + 1] && array[left' - 1] > array[left'] && array[right'] < array[right' + 1] ->
            // -> R == (k + 1): array[k] < array[k + 1]
            return right;
        }
        // left' <= k <= right' && array[k] < array[k + 1]
        int middle = left + (right - left) / 2;
        // left' <= k <= right' && left' < right' && middle' = (left' + right') // 2 && array[k] < array[k + 1]
        if (array[middle] > array[right]) {
            // array[middle'] > array[right'] && left' <= k <= middle' && left' < middle' && middle' = (left' + right') // 2 &&
            // && array[k] < array[k + 1]

            //Pred: any array[i] > array[i + 1] \ {i = k} && left' <= k <= middle' && left' < middle' && middle' = (left' + right') // 2 &&
            // && array[k] < array[k + 1]
            //Post: R == (k + 1): array[k] < array[k + 1]
            return recursive(array, left, middle);
        } else {
            // middle' + 1 <= k <= right' && (middle + 1)' <= right' && middle' = (left' + right') // 2 && array[middle'] < array[right'] &&
            // && array[k] < array[k + 1]
            return recursive(array, middle + 1, right);
        }
    }

    //Pred: args.length > 0 && any array[i] > array[i + 1] \ {i = k}1
    public static void main(String[] args) {
        int[] array = new int[args.length];
        for (int i = 0; i < array.length; i++) {
            array[i] = Integer.parseInt(args[i]);
        }
        int sum = 0;
        for (int j : array) {
            sum += j;
        }
        if (sum % 2 == 0) {
            System.out.println(iterative(array));
        } else {
            System.out.println(recursive(array, 0, array.length - 1));
        }
    }
}
