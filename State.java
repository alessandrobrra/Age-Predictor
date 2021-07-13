public class State {
    private String name;
    private ArrayList<List> genders;
    public State(){
        name = null;
        genders = new ArrayList<List>();
    }
    public State(String name){
        this.name = name;
        genders = new ArrayList<List>();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<List> getGenders() {
        return genders;
    }
}
