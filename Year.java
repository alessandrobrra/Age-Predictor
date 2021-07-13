public class Year {
    private ArrayList<Integer> year;
    private String name;
    private int occurrences;
    public Year(){
        year = new ArrayList<Integer>();
        name = null;
        occurrences = -1;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setYear(ArrayList<Integer> year) {
        this.year = year;
    }

    public void addYear(int year) {
        this.year.add(year);
    }

    public String getName() {
        return name;
    }

    public void setOccurrences(int occurrences) {
        this.occurrences = occurrences;
    }

    public int getOccurrences() {
        return occurrences;
    }

    public ArrayList<Integer> getYear() throws Exception {
        return year;
    }
}
