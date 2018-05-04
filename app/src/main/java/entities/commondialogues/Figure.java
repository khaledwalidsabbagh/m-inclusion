package entities.commondialogues;

/**
 * Created by Lisanu on 1/22/18.
 */


public class Figure {

    //private properties
    private String name;
    private String caption;

    /**
     * @param name name of the image (E.g vatten-snabbkaffepulver)
     * @param caption caption for the image (E.g Vatten och snabbkaffepulver.)
     */
    public Figure(String name, String caption) {
        this.name = name;
        this.caption = caption;
    }

    //getter and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    @Override
    public String toString() {
        return "Figure{" +
                "name='" + name + '\'' +
                ", caption='" + caption + '\'' +
                '}';
    }
}
