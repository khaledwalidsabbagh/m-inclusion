package entities.cookandlearn;

import java.util.List;

/**
 * ImageQuiz represents data for a match image with corresponding swedish word game.
 * Stores choises the user can pick from, the correct choice, and the image along with the
 * dish this quiz corresponds to
 *
 * @author Lisanu Tebikew Yalew on 1/22/18.
 */
public class ImageQuiz {
    // the dish this quiz is about
    Dish dish;
    // list of choices
    List<String> choices;
    // The correct choice -- the swedish name
    String correctAnswer;
    // The arabic name of the ingredient depicted on the image
    String arabicName;
    // Name of the image
    String image;

    // define what the question is (what is shown in the image) what is not shown in the image ...
    String question;


    /**
     * Constructor to create a new ImageQuiz object
     *
     * @param correctAnswer the correct name of the ingredient dipicted in the picture
     * @param arabicName the arabic name of the ingredient dipicted in this quiz
     * @param image the image showing the ingredient
     * @param choices the list of choices available for the user
     */
    public ImageQuiz(String question, String correctAnswer, String arabicName,
                     String image, List<String> choices) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.arabicName = arabicName;
        this.image = image;
        this.choices = choices;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getArabicName() {
        return arabicName;
    }

    public void setArabicName(String arabicName) {
        this.arabicName = arabicName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getChoices() {
        return choices;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    @Override
    public String toString() {
        return "ImageQuiz{" +
                "correctAnswer='" + correctAnswer + '\'' +
                ", arabicName='" + arabicName + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
