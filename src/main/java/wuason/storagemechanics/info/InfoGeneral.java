package wuason.storagemechanics.info;

public class InfoGeneral {

    private String NameSpaceID;
    private int count;

    public InfoGeneral(String id){

        this.NameSpaceID = id;
        this.count = 0;

    }

    public String getNameSpaceID() {
        return NameSpaceID;
    }

    public void setNameSpaceID(String nameSpaceID) {
        NameSpaceID = nameSpaceID;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void addCount(int add){

        setCount(add + getCount());

    }
}
