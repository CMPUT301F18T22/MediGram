package medigram.medigram;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RecordList implements Serializable {
    private ArrayList<Record> recordList = new ArrayList<Record>();

    /**
     * get the list of records
     * @return recordList
     */
    public ArrayList<Record> getRecordList() {
        return recordList;
    }

    /**
     * add a record to list
     * @param newRecord
     */
    public void addRecord(Record newRecord) {
        recordList.add(newRecord);
    }

    /**
     * delete a record from list
     * @param record
     */
    public void removeRecord(Record record){
        recordList.remove(record);
    }

    /**
     * check if a record exists in list
     * @param record
     * @return true if record exists in list, false otherwise
     */
    public Boolean recordExist(Record record){
        return recordList.contains(record);
    }

    /**
     * get the index of a record in the list
     * @param record
     * @return index of record
     */
    public int getIndex(Record record){
        return recordList.indexOf(record);
    }

    /**
     * Gets the record at the given index
     * @param index The index of the record to be returned
     */
    public void updateRecord(int index, Record record){
        recordList.set(index, record);
    }
    /**
     * get the record given its index in the list
     * @param index
     * @return record
     */
    public Record getRecord(int index){
        return recordList.get(index);
    }

    /**
     * get the size of record list
     * @return size of record list
     */
    public int getSize(){
        return recordList.size();
    }

    /**
     * transform record list to string
     * @return string form of the record list
     */
    public String toString(){
        return recordList.toString();
    }


}
