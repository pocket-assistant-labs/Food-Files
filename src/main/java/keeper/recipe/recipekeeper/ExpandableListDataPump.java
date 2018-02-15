package keeper.recipe.recipekeeper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
* This class takes data read from the file system and translates it
* into usable formatting for the expandable list view
* */

public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData(ArrayList<String> cats, ArrayList<String> names) {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        for (int i = 0; i < cats.size(); i++) {
            List<String> tempArray = new ArrayList<>();
            for (int j = 0; j < names.size(); j++) {
                String n = names.get(j).substring(names.get(j).indexOf(':') + 1, names.get(j).length());
                String cat = cats.get(i);
                String comp = names.get(j).substring(0, names.get(j).indexOf(':'));
                if (cat.compareTo(comp) == 0) {
                    tempArray.add(n);
                }
            }
            expandableListDetail.put(cats.get(i), tempArray);
        }

        return expandableListDetail;
    }
}