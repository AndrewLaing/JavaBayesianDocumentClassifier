/**
 * Author: Andrew Laing
 * Email:  parisianconnections@gmail.com
 * Date:   26/12/2016.
 */
import java.util.List;
import java.util.HashMap;
import java.io.Serializable;

// TODO: 22/12/2016 Add a copy constructor
public class HashMap2D extends HashMap implements Serializable
{
    private HashMap<String, HashMap> outerMap;

    /**
     * Constructor
     */
    public HashMap2D()
    {
        outerMap = new HashMap<String, HashMap>();
    }


    /**
     * The method addElement adds an element to the HashMap.
     * @param key1 The outer key to the element.
     * @param key2 The inner key to the element.
     * @param value The value of the element.
     */
    public void addElement(String key1, String key2, Integer value)
    {
        HashMap<String, Integer> innerMap = outerMap.get(key1);

        if (innerMap==null) {
            innerMap = new HashMap<String,Integer>();
            outerMap.put(key1,innerMap);
        }
        innerMap.put(key2,value);
    }


    /**
     * The method getElement returns the value referenced by the outer
     * and inner keys.
     * @param key1 An outer key to an element.
     * @param key2 An inner key to an element.
     * @return The value referenced by the outer and inner keys.
     */
    public Integer getElement(String key1, String key2)
    {
        // If the key is not in the outerMap quit this method
        if(!outerMap.containsKey(key1))
            return null;

        HashMap<String, Integer> innerMap = outerMap.get(key1);

        return innerMap.get(key2);
    }


    /**
     * The method augment increases the value attached to the outer and inner
     * keys by 1.
     * @param key1 An outer key to an element.
     * @param key2 An inner key to an element.
     */
    public void augment(String key1, String key2)
    {
        if(getElement(key1, key2)==null) {
            addElement(key1, key2, 1);
        }
        else {
            int value = getElement(key1, key2) + 1;
            addElement(key1, key2, value);
        }
    }


    /**
     * The method getOuterKeys adds all of the keys within the outer HashMap
     * to the List passed to it.
     * @param keys A List to add all of the keys in the outer HashMap to.
     */
    public void getOuterKeys(List<String> keys)
    {
        for (String key : outerMap.keySet()) {
            keys.add(key);
        }
    }


    /**
     * The method getInnerKeys adds all of the keys within the inner HashMap
     * attached to the passed key to the List innerKeys.
     * @param key1 A key to an inner HashMap.
     * @param innerKeys A List to add all of the keys within the inner HashMap to.
     */
    public void getInnerKeys(String key1, List<String> innerKeys)
    {
        // If the key is not in the outerMap quit this method
        if(!outerMap.containsKey(key1))
            return;

        HashMap<String, Integer> innerMap = outerMap.get(key1);

        for (String key : innerMap.keySet()) {
            innerKeys.add(key);
        }
    }


    /**
     * The method getValue returns the value from the element referenced by the
     * outer key and the inner key provided.
     * @param key1 The outer key to the element.
     * @param key2 The inner key to the element.
     * @return The value referenced by the outer and inner keys.
     */
    public Integer getValue(String key1, String key2)
    {
        if(!outerMap.containsKey(key1))
            return 0;

        HashMap<String, Integer> innerMap = outerMap.get(key1);

        if(!innerMap.containsKey(key2))
            return 0;

        return innerMap.get(key2);
    }


    /**
     * The method getSumOfAllValues returns the sum of all of the value stored
     * in the inner HashMap referenced by the outer key provided.
     * @param key1 A key to an innner HashMap
     * @return The sum of all of the value stored in the inner HashMap referenced
     *         by the outer key provided.
     */
    public int getSumOfAllValues(String key1)
    {
        int sum = 0;

        if(!outerMap.containsKey(key1))
            return sum;

        HashMap<String, Integer> innerMap = outerMap.get(key1);

        // Iterate through the categories and add their totals to sum
        for (String key2 : innerMap.keySet()) {
            sum += getElement(key1, key2);
        }

        return sum;
    }
}
