package base.Iemdb;

import java.util.ArrayList;

import base.Models.BaseModel;

public class DAO<Model extends BaseModel>{
    private ArrayList<Model> entries = new ArrayList<>();

    public void add(Model m){
       entries.add(m);
    }

    public void update(Model m){
        for(int i = 0; i < entries.size(); i++){
            if(entries.get(i).getKey().equals(m.getKey())){
                entries.remove(i);
                break;
            }
        }
        entries.add(m);
    }

    public int getCount(){
        return entries.size();
    }

    public ArrayList<Model> all(){
        return entries;
    }

    public Model findOne(Object key){
        for(Model model : entries)
            if(model.getKey().equals(key))
                return model;
        return null;
    }

    public Boolean contains(Object key){
        for(Model m : entries)
            if(m.getKey().equals(key))
                return true;
        return false;
    }

    public void print(){
        for(Model model : entries) {
            System.out.println(model.getKey());
        }
    }
}
