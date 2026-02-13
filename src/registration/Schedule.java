package registration;

public class Schedule
{
    private static final int NOT_FOUND = -1;
    private static final int CAPACITY = 4;
    private Section[] sections; // all the sections
    private int numSections;

    /**
     Find a section in our current list
     * @param section section to be found
     * @return the index of section in the list, -1 otherwise
     */
    private int find(Section section)
    {
        for(int i = 0; i < this.sections.length; i++)
        {
            if(section.equals(this.sections[i])) return i;
        }
        return NOT_FOUND;
    }

    /**
     Determines if the current section list is full
     * @return true if at max capacity, false otherwise
     */
    private boolean isFull()
    {
        return this.numSections == this.sections.length;
    }

    /**
     Increase capcity by 4
     */
    private void grow()
    {
        this.numSections += CAPACITY;
        Section [] newList = new Section [numSections];
        for(int i = 0; i < this.sections.length; i++)
        {
            newList[i] = this.sections[i];
        }
        this.sections = newList;
    }

    /**
     Add section to list of sections
     * @param section section to be added
     */
    public void add(Section section)
    {
        if(isFull())
        {
            this.grow();
        }
        sections[numSections++] = section;
    }
    public void remove(Section section)
    {
        if(!this.contains(section)) return;
        Section lastSection = this.sections[numSections -1]; //get last section

        //replace last with what we want to remove

        int index = this.find(section);
        this.sections[numSections - 1] = null;
        this.sections[index] = lastSection;
    }
    public void enroll(Section section, Student student)
    {

    }
    public void drop(Section section, Student student)
    {

    }
    public boolean contains(Section section)
    {
        if(this.find(section) == NOT_FOUND) return false;
        return true;
    }

    public void printByClassroom()
    {
        //selection sort
        for(int i = 0; i < this.numSections; i++)
        {
            int swapIndex = i;
            Section smallestSection = this.sections[i];
            for(int j = i + 1; j < this.numSections; j++)
            {
                //compare by campus first

                //if equal go by building
                //collegeave.compareTo(busch) will return 1
                int campusCompare = smallestSection.getClassroom().getCampus().compareTo(this.sections[j].getClassroom().getCampus());
                if(campusCompare > 0)
                {
                    swapIndex = j;
                    smallestSection = this.sections[j];
                }
                if(campusCompare == 0)
                {
                    //check building
                    if(smallestSection.getClassroom().getBuilding().compareTo(this.sections[j].getClassroom().getBuilding()) > 0)
                    {
                        swapIndex = j;
                        smallestSection = this.sections[j];

                    }
                }
            }
            Section temp = this.sections[i];
            this.sections[i] = this.sections[swapIndex];
            this.sections[swapIndex] = temp;


        }
        for(Section s: this.sections) //print now that it is sorted
        {
            System.out.print(s+ " ");
        }
    }
    public void printByCourse()
    {
        //selection sort by course#, then period
        for(int i = 0; i < numSections; i++)
        {
            int swapIndex = i;
            Section smallestSection = this.sections[i];
            for(int j = i + 1; j < this.numSections; j++)
            {
                //compare by coursenumber first

                //if equal go by building
                //collegeave.compareTo(busch) will return 1
                int courseCompare = smallestSection.getCourse().compareTo(this.sections[j].getCourse());
                if(courseCompare > 0)
                {
                    swapIndex = j;
                    smallestSection = this.sections[j];
                }
                if(courseCompare == 0)
                {
                    //check building
                    if(smallestSection.getTime().compareTo(this.sections[j].getTime()) > 0)
                    {
                        swapIndex = j;
                        smallestSection = this.sections[j];

                    }
                }
            }
            Section temp = this.sections[i];
            this.sections[i] = this.sections[swapIndex];
            this.sections[swapIndex] = temp;
        }

    }
}
