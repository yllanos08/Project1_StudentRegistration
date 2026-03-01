package util;

public class Sort {


    /**

     * @param list
     * @param <E>
     */
    public static <E extends Comparable<E>> void selSort(List<E> list)
    {
        int length = list.size();
        for(int i = 0; i < length; i++)
        {
            int minIndex = i;
            for(int j = i + 1; j < length; j++)
            {
                if(list.get(j).compareTo(list.get(minIndex)) < 0) //j comes before current min
                {
                    minIndex = j;
                }
            }
            E temp = list.get(minIndex);
            list.set(minIndex, list.get(i));
            list.set(i, temp);

        }
    }
}
