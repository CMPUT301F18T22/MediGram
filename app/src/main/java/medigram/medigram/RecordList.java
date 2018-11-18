package medigram.medigram;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RecordList implements Serializable {
    private ArrayList<Record> recordList = new ArrayList<Record>();

    public void addRecord(Record newRecord) {
        recordList.add(newRecord);
    }

    public void removeRecord(Record record){
        recordList.remove(record);
    }

    public Boolean recordExist(Record record){
        return recordList.contains(record);
    }

    public int getIndex(Record record){
        return recordList.indexOf(record);
    }

    public int getSize(){
        return recordList.size();
    }

    public String toString(){
        return recordList.toString();
    }


}
