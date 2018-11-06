package pokerGame
//two types of table types, fixed limit and no limit tables. can be changed due to backend preferences. 
public enum TypeTable {
    
    FIXED_LIMIT("Fixed-Limit"),
    
    NO_LIMIT("No-Limit"), ;
    
    private String name;
    
    TypeTable(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }

}
