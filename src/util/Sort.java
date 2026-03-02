package util;

import registration.Section;
import registration.StudentList;
import registration.Student;

public class Sort {


    /**
    Standard selection sort on generic list
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


    /**
     Sorts list by campus then building using a selection sort algorithm.
     * @param list list to be sorted.
     */
    public static void locSort(List <Section> list) {
        //selection sort
        for (int i = 0; i < list.size(); i++) {
            int swapIndex = i;
            Section smallestSection = list.get(i);
            for (int j = i + 1; j < list.size(); j++) {

                int campusCompare = smallestSection.getClassroom().getCampus().compareTo(list.get(j).getClassroom().getCampus());
                if (campusCompare > 0) {
                    swapIndex = j;
                    smallestSection = list.get(j);
                }
                if (campusCompare == 0) {
                    //check building
                    if (smallestSection.getClassroom().getBuilding().compareTo(list.get(j).getClassroom().getBuilding()) > 0) {
                        swapIndex = j;
                        smallestSection = list.get(j);

                    }
                }
            }
            Section temp = list.get(i);
            list.set(i, list.get(swapIndex));
            list.set(swapIndex, temp);

        }
    }

    /**
        Sorts list by course number, then period. Uses a selection sort algorithm.
     * @param list list to be sorted.
     */
    public static void courseSort(List <Section> list)
    {
        //selection sort by course#, then period
        for(int i = 0; i < list.size(); i++)
        {
            int swapIndex = i;

            for(int j = i + 1; j < list.size(); j++) {
                int courseCompare = list.get(j).getCourse().name().compareTo(list.get(swapIndex).getCourse().name());;

                if (courseCompare < 0) swapIndex = j;
                else if (courseCompare == 0) {
                    if (list.get(j).getPeriod().compareTo(list.get(swapIndex).getPeriod()) < 0) swapIndex = j;
                }
            }
            Section temp = list.get(i);
            list.set(i, list.get(swapIndex));
            list.set(swapIndex, temp);
        }
    }

    /**
     Sorts list by major
     * @param list list to be sorted
     */
    public static void majorSort(StudentList list)
    {
        for(int i = 0 ; i < list.size(); i++)
        {
            int minIndex = i;
            for(int j = i + 1; j < list.size(); j++)
            {
                Student student = list.get(j);
                Student min = list.get(minIndex);

                if(student.getMajor().name().compareTo(min.getMajor().name()) < 0)
                {
                    //curr student comes before our min
                    minIndex = j;
                }
            }
            Student temp = list.get(i);
            list.set(i, list.get(minIndex));
            list.set(minIndex, temp);
        }
    }
}
