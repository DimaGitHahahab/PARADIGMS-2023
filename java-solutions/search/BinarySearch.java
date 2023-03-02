package search;

public class BinarySearch {
    //Pred: any array[i] >= array[i + 1]
    //Post: R == min(i: array[i] <= target)
    static int iterative(final int[] array, final int target) {
        int left = -1;
        // left = -1 && any array[i] >= array[i + 1]
        int right = array.length;
        // left = -1 && right = array.length && any array[i] >= array[i + 1] && left < right
        // I: array[left'] > target && array[right'] <= target && left' < right'
        while (right > left + 1) {
            // array[left'] > target && array[right'] <= target && left' < right' && left' + 1 < right'
            int middle = left + (right - left) / 2;
            // array[left'] > target && array[right'] <= target && left' < right' && left' + 1 < right' &&
            // && middle' = (left' + right') // 2 && left' < middle' < right'
            if (array[middle] <= target) {
                // array[left'] > target && array[middle'] <= target && left' < middle' &&
                // && left' < middle' <= right' && middle' = (left' + right') // 2
                right = middle;
                // array[left'] > target && array[right'] <= target && left' < right' &&
                // && middle' = (left' + right') // 2 && left' < middle' <= right'
            } else {
                // array[middle'] > target && array[right'] <= target && left' < right' &&
                // && middle' = (left' + right') // 2 && left' < middle' <= right'
                left = middle;
                // array[left'] > target && array[right'] <= target && left' < right' &&
                // && middle' = (left' + right') // 2 && left' < middle' <= right' && array[left'] > target
            }
            // array[left'] > target && array[right'] <= target && left' < right' &&
            // && dif(right'', left'') < dif(right', left')
        }
        // array[left'] > target && array[right'] <= target && left' < right' && right' = left' + 1

        //(array[right'] <= target < array[left'] && right' = left' + 1) -> right' = min(i: array[i] <= target)
        return right;
    }

    //Pred: any array[i] >= array[i + 1] && left >= - 1 && right <= array.length && left < right
    //Post: R == min(i: array[i] <= target)
    static int recursive(final int[] array, final int target, int left, int right) {
        // I: array[left'] > target && array[right'] <= target && left' < right'
        if (right == left + 1) {
            //(array[right'] <= target < array[left'] && right' = left' + 1) -> right' = min(i: array[i] <= target)
            return right;
        } // array[left'] > target && array[right'] <= target && left' < right' && left + 1 < right
        int middle = left + (right - left) / 2;
        // array[left'] > target && array[right'] <= target && left' < right' && left' + 1 < right' &&
        // && middle' = (left' + right') // 2 && left' < middle' <= right'
        if (array[middle] <= target) {
            // array[left'] > target && array[middle'] <= target && left' < middle' &&
            // && left' < middle' <= right' && middle' = (left' + right') // 2
            // Pred: any array[i] >= array[i + 1] && left >= - 1 && middle <= array.length && left < middle
            // Post: R == min(i: array[i] <= target)
            return recursive(array, target, left, middle);
        } else {
            // array[middle'] > target && array[right'] <= target && left' < right' &&
            // && middle' = (left' + right') // 2 && left' < middle' <= right'
            // Pred: any array[i] >= array[i + 1] && middle >= - 1 && right <= array.length && middle < right
            // Post: R == min(i: array[i] <= target)
            return recursive(array, target, middle, right);
        }
    }

    //Pred: args.length >= 2 && args[0] == target
    public static void main(String[] args) {
        int target = Integer.parseInt(args[0]);
        int[] array = new int[args.length - 1];
        for (int i = 0; i < array.length; i++) {
            array[i] = Integer.parseInt(args[i + 1]);
        }
        int sum = 0;
        for (int j : array) {
            sum += j;
        }
        if (sum%2 == 0) {
            System.out.println(iterative(array, target));
        } else {
            System.out.println(recursive(array, target, -1, array.length));
        }
    }
}
